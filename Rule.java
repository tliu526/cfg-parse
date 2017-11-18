/*
 * Describes a rule in the grammar.
 * Rules are of the form (A -> X) where A is a 
 * single nonterminal symbol and X is a sequence of terminals/nonterminals
 * (c) 2014 Tony Liu.
 */

import java.util.*;

public class Rule {
    //the LHS of the rule
    protected Symbol input;
    //the RHS of the rule
    protected ArrayList<Symbol> output;

    public Rule(Symbol in, ArrayList<Symbol> out) {
	input = in;
	output = out;
    }

    public Rule(Symbol in, Symbol out) {
	input = in;
	output = new ArrayList<Symbol>();
	output.add(out);
    }
    
    public ArrayList<Symbol> output() { return output; }

    public Symbol input() { return input; }

    public String toString() {
	String s = input + " ->";

	for (int i = 0; i < output.size(); i++) {
	    s += " " + output.get(i);
	}

	return s;
    }
    
    //checks equality by comparing both input and output
    public boolean equals(Rule other) {
	return this.input.equals(other.input())
	    && this.output.equals(other.output());
    }
    
}