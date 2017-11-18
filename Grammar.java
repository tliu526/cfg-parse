/*
 * A Grammar object holds a ArrayList of rules, with
 * the start rule as the first rule in the ArrayList.
 * (c) 2014 Tony Liu.
 */
import java.util.*;
import java.io.*;

public class Grammar {
    //the rules that describe the grammar
    protected ArrayList<Rule> rules;

    //constructor for a vector of rules
    public Grammar(ArrayList<Rule> r) { rules = r; }

    //return size of the grammar (number of RHS)
    public int size() { return rules.size(); }

    //constructor for a text file of rules
    public Grammar(String file){ rules = generateRules(file); }

    //returns the "dummy" start rule
    public Rule start() { return rules.get(0); }

    //return rules
    public ArrayList<Rule> getRules() { return rules; }

    //returns all rules that have a LHS that match the string start
    public ArrayList<Rule> getRules(String start) {
	ArrayList<Rule> set = new ArrayList<Rule>();
	Rule r;

	for(int i = 0; i < rules.size(); i++) {
	    r = rules.get(i);
	    if(r.input().toString().equals(start))
		set.add(r);
	}

	return set;
    }

    //returns all rules that have a LHS that match the Symbol start
    public ArrayList<Rule> getRules(Symbol start) {
	ArrayList<Rule> set = new ArrayList<Rule>();
	Rule r;

	for(int i = 0; i < rules.size(); i++) {
	    r = rules.get(i);
	    if(r.input().equals(start))
		set.add(r);
	}

	return set;
    }

    //Creates a vector of rules based on an input .txt file
    public ArrayList<Rule> generateRules(String text) {
	Scanner scan;
	String cur; //current token
	boolean onRHS = false; //indicates whether the RHS is being built

	NonTerminal left = null; //lhs of a rule
	ArrayList<Symbol> right = new ArrayList<Symbol>(); //rhs of a rule
	ArrayList<Rule> rules = new ArrayList<Rule>(); //the vector of rules
	
	try {
	    scan = new Scanner(new FileInputStream(text)); 
	    
	    while(scan.hasNext()) {
		cur = scan.next();

		if(cur.equals(";")) { //reached end of rule, add to rules and reset L/R
		    rules.add(new Rule(left,right));
		    onRHS = false; //next NonTerminal will be LHS of new rule
		    right = new ArrayList<Symbol>();
		}
		else if(cur.equals("=")) { //left has been assigned, adding to RHS now
		    onRHS = true;
		}
		else if(cur.equals("|")) { //a RHS has been completed
		    left = new NonTerminal(left.toString());
		    rules.add(new Rule(left,right));
		    right = new ArrayList<Symbol>(); //reset right
		}
		else if(cur.charAt(0) == '<' && cur.length() > 1 &&!onRHS) { //left doesn't exist yet
		    left = new NonTerminal(cur.substring(1,cur.length()-1));
		}
		else if(cur.charAt(0) == '<' && cur.length() > 1 && onRHS) { //NonTerminal on RHS
		    right.add(new NonTerminal(cur.substring(1,cur.length()-1)));
		}
		else { //must be a Terminal
		    right.add(new Terminal(cur));
		}
	    }
	    
	}
	catch(IOException e) { 
	    System.out.println("Bad file name.");
	    return null;
	}
	return rules;
    } 

    //prints all rules within a grammar
    public String toString() {
	String s = "";

	for(int i = 0; i < rules.size(); i++) {
	    s += rules.get(i) + "\n";
	}

	return s;
    }

    //test code
    public static void main (String args[]) {
	if(args.length > 0) {
	    Grammar g = new Grammar(args[0]);
	    System.out.println(g);
	}

    }
}