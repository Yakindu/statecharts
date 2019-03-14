package org.yakindu.sct.model.sexec.transformation.ng

import com.google.inject.Singleton
import javax.inject.Inject
import org.yakindu.base.expressions.expressions.ExpressionsFactory
import org.yakindu.base.types.ComplexType
import org.yakindu.base.types.Expression
import org.yakindu.base.types.typesystem.ITypeSystem
import org.yakindu.sct.model.sexec.ExecutionState
import org.yakindu.sct.model.sexec.Sequence
import org.yakindu.sct.model.sexec.extensions.SExecExtensions
import org.yakindu.sct.model.sexec.extensions.StateVectorExtensions
import org.yakindu.sct.model.sexec.transformation.ExpressionBuilder
import org.yakindu.sct.model.sexec.transformation.SexecElementMapping
import org.yakindu.sct.model.sexec.transformation.TypeBuilder
import org.yakindu.sct.model.sgraph.State
import org.yakindu.sct.model.sgraph.Statechart

@Singleton
class StatemachineMethods {

	extension ExpressionsFactory exprFactory = ExpressionsFactory.eINSTANCE
	
	@Inject extension SexecElementMapping  
	@Inject extension SExecExtensions  
	@Inject extension Sequence2Method
	@Inject extension TypeBuilder
	@Inject extension ExpressionBuilder
	@Inject extension StatemachineProperties
	@Inject extension StatemachinePublic
	@Inject extension StateVectorExtensions
	@Inject extension IStatemachine
	@Inject ITypeSystem ts
	

	def defineEnterMethod(ComplexType it, Statechart sc) {
		it.features += createEnterMethod => [
			body = createBlockExpression => [
				expressions += createCallToSequenceMethod(sc.create.enterSequences.defaultSequence)
			]
		]
	}
	
	def defineExitMethod(ComplexType it, Statechart sc) {
		it.features += createExitMethod => [
			body = createBlockExpression => [
				expressions += createCallToSequenceMethod(sc.create.exitSequence)
			]
		]
	}
	
	def defineInitMethod(ComplexType it, Statechart sc) {
		it.features += createInitMethod => [
			body = createBlockExpression => [
				expressions += stateVectorInitialization(sc)
				if (sc.create.hasHistory) {
					expressions += historyStateVectorInitialization(sc)
				}
				// TODO: transform init sequence into expressions
//				expressions += createCallToSequenceMethod(sc.create.initSequence)
			]
		]
	}
	
	def stateVectorInitialization(Statechart sc) {
		val ef = sc.create
		val i = _variable("i", ITypeSystem.INTEGER, 0._int)
		_for(i, i._ref._smaller(ef.stateVector.size._int), i._ref._inc) => [
			it.body = _block(
				stateVector(sc)._ref._get(0._int)._assign(statesEnumeration(sc)._ref._fc(noState(sc)))
			)
		]
	}
	
	def historyStateVectorInitialization(Statechart sc) {
		val ef = sc.create
		val i = _variable("i", ITypeSystem.INTEGER, 0._int)
		_for(i, i._ref._smaller(ef.historyVector.size._int), i._ref._inc) => [
			it.body = _block(
				historyStateVector(sc)._ref._get(0._int)._assign(historyStateVector(sc)._ref._fc(noState(sc)))
			)
		]
	}
	
	def defineIsActiveMethod(ComplexType it, Statechart sc) {
		it.features += createIsActiveMethod => [
			body = createBlockExpression => [
				expressions += _return(isActiveCheck(sc))
			]
		]
	}
	
	protected def isActiveCheck(Statechart sc) {
		return (0..<sc.create.stateVector.size)
			.map[i | notEqualsNoState(sc, i) as Expression]
			.reduce[p1, p2 | p1._or(p2)]
	}
	
	protected def notEqualsNoState(Statechart sc, int index) {
		stateVector(sc)._ref._get(index._int)._notEquals(statesEnumeration(sc)._ref._fc(noState(sc)))
	}
	
	def defineIsFinalMethod(ComplexType it, Statechart sc) {
		it.features += createIsFinalMethod => [
			body = createBlockExpression => [
				expressions += _return(isFinalCheck(sc))
			]
		]
	}
	
	protected def isFinalCheck(Statechart sc) {
		val ef = sc.create
		val fsv = ef.finalStateImpactVector
		if (fsv.isCompletelyCovered) {
			(0..<fsv.size)
				.map[i | fsv.get(i)
					.map[fs | equalsState(sc, fs, i) as Expression]
					.reduce[p1, p2 | p1._or(p2)]
					._parenthesized as Expression]
				.reduce[p1, p2 | p1._and(p2)]
		} else {
			_false
		}
	}
	
	protected def equalsState(Statechart sc, ExecutionState fs, int index) {
		stateVector(sc)._ref._get(index._int)._equals(
			if (fs.stateVector.offset == index) {
				stateVector(sc)._ref._fc((fs.sourceElement as State).enumerator)
			} else {
				stateVector(sc)._ref._fc(noState(sc))
			}
		)
	}
	
	def defineRunCycleMethod(ComplexType it, Statechart sc) {
		val ef = sc.create
		it.features += createRunCycleMethod => [
			body = createBlockExpression => [
				expressions += 
				_for(nextStateIndex(sc)._ref._assign(0._int), nextStateIndex(sc)._ref._smaller(stateVector(sc)._ref._fc(arrayLength)), nextStateIndex(sc)._ref._inc) => [
					body = _block(_switch(stateVector(sc)._ref._get(nextStateIndex(sc)._ref), 
						ef.states.filter[isLeaf].filter[reactMethod!==null].map[state | 
							_case((state.sourceElement as State).enumerator._ref, state.reactMethod._call(_true))
						]
					))
					
				]
			]
		]
	}
	
	protected def arrayLength() {
		(ts.getType(ITypeSystem.ARRAY) as ComplexType).features.findFirst[name=="length"]
	}
	
	protected def createCallToSequenceMethod(Sequence seq) {
		createElementReferenceExpression => [
			operationCall = true
			reference = createMethodForSequence(seq)
		]
	}
	
}
