package myTests;

import dbms.DBApp;
import dbms.DBAppException;
import dbms.Page;
import dbms.Table;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static DBApp dbapp = new DBApp();

    public static void emptyAllFiles() {
        try {
            String filePath = "dbms/src/main/resources/metadata.csv";
            FileWriter fw = new FileWriter(filePath, false);
            fw.write("");
            fw.close();

            String[] directories = {
                    "dbms/src/main/resources/Tables/",
                    "dbms/src/main/resources/Pages/",
                    "dbms/src/main/resources/Indices/"
            };

            for (String directory : directories) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directory), "*.ser")) {
                    for (Path entry : stream) {
                        Files.delete(entry);
                    }
                }
            }

        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public static void main(String[] args) throws DBAppException, TestException {
        emptyAllFiles();
        System.out.println("Test 1 is Starting!");
        EvaluateTest evaluateTest = new EvaluateTest();
        evaluateTest.EvaluateTest1();

        Table t = dbapp.deserializeTable("students");
        for (int i = 0; i < t.getStrPages().size(); i++) {
            Page p = dbapp.deserializePage(t.getStrPages().get(i));
            System.out.println(p.getRecords());

        }

    }

    public void emptyMetadataFile(String filePath) {
        try {
            FileWriter fw = new FileWriter(filePath, false); // the true will append the new data
            fw.write(""); // erase all content
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

}
