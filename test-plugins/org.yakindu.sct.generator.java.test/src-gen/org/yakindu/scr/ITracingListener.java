package org.yakindu.scr;

/**
 * Tracing interface for state machines.
 * Must be implemented if tracing feature is used.
 * Multiple listeners can be added to and removed from the state machine.
 */
public interface ITracingListener<T> {
	
	/**
	 * OnStateEntered will be called if any state has been entered.
	 */
	void onStateEntered(T state);
	
	/**
	* OnStateExited will be called if any state has been exited.
	*/
	void onStateExited(T state);
}
