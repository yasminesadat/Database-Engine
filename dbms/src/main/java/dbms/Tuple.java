package dbms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.String;
import java.util.Hashtable;

public class Tuple implements Serializable {
    private String strTableName;
    private Hashtable<String, Object> htblTuple;

    public Tuple(String strTableName, Hashtable<String, Object> htblTuple) {
        this.strTableName = strTableName;
        this.htblTuple = htblTuple;
    }

    public Tuple() {
        htblTuple = new Hashtable<>();

    }

    public String toString() {
        String res = "";
        BufferedReader br = null;
        String line = "";
        boolean foundTable = false;
        try {
            br = new BufferedReader(new FileReader("dbms/src/main/resources/metadata.csv"));

            while ((line = br.readLine()) != null) {
                String[] s = line.split(", ");

                if (foundTable && !strTableName.equals(s[0])) {
                    break;
                }
                if (s[0].equals(strTableName)) {
                    foundTable = true;
                    res += htblTuple.get(s[1]) + ", ";
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.substring(0, res.length() - 2);
    }

    public Hashtable<String, Object> getHtblTuple() {
        return htblTuple;
    }

    public void updateValues(Hashtable<String, Object> htblColNameValue) {
        for (String s : htblColNameValue.keySet()) {

            this.htblTuple.replace(s, this.htblTuple.get(s), htblColNameValue.get(s));
        }
    }

}