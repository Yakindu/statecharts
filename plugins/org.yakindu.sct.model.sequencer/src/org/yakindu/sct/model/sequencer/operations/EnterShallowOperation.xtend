/** 
 * Copyright (c) 2019 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.sequencer.operations

import com.google.inject.Inject
import com.google.inject.Singleton
import org.yakindu.base.expressions.util.ExpressionBuilder
import org.yakindu.base.types.TypeBuilder
import org.yakindu.base.types.Visibility
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.model.sequencer.types.RegionType
import org.yakindu.sct.model.sequencer.types.StateType
import org.yakindu.sct.model.sequencer.types.StatemachineProperties
import org.yakindu.sct.model.sequencer.types.StatemachinePublic
import org.yakindu.sct.model.sequencer.util.SgraphExtensions
import org.yakindu.sct.model.sequencer.vectors.HistoryVector
import org.yakindu.sct.model.sequencer.vectors.StateVector
import org.yakindu.sct.model.sgraph.Region
import org.yakindu.sct.model.sgraph.State
import org.yakindu.sct.model.sgraph.Statechart

@Singleton
class EnterShallowOperation {
	
	@Inject extension TypeBuilder
	@Inject extension ExpressionBuilder
	
	@Inject extension RegionType
	@Inject extension StateType
	
	@Inject extension SgraphExtensions
	@Inject extension StateVector
	@Inject extension HistoryVector
	@Inject extension StatemachineProperties
	@Inject extension StatemachinePublic
	@Inject extension EnterOperation
	
	def declareEnterShallowOperations(Statechart sc) {
		sc.eAllContents.filter(Region).filter[requireShallowHistory].forEach[enterShallow]
	}
	
	def defineEnterShallowOperations(Statechart sc) {
		sc.eAllContents.filter(Region).filter[requireShallowHistory].forEach[defineEnterShallow]
	}
	
	def create _op enterShallow(Region r) {
		name = "enter_shallow"
		_type(ITypeSystem.VOID)
		visibility = Visibility.PROTECTED
		r.type.features += it
	}
	
	def protected defineEnterShallow(Region r) {
		r.enterShallow => [
			body = _block(r.shallowHistorySwitch)
		]
	}
	
	def protected shallowHistorySwitch(Region r) {
		val sc = r.statechart
		
		val historySwitch = _switch(sc.historyVectorProperty._ref._get(r.historyVector.offset._int))

		for (child : r.vertices.filter(State)) {
			for (childLeaf : child.collectLeafStates(newArrayList).filter(c|c.stateVector.offset == r.stateVector.offset)) {
				historySwitch.cases += _case(childLeaf.enumerator._ref, child.type.defaultEnterSequence._call)
			}
		}
		historySwitch.^default = _block

		return historySwitch
	}
	
}
