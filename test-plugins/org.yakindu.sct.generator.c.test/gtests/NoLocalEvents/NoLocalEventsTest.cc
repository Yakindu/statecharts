
#include "gtest/gtest.h"
#include "NoLocalEvents.h"

#include "sc_timer_service.h"

static NoLocalEvents statechart;


class NoLocalEventsTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void test();
	void setTimer(NoLocalEvents* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(NoLocalEvents* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static NoLocalEventsTest * tc;


void NoLocalEventsTest::SetUp()
{
	noLocalEvents_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &noLocalEvents_runCycle,
		true,
		200,
		&statechart
	);
	
	
	tc = this;
}
void NoLocalEventsTest::test()
{
	noLocalEvents_enter(&statechart);
	EXPECT_TRUE(noLocalEvents_isStateActive(&statechart, NoLocalEvents_main_region_StateA));
	noLocalEventsIface_raise_e(&statechart);
	EXPECT_TRUE(noLocalEvents_isStateActive(&statechart, NoLocalEvents_main_region_StateB));
	noLocalEventsIface_raise_e(&statechart);
	EXPECT_TRUE(noLocalEvents_isStateActive(&statechart, NoLocalEvents_main_region_StateA));
	EXPECT_TRUE((noLocalEventsIface_get_x(&statechart)== 0));
	noLocalEventsIface_raise_i(&statechart, 42);
	EXPECT_TRUE(noLocalEvents_isStateActive(&statechart, NoLocalEvents_main_region_StateB));
	EXPECT_TRUE((noLocalEventsIface_get_x(&statechart)== 42));
	noLocalEvents_exit(&statechart);
}

void NoLocalEventsTest::setTimer(NoLocalEvents* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void NoLocalEventsTest::unsetTimer(NoLocalEvents* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(NoLocalEventsTest, test) {
	test();
}


