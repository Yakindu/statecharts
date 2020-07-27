/**
 * Copyright (c) 2013 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.model.sexec.extensions

import com.google.inject.Inject
import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.yakindu.base.expressions.expressions.AssignmentExpression
import org.yakindu.base.expressions.expressions.ElementReferenceExpression
import org.yakindu.base.expressions.expressions.FeatureCall
import org.yakindu.base.types.ComplexType
import org.yakindu.base.types.Declaration
import org.yakindu.base.types.Direction
import org.yakindu.base.types.Event
import org.yakindu.base.types.Expression
import org.yakindu.base.types.Property
import org.yakindu.base.types.adapter.OriginTracing
import org.yakindu.sct.model.sexec.Check
import org.yakindu.sct.model.sexec.ExecutionFlow
import org.yakindu.sct.model.sexec.ExecutionNode
import org.yakindu.sct.model.sexec.ExecutionRegion
import org.yakindu.sct.model.sexec.ExecutionScope
import org.yakindu.sct.model.sexec.ExecutionState
import org.yakindu.sct.model.sexec.Method
import org.yakindu.sct.model.sexec.Reaction
import org.yakindu.sct.model.sexec.Sequence
import org.yakindu.sct.model.sexec.Step
import org.yakindu.sct.model.sexec.TimeEvent
import org.yakindu.sct.model.sgraph.Scope
import org.yakindu.sct.model.sgraph.Statechart
import org.yakindu.sct.model.stext.stext.EventDefinition
import org.yakindu.sct.model.stext.stext.ImportScope
import org.yakindu.sct.model.stext.stext.InterfaceScope
import org.yakindu.sct.model.stext.stext.InternalScope
import org.yakindu.sct.model.stext.stext.OperationDefinition
import org.yakindu.sct.model.stext.stext.StatechartScope
import org.yakindu.sct.model.stext.stext.VariableDefinition

class SExecExtensions {
	@Inject extension OriginTracing
	
	
	def <T extends EObject> T eContainerOfType(EObject eObject, Class<T> type) {
		EcoreUtil2.getContainerOfType(eObject, type)
	}
	
	def isDefaultInterface(StatechartScope scope) {
		switch scope {
			InterfaceScope: scope.name === null || scope.name.empty
			default: false
		}
	}
	
	def ExecutionFlow flow(EObject it){
		eContainerOfType(ExecutionFlow)
	}
	
	def Statechart statechart(ExecutionFlow it) {
		sourceElement as Statechart
	}
	
	def scope(EObject it) {
		var container = eContainer
		if (container instanceof ComplexType){
			val origin = it.originTraces.head
			if(origin instanceof Declaration){
				container = origin.eContainer
			}
		}
		if (container instanceof Scope) container
		else null
	}
	
	def InterfaceScope getInterfaceScope(EObject it) {
		eContainerOfType(InterfaceScope)
	}
	
	def operations(ExecutionFlow it) {
		scopes.map[operations].flatten
	}
	
	def Scope getTimeEventScope(ExecutionFlow it) {
		return 	scopes.filter[declarations.filter(TimeEvent).size > 0].head
	}
	
	def isTimed (ExecutionFlow it) {
		!timeEvents.empty
	}
	
	def getStatechartScopes(ExecutionFlow it) {
		scopes.filter(StatechartScope)
	}
	
	def operations(Scope it) {
		declarations.filter(OperationDefinition);
	}
	
	def hasOperations(Scope it) {
		!operations.isEmpty;
	}
	
	def indexOf(TimeEvent it) {
		scope.declarations.filter(TimeEvent).toList.indexOf(it);
	}
	
	def getInterfaces(ExecutionFlow it) {
		scopes.filter(StatechartScope).filter[!(it instanceof ImportScope)]
	}
	
	def getProperties(ExecutionFlow it) {
		features.filter(Property)
	}
	
	def hasOperationCallbacks (ExecutionFlow it){
		scopes.filter[declarations.filter(OperationDefinition).size > 0].size > 0
	}
	
	def getTimeEvents(ExecutionFlow it) {
		scopes.map[declarations.filter(TimeEvent)].flatten.toList
	}
	
	def getTimeEvent(ExecutionFlow flow, String timeEventName) {
		flow.timeEvents.findFirst[name.compareTo(timeEventName) == 0]
	}
	
	def dispatch hasValue (Event it) {
		type !== null && type.name != 'void'
	}
	
	def dispatch hasValue(EObject it) {
		false;
	}
	
	def boolean hasInternalScope(ExecutionFlow it) {
		internalScope !== null;
	}
	
	def getInternalScope(ExecutionFlow it) {
		it.scopes.filter(typeof(InternalScope)).head
	}
	
	def hasHistory(ExecutionFlow it) {
		historyVector !== null && historyVector.size > 0;		
	}
	
	def hasOutgoingEvents(Scope it) {
		!outgoingEvents.empty
	}
	
	def hasOutgoingEvents(ExecutionFlow it) {
		!outgoingEvents.empty
	}
	
	def getInternalScopeEvents(ExecutionFlow flow) {
		flow.internalScopes.map[eventDefinitions].flatten
	}

	def getInternalScopeVariables(ExecutionFlow flow) {
		val variables = new ArrayList<VariableDefinition>
		flow.internalScopes.forEach[variables.addAll(variableDefinitions)]
		return variables
	}
	
	def getEventDefinitions(Scope scope) {
		scope.declarations.filter(EventDefinition)
	}
	
	def eventAndVariableDefinitions(Scope scope) {
		scope.declarations.filter[ e | e instanceof EventDefinition || e instanceof VariableDefinition]
	}
	
	def boolean hasEvents(Scope it) {
		return !eventDefinitions.empty
	}
	
	def boolean hasEvents(ExecutionFlow it) {
		return !getAllEvents.empty
	}
	
	def getAllEvents(ExecutionFlow it) {
		return scopes.map[eventDefinitions].flatten
	}
	
	def getAllEventAndVariables(ExecutionFlow it) {
		return scopes.map[eventAndVariableDefinitions].flatten
	}
	
	def hasLocalEvents(ExecutionFlow it) {
		return hasInternalScope && !internalScope.localEvents.empty
	}
	
	def hasLocalEventsWithValue(ExecutionFlow it) {
		return hasInternalScope && !internalScope.localEvents.filter[hasValue].empty
	}
	
	def getOutgoingEvents(Scope it) {
		eventDefinitions.filter[isOutEvent]
	}
	
	def Iterable<EventDefinition> getOutgoingEvents(ExecutionFlow it) {
		scopes.map[outgoingEvents].flatten
	}
	
	def hasInEvents(ExecutionFlow it) {
		!getIncomingEvents.empty
	}
	
	def hasIncomingEventsWithValue(Scope it) {
		!incomingEvents.filter[hasValue].empty
	}
	
	def List<EventDefinition> getIncomingEvents(Scope it) {
		declarations.filter(EventDefinition).filter[isInEvent].toList
	}
	
	def List<EventDefinition> getIncomingEvents(ExecutionFlow it) {
		interfaces.map[incomingEvents].flatten.toList
	}
	
	def List<EventDefinition> getLocalEvents(Scope it) {
		if(it === null) return emptyList
		declarations.filter(EventDefinition).filter[isLocalEvent].toList
	}
	
	def List<EventDefinition> getLocalEvents(ExecutionFlow it) {
		internalScope.localEvents
	}
	
	def boolean isLocalEvent(EventDefinition it) {
		direction === Direction::LOCAL
	}
	
	def boolean isOutEvent(EventDefinition it) {
		direction === Direction::OUT
	}
	
	def boolean isInEvent(EventDefinition it) {
		direction === Direction::IN
	}
	
	def getInterfaceScopes(ExecutionFlow it) {
		scopes.filter(InterfaceScope)
	}

	def Iterable<InternalScope> getInternalScopes(ExecutionFlow it) {
		return scopes.filter(InternalScope)
	}
	
	def getDefaultScope(ExecutionFlow it) {
		interfaceScopes.filter[isDefaultInterface].head
	}
	
	def getVariableDefinitions(Scope it) {
		declarations.filter(VariableDefinition)
	} 
	
	def dispatch definition(ElementReferenceExpression it) {
		if (reference instanceof Declaration) reference as Declaration
	}
	def dispatch definition(FeatureCall it) {
		feature as Declaration
	}
	
	def dispatch definition(EObject it){
		null
	}
	
	def needsAssignMethod(Property property) {
		property.flow.eAllContents.filter(AssignmentExpression)
			.filter[eContainer instanceof Expression 
				&& varRef.definition instanceof Property
			]
			.findFirst[(varRef.definition as Property).equals(property)] !== null
	}
	
	def EventDefinition event(Declaration it) {
		if (it instanceof EventDefinition) it else null 	
	}

	def dispatch List<ExecutionState> subStates(ExecutionState it) {
		subScopes.fold(new ArrayList<ExecutionState>, 
			[a, s | 
				a.addAll(s.subStates)
				a
			]
		)
	} 
	
	def dispatch List<ExecutionState> subStates(ExecutionRegion it) {
		subScopes.fold(new ArrayList<ExecutionState>, 
			[a, s | 
				a.add(s as ExecutionState)
				a.addAll(s.subStates)
				a
			]
		)
	} 
	
	def dispatch List<ExecutionState> subStates(ExecutionScope it) {
		return new ArrayList<ExecutionState>()
	}
	
	def referencedChecks(ExecutionNode it) {
		reactions.filter( r | r.check !== null && r.check.refs.size > 0).map[it.check]
	}

	def referencedEffects(ExecutionNode it) {
		reactions.filter( r | r.effect !== null && r.effect.caller.size > 0).map( r | r.effect )
	}
	
	def List<Step> checkFunctions(ExecutionFlow it) {
		val funcs = new ArrayList<Step>()
		funcs += referencedChecks
		states.forEach( s | funcs += s.referencedChecks )
		nodes.forEach( n | funcs += n.referencedChecks )
		funcs
	}
	 
	def List<Step> effectFunctions(ExecutionFlow it) {
		val funcs = new ArrayList<Step>()
		funcs += referencedEffects.filter[reachable]
		states.forEach( s | funcs += s.referencedEffects.filter[reachable] )
		nodes.forEach( n | funcs += n.referencedEffects.filter[reachable] )
		return funcs
	}
	 
	def List<Step> entryActionFunctions(ExecutionFlow it) {
		val funcs = new ArrayList<Step>()
		if (entryAction.reachable) funcs.add(entryAction) 
		states.forEach( s | if (s.entryAction.reachable) funcs += s.entryAction )
		return funcs
	}
	
	def List<Step> exitActionFunctions(ExecutionFlow it) {
		val funcs = new ArrayList<Step>()
		if (exitAction.reachable) funcs.add(exitAction) 
		states.forEach( s | if (s.exitAction.reachable) funcs += s.exitAction )
		return funcs
	}


	def dispatch Sequence ownerFunction(Sequence it){
		if ( (eContainer instanceof ExecutionNode) || (eContainer instanceof ExecutionScope) || (eContainer instanceof Reaction))  {
			return it	
		} else {
			return eContainer.ownerFunction
		}
	}
	
	def dispatch Sequence ownerFunction(EObject it){
		
		if (eContainer === null) return null
		
		return eContainer.ownerFunction
	}
	
	def dispatch Sequence ownerFunction(Method it){
		return null
	}
	
	
	def dispatch Method ownerMethod(EObject it){
		if (eContainer === null) {
			return null	
		} else {
			return eContainer.ownerMethod
		}
	}
	
	def dispatch Method ownerMethod(ExecutionNode it){
		return null
	}
	
	def dispatch Method ownerMethod(ExecutionScope it){
		return null
	}
	
	def dispatch Method ownerMethod(Method it){
		return it
	}
	
	
	/** Checks if a step is reachable.   */
	def boolean isReachable(Step it) {
		if ( it === null ) return false
		
		for( c : it.caller ) {
			if (c.ownerMethod !== null) return true
			val f = c.ownerFunction
			if (f !== null ) {
				if (f.eContainer instanceof ExecutionFlow ) return true
				if (f.reachable) return true	
			}
		}
		
		return false
	}
	
	def List<Sequence> reachable(List<Sequence> it) {
		filter( s | s.reachable ).toList
	}
	
	
	/**
	 * Checks if a step is called or not.
	 */
	def isCalled(Step it) { it !== null && caller.size > 0 }
	
	def List<Step> enterSequenceFunctions(ExecutionFlow it) {
		val funcs = new ArrayList<Step>()
		funcs.addAll(enterSequences.reachable) 
		states.forEach( s | funcs += s.enterSequences.reachable )
		regions.forEach( s | {
			funcs += s.enterSequences.reachable
			if (s.deepEnterSequence.reachable) funcs += s.deepEnterSequence
			if (s.shallowEnterSequence.reachable) funcs += s.shallowEnterSequence
		})
		return funcs
	}
	 
	def List<Step> exitSequenceFunctions(ExecutionFlow it) {
		val funcs = new ArrayList<Step>()
		if (exitSequence.reachable) funcs.add(exitSequence) 
		states.forEach( s | if (s.exitSequence.reachable) funcs += s.exitSequence )
		regions.forEach( s | if (s.exitSequence.reachable
		) funcs += s.exitSequence )
		return funcs
	}
	 
	def List<Method> reactMethods(ExecutionFlow it) {
		new ArrayList<Method>() => [ l | 
			l.add(it.reactMethod()) 
			l.addAll(it.states.map( s | s.reactMethod))
			l.removeIf( m | m === null)
		] 	
	}
	
	def Method reactMethod(ExecutionNode it) {
		features.filter( typeof(Method) ).filter( m | m.name == "react").head
	}
	
	
	def methods(ExecutionFlow it) {
		features.filter(Method).filter( m | m !== reactMethod)
	}
	
	
		
	def List<Step> reactFunctions(ExecutionFlow it) {
		val funcs = new ArrayList<Step>()
		if (reactSequence.reachable) funcs.add(reactSequence) 
		states.forEach( s | if (s.reactSequence.reachable) funcs += s.reactSequence )
		nodes.forEach( s | if (s.reactSequence.reachable) funcs += s.reactSequence )
		return funcs
	}
	
	
	
	def dispatch Reaction reaction(Check it) { eContainer as Reaction }
	def dispatch Reaction reaction(EObject it) { eContainer?.reaction }
	def dispatch Reaction reaction(Reaction it) { it }

	def isEntryAction(Step it) { eContainer.isEntryAction(it) }
	def dispatch isEntryAction(ExecutionFlow it, Step s) { entryAction == s }
	def dispatch isEntryAction(ExecutionState it, Step s) { entryAction == s }
	def dispatch isEntryAction(EObject it, Step s) { false }
	
	def isExitAction(Step it) { eContainer.isExitAction(it) }
	def dispatch isExitAction(ExecutionFlow it, Step s) { exitAction == s }
	def dispatch isExitAction(ExecutionState it, Step s) { exitAction == s }
	def dispatch isExitAction(EObject it, Step s) { false }
	
	def isEffect(Step it) { eContainer.isEffect(it) }
	def dispatch isEffect(Reaction it, Step s) { effect == s }
	def dispatch isEffect(EObject it, Step s) { false }
	
	def isEnterSequence(Step it) { eContainer.isEnterSequence(it) }
	def dispatch isEnterSequence(ExecutionScope it, Step s) { enterSequences.contains(s) }
	def dispatch isEnterSequence(EObject it, Step s) { false }
	
	def isDeepEnterSequence(Step it) { eContainer.isDeepEnterSequence(it) }
	def dispatch isDeepEnterSequence(ExecutionRegion it, Step s) { deepEnterSequence == s }
	def dispatch isDeepEnterSequence(EObject it, Step s) { false }
	
	def isShallowEnterSequence(Step it) { eContainer.isShallowEnterSequence(it) }
	def dispatch isShallowEnterSequence(ExecutionRegion it, Step s) { shallowEnterSequence == s }
	def dispatch isShallowEnterSequence(EObject it, Step s) { false }

	def isExitSequence(Step it) { eContainer.isExitSequence(it) }
	def dispatch isExitSequence(ExecutionScope it, Step s) { exitSequence == s }
	def dispatch isExitSequence(EObject it, Step s) { false }
	
	def isReactSequence(Step it) { eContainer.isReactSequence(it) }
	def dispatch isReactSequence(ExecutionNode it, Step s) { reactSequence == s }
	def dispatch isReactSequence(EObject it, Step s) { false }
	
	def isCheckFunction(Step it) { it instanceof Check }

	/**
	 * Returns the default step that is the step without name or the name 'default'.
	 */
	def Sequence defaultSequence(List<Sequence>steps) {
		steps.filter(s | s.name === null || s.name.trim == "" ||  s.name.trim == "default" ).head
	}
	
	/**
	 * Tries to find the next super ExecutionScope from the eContainer hierarchy.
	 * 
	 * @return
	 * 		The super ExecutionScope or null
	 */
	def ExecutionScope parentExecutionScope(EObject it) {
		eContainerOfType(ExecutionScope)
	}
	
	/**
	 * Calculates the depth of a step within the ExecutionScope hierarchy. If the
	 * Step is directly below the ExecutionFlow it has a depth of 0.
	 * 
	 * @return
	 * 		The depth of the element
	 */
	def int getScopeDepth(Step it) {
		return parentExecutionScope.scopeDepth
	}
	
	/**
	 * Calculates the depth of a ExecutionScope.
	 * The ExecutionFlow has as root element a depth of 0.
	 * 
	 * * @return
	 * 		The depth of the element
	 */
	def int getScopeDepth(ExecutionScope it) {
		var scopeDepth = 0

		if (it instanceof ExecutionFlow) {
			return 0
		} else {
			scopeDepth = superScope.scopeDepth + 1
		}
		return scopeDepth
	}
	

	/**
	 * returns all functions of an ExecutionFlow.
	 */
	def List<Step> getAllFunctions(ExecutionFlow it) {
		var functions = new ArrayList<Step>
		functions.addAll(checkFunctions)
		functions.addAll(effectFunctions)
		functions.addAll(entryActionFunctions)
		functions.addAll(exitActionFunctions)
		functions.addAll(enterSequenceFunctions)
		functions.addAll(exitSequenceFunctions)
		functions.addAll(reactFunctions)
		return functions
	}
	
	
	def derivedComplexTypes(ExecutionFlow flow) {
		
		val types = new LinkedList<ComplexType> 
		flow.collectDerivedComplexTypes(types)
		return types
	}
	
	
	protected def LinkedList<ComplexType> collectDerivedComplexTypes(ComplexType it, LinkedList<ComplexType> types) {
		it.referencedComplexTypes.reverseView.forEach[ t | 
			if (!types.contains(t)) {
				types.push(t)
				t.collectDerivedComplexTypes(types)
			}
		]
		
		return types		
	} 
	
	def referencedComplexTypes(ComplexType it) {
		it	.features
			.filter(Property)
			.filter[typeSpecifier.type instanceof ComplexType]
			.map[typeSpecifier.type as ComplexType]
			.toList
	}
	
}