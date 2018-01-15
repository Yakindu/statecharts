/**
 * Copyright (c) 2012-2016 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.model.sgraph.validation;

import static org.yakindu.sct.model.sgraph.util.SGgraphUtil.areOrthogonal;
import static org.yakindu.sct.model.sgraph.util.SGgraphUtil.collectAncestors;
import static org.yakindu.sct.model.sgraph.util.SGgraphUtil.commonAncestor;
import static org.yakindu.sct.model.sgraph.util.SGgraphUtil.sources;
import static org.yakindu.sct.model.sgraph.util.SGgraphUtil.targets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.yakindu.base.base.BasePackage;
import org.yakindu.sct.model.sgraph.Choice;
import org.yakindu.sct.model.sgraph.CompositeElement;
import org.yakindu.sct.model.sgraph.Entry;
import org.yakindu.sct.model.sgraph.EntryKind;
import org.yakindu.sct.model.sgraph.Exit;
import org.yakindu.sct.model.sgraph.FinalState;
import org.yakindu.sct.model.sgraph.Region;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Synchronization;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.sgraph.Vertex;

import com.google.inject.Inject;

/**
 * This validator is intended to be used by a compositeValidator (See
 * {@link org.eclipse.xtext.validation.ComposedChecks}) of another language
 * specific validator. It does not register itself as an EValidator.
 * 
 * This validator checks for common graphical constraints for all kinds of state
 * charts.
 * 
 * @author terfloth
 * @author muelder
 * @author bohl - migrated to xtext infrastruture
 * @author schwertfeger
 * @author antony
 */
public class SGraphJavaValidator extends AbstractDeclarativeValidator {

