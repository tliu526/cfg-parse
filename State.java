/*
 * A state holds a dotted rule and an index, which indicates at what point
 * in the parsing the dotted rule was inserted. Used in the Earley Recognizer.
 * (c) 2014 Tony Liu.
 */
public class State {
    //the rule stored in the state
    private DottedRule rule;
    //the index where the state was created
    private int index;

    public State (DottedRule r, int i) {
	rule = r;
	index = i;
    }

    public DottedRule getRule() { return rule; }

    public int getIndex() { return index; }

    public String toString() { return "(" + rule + ", " + index + ")"; }

    //checks equality by comparing both rules and indices
    public boolean equals(State other) {
	return rule.equals(other.getRule()) && index == other.getIndex();
    }
}