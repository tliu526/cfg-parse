/*
 * Extends Symbol, a separate class that represents Terminals in a 
 * clearer manner.
 * (c) 2014 Tony Liu.
 */
public class Terminal extends Symbol {

    //isTerminal in superclass is set to true
    public Terminal(String s) { super(s, true); }

}