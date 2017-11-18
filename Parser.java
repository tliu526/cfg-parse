/*
 * Abstract class that defines methods that need to be
 * implemented by a parser.
 * (c) 2014 Tony Liu.
 */
import java.util.ArrayList;

abstract class Parser {

    abstract boolean isGrammatical(ArrayList<String> s);

}