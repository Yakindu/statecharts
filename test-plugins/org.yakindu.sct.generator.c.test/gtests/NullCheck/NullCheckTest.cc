/* Generated by YAKINDU Statechart Tools code generator. */
#include "gtest/gtest.h"
#include "NullCheck.h"


static NullCheck statechart;


class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		nullCheck_init(&statechart);
	}
};


TEST_F(StatemachineTest, SimpleNullCheckTest) {
	nullCheck_enter(&statechart);
	EXPECT_TRUE(nullCheck_isStateActive(&statechart, NullCheck_main_region_A));
	nullCheck_runCycle(&statechart);
	EXPECT_TRUE(nullCheck_isStateActive(&statechart, NullCheck_main_region_B));
}


