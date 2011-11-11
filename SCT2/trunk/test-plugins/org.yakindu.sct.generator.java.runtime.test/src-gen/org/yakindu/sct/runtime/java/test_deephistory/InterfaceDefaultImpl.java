/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.runtime.java.test_deephistory;

import org.yakindu.sct.runtime.java.EventNotification;
import org.yakindu.sct.runtime.java.Event;

public class InterfaceDefaultImpl implements InterfaceDefault {
	
	private final Event<Void> EventEvent1 = new Event<Void>("event1", null); 
	private final Event<Void> EventEvent2 = new Event<Void>("event2", null); 
	private final Event<Void> EventEvent3 = new Event<Void>("event3", null); 
	private final Event<Void> EventEvent4 = new Event<Void>("event4", null); 
	private final Event<Void> EventEvent5 = new Event<Void>("event5", null); 
	private final Event<Void> EventEvent6 = new Event<Void>("event6", null); 
	private final Event<Void> EventEvent7 = new Event<Void>("event7", null); 
	private final Event<Void> EventEvent8 = new Event<Void>("event8", null); 
	private final Event<Void> EventEvent9 = new Event<Void>("event9", null); 
	
	private Test_DeepHistoryAbstractBaseStatemachine statemachine; 
	
	public InterfaceDefaultImpl(Test_DeepHistoryAbstractBaseStatemachine statemachine) {
		this.statemachine = statemachine;
	}
	
	public void raiseEvent1() {
		statemachine.getOccuredEvents().add(EventEvent1);
	}
	
	
	public Event<Void> getEventEvent1() {
		return EventEvent1;
	}
	
	public void raiseEvent2() {
		statemachine.getOccuredEvents().add(EventEvent2);
	}
	
	
	public Event<Void> getEventEvent2() {
		return EventEvent2;
	}
	
	public void raiseEvent3() {
		statemachine.getOccuredEvents().add(EventEvent3);
	}
	
	
	public Event<Void> getEventEvent3() {
		return EventEvent3;
	}
	
	public void raiseEvent4() {
		statemachine.getOccuredEvents().add(EventEvent4);
	}
	
	
	public Event<Void> getEventEvent4() {
		return EventEvent4;
	}
	
	public void raiseEvent5() {
		statemachine.getOccuredEvents().add(EventEvent5);
	}
	
	
	public Event<Void> getEventEvent5() {
		return EventEvent5;
	}
	
	public void raiseEvent6() {
		statemachine.getOccuredEvents().add(EventEvent6);
	}
	
	
	public Event<Void> getEventEvent6() {
		return EventEvent6;
	}
	
	public void raiseEvent7() {
		statemachine.getOccuredEvents().add(EventEvent7);
	}
	
	
	public Event<Void> getEventEvent7() {
		return EventEvent7;
	}
	
	public void raiseEvent8() {
		statemachine.getOccuredEvents().add(EventEvent8);
	}
	
	
	public Event<Void> getEventEvent8() {
		return EventEvent8;
	}
	
	public void raiseEvent9() {
		statemachine.getOccuredEvents().add(EventEvent9);
	}
	
	
	public Event<Void> getEventEvent9() {
		return EventEvent9;
	}
	
	
}
