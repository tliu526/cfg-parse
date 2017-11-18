/*
 * An implementation of the CYK algorithm, which utilizes bottom-up parsing and dynamic programming.
 * (c) 2014 Tony Liu.
 */
import java.util.*;

public class CYK extends Parser{
    private CNF grammar;
    private HashMap<String,Boolean>[][] table; //the table we are building
    
    //pre: grammar g is in CNF
    public CYK(CNF g) { grammar = g; }
    
    //checks whether the given input is part of language
    @SuppressWarnings("unchecked")
    public boolean isGrammatical(ArrayList<String> input) {
	HashMap<String,Boolean> temp; //temporary map
	table = (HashMap<String,Boolean>[][]) new HashMap[input.size()+1][input.size()+1];
	ArrayList<Rule> rules = grammar.getNT();
	Symbol s;

	//begin by "seeding" table with input symbols and rules that correspond
	//to them
	for(int i = 1; i <= input.size(); i++) { 
	    s = grammar.getLHS(new Terminal(input.get(i-1)));
	    temp = new HashMap<String,Boolean>();
	    temp.put(s.toString(),true);
	    table[i-1][i] = temp;
	}
	
	//move through the "half" table, left to right, bottom to up
	for(int j = 1; j <= input.size(); j++) {
	    for(int i = j-1; i >= 0; i--) {

		for(int k = i+1; k < j; k++) { //k is our partition
		    temp = new HashMap<String,Boolean>();
		    for(int l=0; l < rules.size(); l++) {
			//Given rule A -> B C
			Rule r = rules.get(l);
			//B and C exist within the partitions
			if(table[i][k].get(r.output().get(0).toString()) != null &&
			   table[k][j].get(r.output().get(1).toString()) != null) {
			    temp.put(r.input().toString(),true);
			}
		    }
		    //if the table entry is uninitialized
		    if(table[i][j] == null) {
			table[i][j] = temp;
		    }
		    else table[i][j].putAll(temp);
		}
	    }
	}
	
	//check if [0][size] entry contains start symbol
	temp = table[0][input.size()];

	if(temp.get("$") != null) 
	    return true;
	
	return false;
    }

    public static void main(String args[]) {
	CNF grammar = new CNF("short.txt");
	System.out.println(grammar.getNT());
	
	
    }

   
}