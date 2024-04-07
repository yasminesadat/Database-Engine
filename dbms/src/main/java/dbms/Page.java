package dbms;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Properties;

public class Page implements Serializable {
    private String pageName;
    private int numOfEntries = 0; // keep track of how many entries are there
    private int maxEntries; // get from config file
    private Vector<Tuple> Records;

    public int getNumOfEntries() {
        return numOfEntries;
    }

    public int getMaxEntries() {
        return maxEntries;
    }

    public String getPageNum() {
        String[] s = pageName.split("_");
        return s[1];
    }

    public Page(String tableName, int pageNum) {
        try {
            Properties prop = new Properties();
            Path path = Path.of("dbms/src/main/resources/DBApp.config");

            BufferedReader reader = Files.newBufferedReader(path);
            prop.load(reader);

            this.maxEntries = Integer.parseInt(prop.getProperty("MaximumRowsCountinPage"));
            this.pageName = tableName + "_" + pageNum;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Records = new Vector<Tuple>();

    }

    public String getPageName() {
        return pageName;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < numOfEntries; i++) {
            s += Records.get(i).toString() + ", ";
        }
        return s.substring(0, s.length() - 2);
    }

    public Vector<Tuple> getRecords() {
        return Records;
    }

    public void setRecords(Vector<Tuple> Records) {
        this.Records = Records;
    }

    // Compare the tuples based on the clustering key
    public static int compareTuples(Tuple t1, Tuple t2, String strClusteringKeyColumn) {
        Object t1clustervalue = t1.getHtblTuple().get(strClusteringKeyColumn);
        Object t2clustervalue = t2.getHtblTuple().get(strClusteringKeyColumn);
        if (t1clustervalue instanceof Integer) {
            return ((Integer) t1clustervalue).compareTo((Integer) t2clustervalue);
        } else if (t1clustervalue instanceof String) {
            return ((String) t1clustervalue).compareTo((String) t2clustervalue);
        } else if (t1clustervalue instanceof Double) {
            return ((Double) t1clustervalue).compareTo((Double) t2clustervalue);
        }

        return 0;
    }

    public int searchBinary(Hashtable<String, Object> value, String strClusteringKeyColumn) {
        // create a tuple from the insert hashtable
        String[] s = pageName.split("_");
        Tuple insertTuple = new Tuple(s[0], value);
        Comparator<Tuple> tupleComparator = new Comparator<Tuple>() {
            @Override
            public int compare(Tuple t1, Tuple t2) {
                Object t1clustervalue = t1.getHtblTuple().get(strClusteringKeyColumn);
                Object t2clustervalue = t2.getHtblTuple().get(strClusteringKeyColumn);
                if (t1clustervalue instanceof Integer) {
                    return ((Integer) t1clustervalue).compareTo((Integer) t2clustervalue);
                } else if (t1clustervalue instanceof String) {
                    return ((String) t1clustervalue).compareTo((String) t2clustervalue);
                } else if (t1clustervalue instanceof Double) {
                    return ((Double) t1clustervalue).compareTo((Double) t2clustervalue);
                }

                return 0;
            }
        };

        // the index here is used to find the insertion point to insert to the vector
        return Collections.binarySearch(Records, insertTuple, tupleComparator);
    }

    /*
     * used to binary insert in the page rows and the comparator is similar to the
     * compareTuples method but it needs only 2 parameters
     * which are tuples in order to implement it in the binary search method
     */

    // enter here if page has space
    public void insertBinary(Hashtable<String, Object> value, String strClusteringKeyColumn) {
        String[] s = pageName.split("_");
        Tuple insertTuple = new Tuple(s[0], value);
        // creare a Comparator to binary search on the tuples and compare based on the
        // clustering key
        int index = searchBinary(value, strClusteringKeyColumn);
        if (index < 0) {
            index = -(index + 1);
        }

        // we insert the record in the index shown in the previous line
        Records.add(index, insertTuple);
        numOfEntries++;

    }

    public Tuple insertAndReturnOther(Tuple t1, Tuple t2, String strClusteringKeyColumn) {
        // this method compares the 2 tuples, inserts the minimum one and the 'other'
        // returns it to be inserted in the next page;
        if (compareTuples(t1, t2, strClusteringKeyColumn) <= 0) {
            insertBinary(t1.getHtblTuple(), strClusteringKeyColumn);
            return t2;
        } else {
            insertBinary(t2.getHtblTuple(), strClusteringKeyColumn);
            return t1;
        }
    }

    public Tuple getLastValue() {

        return Records.get(Records.size() - 1);
    }

    public Tuple getFirstValue() {

        return Records.get(0);
    }

    public int binarySearch(String strClusteringKey, Object clusteringKeyValue) {
        Comparator<Tuple> tupleComparator = (Tuple t1, Tuple t2) -> {
            Object key1 = t1.getHtblTuple().get(strClusteringKey);
            Object key2 = t2.getHtblTuple().get(strClusteringKey);

            // Handling Integer
            if (key1 instanceof Integer) {
                return ((Integer) key1).compareTo((Integer) key2);
            }
            // Handling Double
            else if (key1 instanceof Double) {
                return ((Double) key1).compareTo((Double) key2);
            }
            // Handling String
            else if (key1 instanceof String) {
                return ((String) key1).compareTo((String) key2);
            }
            return 0;
        };
        Tuple searchKey = new Tuple();
        searchKey.getHtblTuple().put(strClusteringKey, clusteringKeyValue);

        return Collections.binarySearch(Records, searchKey, tupleComparator);

    }

}