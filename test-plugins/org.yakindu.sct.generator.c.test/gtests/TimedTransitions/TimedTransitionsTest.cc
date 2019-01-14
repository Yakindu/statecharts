
#include "gtest/gtest.h"
#include "TimedTransitions.h"

#include "TimedTransitionsRequired.h"
#include "sc_timer_service.h"

static TimedTransitions statechart;


class TimedTransitionsTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void Timer01();
	void setTimer(TimedTransitions* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(TimedTransitions* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static TimedTransitionsTest * tc;


void TimedTransitionsTest::SetUp()
{
	timedTransitions_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		(sc_raise_time_event_fp) &timedTransitions_raiseTimeEvent,
		(sc_run_cycle_fp) &timedTransitions_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void TimedTransitionsTest::Timer01()
{
	timedTransitions_enter(&statechart);
	EXPECT_TRUE(timedTransitions_isStateActive(&statechart, TimedTransitions_main_region_Start));
	sc_timer_service_proceed_time(&timer_service, 2030);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(timedTransitions_isStateActive(&statechart, TimedTransitions_main_region_End));
}

void TimedTransitionsTest::setTimer(TimedTransitions* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void TimedTransitionsTest::unsetTimer(TimedTransitions* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(TimedTransitionsTest, Timer01) {
	Timer01();
}

void timedTransitions_setTimer(TimedTransitions* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	tc->setTimer(statechart, evid, time_ms, periodic);
}

void timedTransitions_unsetTimer(TimedTransitions* handle, const sc_eventid evid){
	tc->unsetTimer(handle, evid);
}

