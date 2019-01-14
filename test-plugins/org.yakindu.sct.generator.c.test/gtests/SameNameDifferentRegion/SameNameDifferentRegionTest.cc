
#include "gtest/gtest.h"
#include "SameNameDifferentRegion.h"

#include "sc_timer_service.h"

static SameNameDifferentRegion statechart;


class SameNameDifferentRegionTest : public ::testing::Test
{
public:
	/* All operations from the SCTUnit test class. */
	void sameNameDifferenRegionTest();
	void setTimer(SameNameDifferentRegion* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic);
	void unsetTimer(SameNameDifferentRegion* handle, const sc_eventid evid);
protected:
	sc_unit_timer_service_t timer_service;
	virtual void SetUp();
};

static SameNameDifferentRegionTest * tc;


void SameNameDifferentRegionTest::SetUp()
{
	sameNameDifferentRegion_init(&statechart);
	sc_timer_service_init(
		&timer_service,
		0,
		(sc_run_cycle_fp) &sameNameDifferentRegion_runCycle,
		false,
		200,
		&statechart
	);
	
	
	tc = this;
}
void SameNameDifferentRegionTest::sameNameDifferenRegionTest()
{
	sameNameDifferentRegion_enter(&statechart);
	EXPECT_TRUE(sameNameDifferentRegion_isStateActive(&statechart, SameNameDifferentRegion_main_region_StateA));
	sameNameDifferentRegionIface_raise_e1(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(sameNameDifferentRegion_isStateActive(&statechart, SameNameDifferentRegion_main_region_StateB));
	EXPECT_TRUE(sameNameDifferentRegion_isStateActive(&statechart, SameNameDifferentRegion_main_region_StateB_r1_StateA));
	sameNameDifferentRegionIface_raise_e1(&statechart);
	sc_timer_service_proceed_cycles(&timer_service, 1);
	EXPECT_TRUE(sameNameDifferentRegion_isStateActive(&statechart, SameNameDifferentRegion_main_region_StateB));
	EXPECT_TRUE(sameNameDifferentRegion_isStateActive(&statechart, SameNameDifferentRegion_main_region_StateB_r1_StateB));
}

void SameNameDifferentRegionTest::setTimer(SameNameDifferentRegion* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&(tc->timer_service), timer);
}

void SameNameDifferentRegionTest::unsetTimer(SameNameDifferentRegion* handle, const sc_eventid evid){
	delete_task(&(tc->timer_service), find_time_event(&timer_service, evid));
}

TEST_F(SameNameDifferentRegionTest, sameNameDifferenRegionTest) {
	sameNameDifferenRegionTest();
}


