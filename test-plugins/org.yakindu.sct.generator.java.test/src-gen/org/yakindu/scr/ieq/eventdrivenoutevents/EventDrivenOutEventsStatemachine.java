/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.ieq.eventdrivenoutevents;

import java.util.LinkedList;
import java.util.Queue;

public class EventDrivenOutEventsStatemachine implements IEventDrivenOutEventsStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e1;
		
		
		public void raiseE1() {
			inEventQueue.add(
				new Runnable() {
					@Override
					public void run() {
						e1 = true;
						singleCycle();
					}
				}
			);
			runCycle();
		}
		
		private boolean e2;
		
		
		public boolean isRaisedE2() {
			return e2;
		}
		
		protected void raiseE2() {
			e2 = true;
		}
		
		protected void clearEvents() {
			e1 = false;
		}
		protected void clearOutEvents() {
		
		e2 = false;
		}
		
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_StateA,
		main_region_StateB,
		second_region_StateC,
		second_region_StateD,
		$NullState$
	};
	
	private final State[] stateVector = new State[2];
	
	private int nextStateIndex;
	
	private Queue<Runnable> inEventQueue = new LinkedList<Runnable>();
	private boolean isRunningCycle = false;
	public EventDrivenOutEventsStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NullState$;
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
		enterSequence_main_region_default();
		enterSequence_second_region_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		
		if (isRunningCycle) {
			return;
		}
		isRunningCycle = true;
		
		clearOutEvents();
	
		Runnable task = getNextEvent();
		if (task == null) {
			task = getDefaultEvent();
		}
		
		while (task != null) {
			task.run();
			clearEvents();
			task = getNextEvent();
		}
		
		isRunningCycle = false;
	}
	
	protected void singleCycle() {
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
				case main_region_StateA:
					main_region_StateA_react(true);
					break;
				case main_region_StateB:
					main_region_StateB_react(true);
					break;
				case second_region_StateC:
					second_region_StateC_react(true);
					break;
				case second_region_StateD:
					second_region_StateD_react(true);
					break;
			default:
				// $NullState$
			}
		}
	}
	
	protected Runnable getNextEvent() {
		if(!inEventQueue.isEmpty()) {
			return inEventQueue.poll();
		}
		return null;
	}
	
	protected Runnable getDefaultEvent() {
		return new Runnable() {
			@Override
			public void run() {
				singleCycle();
			}
		};
	}
	
	public void exit() {
		exitSequence_main_region();
		exitSequence_second_region();
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
		sCInterface.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case main_region_StateA:
			return stateVector[0] == State.main_region_StateA;
		case main_region_StateB:
			return stateVector[0] == State.main_region_StateB;
		case second_region_StateC:
			return stateVector[1] == State.second_region_StateC;
		case second_region_StateD:
			return stateVector[1] == State.second_region_StateD;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public void raiseE1() {
		sCInterface.raiseE1();
	}
	
	public boolean isRaisedE2() {
		return sCInterface.isRaisedE2();
	}
	
	/* Entry action for state 'StateB'. */
	private void entryAction_main_region_StateB() {
		sCInterface.raiseE2();
	}
	
	/* 'default' enter sequence for state StateA */
	private void enterSequence_main_region_StateA_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateA;
	}
	
	/* 'default' enter sequence for state StateB */
	private void enterSequence_main_region_StateB_default() {
		entryAction_main_region_StateB();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_StateB;
	}
	
	/* 'default' enter sequence for state StateC */
	private void enterSequence_second_region_StateC_default() {
		nextStateIndex = 1;
		stateVector[1] = State.second_region_StateC;
	}
	
	/* 'default' enter sequence for state StateD */
	private void enterSequence_second_region_StateD_default() {
		nextStateIndex = 1;
		stateVector[1] = State.second_region_StateD;
	}
	
	/* 'default' enter sequence for region main region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region second region */
	private void enterSequence_second_region_default() {
		react_second_region__entry_Default();
	}
	
	/* Default exit sequence for state StateA */
	private void exitSequence_main_region_StateA() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateB */
	private void exitSequence_main_region_StateB() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateC */
	private void exitSequence_second_region_StateC() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state StateD */
	private void exitSequence_second_region_StateD() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for region main region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_StateA:
			exitSequence_main_region_StateA();
			break;
		case main_region_StateB:
			exitSequence_main_region_StateB();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region second region */
	private void exitSequence_second_region() {
		switch (stateVector[1]) {
		case second_region_StateC:
			exitSequence_second_region_StateC();
			break;
		case second_region_StateD:
			exitSequence_second_region_StateD();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_StateA_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_second_region__entry_Default() {
		enterSequence_second_region_StateC_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_region_StateA_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				if (sCInterface.e1) {
					exitSequence_main_region_StateA();
					enterSequence_main_region_StateB_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean main_region_StateB_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean second_region_StateC_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.e2) {
				exitSequence_second_region_StateC();
				enterSequence_second_region_StateD_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean second_region_StateD_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			did_transition = false;
		}
		return did_transition;
	}
	
}
