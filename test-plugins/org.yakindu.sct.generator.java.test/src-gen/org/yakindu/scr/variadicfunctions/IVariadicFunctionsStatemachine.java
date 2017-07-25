package org.yakindu.scr.variadicfunctions;

import org.yakindu.scr.IStatemachine;

public interface IVariadicFunctionsStatemachine extends IStatemachine {

	public interface SCInterface {
	
		public void setSCInterfaceOperationCallback(SCInterfaceOperationCallback operationCallback);
	
	}
	
	public interface SCInterfaceOperationCallback {
	
		public void myVarOperation(String... name);
		
	}
	
	public SCInterface getSCInterface();
	
	public interface SCIIF2 {
	
		public void setSCIIF2OperationCallback(SCIIF2OperationCallback operationCallback);
	
	}
	
	public interface SCIIF2OperationCallback {
	
		public void myVarOperation2(long argCount, long... args);
		
	}
	
	public SCIIF2 getSCIIF2();
	
	public interface InternalOperationCallback {
	
		public long myInternalVarOperation(double... test);
		
	}
	
	public void setInternalOperationCallback(InternalOperationCallback operationCallback);
	
}
