
#include "gtest/gtest.h"
#include "EventDrivenTriggeredByTimeEvent.h"

#include "EventDrivenTriggeredByTimeEventRequired.h"
#include "sc_timer_service.h"

static EventDrivenTriggeredByTimeEvent statechart;


class EventDrivenTriggeredByTimeEventTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void timeEventTriggersRunCycle();
	void setTimer(EventDrivenTriggeredByTimeEvent* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(EventDrivenTriggeredByTimeEvent* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static EventDrivenTriggeredByTimeEventTest * tc;


void EventDrivenTriggeredByTimeEventTest::SetUp()
{
	eventDrivenTriggeredByTimeEvent_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		(sc_raise_time_event_fp) &eventDrivenTriggeredByTimeEvent_raiseTimeEvent,
		(sc_run_cycle_fp) &eventDrivenTriggeredByTimeEvent_runCycle,
		true,
		200,
		&statechart
	);
	
	
	tc = this;
}
void EventDrivenTriggeredByTimeEventTest::timeEventTriggersRunCycle()
{
	eventDrivenTriggeredByTimeEvent_enter(&statechart);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEvent_isStateActive(&statechart, EventDrivenTriggeredByTimeEvent_EventDrivenTriggeredByTimeEvent_r_A));
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_x(&statechart)== 0);
	sc_timer_service_proceed_time(&timer_service, 999);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEvent_isStateActive(&statechart, EventDrivenTriggeredByTimeEvent_EventDrivenTriggeredByTimeEvent_r_A));
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_x(&statechart)== 0);
	sc_timer_service_proceed_time(&timer_service, 1);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEvent_isStateActive(&statechart, EventDrivenTriggeredByTimeEvent_EventDrivenTriggeredByTimeEvent_r_B));
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_x(&statechart)== 0);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_transition_count(&statechart)== 1);
	sc_timer_service_proceed_time(&timer_service, 1000);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEvent_isStateActive(&statechart, EventDrivenTriggeredByTimeEvent_EventDrivenTriggeredByTimeEvent_r_A));
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_x(&statechart)== 0);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_transition_count(&statechart)== 2);
	sc_timer_service_proceed_time(&timer_service, 999000);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEvent_isStateActive(&statechart, EventDrivenTriggeredByTimeEvent_EventDrivenTriggeredByTimeEvent_r_B));
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_x(&statechart)== 0);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_transition_count(&statechart)== 1001);
	sc_timer_service_proceed_time(&timer_service, 999000);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEvent_isStateActive(&statechart, EventDrivenTriggeredByTimeEvent_EventDrivenTriggeredByTimeEvent_r_A));
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_x(&statechart)== 0);
	EXPECT_TRUE(eventDrivenTriggeredByTimeEventIface_get_transition_count(&statechart)== 2000);
	eventDrivenTriggeredByTimeEvent_exit(&statechart);
}

void EventDrivenTriggeredByTimeEventTest::setTimer(EventDrivenTriggeredByTimeEvent* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void EventDrivenTriggeredByTimeEventTest::unsetTimer(EventDrivenTriggeredByTimeEvent* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(EventDrivenTriggeredByTimeEventTest, timeEventTriggersRunCycle) {
	timeEventTriggersRunCycle();
}

void eventDrivenTriggeredByTimeEvent_setTimer(EventDrivenTriggeredByTimeEvent* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	tc->setTimer(statechart, evid, time_ms, periodic);
}

void eventDrivenTriggeredByTimeEvent_unsetTimer(EventDrivenTriggeredByTimeEvent* handle, const sc_eventid evid){
	tc->unsetTimer(handle, evid);
}

