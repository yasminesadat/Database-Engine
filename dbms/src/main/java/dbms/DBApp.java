package dbms;

/** * @author Wael Abouelsaadat */
import bPlusTree.bplustree;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
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
import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.Integer;
import java.lang.Double;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;

public class DBApp {
	private static final String METADATA_PATH = "dbms/src/main/resources/metadata.csv";
	private static final String CONFIG_FILE_PATH = "dbms/src/main/resources/DBApp.config";
	private static final String TABLES_DIR = "dbms/src/main/resources/Tables/";
	private static final String PAGES_DIR = "dbms/src/main/resources/Pages/";
	private static final String INDICES_DIR = "dbms/src/main/resources/Indices/";

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

			int maxSize = Integer.parseInt(prop.getProperty("MaximumRowsCountinPage"));
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

		// Check table exists
		if (!checkTableExists(strTableName))
			throw new DBAppException("INSERT INTO TABLE: Table doesn't exist");

		// Check datatypes from csv and if clusteringKey is repeated
		// Returns {strClusteringKey, strClusteringColIndexName}
		String[] clusteringData = checkDataForInsert(strTableName, htblColNameValue);

		// Base case: no pages yet
		Table t = deserializeTable(strTableName);
		Vector<String> pages = t.getStrPages();
		System.out.println("how many pages: " + pages.size());
		if (pages.size() == 0) {
			Page p = new Page(strTableName, 1);
			p.insertBinary(htblColNameValue, clusteringData[0]);
			serializePage(p);
			t.addNewPage();
		}
		// Second case: no index on clusteringKey
		else if (clusteringData[1].equals("null")) {
			// Find Page
			int x = binarySearchWithoutIndexForInsertion(strTableName, clusteringData[0],
					htblColNameValue.get(clusteringData[0]));
			System.out.println("Page number from binary search:" + x);
			Page p = deserializePage(strTableName + "_" + x);
			// page is not full
			System.out.println("Page Size: " + p.getRecords().size());
			if (p.getRecords().size() < p.getMaxEntries()) {
				p.insertBinary(htblColNameValue, clusteringData[0]);
				serializePage(p);
			} else {

			}

		} else { // Third Case: handle index exists

		}

