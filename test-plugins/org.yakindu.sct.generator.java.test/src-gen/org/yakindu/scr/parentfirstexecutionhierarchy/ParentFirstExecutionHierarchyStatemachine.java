package org.yakindu.scr.parentfirstexecutionhierarchy;

public class ParentFirstExecutionHierarchyStatemachine implements IParentFirstExecutionHierarchyStatemachine {

	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean e;
		
		public void raiseE() {
			e = true;
		}
		
		private boolean disable_a;
		
		public boolean getDisable_a() {
			return disable_a;
		}
		
		public void setDisable_a(boolean value) {
			this.disable_a = value;
		}
		
		private boolean disable_aa;
		
		public boolean getDisable_aa() {
			return disable_aa;
		}
		
		public void setDisable_aa(boolean value) {
			this.disable_aa = value;
		}
		
		private boolean disable_aaa;
		
		public boolean getDisable_aaa() {
			return disable_aaa;
		}
		
		public void setDisable_aaa(boolean value) {
			this.disable_aaa = value;
		}
		
		private boolean a_reacted;
		
		public boolean getA_reacted() {
			return a_reacted;
		}
		
		public void setA_reacted(boolean value) {
			this.a_reacted = value;
		}
		
		private boolean aa_reacted;
		
		public boolean getAa_reacted() {
			return aa_reacted;
		}
		
		public void setAa_reacted(boolean value) {
			this.aa_reacted = value;
		}
		
		private boolean aaa_reacted;
		
		public boolean getAaa_reacted() {
			return aaa_reacted;
		}
		
		public void setAaa_reacted(boolean value) {
			this.aaa_reacted = value;
		}
		
		private boolean a_local;
		
		public boolean getA_local() {
			return a_local;
		}
		
		public void setA_local(boolean value) {
			this.a_local = value;
		}
		
		private boolean aa_local;
		
		public boolean getAa_local() {
			return aa_local;
		}
		
		public void setAa_local(boolean value) {
			this.aa_local = value;
		}
		
		private boolean aaa_local;
		
		public boolean getAaa_local() {
			return aaa_local;
		}
		
		public void setAaa_local(boolean value) {
			this.aaa_local = value;
		}
		
