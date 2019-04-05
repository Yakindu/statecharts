/**
 * Copyright (c) 2019 itemis AG - All rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * 
 * Contributors:
 *  axel terfloth - itemis AG
 */
package org.yakindu.sct.model.sexec.transformation.ng

import com.google.inject.Inject
import com.google.inject.Singleton
import org.yakindu.base.types.TypesFactory
import org.yakindu.sct.model.sgraph.RegularState

@Singleton class StateType {
	
	extension TypesFactory tFactory = TypesFactory.eINSTANCE
	@Inject extension RegionType regionType
	@Inject extension ModelSequencerNaming
	
	def create createComplexType type(RegularState state) {
		it => [
			name = state.stateTypeName
			state.parentRegion.type.features += it
		]
	}
}