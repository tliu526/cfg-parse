/*
 * A simple Grammar parser that uses DFS to generate the parse chart.
 * (c) 2014 Tony Liu.
 */
import java.util.*;

public class DParser extends Parser{
    private Grammar grammar;
 
    public DParser(Grammar g) {	
	grammar = g; 
    }
    /*
    //main parsing method, returns boolean on whether the input is grammatical
    public boolean isGrammatical(ArrayList<String> input) {

	int index = 0; 	//indicates where in the input parsing has reached
	DottedRule current; //current search state
	ArrayList<Rule> rules;
	DottedRule newRule;

	//initialize with start rule
	agenda.push(new DottedRule(grammar.start().input(), grammar.start().output()));
	
	//main loop, continue while there are states to be searched
	while(!agenda.isEmpty()) {
	    current = agenda.pop();
	    
	    //check if all Strings in the input have been accepted
	    if(index == input.size()-1) return true;

	    if(current.hasNext()) {		
		// if next Symbol is Term, check if input corresponds to a grammar rule
		if(current.peek().isTerminal()) {
		    if(current.peek().toString().equals(input.get(index))) {
			agenda.push(current.next());
			index++;
		    }

		}
		else { //next symbol is nonTerm, push all rules that match it onto stack
		    rules = grammar.getRules(current.peek().toString()); //grab rules that match the nonTerm
		    agenda.push(current.next()); //push the incremented rule

		    for (int i = 0; i < rules.size(); i++) {
			newRule = new DottedRule(rules.get(i).input(),rules.get(i).output());
			agenda.push(newRule);
		    }
		}
	    }
	}
	//the agenda has no more search states but a parse for the input wasn't found
	return false;
    }
  
    public boolean isGrammatical(ArrayList<String> input) {
	
	int index = 0; //indicates where in the input parsing has reached
	State current; //current search state; index is next input String to be parsed
	ArrayList<Rule> rules; //temp var to hold rules
	DottedRule curRule; //temp var
	
	curRule = new DottedRule(grammar.start().input(), grammar.start().output());
	agenda.push(new State(curRule, 0));
	
	while(!agenda.isEmpty()) {
	    current = agenda.pop();
	    curRule = current.getRule();
	    
	    //if index reaches input.size, then all of input has been parsed
	    if(index == input.size()) return true;
	    
	    if(curRule.hasNext()) {		
		// if next Symbol is Term, check if input corresponds to a grammar rule
		if(curRule.peek().isTerminal()) {
		    
		    System.out.println("rule: " + curRule.peek());
		    System.out.println("input: " + input.get(index));
		    
		    if(curRule.peek().toString().equals(input.get(current.getIndex()))){
			index = current.getIndex();
			index++;
			agenda.push(new State(curRule.next(), index));
			//	index++;
		    }
		}
		else { //next symbol is nonTerm, push all rules that match it onto stack
		 
		    rules = grammar.getRules(curRule.peek().toString()); //grab rules that match the nonTerm
		    agenda.push(new State(curRule.next(), index)); //push the incremented rule with same index
		    
		    for (int i = 0; i < rules.size(); i++) { //push all the rules with matching input
			curRule = new DottedRule(rules.get(i).input(),rules.get(i).output());
			agenda.push(new State(curRule,index)); 
		    }
		}
	    }
	}
	//agenda is empty but not all input has been parsed
	return false;
    }
    //poop luvs  nee. neeluvs na. derfor poop luv peepee
*/
    //main parsing method
    public boolean isGrammatical(ArrayList<String> input) {
	Stack<LinkedList<Symbol>> agenda = new Stack<LinkedList<Symbol>>();
	LinkedList<Symbol> curState; //current partial solution
	ArrayList<Rule> rules;
	LinkedList<Symbol> temp;
	boolean match; //indicates whether partial solution matches input
	int index = 0;
	//initialize
	agenda.push(new LinkedList<Symbol>(grammar.start().output()));
	
	while(!agenda.isEmpty()) {
	    match = true;
	    index = 0;
	    curState = agenda.pop();
	    
	    //check if the terminals match the input
	    while(index < curState.size() && curState.get(index).isTerminal()) {
		if(index >= input.size() || 
		   !curState.get(index).toString().equals(input.get(index)))
		    {match = false;}
		index++;
	    }
	    //if all terminals correspond, return success
	    if(index == input.size() && match) {
		return true;
	    }
	    
	    //else, if curState is partial match with nonterms
	    else if(index < curState.size() && match){
		rules = grammar.getRules(curState.get(index));
		curState.remove(index);
		for(int i = 0; i < rules.size(); i++) { //push all possible productions
		    temp = new LinkedList<Symbol>(curState);
		    temp.addAll(index, rules.get(i).output());
		    agenda.push(temp);
		}
	    }
	}
	return false;
    }
}