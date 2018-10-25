package org.yakindu.scr.eventdriventriggeredbytimeevent;
import org.yakindu.scr.ITimer;

public class EventDrivenTriggeredByTimeEventStatemachine implements IEventDrivenTriggeredByTimeEventStatemachine {

	protected class SCInterfaceImpl implements SCInterface {
	
		private long x;
		
		public long getX() {
			return x;
		}
		
		public void setX(long value) {
			this.x = value;
		}
		
		private long transition_count;
		
		public long getTransition_count() {
			return transition_count;
		}
		
		public void setTransition_count(long value) {
			this.transition_count = value;
		}
		
	}
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		eventDrivenTriggeredByTimeEvent_r_A,
		eventDrivenTriggeredByTimeEvent_r_B,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[2];
	public EventDrivenTriggeredByTimeEventStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setX(0);
		
		sCInterface.setTransition_count(0);
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		}
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		enterSequence_EventDrivenTriggeredByTimeEvent_r_default();
	}
	
	public void exit() {
		exitSequence_EventDrivenTriggeredByTimeEvent_r();
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
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
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
		case eventDrivenTriggeredByTimeEvent_r_A:
			return stateVector[0] == State.eventDrivenTriggeredByTimeEvent_r_A;
		case eventDrivenTriggeredByTimeEvent_r_B:
			return stateVector[0] == State.eventDrivenTriggeredByTimeEvent_r_B;
		default:
			return false;
		}
	}
	
	/**
	* Set the {@link ITimer} for the state machine. It must be set
	* externally on a timed state machine before a run cycle can be correctly
	* executed.
	* 
	* @param timer
	*/
	public void setTimer(ITimer timer) {
		this.timer = timer;
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return timer;
	}
	
	public void timeElapsed(int eventID) {
		timeEvents[eventID] = true;
		runCycle();
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public long getX() {
		return sCInterface.getX();
	}
	
	public void setX(long value) {
		sCInterface.setX(value);
	}
	
	public long getTransition_count() {
		return sCInterface.getTransition_count();
	}
	
	public void setTransition_count(long value) {
		sCInterface.setTransition_count(value);
	}
	
	/* Entry action for state 'A'. */
	private void entryAction_EventDrivenTriggeredByTimeEvent_r_A() {
		timer.setTimer(this, 0, 1 * 1000, false);
	}
	
	/* Entry action for state 'B'. */
	private void entryAction_EventDrivenTriggeredByTimeEvent_r_B() {
		timer.setTimer(this, 1, 1 * 1000, false);
	}
	
	/* Exit action for state 'A'. */
	private void exitAction_EventDrivenTriggeredByTimeEvent_r_A() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'B'. */
	private void exitAction_EventDrivenTriggeredByTimeEvent_r_B() {
		timer.unsetTimer(this, 1);
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_EventDrivenTriggeredByTimeEvent_r_A_default() {
		entryAction_EventDrivenTriggeredByTimeEvent_r_A();
		nextStateIndex = 0;
		stateVector[0] = State.eventDrivenTriggeredByTimeEvent_r_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_EventDrivenTriggeredByTimeEvent_r_B_default() {
		entryAction_EventDrivenTriggeredByTimeEvent_r_B();
		nextStateIndex = 0;
		stateVector[0] = State.eventDrivenTriggeredByTimeEvent_r_B;
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_EventDrivenTriggeredByTimeEvent_r_default() {
		react_EventDrivenTriggeredByTimeEvent_r__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_EventDrivenTriggeredByTimeEvent_r_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_EventDrivenTriggeredByTimeEvent_r_A();
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_EventDrivenTriggeredByTimeEvent_r_B() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_EventDrivenTriggeredByTimeEvent_r_B();
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_EventDrivenTriggeredByTimeEvent_r() {
		switch (stateVector[0]) {
		case eventDrivenTriggeredByTimeEvent_r_A:
			exitSequence_EventDrivenTriggeredByTimeEvent_r_A();
			break;
		case eventDrivenTriggeredByTimeEvent_r_B:
			exitSequence_EventDrivenTriggeredByTimeEvent_r_B();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_EventDrivenTriggeredByTimeEvent_r__entry_Default() {
		enterSequence_EventDrivenTriggeredByTimeEvent_r_A_default();
	}
	
	private boolean react(boolean try_transition) {
		return false;
	}
	
	private boolean eventDrivenTriggeredByTimeEvent_r_A_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react(try_transition)==false) {
				if (timeEvents[0]) {
					exitSequence_EventDrivenTriggeredByTimeEvent_r_A();
					sCInterface.setTransition_count(sCInterface.getTransition_count() + 1);
					
					enterSequence_EventDrivenTriggeredByTimeEvent_r_B_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			sCInterface.setX(sCInterface.getX() + 1);
		}
		return did_transition;
	}
	
	private boolean eventDrivenTriggeredByTimeEvent_r_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react(try_transition)==false) {
				if (timeEvents[1]) {
					exitSequence_EventDrivenTriggeredByTimeEvent_r_B();
					sCInterface.setTransition_count(sCInterface.getTransition_count() + 1);
					
					enterSequence_EventDrivenTriggeredByTimeEvent_r_A_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
			sCInterface.setX(sCInterface.getX() + 1);
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
			case eventDrivenTriggeredByTimeEvent_r_A:
				eventDrivenTriggeredByTimeEvent_r_A_react(true);
				break;
			case eventDrivenTriggeredByTimeEvent_r_B:
				eventDrivenTriggeredByTimeEvent_r_B_react(true);
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
}
