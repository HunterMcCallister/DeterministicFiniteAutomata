package fa.dfa;
import fa.State;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * DFA State class for its transitions and next states 
 */
public class DFAState extends State {
    private final Map<Character, DFAState> nextStates;

    public DFAState(String name) {
        super(name);
        this.nextStates = new LinkedHashMap<>();
    }

    /**
     * updates transition table
     */
    public void addTransition(char symbol, DFAState toState) {
        nextStates.put(symbol, toState);
    }

    /** gives us the next state*/
    public DFAState getTransition(char symbol) {
        return nextStates.get(symbol);
    }

    /** shows next states */
    public Map<Character, DFAState> getTransitions() {
        return Collections.unmodifiableMap(nextStates);
    }
}
