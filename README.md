# Exploring Effective Parsing of Context-Free Grammars

My CSCI 256: Algorithm Design & Analysis final project. Here, I explore CFG parsers, implementing a depth-first search parser, an Earley parser, and a CYK parser.

## Paper

The paper writeup can be found [here](tex/Earley.pdf).

## Implementation

Main files of interest:
 - `DParser.java`
 - `CYK.java`
 - `Earley.java`

Test files can be found in the `test_files` directory:
 - short.txt
 - medium.txt
 - long.txt
 - longer.txt
 - longest.txt
 - stress0.txt
 - stress1.txt
 - recursive.txt
 - simple.txt
 - haiku.txt

The main test files are essentially the same grammar, with a lengthened start rule to generate longer input strings.
All the grammars other than simple.txt and recursive.txt have been adapted from Steve Freund's grammars.

To run the same tests as presented in the paper:
1) compile all with: `javac *.java`
2) run with: `java Test "numTrials" "testFile"`

### Notes
DFS will not terminate on recursive.txt, and will not terminate for a very long time on stress0.txt and stress1.txt.

Try as I might, I could not get CYK to run in a reasonable amount of time on anything other than simple.txt and haiku.txt.
It may be because my CNF class converts grammars inefficiently, but efficient conversion of CFGs to Chomsky Normal Form is another
research topic in itself.
So, I leave the CYK implementation here for viewing purposes, but Test.java does not run CYK when performing
its tests.
