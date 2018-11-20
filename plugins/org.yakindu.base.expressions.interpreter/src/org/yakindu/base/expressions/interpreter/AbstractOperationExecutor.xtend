/**
 * Copyright (c) 2018 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.base.expressions.interpreter

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.yakindu.base.base.NamedElement
import org.yakindu.base.expressions.expressions.Argument
import org.yakindu.base.expressions.expressions.ElementReferenceExpression
import org.yakindu.base.expressions.expressions.FeatureCall
import org.yakindu.base.expressions.expressions.util.ArgumentSorter
import org.yakindu.base.types.Operation
import org.yakindu.sct.model.sruntime.ExecutionContext

/**
 * @author andreas muelder - Initial contribution and API 
 * 
 */
abstract class AbstractOperationExecutor implements IOperationExecutor {

	@Inject extension protected IExpressionInterpreter interpreter

	def dispatch getOperation(ElementReferenceExpression it) {
		reference as Operation
	}

	def dispatch getOperation(FeatureCall it) {
		feature as Operation
	}

	def dispatch NamedElement getOwner(FeatureCall it) {
		val owner = owner
		if (owner instanceof ElementReferenceExpression) {
			return owner.reference as NamedElement
		} else
			owner.owner
	}

	def dispatch NamedElement getOwner(ElementReferenceExpression it) {
		null
	}

	def dispatch NamedElement getOwner(EObject it) {}

	def executeArguments(List<Argument> arguments, ExecutionContext context, Operation operation) {
		val orderedExpressions = ArgumentSorter.getOrderedExpressions(arguments, operation)
		orderedExpressions.map(it|evaluate(context)).toArray
	}

}
