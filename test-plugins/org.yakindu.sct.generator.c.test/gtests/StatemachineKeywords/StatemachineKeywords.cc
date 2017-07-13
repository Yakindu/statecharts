/**
* Copyright (c) 2017 committers of YAKINDU and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     committers of YAKINDU - initial API and implementation
*/
#include "gtest/gtest.h"
#include "StatechartKeywords.h"
#include "StatechartKeywordsRequired.h"
#include "sc_timer_service.h"

//! The timers are managed by a timer service. */
static sc_unit_timer_service_t timer_service;

static StatechartKeywords statechart;

class StatemachineTest : public ::testing::Test{
	protected:
	virtual void SetUp() {
		statechartKeywords_init(&statechart);
		sc_timer_service_init(
			&timer_service,
			(sc_raise_time_event_fp) &statechartKeywords_raiseTimeEvent,
			(sc_run_cycle_fp) &statechartKeywords_runCycle,
			false,
			200,
			&statechart
		);
	}
};


TEST_F(StatemachineTest, statemachineKeywords) {					
	statechartKeywords_enter(&statechart);
	EXPECT_TRUE(statechartKeywords_isStateActive(&statechart, StatechartKeywords_main_region_Timer));
}

void statechartKeywords_setTimer(StatechartKeywords* statechart, const sc_eventid evid, const sc_integer time_ms, const sc_boolean periodic){
	sc_timer_t timer;
	sc_timer_init(&timer, time_ms, periodic, evid);
	insert_timer(&timer_service, timer);
}

void statechartKeywords_unsetTimer(StatechartKeywords* handle, const sc_eventid evid){
	delete_task(&timer_service, find_time_event(&timer_service, evid));
}		

void statechartKeywordsIfaceIf_myOperation(const StatechartKeywords* statechart){
}
void statechartKeywordsInternal_myOperation(const StatechartKeywords* statechart){
}
