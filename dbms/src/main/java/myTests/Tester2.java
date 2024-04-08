package myTests;

import dbms.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

public class Tester2 {
    @SuppressWarnings({ "removal", "unchecked", "rawtypes", "unused" })
    public static void main(String[] args) throws DBAppException {
        // SET UP: Config to 4
        // PURPOSE: Shifting old tuple to new page to insert new one
        // then update tuple 250
        // insert 100, 0, 500, 300, 250

        String filePath = "dbms/src/main/resources/metadata.csv";
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
        }
        String strTableName = "Student";
        DBApp dbApp = new DBApp();

        Hashtable htblColNameType = new Hashtable();
        htblColNameType.put("id", "java.lang.Integer");
        htblColNameType.put("name", "java.lang.String");
        htblColNameType.put("gpa", "java.lang.double");
        dbApp.createTable(strTableName, "id", htblColNameType);
        dbApp.createIndex(strTableName, "gpa", "gpaIndex");
        dbApp.createIndex(strTableName, "id", "idIndex");
        Hashtable htblColNameValue = new Hashtable();
        htblColNameValue.put("id", new Integer(100));
        htblColNameValue.put("name", new String("Ahmed Noor"));
        htblColNameValue.put("gpa", new Double(0.95));
        System.out.println("first insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(0));
        htblColNameValue.put("name", new String("Karim Noor"));
        htblColNameValue.put("gpa", new Double(0.95));
        System.out.println("second insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(500));
        htblColNameValue.put("name", new String("Dalia Noor"));
        htblColNameValue.put("gpa", new Double(1.25));
        System.out.println("third insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(300));
        htblColNameValue.put("name", new String("John Noor"));
        htblColNameValue.put("gpa", new Double(1.5));
        System.out.println("fourth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(250));
        htblColNameValue.put("name", new String("Zaky Noor"));
        htblColNameValue.put("gpa", new Double(0.88));
        System.out.println("fifth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("name", new String("UPDATED"));
        htblColNameValue.put("gpa", new Double(100000));
        dbApp.updateTable(strTableName, "250", htblColNameValue);
    }
}
