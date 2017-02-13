/**
 *   Copyright (c) 2016 committers of YAKINDU Statechart Tools.
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   which accompanies this distribution, and is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 * 		@author René Beckmann (beckmann@itemis.de)
 */

package org.yakindu.sct.model.sexec.naming

import java.lang.String

class ShortString {
	/*
	 * Class that manages a string and shortened versions of it.
	 */
	private String originalString;

	private int[] cutArray; // holds information if char is cut or not
	private int[] previous_cutArray; // holds previous state for rollback / undo possibility
	// cost of cutting operations
	final static public int COST_LOWERCASE_VOCALS = 1;
	final static public int COST_UNDERSCORE = 1;
	final static public int COST_LOWERCASE_CONSONANTS = 2;
	final static public int COST_UPPERCASE = 3;
	final static public int COST_DIGIT = 10;
	final static public int COST_FIRSTLETTER = 10;

	new(String s) {
		if (s == null) {
			originalString = "";
		} else {
			originalString = s;
		}
		cutArray = newIntArrayOfSize(size);
		previous_cutArray = newIntArrayOfSize(size);
		reset();
		saveCurrentToPrevious();
	}

	def public getOriginalString() {
		originalString
	}

	def private int size() {
		// instead of saving originalString.length as an own member
		originalString.length
	}

	def public reset() {
		// Reset cut_array so that the shortened String is equal to the original String.
		saveCurrentToPrevious();
		for (var i = 0; i < size; i++) {
			cutArray.set(i, 1);
		}
	}

	def private saveCurrentToPrevious() {
		// save current cut-state to previous_cutArray.
		for (var i = 0; i < size; i++) {
			previous_cutArray.set(i, cutArray.get(i));
		}
	}

	def public rollback() {
		// return to previous state
		for (var i = 0; i < size; i++) {
			cutArray.set(i, previous_cutArray.get(i));
		}
	}

	def public String getShortenedString() {
		// return the current version of the shortened string according to cutArray
		var sb = new StringBuilder();

		for (var i = 0; i < size; i++) {
			if (cutArray.get(i) != 0) {
				sb.append(originalString.charAt(i));
			}
		}

		sb.toString;
	}

	def public int getShortenedSize() {
		var length = 0;
		for (var i = 0; i < size; i++) {
			if (cutArray.get(i) != 0) {
				length += 1;
			}
		}

		return length;
	}

	def public int getCutCostFactor() {
		// factor that takes into account how much of the string is already cut, so that cutting away more characters get's more expensive
		return 10 + (getCutRatio() * 10) as int;
	}

	def public int getCutCost() {
		// returns the current cutting cost of the ShortString by iterating over the cutArray and accumulating cost per index
		if (1.0 - getCutRatio() < 0.001) {
			return Integer.MAX_VALUE;
		}
		var cost = 0;

		for (var i = 0; i < size; i++) {
			if (cutArray.get(i) == 0) {
				cost += getBaseCutCost(i);
			}
		}

		return cost * getCutCostFactor;
	}

	def public int getBaseCutCost(int index) {
		// returns the cut cost of this char, independent of its cut state.
		var cost = 0;

		var c = originalString.charAt(index);

		if (index == 0) {
			cost += org.yakindu.sct.model.sexec.naming.ShortString.COST_FIRSTLETTER;
		}
		if (Character.isDigit(c)) {
			cost += org.yakindu.sct.model.sexec.naming.ShortString.COST_DIGIT;
		} else if (Character.isUpperCase(c)) {
			cost += org.yakindu.sct.model.sexec.naming.ShortString.COST_UPPERCASE;
		} else if (isLowercaseVocal(c)) {
			cost += org.yakindu.sct.model.sexec.naming.ShortString.COST_LOWERCASE_VOCALS;
		} else if (c.toString().equals("_")) {
			cost += org.yakindu.sct.model.sexec.naming.ShortString.COST_UNDERSCORE;
		} else {
			cost += org.yakindu.sct.model.sexec.naming.ShortString.COST_LOWERCASE_CONSONANTS;
		}

		return cost;
	}

	def public removeCheapestChar() {
		// of all possible characters that aren't cut yet, remove the one with the lowest cutting cost.
		// saveCurrentToPrevious(); - done in removeIndex(i);
		var cheapestOperation_cost = Integer.MAX_VALUE;
		var cheapestOperation_index = 0;
		for (var i = 0; i < size; i++) {
			if (cutArray.get(i) != 0) {
				var cost = getBaseCutCost(i);
				if (cost < cheapestOperation_cost) {
					cheapestOperation_cost = cost;
					cheapestOperation_index = i;
				}
			}
		}

		removeIndex(cheapestOperation_index);
	}

	def public float getCutRatio() {
		// returns the ratio of characters that are cut.
		1 - ((getShortenedSize as float) / (size as float))
	}

	def public removeIndex(int index) {
		saveCurrentToPrevious();
		if (index < size) {
			cutArray.set(index, 0);
		}
	}

	def private boolean isLowercaseVocal(int i) {
		var c = originalString.charAt(i);
		return isLowercaseVocal(c);
	}

	def private boolean isLowercaseVocal(char c) {
		val s = c.toString();
		return (s == "a" || s == "e" || s == "i" || s == "o" || s == "u");
	}
}
