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
#include "gtest/gtest.h"
#include "AssignmentAsExpression.h"

static AssignmentAsExpression statechart;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		assignmentAsExpression_init(&statechart);
	}
};


TEST_F(StatemachineTest, simpleAssignment) {					
	assignmentAsExpression_enter(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_Add));
	EXPECT_TRUE(assignmentAsExpressionIface_get_b(&statechart)== 5l);
	EXPECT_TRUE(assignmentAsExpressionIface_get_a(&statechart)== 9l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_Subtract));
	EXPECT_TRUE(assignmentAsExpressionIface_get_d(&statechart)== 6l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_Multiply));
	EXPECT_TRUE(assignmentAsExpressionIface_get_e(&statechart)== 15l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_Divide));
	EXPECT_TRUE(assignmentAsExpressionIface_get_g(&statechart)== 1l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_Modulo));
	EXPECT_TRUE(assignmentAsExpressionIface_get_i(&statechart)== 1l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_Shift));
	EXPECT_TRUE(assignmentAsExpressionIface_get_j(&statechart)== 16l);
	EXPECT_TRUE(assignmentAsExpressionIface_get_k(&statechart)== 4l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_boolean_And));
	EXPECT_TRUE(assignmentAsExpressionIface_get_l(&statechart)== 1l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_boolean_Or));
	EXPECT_TRUE(assignmentAsExpressionIface_get_p(&statechart)== 15l);
	assignmentAsExpression_runCycle(&statechart);
	EXPECT_TRUE(assignmentAsExpression_isStateActive(&statechart, AssignmentAsExpression_main_region_boolean_Xor));
	EXPECT_TRUE(assignmentAsExpressionIface_get_u(&statechart)== 12l);
	assignmentAsExpression_exit(&statechart);
}


