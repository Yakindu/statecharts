/** 
 * Copyright (c) 2017 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 * 
 */

package org.yakindu.sct.generator.c

import com.google.inject.Inject
import org.yakindu.base.expressions.expressions.AssignmentExpression
import org.yakindu.base.expressions.expressions.AssignmentOperator
import org.yakindu.base.expressions.expressions.BoolLiteral
import org.yakindu.base.expressions.expressions.ElementReferenceExpression
import org.yakindu.base.expressions.expressions.Expression
import org.yakindu.base.expressions.expressions.FeatureCall
import org.yakindu.base.expressions.expressions.LogicalAndExpression
import org.yakindu.base.expressions.expressions.LogicalNotExpression
import org.yakindu.base.expressions.expressions.LogicalOrExpression
import org.yakindu.base.expressions.expressions.LogicalRelationExpression
import org.yakindu.base.expressions.expressions.MultiplicativeOperator
import org.yakindu.base.expressions.expressions.NullLiteral
import org.yakindu.base.expressions.expressions.NumericalMultiplyDivideExpression
import org.yakindu.base.types.Enumerator
import org.yakindu.base.types.Event
import org.yakindu.base.types.Operation
import org.yakindu.base.types.Property
import org.yakindu.base.types.inferrer.ITypeSystemInferrer
import org.yakindu.base.types.typesystem.GenericTypeSystem
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.generator.c.extensions.Naming
import org.yakindu.sct.generator.core.templates.ExpressionsGenerator
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.naming.INamingService
import org.yakindu.sct.model.stext.stext.ActiveStateReferenceExpression
import org.yakindu.sct.model.stext.stext.EventRaisingExpression
import org.yakindu.sct.model.stext.stext.EventValueReferenceExpression
import org.yakindu.sct.model.stext.stext.OperationDefinition
import org.yakindu.sct.model.stext.stext.VariableDefinition

class CExpressionsGenerator extends ExpressionsGenerator {

	@Inject protected extension Naming
	@Inject protected extension SExecExtensions

	@Inject protected extension ITypeSystem
	@Inject protected extension ITypeSystemInferrer
	@Inject protected extension INamingService

	/* Referring to declared elements */
	def dispatch CharSequence code(ElementReferenceExpression it) {
		it.code(it.definition)
	}

	/* Expressions */
	def dispatch CharSequence code(Expression it, Event target) '''«target.access»'''

	def dispatch CharSequence code(Expression it, VariableDefinition target) '''«target.access»'''

	/* TODO: check if event is active */
	def dispatch CharSequence code(EventValueReferenceExpression it) '''«value.definition.event.valueAccess»'''

	def dispatch CharSequence code(ElementReferenceExpression it, VariableDefinition target) '''«target.access»'''

	def dispatch CharSequence code(ElementReferenceExpression it,
		OperationDefinition target) '''«target.access»(«scHandle»«FOR arg : expressions BEFORE ', ' SEPARATOR ', '»«arg.
		code»«ENDFOR»)'''

	def dispatch CharSequence code(ElementReferenceExpression it,
		Operation target) '''«target.access»(«FOR arg : expressions SEPARATOR ', '»«arg.code»«ENDFOR»)'''

	def dispatch CharSequence code(ElementReferenceExpression it, Property target) '''«target.access»'''

	def dispatch CharSequence code(EventRaisingExpression it) '''
	«IF value !== null»
		«event.definition.event.valueAccess» = «value.code»;
	«ENDIF»
	«event.definition.event.access» = bool_true'''

	def dispatch CharSequence code(
		ActiveStateReferenceExpression it) '''«flow.stateActiveFctID»(«scHandle», «value.shortName»)'''

	def dispatch CharSequence code(LogicalRelationExpression it) '''
	«IF isSame(leftOperand.infer.type, getType(GenericTypeSystem.STRING))»
		(strcmp(«leftOperand.code», «rightOperand.code») «operator.literal» 0)
	«ELSE»«leftOperand.code» «operator.literal» «rightOperand.code»«ENDIF»'''

	override dispatch CharSequence code(AssignmentExpression it) {
		if (it.operator.equals(AssignmentOperator.MOD_ASSIGN) && haveCommonTypeReal(it)) {
			'''«varRef.code» = fmod(«varRef.code»,«expression.code»)'''
		} else
			'''«varRef.code» «operator.literal» «expression.code»'''
	}

	def dispatch CharSequence code(NumericalMultiplyDivideExpression expression) {
		if (expression.operator == MultiplicativeOperator.MOD && haveCommonTypeReal(expression)) {
			'''fmod(«expression.leftOperand.code.toString.trim»,«expression.rightOperand.code»)'''
		} else {
			super._code(expression);
		}
	}

	/* Feature call */
	def dispatch CharSequence code(FeatureCall it) {
		it.code(it.definition)
	}

	def dispatch CharSequence code(FeatureCall it, VariableDefinition target) '''«target.access»'''

	def dispatch CharSequence code(FeatureCall it,
		OperationDefinition target) '''«target.access»(«scHandle»«FOR arg : expressions BEFORE ', ' SEPARATOR ', '»«arg.
		code»«ENDFOR»)'''

	def dispatch CharSequence code(FeatureCall it,
		Operation target) '''«it.owner.code».«target.access»(«FOR arg : expressions SEPARATOR ', '»«arg.
		code»«ENDFOR»)'''

	def dispatch CharSequence code(FeatureCall it, Property target) '''«it.owner.code».«target.access»'''

	def dispatch CharSequence code(FeatureCall it, Enumerator target) '''«target.access»'''

	/* Literals */
	override dispatch CharSequence code(NullLiteral it) '''«Naming::NULL_STRING»'''

	override dispatch CharSequence code(BoolLiteral it) '''«IF value»bool_true«ELSE»bool_false«ENDIF»'''

	// ensure we obtain an expression of type sc_boolean
	def dispatch CharSequence sc_boolean_code(Expression it) '''«it.code»'''

	def dispatch CharSequence sc_boolean_code(LogicalOrExpression it) '''(«it.code») ? bool_true : bool_false'''

	def dispatch CharSequence sc_boolean_code(LogicalAndExpression it) '''(«it.code») ? bool_true : bool_false'''

	def dispatch CharSequence sc_boolean_code(LogicalNotExpression it) '''(«it.code») ? bool_true : bool_false'''

	def dispatch CharSequence sc_boolean_code(LogicalRelationExpression it) '''(«it.code») ? bool_true : bool_false'''

	def boolean haveCommonTypeReal(Expression expression) {
		if(isSame(getCommonType((infer(expression).getType), getType(ITypeSystem.INTEGER)),
			getType(ITypeSystem.INTEGER))) return false
		return true
	}

}
