/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ANTLR_C_Functions;

import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Search for the TerminalNodes in a tree
 * @author X
 */
public class LeavesFinder {
    
    public ArrayList<String> leaves = new ArrayList<>();
    
    public void getLeaves(ParseTree tree) {
        
        int c = tree.getChildCount();

        if (c == 0) {
            leaves.add(tree.getText());
        } else {
            int i;
            for (i = 0; i < c; i++) {
                getLeaves(tree.getChild(i));                
            }
        }
    }    
}
