package dbms;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.String;

public class Table implements Serializable {
    private final String strTableName;
    private Vector<String> strPages;
    private int nextPageNum = 1;

    public int getNextPageNum(int pageNum) {
        for (int i = 0; i < strPages.size(); i++) {
            String page = strPages.get(i);
            String[] s = page.split("_");
            if (s[1].equals(pageNum + "")) {
                // when next page exists in vector
                if (i + 1 < strPages.size()) {
                    String res = strPages.get(i + 1);
                    return Integer.parseInt(res.charAt(res.length() - 1) + "");
                } else
                    return pageNum + 1;
            }
        }
        return -1;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public Table(String strTableName) {
        this.strTableName = strTableName;
        strPages = new Vector<String>();
        // then add it to the csv file
    }

    public Vector<String> getStrPages() {
        return strPages;
    }

    public String getStrTableName() {
        return strTableName;
    }

    public void addNewPage() {
        strPages.add(strTableName + "_" + nextPageNum++);
    }

    public boolean checkPageExists(String pageName) {
        return strPages.contains(pageName);
    }

    // return the column and its datatype
    public String getStrClusteringKeyColumn() {
        String clusteringKey = "";
        BufferedReader br = null;
        String line = "";
        try {
            // main
            br = new BufferedReader(new FileReader("dbms/src/main/resources/metadata.csv"));
            // for JUnit
            // br = new BufferedReader(new FileReader(
            // "E:/Semester 6/Database
            // 2/Project/GitHub/DBEngineWithMaven/dbms/src/main/resources/metadata.csv"));

            while ((line = br.readLine()) != null) {
                String[] s = line.split(", ");
                if (s[0].equals(strTableName)) {
                    clusteringKey = s[1];
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clusteringKey;

    }

    public void setStrPages(Vector<String> strPages) {
        this.strPages = strPages;
    }

}