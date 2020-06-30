/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.internaleventlifecycle;


public class InternalEventLifeCycleStatemachine implements IInternalEventLifeCycleStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e;
		
		
		public void raiseE() {
			e = true;
		}
		
		private boolean f;
		
		
		public void raiseF() {
			f = true;
		}
		
		protected void clearEvents() {
			e = false;
			f = false;
		}
	}
	
	
	private static class SCInterfaceEvBuf {
		private boolean e;
		private boolean f;
	}
	private static class InternalEventLifeCycleStatemachineInternalEvBuf {
		private boolean i1;
		private boolean i2;
	}
	private static class InternalEventLifeCycleStatemachineEvBuf {
		private SCInterfaceEvBuf iface = new SCInterfaceEvBuf();
		private InternalEventLifeCycleStatemachineInternalEvBuf internal = new InternalEventLifeCycleStatemachineInternalEvBuf();
	}
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		r1_A,
		r1_B,
		r2_C,
		r2_D,
		$NullState$
	};
	
	private final State[] stateVector = new State[2];
	
	private int nextStateIndex;
	
	private boolean i1;
	private boolean i2;
	private InternalEventLifeCycleStatemachineEvBuf _current = new InternalEventLifeCycleStatemachineEvBuf();
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public InternalEventLifeCycleStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NullState$;
		}
		
		clearInEvents();
		clearInternalEvents();
		
		
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
		
		enterSequence_r1_default();
		enterSequence_r2_default();
		isExecuting = false;
	}
	
	public void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		
		exitSequence_r1();
		exitSequence_r2();
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
		
		swapInEvents();
		do { 
			for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
				switch (stateVector[nextStateIndex]) {
				case r1_A:
					r1_A_react(true);
					break;
				case r1_B:
					r1_B_react(true);
					break;
				case r2_C:
					r2_C_react(true);
					break;
				case r2_D:
					r2_D_react(true);
					break;
				default:
					// $NullState$
				}
			}
			
			swapInternalEvents();
		} while ((_current.internal.i1 || _current.internal.i2));
		
		
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$||stateVector[1] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	private void swapInEvents() {
		_current.iface.e = sCInterface.e;
		sCInterface.e = false;
		
		_current.iface.f = sCInterface.f;
		sCInterface.f = false;
	}
	
	private void clearInEvents() {
		sCInterface.e = false;
		
		sCInterface.f = false;
	}
	
	private void swapInternalEvents() {
		_current.iface.e = false;
		
		_current.iface.f = false;
		
		_current.internal.i1 = i1;
		i1 = false;
		
		_current.internal.i2 = i2;
		i2 = false;
	}
	
	private void clearInternalEvents() {
		i1 = false;
		
		i2 = false;
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case r1_A:
			return stateVector[0] == State.r1_A;
		case r1_B:
			return stateVector[0] == State.r1_B;
		case r2_C:
			return stateVector[1] == State.r2_C;
		case r2_D:
			return stateVector[1] == State.r2_D;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	private void raiseI1() {
		i1 = true;
	}
	
	private void raiseI2() {
		i2 = true;
	}
	
	public void raiseE() {
		sCInterface.raiseE();
	}
	
	public void raiseF() {
		sCInterface.raiseF();
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_r1_A_default() {
		nextStateIndex = 0;
		stateVector[0] = State.r1_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_r1_B_default() {
		nextStateIndex = 0;
		stateVector[0] = State.r1_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_r2_C_default() {
		nextStateIndex = 1;
		stateVector[1] = State.r2_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_r2_D_default() {
		nextStateIndex = 1;
		stateVector[1] = State.r2_D;
	}
	
	/* 'default' enter sequence for region r1 */
	private void enterSequence_r1_default() {
		react_r1__entry_Default();
	}
	
	/* 'default' enter sequence for region r2 */
	private void enterSequence_r2_default() {
		react_r2__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_r1_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_r1_B() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_r2_C() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_r2_D() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for region r1 */
	private void exitSequence_r1() {
		switch (stateVector[0]) {
		case r1_A:
			exitSequence_r1_A();
			break;
		case r1_B:
			exitSequence_r1_B();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r2 */
	private void exitSequence_r2() {
		switch (stateVector[1]) {
		case r2_C:
			exitSequence_r2_C();
			break;
		case r2_D:
			exitSequence_r2_D();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_r1__entry_Default() {
		enterSequence_r1_A_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_r2__entry_Default() {
		enterSequence_r2_C_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean r1_A_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (_current.internal.i2) {
					exitSequence_r1_A();
					enterSequence_r1_B_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			if (_current.iface.e) {
				raiseI1();
			}
		}
		return did_transition;
	}
	
	private boolean r1_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (_current.iface.e) {
					exitSequence_r1_B();
					enterSequence_r1_A_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean r2_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (_current.internal.i1) {
				exitSequence_r2_C();
				enterSequence_r2_D_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean r2_D_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (_current.iface.f) {
				exitSequence_r2_D();
				raiseI2();
				
				enterSequence_r2_C_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
}
