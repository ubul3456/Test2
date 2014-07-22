/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ANTLR_C_Functions;

/**
 *
 * @author X
 */
class Counter {
    private int c = 0;
 
    public void inc() {
        c++;
    }
 
    public void dec() {
        c--;
    }
 
    public int get() {
        return c;
    }
    
    public void set(int c) {
        this.c = c;
    }
 
}
