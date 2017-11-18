/*
 * A ParseChart is a 2D table that holds states (expressed as a pair) 
 * that need to 
 * be stored during the execution of the Earley parser. Implemented
 * as a ArrayList of ArrayLists because of the need for the table to grow
 * while the algorithm is executing.
 * (c) 2014 Tony Liu.
 */
import java.util.ArrayList;

public class ParseChart {
    private ArrayList<ArrayList<State>> chart;

    public ParseChart(int n) {
	chart = new ArrayList<ArrayList<State>>(n);
	//initializes to size of n+1
	for(int i = 0; i <= n; i++)
	    chart.add(new ArrayList<State>());
    }

    //returns a single set of states at a given point in the algorithm
    public ArrayList<State> getStates(int i) { return chart.get(i); }

    //returns the number of states in the chart
    public int numStates() { return chart.size(); }

    //inserts a new "column" of state into the chart
    public void addState() { chart.add(new ArrayList<State>()); }

    //attempts to add a State s into a given entry in the chart
    public void add(int i, State s) { chart.get(i).add(s); }

    //prints out each state sequentially
    public String toString() {
	String s = "";
	ArrayList<State> v;

	for (int i = 0; i < chart.size(); i++){
	    v = chart.get(i);
	    s += "State " + i + "\n";
	    for(int j = 0; j < v.size(); j++) {
		s += v.get(j) + "\n";
	    } 
	}
	return s;
    }	    

    //returns whether a state set already contains s
    public boolean hasState(int index, State s) {
	boolean hasState = false;
	ArrayList<State> states = getStates(index);
	for(int i = 0; i < states.size(); i++) {
	    if(s.equals(states.get(i))) hasState = true;
	}
	return hasState;
    }
}