	public static final String ISSUE_STATE_WITHOUT_NAME = "A state must have a name.";
	public static final String ISSUE_NODE_NOT_REACHABLE = "Node is not reachable.";
	public static final String ISSUE_FINAL_STATE_OUTGOING_TRANSITION = "A final state should have no outgoing transition.";
	public static final String ISSUE_STATE_WITHOUT_OUTGOING_TRANSITION = "A state should have at least one outgoing transition.";
	public static final String ISSUE_INITIAL_ENTRY_WITH_IN_TRANS = "Initial entry should have no incoming transition.";
	public static final String ISSUE_INITIAL_ENTRY_WITHOUT_OUT_TRANS = "Initial entry should have a single outgoing transition.";
	public static final String ISSUE_ENTRY_WITH_MULTIPLE_OUT_TRANS = "Entries must not have more than one outgoing transition.";
	public static final String ISSUE_ENTRY_WITH_TRIGGER = "Outgoing transitions from entry points can not have a trigger or guard.";
	public static final String ISSUE_EXIT_WITH_OUT_TRANS = "Exit node should have no outgoing transition.";
	public static final String ISSUE_EXIT_WITHOUT_IN_TRANS = "Exit node should have at least one incoming transition.";
	public static final String ISSUE_EXIT_ON_STATECHART = "Exit node in top level region not supported - use final states instead.";
	public static final String ISSUE_CHOICE_WITHOUT_OUTGOING_TRANSITION = "A choice must have at least one outgoing transition.";
	public static final String ISSUE_REGION_CANT_BE_ENTERED_USING_SHALLOW_HISTORY_NO_DEFAULT_ENTRY = "The region can't be entered using the shallow history. Add a default entry node.";
	public static final String ISSUE_REGION_CANT_BE_ENTERED_USING_SHALLOW_HISTORY_NON_CONNECTED_DEFAULT_ENTRY = "The region can't be entered using the shallow history. Add a transition from default entry to a state.";
	public static final String ISSUE_SUBMACHINE_UNRESOLVABLE = "Referenced substate machine '%s'does not exist!";
	public static final String ISSUE_SYNCHRONIZATION_TARGET_STATES_NOT_ORTHOGONAL = "The target states of a synchronization must be orthogonal!";
	public static final String ISSUE_SYNCHRONIZATION_TARGET_STATES_NOT_WITHIN_SAME_PARENTSTATE = "The target states of a synchronization have to be contained in the same parent state within different regions!";
	public static final String ISSUE_SYNCHRONIZATION_SOURCE_STATES_NOT_ORTHOGONAL = "The source states of a synchronization must be orthogonal!";
	public static final String ISSUE_SYNCHRONIZATION_SOURCE_STATES_NOT_WITHIN_SAME_PARENTSTATE = "The source states of a synchronization have to be contained in the same parent state within different regions!";
	public static final String ISSUE_SYNCHRONIZATION_TRANSITION_COUNT = "A synchronization should have at least two incoming or two outgoing transitions.";
	public static final String ISSUE_SYNCHRONIZATION_SOURCE_TARGET_STATES_PARENT_REGION = "A synchronization's source- and parent states last common ancestor has to be a region!";
	public static final String ISSUE_TRANSITION_ORTHOGONAL = "Source and target of a transition must not be located in orthogonal regions!";
	public static final String ISSUE_INITIAL_ENTRY_WITH_TRANSITION_TO_CONTAINER = "Outgoing transitions from entries can only target to sibling or inner states.";
	public static final String ISSUE_STATECHART_NAME_NO_IDENTIFIER = "%s is not a valid identifier!";

	
	@Check(CheckType.FAST)
	public void vertexNotReachable(final Vertex vertex) {
		if (!(vertex instanceof Entry)) {

			final Set<Object> stateScopeSet = new HashSet<Object>();
			for (EObject obj : EcoreUtil2.eAllContents(vertex)) {
				stateScopeSet.add(obj);
			}
			stateScopeSet.add(vertex);

			final List<Object> externalPredecessors = new ArrayList<Object>();

			DFS dfs = new DFS() {

				@Override
				public Iterator<Object> getElementLinks(Object element) {
					List<Object> elements = new ArrayList<Object>();

					if (element instanceof org.yakindu.sct.model.sgraph.State) {
						if (!stateScopeSet.contains(element)) {
							externalPredecessors.add(element);
						} else {
							elements.addAll(((org.yakindu.sct.model.sgraph.State) element).getRegions());
							elements.addAll(((org.yakindu.sct.model.sgraph.State) element).getIncomingTransitions());
						}

					} else if (element instanceof Region) {
						elements.addAll(((Region) element).getVertices());
					} else if (element instanceof Entry) {
						if (!stateScopeSet.contains(element)) {
							externalPredecessors.add(element);
						} else {
							elements.addAll(((Entry) element).getIncomingTransitions());
						}

					} else if (element instanceof Vertex) {
						elements.addAll(((Vertex) element).getIncomingTransitions());

					} else if (element instanceof Transition) {
						elements.add(((Transition) element).getSource());

					}

					return elements.iterator();
				}
			};

			dfs.perform(vertex);

			if (externalPredecessors.size() == 0) {
				error(ISSUE_NODE_NOT_REACHABLE, vertex, null, -1);
			}
		}
	}

	/**
	 * Calculates all predecessor states
	 */

