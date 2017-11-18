/*
 * The class describing Symbols. Symbols can 
 * be either Terminal or nonTerminal
 * (c) 2014 Tony Liu.
 */
public class Symbol {
    //the string associated with the terminal
    private String s;
    //whether the symbol is a terminal or not
    private boolean isTerminal;
    
    public Symbol(String s, boolean isTerm) { 
	this.s = s;
	this.isTerminal = isTerm;
    }

    //returns isTerminal
    public boolean isTerminal() { return isTerminal; }

    //checks equality by comparing this.s to other.s 
    public boolean equals(Symbol other) {
	if(other.isTerminal() != this.isTerminal) return false;
	else return other.toString().equals(this.s);
    }

    
    public String toString() { return s; }

}