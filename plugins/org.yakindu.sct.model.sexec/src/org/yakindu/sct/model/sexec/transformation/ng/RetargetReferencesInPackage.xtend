/**
 * Copyright (c) 2019 itemis AG - All rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * 
 * Contributors:
 *  Thomas Kutz - itemis AG
 */
package org.yakindu.sct.model.sexec.transformation.ng

import org.eclipse.emf.ecore.EObject
import org.yakindu.base.expressions.expressions.ElementReferenceExpression
import org.yakindu.base.types.Package
import com.google.inject.Inject
import org.yakindu.base.types.Declaration
import org.yakindu.base.expressions.expressions.FeatureCall
import org.yakindu.sct.model.stext.stext.InterfaceScope

class RetargetReferencesInPackage {
	
	@Inject
	protected extension StatemachinePublic
	
	def retargetReferences(Package p) {
		
		p.eAllContents.filter(ElementReferenceExpression).filter[!reference.containedIn(p)].forEach[ref | ref.retarget]
		p.eAllContents.filter(FeatureCall).filter[!feature.containedIn(p)].forEach[ref | ref.retarget]
	}
	
	protected def boolean containedIn(EObject exp, EObject toCheck) {
		var container = exp.eContainer
		if (container === null) {
			return false
		}
		if (container === toCheck) {
			return true;
		}
		return container.containedIn(toCheck)
	}
	
	protected def retarget(ElementReferenceExpression ere) {
		val newRef = ere.reference.replaced
		if (newRef !== null) ere.reference = newRef
	}
	
	protected def retarget(FeatureCall fc) {
		val newRef = fc.feature.replaced
		if (newRef !== null) fc.feature = newRef
	}
	
	protected def dispatch replaced(EObject e) { 
		null
	}
	
	protected def dispatch replaced(Declaration e) {
		return e.feature
	}
	
	protected def dispatch replaced(InterfaceScope e) {
		return e.property
	}
	
	
}