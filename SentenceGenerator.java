/*
 * A simple sentence generator that creates sentences according to the grammar.
 * (c) 2014 Tony Liu.
 */
import java.util.*;
import java.io.*;

public class SentenceGenerator {
    private Grammar grammar;

    public SentenceGenerator(Grammar g) { grammar = g; }

    //generates a random sentence using the grammar. May run forever if grammar is recursive
    public ArrayList<String> generate() {
	Random rand = new Random(); //RNG for picking rules
	Symbol current; //current nonterm that is being generated
	Stack<Symbol> stack = new Stack<Symbol>(); //stack of symbols to be processed
	ArrayList<String> sentence = new ArrayList<String>(); //the sentence to be returned
	ArrayList<Rule> rules; //rules that can be considered to be used
	Rule r; //a rule chosen at random

	//initializing 
	Rule start = grammar.start();
	stack.push(start.output().get(0));

	while (!stack.isEmpty()) {
	    current = stack.pop();
	    if (!current.isTerminal()){ //current is nonterm
		rules = grammar.getRules(current.toString());
		r = rules.get(rand.nextInt(rules.size())); //pick a random rule

		for(int i = r.output.size()-1; i >= 0; i--) {
		    stack.push(r.output().get(i)); //push all symbols in r 
		}
	    }	    
	    else sentence.add(current.toString()); //current is term, add to sentence
	}
	return sentence;
    }
    
    //returns a vector of strings based on reading in an input file
    public ArrayList<String> read(String file) {
	Scanner scan;
	String cur;
	ArrayList<String> sentence = new ArrayList<String>();
	try {
	    scan = new Scanner(new FileInputStream(file));
	    while(scan.hasNext()) {
		sentence.add(scan.next());
	    }
	}
	catch(IOException e) { 
	    System.out.println("Bad file name.");
	    return null;
	}
	return sentence;
    }

    //test code
    public static void main(String args[]) {
	Grammar g = new Grammar("simple.txt");
	System.out.println("CFG size: " + g.size());
	CNF cnf = new CNF("simple.txt");
	System.out.println("CNF size: " + cnf.size());

	SentenceGenerator gen = new SentenceGenerator(g);
	ArrayList<String> sent = gen.generate();
	System.out.println(sent);
	System.out.println("sentence length: " + sent.size());
	Earley e = new Earley(g);
	DParser d = new DParser(g);
	CYK c = new CYK(cnf);


	ParseChart p = e.parse(sent);
	System.out.println(p);
	
	System.out.println("Earley:");
	System.out.println(e.isGrammatical(sent));
	System.out.println("DFS:");
	System.out.println(d.isGrammatical(sent));
	System.out.println("CYK:");
	System.out.println(c.isGrammatical(sent));

    }
}