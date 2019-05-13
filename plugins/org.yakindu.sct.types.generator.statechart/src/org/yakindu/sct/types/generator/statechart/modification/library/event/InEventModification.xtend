/**
* Copyright (c) 2019 itemis AG - All rights Reserved
* Unauthorized copying of this file, via any medium is strictly prohibited
* 
* Contributors:
* 	itemis AG
*
*/
package org.yakindu.sct.types.generator.statechart.modification.library.event

import com.google.inject.Inject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.yakindu.base.expressions.util.ContainmentExtensions
import org.yakindu.base.expressions.util.ExpressionBuilder
import org.yakindu.base.types.Event
import org.yakindu.base.types.TypeBuilder
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.types.generator.modification.library.ReferenceExtension

class InEventModification extends BaseEventModification {

	@Inject protected extension TypeBuilder
	@Inject protected extension ExpressionBuilder
	@Inject protected extension ContainmentExtensions
	@Inject protected extension ReferenceExtension
	
	@Inject protected extension RaiseEventModification
	@Inject protected extension ClearEventModification
	@Inject protected extension ValueOfEventModification

	override modifyInEvent(Event e) {
		if (!e.hasPayload) {
			val prop = createEventFlag(e)
			e.eContainer.add(prop)
			
			val op = operation(nameEventRaiser(e.name), assign(prop, true))
			e.eContainer.add(op)
	
			modifyRaiseEvent(prop, e)
			modifyClearEvent(prop, e)
	
			e.references.forEach[retargetTo(prop)]
			EcoreUtil.remove(e)
		}
		
		else {
			val prop = createEventFlag(e)
			e.eContainer.add(prop)
			
			val valueProp = createEventValueProp(e)
			e.eContainer.add(valueProp)

			val op = _op(nameEventRaiser(e.name), ITypeSystem.VOID)._param("value", e.type) => [
				body = _block(
					prop._ref._assign(_true),
					valueProp._ref._assign(parameters.head._ref)
				)
			]
			e.eContainer.add(op)
			
			modifyRaiseEvent(prop, valueProp, e)
			modifyClearEvent(prop, e)
			modifyValueOfEvent(valueProp, e)
	
			e.references.forEach[retargetTo(prop)]
			EcoreUtil.remove(e)
		}
		
	}

}