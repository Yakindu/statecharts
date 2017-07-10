/**
* Copyright (c) 2017 committers of YAKINDU and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     committers of YAKINDU - initial API and implementation
*/

package org.yakindu.sct.generator.java.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AlwaysOncycle.class,
	AssignmentAsExpression.class,
	BitExpressions.class,
	BooleanExpressions.class,
	CastExpressions.class,
	Choice.class,
	CKeywords.class,
	ConditionalExpression.class,
	ConstantsTests.class,
	ConstOnlyDefaultScope.class,
	ConstOnlyInternalScope.class,
	ConstOnlyNamedScope.class,
	Declarations.class,
	DeepEntry.class,
	DeepHistory.class,
	DynamicChoice.class,
	EmptyTransition.class,
	EnterState.class,
	EntryChoice.class,
	EntryExitSelfTransition.class,
	EntryReactionAction.class,
	ExitOnSelfTransition.class,
	ExitState.class,
	FeatureCalls.class,
	FinalState.class,
	Guard.class,
	GuardedEntry.class,
	GuardedExit.class,
	HistoryWithExitPoint.class,
	HistoryWithoutInitialStep.class,
	InEventLifeCycle.class,
	IntegerExpressions.class,
	InternalEventLifeCycle.class,
	LocalReactions.class,
	LogicalAndTests.class,
	LogicalOrTests.class,
	NamedInterfaceAccess.class,
	OperationsTest.class,
	OutEventLifeCycle.class,
	Parenthesis.class,
	PriorityValues.class,
	RaiseEvent.class,
	ReadOnlyVariable.class,
	SameNameDifferentRegion.class,
	ShallowHistory.class,
	ShallowHistoryWithDeepEntry.class,
	SimpleEvent.class,
	SimpleHierachy.class,
	StatechartActive.class,
	StatechartLocalReactions.class,
	StateIsActive.class,
	StaticChoice.class,
	STextKeywordsInStatesAndRegions.class,
	StringExpressions.class,
	SyncFork.class,
	SyncJoin.class,
	TransitionWithoutCondition.class,
	TriggerGuardExpressions.class,
	TriggerExpressionPrecedence.class,
	ValuedEvents.class,
	TypeAlias.class,
	StatemachineKeywords.class,
	TimedTransitions.class
	})
public class AllTests {
}
