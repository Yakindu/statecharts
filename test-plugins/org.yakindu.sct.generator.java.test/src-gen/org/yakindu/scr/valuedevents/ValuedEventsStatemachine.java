/** Generated by YAKINDU Statechart Tools code generator. */
package org.yakindu.scr.valuedevents;


public class ValuedEventsStatemachine implements IValuedEventsStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean integerEvent;
		
		private long integerEventValue;
		
		
		public void raiseIntegerEvent(long value) {
			integerEventValue = value;
			integerEvent = true;
		}
		protected long getIntegerEventValue() {
			if (! integerEvent ) 
				throw new IllegalStateException("Illegal event value access. Event IntegerEvent is not raised!");
			return integerEventValue;
		}
		
		private boolean booleanEvent;
		
		private boolean booleanEventValue;
		
		
		public void raiseBooleanEvent(boolean value) {
			booleanEventValue = value;
			booleanEvent = true;
		}
		protected boolean getBooleanEventValue() {
			if (! booleanEvent ) 
				throw new IllegalStateException("Illegal event value access. Event BooleanEvent is not raised!");
			return booleanEventValue;
		}
		
		private boolean realEvent;
		
		private double realEventValue;
		
		
		public void raiseRealEvent(double value) {
			realEventValue = value;
			realEvent = true;
		}
		protected double getRealEventValue() {
			if (! realEvent ) 
				throw new IllegalStateException("Illegal event value access. Event RealEvent is not raised!");
			return realEventValue;
		}
		
		private boolean stringEvent;
		
		private String stringEventValue;
		
		
		public void raiseStringEvent(String value) {
			stringEventValue = value;
			stringEvent = true;
		}
		protected String getStringEventValue() {
			if (! stringEvent ) 
				throw new IllegalStateException("Illegal event value access. Event StringEvent is not raised!");
			return stringEventValue;
		}
		
		private long myInt;
		
		public long getMyInt() {
			return myInt;
		}
		
		public void setMyInt(long value) {
			this.myInt = value;
		}
		
		private boolean myBool;
		
		public boolean getMyBool() {
			return myBool;
		}
		
		public void setMyBool(boolean value) {
			this.myBool = value;
		}
		
		private double myReal;
		
		public double getMyReal() {
			return myReal;
		}
		
		public void setMyReal(double value) {
			this.myReal = value;
		}
		
		private String myString;
		
		public String getMyString() {
			return myString;
		}
		
		public void setMyString(String value) {
			this.myString = value;
		}
		
		protected void clearEvents() {
			integerEvent = false;
			booleanEvent = false;
			realEvent = false;
			stringEvent = false;
		}
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		main_region1_A,
		integer_region_B,
		integer_region_C,
		integer_region_D,
		boolean_region_B,
		boolean_region_C,
		boolean_region_D,
		real_region_B,
		real_region_C,
		real_region_D,
		string_region_B,
		string_region_C,
		string_region_D,
		$NullState$
	};
	
	private final State[] stateVector = new State[5];
	
	private int nextStateIndex;
	
	public ValuedEventsStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 5; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setMyInt(0);
		
		sCInterface.setMyBool(false);
		
		sCInterface.setMyReal(0.0);
		
		sCInterface.setMyString("");
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
				"The state machine needs to be initialized first by calling the init() function."
			);
		}
		enterSequence_main_region1_default();
		enterSequence_integer_region_default();
		enterSequence_boolean_region_default();
		enterSequence_real_region_default();
		enterSequence_string_region_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case main_region1_A:
				main_region1_A_react(true);
				break;
			case integer_region_B:
				integer_region_B_react(true);
				break;
			case integer_region_C:
				integer_region_C_react(true);
				break;
			case integer_region_D:
				integer_region_D_react(true);
				break;
			case boolean_region_B:
				boolean_region_B_react(true);
				break;
			case boolean_region_C:
				boolean_region_C_react(true);
				break;
			case boolean_region_D:
				boolean_region_D_react(true);
				break;
			case real_region_B:
				real_region_B_react(true);
				break;
			case real_region_C:
				real_region_C_react(true);
				break;
			case real_region_D:
				real_region_D_react(true);
				break;
			case string_region_B:
				string_region_B_react(true);
				break;
			case string_region_C:
				string_region_C_react(true);
				break;
			case string_region_D:
				string_region_D_react(true);
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
	public void exit() {
		exitSequence_main_region1();
		exitSequence_integer_region();
		exitSequence_boolean_region();
		exitSequence_real_region();
		exitSequence_string_region();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$||stateVector[1] != State.$NullState$||stateVector[2] != State.$NullState$||stateVector[3] != State.$NullState$||stateVector[4] != State.$NullState$;
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
		case main_region1_A:
			return stateVector[0] == State.main_region1_A;
		case integer_region_B:
			return stateVector[1] == State.integer_region_B;
		case integer_region_C:
			return stateVector[1] == State.integer_region_C;
		case integer_region_D:
			return stateVector[1] == State.integer_region_D;
		case boolean_region_B:
			return stateVector[2] == State.boolean_region_B;
		case boolean_region_C:
			return stateVector[2] == State.boolean_region_C;
		case boolean_region_D:
			return stateVector[2] == State.boolean_region_D;
		case real_region_B:
			return stateVector[3] == State.real_region_B;
		case real_region_C:
			return stateVector[3] == State.real_region_C;
		case real_region_D:
			return stateVector[3] == State.real_region_D;
		case string_region_B:
			return stateVector[4] == State.string_region_B;
		case string_region_C:
			return stateVector[4] == State.string_region_C;
		case string_region_D:
			return stateVector[4] == State.string_region_D;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public void raiseIntegerEvent(long value) {
		sCInterface.raiseIntegerEvent(value);
	}
	
	public void raiseBooleanEvent(boolean value) {
		sCInterface.raiseBooleanEvent(value);
	}
	
	public void raiseRealEvent(double value) {
		sCInterface.raiseRealEvent(value);
	}
	
	public void raiseStringEvent(String value) {
		sCInterface.raiseStringEvent(value);
	}
	
	public long getMyInt() {
		return sCInterface.getMyInt();
	}
	
	public void setMyInt(long value) {
		sCInterface.setMyInt(value);
	}
	
	public boolean getMyBool() {
		return sCInterface.getMyBool();
	}
	
	public void setMyBool(boolean value) {
		sCInterface.setMyBool(value);
	}
	
	public double getMyReal() {
		return sCInterface.getMyReal();
	}
	
	public void setMyReal(double value) {
		sCInterface.setMyReal(value);
	}
	
	public String getMyString() {
		return sCInterface.getMyString();
	}
	
	public void setMyString(String value) {
		sCInterface.setMyString(value);
	}
	
	/* Entry action for state 'A'. */
	private void entryAction_main_region1_A() {
		sCInterface.raiseIntegerEvent((2 * 21));
		
		sCInterface.raiseRealEvent(10);
		
		sCInterface.raiseBooleanEvent((true || false));
		
		sCInterface.raiseStringEvent("sct");
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_main_region1_A_default() {
		entryAction_main_region1_A();
		nextStateIndex = 0;
		stateVector[0] = State.main_region1_A;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_integer_region_B_default() {
		nextStateIndex = 1;
		stateVector[1] = State.integer_region_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_integer_region_C_default() {
		nextStateIndex = 1;
		stateVector[1] = State.integer_region_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_integer_region_D_default() {
		nextStateIndex = 1;
		stateVector[1] = State.integer_region_D;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_boolean_region_B_default() {
		nextStateIndex = 2;
		stateVector[2] = State.boolean_region_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_boolean_region_C_default() {
		nextStateIndex = 2;
		stateVector[2] = State.boolean_region_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_boolean_region_D_default() {
		nextStateIndex = 2;
		stateVector[2] = State.boolean_region_D;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_real_region_B_default() {
		nextStateIndex = 3;
		stateVector[3] = State.real_region_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_real_region_C_default() {
		nextStateIndex = 3;
		stateVector[3] = State.real_region_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_real_region_D_default() {
		nextStateIndex = 3;
		stateVector[3] = State.real_region_D;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_string_region_B_default() {
		nextStateIndex = 4;
		stateVector[4] = State.string_region_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_string_region_C_default() {
		nextStateIndex = 4;
		stateVector[4] = State.string_region_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_string_region_D_default() {
		nextStateIndex = 4;
		stateVector[4] = State.string_region_D;
	}
	
	/* 'default' enter sequence for region main region1 */
	private void enterSequence_main_region1_default() {
		react_main_region1__entry_Default();
	}
	
	/* 'default' enter sequence for region integer_region */
	private void enterSequence_integer_region_default() {
		react_integer_region__entry_Default();
	}
	
	/* 'default' enter sequence for region boolean_region */
	private void enterSequence_boolean_region_default() {
		react_boolean_region__entry_Default();
	}
	
	/* 'default' enter sequence for region real_region */
	private void enterSequence_real_region_default() {
		react_real_region__entry_Default();
	}
	
	/* 'default' enter sequence for region string_region */
	private void enterSequence_string_region_default() {
		react_string_region__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_main_region1_A() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_integer_region_B() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_integer_region_C() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_integer_region_D() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_boolean_region_B() {
		nextStateIndex = 2;
		stateVector[2] = State.$NullState$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_boolean_region_C() {
		nextStateIndex = 2;
		stateVector[2] = State.$NullState$;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_boolean_region_D() {
		nextStateIndex = 2;
		stateVector[2] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_real_region_B() {
		nextStateIndex = 3;
		stateVector[3] = State.$NullState$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_real_region_C() {
		nextStateIndex = 3;
		stateVector[3] = State.$NullState$;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_real_region_D() {
		nextStateIndex = 3;
		stateVector[3] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_string_region_B() {
		nextStateIndex = 4;
		stateVector[4] = State.$NullState$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_string_region_C() {
		nextStateIndex = 4;
		stateVector[4] = State.$NullState$;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_string_region_D() {
		nextStateIndex = 4;
		stateVector[4] = State.$NullState$;
	}
	
	/* Default exit sequence for region main region1 */
	private void exitSequence_main_region1() {
		switch (stateVector[0]) {
		case main_region1_A:
			exitSequence_main_region1_A();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region integer_region */
	private void exitSequence_integer_region() {
		switch (stateVector[1]) {
		case integer_region_B:
			exitSequence_integer_region_B();
			break;
		case integer_region_C:
			exitSequence_integer_region_C();
			break;
		case integer_region_D:
			exitSequence_integer_region_D();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region boolean_region */
	private void exitSequence_boolean_region() {
		switch (stateVector[2]) {
		case boolean_region_B:
			exitSequence_boolean_region_B();
			break;
		case boolean_region_C:
			exitSequence_boolean_region_C();
			break;
		case boolean_region_D:
			exitSequence_boolean_region_D();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region real_region */
	private void exitSequence_real_region() {
		switch (stateVector[3]) {
		case real_region_B:
			exitSequence_real_region_B();
			break;
		case real_region_C:
			exitSequence_real_region_C();
			break;
		case real_region_D:
			exitSequence_real_region_D();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region string_region */
	private void exitSequence_string_region() {
		switch (stateVector[4]) {
		case string_region_B:
			exitSequence_string_region_B();
			break;
		case string_region_C:
			exitSequence_string_region_C();
			break;
		case string_region_D:
			exitSequence_string_region_D();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region1__entry_Default() {
		enterSequence_main_region1_A_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_integer_region__entry_Default() {
		enterSequence_integer_region_B_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_boolean_region__entry_Default() {
		enterSequence_boolean_region_B_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_real_region__entry_Default() {
		enterSequence_real_region_B_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_string_region__entry_Default() {
		enterSequence_string_region_B_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_region1_A_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean integer_region_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.integerEvent) {
				exitSequence_integer_region_B();
				sCInterface.setMyInt(sCInterface.getIntegerEventValue());
				
				enterSequence_integer_region_C_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean integer_region_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.integerEvent) {
				exitSequence_integer_region_C();
				sCInterface.setMyInt(sCInterface.getIntegerEventValue());
				
				enterSequence_integer_region_D_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean integer_region_D_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			did_transition = false;
		}
		return did_transition;
	}
	
	private boolean boolean_region_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.booleanEvent) {
				exitSequence_boolean_region_B();
				sCInterface.setMyBool(sCInterface.getBooleanEventValue());
				
				enterSequence_boolean_region_C_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean boolean_region_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.booleanEvent) {
				exitSequence_boolean_region_C();
				sCInterface.setMyBool(sCInterface.getBooleanEventValue());
				
				enterSequence_boolean_region_D_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean boolean_region_D_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			did_transition = false;
		}
		return did_transition;
	}
	
	private boolean real_region_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.realEvent) {
				exitSequence_real_region_B();
				sCInterface.setMyReal(sCInterface.getRealEventValue());
				
				enterSequence_real_region_C_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean real_region_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.realEvent) {
				exitSequence_real_region_C();
				sCInterface.setMyReal(sCInterface.getRealEventValue());
				
				enterSequence_real_region_D_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean real_region_D_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			did_transition = false;
		}
		return did_transition;
	}
	
	private boolean string_region_B_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.stringEvent) {
				exitSequence_string_region_B();
				sCInterface.setMyString(sCInterface.getStringEventValue());
				
				enterSequence_string_region_C_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean string_region_C_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.stringEvent) {
				exitSequence_string_region_C();
				sCInterface.setMyString(sCInterface.getStringEventValue());
				
				enterSequence_string_region_D_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean string_region_D_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			did_transition = false;
		}
		return did_transition;
	}
	
}