		// update any indices for the table after successful insertion
		Hashtable<String, String> v = loadAllIndices(strTableName);
		for (String i : v.keySet()) {
			bplustree b = deserializeIndex(i);
			// to be cont
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
		if (!checkTableExists(strTableName))
			throw new DBAppException("UPDATE TABLE: Table doesn't exist");

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

		checkDataTypesForUpdate(strTableName, htblColNameValue);

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
			// pageNum
			int x = binarySearchWithoutIndex(strTableName, clusteringKey, clusteringKeyValue);
			if (x <= 0) {
				throw new DBAppException("UPDATE TABLE: Record not found in table");
			} else {
				Page p = deserializePage(strTableName + "_" + x);
				Vector<Tuple> records = p.getRecords();
				int i = p.binarySearch(clusteringKey, clusteringKeyValue); // rowNumber
				Tuple row = records.get(i);
				Hashtable<String, Object> hashtable = row.getHtblTuple();
				// colName,indexName
				for (String colName : otherIndices.keySet()) {
					bplustree tree = deserializeIndex(otherIndices.get(colName));
					tree.delete(hashtable.get(colName), x + "");
					tree.insert(htblColNameValue.get(colName), x + "");
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
				throw new DBAppException("UPDATE TABLE: Record not found in table");
			} else {
				// already know it'll be one value
				String pageNum = v.get(0);
				Page p = deserializePage(strTableName + "_" + pageNum);
				int i = p.binarySearch(clusteringKey, clusteringKeyValue); // rowNumber
				System.out.println(i);
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

		throw new DBAppException("not implemented yet");
	}

	public Iterator selectFromTable(SQLTerm[] arrSQLTerms,
			String[] strarrOperators) throws DBAppException {

		return null;
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

	// public boolean checkTableExists(String strTableName) {
	// if (Files.exists(Paths.get("Tables/" + strTableName + ".ser"))) {
	// return true;
	// }
	// return false;
	// }

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
			if (binarySearchWithoutIndex(strTableName, clusteringKey, htblColNameValue.get(clusteringKey)) >= 0)
				throw new DBAppException(
						"CHECK DATA: Repeated clustering key value " + htblColNameValue.get(clusteringKey));
		}
		return new String[] { clusteringKey, clusteringKeyIndex };
	}

	public void checkDataTypesForUpdate(String strTableName, Hashtable<String, Object> htblColNameValue)
			throws DBAppException {
		BufferedReader br = null;
		String line = "";
		boolean foundTable = false; // use as not to continue looping through all of the file if the needed
									// datatypes
									// have been all checked
		int countColumns = 0; // to make sure that the hashtable contains exactly the number of fields
								// that have been checked
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

					// check if not matching datatypes for present fields
					if (htblColNameValue.containsKey(s[1])) {

						if (!(htblColNameValue.get(s[1]).getClass().getTypeName().toLowerCase()).equals(s[2])) {
							br.close();
							throw new DBAppException(
									"CHECK DATATYPES: Invalid datatype for " + s[1] + ". You have entered "
											+ htblColNameValue.get(s[1]).getClass().getTypeName());
						} else {
							countColumns++;
						}
					}
				}

			}
			br.close();
			if (htblColNameValue.size() != countColumns)
				throw new DBAppException(
						"CHECK DATATYPES: You entered more column fields that don't exist in the table");
		} catch (IOException e) {
			throw new DBAppException(e.getMessage());
		}

	}

	// public int binarySearchWithoutIndex(String strTableName, String
	// strClusteringKey, Object clusteringKeyValue)
	// throws DBAppException {
	// Table t = deserializeTable(strTableName);
	// boolean foundPage = false;
	// Page p = null;
	// for (String pageName : t.getStrPages()) {
	// p = deserializePage(pageName);
	// Object first = p.getFirstValue().getHtblTuple().get(strClusteringKey);
	// Object last = p.getLastValue().getHtblTuple().get(strClusteringKey);
	// if (clusteringKeyValue instanceof String)
	// foundPage = ((String) first).compareTo((String) clusteringKeyValue) <= 0
	// && ((String) last).compareTo((String) clusteringKeyValue) >= 0;
	// else if (clusteringKeyValue instanceof Integer)
	// foundPage = ((Integer) first).compareTo((Integer) clusteringKeyValue) <= 0
	// && ((Integer) last).compareTo((Integer) clusteringKeyValue) >= 0;
	// else
	// foundPage = ((Double) first).compareTo((Double) clusteringKeyValue) <= 0
	// && ((Double) last).compareTo((Double) clusteringKeyValue) >= 0;
	// if (foundPage)
	// break;

	// }
	// // if not page can potentially have this value for the clusteringKey
	// if (!foundPage) {
	// return -1;
	// } else
	// return p.binarySearch(strClusteringKey, clusteringKeyValue);
	// }
	// get PageNum
	public int binarySearchWithoutIndex(String strTableName, String strClusteringKey, Object clusteringKeyValue)
			throws DBAppException {
		Table t = deserializeTable(strTableName);
		boolean foundPage = false; // findPage
		Page p = null;
		Vector<String> pages = t.getStrPages();
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
				return Integer.parseInt(p.getPageNum());
			if (goLeft) {
				j = mid - 1;
			} else {
				i = mid + 1;
			}
		}
		return -1;

	}

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
		 * two more cases different from update: key value less than minimum value in
		 * table or
		 * greater than maximum in table
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
				goLeft = ((Double) first).compareTo((Double) clusteringKeyValue) > 0;
				if (mid == 0) {
					foundPage = foundPage || ((Double) first).compareTo((Double) clusteringKeyValue) > 0;
				}
				if (mid == pages.size() - 1) {
					foundPage = foundPage || ((Double) last).compareTo((Double) clusteringKeyValue) < 0;
				}
			}

			if (foundPage)
				return Integer.parseInt(p.getPageNum());
			if (goLeft) {
				j = mid - 1;
			} else {
				i = mid + 1;
			}
		}
		return -1;

	}

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

	/////////////////////////////////////////// END
	/////////////////////////////////////////// //////////////////////////////////////////////////////////

	@SuppressWarnings({ "removal", "unchecked", "rawtypes", "unused" })
	public static void main(String[] args) throws DBAppException {
		// INSERT
		String strTableName = "Student";
		DBApp dbApp = new DBApp();

		// Hashtable htblColNameType = new Hashtable();
		// htblColNameType.put("id", "java.lang.Integer");
		// htblColNameType.put("name", "java.lang.String");
		// htblColNameType.put("gpa", "java.lang.double");
		// dbApp.createTable(strTableName, "id", htblColNameType);

		// Hashtable htblColNameValue = new Hashtable();
		// htblColNameValue.put("id", new Integer(1));
		// htblColNameValue.put("name", new String("noody"));
		// htblColNameValue.put("gpa", new Double(0.95));
		// dbApp.insertIntoTable(strTableName, htblColNameValue);
		// htblColNameValue.clear();
		// htblColNameValue.put("id", new Integer(2));
		// htblColNameValue.put("name", new String("alia"));
		// htblColNameValue.put("gpa", new Double(0.95));
		// System.out.println("INSERT 1");
		// dbApp.insertIntoTable(strTableName, htblColNameValue);
		// htblColNameValue.clear();
		// System.out.println("INSERT 2");
		// htblColNameValue.put("id", new Integer(0));
		// htblColNameValue.put("name", new String("monmon"));
		// htblColNameValue.put("gpa", new Double(1.25));
		// System.out.println("INSERT 3");
		// dbApp.insertIntoTable(strTableName, htblColNameValue);
		// htblColNameValue.clear();
		// System.out.println("INSERT 4");
		// htblColNameValue.put("id", new Integer(4));
		// htblColNameValue.put("name", new String("malouka"));
		// htblColNameValue.put("gpa", new Double(1.5));
		// dbApp.insertIntoTable(strTableName, htblColNameValue);
		// htblColNameValue.clear();

		// overflow insert
		Page p = dbApp.deserializePage("Student_1");
		System.out.println(p);

		// dbApp.createIndex(strTableName, "gpa", "gpaIndex");
		// bplustree b = dbApp.deserializeIndex("gpaIndex");
		// b.printTree();

		// UPDATE
		// String strTableName = "Student";
		// DBApp dbApp = new DBApp();
		// Hashtable htblColNameValue = new Hashtable();
		// // htblColNameValue.put("id", new Integer(1));
		// htblColNameValue.put("name", new String("aaaa"));
		// htblColNameValue.put("gpa", new Double(99));
		// dbApp.updateTable(strTableName, "3", htblColNameValue);
		// Page p = dbApp.deserializePage("Student_1");
		// System.out.println(p);

		// UPDATE 2
		// String strTableName = "Student";
		// DBApp dbApp = new DBApp();
		// Hashtable htblColNameValue = new Hashtable();
		// dbApp.createIndex(strTableName, "name", "name_Index");
		// bplustree b = dbApp.deserializeIndex("name_Index");
		// // htblColNameValue.put("id", new Integer(1));
		// htblColNameValue.put("name", new String("CHANGE"));
		// htblColNameValue.put("gpa", new Double(100.0));
		// dbApp.updateTable(strTableName, "4", htblColNameValue);
		// Page p = dbApp.deserializePage("Student_1");
		// System.out.println(p);
		// b = dbApp.deserializeIndex("name_Index");
		// b.printTree();
		// bplustree b1 = dbApp.deserializeIndex("gpaIndex");
		// b1.printTree();

		// UPDATE 3: test search with index
		// String strTableName = "Student";
		// DBApp dbApp = new DBApp();
		// // dbApp.createIndex(strTableName, "id", "IDindex");
		// Hashtable htblColNameValue = new Hashtable();
		// // htblColNameValue.put("id", new Integer(1));
		// htblColNameValue.put("name", new String("BALABIZO3"));
		// dbApp.updateTable(strTableName, "1", htblColNameValue);
		// Page p = dbApp.deserializePage("Student_1");
		// System.out.println(p);

	}
}