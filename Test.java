/*
 * A class where tests are run on the Earley and depth-first parsers.
 * (c) 2014 Tony Liu.
 */
import java.util.*;

public class Test {

    /*
     * Runs a given number of trials on the input grammar.
     */
    public static void runTest(int trials, String grammar) {
	//stores all the sentences to be parsed
	ArrayList <ArrayList<String>> sentences = new ArrayList<ArrayList<String>>(); 
	Grammar g = new Grammar(grammar);
	SentenceGenerator gen = new SentenceGenerator(g);
	ArrayList <String> temp;
	double inSize = 0; //the average input length
	double dSpeed = 0; //the DFS speed
	double eSpeed = 0; //the Earley speed

	System.out.println("-----------------------------------");
	System.out.println("Number of trials: " + trials);
	System.out.println("Grammar size: " + g.size());
	//initialize sentences
	for(int i = 0; i < trials; i++) {
	    temp = gen.generate();
	    inSize += temp.size();
	    sentences.add(temp);
	}
	inSize = inSize / trials;
	System.out.println("Average input size: " + inSize);
	
	//run tests
	DParser d = new DParser(g);
	Earley e = new Earley(g);

	System.out.println("Running Earley...");

	eSpeed = runParse(trials, sentences, e);
	System.out.println("Earley Parser speed: " + eSpeed);	

	System.out.println();
	System.out.println("Running DFS Parser...");

	dSpeed = runParse(trials, sentences, d);
	System.out.println("Depth-first Parser speed: " + dSpeed);
	System.out.println("-----------------------------------");
    }

    //runs a parser on a ArrayList of input sentences and returns an average speed
    public static double runParse(int trials, ArrayList<ArrayList<String>> sentences, Parser p) {
	double speed = 0; //the average speed of the parser in ms
	double start = 0; //start time
	double stop = 0; //stop time for parsing

	for(int i = 0; i < trials; i++) {
	    start = System.currentTimeMillis();
	    if(!p.isGrammatical(sentences.get(i))){
		System.out.println("failed test");
		break;
	    }
	    else stop = System.currentTimeMillis();
	    speed += stop-start;
	}
	speed /= trials;

	return speed;
    }

    public static void main(String args[]) {

	if(args.length == 2){
	    int trials = Integer.parseInt(args[0]);
	    String in = args[1];
	    runTest(trials, in );
	}
	else System.out.println("Usage: n g, where n is number of Trials and g is an input grammar");
    }
}