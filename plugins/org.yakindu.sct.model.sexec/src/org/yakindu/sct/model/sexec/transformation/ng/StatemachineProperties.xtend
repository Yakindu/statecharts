/**
 * Copyright (c) 2019 itemis AG - All rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * 
 * Contributors:
 *  thomas kutz - itemis AG
 */
package org.yakindu.sct.model.sexec.transformation.ng

import com.google.inject.Inject
import com.google.inject.Singleton
import org.yakindu.base.expressions.util.ExpressionBuilder
import org.yakindu.base.types.ComplexType
import org.yakindu.base.types.TypeBuilder
import org.yakindu.base.types.TypesFactory
import org.yakindu.base.types.Visibility
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.model.sexec.transformation.ArrayType
import org.yakindu.sct.model.sexec.transformation.SgraphExtensions
import org.yakindu.sct.model.sexec.transformation.ng.vectors.HistoryVector
import org.yakindu.sct.model.sexec.transformation.ng.vectors.StateVector
import org.yakindu.sct.model.sgraph.Statechart

@Singleton class StatemachineProperties {
	
	@Inject extension StatemachinePublic
	@Inject extension SgraphExtensions
	@Inject extension StateVector
	@Inject extension HistoryVector
	
	@Inject extension TypeBuilder
	@Inject extension ExpressionBuilder
	@Inject extension ArrayType
	
	extension TypesFactory typesFactory = TypesFactory.eINSTANCE
	
	def defineProperties(ComplexType it, Statechart sc) {
		it.features += stateVectorProperty(sc)
		if (sc.requireHistory) {
			it.features += historyVectorProperty(sc)
		}
		it.features += nextStateIndex(sc)
	}
	
	def create createProperty nextStateIndex(Statechart sc) {
		name = "nextStateIndex"
		_type(ITypeSystem.INTEGER)
		visibility = Visibility.PROTECTED
	}
	
	def create createProperty stateVectorProperty(Statechart sc) {
		name = "stateVector"
		typeSpecifier = _array._of(sc.statesEnumeration)
		visibility = Visibility.PROTECTED
		
		val size = sc.stateVector.size
		initialValue = _array._ref._fc(_array._new, size._int)
	}
	
	def create createProperty historyVectorProperty(Statechart sc) {
		name = "historyStateVector"
		typeSpecifier = _array._of(sc.statesEnumeration)
		visibility = Visibility.PROTECTED
		
		val size = sc.historyVector.size
		initialValue = _array._ref._fc(_array._new, size._int)
	}
	
}