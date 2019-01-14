
#include "gtest/gtest.h"
#include "BooleanExpressions.h"

#include "sc_timer_service.h"

static BooleanExpressions statechart;


class BooleanExpressionsTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void booleanExpressions();
	void setTimer(BooleanExpressions* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(BooleanExpressions* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static BooleanExpressionsTest * tc;


void BooleanExpressionsTest::SetUp()
{
	booleanExpressions_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &booleanExpressions_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void BooleanExpressionsTest::booleanExpressions()
{
	booleanExpressions_enter(&statechart);
	EXPECT_TRUE(booleanExpressions_isStateActive(&statechart, BooleanExpressions_main_region_StateA));
	EXPECT_TRUE(booleanExpressionsIface_get_myBool1(&statechart)== true);
	EXPECT_TRUE(booleanExpressionsIface_get_myBool2(&statechart)== false);
	booleanExpressionsIface_raise_e1(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(booleanExpressions_isStateActive(&statechart, BooleanExpressions_main_region_StateB));
	EXPECT_TRUE(booleanExpressionsIface_get_and(&statechart)== false);
	EXPECT_TRUE(booleanExpressionsIface_get_or(&statechart)== true);
	EXPECT_TRUE(booleanExpressionsIface_get_not(&statechart)== false);
	EXPECT_TRUE(booleanExpressionsIface_get_equal(&statechart)== false);
	EXPECT_TRUE(booleanExpressionsIface_get_notequal(&statechart)== true);
}

void BooleanExpressionsTest::setTimer(BooleanExpressions* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void BooleanExpressionsTest::unsetTimer(BooleanExpressions* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(BooleanExpressionsTest, booleanExpressions) {
	booleanExpressions();
}


