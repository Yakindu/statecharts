package org.yakindu.scr.tracing;

import org.yakindu.scr.IStatemachine;

public interface ITracingStatemachine extends IStatemachine {
	public interface SCInterface {
	
	}
	
	public SCInterface getSCInterface();
	
}
