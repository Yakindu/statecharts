/**
 * Copyright (c) 2017 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	rbeckmann - initial API and implementation
 * 
 */
package org.yakindu.sct.generator.c.language;

import org.yakindu.sct.generator.core.language.IType;

/**
 * @author rbeckmann
 *
 */
public enum Type implements IType {
	VOID (CKeywords.VOID),
	INT ("sc_integer"),
	BOOL ("sc_boolean"),
	SHORT ("sc_ushort")
	;
	
	protected String s;
	Type(String s) {
		this.s = s;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return s;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.CharSequence#charAt(int)
	 */
	@Override
	public char charAt(int index) {
		return toString().charAt(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.CharSequence#length()
	 */
	@Override
	public int length() {
		return toString().length();
	}

	/* (non-Javadoc)
	 * @see java.lang.CharSequence#subSequence(int, int)
	 */
	@Override
	public CharSequence subSequence(int start, int end) {
		return toString().subSequence(start, end);
	}



	/* (non-Javadoc)
	 * @see org.yakindu.sct.generator.core.language.INameOwner#getName()
	 */
	@Override
	public CharSequence getName() {
		// TODO Auto-generated method stub
		return s;
	}



	/* (non-Javadoc)
	 * @see org.yakindu.sct.generator.core.language.INameOwner#setName(java.lang.CharSequence)
	 */
	@Override
	public void setName(CharSequence name) {
	}

}
