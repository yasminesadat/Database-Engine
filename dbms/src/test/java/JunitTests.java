
import bPlusTree.bplustree;
import dbms.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Vector;

public class JunitTests {
	private static DBApp engine;
	private static String newTableName;
	// private static final String id = "id";
	// private static final String name = "name";
	// private static final String gpa = "gpa";
	// private static final String TEST_NAME = "Abdo";
	// private static final double TEST_GPA = 1.8;
	// private static final String STRING_DATA_TYPE_NAME = "java.lang.String";
	// private static final String INTEGER_DATA_TYPE_NAME = "java.lang.Integer";
	// private static final String DOUBLE_DATA_TYPE_NAME = "java.lang.Double";
	private static final String METADATA_PATH = "E:\\Semester 6\\Database 2\\Project\\GitHub\\DBEngineWithMaven\\dbms\\src\\main\\resources\\metadata.csv";
	private static final String CONFIG_FILE_PATH = "E:\\Semester 6\\Database 2\\Project\\GitHub\\DBEngineWithMaven\\dbms\\src\\main\\resources\\DBApp.config";
	private static final String TABLES_DIR = "E:\\Semester 6\\Database 2\\Project\\GitHub\\DBEngineWithMaven\\dbms\\src\\main\\resources\\Tables\\";
	private static final String PAGES_DIR = "E:\\Semester 6\\Database 2\\Project\\GitHub\\DBEngineWithMaven\\dbms\\src\\main\\resources\\Pages\\";
	private static final String INDICES_DIR = "E:\\Semester 6\\Database 2\\Project\\GitHub\\DBEngineWithMaven\\dbms\\src\\main\\resources\\Indices\\";

	// Helper method to read the metadata file
	public String readMetadataFile() throws IOException {
		Path filePath = Paths.get(METADATA_PATH);
		return Files.readString(filePath);
	}

	// Helper method to empty metadata file
	public void emptyMetadataFile(String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath, false); // the true will append the new data
			fw.write(""); // erase all content
			fw.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	// NOTE: Make createTable and deleteFromTable throw DBAppException only

	// CREATE TABLE

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
		try {

			Hashtable<String, String> colNameType = new Hashtable<>();
			colNameType.put("name", "java.lang.String");
			colNameType.put("age", "java.lang.Integer");
			colNameType.put("gpa", "java.lang.Double");
			dbApp.createTable("Students", "name", colNameType);

			dbApp.createTable("Students", "name", colNameType);

			fail("Expected DBAppException for existing table name not thrown");
		} catch (DBAppException e) {
			assertEquals("CREATE TABLE: Table already exists", e.getMessage());
		}
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

	// CREATE INDEX

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

	// INSERT

	// insert into a table that doesn't exist
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
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.string");
		colNameType.put("id", "java.lang.integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "gpa", colNameType);

		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 2.3);
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();

		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", 2.3);

		Exception e = assertThrows(DBAppException.class, () -> {
			dbApp.insertIntoTable("Students", htblColNameValue);
		});

		String expectedMessage = "CHECK DATA: Repeated clustering key value 2.3";
		String actualMessage = e.getMessage();

