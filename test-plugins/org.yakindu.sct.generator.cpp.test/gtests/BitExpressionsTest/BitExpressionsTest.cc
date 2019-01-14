#include <string>
#include "gtest/gtest.h"
#include "BitExpressions.h"
#include "sc_runner.h"
#include "sc_types.h"

namespace  {

BitExpressions* statechart;



//! The timers are managed by a timer service. */
static SctUnitRunner * runner;

class BitExpressionsTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechart = new BitExpressions();
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


TEST_F(BitExpressionsTest, BitExpressions) {
	
	statechart->enter();
	
	EXPECT_TRUE(statechart->isStateActive(BitExpressions::main_region_StateA));
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_myBit1()== 5);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_myBit2()== 7);
	
	statechart->getDefaultSCI()->raise_e1();
	
	runner->proceed_cycles(1);
	
	EXPECT_TRUE(statechart->isStateActive(BitExpressions::main_region_StateB));
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_leftBitshift()== 10);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_rightBitshift()== 2);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_complementBitshift()== - 6 );
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_bitwiseAnd()== 5);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_bitwiseOr()== 7);
	
	EXPECT_TRUE(statechart->getDefaultSCI()->get_bitwiseXor()== 2);
	
	
}

}

