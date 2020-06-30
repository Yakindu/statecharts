/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.outeventvisibilityeventdriven;

import java.util.LinkedList;
import java.util.Queue;
import org.yakindu.sct.rx.Observable;

public class OutEventVisibilityEventDrivenStatemachine implements IOutEventVisibilityEventDrivenStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
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
		
		protected void clearEvents() {
			i = false;
		}
		protected void clearOutEvents() {
		
		o1 = false;
		o2 = false;
		}
		
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		outEventVisibilityEventDriven_main_region_A,
		outEventVisibilityEventDriven_main_region_B,
		outEventVisibilityEventDriven_main_region_C,
		outEventVisibilityEventDriven_main_region_D,
		outEventVisibilityEventDriven_main_region_E,
		$NullState$
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
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
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
				case outEventVisibilityEventDriven_main_region_A:
					main_region_A_react(true);
					break;
				case outEventVisibilityEventDriven_main_region_B:
					main_region_B_react(true);
					break;
				case outEventVisibilityEventDriven_main_region_C:
					main_region_C_react(true);
					break;
				case outEventVisibilityEventDriven_main_region_D:
					main_region_D_react(true);
					break;
				case outEventVisibilityEventDriven_main_region_E:
					main_region_E_react(true);
					break;
				default:
					// $NullState$
				}
			}
			
			clearInEvents();
			clearInternalEvents();
			nextEvent();
		} while ((sCInterface.i || l));
		
		
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
	private void clearOutEvents() {
		sCInterface.o1 = false;
		
		sCInterface.o2 = false;
	}
	
	private void clearInEvents() {
		sCInterface.i = false;
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
		case outEventVisibilityEventDriven_main_region_A:
			return stateVector[0] == State.outEventVisibilityEventDriven_main_region_A;
		case outEventVisibilityEventDriven_main_region_B:
			return stateVector[0] == State.outEventVisibilityEventDriven_main_region_B;
		case outEventVisibilityEventDriven_main_region_C:
			return stateVector[0] == State.outEventVisibilityEventDriven_main_region_C;
		case outEventVisibilityEventDriven_main_region_D:
			return stateVector[0] == State.outEventVisibilityEventDriven_main_region_D;
		case outEventVisibilityEventDriven_main_region_E:
			return stateVector[0] == State.outEventVisibilityEventDriven_main_region_E;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	private void raiseL() {
	
		internalEventQueue.add(new Runnable() {
			@Override public void run() {
				l = true;					
			}
		});
	}
	
	public void raiseI(long value) {
		sCInterface.raiseI(value);
	}
	
	public boolean isRaisedO1() {
		return sCInterface.isRaisedO1();
	}
	
	public boolean isRaisedO2() {
		return sCInterface.isRaisedO2();
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region_A_default() {
		nextStateIndex = 0;
		stateVector[0] = State.outEventVisibilityEventDriven_main_region_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_main_region_B_default() {
		nextStateIndex = 0;
		stateVector[0] = State.outEventVisibilityEventDriven_main_region_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_main_region_C_default() {
		nextStateIndex = 0;
		stateVector[0] = State.outEventVisibilityEventDriven_main_region_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_main_region_D_default() {
		nextStateIndex = 0;
		stateVector[0] = State.outEventVisibilityEventDriven_main_region_D;
	}
	
	/* 'default' enter sequence for state E */
	private void enterSequence_main_region_E_default() {
		nextStateIndex = 0;
		stateVector[0] = State.outEventVisibilityEventDriven_main_region_E;
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
	
	/* Default exit sequence for state D */
	private void exitSequence_main_region_D() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state E */
	private void exitSequence_main_region_E() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case outEventVisibilityEventDriven_main_region_A:
			exitSequence_main_region_A();
			break;
		case outEventVisibilityEventDriven_main_region_B:
			exitSequence_main_region_B();
			break;
		case outEventVisibilityEventDriven_main_region_C:
			exitSequence_main_region_C();
			break;
		case outEventVisibilityEventDriven_main_region_D:
			exitSequence_main_region_D();
			break;
		case outEventVisibilityEventDriven_main_region_E:
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
			if (((sCInterface.i) && (sCInterface.getIValue()==1))) {
				exitSequence_main_region_A();
				sCInterface.raiseO1();
				
				raiseL();
				
				enterSequence_main_region_B_default();
				react();
			} else {
				if (sCInterface.i) {
					exitSequence_main_region_A();
					sCInterface.raiseO1();
					
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
				sCInterface.raiseO2();
				
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
			if (sCInterface.i) {
				exitSequence_main_region_C();
				sCInterface.raiseO2();
				
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
			if (sCInterface.i) {
				exitSequence_main_region_D();
				sCInterface.raiseO1();
				
				sCInterface.raiseO2();
				
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
