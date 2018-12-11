/** 
 * Copyright (c) 2015 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 */
package org.yakindu.base.expressions

import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider
import org.eclipse.xtext.parser.antlr.ISyntaxErrorMessageProvider
import org.yakindu.base.expressions.inferrer.ExpressionsTypeInferrer
import org.yakindu.base.expressions.linking.LinkingDiagnosticMessageProvider
import org.yakindu.base.expressions.parser.SyntaxErrorMessageProvider
import org.yakindu.base.types.inferrer.ITypeSystemInferrer
import org.yakindu.base.types.typesystem.GenericTypeSystem
import org.yakindu.base.types.typesystem.ITypeSystem
import com.google.inject.Binder

class ExpressionsRuntimeModule extends org.yakindu.base.expressions.AbstractExpressionsRuntimeModule {
	override void configure(Binder binder) {
		super.configure(binder)
		binder.bind(ITypeSystem).toInstance(getTypeSystem())
		binder.bind(ILinkingDiagnosticMessageProvider).to(LinkingDiagnosticMessageProvider)
		binder.bind(ISyntaxErrorMessageProvider).to(SyntaxErrorMessageProvider)
	}

	def protected ITypeSystem getTypeSystem() {
		return GenericTypeSystem.getInstance()
	}

	def Class<? extends ITypeSystemInferrer> bindITypeSystemInferrer() {
		return ExpressionsTypeInferrer
	}
}