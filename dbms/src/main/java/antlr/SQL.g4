
grammar SQL;

parse
 : block EOF| createTable EOF| createIndex EOF|insertintoTable EOF|updateTable EOF|deleteFromTable EOF|selectFromTable EOF
 ;

block
 : stat*
 ;


//Make it similar to SQL Syntax
createTable
: 'CREATE TABLE' tableName OPAR clusteringKeyColumn dataType 'PRIMARY KEY' (COMMA columnName dataType)* CPAR closerCreateTable
;


//public void createTable(String strTableName,
							//String strClusteringKeyColumn,
							//Hashtable<String, String> htblColNameType

							//CREATE TABLE "Student" ( "id" INT PRIMARY KEY,"gpa" DOUBLE,"name" STRING);


createIndex
:
'CREATE INDEX' indexName 'ON' tableName OPAR indexedColumnName CPAR closerCreateIndex
;

                //public void createIndex(String strTableName,
                     //String strColName,
                    //String strIndexName) throws DBAppException

                    //CREATE INDEX "nameIndex" ON "Students" ("name");


insertintoTable
:
'INSERT INTO' tableName '(' columnName (COMMA columnName)* ')' 'VALUES' '(' dataValue (COMMA dataValue)* ')'closerInsertIntoTable
;


            //public void insertIntoTable(String strTableName,
                    //Hashtable<String,Object> htblColNameValue)
                        //throws DBAppException

                     //INSERT INTO "Students" ("name","id","gpa") VALUES ("Hi",3,4.5);


updateTable
:
'UPDATE' tableName 'SET' columnName '=' dataValue (COMMA columnName '=' dataValue)* 'WHERE' clusteringKeyColumn '=' clusteringKeyValue closerUpdate
;


                    //public void updateTable(String strTableName,
                     //String strClusteringKeyValue,
                    //Hashtable<String,Object> htblColNameValue )

                    //UPDATE "Student" SET "name"="John","age"=2,"gpa"=2.34 WHERE "id"=5;


deleteFromTable
:
'DELETE' 'FROM' tableName 'WHERE' columnName '=' dataValue ('AND' columnName '=' dataValue)* closerDelete
;


                     //public void deleteFromTable(String strTableName,Hashtable<String,Object> htblColNameValue)

                    //DELETE FROM "Student" WHERE "name"="Ahmed" AND "id=3" AND "gpa"=1.2;

selectFromTable
:
'SELECT' '*' 'FROM' tableName 'WHERE' columnName arithmeticOperator dataValue (logicalOperator columnName arithmeticOperator dataValue)* closerSelect
;

                        //public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators)
                        //SELECT * FROM "Student" WHERE "name"="Ahmed" AND "age">10 OR "gpa"<3;


closerSelect
:
SCOL
;

arithmeticOperator:
GT|GTEQ|LT|LTEQ|ASSIGN
;

logicalOperator:
'AND'|'OR'|'XOR'|'and'|'or'|'xor'
;

tableName
:STRING;
indexName
:STRING;

dataValue
:INT|DOUBLE|STRING;


COMMA
:',';

clusteringKeyColumn
:
STRING
;
clusteringKeyValue
:
STRING|INT|DOUBLE
;

columnName
:
STRING
;

indexedColumnName
:
STRING
;

dataType
:
'INT'|'DOUBLE'|'STRING'
;

closerCreateTable
:
SCOL
;
closerCreateIndex
:
SCOL
;
closerInsertIntoTable
:
SCOL
;

closerUpdate
:
SCOL
;
closerDelete
:
SCOL
;



stat
 : assignment
 | if_stat
 | while_stat
 | log
 | OTHER {System.err.println("unknown char: " + $OTHER.text);}
 ;

assignment
 : ID ASSIGN expr SCOL
 ;

if_stat
 : IF condition_block (ELSE IF condition_block)* (ELSE stat_block)?
 ;

condition_block
 : expr stat_block
 ;

stat_block
 : OBRACE block CBRACE
 | stat
 ;

while_stat
 : WHILE expr stat_block
 ;

log
 : LOG expr SCOL
 ;

expr
 : expr POW<assoc=right> expr           #powExpr
 | MINUS expr                           #unaryMinusExpr
 | NOT expr                             #notExpr
 | expr op=(MULT | DIV | MOD) expr      #multiplicationExpr
 | expr op=(PLUS | MINUS) expr          #additiveExpr
 | expr op=(LTEQ | GTEQ | LT | GT) expr #relationalExpr
 | expr op=(EQ | NEQ) expr              #equalityExpr
 | expr AND expr                        #andExpr
 | expr OR expr                         #orExpr
 | atom                                 #atomExpr
 ;

atom
 : OPAR expr CPAR #parExpr
 | (INT | DOUBLE)  #numberAtom
 | (TRUE | FALSE) #booleanAtom
 | ID             #idAtom
 | STRING         #stringAtom
 | NIL            #nilAtom
 ;

OR : '||';
AND : '&&';
EQ : '==';
NEQ : '!=';
GT : '>';
LT : '<';
GTEQ : '>=';
LTEQ : '<=';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';
MOD : '%';
POW : '^';
NOT : '!';

SCOL : ';';
ASSIGN : '=';
OPAR : '(';
CPAR : ')';
OBRACE : '{';
CBRACE : '}';

TRUE : 'true';
FALSE : 'false';
NIL : 'nil';
IF : 'if';
ELSE : 'else';
WHILE : 'while';
LOG : 'log';

ID
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

INT
 : [0-9]+
 ;

DOUBLE
 : [0-9]+ '.' [0-9]*
 | '.' [0-9]+
 ;

STRING
 : '"' (~["\r\n] | '""')* '"'
 ;

COMMENT
 : '#' ~[\r\n]* -> skip
 ;

SPACE
 : [ \t\r\n] -> skip
 ;

OTHER
 : .
 ;