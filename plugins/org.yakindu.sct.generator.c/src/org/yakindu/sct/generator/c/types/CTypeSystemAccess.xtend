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
package org.yakindu.sct.generator.c.types

import com.google.inject.Inject
import org.yakindu.base.types.Type
import org.yakindu.base.types.TypeSpecifier
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.generator.core.types.ICodegenTypeSystemAccess

import static org.yakindu.base.types.typesystem.ITypeSystem.*

/**
 * @author andreas muelder
 */
class CTypeSystemAccess implements ICodegenTypeSystemAccess {

	@Inject
	extension ITypeSystem ts

	override getTargetLanguageName(Type type) {
		val originialType = type?.originType
		switch (originialType) {
			case originialType == null || ts.isSame(originialType, getType(VOID)) : 'void'
			case ts.isSame(originialType, getType(INTEGER)): 'sc_integer'
			case ts.isSame(originialType, getType(REAL)): 'sc_real'
			case ts.isSame(originialType, getType(BOOLEAN)): 'sc_boolean'
			case ts.isSame(originialType, getType(STRING)): 'sc_string'
			default: throw new IllegalArgumentException("Target language name could not be inferred for type " + type)
		}
	}
	
	override getTargetLanguageName(TypeSpecifier typeSpecifier) {
		return getTargetLanguageName(typeSpecifier?.type)
	}

}
