/** 
 * Copyright (c) 2020 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.model.sexec.extensions

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.yakindu.base.base.NamedElement
import org.yakindu.base.expressions.expressions.FeatureCall
import org.yakindu.base.expressions.util.ExpressionExtensions
import org.yakindu.base.types.Direction
import org.yakindu.base.types.Event
import org.yakindu.base.types.Property
import org.yakindu.base.types.TypeBuilder
import org.yakindu.base.types.adapter.OriginTracing
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.concepts.ShadowMemberScope
import org.yakindu.sct.model.sgraph.util.StatechartUtil
import org.yakindu.sct.model.stext.stext.InterfaceScope
import org.yakindu.sct.model.stext.stext.InternalScope
import org.yakindu.sct.model.stext.stext.StatechartScope
import org.yakindu.sct.model.stext.stext.StextFactory

@Singleton
class ShadowEventExtensions {

	@Inject protected extension ShadowMemberScope
	
	@Inject protected extension OriginTracing
	@Inject protected extension SExecExtensions
	@Inject protected extension ExpressionExtensions
	@Inject protected extension StatechartUtil
	@Inject protected extension TypeBuilder
	
	

	def getShadowEvents(Property member) {
		member.flow.shadowEvents.filter[originTraces.contains(member)]
	}

	def getShadowEvent(Property member, Event outEvent) {
		member.shadowEvents.findFirst[originTraces.contains(outEvent)]
	}

	def getShadowEvents(ExecutionFlow flow) {
		var internalEvents = flow.scopes.filter(InternalScope).map[members].flatten.filter(Event)
		// in events in internal scope => must be a shadow event
		internalEvents.filter[direction == Direction.IN]
	}
	
	def getShadowEvents(StatechartScope it) {
		members.filter[scope.flow.shadowEvents.toList.contains(it)].filter(Event)
	}
	
	def getShadowEventsByScope(Property member) {
		member.shadowEvents.groupBy[originTraces.filter(Event).head.eContainer as InterfaceScope]
	}

	def getShadowEventName(FeatureCall fc) {
		fc.toCallStack.map[featureOrReference].filter(NamedElement).map[name].join("_")
	}
	
	def needsShadowEventMapping(Property member) {
		member.type.isOriginStatechart && !member.shadowEvents.nullOrEmpty
	}
	
	def getOutEvent(Event shadowEvent) {
		shadowEvent.originTraces.filter(Event).filter[direction == Direction.OUT].head
	}

	def create StextFactory.eINSTANCE.createEventDefinition createShadowEvent(String shadowEventName,
		EObject member, Event originEvent, ExecutionFlow flow) {

		name = shadowEventName
		direction = Direction.IN
		typeSpecifier = EcoreUtil.copy(originEvent.typeSpecifier)
		if ( type !== null && type != _void ) _meta(_variable("value", type))

		// trace to statechart event, not the one in the statechart type
		traceOrigin(originEvent.originTraces.filter(Event).head)
		// also trace to submachine member, so we can properly trace back in getShadowEvent(member, event)
		traceOrigin(member)

		flow.shadowMemberScope.members += it
	}

}
