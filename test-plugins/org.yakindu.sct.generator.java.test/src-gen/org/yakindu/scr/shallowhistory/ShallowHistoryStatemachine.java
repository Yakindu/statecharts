package org.yakindu.scr.shallowhistory;


public class ShallowHistoryStatemachine implements IShallowHistoryStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean event1;
		
		public void raiseEvent1() {
			event1 = true;
		}
		
		private boolean event2;
		
		public void raiseEvent2() {
			event2 = true;
		}
		
		private boolean event3;
		
		public void raiseEvent3() {
			event3 = true;
		}
		
		private boolean event4;
		
		public void raiseEvent4() {
			event4 = true;
		}
		
		private boolean event5;
		
		public void raiseEvent5() {
			event5 = true;
		}
		
		private boolean event6;
		
		public void raiseEvent6() {
			event6 = true;
		}
		
		private boolean event7;
		
		public void raiseEvent7() {
			event7 = true;
		}
		
		private boolean event8;
		
		public void raiseEvent8() {
			event8 = true;
		}
		
		protected void clearEvents() {
			event1 = false;
			event2 = false;
			event3 = false;
			event4 = false;
			event5 = false;
			event6 = false;
			event7 = false;
			event8 = false;
		}
	}
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		mainRegion_State1,
		mainRegion_State2,
		mainRegion_State2__region0_State3,
		mainRegion_State2__region0_State4,
		mainRegion_State2__region0_State4__region0_State6,
		mainRegion_State2__region0_State4__region0_State7,
		mainRegion_State2__region0_State4__region0_State7__region0_State8,
		mainRegion_State2__region0_State4__region0_State7__region0_State9,
		mainRegion_State2__region0_State5,
		$NullState$
	};
	
	private State[] historyVector = new State[2];
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	
	public ShallowHistoryStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		for (int i = 0; i < 2; i++) {
			historyVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
				"The state machine needs to be initialized first by calling the init() function."
			);
		}
		enterSequence_mainRegion_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case mainRegion_State1:
				mainRegion_State1_react(true);
				break;
			case mainRegion_State2__region0_State3:
				mainRegion_State2__region0_State3_react(true);
				break;
			case mainRegion_State2__region0_State4__region0_State6:
				mainRegion_State2__region0_State4__region0_State6_react(true);
				break;
			case mainRegion_State2__region0_State4__region0_State7__region0_State8:
				mainRegion_State2__region0_State4__region0_State7__region0_State8_react(true);
				break;
			case mainRegion_State2__region0_State4__region0_State7__region0_State9:
				mainRegion_State2__region0_State4__region0_State7__region0_State9_react(true);
				break;
			case mainRegion_State2__region0_State5:
				mainRegion_State2__region0_State5_react(true);
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
	public void exit() {
		exitSequence_mainRegion();
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
		case mainRegion_State1:
			return stateVector[0] == State.mainRegion_State1;
		case mainRegion_State2:
			return stateVector[0].ordinal() >= State.
					mainRegion_State2.ordinal()&& stateVector[0].ordinal() <= State.mainRegion_State2__region0_State5.ordinal();
		case mainRegion_State2__region0_State3:
			return stateVector[0] == State.mainRegion_State2__region0_State3;
		case mainRegion_State2__region0_State4:
			return stateVector[0].ordinal() >= State.
					mainRegion_State2__region0_State4.ordinal()&& stateVector[0].ordinal() <= State.mainRegion_State2__region0_State4__region0_State7__region0_State9.ordinal();
		case mainRegion_State2__region0_State4__region0_State6:
			return stateVector[0] == State.mainRegion_State2__region0_State4__region0_State6;
		case mainRegion_State2__region0_State4__region0_State7:
			return stateVector[0].ordinal() >= State.
					mainRegion_State2__region0_State4__region0_State7.ordinal()&& stateVector[0].ordinal() <= State.mainRegion_State2__region0_State4__region0_State7__region0_State9.ordinal();
		case mainRegion_State2__region0_State4__region0_State7__region0_State8:
			return stateVector[0] == State.mainRegion_State2__region0_State4__region0_State7__region0_State8;
		case mainRegion_State2__region0_State4__region0_State7__region0_State9:
			return stateVector[0] == State.mainRegion_State2__region0_State4__region0_State7__region0_State9;
		case mainRegion_State2__region0_State5:
			return stateVector[0] == State.mainRegion_State2__region0_State5;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public void raiseEvent1() {
		sCInterface.raiseEvent1();
	}
	
	public void raiseEvent2() {
		sCInterface.raiseEvent2();
	}
	
	public void raiseEvent3() {
		sCInterface.raiseEvent3();
	}
	
	public void raiseEvent4() {
		sCInterface.raiseEvent4();
	}
	
	public void raiseEvent5() {
		sCInterface.raiseEvent5();
	}
	
	public void raiseEvent6() {
		sCInterface.raiseEvent6();
	}
	
	public void raiseEvent7() {
		sCInterface.raiseEvent7();
	}
	
	public void raiseEvent8() {
		sCInterface.raiseEvent8();
	}
	
	/* 'default' enter sequence for state State1 */
	private void enterSequence_mainRegion_State1_default() {
		nextStateIndex = 0;
		stateVector[0] = State.mainRegion_State1;
	}
	
	/* 'default' enter sequence for state State2 */
	private void enterSequence_mainRegion_State2_default() {
		enterSequence_mainRegion_State2__region0_default();
	}
	
	/* 'default' enter sequence for state State3 */
	private void enterSequence_mainRegion_State2__region0_State3_default() {
		nextStateIndex = 0;
		stateVector[0] = State.mainRegion_State2__region0_State3;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state State4 */
	private void enterSequence_mainRegion_State2__region0_State4_default() {
		enterSequence_mainRegion_State2__region0_State4__region0_default();
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state State6 */
	private void enterSequence_mainRegion_State2__region0_State4__region0_State6_default() {
		nextStateIndex = 0;
		stateVector[0] = State.mainRegion_State2__region0_State4__region0_State6;
	}
	
	/* 'default' enter sequence for state State7 */
	private void enterSequence_mainRegion_State2__region0_State4__region0_State7_default() {
		enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_default();
	}
	
	/* 'default' enter sequence for state State8 */
	private void enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8_default() {
		nextStateIndex = 0;
		stateVector[0] = State.mainRegion_State2__region0_State4__region0_State7__region0_State8;
		
		historyVector[1] = stateVector[0];
	}
	
	/* 'default' enter sequence for state State9 */
	private void enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9_default() {
		nextStateIndex = 0;
		stateVector[0] = State.mainRegion_State2__region0_State4__region0_State7__region0_State9;
		
		historyVector[1] = stateVector[0];
	}
	
	/* 'default' enter sequence for state State5 */
	private void enterSequence_mainRegion_State2__region0_State5_default() {
		nextStateIndex = 0;
		stateVector[0] = State.mainRegion_State2__region0_State5;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for region mainRegion */
	private void enterSequence_mainRegion_default() {
		react_ShallowHistory_mainRegion__entry_Default();
	}
	
	/* 'default' enter sequence for region null */
	private void enterSequence_mainRegion_State2__region0_default() {
		react_ShallowHistory_mainRegion_State2__region0__entry_Default();
	}
	
	/* shallow enterSequence with history in child null */
	private void shallowEnterSequence_mainRegion_State2__region0() {
		switch (historyVector[0]) {
		case mainRegion_State2__region0_State3:
			enterSequence_mainRegion_State2__region0_State3_default();
			break;
		case mainRegion_State2__region0_State4__region0_State6:
			enterSequence_mainRegion_State2__region0_State4_default();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State8:
			enterSequence_mainRegion_State2__region0_State4_default();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State9:
			enterSequence_mainRegion_State2__region0_State4_default();
			break;
		case mainRegion_State2__region0_State5:
			enterSequence_mainRegion_State2__region0_State5_default();
			break;
		default:
			break;
		}
	}
	
	/* 'default' enter sequence for region null */
	private void enterSequence_mainRegion_State2__region0_State4__region0_default() {
		react_ShallowHistory_mainRegion_State2__region0_State4__region0__entry_Default();
	}
	
	/* 'default' enter sequence for region null */
	private void enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_default() {
		react_ShallowHistory_mainRegion_State2__region0_State4__region0_State7__region0__entry_Default();
	}
	
	/* shallow enterSequence with history in child null */
	private void shallowEnterSequence_mainRegion_State2__region0_State4__region0_State7__region0() {
		switch (historyVector[1]) {
		case mainRegion_State2__region0_State4__region0_State7__region0_State8:
			enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8_default();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State9:
			enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9_default();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for state State1 */
	private void exitSequence_mainRegion_State1() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State2 */
	private void exitSequence_mainRegion_State2() {
		exitSequence_mainRegion_State2__region0();
	}
	
	/* Default exit sequence for state State3 */
	private void exitSequence_mainRegion_State2__region0_State3() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State4 */
	private void exitSequence_mainRegion_State2__region0_State4() {
		exitSequence_mainRegion_State2__region0_State4__region0();
	}
	
	/* Default exit sequence for state State6 */
	private void exitSequence_mainRegion_State2__region0_State4__region0_State6() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State7 */
	private void exitSequence_mainRegion_State2__region0_State4__region0_State7() {
		exitSequence_mainRegion_State2__region0_State4__region0_State7__region0();
	}
	
	/* Default exit sequence for state State8 */
	private void exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State9 */
	private void exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state State5 */
	private void exitSequence_mainRegion_State2__region0_State5() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region mainRegion */
	private void exitSequence_mainRegion() {
		switch (stateVector[0]) {
		case mainRegion_State1:
			exitSequence_mainRegion_State1();
			break;
		case mainRegion_State2__region0_State3:
			exitSequence_mainRegion_State2__region0_State3();
			break;
		case mainRegion_State2__region0_State4__region0_State6:
			exitSequence_mainRegion_State2__region0_State4__region0_State6();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State8:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State9:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9();
			break;
		case mainRegion_State2__region0_State5:
			exitSequence_mainRegion_State2__region0_State5();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region null */
	private void exitSequence_mainRegion_State2__region0() {
		switch (stateVector[0]) {
		case mainRegion_State2__region0_State3:
			exitSequence_mainRegion_State2__region0_State3();
			break;
		case mainRegion_State2__region0_State4__region0_State6:
			exitSequence_mainRegion_State2__region0_State4__region0_State6();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State8:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State9:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9();
			break;
		case mainRegion_State2__region0_State5:
			exitSequence_mainRegion_State2__region0_State5();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region null */
	private void exitSequence_mainRegion_State2__region0_State4__region0() {
		switch (stateVector[0]) {
		case mainRegion_State2__region0_State4__region0_State6:
			exitSequence_mainRegion_State2__region0_State4__region0_State6();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State8:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State9:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region null */
	private void exitSequence_mainRegion_State2__region0_State4__region0_State7__region0() {
		switch (stateVector[0]) {
		case mainRegion_State2__region0_State4__region0_State7__region0_State8:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8();
			break;
		case mainRegion_State2__region0_State4__region0_State7__region0_State9:
			exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_ShallowHistory_mainRegion__entry_Default() {
		enterSequence_mainRegion_State1_default();
	}
	
	/* Default react sequence for shallow history entry  */
	private void react_ShallowHistory_mainRegion_State2__region0__entry_Default() {
		/* Enter the region with shallow history */
		if (historyVector[0] != State.$NullState$) {
			shallowEnterSequence_mainRegion_State2__region0();
		} else {
			enterSequence_mainRegion_State2__region0_State3_default();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_ShallowHistory_mainRegion_State2__region0_State4__region0__entry_Default() {
		enterSequence_mainRegion_State2__region0_State4__region0_State6_default();
	}
	
	/* Default react sequence for shallow history entry  */
	private void react_ShallowHistory_mainRegion_State2__region0_State4__region0_State7__region0__entry_Default() {
		/* Enter the region with shallow history */
		if (historyVector[1] != State.$NullState$) {
			shallowEnterSequence_mainRegion_State2__region0_State4__region0_State7__region0();
		} else {
			enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8_default();
		}
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean mainRegion_State1_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (sCInterface.event1) {
					exitSequence_mainRegion_State1();
					enterSequence_mainRegion_State2_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (sCInterface.event2) {
					exitSequence_mainRegion_State2();
					enterSequence_mainRegion_State1_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2__region0_State3_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (mainRegion_State2_react(try_transition)==false) {
				if (sCInterface.event3) {
					exitSequence_mainRegion_State2__region0_State3();
					enterSequence_mainRegion_State2__region0_State4_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2__region0_State4_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (mainRegion_State2_react(try_transition)==false) {
				if (sCInterface.event4) {
					exitSequence_mainRegion_State2__region0_State4();
					enterSequence_mainRegion_State2__region0_State5_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2__region0_State4__region0_State6_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (mainRegion_State2__region0_State4_react(try_transition)==false) {
				if (sCInterface.event5) {
					exitSequence_mainRegion_State2__region0_State4__region0_State6();
					enterSequence_mainRegion_State2__region0_State4__region0_State7_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2__region0_State4__region0_State7_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (mainRegion_State2__region0_State4_react(try_transition)==false) {
				if (sCInterface.event6) {
					exitSequence_mainRegion_State2__region0_State4__region0_State7();
					enterSequence_mainRegion_State2__region0_State4__region0_State6_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2__region0_State4__region0_State7__region0_State8_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (mainRegion_State2__region0_State4__region0_State7_react(try_transition)==false) {
				if (sCInterface.event7) {
					exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8();
					enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2__region0_State4__region0_State7__region0_State9_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (mainRegion_State2__region0_State4__region0_State7_react(try_transition)==false) {
				if (sCInterface.event8) {
					exitSequence_mainRegion_State2__region0_State4__region0_State7__region0_State9();
					enterSequence_mainRegion_State2__region0_State4__region0_State7__region0_State8_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean mainRegion_State2__region0_State5_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (mainRegion_State2_react(try_transition)==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
}
