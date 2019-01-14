
#include "gtest/gtest.h"
#include "Declarations.h"

#include "sc_timer_service.h"

static Declarations statechart;


class DeclarationsTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void declarationsTest();
	void setTimer(Declarations* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(Declarations* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static DeclarationsTest * tc;


void DeclarationsTest::SetUp()
{
	declarations_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &declarations_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void DeclarationsTest::declarationsTest()
{
	declarations_enter(&statechart);
}

void DeclarationsTest::setTimer(Declarations* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void DeclarationsTest::unsetTimer(Declarations* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(DeclarationsTest, declarationsTest) {
	declarationsTest();
}