		protected void clearEvents() {
			e = false;
		}
	}
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		parentFirstExecutionHierarchy_r_A,
		parentFirstExecutionHierarchy_r_A_r_AA,
		parentFirstExecutionHierarchy_r_A_r_AA_r_AAA,
		parentFirstExecutionHierarchy_r_B,
		$NullState$
	};
	
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	public ParentFirstExecutionHierarchyStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setDisable_a(false);
		
		sCInterface.setDisable_aa(false);
		
		sCInterface.setDisable_aaa(false);
		
		sCInterface.setA_reacted(false);
		
		sCInterface.setAa_reacted(false);
		
		sCInterface.setAaa_reacted(false);
		
		sCInterface.setA_local(false);
		
		sCInterface.setAa_local(false);
		
		sCInterface.setAaa_local(false);
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		}
		enterSequence_ParentFirstExecutionHierarchy_r_default();
	}
	
	public void exit() {
		exitSequence_ParentFirstExecutionHierarchy_r();
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
		case parentFirstExecutionHierarchy_r_A:
			return stateVector[0].ordinal() >= State.
					parentFirstExecutionHierarchy_r_A.ordinal()&& stateVector[0].ordinal() <= State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA.ordinal();
		case parentFirstExecutionHierarchy_r_A_r_AA:
			return stateVector[0].ordinal() >= State.
					parentFirstExecutionHierarchy_r_A_r_AA.ordinal()&& stateVector[0].ordinal() <= State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA.ordinal();
		case parentFirstExecutionHierarchy_r_A_r_AA_r_AAA:
			return stateVector[0] == State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA;
		case parentFirstExecutionHierarchy_r_B:
			return stateVector[0] == State.parentFirstExecutionHierarchy_r_B;
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
	
	public boolean getDisable_a() {
		return sCInterface.getDisable_a();
	}
	
	public void setDisable_a(boolean value) {
		sCInterface.setDisable_a(value);
	}
	
	public boolean getDisable_aa() {
		return sCInterface.getDisable_aa();
	}
	
	public void setDisable_aa(boolean value) {
		sCInterface.setDisable_aa(value);
	}
	
	public boolean getDisable_aaa() {
		return sCInterface.getDisable_aaa();
	}
	
	public void setDisable_aaa(boolean value) {
		sCInterface.setDisable_aaa(value);
	}
	
	public boolean getA_reacted() {
		return sCInterface.getA_reacted();
	}
	
	public void setA_reacted(boolean value) {
		sCInterface.setA_reacted(value);
	}
	
	public boolean getAa_reacted() {
		return sCInterface.getAa_reacted();
	}
	
	public void setAa_reacted(boolean value) {
		sCInterface.setAa_reacted(value);
	}
	
	public boolean getAaa_reacted() {
		return sCInterface.getAaa_reacted();
	}
	
	public void setAaa_reacted(boolean value) {
		sCInterface.setAaa_reacted(value);
	}
	
	public boolean getA_local() {
		return sCInterface.getA_local();
	}
	
	public void setA_local(boolean value) {
		sCInterface.setA_local(value);
	}
	
	public boolean getAa_local() {
		return sCInterface.getAa_local();
	}
	
	public void setAa_local(boolean value) {
		sCInterface.setAa_local(value);
	}
	
	public boolean getAaa_local() {
		return sCInterface.getAaa_local();
	}
	
	public void setAaa_local(boolean value) {
		sCInterface.setAaa_local(value);
	}
	
	private boolean check_ParentFirstExecutionHierarchy_r_A_lr1_lr1() {
		return true;
	}
	
	private boolean check_ParentFirstExecutionHierarchy_r_A_tr0_tr0() {
		return (sCInterface.e) && (!sCInterface.getDisable_a());
	}
	
	private boolean check_ParentFirstExecutionHierarchy_r_A_r_AA_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstExecutionHierarchy_r_A_r_AA_tr0_tr0() {
		return (sCInterface.e) && (!sCInterface.getDisable_aa());
	}
	
	private boolean check_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_tr0_tr0() {
		return (sCInterface.e) && (!sCInterface.getDisable_aaa());
	}
	
	private boolean check_ParentFirstExecutionHierarchy_r_B_tr0_tr0() {
		return sCInterface.e;
	}
	
	private void effect_ParentFirstExecutionHierarchy_r_A_lr1_lr1() {
		sCInterface.setA_local(true);
	}
	
	private void effect_ParentFirstExecutionHierarchy_r_A_tr0() {
		exitSequence_ParentFirstExecutionHierarchy_r_A();
		sCInterface.setA_reacted(true);
		
		enterSequence_ParentFirstExecutionHierarchy_r_B_default();
	}
	
	private void effect_ParentFirstExecutionHierarchy_r_A_r_AA_lr0_lr0() {
		sCInterface.setAa_local(true);
	}
	
	private void effect_ParentFirstExecutionHierarchy_r_A_r_AA_tr0() {
		exitSequence_ParentFirstExecutionHierarchy_r_A();
		sCInterface.setAa_reacted(true);
		
		enterSequence_ParentFirstExecutionHierarchy_r_B_default();
	}
	
	private void effect_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_lr0_lr0() {
		sCInterface.setAaa_local(true);
	}
	
	private void effect_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_tr0() {
		exitSequence_ParentFirstExecutionHierarchy_r_A();
		sCInterface.setAaa_reacted(true);
		
		enterSequence_ParentFirstExecutionHierarchy_r_B_default();
	}
	
	private void effect_ParentFirstExecutionHierarchy_r_B_tr0() {
		exitSequence_ParentFirstExecutionHierarchy_r_B();
		enterSequence_ParentFirstExecutionHierarchy_r_A_default();
	}
	
	/* Entry action for state 'A'. */
	private void entryAction_ParentFirstExecutionHierarchy_r_A() {
		sCInterface.setA_reacted(false);
		
		sCInterface.setAa_reacted(false);
		
		sCInterface.setAaa_reacted(false);
		
		sCInterface.setA_local(false);
		
		sCInterface.setAa_local(false);
		
		sCInterface.setAaa_local(false);
		
		sCInterface.setDisable_a(false);
		
		sCInterface.setDisable_aa(false);
		
		sCInterface.setDisable_aaa(false);
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_ParentFirstExecutionHierarchy_r_A_default() {
		entryAction_ParentFirstExecutionHierarchy_r_A();
		enterSequence_ParentFirstExecutionHierarchy_r_A_r_default();
	}
	
	/* 'default' enter sequence for state AA */
	private void enterSequence_ParentFirstExecutionHierarchy_r_A_r_AA_default() {
		enterSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_default();
	}
	
	/* 'default' enter sequence for state AAA */
	private void enterSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_default() {
		nextStateIndex = 0;
		stateVector[0] = State.parentFirstExecutionHierarchy_r_A_r_AA_r_AAA;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_ParentFirstExecutionHierarchy_r_B_default() {
		nextStateIndex = 0;
		stateVector[0] = State.parentFirstExecutionHierarchy_r_B;
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_ParentFirstExecutionHierarchy_r_default() {
		react_ParentFirstExecutionHierarchy_r__entry_Default();
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_ParentFirstExecutionHierarchy_r_A_r_default() {
		react_ParentFirstExecutionHierarchy_r_A_r__entry_Default();
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_default() {
		react_ParentFirstExecutionHierarchy_r_A_r_AA_r__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_ParentFirstExecutionHierarchy_r_A() {
		exitSequence_ParentFirstExecutionHierarchy_r_A_r();
	}
	
	/* Default exit sequence for state AAA */
	private void exitSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_ParentFirstExecutionHierarchy_r_B() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_ParentFirstExecutionHierarchy_r() {
		switch (stateVector[0]) {
		case parentFirstExecutionHierarchy_r_A_r_AA_r_AAA:
			exitSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA();
			break;
		case parentFirstExecutionHierarchy_r_B:
			exitSequence_ParentFirstExecutionHierarchy_r_B();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_ParentFirstExecutionHierarchy_r_A_r() {
		switch (stateVector[0]) {
		case parentFirstExecutionHierarchy_r_A_r_AA_r_AAA:
			exitSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r() {
		switch (stateVector[0]) {
		case parentFirstExecutionHierarchy_r_A_r_AA_r_AAA:
			exitSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA();
			break;
		default:
			break;
		}
	}
	
	/* The reactions of state AAA. */
	private void react_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA() {
		if (check_ParentFirstExecutionHierarchy_r_A_tr0_tr0()) {
			effect_ParentFirstExecutionHierarchy_r_A_tr0();
		} else {
			effect_ParentFirstExecutionHierarchy_r_A_lr1_lr1();
			if (check_ParentFirstExecutionHierarchy_r_A_r_AA_tr0_tr0()) {
				effect_ParentFirstExecutionHierarchy_r_A_r_AA_tr0();
			} else {
				effect_ParentFirstExecutionHierarchy_r_A_r_AA_lr0_lr0();
				if (check_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_tr0_tr0()) {
					effect_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_tr0();
				} else {
					effect_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_lr0_lr0();
				}
			}
		}
	}
	
	/* The reactions of state B. */
	private void react_ParentFirstExecutionHierarchy_r_B() {
		if (check_ParentFirstExecutionHierarchy_r_B_tr0_tr0()) {
			effect_ParentFirstExecutionHierarchy_r_B_tr0();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstExecutionHierarchy_r__entry_Default() {
		enterSequence_ParentFirstExecutionHierarchy_r_A_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstExecutionHierarchy_r_A_r__entry_Default() {
		enterSequence_ParentFirstExecutionHierarchy_r_A_r_AA_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstExecutionHierarchy_r_A_r_AA_r__entry_Default() {
		enterSequence_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case parentFirstExecutionHierarchy_r_A_r_AA_r_AAA:
				react_ParentFirstExecutionHierarchy_r_A_r_AA_r_AAA();
				break;
			case parentFirstExecutionHierarchy_r_B:
				react_ParentFirstExecutionHierarchy_r_B();
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
}
