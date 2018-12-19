package org.yakindu.scr.floatmodulo;

import org.yakindu.scr.IStatemachine;

public interface IFloatModuloStatemachine extends IStatemachine {
	public interface SCInterface {
	
		public double getR();
		
		public void setR(double value);
		
		public long getI();
		
		public void setI(long value);
		
	}
	
	public SCInterface getSCInterface();
	
	public interface SCIA {
	
		public double getR();
		
		public void setR(double value);
		
		public long getI();
		
		public void setI(long value);
		
	}
	
	public SCIA getSCIA();
	
}
