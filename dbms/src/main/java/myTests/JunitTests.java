package myTests;

import bPlusTree.bplustree;
import dbms.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class JunitTests {
	private static DBApp engine;
	private static String newTableName;
//	private static final String id = "id";
//	private static final String name = "name";
//	private static final String gpa = "gpa";
//	private static final String TEST_NAME = "Abdo";
//	private static final double TEST_GPA = 1.8;
//	private static final String STRING_DATA_TYPE_NAME = "java.lang.String";
//	private static final String INTEGER_DATA_TYPE_NAME = "java.lang.Integer";
//	private static final String DOUBLE_DATA_TYPE_NAME = "java.lang.Double";
private static final String METADATA_PATH = "C:\\Users\\Zrafa\\IdeaProjects\\Database-Engine\\dbms\\src\\main\\resources\\metadata.csv";
	private static final String CONFIG_FILE_PATH = "C:\\Users\\Zrafa\\IdeaProjects\\Database-Engine\\dbms\\src\\main\\resources\\DBApp.config";
	private static final String TABLES_DIR = "C:\\Users\\Zrafa\\IdeaProjects\\Database-Engine\\dbms\\src\\main\\resources\\Tables\\";
	private static final String PAGES_DIR = "C:\\Users\\Zrafa\\IdeaProjects\\Database-Engine\\dbms\\src\\main\\resources\\Pages\\";
	private static final String INDICES_DIR = "C:\\Users\\Zrafa\\IdeaProjects\\Database-Engine\\dbms\\src\\main\\resources\\Indices\\";


	// Helper method to read the metadata file
	public String readMetadataFile() throws IOException {
		Path filePath = Paths.get(METADATA_PATH);
		return Files.readString(filePath);
	}

	//Helper method to empty metadata file
	public void emptyMetadataFile(String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath, false); // the true will append the new data
			fw.write(""); // erase all content
			fw.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	//NOTE: Make createTable and deleteFromTable throw DBAppException only



//CREATE TABLE


	@Test
	void testCreateTableValid() {

		try {
			emptyMetadataFile(METADATA_PATH);
			DBApp dbApp = new DBApp();
			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "java.lang.string");
			colNameType.put("age", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			dbApp.createTable("Students", "name", colNameType);



			String metadata = readMetadataFile();
			assertTrue(metadata.contains("Students, name, java.lang.string, True, null, null"));
			assertTrue(metadata.contains("Students, age, java.lang.integer, False, null, null"));
			assertTrue(metadata.contains("Students, gpa, java.lang.double, False, null, null"));
		} catch (DBAppException | IOException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testCreateTableExistingTableName() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();

		// Define column names and types correctly, including the clustering key
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("age", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.Double");

		// First creation of the table which should go through without issues
		assertDoesNotThrow(() -> dbApp.createTable("Students", "name", colNameType));

		// Second creation attempt of the table with the same name
		DBAppException thrown = assertThrows(
				DBAppException.class,
				() -> dbApp.createTable("Students", "name", colNameType),
				"Expected DBAppException for existing table name not thrown"
		);

		// Assert that the exception message is as expected for a duplicate table creation attempt
		assertEquals("CREATE TABLE: Table already exists", thrown.getMessage());
	}

	@Test
	void testCreateTableInvalidClusteringKey() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		try {

			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "java.lang.string");
			colNameType.put("age", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			// Use a column that doesn't exist in the column types
			dbApp.createTable("Students", "invalidKey", colNameType);
			fail("Expected DBAppException for invalid clustering key not thrown");
		} catch (DBAppException e) {
			assertEquals("CREATE TABLE: Clustering Key Not Found!", e.getMessage());
		}
	}
	@Test
	void testCreateTableInvalidDataTypes() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		try {

			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "InvalidType");
			colNameType.put("age", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			dbApp.createTable("Students", "name", colNameType);
			fail("Expected DBAppException for invalid data type not thrown");
		} catch (DBAppException e) {
			assertEquals("CREATE TABLE: Invalid Data Type InvalidType", e.getMessage());
		}
	}





	//CREATE INDEX

	@Test
	void testCreateIndexValid() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		try {

			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "java.lang.string");
			colNameType.put("age", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			dbApp.createTable("Students", "name", colNameType);



			// Create an index
			dbApp.createIndex("Students", "name", "NameIndex");

			// Check if the index is created
			boolean indexCreated = false;
			BufferedReader br = new BufferedReader(new FileReader(METADATA_PATH));
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(", ");
				if (parts[0].equals("Students") && parts[1].equals("name")) {
					if (parts[4].equals("NameIndex")) {
						indexCreated = true;
						break;
					}
				}
			}
			br.close();
			assertTrue(indexCreated, "Index should be created");
		} catch (DBAppException | IOException e) {
			fail("Exception thrown: " + e.getMessage());
		}
	}

	@Test
	void testCreateIndexTableNotExists() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		try {
			// Try to create an index on a table that does not exist
			dbApp.createIndex("NonExistingTable", "name", "NameIndex");
			fail("Expected DBAppException for non-existing table not thrown");
		} catch (DBAppException e) {
			assertEquals("Table does not exist", e.getMessage());
		}
	}

	@Test
	void testCreateIndexColumnNotExists() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		try {
			// Create a table with some data
			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "java.lang.string");
			colNameType.put("age", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			dbApp.createTable("Students", "name", colNameType);

			// Try to create an index on a column that does not exist
			dbApp.createIndex("Students", "nonExistingColumn", "NameIndex");
			fail("Expected DBAppException for non-existing column not thrown");
		} catch (DBAppException e) {
			assertEquals("Column doesn't exist", e.getMessage());
		}
	}
	@Test
	void testCreateIndexAlreadyExists() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		try {
			// Create a table with some data
			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "java.lang.string");
			colNameType.put("age", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			dbApp.createTable("Students", "name", colNameType);

			// Create an index
			dbApp.createIndex("Students", "name", "NameIndex");

			// Try to create another index on the same column
			dbApp.createIndex("Students", "name", "NameIndex2");
			fail("Expected DBAppException for index already exists not thrown");
		} catch (DBAppException e) {
			assertEquals("CREATE INDEX: Index already exists with the name NameIndex", e.getMessage());
		}
	}

	@Test
	void testCreateIndexDuplicateName() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		try {
			// Create a table with some data
			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "java.lang.string");
			colNameType.put("age", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			dbApp.createTable("Students", "name", colNameType);

			// Create an index
			dbApp.createIndex("Students", "name", "NameIndex");

			// Try to create another index with the same name
			dbApp.createIndex("Students", "age", "NameIndex");
			fail("Expected DBAppException for duplicate index name not thrown");
		} catch (DBAppException e) {
			assertEquals("CREATE INDEX: Duplicate Index Name.", e.getMessage());
		}
	}

	//INSERT

	//insert into a table that doesn't exist
	@Test
	void testInsertIntoNonExistentTable() {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("id", 1);
		assertThrows(DBAppException.class, () -> dbApp.insertIntoTable("nonExistentTable", htblColNameValue));
	}

	@Test
	void insertDuplicateClusteringKey() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp=new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

			colNameType.put("name", "java.lang.string");
			colNameType.put("id", "java.lang.integer");
			colNameType.put("gpa", "java.lang.double");
			dbApp.createTable("Students", "gpa", colNameType);


			Hashtable<String, Object> htblColNameValue = new Hashtable<>();
			htblColNameValue.put("name","John");
			htblColNameValue.put("id",2);
			htblColNameValue.put("gpa",2.3);
			dbApp.insertIntoTable("Students",htblColNameValue);

			htblColNameValue.clear();

			htblColNameValue.put("name","Mike");
			htblColNameValue.put("id",3);
			htblColNameValue.put("gpa",2.3);

			Exception e=assertThrows(DBAppException.class, () -> {
				dbApp.insertIntoTable("Students",htblColNameValue);
			});

			String expectedMessage = "CHECK DATA: Repeated clustering key value 2.3";
			String actualMessage=e.getMessage();

			assertEquals(expectedMessage,actualMessage);

	}
	//invalid datatype

	@Test
	public void testInsertMoreColumnFields() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.string");
		colNameType.put("id", "java.lang.integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "gpa", colNameType);


		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",2);
		htblColNameValue.put("gpa",2.3);
		htblColNameValue.put("age",16);

		Exception e=assertThrows(DBAppException.class, () -> dbApp.insertIntoTable("Students",htblColNameValue));

		String expectedMessage = "CHECK DATATYPES: You entered more column fields that don't exist in the table";
		String actualMessage=e.getMessage();

		assertEquals(expectedMessage,actualMessage);
	}
	@Test
	public void insertInvalidDatatype() throws DBAppException{
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.string");
		colNameType.put("id", "java.lang.integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "gpa", colNameType);


		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",3);
		htblColNameValue.put("gpa","Two");

		Exception e=assertThrows(DBAppException.class, () -> dbApp.insertIntoTable("Students",htblColNameValue));

		String expectedMessage = "CHECK DATATYPES: Invalid datatype for gpa. You have entered java.lang.String";
		String actualMessage=e.getMessage();

		assertEquals(expectedMessage,actualMessage);


	}
	@Test
	public void insertCheckIndexedProperly() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);


		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",3);
		htblColNameValue.put("gpa",2.34);

		dbApp.insertIntoTable("Students",htblColNameValue);
		dbApp.createIndex("Students","gpa","gpaIndex");

		bplustree bt=dbApp.deserializeIndex("gpaIndex");
		Vector<String> result=bt.search(2.34);
		bt.printTree();

		assert result.contains("1");   //Page number
