# DeterministicFiniteAutomata

****************
* Project 1 - DFA
* CS 361 - Theory of Computation
* 2/19/2026
* Hunter McCallister and Diego Dominguez
****************


OVERVIEW:

 This program models a Deterministic Finite Automaton (DFA). It lets you
 build a DFA by adding states, alphabet symbols, and transitions, then
 simulate it on input strings to check if they are accepted or rejected.


INCLUDED FILES:

 * fa/FAInterface.java - interface for all finite automata
 * fa/State.java - abstract base class for automaton states
 * fa/dfa/DFAInterface.java - interface for DFA-specific methods
 * fa/dfa/DFA.java - main DFA implementation 
 * fa/dfa/DFAState.java - DFA state with transition map 
 * test/dfa/DFATest.java - JUnit test suite
 * README - this file


COMPILING AND RUNNING:

 Make sure the fa folder (which contains State.java, FAInterface.java,
 and the dfa subfolder with DFA.java, DFAState.java, and DFAInterface.java)
 and the test folder (which contains dfa/DFATest.java) are all in the
 same top-level directory.

 From that top-level directory, compile the test file (which will also
 compile all the dependencies) with the command:
 $ javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java

 Run the tests with the following command (all on one line):
 $ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/hamcrest.jar org.junit.runner.JUnitCore test.dfa.DFATest

 Console output will show the results of each test after it finishes.
 There are no command-line arguments needed.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 The program is split across two main classes we implemented: DFA and DFAState.

 DFAState extends the abstract State class. Each DFAState holds its own row
 of the transition table as a LinkedHashMap that maps input symbols to
 destination states. This way each state knows where to go on a given symbol.

 DFA implements DFAInterface (which extends FAInterface). It stores the
 5-tuple (Q, Sigma, delta, q0, F) using LinkedHashSets for the states,
 alphabet, and final states. We used LinkedHashSets because they preserve
 insertion order, which matters for the toString output. The transition
 function (delta) is distributed across the individual DFAState objects
 rather than stored in one big table in DFA.

 The accepts method works by starting at q0 and following transitions
 character by character through the input string. If it ends up in a
 final state, the string is accepted. The string "e" is treated as the
 empty string (epsilon).

 The swap method creates a deep copy of the entire DFA but swaps which
 transitions go with which symbols for the two given symbols. It builds
 a completely new DFA with new state objects so the original is not affected.


TESTING:

 We tested by writing the code and then running the provided DFATest JUnit
 tests until all 18 passed. The tests cover three different DFAs with
 various configurations and check instantiation, correctness of states,
 string acceptance/rejection, toString formatting and the swap method.
 We just kept iterating on the code until everything was green.


DISCUSSION:

 The trickiest part was probably getting toString to match the expected
 format and making sure swap did a proper deep copy without messing up
 the original DFA. The swap method was a bit confusing at first but once
 we realized it is basically just rebuilding the DFA from scratch with
 the symbols flipped, it made sense. Also had to remember to use .equals()
 for string comparison instead of == which tripped us up for a sec.



SOURCES:

 Claude Code (Anthropic AI), 19 February 2026. Used for debugging help
 and guidance on structuring the DFA class implementation. Also on how to compile and test locally with JUnit.
