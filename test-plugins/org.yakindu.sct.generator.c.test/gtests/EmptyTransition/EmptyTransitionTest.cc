
#include "gtest/gtest.h"
#include "EmptyTransition.h"

#include "sc_timer_service.h"

static EmptyTransition statechart;


class EmptyTransitionTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void emptyTransitionTest();
	void setTimer(EmptyTransition* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(EmptyTransition* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static EmptyTransitionTest * tc;


void EmptyTransitionTest::SetUp()
{
	emptyTransition_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &emptyTransition_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void EmptyTransitionTest::emptyTransitionTest()
{
	emptyTransition_enter(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(!emptyTransition_isStateActive(&statechart, EmptyTransition_main_region_B));
	EXPECT_TRUE(emptyTransition_isStateActive(&statechart, EmptyTransition_main_region_A));
}

void EmptyTransitionTest::setTimer(EmptyTransition* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void EmptyTransitionTest::unsetTimer(EmptyTransition* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(EmptyTransitionTest, EmptyTransitionTest) {
	emptyTransitionTest();
}


