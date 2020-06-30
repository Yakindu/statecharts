/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.parenthesis;


public class ParenthesisStatemachine implements IParenthesisStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private long erg;
		
		public long getErg() {
			return erg;
		}
		
		public void setErg(long value) {
			this.erg = value;
		}
		
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		mainRegion_A,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public ParenthesisStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		
		
		sCInterface.setErg(0);
		
		isExecuting = false;
	}
	
	public void enter() {
		if (!initialized)
			throw new IllegalStateException(
			        "The state machine needs to be initialized first by calling the init() function.");
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		enterSequence_mainRegion_default();
		isExecuting = false;
	}
	
	public void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence_mainRegion();
		isExecuting = false;
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
			        "The state machine needs to be initialized first by calling the init() function.");
		
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case mainRegion_A:
				mainRegion_A_react(true);
				break;
			default:
				// $NullState$
			}
		}
		
		
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case mainRegion_A:
			return stateVector[0] == State.mainRegion_A;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public long getErg() {
		return sCInterface.getErg();
	}
	
	public void setErg(long value) {
		sCInterface.setErg(value);
	}
	
	/* Entry action for state 'A'. */
	private void entryAction_mainRegion_A() {
		sCInterface.setErg((4 * ((3 - 1))));
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_mainRegion_A_default() {
		entryAction_mainRegion_A();
		nextStateIndex = 0;
		stateVector[0] = State.mainRegion_A;
	}
	
	/* 'default' enter sequence for region mainRegion */
	private void enterSequence_mainRegion_default() {
		react_mainRegion__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_mainRegion_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region mainRegion */
	private void exitSequence_mainRegion() {
		switch (stateVector[0]) {
		case mainRegion_A:
			exitSequence_mainRegion_A();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_mainRegion__entry_Default() {
		enterSequence_mainRegion_A_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean mainRegion_A_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
}
