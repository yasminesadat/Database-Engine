package antlr;

import dbms.DBApp;
import dbms.DBAppException;
import dbms.SQLTerm;
import dbms.Tuple;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class myVisitor extends SQLBaseVisitor<Object> {


    Vector<String> columnNames=new Vector<String>();
    Vector<String> columnTypes=new Vector<String>();
    Vector<Object> columnValues=new Vector<Object>();

    Vector<String>logicalOperators=new Vector<String>();
    Vector<String>arithmeticOperators=new Vector<String>();


    String tableName="";
    String clusteringKeyColumn="";
    String indexName="";
    String indexedColumnName="";
    String clusteringKeyValue="";

    Iterator<Tuple>result;



    public Object visitParse(SQLParser.ParseContext ctx) {

        return visitChildren(ctx);
    }

    public Object visitNumberAtom(SQLParser.NumberAtomContext ctx) {
        System.out.println(ctx.getText());

        return visitChildren(ctx);
    }

    public Object visitTableName(SQLParser.TableNameContext ctx) {
        tableName=ctx.getText();
        System.out.println(tableName);
        return visitChildren(ctx);


    }

    public Object visitClusteringKeyColumn(SQLParser.ClusteringKeyColumnContext ctx) {


        String s=ctx.getText();
        clusteringKeyColumn=discardQuotes(s);
        columnNames.add(clusteringKeyColumn);
        return visitChildren(ctx);
    }

    public Object visitColumnName(SQLParser.ColumnNameContext ctx) {

        for (Object o: ctx.children) {


            String s=o.toString();
            s=discardQuotes(s);

            columnNames.add(s);
        }
        return visitChildren(ctx);

    }

    public Object visitIndexedColumnName(SQLParser.IndexedColumnNameContext ctx) {

        indexedColumnName=ctx.getText();
        System.out.println(indexedColumnName);
        return visitChildren(ctx);

    }

    public Object visitDataType(SQLParser.DataTypeContext ctx) {



        for(int i = 0; i < ctx.getChildCount(); i++){
            if(ctx.getChild(i).getText().equals("INT")) {
                columnTypes.add("java.lang.Integer");
            }
            if(ctx.getChild(i).getText().equals("STRING")) {
                columnTypes.add("java.lang.String");
            }
            if(ctx.getChild(i).getText().equals("DOUBLE")) {
                columnTypes.add("java.lang.double");
            }
        }
        return visitChildren(ctx);
    }

    public Object visitIndexName(SQLParser.IndexNameContext ctx) {

        indexName=ctx.getText();



        return visitChildren(ctx);
    }

    public Object visitDataValue(SQLParser.DataValueContext ctx) {

        for (Object o: ctx.children) {
            columnValues.add(o.toString());
        }


        return visitChildren(ctx);


    }


    public Object visitLogicalOperator(SQLParser.LogicalOperatorContext ctx) {



        logicalOperators.add(ctx.getText());
        return visitChildren(ctx);
    }

    public Object visitArithmeticOperator(SQLParser.ArithmeticOperatorContext ctx) {

        arithmeticOperators.add(ctx.getText());
        return visitChildren(ctx);
    }

    public Object visitClusteringKeyValue(SQLParser.ClusteringKeyValueContext ctx) {

        clusteringKeyValue= ctx.getText();
        clusteringKeyValue=""+clusteringKeyValue;


        System.out.println("CCV: "+clusteringKeyValue);



        return visitChildren(ctx);
    }


    public Object visitCloserCreateTable(SQLParser.CloserCreateTableContext ctx) {
        System.out.println("Create Table Accessed");
        Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
        System.out.println(tableName);
        System.out.println(clusteringKeyColumn);

        for (int i = 0; i < columnNames.size(); i++) {
            //discardQuotes(columnNames.get(i));discardQuotes(columnTypes.get(i));
            htblColNameType.put(columnNames.get(i), columnTypes.get(i));
        }

        System.out.println(htblColNameType);
        DBApp dbapp=new DBApp();
        try {

            tableName = tableName.substring(1, tableName.length() - 1);


            dbapp.createTable(tableName, clusteringKeyColumn, htblColNameType);
        }
        catch (DBAppException e) {
            e.printStackTrace();
        }




        return visitChildren(ctx); }

    public Object visitCloserCreateIndex(SQLParser.CloserCreateIndexContext ctx) {
        System.out.println("Create Index Accessed");

        DBApp dbapp=new DBApp();
        try {
            tableName=discardQuotes(tableName);

            indexedColumnName=discardQuotes(indexedColumnName);
            indexName=discardQuotes(indexName);

            System.out.println(tableName+" "+indexedColumnName+" "+indexName);
            dbapp.createIndex(tableName,indexedColumnName,indexName);
        }
        catch (DBAppException e) {
            e.printStackTrace();
        }


        return visitChildren(ctx); }


    public Object visitCloserInsertIntoTable(SQLParser.CloserInsertIntoTableContext ctx) {
        System.out.println("Insert Into Table Accessed");

        tableName=discardQuotes(tableName);
        Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>();

        for (int i = 0; i < columnValues.size(); i++) {
            Object keyValue;
            if (canParseInt((String) columnValues.get(i)) != null) {
                keyValue = canParseInt((String) columnValues.get(i));
                System.out.println("ENTERED THIS: "+columnValues.get(i));
            } else if (canParseDouble((String) columnValues.get(i)) != null) {
                keyValue = canParseDouble((String) columnValues.get(i));
            }
            else{
                keyValue=columnValues.get(i);
                keyValue=discardQuotes((String) keyValue);
            }

            htblColNameValue.put(columnNames.get(i), keyValue);
            System.out.println(columnNames.get(i).getClass().getSimpleName());
            System.out.println(htblColNameValue);
        }
        try {
            DBApp DBApp = new DBApp();
            System.out.println("TN: "+ tableName);
            DBApp.insertIntoTable(tableName, htblColNameValue);
            System.out.println(htblColNameValue);
        }
        catch (DBAppException e) {
            e.printStackTrace();
        }


        return visitChildren(ctx); }





    public Object visitCloserUpdate(SQLParser.CloserUpdateContext ctx) {
        System.out.println("Update Table Accessed");


        Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>();

        for (int i = 0; i < columnValues.size(); i++) {
            Object keyValue;
            if (canParseInt((String) columnValues.get(i)) != null) {
                keyValue = canParseInt((String) columnValues.get(i));
            } else if (canParseDouble((String) columnValues.get(i)) != null) {
                keyValue = canParseDouble((String) columnValues.get(i));
            } else {
                keyValue=columnValues.get(i);
                keyValue=discardQuotes((String) keyValue);
            }


            htblColNameValue.put(columnNames.get(i), keyValue);
            System.out.println("FOR UPDATE: "+htblColNameValue);
        }
        try {
            DBApp DBApp = new DBApp();
            tableName = discardQuotes(tableName);
            System.out.println("TN: " + tableName);
            DBApp.updateTable(tableName, clusteringKeyValue,htblColNameValue);
            System.out.println(htblColNameValue);
        } catch (DBAppException e) {
            e.printStackTrace();
        }

        return visitChildren(ctx);

    }
    public Object visitCloserDelete(SQLParser.CloserDeleteContext ctx) {
        System.out.println("Delete Table Accessed");
        tableName=discardQuotes(tableName);
        Hashtable<String, Object> htblColNameValue = new Hashtable<String, Object>();

        for (int i = 0; i < columnValues.size(); i++) {
            Object keyValue;
            if (canParseInt((String) columnValues.get(i)) != null) {
                keyValue = canParseInt((String) columnValues.get(i));
                System.out.println("ENTERED THIS: "+columnValues.get(i));
            } else if (canParseDouble((String) columnValues.get(i)) != null) {
                keyValue = canParseDouble((String) columnValues.get(i));
            }
            else{
                keyValue=columnValues.get(i);
                keyValue=discardQuotes((String) keyValue);
            }

            htblColNameValue.put(columnNames.get(i), keyValue);
            System.out.println(columnNames.get(i).getClass().getSimpleName());
            System.out.println(htblColNameValue);
        }
        try {
            DBApp DBApp = new DBApp();
            System.out.println("TN: "+ tableName);
            DBApp.deleteFromTable(tableName, htblColNameValue);
            System.out.println(htblColNameValue);
        }
        catch (DBAppException e) {
            e.printStackTrace();
        }




        return visitChildren(ctx);
    }

    public Object visitCloserSelect(SQLParser.CloserSelectContext ctx) {
        System.out.println("SELECT Accessed");
        tableName=discardQuotes(tableName);
        String[] strarrOperators=new String[logicalOperators.size()];
        System.out.println(logicalOperators.size());
        for (int i = 0; i < logicalOperators.size(); i++) {
            strarrOperators[i]=logicalOperators.get(i);
        }

        for (int i=0;i<strarrOperators.length;i++) {
            System.out.println(strarrOperators[i]);
        }

        for (int i = 0; i < columnValues.size(); i++) {
            Object keyValue;
            if (canParseInt((String) columnValues.get(i)) != null) {
                keyValue = canParseInt((String) columnValues.get(i));
                System.out.println("ENTERED THIS: " + columnValues.get(i));
            } else if (canParseDouble((String) columnValues.get(i)) != null) {
                keyValue = canParseDouble((String) columnValues.get(i));
            } else {
                keyValue = columnValues.get(i);
                keyValue = discardQuotes((String) keyValue);
            }
            columnValues.set(i,keyValue);
        }

        System.out.println(columnNames.size());
        SQLTerm[] arrSQLTerms  = new SQLTerm[columnNames.size()];
        for(int i=0;i<columnNames.size();i++){
            arrSQLTerms[i]= new SQLTerm();
            arrSQLTerms[i]._strTableName=tableName;
            arrSQLTerms[i]._strColumnName=columnNames.get(i);
            arrSQLTerms[i]._strOperator= arithmeticOperators.get(i);
            arrSQLTerms[i]._objValue= columnValues.get(i);
        }

        try{
            System.out.println("-------------");
            DBApp dbapp = new DBApp();
            result=dbapp.selectFromTable(arrSQLTerms,strarrOperators);
            while (result.hasNext()) {
                Tuple tuple = result.next();
                System.out.println("Record: " + tuple);
            }

        }
        catch (DBAppException e) {
            e.printStackTrace();
        }


        return visitChildren(ctx);
    }


    public static String discardQuotes(String s) {
        return  s.substring(1, s.length() - 1);
    }
    public static Integer canParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double canParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public Iterator<Tuple> getResultIterator() {

        return result;
    }




}