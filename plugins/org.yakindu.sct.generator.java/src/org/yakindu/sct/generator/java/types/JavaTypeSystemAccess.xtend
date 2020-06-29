/**
 * Copyright (c) 2012 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.generator.java.types

import com.google.inject.Inject
import org.yakindu.base.types.ComplexType
import org.yakindu.base.types.EnumerationType
import org.yakindu.base.types.Type
import org.yakindu.base.types.TypeSpecifier
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess
import org.yakindu.sct.generator.java.Naming
import org.yakindu.sct.model.sgraph.util.StatechartUtil

import static org.yakindu.base.types.typesystem.ITypeSystem.*
import org.yakindu.sct.model.sexec.concepts.EventBuffer

/**
 * @author andreas muelder
 */
class JavaTypeSystemAccess implements ICodegenTypeSystemAccess {

	@Inject
	protected extension ITypeSystem ts
	
	@Inject
	protected extension Naming
	
	@Inject
	protected extension StatechartUtil
	
	@Inject
	protected extension EventBuffer

	override String getTargetLanguageName(Type type) {
		val originalType = type?.originType
		switch (originalType) {
			case originalType === null || ts.isSame(originalType, getType(VOID)) : 'void'
			case ts.isReal(originalType): "double"
			case ts.isInteger(originalType): "long"
			case ts.isBoolean(originalType): "boolean"
			case ts.isString(originalType): "String"
			// multi SM
			EnumerationType case originalType.isOriginStatechart: originalType.getOriginStatechart.statemachineClassName + ".State"
			ComplexType case originalType.isOriginStatechart: originalType.getOriginStatechart.statemachineClassName
			
			case originalType.isEventBuffer: originalType.eventBufferTypeName
			
			default: "//" + this
		};
	}
	
	override getTargetLanguageName(TypeSpecifier typeSpecifier) {
		return getTargetLanguageName(typeSpecifier?.type)
	}
}