		assertEquals(expectedMessage, actualMessage);

	}
	// invalid datatype

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
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 2);
		htblColNameValue.put("gpa", 2.3);
		htblColNameValue.put("age", 16);

		Exception e = assertThrows(DBAppException.class, () -> dbApp.insertIntoTable("Students", htblColNameValue));

		String expectedMessage = "CHECK DATATYPES: You entered more column fields that don't exist in the table";
		String actualMessage = e.getMessage();

		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void insertInvalidDatatype() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.string");
		colNameType.put("id", "java.lang.integer");
		colNameType.put("gpa", "java.lang.double");
		dbApp.createTable("Students", "gpa", colNameType);

		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", "Two");

		Exception e = assertThrows(DBAppException.class, () -> dbApp.insertIntoTable("Students", htblColNameValue));

		String expectedMessage = "CHECK DATATYPES: Invalid datatype for gpa. You have entered java.lang.String";
		String actualMessage = e.getMessage();

		assertEquals(expectedMessage, actualMessage);

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
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 3);
		htblColNameValue.put("gpa", 2.34);

		dbApp.insertIntoTable("Students", htblColNameValue);
		dbApp.createIndex("Students", "gpa", "gpaIndex");

		bplustree bt = dbApp.deserializeIndex("gpaIndex");
		Vector<String> result = bt.search(2.34);
		bt.printTree();

		assert result.contains("1"); // Page number
		// System.out.print(result);

	}

	// Check user inputs value for clustering key (clustering key can't be null)
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
		htblColNameValue.put("name", "John");
		// htblColNameValue.put("id",null);
		htblColNameValue.put("gpa", 2.34);

		Exception e = assertThrows(DBAppException.class, () -> dbApp.insertIntoTable("Students", htblColNameValue));
		String expectedMessage = ("CHECK DATATYPES: Missing column entry for id");
		String actualMessage = e.getMessage();
		assertEquals(expectedMessage, actualMessage);

	}

	@Test
	public void handleShiftingCorrectly() throws DBAppException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.string");
		colNameType.put("id", "java.lang.integer");
		dbApp.createTable("Students", "id", colNameType);

		for (int i = 0; i < 21; i++) {
			Hashtable<String, Object> htblColNameValue = new Hashtable<>();
			htblColNameValue.put("name", "John " + i);
			htblColNameValue.put("id", i);
			dbApp.insertIntoTable("Students", htblColNameValue);
			htblColNameValue.clear();
		}
		// Check using maximum rows count in page 20
		Table t = dbApp.deserializeTable("Students");
		Vector<String> pages = t.getStrPages();

		Page lastPage = dbApp.deserializePage(pages.get(pages.size() - 1));

		// Check 1: there is only one element in the new page
		assertEquals(1, lastPage.getRecords().size());
		// Check 2: the id in the newest page is the largest (20)
		assertEquals(20, lastPage.getRecords().get(0).getHtblTuple().get("id"));
		// Check 3: Check last tuple in the first page is 19 and first tuple is 0
		Page firstPage = dbApp.deserializePage(pages.get(0));
		assertEquals(0, firstPage.getRecords().get(0).getHtblTuple().get("id"));
		assertEquals(19, firstPage.getRecords().get(firstPage.getRecords().size() - 1).getHtblTuple().get("id"));
		// Check 4: check that the records are sorted by clustering key
		for (int i = 0; i < firstPage.getRecords().size() - 1; i++) {
			assertEquals(1, (int) firstPage.getRecords().get(i + 1).getHtblTuple().get("id")
					- (int) firstPage.getRecords().get(i).getHtblTuple().get("id"));
		}

	}

	// DELETE:

	@Test
	public void deleteFromTableThatDoesntExist() {
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
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);

		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 2);

		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 2);

		dbApp.deleteFromTable("Students", htblColNameValue);
		Page p = dbApp.deserializePage("Students_1");

		Vector<Tuple> records = p.getRecords();

		assert records.size() == 1;

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
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);

		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();

		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 2);

		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();
		dbApp.createIndex("Students", "id", "idIndex");
		bplustree bt = dbApp.deserializeIndex("idIndex");
		bt.printTree();

		Table t = dbApp.deserializeTable("Students");
		Page p = dbApp.deserializePage("Students_1");
		System.out.println("Page Size Before: " + p.getNumOfEntries());

		htblColNameValue.clear();
		htblColNameValue.put("id", 1);
		dbApp.deleteFromTable("Students", htblColNameValue);

		Page p2 = dbApp.deserializePage("Students_1");

		System.out.println("Page Size After: " + p2.getNumOfEntries());
		bplustree bt2 = dbApp.deserializeIndex("idIndex");
		bt2.printTree();

		System.out.println(bt2.search(1));

		Vector<Tuple> r = p2.getRecords();

		assert r.size() == 1;
		// Assert that the result does not contain the deleted record
		// assert result.size()==0; //This doesn't delete from the bplustree?
	}

	@Test
	public void checkFileGetsDeletedWhenLastTupleIsRemoved()
			throws DBAppException, IOException, ClassNotFoundException {
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
	public void handleDeletingMoreThanOneRecordWithoutIndex()
			throws DBAppException, IOException, ClassNotFoundException {
		emptyMetadataFile(METADATA_PATH);
		DBApp dbApp = new DBApp();
		Hashtable<String, String> colNameType = new Hashtable<>();

		colNameType.put("name", "java.lang.String");
		colNameType.put("id", "java.lang.Integer");

		dbApp.createTable("Students", "id", colNameType);

		Hashtable<String, Object> htblColNameValue = new Hashtable<>();

		for (int i = 0; i < 10; i++) {
			htblColNameValue.put("name", "John");
			htblColNameValue.put("id", i);
			dbApp.insertIntoTable("Students", htblColNameValue);
			htblColNameValue.clear();
		}
		htblColNameValue.clear();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 2);
		dbApp.deleteFromTable("Students", htblColNameValue);

		Page p = dbApp.deserializePage("Students_1");
		Vector<Tuple> t = p.getRecords();
		for (int i = 0; i < t.size(); i++) {
			assert !(t.get(i).getHtblTuple().get("id").equals(2) && t.get(i).getHtblTuple().get("name").equals("John"));
		}

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

		// Test deleting non-existent tuple (Non-Indexed)
		for (int i = 0; i < 10; i++) {
			htblColNameValue.put("name", "John" + i);
			htblColNameValue.put("id", i);
			dbApp.insertIntoTable("Students", htblColNameValue);
			htblColNameValue.clear();
		}

		Page p1 = dbApp.deserializePage("Students_1");
		Vector<Tuple> t1 = p1.getRecords();

		htblColNameValue.put("name", "John" + 20);
		htblColNameValue.put("id", 20);

		dbApp.deleteFromTable("Students", htblColNameValue);

		Page p2 = dbApp.deserializePage("Students_1");
		Vector<Tuple> t2 = p2.getRecords();

		assert t1.size() == t2.size();

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
				"Expected deleteFromTable to throw DBAppException, but it didn't");

		String expectedMessage = "CHECK DATATYPES: Invalid datatype for name. You have entered java.lang.Integer";
		assertEquals(expectedMessage, thrown.getMessage());
	}

	// UPDATE:
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
		dbApp.insertIntoTable("Students", htblColNameValue);

		htblColNameValue.clear();

		Page p = dbApp.deserializePage("Students_1");
		assertTrue(p.getNumOfEntries() == 1);
		assert (p.getRecords().get(0).getHtblTuple().get("name").equals("John"));

		htblColNameValue.put("name", "Alex");
		htblColNameValue.put("gpa", 3.5);
		System.out.println(p.getRecords().get(0).getHtblTuple().get("name"));
		System.out.println(p.getRecords().get(0).getHtblTuple().get("gpa"));
		dbApp.updateTable("Students", "1", htblColNameValue);
		p = dbApp.deserializePage("Students_1");
		System.out.println(p.getRecords().get(0).getHtblTuple().get("name"));
		System.out.println(p.getRecords().get(0).getHtblTuple().get("gpa"));
		htblColNameValue.clear();

		Page p2 = dbApp.deserializePage("Students_1");
		assert (p2.getRecords().get(0).getHtblTuple().get("name").equals("Alex"));

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
		assertTrue(resultAfter2.contains("1") && resultAfter2.size() == 1);

	}

	@Test
	public void checkUpdatedCorrectlyWithMixedIndex() throws DBAppException {
		// This works when the maximumRowsCountInPage is 3, not 20
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
		assertTrue(resultAfter2.contains("1") && resultAfter2.size() == 1);

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
		Exception e = assertThrows(DBAppException.class,
				() -> dbApp.updateTable("NonExistentTable", "1", htblColNameValue));
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
		dbApp.createTable("Students", "id", colNameType);
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
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();
		htblColNameValue.put("name", "Mike");
		Exception e = assertThrows(DBAppException.class, () -> dbApp.updateTable("Students", "2", htblColNameValue));
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
		dbApp.createTable("Students", "id", colNameType);
		Hashtable<String, Object> htblColNameValue = new Hashtable<>();
		htblColNameValue.put("name", "John");
		htblColNameValue.put("id", 1);
		htblColNameValue.put("gpa", 2.45);
		dbApp.insertIntoTable("Students", htblColNameValue);
		htblColNameValue.clear();
		htblColNameValue.put("name", "Mike");
		htblColNameValue.put("gpa", 2.5);
		htblColNameValue.put("age", 47);
		Exception e = assertThrows(DBAppException.class, () -> dbApp.updateTable("Students", "2", htblColNameValue));
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
		bplustree nameI = dbApp.deserializeIndex("nameIndex");
		bplustree AgeI = dbApp.deserializeIndex("AgeIndex");
		nameI.printTree();
		AgeI.printTree();

		Vector<String> nameIndex = nameI.search("Alex");
		Vector<String> AgeIndex = AgeI.search(30);
		assertTrue(nameIndex.contains("1") && nameIndex.size() == 1);
		assertTrue(AgeIndex.contains("1") && AgeIndex.size() == 1);

	}

	// SELECT:

}