//		System.out.print(result);

	}



	//Check user inputs value for clustering key (clustering key can't be null)
	@Test
	public void checkClusteringKeyNotNull() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.string");
		colNameType.put("id", "java.lang.integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);



		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		//htblColNameValue.put("id",null);
		htblColNameValue.put("gpa",2.34);


		Exception e=assertThrows(DBAppException.class, () -> dbApp.insertIntoTable("Students",htblColNameValue));
		String expectedMessage=("CHECK DATATYPES: Missing column entry for id");
		String actualMessage=e.getMessage();
		assertEquals(expectedMessage,actualMessage);


	}

	@Test
	public void handleShiftingCorrectly() throws DBAppException {
		//MAKE MAXIMUMROWSCOUNTINPAGE 20 FOR THIS TEST
		emptyMetadataFile(METADATA_PATH);
	DBApp dbApp = new DBApp();
	Hashtable<String, String> colNameType = new Hashtable<>();

	colNameType.put("name", "java.lang.string");
	colNameType.put("id", "java.lang.integer");
	dbApp.createTable("Students", "id", colNameType);

	for(int i=0;i<21;i++){
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John "+i);
		htblColNameValue.put("id",i);
		dbApp.insertIntoTable("Students",htblColNameValue);
		htblColNameValue.clear();
	}
	//Check using maximum rows count in page 20
		Table t=dbApp.deserializeTable("Students");
		Vector<String> pages=t.getStrPages();

		Page lastPage=dbApp.deserializePage(pages.get(pages.size()-1));

		//Check 1: there is only one element in the new page
		assertEquals(1,lastPage.getRecords().size());
		//Check 2: the id in the newest page is the largest (20)
		assertEquals(20,lastPage.getRecords().get(0).getHtblTuple().get("id"));
		//Check 3: Check last tuple in the first page is 19 and first tuple is 0
		Page firstPage=dbApp.deserializePage(pages.get(0));
		assertEquals(0,firstPage.getRecords().get(0).getHtblTuple().get("id"));
		assertEquals(19,firstPage.getRecords().get(firstPage.getRecords().size()-1).getHtblTuple().get("id"));
		//Check 4: check that the records are sorted by clustering key
		for (int i = 0; i < firstPage.getRecords().size() - 1; i++) {
			assertEquals(1, (int)firstPage.getRecords().get(i + 1).getHtblTuple().get("id") - (int)firstPage.getRecords().get(i).getHtblTuple().get("id"));
		}




	}



	//DELETE:

	@Test
	public void deleteFromTableThatDoesntExist(){
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("id", 1);
		assertThrows(DBAppException.class, () -> dbApp.deleteFromTable("nonExistentTable", htblColNameValue));
	}
	@Test
	public void deleteOneRecordWithoutIndex() throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.string");
		colNameType.put("id", "java.lang.integer");

		dbApp.createTable("Students", "id", colNameType);


		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",1);

		dbApp.insertIntoTable("Students",htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name","John");
		htblColNameValue.put("id",2);

		dbApp.insertIntoTable("Students",htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name","John");
		htblColNameValue.put("id",2);


		dbApp.deleteFromTable("Students", htblColNameValue);
		Page p=dbApp.deserializePage("Students_1");


		Vector<Tuple> records= p.getRecords();

		assert records.size()==1;

		assert (int) records.get(0).getHtblTuple().get("id") ==1;




	}
	@Test
	public void deleteOneRecordWithIndex() throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");

		dbApp.createTable("Students", "id", colNameType);

		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",1);

		dbApp.insertIntoTable("Students",htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name","John");
		htblColNameValue.put("id",2);

		dbApp.insertIntoTable("Students",htblColNameValue);
		htblColNameValue.clear();

		dbApp.createIndex("Students","id","idIndex");
		bplustree bt=dbApp.deserializeIndex("idIndex");
		bt.printTree();


		Page p=dbApp.deserializePage("Students_1");
		System.out.println("Page Size Before: "+p.getNumOfEntries());

		htblColNameValue.clear();
		htblColNameValue.put("id", 1);
		dbApp.deleteFromTable("Students", htblColNameValue);

		bplustree bt2=dbApp.deserializeIndex("idIndex");

		Page p2=dbApp.deserializePage("Students_1");

		System.out.println("Page Size After: "+p2.getNumOfEntries());

		bt2.printTree();

		System.out.println(bt2.search(1));

		Vector<Tuple> r= p2.getRecords();

		assert r.size()==1;
		assert (int) r.get(0).getHtblTuple().get("id") ==2;


		assert bt2.search(1) == null;
		// Assert that the result does not contain the deleted record
//		assert result.size()==0;   //This doesn't delete from the bplustree?
	}

	@Test
	public void checkFileGetsDeletedWhenLastTupleIsRemoved() throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);

		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");

		dbApp.createTable("Students", "id", colNameType);

		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",1);

		dbApp.insertIntoTable("Students",htblColNameValue);
		htblColNameValue.clear();

		String filePath = PAGES_DIR + "Students_1.ser";
		File file = new File(filePath);

		// Assert that the file exists after insertion
		assertTrue(file.exists());

		htblColNameValue.put("id", 1);
		dbApp.deleteFromTable("Students", htblColNameValue);

		// Assert that the file is deleted after the last tuple is removed
		assertFalse(file.exists());

	}

	@Test
	public void handleDeletingMoreThanOneRecordWithoutIndex() throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");

		dbApp.createTable("Students", "id", colNameType);


		int count=0;
		for (int i=0;i<10;i++){
			Hashtable<String, Object> htblColNameValue2 = new Hashtable<>();
			htblColNameValue2.put("name","John");
			htblColNameValue2.put("id",i);
			dbApp.insertIntoTable("Students",htblColNameValue2);
			count++;
//			htblColNameValue.clear();
		}

		System.out.print("count: "+  count);
