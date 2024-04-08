package myTests;

import dbms.*;

import java.util.Hashtable;

import bPlusTree.*;

public class Tester1 {
    @SuppressWarnings({ "removal", "unchecked", "rawtypes", "unused" })
    public static void main(String[] args) throws DBAppException {

        // SET UP: Config to 4
        // PURPOSE: Edge case of inserted tuple being shifted
        // insert 100, 0, 300, 250,500,600,700,800,900,1000
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
        htblColNameValue.put("id", new Integer(300));
        htblColNameValue.put("name", new String("Dalia Noor"));
        htblColNameValue.put("gpa", new Double(1.25));
        System.out.println("third insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(250));
        htblColNameValue.put("name", new String("John Noor"));
        htblColNameValue.put("gpa", new Double(1.5));
        System.out.println("fourth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(500));
        htblColNameValue.put("name", new String("Zaky Noor"));
        htblColNameValue.put("gpa", new Double(0.88));
        System.out.println("fifth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(600));
        htblColNameValue.put("name", new String("Zaky Noor"));
        htblColNameValue.put("gpa", new Double(0.88));
        System.out.println("sixth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(700));
        htblColNameValue.put("name", new String("Zaky Noor"));
        htblColNameValue.put("gpa", new Double(0.88));
        System.out.println("seventh insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(800));
        htblColNameValue.put("name", new String("Zaky Noor"));
        htblColNameValue.put("gpa", new Double(0.88));
        System.out.println("eighth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(900));
        htblColNameValue.put("name", new String("Zaky Noor"));
        htblColNameValue.put("gpa", new Double(0.88));
        System.out.println("ninth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);
        htblColNameValue.clear();
        htblColNameValue.put("id", new Integer(1000));
        htblColNameValue.put("name", new String("Zaky Noor"));
        htblColNameValue.put("gpa", new Double(0.88));
        System.out.println("tenth insert");
        dbApp.insertIntoTable(strTableName, htblColNameValue);

    }
}
