/* Generated by YAKINDU Statechart Tools code generator. */
#include "gtest/gtest.h"
#include "ConditionalExpressions.h"


static ConditionalExpressions statechart;


class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		conditionalExpressions_init(&statechart);
	}
};


TEST_F(StatemachineTest, ConditionalExpressionTest) {
	conditionalExpressions_enter(&statechart);
	EXPECT_TRUE(conditionalExpressions_isStateActive(&statechart, ConditionalExpressions_main_region_A));
	EXPECT_TRUE(conditionalExpressionsIface_get_condition(&statechart)== 1l);
	conditionalExpressionsIface_raise_e(&statechart);
	conditionalExpressions_runCycle(&statechart);
	EXPECT_TRUE(conditionalExpressions_isStateActive(&statechart, ConditionalExpressions_main_region_B));
	EXPECT_TRUE(conditionalExpressionsIface_get_condition(&statechart)== 2l);
}


