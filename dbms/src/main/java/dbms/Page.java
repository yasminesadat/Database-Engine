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
            // main
            Path path = Path.of("dbms/src/main/resources/DBApp.config");
            // for JUnit
            // Path path = Path.of(
            // "E:/Semester 6/Database
            // 2/Project/GitHub/DBEngineWithMaven/dbms/src/main/resources/DBApp.config");

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
        if (numOfEntries == 0)
            return "Empty Page";
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

    public void remove(int i) {
        Records.remove(i);
        numOfEntries--;
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
    // insert when there is space
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

    public Tuple insertAndDisplaceLast(Hashtable<String, Object> value, String strClusteringKeyColumn) {
        // Create a tuple from the insert hashtable
        String[] s = pageName.split("_");
        Tuple insertTuple = new Tuple(s[0], value);

        // If the page is not full, simply insert the tuple
        if (numOfEntries < maxEntries) {
            insertBinary(value, strClusteringKeyColumn);
            return null; // No tuple was displaced
        } else {
            // If the page is full, displace the largest tuple
            Tuple lastTuple = Records.lastElement();
            Tuple displacedTuple = null;
            if (compareTuples(lastTuple, insertTuple, strClusteringKeyColumn) > 0) {
                // lastTuple is to be displaced
                displacedTuple = lastTuple;
                // Find the correct position for the new tuple and insert it
                int index = searchBinary(value, strClusteringKeyColumn);
                if (index < 0) {
                    index = -(index + 1); // Convert the insertion point
                }
                Records.add(index, insertTuple); // Insert the new tuple at the correct position

                // Since the page was full, we remove the last tuple (now an extra element)
                Records.remove(Records.size() - 1); // Remove the last tuple

            } else { // New tuple to be shifted - edge case where insertion is beyond the max element
                     // and the page is full

                displacedTuple = insertTuple;

            }

            // Return the displaced tuple
            return displacedTuple;
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