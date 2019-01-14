
#include "gtest/gtest.h"
#include "PriorityValues.h"

#include "sc_timer_service.h"

static PriorityValues statechart;


class PriorityValuesTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void transitionPriority();
	void regionPriority();
	void setTimer(PriorityValues* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(PriorityValues* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static PriorityValuesTest * tc;


void PriorityValuesTest::SetUp()
{
	priorityValues_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &priorityValues_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void PriorityValuesTest::transitionPriority()
{
	priorityValues_enter(&statechart);
	EXPECT_TRUE(priorityValues_isStateActive(&statechart, PriorityValues_main_region_A));
	priorityValuesIface_raise_event1(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(priorityValues_isStateActive(&statechart, PriorityValues_main_region_C));
}
void PriorityValuesTest::regionPriority()
{
	priorityValues_enter(&statechart);
	EXPECT_TRUE(priorityValues_isStateActive(&statechart, PriorityValues_someRegion_A));
	priorityValuesIface_raise_event2(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(priorityValues_isStateActive(&statechart, PriorityValues_someRegion_B));
	EXPECT_TRUE(!priorityValues_isStateActive(&statechart, PriorityValues_main_region_E));
}

void PriorityValuesTest::setTimer(PriorityValues* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void PriorityValuesTest::unsetTimer(PriorityValues* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(PriorityValuesTest, transitionPriority) {
	transitionPriority();
}
TEST_F(PriorityValuesTest, regionPriority) {
	regionPriority();
}


