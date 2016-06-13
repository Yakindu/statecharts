/** 
 * Copyright (c) 2015 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.sexec;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Save History</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.yakindu.sct.model.sexec.SaveHistory#getRegion <em>Region</em>}</li>
 *   <li>{@link org.yakindu.sct.model.sexec.SaveHistory#isDeep <em>Deep</em>}</li>
 * </ul>
 *
 * @see org.yakindu.sct.model.sexec.SexecPackage#getSaveHistory()
 * @model
 * @generated
 */
public interface SaveHistory extends Step {
	/**
	 * Returns the value of the '<em><b>Region</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Region</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Region</em>' reference.
	 * @see #setRegion(ExecutionRegion)
	 * @see org.yakindu.sct.model.sexec.SexecPackage#getSaveHistory_Region()
	 * @model
	 * @generated
	 */
	ExecutionRegion getRegion();

	/**
	 * Sets the value of the '{@link org.yakindu.sct.model.sexec.SaveHistory#getRegion <em>Region</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Region</em>' reference.
	 * @see #getRegion()
	 * @generated
	 */
	void setRegion(ExecutionRegion value);

	/**
	 * Returns the value of the '<em><b>Deep</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deep</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deep</em>' attribute.
	 * @see #setDeep(boolean)
	 * @see org.yakindu.sct.model.sexec.SexecPackage#getSaveHistory_Deep()
	 * @model
	 * @generated
	 */
	boolean isDeep();

	/**
	 * Sets the value of the '{@link org.yakindu.sct.model.sexec.SaveHistory#isDeep <em>Deep</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deep</em>' attribute.
	 * @see #isDeep()
	 * @generated
	 */
	void setDeep(boolean value);

} // SaveHistory
