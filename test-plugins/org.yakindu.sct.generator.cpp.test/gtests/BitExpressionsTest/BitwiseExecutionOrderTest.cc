#include <string>
#include "gtest/gtest.h"
#include "BitwiseExecutionOrder.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

BitwiseExecutionOrder* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class BitwiseExecutionOrderTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new BitwiseExecutionOrder();
		statechart->init();
		runner = new SctUnitRunner(
			statechart,
			false,
			200
		);
	}
	virtual void TearDown() {
		delete statechart;
		delete runner;
	}
};


TEST_F(BitwiseExecutionOrderTest, test) {
	
	statechart->enter();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isFinal());
	
	statechart->exit();
	
	
}

}

