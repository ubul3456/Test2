/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ANTLR_C_Functions;


import ANTLR_Rules.CLexer;
import ANTLR_Rules.CParser;
import java.io.FileInputStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;


/**
 *
 * @author X
 */
public class Start {

    /**
     * @param args the command line arguments: args[0] is the filepath of the C file
     */
    public static void main(String[] args) throws Exception {

        // Open the file that is the first 
        String filepath = args[0];//"C:\\exercise1.c";
        
        try (FileInputStream fstream = new FileInputStream(filepath)) {
            
            //ANTLR lexer and parse objects
            CLexer lexer = new CLexer(new ANTLRInputStream(fstream));
            CParser parser = new CParser(new CommonTokenStream(lexer));
            
            ANTLR_C_Functions.cFileParsing(parser);
        
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }
}
