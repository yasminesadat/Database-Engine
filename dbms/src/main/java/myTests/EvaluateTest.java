package myTests;

import dbms.*;

import dbms.DBApp;
import dbms.DBAppException;
import dbms.Page;
import dbms.Table;
import dbms.Tuple;
import bPlusTree.bplustree;

import java.util.Vector;

public class EvaluateTest {
    public void EvaluateTest1() throws DBAppException, TestException {
        DBApp dbApp = new DBApp();
        GenerateTests generateTests = new GenerateTests(dbApp);
        Vector<Integer> chosenIds = generateTests.InitializeTest1(); // Check for Table Insertions : A. Check Clustered
                                                                     // , B. Check Existence and Index Creation
        Table testingTable;
        try {
            testingTable = (Table) dbApp.deserializeTable("students");
        } catch (Exception e) {
            throw new TestException("Table students not found/created");
        }
        System.err.println("CHOOOOSEN IDSSS SIZEEEEE!!" + chosenIds.size());
        // if (chosenIds.size() != 49) {
        // throw new TestException("Not all tuples were inserted, first check!");
        // }
        Integer prevId = -1;
        for (String pageName : testingTable.getStrPages()) {
            Page page = (Page) dbApp.deserializePage(pageName);
            for (Tuple tuple : page.getRecords()) {
                chosenIds.remove(tuple.getHtblTuple().get("id"));
                if (prevId.compareTo((Integer) tuple.getHtblTuple().get("id")) > 0) {
                    System.out.println(testingTable);
                    throw new TestException("Tuples are not sorted");
                }
                prevId = (Integer) tuple.getHtblTuple().get("id");
            }
        }
        if (chosenIds.size() != 0) {
            System.out.println(testingTable);
            throw new TestException("Not all tuples were inserted and a tuple could be duplicated , 2nd Check!");
        }

        System.out.println("All Insertion Tests Passed Successfully");

        generateTests.deletionsAgeTest1();
        for (String pageName : testingTable.getStrPages()) {
            Page page = (Page) dbApp.deserializePage(pageName);
            for (Tuple tuple : page.getRecords()) {
                if ((Integer) tuple.getHtblTuple().get("age") == 27) {
                    throw new TestException("Age Table Deletion failed");
                }
            }
        }

        System.out.println("All Deletion Tests without Index Passed Successfully");

        generateTests.deletionsGpaTest1();
        for (String pageName : testingTable.getStrPages()) {
            Page page = (Page) dbApp.deserializePage(pageName);
            for (Tuple tuple : page.getRecords()) {
                if ((Double) tuple.getHtblTuple().get("gpa") == 0.7) {
                    throw new TestException("Gpa Table Deletion failed");
                }
            }
        }

        System.out.println("All Deletion Tests with Index Passed Successfully");

        bplustree gpaIndex = (bplustree) dbApp.deserializeIndex("studentsGpaIndex");
        Vector<String> values = gpaIndex.search(0.7);
        if (values != null) {
            System.out.println(values);
            throw new TestException("Gpa Index Deletion failed , Not Null or Empty");
        }

        generateTests.updateTest1();
        for (String pageName : testingTable.getStrPages()) {
            Page page = (Page) dbApp.deserializePage(pageName);
            for (Tuple tuple : page.getRecords()) {
                if ((Integer) tuple.getHtblTuple().get("id") < 50) {
                    System.out.println("YES: " + tuple.getHtblTuple().get("id"));
                    if ((Double) tuple.getHtblTuple().get("gpa") != 0.5) {
                        throw new TestException("Update Test 1 failed");
                    }
                }
            }
        }
        // generateTests.selectOnlyoneconditon();
        System.out.println("All Tests 1 Passed Successfully");

    }
}
