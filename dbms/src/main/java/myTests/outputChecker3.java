package myTests;

import dbms.*;
import bPlusTree.*;

public class outputChecker3 {
    public static void main(String[] args) throws DBAppException {
        DBApp dbApp = new DBApp();
        System.out.println("first page");
        System.out.println(dbApp.deserializePage("Student_1"));
        System.out.println("second page");
        System.out.println(dbApp.deserializePage("Student_2"));
        System.out.println("third page");
        System.out.println(dbApp.deserializePage("Student_3"));
    }
}
