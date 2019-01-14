
#include "gtest/gtest.h"
#include "RaiseEvent.h"

#include "sc_timer_service.h"

static RaiseEvent statechart;


class RaiseEventTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void raiseEvent();
	void setTimer(RaiseEvent* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(RaiseEvent* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static RaiseEventTest * tc;


void RaiseEventTest::SetUp()
{
	raiseEvent_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &raiseEvent_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void RaiseEventTest::raiseEvent()
{
	raiseEvent_enter(&statechart);
	EXPECT_TRUE(raiseEvent_isStateActive(&statechart, RaiseEvent_second_region_SateA));
	EXPECT_TRUE(raiseEvent_isStateActive(&statechart, RaiseEvent_main_region_StateA));
	raiseEventIface_raise_e2(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(raiseEvent_isStateActive(&statechart, RaiseEvent_second_region_StateB));
	EXPECT_TRUE(raiseEvent_isStateActive(&statechart, RaiseEvent_main_region_StateB));
}

void RaiseEventTest::setTimer(RaiseEvent* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void RaiseEventTest::unsetTimer(RaiseEvent* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(RaiseEventTest, raiseEvent) {
	raiseEvent();
}


