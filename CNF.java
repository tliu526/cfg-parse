/*
 * Describes the Chomsky Normal Form of a Grammar.
 * A Grammar in CNF has production rules of only the following two forms:
 * A -> a, where a is a terminal
 * A -> B C, where B and C are nonterminals
 * This conversion assumes that the grammar is not nullable.
 * (c) 2014 Tony Liu.
 */
import java.util.*;

public class CNF extends Grammar{
    private int label = 0; //labels for new rules
     private ArrayList<Rule> ntRules; //the set of rules of the form A -> B C
    
    public CNF(ArrayList<Rule> r) { 
	super(r); 
	toCNF();
    }

    public CNF(String file) { 
	super(file); 
	toCNF();
    }

    /*
     * converts rules to CNF.
     * 1) remove unit rules (A -> B)
     * 2) create new rules for every terminal
     * 3) reduce any rules with RHS greater than 2 
     */
    public void toCNF() {
	removeUnit();
	createTermRule();
	reduce();
    }

    //step 1 of the conversion
    public void removeUnit() {
	Rule r; //temp rule
	ArrayList<Rule> temp; //temp vector
	Symbol s; //temp symbol
	for(int i = 0; i < rules.size(); i++) {
	    r = rules.get(i);
	    
	    if(r.output().size() == 1) {
		s = r.output().get(0);
		if(!s.isTerminal()) { //if a unit rule
		    rules.remove(r); //pull out the old rule
		    temp = this.getRules(s);
		    rules.removeAll(temp);
		    for(int j = 0; j < temp.size(); j++) { //add new rules
			rules.add(new Rule(r.input(),temp.get(j).output()));
		    }
		}
	    } 
	}
    }
    
    //step 2 of the conversion
    public void createTermRule() {

	//the names that have had rules created 
	ArrayList<String> names = new ArrayList<String>();
	Symbol s; //temp symbol
	Rule r; //temp, new rule
	Rule curRule;
	ArrayList<Symbol> rhs; //temp rhs

	for(int i = 0; i < rules.size(); i++) {
	    curRule = rules.get(i);
	    rhs = curRule.output();
	    if(rhs.size()>1) {
		for(int j = 0; j < rhs.size(); j++) {
		    s = rhs.get(j);
		    //s is Terminal
		    if(s.isTerminal()){
			//create new production
			if(!names.contains(s.toString()) && getLHS(s) == null) {
			    r = new Rule(new NonTerminal(label+""), s);
			    rules.add(r);
			    names.add(s.toString()); //keep track of added symbols
			    label++; //increment label to make it unique
			}
			
			s = getLHS(s);
			rhs.set(j, s); //replaced the terminal with new nonterminal
			
		    }
		}
	    }
	}
    }

    //step 3 of the conversion, also populates ntRules
    public void reduce() {
	ArrayList<Rule> newRules = new ArrayList<Rule>(); //new rules of grammar
	
	//the current rule in old grammar being analyzed
	Rule curRule; 
	//the right hand side of curRule, linked list used for easy remove
	LinkedList<Symbol> curRhs; 
	
	Rule tempRule; //temp rules that are generated and added
	ArrayList<Symbol> tempVec; //temp vector for new rules
	ntRules = new ArrayList<Rule>();
	
	for(int i = 0; i < rules.size(); i++) {
	    curRule = rules.get(i);
	    curRhs = new LinkedList<Symbol>(curRule.output());
	    tempVec = new ArrayList<Symbol>();
	    label++;

	    if(curRhs.size() <=2) { //if its under the correct size, then add
		newRules.add(curRule);
		if(curRhs.size() > 1)
		    ntRules.add(curRule);
	    }
	    else{ 
		//remove first element from curRHS and place it in new rhs
		tempVec.add(curRhs.remove());
		//add new nonterminal that corresponds to rest of rule
		tempVec.add(new NonTerminal(label+"")); 
		
		//new rule has been created
		tempRule = new Rule(curRule.input(), tempVec); 
		newRules.add(tempRule);
		ntRules.add(tempRule);

		while(curRhs.size() > 2) { //apply same transformation to convert to grammars of size 2
		    tempVec = new ArrayList<Symbol>();
		    tempVec.add(curRhs.remove());
		    tempVec.add(new NonTerminal((label+1)+""));
		    
		    tempRule = new Rule(new NonTerminal(label+""), tempVec);
		    newRules.add(tempRule);
		    ntRules.add(tempRule);
		    label++; //increment label such that it is unique
		}
		//still two symbols left
		tempVec = new ArrayList<Symbol>();
		tempVec.add(curRhs.remove());
		tempVec.add(curRhs.remove());
		tempRule = new Rule(new NonTerminal(label+""), tempVec);
		newRules.add(tempRule);
		ntRules.add(tempRule);
	    }
	}

	rules = newRules;
    }

    //grabs the LHS symbol A of the form A -> right
    public Symbol getLHS( Symbol right) {
	Rule r; //temp rule
	for(int i = 0; i < rules.size(); i++) {
	    r = rules.get(i);
	    if(r.output().size() == 1 &&
	       r.output().get(0).equals(right))
		return r.input();
	}
	//if it doesn't exist
	return null;
    }

    //returns the nonterminal rules
    public ArrayList<Rule> getNT() { return ntRules; }

    //test code
    public static void main(String args[]) {
	CNF c = new CNF("simple.txt");
	System.out.println(c);
	ArrayList<Symbol> test = new ArrayList<Symbol>();

	test.add(new NonTerminal("0"));
	test.add(new NonTerminal("15"));
	
    }
}