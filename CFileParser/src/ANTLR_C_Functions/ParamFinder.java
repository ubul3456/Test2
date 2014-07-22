/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ANTLR_C_Functions;

import java.util.ArrayList;

/**
 * It gives paramName and paramType under the first
 * ParameterTypeList/ParameterList
 *
 * @author X
 */
public class ParamFinder {

    public static ArrayList<String> paramName = new ArrayList<>();
    public static ArrayList<String> paramType = new ArrayList<>();
    public static String tempType = new String();
    public static int number = 0; //children number of ParameterTypeList

    public static void init() {
        ParamFinder.paramName = new ArrayList<>();
        ParamFinder.paramType = new ArrayList<>();
        ParamFinder.tempType = new String();
        ParamFinder.number = 0;
    }
}
