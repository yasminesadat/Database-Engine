package dbms;

/** * @author Wael Abouelsaadat */
// import antlr.SQLLexer;
// import antlr.SQLParser;
// import antlr.myVisitor;
import bPlusTree.bplustree;
import bPlusTree.bplustree.DictionaryPair;
import bPlusTree.bplustree.LeafNode;
// import org.antlr.v4.runtime.CharStream;
// import org.antlr.v4.runtime.CommonTokenStream;
// import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.Integer;
import java.lang.Double;
import java.lang.String;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class DBApp {
	// generic
	private static final String METADATA_PATH = "dbms/src/main/resources/metadata.csv";
	private static final String CONFIG_FILE_PATH = "dbms/src/main/resources/DBApp.config";
	private static final String TABLES_DIR = "dbms/src/main/resources/Tables/";
	private static final String PAGES_DIR = "dbms/src/main/resources/Pages/";
	private static final String INDICES_DIR = "dbms/src/main/resources/Indices/";

	// for JUNIT tests
	// private static final String METADATA_PATH = "E:/Semester 6/Database
	// 2/Project/GitHub/DBEngineWithMaven/dbms/src/main/resources/metadata.csv";
	// private static final String CONFIG_FILE_PATH = "E:/Semester 6/Database
	// 2/Project/GitHub/DBEngineWithMaven/dbms/src/main/resources/DBApp.config";
	// private static final String TABLES_DIR = "E:/Semester 6/Database
	// 2/Project/GitHub/DBEngineWithMaven/dbms/src/main/resources/Tables/";
	// private static final String PAGES_DIR = "E:/Semester 6/Database
	// 2/Project/GitHub/DBEngineWithMaven/dbms/src/main/resources/Pages/";
	// private static final String INDICES_DIR = "E:/Semester 6/Database
	// 2/Project/GitHub/DBEngineWithMaven/dbms/src/main/resources/Indices/";

	public DBApp() {
	}

	// this does whatever initialization you would like
	// or leave it empty if there is no code you want to
	// execute at application startup
	public void init() {
	}

	// following method creates one table only
	// strClusteringKeyColumn is the name of the column that will be the primary
	// key and the clustering column as well. The data type of that column will
	// be passed in htblColNameType
	// htblColNameValue will have the column name as key and the data
	// type as value
	public void createTable(String strTableName,
			String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType) throws DBAppException {

		// Check entered strClusteringKeyColumn
		if (!htblColNameType.containsKey(strClusteringKeyColumn))
			throw new DBAppException("CREATE TABLE: Clustering Key Not Found!");

		// Check if table name already exists and generate an exception
		// Also creates the header for the table
		try {
			File f = new File(METADATA_PATH);
			if (!f.exists()) {
				FileWriter fw = new FileWriter(METADATA_PATH);
				fw.append("Table Name, Column Name, Column Type, ClusteringKey, IndexName,IndexType");
				fw.write(System.lineSeparator());
				fw.close();
			}

			// Check if table already exists
			if (!checkTableExists(strTableName)) {

				// Check for the data types for the allowed data types Integer, String, and
				// Double
				ArrayList<String> allowedDataTypes = new ArrayList<String>(3);
				allowedDataTypes.add("java.lang.integer");
				allowedDataTypes.add("java.lang.double");
				allowedDataTypes.add("java.lang.string");
				boolean errorFlag = false;
				for (String key : htblColNameType.keySet()) {
					String value = (String) htblColNameType.get(key);
					if (!allowedDataTypes.contains(value.toLowerCase())) {
						errorFlag = true;
						throw new DBAppException("CREATE TABLE: Invalid Data Type " + value);
					}
				}
				// Start adding to the csv file after checking the errorFlag
				if (!errorFlag) {
					FileWriter fw = new FileWriter(METADATA_PATH, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw);

					// place clustering key in first row
					out.println(strTableName + ", " + strClusteringKeyColumn + ", "
							+ htblColNameType.get(strClusteringKeyColumn).toLowerCase()
							+ ", True, null, null");
					htblColNameType.remove(strClusteringKeyColumn);

					// loops through each hashtable (column) key and writes each one's data in the
					// csv
					for (String colName : htblColNameType.keySet()) {
						out.println(
								strTableName + ", " + colName + ", " + htblColNameType.get(colName).toLowerCase()
										+ ", False, null, null");
					}
					out.close();
					bw.close();
					fw.close();

				}
			} else
				throw new DBAppException("CREATE TABLE: Table already exists");
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}

		// code to add the Table object to directory
		serializeTable(new Table(strTableName));
	}

	// createIndex
	public void createIndex(String strTableName,
			String strColName,
			String strIndexName) throws DBAppException {

		boolean tableExists = false;
		boolean columnExists = false;
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(METADATA_PATH));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");
				if (s[0].equals(strTableName)) {
					tableExists = true;
					if (s[1].equals(strColName)) {
						columnExists = true;
						if (!s[4].equals("null") && s[5].equals("B+tree")) {
							br.close();
							throw new DBAppException("CREATE INDEX: Index already exists with the name " + s[4]);
						}
					}
				}
				if (s[4].equals(strIndexName)) {
					br.close();
					throw new DBAppException(
							"CREATE INDEX: Duplicate Index Name.");
				}

			}
			br.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}

		if (!tableExists) {
			throw new DBAppException("Table does not exist");
		}
		if (!columnExists) {
			throw new DBAppException("Column doesn't exist");
		}
		bplustree bt = null;
		try {
			Properties prop = new Properties();
			Path path = Path.of(CONFIG_FILE_PATH);

			BufferedReader reader = Files.newBufferedReader(path);
			prop.load(reader);
			// set to half number of tuples in page
			int maxSize = Integer.parseInt(prop.getProperty("MaximumRowsCountinPage")) / 2;
			reader.close();

			bt = new bplustree(maxSize);
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}

		Table tableobj = deserializeTable(strTableName);
		Vector<String> tblPages = tableobj.getStrPages();

		// I assumed that the page path in the vector of strings of the table contains
		// the tablename with the page number for ex: Student1

		for (String pagepath : tblPages) {
			Page page = deserializePage(pagepath);
			String[] s = page.getPageName().split("_");
			Vector<Tuple> pageRecords = page.getRecords();

			for (int i = 0; i < pageRecords.size(); i++) {
				Hashtable<String, Object> htblRow = pageRecords.get(i).getHtblTuple();
				// inserting the colvalue along with a vector having the page number
				bt.insert(htblRow.get(strColName), s[1]);
			}
		}
		serializeIndex(bt, strIndexName);
		// for garbage collection
		bt = null;
		tableobj = null;
		System.gc();
		// Updating the metadata.csv file
		try {
			File csv = new File(METADATA_PATH);
			// Created a vector of strings to store all the lines in the csv file
			Vector<String> allCsvLines = new Vector<>();
			// The csvLine that will get inserted into the allCsvLines vector
			// It will also update the index name and index type if it matches the criteria
			String csvLine;
			BufferedReader br2 = new BufferedReader(new FileReader(csv));
			// Loops line by line and checks if the table name and column name
			// match the ones in the csv file and if it does, it updates the csvLine
			while ((csvLine = br2.readLine()) != null) {
				String[] csvLineChunks = csvLine.split(", ");
				if (csvLineChunks[0].equals(strTableName) && csvLineChunks[1].equals(strColName)) {
					csvLineChunks[4] = strIndexName;
					csvLineChunks[5] = "B+tree";
					csvLine = String.join(", ", csvLineChunks);
				}
				// Adds the csvLine to the allCsvLines vector whether it changed or not
				allCsvLines.add(csvLine);

			}
			br2.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv));
			// Writes the allCsvLines vector to the csv file

			for (String newLine : allCsvLines) {
				bw.write(newLine);
				// This is to add a new line after each entry
				bw.write(System.getProperty("line.separator"));
			}
			bw.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}

	}

	// following method inserts one row only.
	// htblColNameValue must include a value for the primary key
	// https://piazza.com/class/lsbl61kzegk3qo/post/99

	public void insertIntoTable(String strTableName,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {

		// Check datatypes from csv and if clusteringKey is repeated
		// Returns {strClusteringKey, strClusteringColIndexName}
		String[] clusteringData = checkDataForInsert(strTableName, htblColNameValue);

		Table t = deserializeTable(strTableName);
		Vector<String> pages = t.getStrPages();

		// Base case: no pages yet
		if (pages.size() == 0) {
			Page p = new Page(strTableName, 1);
			p.insertBinary(htblColNameValue, clusteringData[0]);
			serializePage(p);
			t.addNewPage();
			// in case user creates indices before any tuples have been inserted
			Hashtable<String, String> indices = loadAllIndices(strTableName);
			for (String i : indices.keySet()) {
				// System.out.println("add in index for first insert only");
				bplustree b = deserializeIndex(i);
				b.insert(htblColNameValue.get(indices.get(i)), "1");
				serializeIndex(b, i);
			}
		}

		else {
			// Find the page
			String targetPage = "0";
			// First case: no index on clusteringKey
			if (clusteringData[1].equals("null")) {
				targetPage = binarySearchWithoutIndexForInsertion(strTableName, clusteringData[0],
						htblColNameValue.get(clusteringData[0])) + "";
				// System.out.println("Page number from binary search:" + targetPage);

			} else { // Second case: index exists
				bplustree b = deserializeIndex(clusteringData[1]);
				Object insert = htblColNameValue.get(clusteringData[0]);
				LeafNode n = b.findLeafNodeShouldContainKey(insert);
				DictionaryPair[] dp = n.getDictionary();
				// binary search on leaf node keys to find correct "in between" values
				int i = 0;
				int j = n.getNumPairs() - 1;
				int lastElement = j;
				// note: can't have duplicates for values in dp
				while (j >= i) {
					int mid = i + (j - i) / 2;
					if (dp[mid].compare(insert) < 0 && mid != lastElement && dp[mid + 1].compare(insert) > 0) {
						targetPage = dp[mid].getValues().get(0);
						break;
					}
					if (mid == 0 && dp[mid].compare(insert) > 0) {
						targetPage = dp[mid].getValues().get(0);
						break;
					}
					if (mid == lastElement && dp[mid].compare(insert) < 0) {
						targetPage = dp[mid].getValues().get(0);
						break;
					}
					if (dp[mid].compare(insert) > 0) {
						j = mid - 1;
					} else {
						i = mid + 1;
					}
				}
				// System.out.println("Index targetPage: " + targetPage);
			}

			// page logic here
			Page p = deserializePage(strTableName + "_" + targetPage);
			// Insert the new tuple in the current page which will displace the last tuple
			Tuple displacedTuple = p.insertAndDisplaceLast(htblColNameValue, clusteringData[0]);
			// Prepare a list to track displaced elements and their page movements
			Hashtable<Tuple, String> displacedElements = new Hashtable<>();
			String displacedTuplePageNum = p.getPageNum();
			String newTuplePage = displacedTuplePageNum;
			serializePage(p);

			// Propagate the displaced tuple to the next pages
			while (displacedTuple != null) {
				// Calculate the next page number
				int nextPageNum = t.getNextPageNum(Integer.parseInt(displacedTuplePageNum));
				Page nextPage;
				// Check if the next page exists, if not create it
				if (t.checkPageExists(strTableName + "_" + nextPageNum)) {
					nextPage = deserializePage(strTableName + "_" + nextPageNum);
				} else {
					nextPage = new Page(strTableName, nextPageNum);
					t.addNewPage(); // Assuming 't' is your Table object
				}

				// Insert the displaced tuple in the next page, which may displace another tuple
				Tuple nextDisplacedTuple = nextPage.insertAndDisplaceLast(displacedTuple.getHtblTuple(),
						clusteringData[0]);

				// Track the movement of the displaced tuple
				displacedElements.put(displacedTuple, displacedTuplePageNum);

				// Prepare for the next iteration if there was a displacement
				displacedTuple = nextDisplacedTuple;
				displacedTuplePageNum = String.valueOf(nextPageNum);
				// System.out.println("nextPage to be serialized: " + nextPage.getPageName());
				// Serialize the updated page
				serializePage(nextPage);

				// At this point, displacedElements contains all the movements of displaced
				// tuples

			}
			// update any indices <indexName, colName> for the table after successful
			// insertion
			// System.out.println("displaced tuples:" + displacedElements.size());
			Hashtable<String, String> v = loadAllIndices(strTableName);
			for (String i : v.keySet()) {
				String colName = v.get(i);
				bplustree b = deserializeIndex(i);
				// new tuple
				b.insert(htblColNameValue.get(colName), newTuplePage);
				for (Tuple row : displacedElements.keySet()) {
					// change page numbers for displaced tuples
					// System.out.println("displaced tuple: " + row);
					Hashtable<String, Object> h = row.getHtblTuple();
					b.delete(h.get(colName), displacedElements.get(row));
					int newPage = t.getNextPageNum(Integer.parseInt(displacedElements.get(row)));
					b.insert(h.get(colName), newPage + "");
				}
				serializeIndex(b, i);
			}

		}

		serializeTable(t);
		t = null;

	}

	// following method updates one row only
	// htblColNameValue holds the key and new value
	// htblColNameValue will not include clustering key as column name
	// strClusteringKeyValue is the value to look for to find the row to update.
	public void updateTable(String strTableName,
			String strClusteringKeyValue,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {
		// get clustering key and its datatype from csv
		// get all indices as they might get updated
		String clusteringKey = "";
		String datatype = "";
		String line = "";
		String index = "";
		Hashtable<String, String> otherIndices = new Hashtable<>(); // to be updated->(columnName,indexName)
		try {
			BufferedReader br = new BufferedReader(new FileReader(METADATA_PATH));
			boolean foundTable = false;
			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");

				if (!s[0].equals(strTableName) && foundTable)
					break;
				// first row with clusteringColumn info
				if (s[0].equals(strTableName) && !foundTable) {
					clusteringKey = s[1];
					datatype = s[2];
					index = s[4];
					foundTable = true;
				} else if (s[0].equals(strTableName) && htblColNameValue.containsKey(s[1]) && !s[4].equals("null")) {
					otherIndices.put(s[1], s[4]);
				}

			}
			br.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}

		if (htblColNameValue.containsKey(clusteringKey))
			throw new DBAppException("UPDATE TABLE: Clustering key's value can't be updated");

		dataChecker(strTableName, htblColNameValue);

		// check if clusteringKeyValue can be parsed to its correct datatype if is
		// double or int
		Object clusteringKeyValue = null;
		switch (datatype) {
			case "java.lang.integer":
				try {
					clusteringKeyValue = Integer.parseInt(strClusteringKeyValue);
				} catch (NumberFormatException e) {
					throw new DBAppException("UPDATE TABLE: strClusterKeyValue can't be parsed to Integer");
				}
				break;
			case "java.lang.double":
				try {
					clusteringKeyValue = Double.parseDouble(strClusteringKeyValue);
				} catch (NumberFormatException e) {
					throw new DBAppException("UPDATE TABLE: strClusterKeyValue can't be parsed to Double");
				}
				break;
		}
		if (index.equals("null")) {
			// page
			Page p = binarySearchWithoutIndex(strTableName, clusteringKey, clusteringKeyValue);
			if (p == (null)) {
				return;
			} else {

				Vector<Tuple> records = p.getRecords();
				int i = p.binarySearch(clusteringKey, clusteringKeyValue); // rowNumber
				if (i < 0) {
					return;
				}
				Tuple row = records.get(i);
				Hashtable<String, Object> hashtable = row.getHtblTuple();
				// colName,indexName
				for (String colName : otherIndices.keySet()) {
					bplustree tree = deserializeIndex(otherIndices.get(colName));
					tree.delete(hashtable.get(colName), p.getPageNum());
					tree.insert(htblColNameValue.get(colName), p.getPageNum());
					serializeIndex(tree, otherIndices.get(colName));
				}
				row.updateValues(htblColNameValue);
				p.setRecords(records);
				serializePage(p);
				// for garbage collection
				p = null;
			}
			// call to garbage collector
			System.gc();

		} // have clusteringKey column index
		else

		{
			bplustree b = deserializeIndex(index);

			Vector<String> v = b.search(clusteringKeyValue);
			if (v == null) {
				return;
			} else {
				// already know it'll be one value
				// System.out.println("UPDATE USING INDEX");
				String pageNum = v.get(0);
				Page p = deserializePage(strTableName + "_" + pageNum);
				int i = p.binarySearch(clusteringKey, clusteringKeyValue); // rowNumber
				Vector<Tuple> records = p.getRecords();
				Tuple row = records.get(i);
				Hashtable<String, Object> hashtable = row.getHtblTuple();
				// colName,indexName
				for (String colName : otherIndices.keySet()) {
					bplustree tree = deserializeIndex(otherIndices.get(colName));
					tree.delete(hashtable.get(colName), pageNum);
					tree.insert(htblColNameValue.get(colName), pageNum);
					serializeIndex(tree, otherIndices.get(colName));
				}
				row.updateValues(htblColNameValue);
				p.setRecords(records);
				serializePage(p);
				// update value in indices

				// for garbage collection
				p = null;
			}
			b = null;
			System.gc();
		}

	}

	// following method could be used to delete one or more rows.
	// htblColNameValue holds the key and value. This will be used in search
	// to identify which rows/tuples to delete.
	// htblColNameValue enteries are ANDED together
	public void deleteFromTable(String strTableName,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {

		String clusteringKey = dataChecker(strTableName, htblColNameValue);
		// key: ColName, value: IndexName
		Hashtable<String, String> indexedColumns = loadAllColumnsHavingIndex(strTableName);

		// track deleted tuples to delete from indices in the end if applicable
		Hashtable<Tuple, String> deletedTuples = new Hashtable<>();

		// case # Special : if hashtable is empty empty all records from table
		if (htblColNameValue.size() == 0) {
			Table t = deserializeTable(strTableName);
			Vector<String> strpages = t.getStrPages();
			for (String strpage : strpages) {
				Page p = deserializePage(strpage);
				Vector<Tuple> tuples = p.getRecords();
				for (Tuple tupleValue : tuples) {
					deletedTuples.put(tupleValue, p.getPageNum());
				}
				Path filePath = Paths.get(PAGES_DIR + p.getPageName() + ".ser");
				try {
					Files.delete(filePath);
				} catch (IOException e) {
					throw new DBAppException(e.getMessage());
				}

			}
			t.setStrPages(new Vector<>());
			serializeTable(t);
			// get all indices and create ones again
			// delete method in b plus tree results in many nulls with the
			// structure not maintained when too few nodes are left
			for (String index : loadAllIndices(strTableName).keySet()) {
				bplustree b = deserializeIndex(index);
				serializeIndex(new bplustree(b.getM()), index);
			}
			return;
		}

		// case 1: if the hashtable contains a clustering key to delete and it has an
		// index
		else if (htblColNameValue.get(clusteringKey) != null && indexedColumns.get(clusteringKey) != null) {
			Hashtable<String, Object> htblvalue = null;
			bplustree b = deserializeIndex(indexedColumns.get(clusteringKey));
			Vector<String> v = b.search(htblColNameValue.get(clusteringKey));
			// value not found in a page
			if (v == null) {
				return;
			}
			// already know it'll be one value
			String pageNum = v.get(0);
			Page p = deserializePage(strTableName + "_" + pageNum);
			int i = p.binarySearch(clusteringKey, htblColNameValue.get(clusteringKey)); // rowNumber

			// check if it is a valid tuple (all columns are same in hashtable)
			Tuple tupleValue = p.getRecords().get(i);
			htblvalue = tupleValue.getHtblTuple();
			for (String key : htblColNameValue.keySet()) {
				if (!htblColNameValue.get(key).equals(htblvalue.get(key))) {
					return;
				}

			}
			// deletion logic here
			// more than one entry in page so at least one remains
			if (p.getNumOfEntries() > 1) {
				p.remove(i);
				serializePage(p);
			} else {
				// only this element in the page so we will delete the page
				Path filePath = Paths.get(PAGES_DIR + p.getPageName() + ".ser");
				try {
					Files.delete(filePath);
				} catch (IOException e) {
					throw new DBAppException(e.getMessage());
				}
				Table t = deserializeTable(strTableName);
				// t.getStrPages().remove(Integer.parseInt(p.getPageNum()) - 1);
				// loop on the strpages and check the last number of the string for exmaple the
				// 1 in Student_1 is equal to the page num
				// then delete that pagename after the loop
				Vector<String> strpages = t.getStrPages();
				int indexToBeDeleted = Collections.binarySearch(strpages, p.getPageName());

				if (indexToBeDeleted > -1) {
					t.getStrPages().remove(indexToBeDeleted);
				}
				serializeTable(t);
			}

			deletedTuples.put(tupleValue, p.getPageNum());

		}
		// case 2: see if existing indices can limit the search or are they of no use
		// like no index case
		else {
			Hashtable<String, String> usefulIndices = new Hashtable<>();
			for (String key : indexedColumns.keySet()) {
				if (htblColNameValue.containsKey(key)) {
					usefulIndices.put(key, indexedColumns.get(key));
				}
			}
			// subcase 1: no useful indices same as no index case
			if (usefulIndices.size() == 0) {
				// case 1.1: clustering key is in hashtable so use binary search
				if (htblColNameValue.containsKey(clusteringKey)) {
					Page p = binarySearchWithoutIndex(strTableName, clusteringKey,
							htblColNameValue.get(clusteringKey));
					if (p == null)
						return;
					int i = p.binarySearch(clusteringKey, htblColNameValue.get(clusteringKey));
					if (i < 0)
						return;
					Tuple row = p.getRecords().get(i);
					Hashtable<String, Object> htblvalue = row.getHtblTuple();
					// validate row
					for (String key : htblColNameValue.keySet()) {
						if (!htblColNameValue.get(key).equals(htblvalue.get(key))) {
							return;
						}
					}
					// deletion logic here
					// more than one entry in page so at least one remains
					if (p.getNumOfEntries() > 1) {
						p.remove(i);
						serializePage(p);
					}

					else {
						// only this element in the page so we will delete the page
						Path filePath = Paths.get(PAGES_DIR + p.getPageName() + ".ser");
						try {
							Files.delete(filePath);
						} catch (IOException e) {
							throw new DBAppException(e.getMessage());
						}
						Table t = deserializeTable(strTableName);
						// loop on the strpages and check the last number of the string for exmaple the
						// 1 in Student_1 is equal to the page num
						// then delete that pagename after the loop
						Vector<String> strpages = t.getStrPages();
						int indexToBeDeleted = Collections.binarySearch(strpages, p.getPageName());
						if (indexToBeDeleted > -1) {
							t.getStrPages().remove(indexToBeDeleted);
						}
						serializeTable(t);
					}
					deletedTuples.put(row, p.getPageNum());

				}
				// case 1.2: clustering key is not in hashtable
				// can have one or more values so search linearly
				else {
					Table t = deserializeTable(strTableName);
					for (String pageName : t.getStrPages()) {
						Page p = deserializePage(pageName);
						Vector<Tuple> records = p.getRecords();
						for (int i = 0; i < records.size(); i++) {
							Hashtable<String, Object> htblvalue = records.get(i).getHtblTuple();
							boolean matching = true;
							for (String key : htblColNameValue.keySet()) {
								if (!htblColNameValue.get(key).equals(htblvalue.get(key))) {
									matching = false;
									break;
								}
							}
							if (matching) {
								deletedTuples.put(records.get(i), p.getPageNum());
								// deletion logic here
								// more than one entry in page so at least one remains
								if (p.getNumOfEntries() > 1) {
									p.remove(i);
								}

								else {
									// only this element in the page so we will delete the page
									Path filePath = Paths.get(PAGES_DIR + p.getPageName() + ".ser");
									try {
										Files.delete(filePath);
									} catch (IOException e) {
										throw new DBAppException(e.getMessage());
									}
									// loop on the strpages and check the last number of the string for exmaple the
									// 1 in Student_1 is equal to the page num
									// then delete that pagename after the loop
									Vector<String> strpages = t.getStrPages();
									int indexToBeDeleted = Collections.binarySearch(strpages, p.getPageName());
									if (indexToBeDeleted > -1) {
										t.getStrPages().remove(indexToBeDeleted);
									}
								}
							}
						}
						serializePage(p);
					}
					serializeTable(t);

				}
			} else {
				// subcase 2: one or more useful indices
				HashSet<String> pageNum = new HashSet<>();
				boolean first = true;
				for (String key : usefulIndices.keySet()) {
					bplustree tempIndex = deserializeIndex(usefulIndices.get(key));
					Object value = htblColNameValue.get(key);

					Vector<String> tempPages = tempIndex.search(value);
					// terms are ANDED so if one doesn't exist, no matching tuple exists
					if (tempPages == null) {
						return;
					}
					if (first) {
						pageNum.addAll(tempPages);
						first = false;
					} else {
						pageNum.retainAll(tempPages);
					}

				}
				// check if the pageNum is not empty else return
				if (pageNum.isEmpty()) {
					return;
				}
				// loop through pages

				// case 2.1: binary search if clusteringKey in hashtable
				if (htblColNameValue.get(clusteringKey) != null) {
					for (String pageno : pageNum) {

						// get pagename from page no
						Page p = deserializePage(strTableName + "_" + pageno);
						if (p == null)
							return;
						int i = p.binarySearch(clusteringKey, htblColNameValue.get(clusteringKey));
						// if not found skip to the next page
						if (i < 0) {
							continue;
						}
						Tuple row = p.getRecords().get(i);
						Hashtable<String, Object> htblvalue = row.getHtblTuple();
						// validate row
						boolean match = true;
						for (String key : htblColNameValue.keySet()) {
							if (!htblColNameValue.get(key).equals(htblvalue.get(key))) {
								match = false;
								break;
							}
						}
						if (match) {
							// deletion logic here
							// more than one entry in page so at least one remains
							if (p.getNumOfEntries() > 1) {
								p.remove(i);
								serializePage(p);
							}

							else {
								// only this element in the page so we will delete the page
								Path filePath = Paths.get(PAGES_DIR + p.getPageName() + ".ser");
								try {
									Files.delete(filePath);
								} catch (IOException e) {
									throw new DBAppException(e.getMessage());
								}
								Table t = deserializeTable(strTableName);
								// loop on the strpages and check the last number of the string for exmaple the
								// 1 in Student_1 is equal to the page num
								// then delete that pagename after the loop
								Vector<String> strpages = t.getStrPages();
								int indexToBeDeleted = Collections.binarySearch(strpages, p.getPageName());
								if (indexToBeDeleted > -1) {
									t.getStrPages().remove(indexToBeDeleted);
								}
								serializeTable(t);
							}
							deletedTuples.put(row, p.getPageNum());
							// if found the record so i broke from the loop
							break;
						}
					}
				} else {
					// case 2.2
					// Note: indices may output more than one page for that case since they are not
					// on the clustering column
					// linear search if no clusteringKey in hashtable
					Table t = deserializeTable(strTableName);
					for (String num : pageNum) {
						Page p = deserializePage(strTableName + "_" + num);
						Vector<Tuple> records = p.getRecords();
						for (int i = 0; i < records.size(); i++) {
							Hashtable<String, Object> htblvalue = records.get(i).getHtblTuple();
							boolean matching = true;
							for (String key : htblColNameValue.keySet()) {
								if (!htblColNameValue.get(key).equals(htblvalue.get(key))) {
									matching = false;
									break;
								}
							}
							if (matching) {
								deletedTuples.put(records.get(i), p.getPageNum());
								// deletion logic here
								// more than one entry in page so at least one remains
								if (p.getNumOfEntries() > 1) {
									p.remove(i);

								}

								else {
									// only this element in the page so we will delete the page
									Path filePath = Paths.get(PAGES_DIR + p.getPageName() + ".ser");
									try {
										Files.delete(filePath);
									} catch (IOException e) {
										throw new DBAppException(e.getMessage());
									}

									// loop on the strpages and check the last number of the string for exmaple the
									// 1 in Student_1 is equal to the page num
									// then delete that pagename after the loop
									Vector<String> strpages = t.getStrPages();
									int indexToBeDeleted = Collections.binarySearch(strpages, p.getPageName());
									if (indexToBeDeleted > -1) {
										t.getStrPages().remove(indexToBeDeleted);
									}
								}
							}
						}
						serializePage(p);

					}
					serializeTable(t);

				}

			}

		}
		// handle index deletions here
		for (String col : indexedColumns.keySet()) {
			bplustree b = deserializeIndex(indexedColumns.get(col));
			// loop through all deleted tuple
			for (Tuple row : deletedTuples.keySet()) {
				b.delete(row.getHtblTuple().get(col), deletedTuples.get(row));
			}
			serializeIndex(b, indexedColumns.get(col));
		}
	}

	// _strOperator can be >, >=, <, <=, != or = (6 operators)
	// AND, OR, or XOR
	// Number of operators = number of terms - 1
	// Remember base case: one select with no operators
	public Iterator selectFromTable(SQLTerm[] arrSQLTerms,
			String[] strarrOperators) throws DBAppException {
		// check forarrsql and stroperators for null
		if (arrSQLTerms == null && strarrOperators == null) {
			throw new DBAppException("Invalid input: both inputs are null");
		}
		if (arrSQLTerms.length == 1 && arrSQLTerms[0]._strTableName != null
				&& arrSQLTerms[0]._objValue == null && arrSQLTerms[0]._strColumnName == null
				&& arrSQLTerms[0]._strOperator == null && strarrOperators == null) {
			if (checkTableExists(arrSQLTerms[0]._strTableName)) {
				Vector<Tuple> finalresult = new Vector<>();
				Table t = deserializeTable(arrSQLTerms[0]._strTableName);
				for (String pname : t.getStrPages()) {
					Page p = deserializePage(pname);
					for (Tuple row : p.getRecords()) {
						finalresult.add(row);
					}

				}
				return finalresult.iterator();

			} else {
				throw new DBAppException("Table doesn't exists");
			}

		}
		if (arrSQLTerms == null || strarrOperators == null) {
			throw new DBAppException("Invalid input: one of the inputs (or both) is null");
		}
		// Check for valid input
		if (arrSQLTerms.length == 0 || strarrOperators.length != arrSQLTerms.length - 1) {
			throw new DBAppException("Invalid input: Term and Operator arrays mismatch");
		}
		checkDataForSelect(arrSQLTerms, strarrOperators);
		Hashtable<String, String> columns = loadAllColumnsHavingIndex(arrSQLTerms[0]._strTableName);
		Vector<Object> operations = queryOperationsGenerator(arrSQLTerms, strarrOperators, columns);
		// passed by reference to method
		Vector<Object> operationsCopy = new Vector<>();
		operationsCopy.addAll(operations);
		boolean useIndex = queryOptimizer(operationsCopy);
		// keep only sql terms true and false in operations vector to access it with
		// same index
		operations.removeIf(element -> element instanceof String);
		// System.out.println("operations" + operations);
		// System.out.println("useIndex:" + useIndex);
		// as not to contain duplicates
		HashSet<String> pages = new HashSet<>();
		if (useIndex) {
			for (int i = 0; i < arrSQLTerms.length; i++) {
				// only case that can be false when it's an AND with possibly multiple ones
				// after
				// each other
				if ((boolean) operations.get(i)) {
					bplustree b = deserializeIndex(columns.get(arrSQLTerms[i]._strColumnName));
					HashSet<String> intermediateRes = executeindexedSQlterm(b, arrSQLTerms[i]);
					// first term
					if (i == 0) {
						pages.addAll(intermediateRes);
					} else { // know operation and do it on pages
						switch (strarrOperators[i - 1]) {
							case "AND":
								// System.out.println(i + ": entered AND");
								// System.out.println("intermediate " + intermediateRes);
								pages.retainAll(intermediateRes);
								break;
							default: // OR or XOR
								// System.out.println(i + ": entered OR/XOR");
								// System.out.println("intermediate " + intermediateRes);
								pages.addAll(intermediateRes);
						}

					}
				} else {
					int oldValue = i;
					// handle terms that won't make use of index or don't have indices
					do {
						i++;
					} while (i < arrSQLTerms.length - 1 && !(boolean) operations.get(i));

					// special case of first term
					if (oldValue == 0 && (boolean) operations.get(i)) {
						bplustree b = deserializeIndex(columns.get(arrSQLTerms[i]._strColumnName));
						HashSet<String> intermediateRes = executeindexedSQlterm(b, arrSQLTerms[i]);
						pages.addAll(intermediateRes);
					} else { // enter loop normally
						i--;
					}
				}
				// System.out.println(i + ": " + pages);

			}
			// System.out.println("final:" + pages);
			// add table name to pages as to have the full path
			pages = pages.stream().map(s -> arrSQLTerms[0]._strTableName + "_" + s)
					.collect(Collectors.toCollection(HashSet::new));
		} else {
			// direct tuple manipulation in all pages
			Table t = deserializeTable(arrSQLTerms[0]._strTableName);
			pages.addAll(t.getStrPages());
			t = null;
		}
		return searchRecordswithinSelectedPages(pages, arrSQLTerms, strarrOperators).iterator();
	}

	//////////////////////////////////// EXTRAS
	//////////////////////////////////// /////////////////////////////////////////////////

	// TABLE CLASS
	//////////////
	public void serializeTable(Table t) throws DBAppException {
		try {
			FileOutputStream file = new FileOutputStream(TABLES_DIR + t.getStrTableName() + ".ser");
			ObjectOutputStream write = new ObjectOutputStream(file);
			write.writeObject(t);
			write.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}

	}

	public Table deserializeTable(String tName) throws DBAppException {
		try {
			FileInputStream input = new FileInputStream(TABLES_DIR + tName + ".ser");
			ObjectInputStream read = new ObjectInputStream(input);
			Table t = (Table) read.readObject();
			read.close();
			return t;
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new DBAppException(e.getMessage());
		}

	}

	// PAGE CLASS
	/////////////
	public void serializePage(Page p) throws DBAppException {
		try {
			FileOutputStream file = new FileOutputStream(PAGES_DIR + p.getPageName() + ".ser");
			ObjectOutputStream write = new ObjectOutputStream(file);
			write.writeObject(p);
			write.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}
	}

	public Page deserializePage(String pName) throws DBAppException {
		try {
			FileInputStream input = new FileInputStream(PAGES_DIR + pName + ".ser");
			ObjectInputStream read = new ObjectInputStream(input);
			Page p = (Page) read.readObject();
			read.close();
			return p;
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new DBAppException(e.getMessage());
		}
	}

	// INDEX
	public void serializeIndex(bplustree bt, String indexName) throws DBAppException {
		// Saving the b+tree to the hard disk using serialization
		try {
			FileOutputStream savedTree = new FileOutputStream(INDICES_DIR + indexName + ".ser");
			ObjectOutputStream savedTreeOutput = new ObjectOutputStream(savedTree);
			savedTreeOutput.writeObject(bt);
			savedTreeOutput.close();
			savedTree.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public bplustree deserializeIndex(String strindexName) throws DBAppException {
		try {
			FileInputStream input = new FileInputStream(INDICES_DIR + strindexName + ".ser");
			ObjectInputStream read = new ObjectInputStream(input);
			bplustree bp = (bplustree) read.readObject();
			read.close();
			return bp;
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new DBAppException(e.getMessage());
		}
	}

	// GENERAL HELPER METHODS
	///////////////////////

	public boolean checkTableExists(String strTableName) throws DBAppException {
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(METADATA_PATH));

			while ((line = br.readLine()) != null) {
				String[] s = line.split(",");
				if (strTableName.equals(s[0])) {
					br.close();
					return true;
				}
			}
			br.close();
		} catch (Exception e) {
			throw new DBAppException(e.getMessage());
		}
		return false;

	}

	// returns the index for clusteringKey (its name or null)
	public String[] checkDataForInsert(String strTableName, Hashtable<String, Object> htblColNameValue)
			throws DBAppException {
		BufferedReader br = null;
		String line = "";
		boolean foundTable = false; // use as not to continue looping through all of the file if the needed
									// datatypes
									// have been all checked
		int countColumns = 0; // to make sure that the hashtable contains exactly the number of fields needed
								// for the columns
		String clusteringKey = ""; // get key from csv
		String clusteringKeyIndex = ""; // get index from csv

		try {
			br = new BufferedReader(new FileReader(METADATA_PATH));

			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");

				if (foundTable && !strTableName.equals(s[0])) {
					break;
				}

				// first row for table in csv: find clusteringKey and its index
				if (!foundTable && strTableName.equals(s[0])) {
					clusteringKey = s[1];
					clusteringKeyIndex = s[4];
				}
				// check type
				if (strTableName.equals(s[0])) {
					foundTable = true; // stopping mechanism
					countColumns++;
					// check missing keys
					if (!htblColNameValue.containsKey(s[1])) {
						br.close();
						throw new DBAppException("CHECK DATATYPES: Missing column entry for " + s[1]);
					}
					// check if not matching datatypes
					if (!(htblColNameValue.get(s[1]).getClass().getTypeName().toLowerCase()).equals(s[2])) {
						br.close();
						throw new DBAppException("CHECK DATATYPES: Invalid datatype for " + s[1] + ". You have entered "
								+ htblColNameValue.get(s[1]).getClass().getTypeName());
					}
				}

			}
			br.close();
			if (!foundTable) {
				throw new DBAppException("CHECK DATA: Table doesn't exist");
			}
			if (htblColNameValue.size() != countColumns)
				throw new DBAppException(
						"CHECK DATATYPES: You entered more column fields that don't exist in the table");
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}
		if (!clusteringKeyIndex.equals("null")) {
			bplustree b = deserializeIndex(clusteringKeyIndex);
			Vector<String> v = b.search(htblColNameValue.get(clusteringKey));
			if (v != null) {
				throw new DBAppException(
						"CHECK DATA: Repeated clustering key value " + htblColNameValue.get(clusteringKey));
			}
		} else {
			Page p = binarySearchWithoutIndex(strTableName, clusteringKey, htblColNameValue.get(clusteringKey));
			if (p != null && p.binarySearch(clusteringKey, htblColNameValue.get(clusteringKey)) >= 0)
				throw new DBAppException(
						"CHECK DATA: Repeated clustering key value " + htblColNameValue.get(clusteringKey));
		}
		return new String[] { clusteringKey, clusteringKeyIndex };
	}

	// used in update and delete
	// returns clusteringKey
	public String dataChecker(String strTableName, Hashtable<String, Object> htblColNameValue)
			throws DBAppException {
		BufferedReader br = null;
		String line = "";
		boolean foundTable = false; // use as not to continue looping through all of the file if the needed
									// datatypes
									// have been all checked
		int countColumns = 0; // to make sure that the hashtable contains exactly the number of fields
								// that have been checked
		String clusteringKey = "";
		try {
			br = new BufferedReader(new FileReader(METADATA_PATH));

			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");

				// first row with clusteringColumn info
				if (s[0].equals(strTableName) && !foundTable) {
					clusteringKey = s[1];
				}

				if (foundTable && !strTableName.equals(s[0])) {
					break;
				}

				// check type
				if (strTableName.equals(s[0])) {
					foundTable = true; // stopping mechanism

					// check if not matching datatypes for present fields
					if (htblColNameValue.containsKey(s[1])) {

						if (!(htblColNameValue.get(s[1]).getClass().getTypeName().toLowerCase()).equals(s[2])) {
							br.close();
							throw new DBAppException(
									"CHECK DATA: Invalid datatype for " + s[1] + ". You have entered "
											+ htblColNameValue.get(s[1]).getClass().getTypeName());
						} else {
							countColumns++;
						}
					}
				}

			}
			br.close();
			if (!foundTable) {
				throw new DBAppException("CHECK DATA: Table doesn't exist");
			}
			if (htblColNameValue.size() != countColumns)
				throw new DBAppException(
						"CHECK DATA: You entered more column fields that don't exist in the table");
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}
		return clusteringKey;

	}

	////////////////////////////////////////// SELECT HELPERS
	////////////////////////////////////////// //////////////////////////////////////////
	public void checkDataForSelect(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException {
		String strTableName = arrSQLTerms[0]._strTableName;
		BufferedReader br = null;
		String line = "";
		boolean foundTable = false; // use as not to continue looping through all of the file if the needed
									// datatypes
									// have been all checked
		Hashtable<String, String> columnData = new Hashtable<>(); // store key:column name, value: data type
		try {
			br = new BufferedReader(new FileReader(METADATA_PATH));

			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");

				if (foundTable && !strTableName.equals(s[0])) {
					break;
				}

				// check type
				if (strTableName.equals(s[0])) {
					foundTable = true; // stopping mechanism
					columnData.put(s[1], s[2]);

				}

			}
			br.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}
		if (!foundTable) {
			throw new DBAppException("CHECK DATA: Table doesn't exist");
		}
		for (SQLTerm term : arrSQLTerms) {
			if (term == null) {
				throw new DBAppException("CHECK DATA: SQL Term is equal to null");
			}
			if (term.isNull()) {
				throw new DBAppException("CHECK DATA: Null exception for SQL Term provided");
			}
			if (!term._strTableName.equals(strTableName)) {
				throw new DBAppException(
						"CHECK DATA: Can't query more than one table at a time. No joins are supported.");
			}
			if (!columnData.containsKey(term._strColumnName)) {
				throw new DBAppException("CHECK DATA: Column " + term._strColumnName + " doesn't exist in the table");
			}
			if (!term._objValue.getClass().getTypeName().toLowerCase().equals(columnData.get(term._strColumnName))) {
				throw new DBAppException("CHECK DATA: You have entered an in compatible "
						+ term._objValue.getClass().getTypeName().toLowerCase() + " for " + term._strColumnName);
			}
			if (!(term._strOperator.equals("!=") || term._strOperator.equals("=") || term._strOperator.equals(">")
					|| term._strOperator.equals(">=") || term._strOperator.equals("<")
					|| term._strOperator.equals("<="))) {
				throw new DBAppException(
						"CHECK DATA: Invalid operator " + term._strOperator + " for SQL Term provided");
			}
		}
		for (String operator : strarrOperators) {
			if (!(operator.equals("AND") || operator.equals("OR") || operator.equals("XOR"))) {
				throw new DBAppException("CHECK DATA: Invalid operator " + operator + " for SQL Term provided");
			}
		}

	}

	public Vector<Object> queryOperationsGenerator(SQLTerm[] arrSQLTerms, String[] strarrOperators,
			Hashtable<String, String> columnsHavingIndex) {
		// check expression with indices and operators
		int i = 0;
		Vector<Object> operations = new Vector<>();
		for (SQLTerm term : arrSQLTerms) {
			// if != operator index is of no use
			if (columnsHavingIndex.containsKey(term._strColumnName) && !term._strOperator.equals("!=")) {
				operations.add(true);
			} else {
				operations.add(false);
			}
			if (i < strarrOperators.length)
				operations.add(strarrOperators[i++]);
		}
		// System.out.println("equation: " + operations);
		return operations;
	}

	/*
	 * logic behind it is avoid inefficeint use of indices if all pages will need
	 * to
	 * be opened
	 * for a non-indexed column with a certain operator
	 */
	public boolean queryOptimizer(Vector<Object> operations) {
		// all have indices
		if (!operations.contains(false)) {
			return true;
		}
		// process sequentially all operators
		int j = 1;
		while (j < operations.size() - 1) {
			// AND case: if any have index open these pages from bPlusTree
			if (operations.get(j).equals("AND")) {
				Boolean resultAND = (Boolean) operations.get(j - 1) || (Boolean) operations.get(j + 1);
				operations.remove(j - 1);
				operations.remove(j);
				operations.set(j - 1, resultAND);
				// OR or XOR case: both must have indices for an index to be used
				// otherwise, must loop over all tuples
			} else {
				Boolean result = (Boolean) operations.get(j - 1) && (Boolean) operations.get(j + 1);
				operations.remove(j - 1);
				operations.remove(j);
				operations.set(j - 1, result);
			}
		}
		// System.out.println("After processing");
		// System.out.println(operations);
		// XOR and OR operators will require opening all pages if any don't have an
		// index
		return (Boolean) operations.get(0);

	}

	// when no index is used, clusteringKey can limit the search if all terms are
	// ANDED or query doesn't have other columns except it
	// != operator excludes one tuple so is of no use
	// pass result to queryOptimizer
	public Vector<Object> keepTrackofClusteringKeyValue(SQLTerm[] arrSQLTerms, String[] strarrOperators,
			String strClusteringKey) {
		int i = 0;
		Vector<Object> operations = new Vector<>();
		for (SQLTerm term : arrSQLTerms) {
			if (term._strColumnName.equals(strClusteringKey) && !term._strOperator.equals("!=")) {
				operations.add(true);
			} else {
				operations.add(false);
			}
			if (i < strarrOperators.length)
				operations.add(strarrOperators[i++]);
		}
		// System.out.println("equation: " + operations);
		return operations;
	}

	public HashSet<String> executeindexedSQlterm(bplustree indextree, SQLTerm sql) {
		String operator = sql._strOperator;

		if (operator.equals(">")) {
			return indextree.rangeSearchWithLowerBoundExclusive(sql._objValue);
		} else if (operator.equals(">=")) {
			return indextree.rangeSearchWithLowerBoundInclusive(sql._objValue);
		} else if (operator.equals("<")) {
			return indextree.rangeSearchWithUpperBoundExclusive(sql._objValue);
		} else if (operator.equals("<=")) {
			return indextree.rangeSearchWithUpperBoundInclusive(sql._objValue);
		} else if (operator.equals("=")) {
			Vector<String> v = indextree.search(sql._objValue);
			HashSet<String> h = new HashSet<>();
			if (v != null)
				h.addAll(v);
			return h;
		}
		return null;

	}

	// public Vector<Tuple> searchRecordswithinSelectedPages(HashSet<String>
	// pageNames, SQLTerm[] arrSQLTerms,
	// String[] strarrOperators) throws DBAppException {
	// Vector<Tuple> res = new Vector<>();
	// for (String page : pageNames) {
	// Page p = deserializePage(page);
	// for (Tuple tuple : p.getRecords()) {
	// // know if each tuple satisfies the sql terms
	// Vector<Boolean> flags = new Vector<>();
	// for (SQLTerm sqlTerm : arrSQLTerms) {
	// flags.add(tupleSatisfiesSQLTerm(tuple, sqlTerm));
	// }
	// // process sequentially all operators
	// for (int j = 0; j < strarrOperators.length; j++) {
	// if (strarrOperators[j].equals("AND")) {
	// Boolean resultAND = flags.get(0) && flags.get(1);
	// flags.remove(0);
	// flags.remove(0);
	// flags.add(0, resultAND);

	// } else if (strarrOperators[j].equals("OR")) {
	// Boolean resultOR = flags.get(0) || flags.get(1);
	// flags.remove(0);
	// flags.remove(0);
	// flags.add(0, resultOR);

	// } else {
	// Boolean resultXOR = flags.get(0) ^ flags.get(1);
	// flags.remove(0);
	// flags.remove(0);
	// flags.add(0, resultXOR);
	// }
	// }
	// if (flags.get(0)) {
	// res.add(tuple);
	// }
	// }
	// }
	// return res;
	// }
	public Vector<Tuple> searchRecordswithinSelectedPages(HashSet<String> pageNames, SQLTerm[] arrSQLTerms,
			String[] strarrOperators) throws DBAppException {

		// save result records in this vector
		Vector<Tuple> res = new Vector<>();
		// deserialize the table to get the strclustering key
		String strTableName = arrSQLTerms[0]._strTableName;
		Table t = deserializeTable(strTableName);
		String strClusteringKey = t.getStrClusteringKeyColumn();

		Vector<Object> v = keepTrackofClusteringKeyValue(arrSQLTerms, strarrOperators, strClusteringKey);
		// check whether the clustering key is included in the select query
		Boolean flag = queryOptimizer(v);
		if (flag) {
			// check whether the clustering key is included with operator equal (to binary
			// search across the pages)
			boolean checkEqualOperator = false;
			Object clusteringvalueForEqual = "";
			SQLTerm termforequal = null;
			SQLTerm termforRange = null;
			for (SQLTerm term : arrSQLTerms) {
				if (term._strColumnName.equals(strClusteringKey) && term._strOperator.equals("=")) {
					checkEqualOperator = true;
					termforequal = term;

				} else if (term._strColumnName.equals(strClusteringKey) && !term._strOperator.equals("!=")) {
					termforRange = term;
				}
			}

			if (checkEqualOperator) {
				clusteringvalueForEqual = termforequal._objValue;
				// System.out.println("rose`");
				// binary search to get the clustering record

				Page p = binarySearchWithoutIndex(strTableName, strClusteringKey, clusteringvalueForEqual);
				if (p == null) {
					return res;
				}
				int index = p.binarySearch(strClusteringKey, clusteringvalueForEqual);
				if (index < 0) {
					return res;
				}
				Tuple tuple = p.getRecords().get(index);

				// check for other sql terms
				Vector<Boolean> flags = new Vector<>();
				for (SQLTerm sqlTerm : arrSQLTerms) {
					flags.add(tupleSatisfiesSQLTerm(tuple, sqlTerm));
				}
				// process sequentially all operators
				for (int j = 0; j < strarrOperators.length; j++) {
					if (strarrOperators[j].equals("AND")) {
						Boolean resultAND = flags.get(0) && flags.get(1);
						flags.remove(0);
						flags.remove(0);
						flags.add(0, resultAND);

					} else if (strarrOperators[j].equals("OR")) {
						Boolean resultOR = flags.get(0) || flags.get(1);
						flags.remove(0);
						flags.remove(0);
						flags.add(0, resultOR);

					} else {
						Boolean resultXOR = flags.get(0) ^ flags.get(1);
						flags.remove(0);
						flags.remove(0);
						flags.add(0, resultXOR);
					}
				}
				if (flags.get(0)) {
					res.add(tuple);
				}

			} else {
				// check whether the record is in the page by checking the first and last value
				String operator = termforRange._strOperator;
				// System.out.println("Orange and Blue");
				for (String page : pageNames) {
					Page p = deserializePage(page);
					if (operator.equals(">") || operator.equals(">=")) {
						// if the last value doesnt satisfy the sql term with clustering key so skip for
						// the next page
						if (!tupleSatisfiesSQLTerm(p.getLastValue(), termforRange)) {
							continue;
						}
					} else if (operator.equals("<") || operator.equals("<=")) {
						// System.out.println("Yellow and Green");
						// if the first value doesnt satisfy the sql term with clustering key then next
						// pages wont satisfy so break
						if (!tupleSatisfiesSQLTerm(p.getFirstValue(), termforRange)) {
							// System.out.println("Yellow and Blue");
							break;
						}

					}
					// loop through the records to chech for satisfied tuples

					for (Tuple tuple : p.getRecords()) {
						// know if each tuple satisfies the sql terms
						Vector<Boolean> flags = new Vector<>();
						for (SQLTerm sqlTerm : arrSQLTerms) {
							flags.add(tupleSatisfiesSQLTerm(tuple, sqlTerm));
						}
						// process sequentially all operators
						for (int j = 0; j < strarrOperators.length; j++) {
							if (strarrOperators[j].equals("AND")) {
								Boolean resultAND = flags.get(0) && flags.get(1);
								flags.remove(0);
								flags.remove(0);
								flags.add(0, resultAND);

							} else if (strarrOperators[j].equals("OR")) {
								Boolean resultOR = flags.get(0) || flags.get(1);
								flags.remove(0);
								flags.remove(0);
								flags.add(0, resultOR);

							} else {
								Boolean resultXOR = flags.get(0) ^ flags.get(1);
								flags.remove(0);
								flags.remove(0);
								flags.add(0, resultXOR);
							}
						}
						if (flags.get(0)) {
							res.add(tuple);
						}
					}
				}

			}

		}

		else {
			// there are no sql terms with clustering key to help with select so loop
			// through all pages linearly

			for (String page : pageNames) {
				Page p = deserializePage(page);
				for (Tuple tuple : p.getRecords()) {
					// know if each tuple satisfies the sql terms
					Vector<Boolean> flags = new Vector<>();
					for (SQLTerm sqlTerm : arrSQLTerms) {
						flags.add(tupleSatisfiesSQLTerm(tuple, sqlTerm));
					}
					// process sequentially all operators
					for (int j = 0; j < strarrOperators.length; j++) {
						if (strarrOperators[j].equals("AND")) {
							Boolean resultAND = flags.get(0) && flags.get(1);
							flags.remove(0);
							flags.remove(0);
							flags.add(0, resultAND);

						} else if (strarrOperators[j].equals("OR")) {
							Boolean resultOR = flags.get(0) || flags.get(1);
							flags.remove(0);
							flags.remove(0);
							flags.add(0, resultOR);

						} else {
							Boolean resultXOR = flags.get(0) ^ flags.get(1);
							flags.remove(0);
							flags.remove(0);
							flags.add(0, resultXOR);
						}
					}
					if (flags.get(0)) {
						res.add(tuple);
					}
				}
			}
		}
		return res;

	}

	public Boolean tupleSatisfiesSQLTerm(Tuple tuple, SQLTerm sqlTerm) {
		String operator = sqlTerm._strOperator;
		Hashtable<String, Object> htblTuple = tuple.getHtblTuple();
		Object ob = htblTuple.get(sqlTerm._strColumnName);
		Object objValue = sqlTerm._objValue;
		int comparable;
		// already checked csv before
		if (ob instanceof Integer) {
			comparable = ((Integer) ob).compareTo((Integer) objValue);
		} else if (ob instanceof Double) {
			comparable = ((Double) ob).compareTo((Double) objValue);
		} else {
			comparable = ((String) ob).compareTo((String) objValue);
		}
		return (operator.equals(">") && comparable > 0) || (operator.equals(">=") && comparable >= 0)
				|| (operator.equals("<") && comparable < 0) || (operator.equals("<=") && comparable <= 0)
				|| (operator.equals("=") && comparable == 0) || (operator.equals("!=") && comparable != 0);

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// get Page
	public Page binarySearchWithoutIndex(String strTableName, String strClusteringKey, Object clusteringKeyValue)
			throws DBAppException {
		Table t = deserializeTable(strTableName);
		boolean foundPage = false; // findPage
		Page p = null;
		Vector<String> pages = t.getStrPages();
		int i = 0;
		int j = pages.size() - 1;
		// System.out.println(pages);
		while (j >= i) {
			boolean goLeft = false; // go to lower half of pages, excluding first half
			int mid = i + (j - i) / 2;
			// System.out.println("I: " + i + " J " + j + " MIDDD: " + mid);
			p = deserializePage(pages.get(mid));
			Object first = p.getFirstValue().getHtblTuple().get(strClusteringKey);
			Object last = p.getLastValue().getHtblTuple().get(strClusteringKey);
			if (clusteringKeyValue instanceof String) {
				foundPage = ((String) first).compareTo((String) clusteringKeyValue) <= 0
						&& ((String) last).compareTo((String) clusteringKeyValue) >= 0;
				goLeft = ((String) first).compareTo((String) clusteringKeyValue) > 0; // first element in page is
				// greater than what we are
				// looking for
			} else if (clusteringKeyValue instanceof Integer) {

				foundPage = ((Integer) first).compareTo((Integer) clusteringKeyValue) <= 0
						&& ((Integer) last).compareTo((Integer) clusteringKeyValue) >= 0;
				goLeft = ((Integer) first).compareTo((Integer) clusteringKeyValue) > 0;
			} else {
				foundPage = ((Double) first).compareTo((Double) clusteringKeyValue) <= 0
						&& ((Double) last).compareTo((Double) clusteringKeyValue) >= 0;
				goLeft = ((Double) first).compareTo((Double) clusteringKeyValue) > 0;
			}

			if (foundPage)
				return p;
			if (goLeft) {
				j = mid - 1;
			} else {
				i = mid + 1;
			}
		}
		return null;

	}

	// get PageNum for insertion
	// assumes at least one page is there
	// get PageNum for insertion
	// assumes at least one page is there
	public int binarySearchWithoutIndexForInsertion(String strTableName, String strClusteringKey,
			Object clusteringKeyValue)
			throws DBAppException {
		Table t = deserializeTable(strTableName);
		boolean foundPage = false; // findPage
		Page p = null;
		Vector<String> pages = t.getStrPages();
		/*
		 * three more cases different from update: key value less than minimum value in
		 * table or
		 * greater than maximum in table or "in between" two pages
		 * 
		 */
		int i = 0;
		int j = pages.size() - 1;
		while (j >= i) {
			boolean goLeft = false; // go to lower half of pages, excluding first half
			int mid = i + (j - i) / 2;
			p = deserializePage(pages.get(mid));
			Object first = p.getFirstValue().getHtblTuple().get(strClusteringKey);
			Object last = p.getLastValue().getHtblTuple().get(strClusteringKey);
			if (clusteringKeyValue instanceof String) {
				foundPage = ((String) first).compareTo((String) clusteringKeyValue) <= 0
						&& ((String) last).compareTo((String) clusteringKeyValue) >= 0;
				if (!foundPage && mid < pages.size() - 1) {
					int nextPageNum = t.getNextPageNum(Integer.parseInt(p.getPageNum()));
					Page p2 = deserializePage(strTableName + "_" + nextPageNum);

					Object first2 = p2.getFirstValue().getHtblTuple().get(strClusteringKey);
					foundPage = ((String) last).compareTo((String) clusteringKeyValue) < 0
							&& ((String) first2).compareTo((String) clusteringKeyValue) > 0;

				}

				goLeft = ((String) first).compareTo((String) clusteringKeyValue) > 0; // first element in page is
				// greater than what we are
				// looking for
				if (mid == 0) {
					foundPage = foundPage || ((String) first).compareTo((String) clusteringKeyValue) > 0;
				}
				if (mid == pages.size() - 1) {
					foundPage = foundPage || ((String) last).compareTo((String) clusteringKeyValue) < 0;
				}
			} else if (clusteringKeyValue instanceof Integer) {

				foundPage = ((Integer) first).compareTo((Integer) clusteringKeyValue) <= 0
						&& ((Integer) last).compareTo((Integer) clusteringKeyValue) >= 0;
				if (!foundPage && mid < pages.size() - 1) {
					int nextPageNum = t.getNextPageNum(Integer.parseInt(p.getPageNum()));
					Page p2 = deserializePage(strTableName + "_" + nextPageNum);

					Object first2 = p2.getFirstValue().getHtblTuple().get(strClusteringKey);
					foundPage = ((Integer) last).compareTo((Integer) clusteringKeyValue) < 0
							&& ((Integer) first2).compareTo((Integer) clusteringKeyValue) > 0;

				}
				goLeft = ((Integer) first).compareTo((Integer) clusteringKeyValue) > 0;
				if (mid == 0) {
					foundPage = foundPage || ((Integer) first).compareTo((Integer) clusteringKeyValue) > 0;
				}
				if (mid == pages.size() - 1) {
					foundPage = foundPage || ((Integer) last).compareTo((Integer) clusteringKeyValue) < 0;
				}

			} else {
				foundPage = ((Double) first).compareTo((Double) clusteringKeyValue) <= 0
						&& ((Double) last).compareTo((Double) clusteringKeyValue) >= 0;

				if (!foundPage && mid < pages.size() - 1) {
					int nextPageNum = t.getNextPageNum(Integer.parseInt(p.getPageNum()));
					Page p2 = deserializePage(strTableName + "_" + nextPageNum);

					Object first2 = p2.getFirstValue().getHtblTuple().get(strClusteringKey);
					foundPage = ((Double) last).compareTo((Double) clusteringKeyValue) < 0
							&& ((Double) first2).compareTo((Double) clusteringKeyValue) > 0;

				}
				goLeft = ((Double) first).compareTo((Double) clusteringKeyValue) > 0;
				if (mid == 0) {
					foundPage = foundPage || ((Double) first).compareTo((Double) clusteringKeyValue) > 0;
				}
				if (mid == pages.size() - 1) {
					foundPage = foundPage || ((Double) last).compareTo((Double) clusteringKeyValue) < 0;
				}
			}

			if (foundPage) {
				return Integer.parseInt(p.getPageNum());
			}
			if (goLeft) {
				j = mid - 1;
			} else {

				i = mid + 1;
			}

		}
		return -1;

	}

	// returns key: indexName, value: ColName
	public Hashtable<String, String> loadAllIndices(String strTableName) throws DBAppException {
		Hashtable<String, String> indices = new Hashtable<>();
		try {
			String line = "";
			boolean foundTable = false;
			BufferedReader br = new BufferedReader(new FileReader(METADATA_PATH));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");
				if (foundTable && !s[0].equals(strTableName)) {
					break;
				}
				if (s[0].equals(strTableName)) {
					foundTable = true;
					if (!s[4].equals("null")) {
						indices.put(s[4], s[1]);
					}

				}
			}
			br.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}
		return indices;
	}

	// returns key: ColName, value: IndexName
	public Hashtable<String, String> loadAllColumnsHavingIndex(String strTableName) throws DBAppException {
		Hashtable<String, String> indices = new Hashtable<>();
		try {
			String line = "";
			boolean foundTable = false;
			BufferedReader br = new BufferedReader(new FileReader(METADATA_PATH));
			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");
				if (foundTable && !s[0].equals(strTableName)) {
					break;
				}
				if (s[0].equals(strTableName)) {
					foundTable = true;
					if (!s[4].equals("null")) {
						indices.put(s[1], s[4]);
					}

				}
			}
			br.close();
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}
		return indices;
	}

	// SELECT With Precedence
	public Vector<Object> convertinfixtoPostfix(SQLTerm[] arrSQLTerms,
			String[] strarrOperators) {
		Stack<String> operators = new Stack<>();
		Vector<Object> resultpostfix = new Vector<>();
		int i = 0;
		int j = 0;
		while (i < arrSQLTerms.length || j < strarrOperators.length) {
			resultpostfix.add(arrSQLTerms[i]);
			i++;
			if (j < strarrOperators.length) {
				if (operators.isEmpty()) {
					operators.add(strarrOperators[j]);
					j++;
				} else {
					// check priority of the inserted operator with the other operators in stack
					// boolean higher= checkhigherprecedence(strarrOperators[j], operators.peek());
					// i am making sure that all the operands left are of lower priproty if not pop
					// or the stack has not become empty yet
					while (!operators.isEmpty() && !checkhigherprecedence(strarrOperators[j], operators.peek())) {
						resultpostfix.add(operators.pop());
					}
					operators.add(strarrOperators[j]);
					j++;

				}
			}

		}
		while (!operators.isEmpty()) {
			resultpostfix.add(operators.pop());
		}

		return resultpostfix;

	}

	// check whether the inserted value has higher precedence than el peeked value
	public boolean checkhigherprecedence(String insertedValue, String peekedValue) {
		if (insertedValue.equals("AND")) {
			if (peekedValue.equals("AND")) {
				return false;
			} else {
				return true;
			}
		} else if (insertedValue.equals("OR")) {
			if (peekedValue.equals("AND") || peekedValue.equals("OR")) {
				return false;
			} else {
				return true;
			}
		} else if (insertedValue.equals("XOR")) {
			return false;
		}
		return false;
	}

	public boolean queryOptimizerWithPrecedence(Vector<Object> operations) {
		// all have indices
		if (!operations.contains(false)) {
			return true;
		}
		// process sequentially all operators
		int j = 1;
		while (j < operations.size() - 1) {
			// AND case: if any have index open these pages from bPlusTree
			if (operations.get(j).equals("AND")) {
				Boolean resultAND = (Boolean) operations.get(j - 1) || (Boolean) operations.get(j + 1);
				operations.remove(j - 1);
				operations.remove(j);
				operations.set(j - 1, resultAND);
				// OR or XOR case: both must have indices for an index to be used
				// otherwise, must loop over all tuples
			}
			j++;

		}
		j = 1;
		while (j < operations.size() - 1) {
			if (!operations.get(j).equals("AND")) {
				Boolean result = (Boolean) operations.get(j - 1) && (Boolean) operations.get(j + 1);
				operations.remove(j - 1);
				operations.remove(j);
				operations.set(j - 1, result);
			}
			j++;
		}
		// System.out.println("After processing");
		// System.out.println(operations);
		// XOR and OR operators will require opening all pages if any don't have an
		// index
		return (Boolean) operations.get(0);

	}

	public Iterator selectFromTableWithPrecedence(SQLTerm[] arrSQLTerms,
			String[] strarrOperators) throws DBAppException {
		// check forarrsql and stroperators for null
		if (arrSQLTerms == null && strarrOperators == null) {
			throw new DBAppException("Invalid input: both inputs are null");
		}
		if (arrSQLTerms.length == 1 && arrSQLTerms[0]._strTableName != null
				&& arrSQLTerms[0]._objValue == null && arrSQLTerms[0]._strColumnName == null
				&& arrSQLTerms[0]._strOperator == null && strarrOperators == null) {
			if (checkTableExists(arrSQLTerms[0]._strTableName)) {
				Vector<Tuple> finalresult = new Vector<>();
				Table t = deserializeTable(arrSQLTerms[0]._strTableName);
				for (String pname : t.getStrPages()) {
					Page p = deserializePage(pname);
					for (Tuple row : p.getRecords()) {
						finalresult.add(row);
					}

				}
				return finalresult.iterator();

			} else {
				throw new DBAppException("Table doesn't exists");
			}

		}
		if (arrSQLTerms == null || strarrOperators == null) {
			throw new DBAppException("Invalid input: one of the inputs (or both) is null");
		}
		// Check for valid input
		if (arrSQLTerms.length == 0 || strarrOperators.length != arrSQLTerms.length - 1) {
			throw new DBAppException("Invalid input: Term and Operator arrays mismatch");
		}
		checkDataForSelect(arrSQLTerms, strarrOperators);
		Hashtable<String, String> columns = loadAllColumnsHavingIndex(arrSQLTerms[0]._strTableName);
		Vector<Object> operations = queryOperationsGenerator(arrSQLTerms, strarrOperators, columns);
		// passed by reference to method
		Vector<Object> operationsCopy = new Vector<>();
		operationsCopy.addAll(operations);
		boolean useIndex = queryOptimizerWithPrecedence(operationsCopy);
		// keep only sql terms true and false in operations vector to access it with
		// same index
		operations.removeIf(element -> element instanceof String);
		// System.out.println("operations" + operations);
		// System.out.println("useIndex:" + useIndex);
		// as not to contain duplicates
		HashSet<String> pages = new HashSet<>();
		if (useIndex) {
			pages = selectForAllWithAllIndexWithPrecedence(arrSQLTerms, strarrOperators);
		} else {
			// direct tuple manipulation in all pages
			Table t = deserializeTable(arrSQLTerms[0]._strTableName);
			pages.addAll(t.getStrPages());
			t = null;
		}
		return selectForAllWithoutIndexWithPrecedence(arrSQLTerms, strarrOperators, pages);
	}

	public HashSet<String> selectForAllWithAllIndexWithPrecedence(SQLTerm[] arrSQLTerms,
			String[] strarrOperators) throws DBAppException {
		String strTableName = arrSQLTerms[0]._strTableName;
		// load all indices
		Hashtable<String, String> indexhtbl = loadAllColumnsHavingIndex(strTableName);

		// check if the are all AND operators
		boolean andonly = true;
		for (int i = 0; i < strarrOperators.length; i++) {
			if (!strarrOperators[i].equals("AND")) {
				andonly = false;
				break;
			}

		}

		if (andonly) {
			// loop to get the first indexed column
			String firstindexname = "";
			SQLTerm firstindexedsqlterm = new SQLTerm();
			for (int i = 0; i < arrSQLTerms.length; i++) {
				if (indexhtbl.get(arrSQLTerms[i]._strColumnName) != null) {
					firstindexname = indexhtbl.get(arrSQLTerms[i]._strColumnName);
					firstindexedsqlterm = arrSQLTerms[i];

					break;

				}
			}
			bplustree indextree = deserializeIndex(firstindexname);
			HashSet<String> pagenames = new HashSet<String>(executeindexedSQlterm(indextree, firstindexedsqlterm));
			HashSet<String> pages = new HashSet<>(pagenames);

			return pages;

		} else {
			Vector<Object> postfix = convertinfixtoPostfix(arrSQLTerms, strarrOperators);
			Stack<Object> sqltermsorResult = new Stack<>();

			// System.out.println("THE POSTFIX SIZE IS: " + postfix.size());
			for (int i = 0; i < postfix.size(); i++) {

				if (postfix.get(i) instanceof SQLTerm) {
					sqltermsorResult.add(postfix.get(i));
				}
				if (postfix.get(i) instanceof String) {
					String stroperator = (String) postfix.get(i);
					HashSet<String> pagenames1 = new HashSet<>();
					HashSet<String> pagenames2 = new HashSet<>();
					boolean indexfound1 = false;
					boolean indexfound2 = false;

					if (sqltermsorResult.peek() instanceof SQLTerm) {
						SQLTerm sql1 = (SQLTerm) sqltermsorResult.pop();
						String indexName = indexhtbl.get(sql1._strColumnName);
						if (indexName != null && !sql1._strOperator.equals("!=")) {
							indexfound1 = true;
							/// compute the sqlterm with b+tree
							bplustree indextree = deserializeIndex(indexName);
							pagenames2 = new HashSet<String>(executeindexedSQlterm(indextree, sql1));
							serializeIndex(indextree, indexName);
						}
					} else if (sqltermsorResult.peek() instanceof HashSet) {
						// System.out.println("temp hashset of results :
						// "+Arrays.toString(((HashSet<String>)sqltermsorResult.pop()).toArray()));
						// Vector<String> v=null;
						// v.get(i);
						pagenames1 = (HashSet<String>) sqltermsorResult.pop();
						indexfound1 = true;

					}

					if (sqltermsorResult.peek() instanceof SQLTerm) {
						SQLTerm sql2 = (SQLTerm) sqltermsorResult.pop();
						String indexName = indexhtbl.get(sql2._strColumnName);
						if (indexName != null && !sql2._strOperator.equals("!=")) {
							indexfound2 = true;
							/// compute the sqlterm with b+tree
							bplustree indextree = deserializeIndex(indexName);
							pagenames2 = new HashSet<String>(executeindexedSQlterm(indextree, sql2));
							serializeIndex(indextree, indexName);

						}
					} else if (sqltermsorResult.peek() instanceof HashSet) {

						pagenames2 = (HashSet<String>) sqltermsorResult.pop();
						indexfound2 = true;

					}

					// if index found in the 2 columns
					if (indexfound1 && indexfound2) {
						// when AND
						if (stroperator.equals("AND")) {
							HashSet<String> common = new HashSet<>(pagenames1);
							common.retainAll(pagenames2);
							// add it back to the stack
							sqltermsorResult.push(common);

						} else if (stroperator.equals("OR")) {
							HashSet<String> union = new HashSet<>(pagenames1);
							union.addAll(pagenames2);
							// add it back to the stack
							sqltermsorResult.push(union);
						} else if (stroperator.equals("XOR")) {
							HashSet<String> xor = new HashSet<String>(pagenames1);
							HashSet<String> intersection = new HashSet<>(pagenames1);

							// xor= union - intersection

							// get the intersection (common between pagenames1 and pagenames2)
							intersection.retainAll(pagenames2);
							// put the union first in the xor
							xor.addAll(intersection);
							// remove the intersection from the xor
							xor.removeAll(intersection);
							sqltermsorResult.push(xor);
						}
					} else if (indexfound1 || indexfound2) {
						HashSet<String> pagenames;
						// save the page names of the one with index
						if (indexfound1) {
							pagenames = pagenames1;
						} else {
							pagenames = pagenames2;
						}
						// only valid condition if we should use pogenames is the ANDing between index
						// and non index
						if (stroperator.equals("AND")) {
							sqltermsorResult.push(pagenames);
						}
					}

				}
			}
			// the stack should only have only 1 value which is the result hashset
			if (sqltermsorResult.size() == 1) {
				// the end result of page names is a Hashset so convert it into vector of tuples
				HashSet<String> pages = new HashSet<String>((HashSet<String>) sqltermsorResult.pop());

				return pages;

			}
		}

		return null;
	}

	public Iterator<Tuple> selectForAllWithoutIndexWithPrecedence(SQLTerm[] arrSQLTerms,
			String[] strarrOperators, HashSet<String> pages) throws DBAppException {
		String strTableName = arrSQLTerms[0]._strTableName;
		Table table = deserializeTable(strTableName);
		Vector<Tuple> selectedRows = new Vector<>();

		// loop to check for undefined operators
		for (int i = 0; i < strarrOperators.length; i++) {
			if (!(strarrOperators[i].equals("AND") || strarrOperators[i].equals("OR")
					|| strarrOperators[i].equals("XOR"))) {
				throw new DBAppException("There are undefined operator(s) in the select query");
			}
		}

		Vector<String> pages2 = new Vector<String>(pages);
		for (String page : pages) {
			Page currentPage = deserializePage(page);
			Vector<Tuple> rows = currentPage.getRecords();
			for (Tuple row : rows) {
				Vector<Boolean> flags = new Vector<>();
				for (SQLTerm sqlTerm : arrSQLTerms) {
					String strColumnName = sqlTerm._strColumnName;
					Object objValue = sqlTerm._objValue;
					String operator = sqlTerm._strOperator;
					flags.add(CompareWithoutIndex(strTableName, row, strColumnName, objValue, operator));
				}
				// System.out.println("flags are " + flags);
				boolean res = false;

				// we will loop to check for AND operators and save the AND operators in a
				// hashtable
				// where the key is the index of the operator in the stararroperator and the
				// value result of this operation

				// [t,f,f,t,f]
				// ['name=John',age>50]
				// ['OR','OR','AND','AND']
				Hashtable<Integer, Boolean> andResults = new Hashtable<>();
				for (int i = 0; i < strarrOperators.length; i++) {

					if (strarrOperators[i].equals("AND")) {
						if (i == 0) {
							res = flags.get(i) && flags.get(i + 1);
							andResults.put(i, res);

						} else if (strarrOperators[i - 1].equals("AND")) {
							res = (andResults.get((i - 1)) && flags.get(i + 1));
							andResults.put(i, res);
						} else {
							res = flags.get(i) && flags.get(i + 1);
							andResults.put(i, res);
						}
					}
				}
				Hashtable<Integer, Boolean> orResults = new Hashtable<>();
				for (int i = 0; i < strarrOperators.length; i++) {
					// System.out.println("i is: " + i);
					// System.out.println("operator is: " + strarrOperators[i]);

					if (strarrOperators[i].equals("OR")) {
						Boolean before = false;
						Boolean after = false;

						if (i == 0 && i == strarrOperators.length - 1) {
							before = null;
							after = null;
						}
						if (i == 0) {
							before = null;
						} else if (i == strarrOperators.length - 1) {
							// System.out.println("blue");
							after = null;
						}
						if (before != null) {
							if (strarrOperators[i - 1].equals("AND")) {
								before = andResults.get(i - 1);

							} else if (strarrOperators[i - 1].equals("OR")) {
								before = orResults.get(i - 1);
							}

							else {
								before = flags.get(i);
							}
						}
						if (after != null) {
							if (strarrOperators[i + 1].equals("AND")) {
								after = andResults.get(i + 1);

							} else {
								after = flags.get(i + 1);
							}

						}

						if (before == null && after == null) {
							// res=flags.get(i)&& flags.get(i+1);
							// andResults.put(i,res);

							res = flags.get(i) || flags.get(i + 1);
							orResults.put(i, res);
							// orResults.put(i,(flags.get(i) || flags.get(i+1)));
						}
						/// [t,f,t]
						// [OR,and]
						else if (after == null) {
							res = before || flags.get(i + 1);

							orResults.put(i, res);
						} else if (before == null) {
							res = (flags.get(i) || after);
							orResults.put(i, res);

						} else {
							res = (before || after);
							orResults.put(i, res);
						}
					}
				}

				///// CHECKING FOR XOR OPERATIONS
				Hashtable<Integer, Boolean> xorResults = new Hashtable<>();
				for (int i = 0; i < strarrOperators.length; i++) {
					if (!strarrOperators[i].equals("XOR")) {
						continue;
					}
					Boolean before = false;
					Boolean after = false;

					if (i == 0 && i == strarrOperators.length - 1) {
						before = null;
						after = null;
					}
					if (i == 0) {
						before = null;
					} else if (i == strarrOperators.length - 1) {
						after = null;
					}
					if (before != null) {
						if (strarrOperators[i - 1].equals("AND")) {
							before = andResults.get(i - 1);

						} else if (strarrOperators[i - 1].equals("OR")) {
							before = orResults.get(i - 1);
						} else if (strarrOperators[i - 1].equals("XOR")) {
							before = xorResults.get(i - 1);
						} else {
							before = flags.get(i);
						}
					}
					if (after != null) {
						if (strarrOperators[i + 1].equals("AND")) {
							after = andResults.get(i + 1);

						} else if (strarrOperators[i + 1].equals("OR")) {
							after = orResults.get(i + 1);
						} else {
							after = flags.get(i + 1);
						}

					}

					if (before == null && after == null) {
						res = (flags.get(i) ^ flags.get(i + 1));
						xorResults.put(i, res);
					} else if (after == null) {
						res = (before ^ flags.get(i + 1));
						xorResults.put(i, res);
					} else if (before == null) {
						res = (flags.get(i) ^ after);
						xorResults.put(i, res);
					} else {
						res = (before ^ after);
						xorResults.put(i, res);
					}
				}
				// System.out.println("Result boolean : " + res);
				/////// check that the row meet all conditions //////
				if (res) {
					selectedRows.add(row);
				}

			}

		}

		Iterator<Tuple> selectionResult = selectedRows.iterator();
		return selectionResult;

	}

	public Boolean CompareWithoutIndex(String strTableName, Tuple row, String strColumnName, Object objValue,
			String operator) {

		// Table table=deserializeTable(strTableName);

		Hashtable<String, Object> htblTuple = row.getHtblTuple();
		// System.out.println("Hashtable : " + htblTuple);
		// System.out.println("col Name " + strColumnName);
		Object ob = htblTuple.get(strColumnName);
		// get clustering key and its datatype from csv
		String datatype = "";
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(METADATA_PATH));

			while ((line = br.readLine()) != null) {
				String[] s = line.split(", ");
				if (s[0].equals(strTableName) && s[1].equals(strColumnName)) {
					datatype = s[2];
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (operator.equals(">")) {
			int comparable = 5;
			switch (datatype.toLowerCase()) {
				case "java.lang.integer":
					comparable = ((Integer) ob).compareTo((Integer) objValue);
					break;
				case "java.lang.string":
					comparable = ((String) ob).compareTo((String) objValue);
					break;
				case "java.lang.double":
					comparable = ((Double) ob).compareTo((Double) objValue);
					break;
			}
			if (comparable > 0) {
				return true;
			}

		} else if (operator.equals(">=")) {
			int comparable = 5;
			switch (datatype.toLowerCase()) {
				case "java.lang.integer":
					comparable = ((Integer) ob).compareTo((Integer) objValue);
					break;
				case "java.lang.string":
					comparable = ((String) ob).compareTo((String) objValue);
					break;
				case "java.lang.double":
					comparable = ((Double) ob).compareTo((Double) objValue);
					break;
			}
			if (comparable >= 0) {
				return true;
			}
		} else if (operator.equals("<")) {
			int comparable = 5;
			switch (datatype.toLowerCase()) {
				case "java.lang.integer":
					comparable = ((Integer) ob).compareTo((Integer) objValue);
					break;
				case "java.lang.string":
					comparable = ((String) ob).compareTo((String) objValue);
					break;
				case "java.lang.double":
					comparable = ((Double) ob).compareTo((Double) objValue);
					break;
			}
			if (comparable < 0) {
				return true;
			}
		} else if (operator.equals("<=")) {
			int comparable = 5;
			switch (datatype.toLowerCase()) {
				case "java.lang.integer":
					comparable = ((Integer) ob).compareTo((Integer) objValue);
					break;
				case "java.lang.string":
					comparable = ((String) ob).compareTo((String) objValue);
					break;
				case "java.lang.double":
					comparable = ((Double) ob).compareTo((Double) objValue);
					break;
			}
			if (comparable <= 0) {
				return true;
			}
		} else if (operator.equals("=")) {
			// System.out.println("operator is right");
			int comparable = 5;
			switch (datatype.toLowerCase()) {
				case "java.lang.integer":
					comparable = ((Integer) ob).compareTo((Integer) objValue);
					break;
				case "java.lang.string":
					comparable = ((String) ob).compareTo((String) objValue);
					break;
				case "java.lang.double":
					comparable = ((Double) ob).compareTo((Double) objValue);
					break;
			}
			// System.out.println(datatype);
			// System.out.println("objectrow is " + (String) (ob + ""));
			// System.out.println("objvalue is" + (String) (objValue + ""));

			// System.out.println("Comparable is " + comparable);
			if (comparable == 0) {
				return true;
			}

		} else if (operator.equals("!=")) {
			// System.out.println("operator is right");
			int comparable = 5;
			switch (datatype.toLowerCase()) {
				case "java.lang.integer":
					comparable = ((Integer) ob).compareTo((Integer) objValue);
					break;
				case "java.lang.string":
					comparable = ((String) ob).compareTo((String) objValue);
					break;
				case "java.lang.double":
					comparable = ((Double) ob).compareTo((Double) objValue);
					break;
			}
			// System.out.println(datatype);
			// System.out.println("objectrow is " + (String) (ob + ""));
			// System.out.println("objvalue is" + (String) (objValue + ""));

			// System.out.println("Comparable is " + comparable);
			if (comparable != 0) {
				return true;
			}

		}
		return false;
	}

	// below method returns Iterator with result set if passed
	// strbufSQL is a select, otherwise returns null.
	// public Iterator parseSQL( StringBuffer strbufSQL ) throws DBAppException{
	// String source =
	// "C:\\Users\\Zrafa\\IdeaProjects\\Database-Engine\\dbms\\src\\main\\java\\antlr\\test.txt";
	// BufferedWriter writer = null;
	// try {
	// writer = new BufferedWriter(new FileWriter(source, false));
	// } catch (IOException e) {
	// throw new DBAppException(e.getMessage());
	// }
	// try {
	// writer.write(strbufSQL.toString());
	// } catch (IOException e) {
	// throw new DBAppException(e.getMessage());
	// }
	// try {
	// writer.close();
	// } catch (IOException e) {
	// throw new DBAppException(e.getMessage());
	// }
	// CharStream cs = null;
	// try {
	// cs = fromFileName(source);
	// } catch (IOException e) {
	// throw new DBAppException(e.getMessage());
	// }
	// antlr.SQLLexer lexer = new SQLLexer(cs);
	// CommonTokenStream token = new CommonTokenStream(lexer);
	// SQLParser parser = new SQLParser(token);
	// ParseTree tree = parser.parse();

	// myVisitor visitor = new myVisitor();
	// visitor.visit(tree);

	// return visitor.getResultIterator();
	// }

	/////////////////////////////////////////// END
	/////////////////////////////////////////// //////////////////////////////////////////////////////////

	@SuppressWarnings({ "removal", "unchecked", "rawtypes", "unused" })
	public static void main(String[] args) throws DBAppException {

		String strTableName = "Student";
		DBApp dbApp = new DBApp();
		Hashtable htblColNameType = new Hashtable();
		htblColNameType.put("id", "java.lang.Integer");
		htblColNameType.put("name", "java.lang.String");
		htblColNameType.put("gpa", "java.lang.double");
		dbApp.createTable(strTableName, "id", htblColNameType);
		dbApp.createIndex(strTableName, "gpa", "gpaINDEX");
		dbApp.createIndex(strTableName, "id", "idINDEX");
		String[] names = { "john", "ahmed", "mohamed", "zoz" };
		for (int i = 0; i < 20; i++) {

			Hashtable htblColNameValue = new Hashtable();
			htblColNameValue.put("id", i);
			htblColNameValue.put("name", names[i % 4]);
			htblColNameValue.put("gpa", Math.abs(new Double(i) - 0.5));
			dbApp.insertIntoTable(strTableName, htblColNameValue);
		}
		// SQLTerm[] sqlTerms = new SQLTerm[4];
		// sqlTerms[0] = new SQLTerm(strTableName, "name", "=", "john");
		// sqlTerms[2] = new SQLTerm(strTableName, "id", ">", 0);
		// sqlTerms[1] = new SQLTerm(strTableName, "gpa", "=", 4.5);
		// sqlTerms[3] = new SQLTerm(strTableName, "gpa", ">=", 11.0);
		// String[] strOperators = { "XOR", "OR", "AND" };

		// dbApp.deleteFromTable("Student", new Hashtable<>());

		System.out.println("BREAK");
		System.out.println(dbApp.deserializeIndex("idINDEX"));
		System.out.println(dbApp.deserializeIndex("gpaINDEX"));

	}
}
