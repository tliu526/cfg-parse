/*
 * Describes a dotted rule in the grammar.
 * Dotted rules have dot indices to indicate how far along
 * parsing has gotten for a particular rule. The index of the
 * dot indicates the NEXT entry to be parsed, so dot-1 was 
 * the most recent entry parsed.
 * (c) 2014 Tony Liu.
 */

import java.util.*;

public class DottedRule extends Rule {
    private int dot;

    //constructor with default dot position
    public DottedRule(Symbol in, ArrayList<Symbol> out) {
	super(in, out);
	dot = 0;
    }
    
    //constructor with given dot position
    public DottedRule(Symbol in, ArrayList<Symbol> out, int d) {
	super(in,out);
	dot = d;
    }

    //returns dot position
    public int getDot() { return dot; }

    //returns whether the entire RHS of the rule has been parsed
    public boolean isComplete() { return dot >= output.size(); }

    //returns whether there are more symbols to be parse
    public boolean hasNext() { return dot < output.size(); }

    //replaces the last Symbol parsed with n
    public void replace(Symbol n) {
	if(dot > 0) output.set(0, n);
	else System.out.println("No symbols parsed yet!");
    }

    //returns itself to be parsed, incremented ahead by one
    public DottedRule next() { return new DottedRule(this.input, this.output, this.dot+1); }
    
    //increments dot by one
    public void increment() { dot += 1;}

    //returns the next symbol to be parsed, but does not increment
    public Symbol peek() { 
	if(this.hasNext()) return output.get(dot);
	else {
	    System.out.println("No more symbols left to parse");
	    return null;
	}
    }

    public String toString() {
	String s = input + " -> ";
	
	for(int i = 0; i < output.size(); i++) {
	    if (i == dot) s += " .";
	    s += " " + output.get(i);
	}
	if(dot == output.size()) s += " .";
	return s;
    }

    //returns whether other has the same in/out/dot as this
    public boolean equals(DottedRule other) {
	return super.equals(other) && other.getDot() == this.dot; 
    }

    //test code
    public static void main(String args[]) {
	NonTerminal start = new NonTerminal("S");

	NonTerminal start2 = new NonTerminal("S");
	ArrayList<Symbol> s = new ArrayList<Symbol>();
	Terminal t = new Terminal("Sentence");
	Terminal t2 = new Terminal("Sentence");

	s.add(t);

	ArrayList<Symbol> s2 = new ArrayList<Symbol>();
	s2.add(t2);


	DottedRule r1 = new DottedRule(start, s, 5);
	DottedRule r2 = new DottedRule(start2, s2, 5);
	
	System.out.println(r1.equals(r2));

    }
}