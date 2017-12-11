package org.yakindu.scr.conditionalexpressions;

import org.yakindu.scr.IStatemachine;

public interface IConditionalExpressionsStatemachine extends IStatemachine {

	public interface SCInterface {
	
		public void raiseE();
		
		public long getCondition();
		
		public void setCondition(long value);
		
		public boolean getBoolVar();
		
		public void setBoolVar(boolean value);
		
		public String getStringVar();
		
		public void setStringVar(String value);
		
		public String getStringCondition();
		
		public void setStringCondition(String value);
		
	}
	
	public SCInterface getSCInterface();
	
}
