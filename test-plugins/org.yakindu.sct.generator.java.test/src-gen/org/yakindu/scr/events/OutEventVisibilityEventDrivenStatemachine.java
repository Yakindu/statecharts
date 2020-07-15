/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.events;

import java.util.LinkedList;
import java.util.Queue;
import org.yakindu.sct.rx.Observable;

public class OutEventVisibilityEventDrivenStatemachine implements IOutEventVisibilityEventDrivenStatemachine {
	protected class InterfaceImpl implements Interface {
	
		private boolean i;
		
		private long iValue;
		
		
		public void raiseI(final long value) {
			iValue = value;
			i = true;
			runCycle();
		}
		protected long getIValue() {
			if (! i ) 
				throw new IllegalStateException("Illegal event value access. Event I is not raised!");
			return iValue;
		}
		
		private boolean o1;
		
		
		protected void raiseO1() {
			o1 = true;
			o1Observable.next(null);
		}
		public boolean isRaisedO1() {
			return o1;
		}
		
		
		private Observable<Void> o1Observable = new Observable<Void>();
		
		public Observable<Void> getO1() {
			return o1Observable;
		}
		
		private boolean o2;
		
		
		protected void raiseO2() {
			o2 = true;
			o2Observable.next(null);
		}
		public boolean isRaisedO2() {
			return o2;
		}
		
		
		private Observable<Void> o2Observable = new Observable<Void>();
		
		public Observable<Void> getO2() {
			return o2Observable;
		}
		
	}
	
	protected InterfaceImpl defaultInterface;
	
	private boolean initialized = false;
	
	public enum State {
		MAIN_REGION_A,
		MAIN_REGION_B,
		MAIN_REGION_C,
		MAIN_REGION_D,
		MAIN_REGION_E,
		$NULLSTATE$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private Queue<Runnable> internalEventQueue = new LinkedList<Runnable>();
	private boolean l;
	private boolean isExecuting;
	
	protected boolean getIsExecuting() {
		return isExecuting;
	}
	
	protected void setIsExecuting(boolean value) {
		this.isExecuting = value;
	}
	public OutEventVisibilityEventDrivenStatemachine() {
		defaultInterface = new InterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NULLSTATE$;
		}
		
		clearInEvents();
		clearInternalEvents();
		clearOutEvents();
		
		
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
		clearOutEvents();
		nextEvent();
		do { 
			for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
				switch (stateVector[nextStateIndex]) {
				case MAIN_REGION_A:
					main_region_A_react(true);
					break;
				case MAIN_REGION_B:
					main_region_B_react(true);
					break;
				case MAIN_REGION_C:
					main_region_C_react(true);
					break;
				case MAIN_REGION_D:
					main_region_D_react(true);
					break;
				case MAIN_REGION_E:
					main_region_E_react(true);
					break;
				default:
					// $NULLSTATE$
				}
			}
			
			clearInEvents();
			clearInternalEvents();
			nextEvent();
		} while ((defaultInterface.i || l));
		
		isExecuting = false;
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NULLSTATE$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	private void clearOutEvents() {
		defaultInterface.o1 = false;
		defaultInterface.o2 = false;
	}
	
	private void clearInEvents() {
		defaultInterface.i = false;
	}
	
	private void clearInternalEvents() {
		l = false;
	}
	
	protected void nextEvent() {
		if(!internalEventQueue.isEmpty()) {
			internalEventQueue.poll().run();
			return;
		}
	}
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case MAIN_REGION_A:
			return stateVector[0] == State.MAIN_REGION_A;
		case MAIN_REGION_B:
			return stateVector[0] == State.MAIN_REGION_B;
		case MAIN_REGION_C:
			return stateVector[0] == State.MAIN_REGION_C;
		case MAIN_REGION_D:
			return stateVector[0] == State.MAIN_REGION_D;
		case MAIN_REGION_E:
			return stateVector[0] == State.MAIN_REGION_E;
		default:
			return false;
		}
	}
	
	public Interface getInterface() {
		return defaultInterface;
	}
	
	private void raiseL() {
	
		internalEventQueue.add(new Runnable() {
			@Override public void run() {
				l = true;					
			}
		});
	}
	
	public void raiseI(long value) {
		defaultInterface.raiseI(value);
	}
	
	public boolean isRaisedO1() {
		return defaultInterface.isRaisedO1();
	}
	
	public boolean isRaisedO2() {
		return defaultInterface.isRaisedO2();
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region_A_default() {
		nextStateIndex = 0;
		stateVector[0] = State.MAIN_REGION_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_main_region_B_default() {
		nextStateIndex = 0;
		stateVector[0] = State.MAIN_REGION_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_main_region_C_default() {
		nextStateIndex = 0;
		stateVector[0] = State.MAIN_REGION_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_main_region_D_default() {
		nextStateIndex = 0;
		stateVector[0] = State.MAIN_REGION_D;
	}
	
	/* 'default' enter sequence for state E */
	private void enterSequence_main_region_E_default() {
		nextStateIndex = 0;
		stateVector[0] = State.MAIN_REGION_E;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_main_region_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_main_region_B() {
		nextStateIndex = 0;
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_main_region_C() {
		nextStateIndex = 0;
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_main_region_D() {
		nextStateIndex = 0;
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for state E */
	private void exitSequence_main_region_E() {
		nextStateIndex = 0;
		stateVector[0] = State.$NULLSTATE$;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case MAIN_REGION_A:
			exitSequence_main_region_A();
			break;
		case MAIN_REGION_B:
			exitSequence_main_region_B();
			break;
		case MAIN_REGION_C:
			exitSequence_main_region_C();
			break;
		case MAIN_REGION_D:
			exitSequence_main_region_D();
			break;
		case MAIN_REGION_E:
			exitSequence_main_region_E();
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
			if (((defaultInterface.i) && (defaultInterface.getIValue()==1))) {
				exitSequence_main_region_A();
				defaultInterface.raiseO1();
				
				raiseL();
				
				enterSequence_main_region_B_default();
				react();
			} else {
				if (defaultInterface.i) {
					exitSequence_main_region_A();
					defaultInterface.raiseO1();
					
					enterSequence_main_region_C_default();
					react();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean main_region_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (l) {
				exitSequence_main_region_B();
				defaultInterface.raiseO2();
				
				enterSequence_main_region_B_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean main_region_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (defaultInterface.i) {
				exitSequence_main_region_C();
				defaultInterface.raiseO2();
				
				enterSequence_main_region_D_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean main_region_D_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (defaultInterface.i) {
				exitSequence_main_region_D();
				defaultInterface.raiseO1();
				
				defaultInterface.raiseO2();
				
				enterSequence_main_region_E_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean main_region_E_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			did_transition = false;
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
}