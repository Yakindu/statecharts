
#include "gtest/gtest.h"
#include "StatechartActive.h"

#include "sc_timer_service.h"

static StatechartActive statechart;


class StatechartActiveTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void inactiveBeforeEnter();
	void activeAfterEnter();
	void inactiveAfterExit();
	void activeAfterReenter();
	void setTimer(StatechartActive* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(StatechartActive* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static StatechartActiveTest * tc;


void StatechartActiveTest::SetUp()
{
	statechartActive_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &statechartActive_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void StatechartActiveTest::inactiveBeforeEnter()
{
	EXPECT_TRUE(!statechartActive_isActive(&statechart));
}
void StatechartActiveTest::activeAfterEnter()
{
	statechartActive_enter(&statechart);
	EXPECT_TRUE(statechartActive_isActive(&statechart));
}
void StatechartActiveTest::inactiveAfterExit()
{
	statechartActive_enter(&statechart);
	statechartActive_exit(&statechart);
	EXPECT_TRUE(!statechartActive_isActive(&statechart));
}
void StatechartActiveTest::activeAfterReenter()
{
	statechartActive_enter(&statechart);
	statechartActive_exit(&statechart);
	statechartActive_enter(&statechart);
	EXPECT_TRUE(statechartActive_isActive(&statechart));
}

void StatechartActiveTest::setTimer(StatechartActive* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void StatechartActiveTest::unsetTimer(StatechartActive* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(StatechartActiveTest, inactiveBeforeEnter) {
	inactiveBeforeEnter();
}
TEST_F(StatechartActiveTest, activeAfterEnter) {
	activeAfterEnter();
}
TEST_F(StatechartActiveTest, inactiveAfterExit) {
	inactiveAfterExit();
}
TEST_F(StatechartActiveTest, activeAfterReenter) {
	activeAfterReenter();
}