//		htblColNameValue.clear();
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",2);
		dbApp.deleteFromTable("Students",htblColNameValue);

Table t = dbApp.deserializeTable("Students");

Vector<String> tableVec = t.getStrPages();
//		Page p = dbApp.deserializePage("Students_1");
//		Vector<Tuple> t = p.getRecords();
		count = 0;
		for(int i=0;i<tableVec.size();i++){
			Page p = dbApp.deserializePage(tableVec.get(i));
			Vector<Tuple> pageVec = p.getRecords();
			for(int j=0; j<pageVec.size();j++){

				assert !(pageVec.get(j).getHtblTuple().get("id").equals(2) && pageVec.get(j).getHtblTuple().get("name").equals("John"));
			}
			count+= pageVec.size();
//			assert !(t.get(i).getHtblTuple().get("id").equals(2) && t.get(i).getHtblTuple().get("name").equals("John"));
		}

//		System.out.print("Vector size"+ t.size());


		assert count ==9;



	}
	@Test
	public void handleDeleteNonExistentValues() throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();


		//Test deleting non-existent tuple (Non-Indexed)
		for (int i=0;i<10;i++){
			htblColNameValue.put("name","John"+i);
			htblColNameValue.put("id",i);
			dbApp.insertIntoTable("Students",htblColNameValue);
			htblColNameValue.clear();
		}

		Page p1 = dbApp.deserializePage("Students_1");
		Vector<Tuple> t1 = p1.getRecords();

		htblColNameValue.put("name","John"+20);
		htblColNameValue.put("id",20);

		dbApp.deleteFromTable("Students", htblColNameValue);

		Page p2 = dbApp.deserializePage("Students_1");
		Vector<Tuple> t2 = p2.getRecords();

		assert t1.size()==t2.size();





	}
	@Test
	public void handleIncorrectDatatypeInputWhenDeleting() throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		dbApp.createTable("Students", "id", colNameType);


		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		dbApp.insertIntoTable("Students", htblColNameValue);


		htblColNameValue.clear();
		htblColNameValue.put("name", 5);


		DBAppException thrown = assertThrows(
				DBAppException.class,
				() -> dbApp.deleteFromTable("Students", htblColNameValue),
				"Expected deleteFromTable to throw DBAppException, but it didn't"
		);


		String expectedMessage = "CHECK DATA: Invalid datatype for name. You have entered java.lang.Integer";
		assertEquals(expectedMessage, thrown.getMessage());
	}

	@Test
	public void deleteIntersectionDoesntExist() throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();


		for (int i=0;i<10;i++){
			htblColNameValue.put("name","John "+i);
			htblColNameValue.put("id",i);
			dbApp.insertIntoTable("Students",htblColNameValue);
			htblColNameValue.clear();
		}
		dbApp.createIndex("Students","name","nameIndex");
		htblColNameValue.put("name","John");
		htblColNameValue.put("id",12);
		dbApp.deleteFromTable("Students",htblColNameValue);
		Table t=dbApp.deserializeTable("Students");

		Vector<String> tableVec = t.getStrPages();

		System.out.println("TV:"+ tableVec.size());
		for(int i=0;i<tableVec.size();i++){
			Page p = dbApp.deserializePage(tableVec.get(i));
			System.out.println(p.getRecords().size());
				//I think this is wrong should adjust this test

			}






		}




	//UPDATE:
	@Test
	public void checkUpdatedCorrectlyWithoutIndex() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);


		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students",htblColNameValue);

		htblColNameValue.clear();

		Page p=dbApp.deserializePage("Students_1");
		assertTrue(p.getNumOfEntries()==1);
		assert(p.getRecords().get(0).getHtblTuple().get("name").equals("John"));

		htblColNameValue.put("name", "Alex");
		htblColNameValue.put("gpa", 3.5);
		System.out.println(p.getRecords().get(0).getHtblTuple().get("name"));
		System.out.println(p.getRecords().get(0).getHtblTuple().get("gpa"));
		dbApp.updateTable("Students", "1", htblColNameValue);
		p=dbApp.deserializePage("Students_1");
		System.out.println(p.getRecords().get(0).getHtblTuple().get("name"));
		System.out.println(p.getRecords().get(0).getHtblTuple().get("gpa"));
		htblColNameValue.clear();

		Page p2=dbApp.deserializePage("Students_1");
		assert(p2.getRecords().get(0).getHtblTuple().get("name").equals("Alex"));



	}
	@Test
	public void checkUpdatedCorrectlyWithOneIndex() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);

		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();


		dbApp.createIndex("Students", "name", "nameIndex");

		bplustree nameIndexBefore = dbApp.deserializeIndex("nameIndex");
		Vector<String> resultBefore = nameIndexBefore.search("John");
		nameIndexBefore.printTree();
		assertTrue(resultBefore.contains("1"));

		htblColNameValue.put("name", "Alex");
		htblColNameValue.put("gpa", 3.5);
		dbApp.updateTable("Students", "1", htblColNameValue);
		htblColNameValue.clear();
		bplustree nameIndexAfter = dbApp.deserializeIndex("nameIndex");
		Vector<String> resultAfter = nameIndexAfter.search("John");
		assertNull(resultAfter);
		Vector<String> resultAfter2 = nameIndexAfter.search("Alex");
		nameIndexAfter.printTree();
		assertTrue(resultAfter2.contains("1")&&resultAfter2.size()==1);



	}

	@Test
	public void checkUpdatedCorrectlyWithMixedIndex() throws DBAppException {
		//This works when the maximumRowsCountInPage is 3, not 20
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);

		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 3.2);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		dbApp.createIndex("Students", "name", "nameIndex");

		bplustree nameIndexBefore = dbApp.deserializeIndex("nameIndex");
		Vector<String> resultBefore = nameIndexBefore.search("John");
		nameIndexBefore.printTree();
		assertTrue(resultBefore.contains("1"));

		htblColNameValue.put("name", "Alex");
		htblColNameValue.put("gpa", 3.5);
		dbApp.updateTable("Students", "1", htblColNameValue);
		htblColNameValue.clear();
		bplustree nameIndexAfter = dbApp.deserializeIndex("nameIndex");
		Vector<String> resultAfter = nameIndexAfter.search("John");
		assertNull(resultAfter);
		Vector<String> resultAfter2 = nameIndexAfter.search("Alex");
		nameIndexAfter.printTree();
		assertTrue(resultAfter2.contains("1")&&resultAfter2.size()==1);


	}

	@Test
	public void checkClusteringKeyCantBeUpdated() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 2);

		Exception e = assertThrows(DBAppException.class, () -> dbApp.updateTable("Students", "1", htblColNameValue));
		String expectedMessage = "UPDATE TABLE: Clustering key's value can't be updated";
		String actualMessage = e.getMessage();
		assertEquals(expectedMessage, actualMessage);


	}
	@Test
	public void updateOnNonExistentTable() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		Exception e = assertThrows(DBAppException.class, () -> dbApp.updateTable("NonExistentTable", "1", htblColNameValue));
		String expectedMessage = "UPDATE TABLE: Table doesn't exist";
		String actualMessage = e.getMessage();
		assertEquals(expectedMessage, actualMessage);

	}

	@Test
	public void updateHandleInvalidDataType() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();
		htblColNameValue.put("name", 5);
		htblColNameValue.put("gpa", 2.5);

		Exception e = assertThrows(DBAppException.class, () -> dbApp.updateTable("Students", "1", htblColNameValue));
		String expectedMessage = "CHECK DATATYPES: Invalid datatype for name. You have entered java.lang.Integer";
		String actualMessage = e.getMessage();
		assertEquals(expectedMessage, actualMessage);


	}
	@Test
	public void updateClusteringKeyDoesntExist() throws DBAppException {
	emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();
		htblColNameValue.put("name","Mike");
		Exception e = assertThrows(DBAppException.class, () -> dbApp.updateTable("Students","2", htblColNameValue));
		String expectedMessage = "UPDATE TABLE: Record not found in table";
		String actualMessage = e.getMessage();
		assertEquals(expectedMessage, actualMessage);


	}
	@Test
	public void updateEnterExtraColumnFields() throws DBAppException {
	emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();
		htblColNameValue.put("name","Mike");
		htblColNameValue.put("gpa", 2.5);
		htblColNameValue.put("age",47);
		Exception e = assertThrows(DBAppException.class, () -> dbApp.updateTable("Students","2", htblColNameValue));
		String expectedMessage = "CHECK DATATYPES: You entered more column fields that don't exist in the table";
		String actualMessage = e.getMessage();
		assertEquals(expectedMessage, actualMessage);


	}

	@Test
	public void updateRecordWithMultipleIndices() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);

		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("age", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);

		dbApp.createIndex("Students", "name", "nameIndex");

		dbApp.createIndex("Students", "age", "ageIndex");

		Hashtable<String, Object> initialRecord = new Hashtable<>();
		initialRecord.put("name", "John");
		initialRecord.put("id", 1);
		initialRecord.put("age", 25);
		initialRecord.put("gpa", 3.5);
		dbApp.insertIntoTable("Students", initialRecord);

		System.out.println("Trees before update:");
		System.out.println("Name Index:");
		dbApp.deserializeIndex("nameIndex").printTree();
		System.out.println("Age Index:");
		dbApp.deserializeIndex("ageIndex").printTree();

		Hashtable<String, Object> updatedValues = new Hashtable<>();
		updatedValues.put("name", "Alex");
		updatedValues.put("age", 30);
		dbApp.updateTable("Students", "1", updatedValues);

		System.out.println("Trees after update:");
		bplustree nameI=dbApp.deserializeIndex("nameIndex");
		bplustree AgeI=dbApp.deserializeIndex("AgeIndex");
		nameI.printTree();
		AgeI.printTree();


		Vector<String> nameIndex=nameI.search("Alex");
		Vector<String> AgeIndex=AgeI.search(30);
		assertTrue(nameIndex.contains("1") && nameIndex.size() == 1);
		assertTrue(AgeIndex.contains("1") && AgeIndex.size() == 1);



	}

	//SELECT:


	//WITHOUT INDEX:


	@Test
	public void handleExceptions(){
	//PLACEHOLDER

	}

	@Test
	public void testSelectWithoutIndexOneRecord() throws DBAppException {
	emptyMetadataFile(METADATA_PATH);
	DBApp dbApp = new DBApp();
	Hashtable<String, String> colNameType = new Hashtable<>();
	colNameType.put("name", "java.lang.String");
	colNameType.put("id", "java.lang.Integer");
	colNameType.put("gpa", "java.lang.double");
	dbApp.createTable("Students","id",colNameType);
	Hashtable<String, Object> htblColNameValue = new Hashtable<>();
	htblColNameValue.put("name", "John");
	htblColNameValue.put("id", 1);
	htblColNameValue.put("gpa", 2.45);
	dbApp.insertIntoTable("Students", htblColNameValue);

	htblColNameValue.clear();
	htblColNameValue.put("name", "Jane");
	htblColNameValue.put("id", 2);
	htblColNameValue.put("gpa", 3.50);
	dbApp.insertIntoTable("Students", htblColNameValue);
	htblColNameValue.clear();

	SQLTerm[] arrSQLTerms = new SQLTerm[1];
	arrSQLTerms[0] = new SQLTerm("Students", "gpa", ">", 2.5);
	String[] strarrOperators = {};
	Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

	assertTrue(result.hasNext(), "There should be at least one result.");

	int count = 0;
	while (result.hasNext()) {
		Tuple tuple = result.next();
		count++;

		double gpa = (double) tuple.getHtblTuple().get("gpa");
		assertTrue(gpa > 2.5, "GPA should be greater than 2.5.");

		System.out.println("Record: " + tuple);
	}
	assertEquals(1, count, "There should be exactly one record with GPA greater than 2.5.");

}
	@Test
	public void testSelectWithoutIndexMoreThanOneRecord() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Jane");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 3.50);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", 2.15);
		dbApp.insertIntoTable("Students", htblColNameValue);

		SQLTerm[] arrSQLTerms = new SQLTerm[2];
		arrSQLTerms[0] = new SQLTerm("Students", "name", "=", "John");
		arrSQLTerms[1] = new SQLTerm("Students", "gpa", "<", 3.1);
		String[] strarrOperators = {"OR"};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		// Assert that there should be at least one result
		assertTrue(result.hasNext(), "There should be at least one result.");

		// Count the number of results returned
		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			// Check that the GPA is less than 2.4
			double gpa = (double) tuple.getHtblTuple().get("gpa");
			assertTrue(gpa < 3.1, "GPA should be less than 2.4.");

			// Print each record for visual verification
			System.out.println("Record: " + tuple);
		}

		// Assert the number of results
		assertEquals(2, count, "There should be exactly two records matching the conditions.");
	}

	@Test
	public void testSelectMultipleOperators() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Jane");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 3.50);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", 2.15);
		dbApp.insertIntoTable("Students", htblColNameValue);

		SQLTerm[] arrSQLTerms = new SQLTerm[3];
		arrSQLTerms[0] = new SQLTerm("Students", "name", "=", "John");
		arrSQLTerms[1] = new SQLTerm("Students", "gpa", "<", 3.1);
		arrSQLTerms[2] = new SQLTerm("Students", "name", "=", "Mike");

		String[] strarrOperators = {"OR", "AND"};

		for (String operator : strarrOperators) {
			Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms,strarrOperators );

			assertTrue(result.hasNext(), "There should be at least one result for operator: " + operator);

			int count = 0;
			while (result.hasNext()) {
				Tuple tuple = result.next();
				count++;

				System.out.println("Record for operator " + operator + ": " + tuple);
			}

			assertEquals(1, count, "There should be exactly one record for operator: " + operator);
		}
	}

	@Test
	public void testSelectExactValues() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.5);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Jane");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 3.50);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		SQLTerm[] arrSQLTerms = new SQLTerm[1];

		//Test for type Double
		arrSQLTerms[0] = new SQLTerm("Students", "gpa", "=", 2.5);
		String[] strarrOperators = {};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		assertTrue(result.hasNext(), "There should be at least one result.");

		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			double gpa = (double) tuple.getHtblTuple().get("gpa");
			assert(gpa == 2.5);

			System.out.println("Record: " + tuple);
		}
		assertEquals(1, count, "There should be exactly one record with GPA greater than 2.5.");

		//Test for type String
		SQLTerm[] arrSQLTerms2 = new SQLTerm[1];
		arrSQLTerms[0] = new SQLTerm("Students", "name", "=", "Jane");
		String[] strarrOperators2 = {};
		Iterator<Tuple> result2 = dbApp.selectFromTable(arrSQLTerms, strarrOperators2);

		assertTrue(result2.hasNext(), "There should be at least one result.");

		int count2 = 0;
		while (result2.hasNext()) {
			Tuple tuple = result2.next();
			count2++;

			String name = (String) tuple.getHtblTuple().get("name");
			assert(name.equals("Jane"));

			System.out.println("Record: " + tuple);
		}
		assertEquals(1, count2, "There should be exactly one record with name Jane");

		//Test for type Int

		SQLTerm[] arrSQLTerms3 = new SQLTerm[1];
		arrSQLTerms[0] = new SQLTerm("Students", "id", "=", 2);
		String[] strarrOperators3 = {};
		Iterator<Tuple> result3 = dbApp.selectFromTable(arrSQLTerms, strarrOperators3);

		assertTrue(result3.hasNext(), "There should be at least one result.");

		int count3 = 0;
		while (result3.hasNext()) {
			Tuple tuple = result3.next();
			count3++;

			int id = (int) tuple.getHtblTuple().get("id");
			assert(id==2);

			System.out.println("Record: " + tuple);
		}
		assertEquals(1, count3);






	}
	@Test
	public void testRangedValuesNormal() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Jane");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 3.50);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", 4.50);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		//GREATER THAN
		SQLTerm[] arrSQLTerms = new SQLTerm[1];
		arrSQLTerms[0] = new SQLTerm("Students", "id", ">", 1);
		String[] strarrOperators = {};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		assertTrue(result.hasNext(), "There should be at least one result.");

		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			double id = (double) tuple.getHtblTuple().get("gpa");
			assertTrue(id > 2, "ID should be greater than 1.");

			System.out.println("Record: " + tuple);
		}
		assertEquals(2, count);


		//GREATER THAN OR EQUAL
		SQLTerm[] arrSQLTerms2 = new SQLTerm[1];
		arrSQLTerms2[0] = new SQLTerm("Students", "id", ">=", 1);
		String[] strarrOperators2 = {};
		Iterator<Tuple> result2 = dbApp.selectFromTable(arrSQLTerms2, strarrOperators2);

		assertTrue(result2.hasNext());

		int count2 = 0;
		while (result2.hasNext()) {
			Tuple tuple = result2.next();
			count2++;

			int id = (int) tuple.getHtblTuple().get("id");
			assertTrue(id >= 1);

			System.out.println("Record: " + tuple);
		}
		assertEquals(3, count2);

		//LESS THAN

		SQLTerm[] arrSQLTerms3 = new SQLTerm[1];
		arrSQLTerms3[0] = new SQLTerm("Students", "gpa", "<", 4.5);
		String[] strarrOperators3 = {};
		Iterator<Tuple> result3 = dbApp.selectFromTable(arrSQLTerms3, strarrOperators3);

		assertTrue(result3.hasNext());

		int count3 = 0;
		while (result3.hasNext()) {
			Tuple tuple = result3.next();
			count3++;

			double gpa = (double) tuple.getHtblTuple().get("gpa");
			assertTrue(gpa < 4.5);

			System.out.println("Record: " + tuple);
		}
		assertEquals(2, count3);

		//LESS THAN OR EQUAL

		SQLTerm[] arrSQLTerms4 = new SQLTerm[1];
		arrSQLTerms4[0] = new SQLTerm("Students", "gpa", "<=", 4.5);
		String[] strarrOperators4 = {};
		Iterator<Tuple> result4 = dbApp.selectFromTable(arrSQLTerms4, strarrOperators4);

		assertTrue(result4.hasNext());

		int count4 = 0;
		while (result4.hasNext()) {
			Tuple tuple = result4.next();
			count4++;

			double gpa = (double) tuple.getHtblTuple().get("gpa");
			assertTrue(gpa <= 4.5);

			System.out.println("Record: " + tuple);
		}
		assertEquals(3, count4);


	}
	@Test
	public void testRangedValuesWithBounds() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);


		//insert 10 records
		double gpaValue=2.5;
		for(int i=1;i<11;i++){
			Hashtable<String, Object> htblColNameValue = new Hashtable<>();
			htblColNameValue.put("name","John "+i);
			htblColNameValue.put("id",i);
			htblColNameValue.put("gpa",gpaValue);gpaValue+=0.5;
			dbApp.insertIntoTable("Students",htblColNameValue);
			htblColNameValue.clear();
		}



		SQLTerm[] arrSQLTerms = new SQLTerm[1];
		arrSQLTerms[0] = new SQLTerm("Students", "id", "<", 10);
		String[] strarrOperators = {};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		assertTrue(result.hasNext(), "There should be at least one result.");

		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			int id = (int) tuple.getHtblTuple().get("id");
			assertTrue(id < 10);

			System.out.println("Record: " + tuple);
		}
		assertEquals(9, count);


	}

	@Test
	public void testRangedValuesOutOfBounds() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);


		//insert 10 records
		double gpaValue=2.5;
		for(int i=1;i<11;i++){
			Hashtable<String, Object> htblColNameValue = new Hashtable<>();
			htblColNameValue.put("name","John "+i);
			htblColNameValue.put("id",i);
			htblColNameValue.put("gpa",gpaValue);gpaValue+=0.5;
			dbApp.insertIntoTable("Students",htblColNameValue);
			htblColNameValue.clear();
		}



		SQLTerm[] arrSQLTerms = new SQLTerm[1];
		arrSQLTerms[0] = new SQLTerm("Students", "id", ">", 10);
		String[] strarrOperators = {};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		//assertTrue(result.hasNext(), "There should be at least one result.");

		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			int id = (int) tuple.getHtblTuple().get("id");
			System.out.println(id);

			System.out.println("Record: " + tuple);
		}
		assertEquals(0, count);
	}


	//SQLTerms must have the same table name
	@Test
	public void selectCheckOperatorsInSameTable() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Jane");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 3.50);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", 2.15);
		dbApp.insertIntoTable("Students", htblColNameValue);

		SQLTerm[] arrSQLTerms = new SQLTerm[3];
		arrSQLTerms[0] = new SQLTerm("Students", "name", "=", "John");
		arrSQLTerms[1] = new SQLTerm("Students", "gpa", "<", 3.1);
		arrSQLTerms[2] = new SQLTerm("Students2", "name", "=", "Mike");

		String[] strarrOperators = {"OR", "AND"};

		assertThrows(DBAppException.class, () -> {
			Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);
		});



	}
	@Test
	public void checkANDOperator() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);


		//insert 10 records
		double gpaValue=2.5;
		for(int i=1;i<11;i++){
			Hashtable<String, Object> htblColNameValue = new Hashtable<>();
			htblColNameValue.put("name","John "+i);
			htblColNameValue.put("id",i);
			htblColNameValue.put("gpa",gpaValue);gpaValue+=0.5;
			dbApp.insertIntoTable("Students",htblColNameValue);
			htblColNameValue.clear();
		}



		SQLTerm[] arrSQLTerms = new SQLTerm[2];
		arrSQLTerms[0] = new SQLTerm("Students", "id", ">", 5);
		arrSQLTerms[1] = new SQLTerm("Students", "gpa", "<", 6.5);
		String[] strarrOperators = {"AND"};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		assertTrue(result.hasNext(), "There should be at least one result.");

		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			int id = (int) tuple.getHtblTuple().get("id");
			double gpa = (double) tuple.getHtblTuple().get("gpa");

			//System.out.println(id);

			System.out.println("Record: " + tuple);
		}
		assertEquals(3, count);


	}
	@Test
	public void checkXOROperator() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);


		//insert 10 records
		double gpaValue=2.5;
		for(int i=1;i<11;i++){
			Hashtable<String, Object> htblColNameValue = new Hashtable<>();
			htblColNameValue.put("name","John");
			htblColNameValue.put("id",i);
			htblColNameValue.put("gpa",gpaValue);gpaValue+=0.5;
			dbApp.insertIntoTable("Students",htblColNameValue);
			htblColNameValue.clear();
		}




		SQLTerm[] arrSQLTerms = new SQLTerm[2];
		arrSQLTerms[0] = new SQLTerm("Students", "id", "=", 1);
		arrSQLTerms[1] = new SQLTerm("Students", "gpa", ">=", 2.5);
		String[] strarrOperators = {"XOR"};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		assertTrue(result.hasNext(), "There should be at least one result.");

		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			int id = (int) tuple.getHtblTuple().get("id");
			double gpa = (double) tuple.getHtblTuple().get("gpa");

			//System.out.println(id);

			System.out.println("Record: " + tuple);
		}
		assertEquals(9, count);


	}


	@Test
	public void checkOROperator() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students","id",colNameType);


		//insert 10 records
		double gpaValue=2.5;
		for(int i=1;i<11;i++){
			Hashtable<String, Object> htblColNameValue = new Hashtable<>();
			htblColNameValue.put("name","John "+i);
			htblColNameValue.put("id",i);
			htblColNameValue.put("gpa",gpaValue);gpaValue+=0.5;
			dbApp.insertIntoTable("Students",htblColNameValue);
			htblColNameValue.clear();
		}



		SQLTerm[] arrSQLTerms = new SQLTerm[2];
		arrSQLTerms[0] = new SQLTerm("Students", "id", "=", 5);
		arrSQLTerms[1] = new SQLTerm("Students", "gpa", "<", 3.5);
		String[] strarrOperators = {"OR"};
		Iterator<Tuple> result = dbApp.selectFromTable(arrSQLTerms, strarrOperators);

		assertTrue(result.hasNext(), "There should be at least one result.");

		int count = 0;
		while (result.hasNext()) {
			Tuple tuple = result.next();
			count++;

			int id = (int) tuple.getHtblTuple().get("id");
			double gpa = (double) tuple.getHtblTuple().get("gpa");

			//System.out.println(id);

			System.out.println("Record: " + tuple);
		}
		assertEquals(3, count);


	}
	@Test
	public void testSelectMultipleOperatorsWithPrecedence() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();
		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 4.0);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Jane");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 3.50);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", 2.15);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();
		htblColNameValue.put("name", "Luke");
		htblColNameValue.put("id", 4);
		htblColNameValue.put("gpa", 7.3);
		dbApp.insertIntoTable("Students", htblColNameValue);

		SQLTerm[] arrSQLTerms = new SQLTerm[4];
		arrSQLTerms[0] = new SQLTerm("Students", "id", "<", 3);
		arrSQLTerms[1] = new SQLTerm("Students", "gpa", "<", 3.1);
		arrSQLTerms[2] = new SQLTerm("Students", "name", "=", "Mike");
		arrSQLTerms[3] = new SQLTerm("Students", "gpa", "<", 2.5);


		String[] strarrOperators = {"OR","XOR","AND"};

		for (String operator : strarrOperators) {
			Iterator<Tuple> result = dbApp.selectFromTableWithPrecedence(arrSQLTerms,strarrOperators);

			assertTrue(result.hasNext(), "There should be at least one result for operator: " + operator);

			int count = 0;
			while (result.hasNext()) {
				Tuple tuple = result.next();
				count++;

				System.out.println("Record for operator " + operator + ": " + tuple);
			}

			assertEquals(2, count );
		}
	}



	@Test
	public void checkBonusInsertandUpdateCorrectly() throws DBAppException {
		DBApp dbapp=new DBApp();
	Page p=dbapp.deserializePage("Sports_1");
	//Page p2=dbapp.deserializePage(("Food_2"));

//	System.out.println(p.getRecords().size());
//	System.out.println(p.getRecords().get(0).getHtblTuple().get("street"));
//	System.out.println(p.getRecords().get(0).getHtblTuple());

	for (int i=0;i<p.getRecords().size();i++){
		System.out.println(p.getRecords().get(i).getHtblTuple());
	}
//		for (int i=0;i<p2.getRecords().size();i++){
//			System.out.println(p2.getRecords().get(i).getHtblTuple());
//		}



	//System.out.println(p.getRecords().get(1).getHtblTuple());


		//assert(p.getRecords().size()==2);

	}





}


