/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ANTLR_C_Functions;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author X
 */
public class Out {
    
    public static String returnType = new String();
    public static String functionName = new String();
    public static ArrayList<String> paramName = new ArrayList<>();
    public static ArrayList<String> paramType = new ArrayList<>();
    public static void commandLineOutput(){
        Out.functionName = "Function name:" + Out.functionName;
        System.out.println(functionName);
        Out.returnType = "Return type:" + Out.returnType;
        System.out.println(returnType);
        System.out.println("Parameters:");
        Iterator iter = paramType.iterator();
        for (String s : paramName) {
            System.out.println("Param name:" + s);
            if (iter.hasNext()) {
                System.out.println("Param type:" + iter.next());
            }
         }
        init();
        ParamFinder.init();
}
    
    public static void init(){
        returnType = new String();
        functionName = new String();
        paramName = new ArrayList<>();
        paramType = new ArrayList<>();
    }    

    
}
