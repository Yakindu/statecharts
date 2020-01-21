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
 
package org.yakindu.sct.generator.cpp

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.yakindu.base.expressions.expressions.ArgumentExpression
import org.yakindu.base.expressions.expressions.BoolLiteral
import org.yakindu.base.expressions.expressions.ElementReferenceExpression
import org.yakindu.base.expressions.expressions.FeatureCall
import org.yakindu.base.expressions.expressions.LogicalNotExpression
import org.yakindu.base.expressions.expressions.StringLiteral
import org.yakindu.base.expressions.util.ExpressionExtensions
import org.yakindu.base.types.ComplexType
import org.yakindu.base.types.Expression
import org.yakindu.base.types.Operation
import org.yakindu.base.types.TypedElement
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.generator.c.CExpressionsGenerator
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.naming.INamingService
import org.yakindu.sct.model.stext.stext.ActiveStateReferenceExpression
import org.yakindu.sct.model.stext.stext.EventRaisingExpression

import static org.yakindu.sct.generator.c.CGeneratorConstants.*
import org.yakindu.sct.model.sexec.Method
import org.yakindu.sct.model.stext.stext.EventDefinition
import org.yakindu.base.types.Event
import org.yakindu.sct.model.stext.stext.VariableDefinition
import org.yakindu.sct.model.stext.stext.EventValueReferenceExpression

class CppExpressionsGenerator extends CExpressionsGenerator {

	@Inject protected extension CppNaming
	@Inject protected extension SExecExtensions
	@Inject protected extension ITypeSystem
	@Inject protected extension INamingService
	@Inject protected extension ExpressionExtensions
	@Inject protected extension EventRaisingCode

	override dispatch CharSequence code(ElementReferenceExpression it, Operation target) '''«target.access»(«argumentsCode»)'''
	
	override dispatch CharSequence code(ElementReferenceExpression it, Method target) '''«target.access»(«argumentsCode»)'''
	
	def dispatch CharSequence code(ElementReferenceExpression it, Event target) '''«target.access»'''

	override dispatch CharSequence code(EventRaisingExpression it) '''«raiseEvent(it, value?.code)»'''

	override dispatch CharSequence code(ActiveStateReferenceExpression it) '''«flow.stateActiveFctID»(«value.shortName»)'''
	
	override dispatch CharSequence code(EventValueReferenceExpression it) {
		val fc = value
		if (fc instanceof FeatureCall) {
			if (fc.feature.isExternal) {
				return '''«fc.owner.code»«fc.owner.callSep»«fc.feature.asGetter»()'''
			}
		}
		return '''«fc.featureOrReference.valueAccess»'''
	}
	
	
	/* Feature Call */
	override dispatch CharSequence code(FeatureCall it, Operation target) '''«owner.code»«owner.callSep»«target.access»(«argumentsCode»)'''
	
	override dispatch CharSequence code(FeatureCall it, EventDefinition target) '''«owner.code»«owner.callSep»«target.access»'''
	
	override dispatch CharSequence code(FeatureCall it, VariableDefinition target) '''«owner.code»«owner.callSep»«target.access»'''
	

	/* Literals */
	override dispatch CharSequence code(BoolLiteral it) '''«IF value»true«ELSE»false«ENDIF»'''
	
	override dispatch CharSequence code(StringLiteral it) '''(«STRING_TYPE») «super._code(it)»'''
	
	/** Don't use bool_true for C++ code */
	override dispatch CharSequence sc_boolean_code(Expression it) {code}
	
	/** Don't use bool_false for C++ code */
	override dispatch CharSequence sc_boolean_code(LogicalNotExpression it) {code}
	
	protected def argumentsCode(ArgumentExpression it) '''«FOR arg : expressions SEPARATOR ', '»«arg.code»«ENDFOR»'''
	
	protected def dispatch CharSequence callSep(EObject owner) ''''''
	protected def dispatch CharSequence callSep(Package owner) '''::''' // namespaces are separated by ::
	protected def dispatch CharSequence callSep(ArgumentExpression it) { featureOrReference.callSep }
	protected def dispatch CharSequence callSep(TypedElement it) { if (type instanceof ComplexType) '''->''' else '''.''' }
		
}
