/*
 * This class defines the Earley parser. The Earley
 * parser takes a Grammar and parses any
 * string within the grammar.
 * Uses top-down processing and dynamic programming.
 * (c) 2014 Tony Liu.
 */
import java.util.ArrayList;

public class Earley extends Parser {
    private Grammar grammar;
    private ParseChart chart;

    //constructor, read in Grammar
    public Earley(Grammar g) { grammar = g; }

    /*
     * The "main" method that uses scan, predict, and complete to create
     * the ParseChart. 
     */
    public ParseChart parse(ArrayList<String> in) {
	//initialize the chart to the correct size
	chart = new ParseChart(in.size());
	Rule start = grammar.start();
	chart.add(0, new State(new DottedRule(start.input(), start.output()),0));
	ArrayList<State> curStates;
	
	for(int i = 0; i <= in.size(); i++) { //main loop, iterates over the input
	    curStates = chart.getStates(i);

	    for(int j = 0; j < curStates.size(); j++){//for each state in the state set
		State cur = curStates.get(j);

		if(!cur.getRule().isComplete()){ //if state not complete, scan/predict
		    
		    if(cur.getRule().peek().isTerminal()){ //if the next is Terminal
			if(i < in.size()) //ugly
			    scan(cur,i, in.get(i));
		    }
		    else predict(cur,i); //else if nonTerm, then predict matchings
		}
		else complete(cur, i); //state is complete
	    }
	}

	return chart;
    }
    
    /*
     * If the rule has the form (X -> a . Yb, j) where
     * Y is the left side of a rule, add (Y -> . c, k) to S(k)
     */
    private void predict(State state, int index) {
	Symbol next = state.getRule().peek();
	ArrayList<Rule> rules = grammar.getRules(next);
	DottedRule r; //current rule being analyzed
	State s; //temp state

	for(int i = 0; i < rules.size(); i++) {
	    //create new dotted rule with correct input and output
	    r = new DottedRule(rules.get(i).input(), rules.get(i).output());
	    s = new State(r, index);
      	    if(!chart.hasState(index, s))
		chart.add(index, s);
	}
    }

    /*
     * If b = word is the next terminal in the input stream,
     * with a rule of form (X -> a . bc, j),
     * add (X -> ab . c, j) to S(k+1)
     */
    private void scan(State state, int index, String word){
	DottedRule r = state.getRule();
	State next;

	//check if the next terminal in r matches word
	if(r.hasNext() && (r.peek().toString().equals(word))) {
	    r = state.getRule().next();
	    next = new State(r, state.getIndex());

	    if(!chart.hasState(index+1, next))
		chart.add(index+1, next);
	}
    }

    /*
     * If the rule has been completed (X -> y . , j), find states in S(j) of the form
     * (Y -> a . Xb, i) and add (Y -> aX . b, i) to S(k)
     */
    private void complete(State state, int index) {
	//the set of incremented states
	ArrayList<State> complete = new ArrayList<State>();
	//an augmented state
	State c; 

	if(state.getRule().isComplete()){
	    Symbol head = state.getRule().input();
	    ArrayList <State> states = chart.getStates(state.getIndex());
	    State curState;
	    DottedRule curRule;
	    
	    for (int i = 0; i < states.size(); i++) {
		curState = states.get(i);
		curRule = curState.getRule();
		if(curRule.hasNext()) {
		    //list of rules that can be added to S(k),their next symbol is head
		    if(curRule.peek().equals(head)) {
			complete.add(curState);
		    }
		}
	    } 

	    //add the incremented states from S(j) to S(k)
	    for (int i = 0; i < complete.size(); i++) {
		c = complete.get(i);
		c = new State(c.getRule().next(), c.getIndex());
		if(!chart.hasState(index,c)){
		    chart.add(index, c);
		}
	    }
	}	
    }

    //returns whether the input was grammatically correct in terms of the input
    public boolean isGrammatical(ArrayList<String> input) {
	parse(input);

	//last state in the chart
	ArrayList<State> states = chart.getStates(chart.numStates()-1);
	DottedRule r;
	
	//scan last state for start symbol $
	for (int i = 0; i < states.size(); i++) {
	    r = states.get(i).getRule();
	    if(r.input().toString().equals("$") && r.isComplete()) 
		return true;
	}
	return false;
    }
        
    //Test code
    public static void main(String args[]) {
	Grammar g = new Grammar("simple.txt");
	System.out.println(g);

	ArrayList<String> in = new ArrayList<String>();
	in.add("The");
	in.add("dog");
	in.add("flies");
	in.add("quickly");

	Earley p = new Earley(g);
	
	ParseChart c = p.parse(in);

       	System.out.println(c);
	System.out.println("Earley:");
	System.out.println(p.isGrammatical(in));

	DParser d = new DParser(g);

	System.out.println("DParse:");
	System.out.println(d.isGrammatical(in));
    } 
}