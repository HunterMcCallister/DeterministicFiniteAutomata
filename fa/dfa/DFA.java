package fa.dfa;

import fa.State;
import java.util.LinkedHashSet;
import java.util.Set;

public class DFA implements DFAInterface {

    // 5-tuple: (Q, Sigma, delta, q0, F)
    private LinkedHashSet<Character> sigma; // Sigma
    private LinkedHashSet<DFAState> states; // Q
    private DFAState startState; // q0
    private LinkedHashSet<DFAState> finalStates; // F
    // delta is represented in DFAState class as a map of transitions

    // "The purpose of the constructor is to initialize the instance variables."
    public DFA() {
        this.sigma = new LinkedHashSet<>();
        this.states = new LinkedHashSet<>();
        startState = null;
        this.finalStates = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        if (getState(name) != null) {
            return false; // State with this name already exists
        } else {
            DFAState newState = new DFAState(name);
            states.add(newState);
            return true; // New state created
        }
    }

    @Override
    public boolean setFinal(String name) {
        DFAState state = getStateByName(name);
        if (state == null) {
            return false; // does not exist
        } else {
            finalStates.add(state);
            return true; // state marked as final successfully
        }
    }

    @Override
    public boolean setStart(String name) {
        DFAState state = getStateByName(name);
        if (state == null) {
            return false; // does not exist
        } else {
            startState = state;
            return true; // start state set successfully
        }
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        if (s.equals("e")) {
            return finalStates.contains(startState); // check if start state is accepting for empty string
        }
        DFAState currentState = startState;
        for (int i = 0; i < s.length(); i++) {
            currentState = currentState.getTransition(s.charAt(i));
            if (currentState == null) {
                return false; // no transition rejects the string
            }
        }
        return finalStates.contains(currentState); // accepted
    }

    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    @Override
    public State getState(String name) {
        return getStateByName(name);
    }

    @Override
    public boolean isFinal(String name) {
        DFAState state = getStateByName(name);
        return state != null && finalStates.contains(state);
    }

    @Override
    public boolean isStart(String name) {
        return startState != null && startState.getName().equals(name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        DFAState from = getStateByName(fromState);
        DFAState to = getStateByName(toState);
        if (from == null || to == null || !sigma.contains(onSymb)) {
            return false; // one of them does not exist or symbol is not in sigma
        } else {
            from.addTransition(onSymb, to);
            return true; // transition added successfully
        }
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA swappedDFA = new DFA();

        // Copy alphabet
        for (char c : sigma) {
            swappedDFA.addSigma(c);
        }
        // Copy states
        for (DFAState state : states) {
            swappedDFA.addState(state.getName());
        }
        // set the new start state to the same as the old one
        swappedDFA.setStart(startState.getName());

        // set the new final states to the same as the old ones
        for (DFAState finalState : finalStates) {
            swappedDFA.setFinal(finalState.getName());
        }

        for (DFAState state : states) {
            for (char c : sigma) {
                DFAState dest = state.getTransition(c);
                if (dest != null) {
                    char swappedSymbol;
                    if (c == symb1) {
                        swappedSymbol = symb2;
                    } else if (c == symb2) {
                        swappedSymbol = symb1;
                    } else {
                        swappedSymbol = c; // no swap
                    }
                    swappedDFA.addTransition(state.getName(), dest.getName(), swappedSymbol);
                }
            }
        }

        return swappedDFA;
    }

    /**
     * Helper method to get a state by its name.
     * 
     * @param name of the state to find
     * @return the DFAState object with the given name, or null if it does not exist
     */
    private DFAState getStateByName(String name) {
        for (DFAState state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null; // does not exist
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Q
        sb.append(" Q = {");
        for (DFAState state : states) {
            sb.append(" ").append(state.getName());
        }
        sb.append(" }\n");

        // Sigma
        sb.append("Sigma = {");
        for (char c : sigma) {
            sb.append(" ").append(c);
        }
        sb.append(" }\n");

        // delta
        sb.append("delta =\n");
        sb.append("\t\t");
        for (char c : sigma) {
            sb.append(c).append("\t");
        }
        sb.append("\n");
        for (DFAState state : states) {
            sb.append("\t").append(state.getName());
            for (char c : sigma) {
                DFAState dest = state.getTransition(c);
                sb.append("\t").append(dest.getName());
            }
            sb.append("\n");
        }

        // q0
        sb.append("q0 = ").append(startState.getName()).append("\n");

        // F
        sb.append("F = {");
        for (DFAState state : finalStates) {
            sb.append(" ").append(state.getName());
        }
        sb.append(" }");

        return sb.toString();
    }

}
