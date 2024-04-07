package dbms;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.String;

public class Table implements Serializable {
    private final String strTableName;
    // private final String strClusteringKeyColumn;
    // private Hashtable<String, String> htblColNameType; // we only need the csv
    // private Vector<Page> tblPages; //useless if we use Page Manager
    private Vector<String> strPages;
    private int nextPageNum = 1;

    public int getNextPageNum() {
        return nextPageNum;
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

    // return the column and its datatype
    public String getStrClusteringKeyColumn() {
        String clusteringKey = "";
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader("dbms/src/main/resources/metadata.csv"));

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

}