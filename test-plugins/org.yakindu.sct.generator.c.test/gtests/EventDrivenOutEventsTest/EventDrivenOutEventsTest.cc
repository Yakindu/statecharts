
#include "gtest/gtest.h"
#include "EventDrivenOutEvents.h"

#include "sc_timer_service.h"

static EventDrivenOutEvents statechart;


class EventDrivenOutEventsTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void test();
	void setTimer(EventDrivenOutEvents* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(EventDrivenOutEvents* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static EventDrivenOutEventsTest * tc;


void EventDrivenOutEventsTest::SetUp()
{
	eventDrivenOutEvents_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &eventDrivenOutEvents_runCycle,
		true,
		200,
		&statechart
	);
	
	
	tc = this;
}
void EventDrivenOutEventsTest::test()
{
	eventDrivenOutEvents_enter(&statechart);
	EXPECT_TRUE(eventDrivenOutEvents_isStateActive(&statechart, EventDrivenOutEvents_main_region_StateA));
	EXPECT_TRUE(eventDrivenOutEvents_isStateActive(&statechart, EventDrivenOutEvents_second_region_StateC));
	eventDrivenOutEventsIface_raise_e1(&statechart);
	EXPECT_TRUE(eventDrivenOutEvents_isStateActive(&statechart, EventDrivenOutEvents_main_region_StateB));
	EXPECT_TRUE(eventDrivenOutEvents_isStateActive(&statechart, EventDrivenOutEvents_second_region_StateD));
	eventDrivenOutEvents_exit(&statechart);
}

void EventDrivenOutEventsTest::setTimer(EventDrivenOutEvents* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void EventDrivenOutEventsTest::unsetTimer(EventDrivenOutEvents* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(EventDrivenOutEventsTest, test) {
	test();
}


