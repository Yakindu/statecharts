/* Generated by YAKINDU Statechart Tools code generator. */
#include <string>
#include "gtest/gtest.h"
#include "LogicalAnd.h"
#include "sc_types.h"
static LogicalAnd* statechart;


class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new LogicalAnd();
		statechart->init();
	}
	virtual void TearDown() {
		delete statechart;
	}
};


TEST_F(StatemachineTest, operandEvaluationOrder) {
	
	statechart->enter();
	
	statechart->runCycle();
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_x()== 4l && statechart->getDefaultSCI()->get_b()) << "logical and expression was executed in wrong order..." ;
	
}
