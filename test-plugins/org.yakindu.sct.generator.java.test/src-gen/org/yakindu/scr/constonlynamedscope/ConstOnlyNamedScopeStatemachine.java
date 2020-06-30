/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.constonlynamedscope;


public class ConstOnlyNamedScopeStatemachine implements IConstOnlyNamedScopeStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e;
		
		private long eValue;
		
		
		public void raiseE(long value) {
			eValue = value;
			e = true;
		}
		protected long getEValue() {
			if (! e ) 
				throw new IllegalStateException("Illegal event value access. Event E is not raised!");
			return eValue;
		}
		
	}
	
	protected class SCIAImpl implements SCIA {
	
		public long getB() {
			return b;
		}
		
		public long getC() {
			return c;
		}
		
	}
	
	private static class SCInterfaceEvBuf {
		private boolean e;
		
		private long eValue;
	}
	private static class ConstOnlyNamedScopeStatemachineEvBuf {
		private SCInterfaceEvBuf iface = new SCInterfaceEvBuf();
	}
	protected SCInterfaceImpl sCInterface;
	
	protected SCIAImpl sCIA;
	
	private boolean initialized = false;
	
	public enum State {
		constOnlyNamedScope_main_region_A,
		constOnlyNamedScope_main_region_B,
		constOnlyNamedScope_main_region_C,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private ConstOnlyNamedScopeStatemachineEvBuf _current = new ConstOnlyNamedScopeStatemachineEvBuf();
	
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public ConstOnlyNamedScopeStatemachine() {
		sCInterface = new SCInterfaceImpl();
		sCIA = new SCIAImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		
		clearInEvents();
		
		
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
		enterSequence_main_region_default();
		isExecuting = false;
	}
	
	public void exit() {
		if (getIsExecuting()) {
			return;
		}
		isExecuting = true;
		exitSequence_main_region();
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
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case constOnlyNamedScope_main_region_A:
				main_region_A_react(true);
				break;
			case constOnlyNamedScope_main_region_B:
				main_region_B_react(true);
				break;
			case constOnlyNamedScope_main_region_C:
				main_region_C_react(true);
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
	private void swapInEvents() {
		_current.iface.e = sCInterface.e;
		_current.iface.eValue = sCInterface.eValue;
		sCInterface.e = false;
	}
	
	private void clearInEvents() {
		sCInterface.e = false;
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case constOnlyNamedScope_main_region_A:
			return stateVector[0] == State.constOnlyNamedScope_main_region_A;
		case constOnlyNamedScope_main_region_B:
			return stateVector[0] == State.constOnlyNamedScope_main_region_B;
		case constOnlyNamedScope_main_region_C:
			return stateVector[0] == State.constOnlyNamedScope_main_region_C;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public SCIA getSCIA() {
		return sCIA;
	}
	
	public void raiseE(long value) {
		sCInterface.raiseE(value);
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region_A_default() {
		nextStateIndex = 0;
		stateVector[0] = State.constOnlyNamedScope_main_region_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_main_region_B_default() {
		nextStateIndex = 0;
		stateVector[0] = State.constOnlyNamedScope_main_region_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_main_region_C_default() {
		nextStateIndex = 0;
		stateVector[0] = State.constOnlyNamedScope_main_region_C;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_main_region_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_main_region_B() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_main_region_C() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case constOnlyNamedScope_main_region_A:
			exitSequence_main_region_A();
			break;
		case constOnlyNamedScope_main_region_B:
			exitSequence_main_region_B();
			break;
		case constOnlyNamedScope_main_region_C:
			exitSequence_main_region_C();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_A_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_region_A_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (((_current.iface.e) && (_current.iface.eValue==sCIA.getB()))) {
					exitSequence_main_region_A();
					enterSequence_main_region_B_default();
				} else {
					if (((_current.iface.e) && (_current.iface.eValue==sCIA.getC()))) {
						exitSequence_main_region_A();
						enterSequence_main_region_C_default();
					} else {
						did_transition = false;
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean main_region_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
}
