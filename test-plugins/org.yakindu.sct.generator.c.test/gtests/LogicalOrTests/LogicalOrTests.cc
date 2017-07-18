/* Generated by YAKINDU Statechart Tools code generator. */
#include "gtest/gtest.h"
#include "LogicalOr.h"


static LogicalOr statechart;


class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		logicalOr_init(&statechart);
	}
};


TEST_F(StatemachineTest, operandEvaluationOrder) {
	logicalOr_enter(&statechart);
	logicalOr_runCycle(&statechart);
	EXPECT_TRUE(logicalOrIface_get_x(&statechart)== 4l && logicalOrIface_get_b(&statechart)) << "logical or expression was executed in wrong order..." ;
}


