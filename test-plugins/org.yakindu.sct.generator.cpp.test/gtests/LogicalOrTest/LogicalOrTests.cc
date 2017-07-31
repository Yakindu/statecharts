/* Generated by YAKINDU Statechart Tools code generator. */
#include <string>
#include "gtest/gtest.h"
#include "LogicalOr.h"
#include "sc_types.h"
static LogicalOr* statechart;


class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new LogicalOr();
		statechart->init();
	}
	virtual void TearDown() {
		delete statechart;
	}
};


TEST_F(StatemachineTest, operandEvaluationOrder) {
	
	statechart->enter();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_x()== 4l && statechart->getDefaultSCI()->get_b()) << "logical or expression was executed in wrong order..." ;
	
}