	@Check(CheckType.FAST)
	public void finalStateWithOutgoingTransition(FinalState finalState) {
		if ((finalState.getOutgoingTransitions().size() > 0)) {
			warning(ISSUE_FINAL_STATE_OUTGOING_TRANSITION, finalState, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void nameIsNotEmpty(org.yakindu.sct.model.sgraph.State state) {
		if ((state.getName() == null || state.getName().trim().length() == 0) && !(state instanceof FinalState)) {
			error(ISSUE_STATE_WITHOUT_NAME, state, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void choiceWithoutOutgoingTransition(Choice choice) {
		// Choice without outgoing transition
		if (choice.getOutgoingTransitions().size() == 0) {
			error(ISSUE_CHOICE_WITHOUT_OUTGOING_TRANSITION, choice, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void disallowTrigger(Entry entry) {
		for (Transition transition : entry.getOutgoingTransitions()) {
			if (transition.getTrigger() != null) {
				error(ISSUE_ENTRY_WITH_TRIGGER, entry, null, -1);
			}
		}
	}

	@Check(CheckType.FAST)
	public void initialEntryWithoutIncomingTransitions(Entry entry) {
		if (entry.getIncomingTransitions().size() > 0 && entry.getKind().equals(EntryKind.INITIAL)) {
			warning(ISSUE_INITIAL_ENTRY_WITH_IN_TRANS, entry, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void initialEntryWithoutOutgoingTransition(Entry entry) {
		if (entry.getOutgoingTransitions().size() == 0 && ((Entry) entry).getKind().equals(EntryKind.INITIAL)) {
			warning(ISSUE_INITIAL_ENTRY_WITHOUT_OUT_TRANS, entry, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void initialEntryWithMultipleOutgoingTransition(Entry entry) {
		if (entry.getOutgoingTransitions().size() > 1) {
			error(ISSUE_ENTRY_WITH_MULTIPLE_OUT_TRANS, entry, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void exitWithoutIncomingTransition(Exit exit) {
		if (exit.getIncomingTransitions().size() == 0) {
			warning(ISSUE_EXIT_WITHOUT_IN_TRANS, exit, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void exitWithOutgoingTransition(Exit exit) {
		if (exit.getOutgoingTransitions().size() > 0) {
			error(ISSUE_EXIT_WITH_OUT_TRANS, exit, null, -1);
		}
	}

	/**
	 * Exit nodes in top level regions are not supported.
	 * 
	 * @param exit
	 */
	@Check(CheckType.FAST)
	public void exitOnStatechart(Exit exit) {
		if (exit.getParentRegion().getComposite() instanceof Statechart) {
			error(ISSUE_EXIT_ON_STATECHART, exit, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void synchronizationTransitionCount(Synchronization sync) {
		if (sync.getIncomingTransitions().size() + sync.getOutgoingTransitions().size()  < 3) {
			warning(ISSUE_SYNCHRONIZATION_TRANSITION_COUNT, sync, null, -1);
		}
	}

	@Check(CheckType.FAST)
	public void initialEntryWithTransitionToContainer(Transition t) {
		if (t.getSource() instanceof Entry && !isChildOrSibling(t.getSource(), t.getTarget())) {
			error(ISSUE_INITIAL_ENTRY_WITH_TRANSITION_TO_CONTAINER, t, null, -1);
		}
	}

	private boolean isChildOrSibling(Vertex source, Vertex target) {
		TreeIterator<EObject> iter = source.getParentRegion().eAllContents();
		while (iter.hasNext()) {
			if (target == iter.next()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if all composite states that are siblings of a shallow history can
	 * enter their regions.
	 * 
	 * @param e
	 */
	@Check(CheckType.FAST)
	public void regionCantBeEnteredUsingShallowHistory(Entry e) {

		if (e.getKind() == EntryKind.SHALLOW_HISTORY) {

			// get all regions off all sibling states
			List<Region> regions = new ArrayList<Region>();
			for (Vertex v : e.getParentRegion().getVertices()) {
				if (v instanceof org.yakindu.sct.model.sgraph.State) {
					org.yakindu.sct.model.sgraph.State state = (org.yakindu.sct.model.sgraph.State) v;
					regions.addAll(state.getRegions());
				}
			}

			// check each region
			for (Region r : regions) {

				// first determine if the region contains a default entry
				Entry defaultEntry = null;
				for (Vertex v : r.getVertices()) {
					if (v instanceof Entry) {
						String name = v.getName().trim().toLowerCase();
						if (name != null || "".equals(name) || "default".equals(name)) {
							defaultEntry = (Entry) v;
							break;
						}
					}
				}

				// now check error conditions
				if (defaultEntry == null) {
					error(ISSUE_REGION_CANT_BE_ENTERED_USING_SHALLOW_HISTORY_NO_DEFAULT_ENTRY, r, null, -1);
				} else if (defaultEntry.getOutgoingTransitions().size() != 1) {
					error(ISSUE_REGION_CANT_BE_ENTERED_USING_SHALLOW_HISTORY_NON_CONNECTED_DEFAULT_ENTRY, r, null, -1);
				}
			}

		}

	}

	@Check
	public void orthogonalTransition(Transition transition) {

		Vertex source = transition.getSource();
		Vertex target = transition.getTarget();

		if ((source instanceof Synchronization) || (target instanceof Synchronization))
			return; // ... the check does not apply.

		EObject commonAncestor = commonAncestor(source, target);

		if (commonAncestor instanceof CompositeElement) {

			error(ISSUE_TRANSITION_ORTHOGONAL, transition, null, -1);
		}
	}

	@Check
	public void orthogonalSourceStates(Synchronization sync) {

		List<Vertex> sourceVertices = sources(sync.getIncomingTransitions());

		if (!areOrthogonal(sourceVertices)) {
			error(ISSUE_SYNCHRONIZATION_SOURCE_STATES_NOT_ORTHOGONAL, sync, null, -1);
		}
	}

	@Check
	public void orthogonalTargetStates(Synchronization sync) {

		List<Vertex> sourceVertices = targets(sync.getOutgoingTransitions());

		if (!areOrthogonal(sourceVertices)) {
			error(ISSUE_SYNCHRONIZATION_TARGET_STATES_NOT_ORTHOGONAL, sync, null, -1);
		}
	}
	
	@Check
	public void orthogonalSynchronizedTransition(Synchronization sync) {

		List<Transition> incoming = sync.getIncomingTransitions();
		List<List<EObject>> inAncestorsList = new ArrayList<List<EObject>>();
		for (Transition trans : incoming) {
			inAncestorsList.add(collectAncestors(trans.getSource(), new ArrayList<EObject>()));
		}

		List<Transition> outgoing = sync.getOutgoingTransitions();
		List<List<EObject>> outAncestorsList = new ArrayList<List<EObject>>();
		for (Transition trans : outgoing) {
			outAncestorsList.add(collectAncestors(trans.getTarget(), new ArrayList<EObject>()));
		}

		Set<Transition> inOrthogonal = new HashSet<Transition>();
		Set<Transition> outOrthogonal = new HashSet<Transition>();
		
		if(incoming.size() == 0 || outgoing.size() == 0) {
			return;
		}

		for (int i = 0; i < incoming.size(); i++) {
			for (int j = 0; j < outgoing.size(); j++) {

				List<Vertex> states = new ArrayList<>(Arrays.asList(incoming.get(i).getSource(), outgoing.get(j).getTarget()));
				
				if (areOrthogonal(states)) {
					inOrthogonal.add(incoming.get(i));
					outOrthogonal.add(outgoing.get(j));
				}
			}
		}

		for (Transition trans : inOrthogonal) {
			error(ISSUE_SYNCHRONIZATION_SOURCE_TARGET_STATES_PARENT_REGION, trans, null, -1);
		}

		for (Transition trans : outOrthogonal) {
			error(ISSUE_SYNCHRONIZATION_SOURCE_TARGET_STATES_PARENT_REGION, trans, null, -1);
		}

	}

	@Check
	public void checkStatechartNameIsIdentifier(Statechart statechart) {
		if (!isValidJavaIdentifier(statechart.getName())) {
			error(String.format(ISSUE_STATECHART_NAME_NO_IDENTIFIER, statechart.getName()), statechart, BasePackage.Literals.NAMED_ELEMENT__NAME, -1);

		}
	}
	
	protected boolean isValidJavaIdentifier(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}

		char[] c = s.toCharArray();
		if (!Character.isJavaIdentifierStart(c[0])) {
			return false;
		}

		for (int i = 1; i < c.length; i++) {
			if (!Character.isJavaIdentifierPart(c[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isLanguageSpecific() {
		return false;
	}

	@Inject
	public void register(EValidatorRegistrar registrar) {
		// Do not register because this validator is only a composite #398987
	}
}
