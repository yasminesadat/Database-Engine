package myTests;

import dbms.*;

import java.util.Hashtable;

public class DeleteTester1 {
    @SuppressWarnings({ "removal", "unchecked", "rawtypes", "unused" })
    public static void main(String[] args) throws DBAppException {
        DBApp dbApp = new DBApp();
        String strTableName = "Student";
        Hashtable htblColNameValue = new Hashtable();
        htblColNameValue.put("id", new Integer(900));
        htblColNameValue.put("name", new String("Zaky Noor"));
        dbApp.deleteFromTable(strTableName, htblColNameValue);
    }
}
