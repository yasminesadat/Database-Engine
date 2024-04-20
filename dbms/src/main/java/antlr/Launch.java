package antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class Launch {
    public static void main(String[] args) {
        try {

            //public Iterator parseSQL( StringBuffer strbufSQL ) throws
            //DBAppException

            //This is an alternative to the method shown below
            //strbufSQL is the SQL Query. example: CREATE TABLE "Student" ( "id" INT PRIMARY KEY, "gpa" DOUBLE, "name" STRING)
            //This Launch class should instead be in the DBApp

            String source = "C:\\Users\\Zrafa\\IdeaProjects\\Database-Engine\\dbms\\src\\main\\java\\antlr\\test.txt";
            CharStream cs = fromFileName(source);
            antlr.SQLLexer lexer = new SQLLexer(cs);
            CommonTokenStream token = new CommonTokenStream(lexer);
            SQLParser parser = new SQLParser(token);
            ParseTree tree = parser.parse();

            myVisitor visitor = new myVisitor();
            visitor.visit(tree);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

