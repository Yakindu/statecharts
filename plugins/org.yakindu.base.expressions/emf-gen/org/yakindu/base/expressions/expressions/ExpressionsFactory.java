/**
 */
package org.yakindu.base.expressions.expressions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.yakindu.base.expressions.expressions.ExpressionsPackage
 * @generated
 */
public interface ExpressionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpressionsFactory eINSTANCE = org.yakindu.base.expressions.expressions.impl.ExpressionsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Bool Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Literal</em>'.
	 * @generated
	 */
	BoolLiteral createBoolLiteral();

	/**
	 * Returns a new object of class '<em>Int Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int Literal</em>'.
	 * @generated
	 */
	IntLiteral createIntLiteral();

	/**
	 * Returns a new object of class '<em>Double Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Double Literal</em>'.
	 * @generated
	 */
	DoubleLiteral createDoubleLiteral();

	/**
	 * Returns a new object of class '<em>Float Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float Literal</em>'.
	 * @generated
	 */
	FloatLiteral createFloatLiteral();

	/**
	 * Returns a new object of class '<em>Hex Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hex Literal</em>'.
	 * @generated
	 */
	HexLiteral createHexLiteral();

	/**
	 * Returns a new object of class '<em>Binary Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Literal</em>'.
	 * @generated
	 */
	BinaryLiteral createBinaryLiteral();

	/**
	 * Returns a new object of class '<em>String Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Literal</em>'.
	 * @generated
	 */
	StringLiteral createStringLiteral();

	/**
	 * Returns a new object of class '<em>Null Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Null Literal</em>'.
	 * @generated
	 */
	NullLiteral createNullLiteral();

	/**
	 * Returns a new object of class '<em>Assignment Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Assignment Expression</em>'.
	 * @generated
	 */
	AssignmentExpression createAssignmentExpression();

	/**
	 * Returns a new object of class '<em>Conditional Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Expression</em>'.
	 * @generated
	 */
	ConditionalExpression createConditionalExpression();

	/**
	 * Returns a new object of class '<em>Logical Or Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Logical Or Expression</em>'.
	 * @generated
	 */
	LogicalOrExpression createLogicalOrExpression();

	/**
	 * Returns a new object of class '<em>Logical And Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Logical And Expression</em>'.
	 * @generated
	 */
	LogicalAndExpression createLogicalAndExpression();

	/**
	 * Returns a new object of class '<em>Logical Not Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Logical Not Expression</em>'.
	 * @generated
	 */
	LogicalNotExpression createLogicalNotExpression();

	/**
	 * Returns a new object of class '<em>Bitwise Xor Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bitwise Xor Expression</em>'.
	 * @generated
	 */
	BitwiseXorExpression createBitwiseXorExpression();

	/**
	 * Returns a new object of class '<em>Bitwise Or Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bitwise Or Expression</em>'.
	 * @generated
	 */
	BitwiseOrExpression createBitwiseOrExpression();

	/**
	 * Returns a new object of class '<em>Bitwise And Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bitwise And Expression</em>'.
	 * @generated
	 */
	BitwiseAndExpression createBitwiseAndExpression();

	/**
	 * Returns a new object of class '<em>Logical Relation Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Logical Relation Expression</em>'.
	 * @generated
	 */
	LogicalRelationExpression createLogicalRelationExpression();

	/**
	 * Returns a new object of class '<em>Shift Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shift Expression</em>'.
	 * @generated
	 */
	ShiftExpression createShiftExpression();

	/**
	 * Returns a new object of class '<em>Numerical Add Subtract Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numerical Add Subtract Expression</em>'.
	 * @generated
	 */
	NumericalAddSubtractExpression createNumericalAddSubtractExpression();

	/**
	 * Returns a new object of class '<em>Numerical Multiply Divide Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numerical Multiply Divide Expression</em>'.
	 * @generated
	 */
	NumericalMultiplyDivideExpression createNumericalMultiplyDivideExpression();

	/**
	 * Returns a new object of class '<em>Numerical Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numerical Unary Expression</em>'.
	 * @generated
	 */
	NumericalUnaryExpression createNumericalUnaryExpression();

	/**
	 * Returns a new object of class '<em>Primitive Value Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive Value Expression</em>'.
	 * @generated
	 */
	PrimitiveValueExpression createPrimitiveValueExpression();

	/**
	 * Returns a new object of class '<em>Feature Call</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Call</em>'.
	 * @generated
	 */
	FeatureCall createFeatureCall();

	/**
	 * Returns a new object of class '<em>Meta Call</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Meta Call</em>'.
	 * @generated
	 */
	MetaCall createMetaCall();

	/**
	 * Returns a new object of class '<em>Element Reference Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Reference Expression</em>'.
	 * @generated
	 */
	ElementReferenceExpression createElementReferenceExpression();

	/**
	 * Returns a new object of class '<em>Parenthesized Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parenthesized Expression</em>'.
	 * @generated
	 */
	ParenthesizedExpression createParenthesizedExpression();

	/**
	 * Returns a new object of class '<em>Type Cast Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Cast Expression</em>'.
	 * @generated
	 */
	TypeCastExpression createTypeCastExpression();

	/**
	 * Returns a new object of class '<em>Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Argument</em>'.
	 * @generated
	 */
	Argument createArgument();

	/**
	 * Returns a new object of class '<em>If Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>If Expression</em>'.
	 * @generated
	 */
	IfExpression createIfExpression();

	/**
	 * Returns a new object of class '<em>Block Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block Expression</em>'.
	 * @generated
	 */
	BlockExpression createBlockExpression();

	/**
	 * Returns a new object of class '<em>While Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>While Expression</em>'.
	 * @generated
	 */
	WhileExpression createWhileExpression();

	/**
	 * Returns a new object of class '<em>Return Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Return Expression</em>'.
	 * @generated
	 */
	ReturnExpression createReturnExpression();

	/**
	 * Returns a new object of class '<em>Switch Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Switch Expression</em>'.
	 * @generated
	 */
	SwitchExpression createSwitchExpression();

	/**
	 * Returns a new object of class '<em>Switch Case</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Switch Case</em>'.
	 * @generated
	 */
	SwitchCase createSwitchCase();

	/**
	 * Returns a new object of class '<em>Event Raising Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Raising Expression</em>'.
	 * @generated
	 */
	EventRaisingExpression createEventRaisingExpression();

	/**
	 * Returns a new object of class '<em>Event Value Reference Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Value Reference Expression</em>'.
	 * @generated
	 */
	EventValueReferenceExpression createEventValueReferenceExpression();

	/**
	 * Returns a new object of class '<em>For Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>For Expression</em>'.
	 * @generated
	 */
	ForExpression createForExpression();

	/**
	 * Returns a new object of class '<em>Throw Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Throw Expression</em>'.
	 * @generated
	 */
	ThrowExpression createThrowExpression();

	/**
	 * Returns a new object of class '<em>Event Clearing Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Clearing Expression</em>'.
	 * @generated
	 */
	EventClearingExpression createEventClearingExpression();

	/**
	 * Returns a new object of class '<em>Post Fix Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Post Fix Unary Expression</em>'.
	 * @generated
	 */
	PostFixUnaryExpression createPostFixUnaryExpression();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ExpressionsPackage getExpressionsPackage();

} //ExpressionsFactory
