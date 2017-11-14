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
package org.yakindu.sct.generator.cpp.language;

import org.yakindu.sct.generator.c.language.CKeywords;
import org.yakindu.sct.generator.core.language.ICodePart;

/**
 * @author rbeckmann
 *
 */
public enum Visibility implements ICodePart {
	PUBLIC		(CKeywords.PUBLIC),
	PROTECTED 	(CKeywords.PROTECTED),
	PRIVATE 	(CKeywords.PRIVATE)
	;
	
	protected String s;
	Visibility(String s) {
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
}
