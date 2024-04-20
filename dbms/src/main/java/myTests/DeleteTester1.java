package myTests;

import dbms.*;

import java.util.Hashtable;

public class DeleteTester1 {
    @SuppressWarnings({ "removal", "unchecked", "rawtypes", "unused" })
    public static void main(String[] args) throws DBAppException {
        DBApp dbApp = new DBApp();
        String strTableName = "Student";
        dbApp.createIndex(strTableName, "name", "nameIndex");
        // System.out.println(dbApp.deserializeIndex("nameIndex"));
        Hashtable htblColNameValue = new Hashtable();
        htblColNameValue.put("name", new String("Zaky Noor"));
        dbApp.deleteFromTable(strTableName, htblColNameValue);
        System.out.println(dbApp.deserializeIndex("nameIndex"));

    }
}
