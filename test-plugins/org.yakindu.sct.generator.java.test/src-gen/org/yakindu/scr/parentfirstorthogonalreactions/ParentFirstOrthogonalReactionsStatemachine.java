package org.yakindu.scr.parentfirstorthogonalreactions;

public class ParentFirstOrthogonalReactionsStatemachine implements IParentFirstOrthogonalReactionsStatemachine {

	protected class SCInterfaceImpl implements SCInterface {
	
		private SCInterfaceOperationCallback operationCallback;
		
		public void setSCInterfaceOperationCallback(
				SCInterfaceOperationCallback operationCallback) {
			this.operationCallback = operationCallback;
		}
		private long a_local;
		
		public long getA_local() {
			return a_local;
		}
		
		public void setA_local(long value) {
			this.a_local = value;
		}
		
		private long c_local;
		
		public long getC_local() {
			return c_local;
		}
		
		public void setC_local(long value) {
			this.c_local = value;
		}
		
		private long aa_local;
		
		public long getAa_local() {
			return aa_local;
		}
		
		public void setAa_local(long value) {
			this.aa_local = value;
		}
		
		private long ac_local;
		
		public long getAc_local() {
			return ac_local;
		}
		
		public void setAc_local(long value) {
			this.ac_local = value;
		}
		
		private long aaa_local;
		
		public long getAaa_local() {
			return aaa_local;
		}
		
		public void setAaa_local(long value) {
			this.aaa_local = value;
		}
		
		private long aac_local;
		
		public long getAac_local() {
			return aac_local;
		}
		
		public void setAac_local(long value) {
			this.aac_local = value;
		}
		
		private long a_check;
		
		public long getA_check() {
			return a_check;
		}
		
		public void setA_check(long value) {
			this.a_check = value;
		}
		
		private long aa_check;
		
		public long getAa_check() {
			return aa_check;
		}
		
		public void setAa_check(long value) {
			this.aa_check = value;
		}
		
		private long aaa_check;
		
		public long getAaa_check() {
			return aaa_check;
		}
		
		public void setAaa_check(long value) {
			this.aaa_check = value;
		}
		
		private long aac_check;
		
		public long getAac_check() {
			return aac_check;
		}
		
		public void setAac_check(long value) {
			this.aac_check = value;
		}
		
		private long ac_check;
		
		public long getAc_check() {
			return ac_check;
		}
		
		public void setAc_check(long value) {
			this.ac_check = value;
		}
		
		private long c_check;
		
		public long getC_check() {
			return c_check;
		}
		
		public void setC_check(long value) {
			this.c_check = value;
		}
		
		private long sm_local;
		
		public long getSm_local() {
			return sm_local;
		}
		
