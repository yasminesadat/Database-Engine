package myTests;

import dbms.*;

import dbms.DBApp;
import dbms.DBAppException;
import dbms.Page;
import dbms.Table;
import dbms.Tuple;
import bPlusTree.bplustree;

import java.util.Vector;

public class TestException extends Exception {
    public TestException(String message) {
        super(message);
    }

    public TestException() {
        super();
    }
}
