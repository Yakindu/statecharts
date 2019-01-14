
#include "gtest/gtest.h"
#include "DeepEntry.h"

#include "sc_timer_service.h"

static DeepEntry statechart;


class DeepEntryTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void enterToSubstate();
	void setTimer(DeepEntry* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(DeepEntry* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static DeepEntryTest * tc;


void DeepEntryTest::SetUp()
{
	deepEntry_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &deepEntry_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void DeepEntryTest::enterToSubstate()
{
	deepEntry_enter(&statechart);
	sc_integer vergleich = 4;
	EXPECT_TRUE(vergleich== 4);
	deepEntry_exit(&statechart);
}

void DeepEntryTest::setTimer(DeepEntry* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void DeepEntryTest::unsetTimer(DeepEntry* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(DeepEntryTest, enterToSubstate) {
	enterToSubstate();
}


