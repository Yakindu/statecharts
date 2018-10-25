package org.yakindu.scr.enterstate;

public class EnterStateStatemachine implements IEnterStateStatemachine {

	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e;
		
		public void raiseE() {
			e = true;
		}
		
		private boolean f;
		
		public void raiseF() {
			f = true;
		}
		
		private boolean g;
		
		public void raiseG() {
			g = true;
		}
		
		protected void clearEvents() {
			e = false;
			f = false;
			g = false;
		}
	}
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		r_A,
		r_B,
		r_B_r_E,
		r_B_r_F,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	public EnterStateStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		}
		enterSequence_r_default();
	}
	
	public void exit() {
		exitSequence_r();
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
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCInterface.clearEvents();
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case r_A:
			return stateVector[0] == State.r_A;
		case r_B:
			return stateVector[0].ordinal() >= State.
					r_B.ordinal()&& stateVector[0].ordinal() <= State.r_B_r_F.ordinal();
		case r_B_r_E:
			return stateVector[0] == State.r_B_r_E;
		case r_B_r_F:
			return stateVector[0] == State.r_B_r_F;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public void raiseE() {
		sCInterface.raiseE();
	}
	
	public void raiseF() {
		sCInterface.raiseF();
	}
	
	public void raiseG() {
		sCInterface.raiseG();
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_r_A_default() {
		nextStateIndex = 0;
		stateVector[0] = State.r_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_r_B_default() {
		enterSequence_r_B_r_default();
	}
	
	/* 'f' enter sequence for state B */
	private void enterSequence_r_B_f() {
		enterSequence_r_B_r_f();
	}
	
	/* 'g' enter sequence for state B */
	private void enterSequence_r_B_g() {
		enterSequence_r_B_r_default();
	}
	
	/* 'default' enter sequence for state E */
	private void enterSequence_r_B_r_E_default() {
		nextStateIndex = 0;
		stateVector[0] = State.r_B_r_E;
	}
	
	/* 'default' enter sequence for state F */
	private void enterSequence_r_B_r_F_default() {
		nextStateIndex = 0;
		stateVector[0] = State.r_B_r_F;
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_r_default() {
		react_r__entry_Default();
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_r_B_r_default() {
		react_r_B_r__entry_Default();
	}
	
	/* 'f' enter sequence for region r */
	private void enterSequence_r_B_r_f() {
		react_r_B_r_f();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_r_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state E */
	private void exitSequence_r_B_r_E() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state F */
	private void exitSequence_r_B_r_F() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_r() {
		switch (stateVector[0]) {
		case r_A:
			exitSequence_r_A();
			break;
		case r_B_r_E:
			exitSequence_r_B_r_E();
			break;
		case r_B_r_F:
			exitSequence_r_B_r_F();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_r_B_r() {
		switch (stateVector[0]) {
		case r_B_r_E:
			exitSequence_r_B_r_E();
			break;
		case r_B_r_F:
			exitSequence_r_B_r_F();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_r__entry_Default() {
		enterSequence_r_A_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_r_B_r__entry_Default() {
		enterSequence_r_B_r_E_default();
	}
	
	/* Default react sequence for initial entry f */
	private void react_r_B_r_f() {
		enterSequence_r_B_r_F_default();
	}
	
	private boolean react(boolean try_transition) {
		return false;
	}
	
	private boolean r_A_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react(try_transition)==false) {
				if (sCInterface.e) {
					exitSequence_r_A();
					enterSequence_r_B_default();
				} else {
					if (sCInterface.f) {
						exitSequence_r_A();
						enterSequence_r_B_f();
					} else {
						if (sCInterface.g) {
							exitSequence_r_A();
							enterSequence_r_B_g();
						} else {
							did_transition = false;
						}
					}
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean r_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react(try_transition)==false) {
				did_transition = false;
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean r_B_r_E_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (r_B_react(try_transition)==false) {
				did_transition = false;
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean r_B_r_F_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (r_B_react(try_transition)==false) {
				did_transition = false;
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case r_A:
				r_A_react(true);
				break;
			case r_B_r_E:
				r_B_r_E_react(true);
				break;
			case r_B_r_F:
				r_B_r_F_react(true);
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
}
