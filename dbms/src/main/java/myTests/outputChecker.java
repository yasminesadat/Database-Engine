package myTests;

import dbms.*;
import bPlusTree.*;

public class outputChecker {
    public static void main(String[] args) throws DBAppException {
        DBApp dbApp = new DBApp();
        System.out.println("first page");
        System.out.println(dbApp.deserializePage("Student_1"));
        System.out.println("second page");
        System.out.println(dbApp.deserializePage("Student_2"));
        bplustree b = dbApp.deserializeIndex("gpaIndex");
        System.out.println("GPA index");
        b.printTree();
        b = dbApp.deserializeIndex("idIndex");
        System.out.println("IDindex");
        b.printTree();
    }
}