		public void setSm_local(long value) {
			this.sm_local = value;
		}
		
	}
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		parentFirstOrthogonalReactions_r_A,
		parentFirstOrthogonalReactions_r_A_r_AA,
		parentFirstOrthogonalReactions_r_A_r_AA_r_AAA,
		parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC,
		parentFirstOrthogonalReactions_r_A_r2_AC,
		parentFirstOrthogonalReactions_r_B,
		parentFirstOrthogonalReactions_r2_C,
		parentFirstOrthogonalReactions_r2_D,
		$NullState$
	};
	
	private final State[] stateVector = new State[4];
	
	private int nextStateIndex;
	
	public ParentFirstOrthogonalReactionsStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		if (this.sCInterface.operationCallback == null) {
			throw new IllegalStateException("Operation callback for interface sCInterface must be set.");
		}
		
		for (int i = 0; i < 4; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setA_local(0);
		
		sCInterface.setC_local(0);
		
		sCInterface.setAa_local(0);
		
		sCInterface.setAc_local(0);
		
		sCInterface.setAaa_local(0);
		
		sCInterface.setAac_local(0);
		
		sCInterface.setA_check(0);
		
		sCInterface.setAa_check(0);
		
		sCInterface.setAaa_check(0);
		
		sCInterface.setAac_check(0);
		
		sCInterface.setAc_check(0);
		
		sCInterface.setC_check(0);
		
		sCInterface.setSm_local(0);
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		}
		enterSequence_ParentFirstOrthogonalReactions_r_default();
		enterSequence_ParentFirstOrthogonalReactions_r2_default();
	}
	
	public void exit() {
		exitSequence_ParentFirstOrthogonalReactions_r();
		exitSequence_ParentFirstOrthogonalReactions_r2();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$||stateVector[1] != State.$NullState$||stateVector[2] != State.$NullState$||stateVector[3] != State.$NullState$;
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
		case parentFirstOrthogonalReactions_r_A:
			return stateVector[0].ordinal() >= State.
					parentFirstOrthogonalReactions_r_A.ordinal()&& stateVector[0].ordinal() <= State.parentFirstOrthogonalReactions_r_A_r2_AC.ordinal();
		case parentFirstOrthogonalReactions_r_A_r_AA:
			return stateVector[0].ordinal() >= State.
					parentFirstOrthogonalReactions_r_A_r_AA.ordinal()&& stateVector[0].ordinal() <= State.parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC.ordinal();
		case parentFirstOrthogonalReactions_r_A_r_AA_r_AAA:
			return stateVector[0] == State.parentFirstOrthogonalReactions_r_A_r_AA_r_AAA;
		case parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC:
			return stateVector[1] == State.parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC;
		case parentFirstOrthogonalReactions_r_A_r2_AC:
			return stateVector[2] == State.parentFirstOrthogonalReactions_r_A_r2_AC;
		case parentFirstOrthogonalReactions_r_B:
			return stateVector[0] == State.parentFirstOrthogonalReactions_r_B;
		case parentFirstOrthogonalReactions_r2_C:
			return stateVector[3] == State.parentFirstOrthogonalReactions_r2_C;
		case parentFirstOrthogonalReactions_r2_D:
			return stateVector[3] == State.parentFirstOrthogonalReactions_r2_D;
		default:
			return false;
		}
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public long getA_local() {
		return sCInterface.getA_local();
	}
	
	public void setA_local(long value) {
		sCInterface.setA_local(value);
	}
	
	public long getC_local() {
		return sCInterface.getC_local();
	}
	
	public void setC_local(long value) {
		sCInterface.setC_local(value);
	}
	
	public long getAa_local() {
		return sCInterface.getAa_local();
	}
	
	public void setAa_local(long value) {
		sCInterface.setAa_local(value);
	}
	
	public long getAc_local() {
		return sCInterface.getAc_local();
	}
	
	public void setAc_local(long value) {
		sCInterface.setAc_local(value);
	}
	
	public long getAaa_local() {
		return sCInterface.getAaa_local();
	}
	
	public void setAaa_local(long value) {
		sCInterface.setAaa_local(value);
	}
	
	public long getAac_local() {
		return sCInterface.getAac_local();
	}
	
	public void setAac_local(long value) {
		sCInterface.setAac_local(value);
	}
	
	public long getA_check() {
		return sCInterface.getA_check();
	}
	
	public void setA_check(long value) {
		sCInterface.setA_check(value);
	}
	
	public long getAa_check() {
		return sCInterface.getAa_check();
	}
	
	public void setAa_check(long value) {
		sCInterface.setAa_check(value);
	}
	
	public long getAaa_check() {
		return sCInterface.getAaa_check();
	}
	
	public void setAaa_check(long value) {
		sCInterface.setAaa_check(value);
	}
	
	public long getAac_check() {
		return sCInterface.getAac_check();
	}
	
	public void setAac_check(long value) {
		sCInterface.setAac_check(value);
	}
	
	public long getAc_check() {
		return sCInterface.getAc_check();
	}
	
	public void setAc_check(long value) {
		sCInterface.setAc_check(value);
	}
	
	public long getC_check() {
		return sCInterface.getC_check();
	}
	
	public void setC_check(long value) {
		sCInterface.setC_check(value);
	}
	
	public long getSm_local() {
		return sCInterface.getSm_local();
	}
	
	public void setSm_local(long value) {
		sCInterface.setSm_local(value);
	}
	
	private boolean check__lr0() {
		return true;
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_tr0_tr0() {
		return sCInterface.operationCallback.check("A");
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r_AA_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r_AA_tr0_tr0() {
		return sCInterface.operationCallback.check("AA");
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_tr0_tr0() {
		return sCInterface.operationCallback.check("AAA");
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_tr0_tr0() {
		return sCInterface.operationCallback.check("AAC");
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r2_AC_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r_A_r2_AC_tr0_tr0() {
		return sCInterface.operationCallback.check("AC");
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r2_C_lr0_lr0() {
		return true;
	}
	
	private boolean check_ParentFirstOrthogonalReactions_r2_C_tr0_tr0() {
		return sCInterface.operationCallback.check("C");
	}
	
	private void effect__lr0() {
		sCInterface.setSm_local(sCInterface.operationCallback.next());
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_lr0_lr0() {
		sCInterface.setA_local(sCInterface.operationCallback.next());
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_tr0() {
		exitSequence_ParentFirstOrthogonalReactions_r_A();
		enterSequence_ParentFirstOrthogonalReactions_r_B_default();
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r_AA_lr0_lr0() {
		sCInterface.setAa_local(sCInterface.operationCallback.next());
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r_AA_tr0() {
		exitSequence_ParentFirstOrthogonalReactions_r_A();
		enterSequence_ParentFirstOrthogonalReactions_r_B_default();
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_lr0_lr0() {
		sCInterface.setAaa_local(sCInterface.operationCallback.next());
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_tr0() {
		exitSequence_ParentFirstOrthogonalReactions_r_A();
		enterSequence_ParentFirstOrthogonalReactions_r_B_default();
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_lr0_lr0() {
		sCInterface.setAac_local(sCInterface.operationCallback.next());
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_tr0() {
		exitSequence_ParentFirstOrthogonalReactions_r_A();
		enterSequence_ParentFirstOrthogonalReactions_r_B_default();
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r2_AC_lr0_lr0() {
		sCInterface.setAc_local(sCInterface.operationCallback.next());
	}
	
	private void effect_ParentFirstOrthogonalReactions_r_A_r2_AC_tr0() {
		exitSequence_ParentFirstOrthogonalReactions_r_A();
		enterSequence_ParentFirstOrthogonalReactions_r_B_default();
	}
	
	private void effect_ParentFirstOrthogonalReactions_r2_C_lr0_lr0() {
		sCInterface.setC_local(sCInterface.operationCallback.next());
	}
	
	private void effect_ParentFirstOrthogonalReactions_r2_C_tr0() {
		exitSequence_ParentFirstOrthogonalReactions_r2_C();
		enterSequence_ParentFirstOrthogonalReactions_r2_D_default();
	}
	
	/* 'default' enter sequence for state A */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_default() {
		enterSequence_ParentFirstOrthogonalReactions_r_A_r_default();
		enterSequence_ParentFirstOrthogonalReactions_r_A_r2_default();
	}
	
	/* 'default' enter sequence for state AA */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA_default() {
		enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_default();
		enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_default();
	}
	
	/* 'default' enter sequence for state AAA */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_default() {
		nextStateIndex = 0;
		stateVector[0] = State.parentFirstOrthogonalReactions_r_A_r_AA_r_AAA;
	}
	
	/* 'default' enter sequence for state AAC */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_default() {
		nextStateIndex = 1;
		stateVector[1] = State.parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC;
	}
	
	/* 'default' enter sequence for state AC */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r2_AC_default() {
		nextStateIndex = 2;
		stateVector[2] = State.parentFirstOrthogonalReactions_r_A_r2_AC;
	}
	
	/* 'default' enter sequence for state B */
	private void enterSequence_ParentFirstOrthogonalReactions_r_B_default() {
		nextStateIndex = 0;
		stateVector[0] = State.parentFirstOrthogonalReactions_r_B;
	}
	
	/* 'default' enter sequence for state C */
	private void enterSequence_ParentFirstOrthogonalReactions_r2_C_default() {
		nextStateIndex = 3;
		stateVector[3] = State.parentFirstOrthogonalReactions_r2_C;
	}
	
	/* 'default' enter sequence for state D */
	private void enterSequence_ParentFirstOrthogonalReactions_r2_D_default() {
		nextStateIndex = 3;
		stateVector[3] = State.parentFirstOrthogonalReactions_r2_D;
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_ParentFirstOrthogonalReactions_r_default() {
		react_ParentFirstOrthogonalReactions_r__entry_Default();
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r_default() {
		react_ParentFirstOrthogonalReactions_r_A_r__entry_Default();
	}
	
	/* 'default' enter sequence for region r */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_default() {
		react_ParentFirstOrthogonalReactions_r_A_r_AA_r__entry_Default();
	}
	
	/* 'default' enter sequence for region  */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_default() {
		react_ParentFirstOrthogonalReactions_r_A_r_AA__region1__entry_Default();
	}
	
	/* 'default' enter sequence for region r2 */
	private void enterSequence_ParentFirstOrthogonalReactions_r_A_r2_default() {
		react_ParentFirstOrthogonalReactions_r_A_r2__entry_Default();
	}
	
	/* 'default' enter sequence for region r2 */
	private void enterSequence_ParentFirstOrthogonalReactions_r2_default() {
		react_ParentFirstOrthogonalReactions_r2__entry_Default();
	}
	
	/* Default exit sequence for state A */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A() {
		exitSequence_ParentFirstOrthogonalReactions_r_A_r();
		exitSequence_ParentFirstOrthogonalReactions_r_A_r2();
	}
	
	/* Default exit sequence for state AAA */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state AAC */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state AC */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A_r2_AC() {
		nextStateIndex = 2;
		stateVector[2] = State.$NullState$;
	}
	
	/* Default exit sequence for state B */
	private void exitSequence_ParentFirstOrthogonalReactions_r_B() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state C */
	private void exitSequence_ParentFirstOrthogonalReactions_r2_C() {
		nextStateIndex = 3;
		stateVector[3] = State.$NullState$;
	}
	
	/* Default exit sequence for state D */
	private void exitSequence_ParentFirstOrthogonalReactions_r2_D() {
		nextStateIndex = 3;
		stateVector[3] = State.$NullState$;
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_ParentFirstOrthogonalReactions_r() {
		switch (stateVector[0]) {
		case parentFirstOrthogonalReactions_r_A_r_AA_r_AAA:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA();
			break;
		case parentFirstOrthogonalReactions_r_B:
			exitSequence_ParentFirstOrthogonalReactions_r_B();
			break;
		default:
			break;
		}
		
		switch (stateVector[1]) {
		case parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC();
			break;
		default:
			break;
		}
		
		switch (stateVector[2]) {
		case parentFirstOrthogonalReactions_r_A_r2_AC:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r2_AC();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A_r() {
		switch (stateVector[0]) {
		case parentFirstOrthogonalReactions_r_A_r_AA_r_AAA:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA();
			break;
		default:
			break;
		}
		
		switch (stateVector[1]) {
		case parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r() {
		switch (stateVector[0]) {
		case parentFirstOrthogonalReactions_r_A_r_AA_r_AAA:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region  */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1() {
		switch (stateVector[1]) {
		case parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r2 */
	private void exitSequence_ParentFirstOrthogonalReactions_r_A_r2() {
		switch (stateVector[2]) {
		case parentFirstOrthogonalReactions_r_A_r2_AC:
			exitSequence_ParentFirstOrthogonalReactions_r_A_r2_AC();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r2 */
	private void exitSequence_ParentFirstOrthogonalReactions_r2() {
		switch (stateVector[3]) {
		case parentFirstOrthogonalReactions_r2_C:
			exitSequence_ParentFirstOrthogonalReactions_r2_C();
			break;
		case parentFirstOrthogonalReactions_r2_D:
			exitSequence_ParentFirstOrthogonalReactions_r2_D();
			break;
		default:
			break;
		}
	}
	
	/* The reactions of state AAA. */
	private void react_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA() {
		effect__lr0();
		if (check_ParentFirstOrthogonalReactions_r_A_tr0_tr0()) {
			effect_ParentFirstOrthogonalReactions_r_A_tr0();
		} else {
			effect_ParentFirstOrthogonalReactions_r_A_lr0_lr0();
			if (check_ParentFirstOrthogonalReactions_r_A_r_AA_tr0_tr0()) {
				effect_ParentFirstOrthogonalReactions_r_A_r_AA_tr0();
			} else {
				effect_ParentFirstOrthogonalReactions_r_A_r_AA_lr0_lr0();
				if (check_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_tr0_tr0()) {
					effect_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_tr0();
				} else {
					effect_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_lr0_lr0();
				}
			}
		}
	}
	
	/* The reactions of state AAC. */
	private void react_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC() {
		if (check_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_tr0_tr0()) {
			effect_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_tr0();
		} else {
			effect_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_lr0_lr0();
		}
	}
	
	/* The reactions of state AC. */
	private void react_ParentFirstOrthogonalReactions_r_A_r2_AC() {
		if (check_ParentFirstOrthogonalReactions_r_A_r2_AC_tr0_tr0()) {
			effect_ParentFirstOrthogonalReactions_r_A_r2_AC_tr0();
		} else {
			effect_ParentFirstOrthogonalReactions_r_A_r2_AC_lr0_lr0();
		}
	}
	
	/* The reactions of state B. */
	private void react_ParentFirstOrthogonalReactions_r_B() {
		effect__lr0();
	}
	
	/* The reactions of state C. */
	private void react_ParentFirstOrthogonalReactions_r2_C() {
		if (check_ParentFirstOrthogonalReactions_r2_C_tr0_tr0()) {
			effect_ParentFirstOrthogonalReactions_r2_C_tr0();
		} else {
			effect_ParentFirstOrthogonalReactions_r2_C_lr0_lr0();
		}
	}
	
	/* The reactions of state D. */
	private void react_ParentFirstOrthogonalReactions_r2_D() {
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstOrthogonalReactions_r__entry_Default() {
		enterSequence_ParentFirstOrthogonalReactions_r_A_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstOrthogonalReactions_r_A_r__entry_Default() {
		enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstOrthogonalReactions_r_A_r_AA_r__entry_Default() {
		enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstOrthogonalReactions_r_A_r_AA__region1__entry_Default() {
		enterSequence_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstOrthogonalReactions_r_A_r2__entry_Default() {
		enterSequence_ParentFirstOrthogonalReactions_r_A_r2_AC_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_ParentFirstOrthogonalReactions_r2__entry_Default() {
		enterSequence_ParentFirstOrthogonalReactions_r2_C_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case parentFirstOrthogonalReactions_r_A_r_AA_r_AAA:
				react_ParentFirstOrthogonalReactions_r_A_r_AA_r_AAA();
				break;
			case parentFirstOrthogonalReactions_r_A_r_AA__region1_AAC:
				react_ParentFirstOrthogonalReactions_r_A_r_AA__region1_AAC();
				break;
			case parentFirstOrthogonalReactions_r_A_r2_AC:
				react_ParentFirstOrthogonalReactions_r_A_r2_AC();
				break;
			case parentFirstOrthogonalReactions_r_B:
				react_ParentFirstOrthogonalReactions_r_B();
				break;
			case parentFirstOrthogonalReactions_r2_C:
				react_ParentFirstOrthogonalReactions_r2_C();
				break;
			case parentFirstOrthogonalReactions_r2_D:
				react_ParentFirstOrthogonalReactions_r2_D();
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
}
