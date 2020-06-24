/**
 * Copyright (c) 2010 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 *
 */
package org.yakindu.sct.ui.editor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutAnimator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableEditPolicyEx;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.internal.tools.RubberbandDragTracker;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.View;
import org.yakindu.base.gmf.runtime.editpolicies.SetPreferredSizeRequest;
import org.yakindu.sct.ui.editor.DiagramActivator;
import org.yakindu.sct.ui.editor.policies.CompartmentCreationEditPolicy;
import org.yakindu.sct.ui.editor.policies.RegionCompartmentCanonicalEditPolicy;

/**
 * @author muelder
 * @author muehlbrandt
 */
@SuppressWarnings("restriction")
public class RegionCompartmentEditPart extends ShapeCompartmentEditPart {

	public RegionCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new CompartmentCreationEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new RegionCompartmentCanonicalEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy());
		// Removes the collapse expand handler
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableEditPolicyEx());
		installEditPolicy(EditPolicyRoles.SNAP_FEEDBACK_ROLE, new SimpleSnapFeedbackPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new XYLayoutEditPolicy() {
			// This is required when live feedback is used
			@Override
			protected Object getConstraintFor(ChangeBoundsRequest request, GraphicalEditPart child) {
				if (request instanceof SetPreferredSizeRequest || RequestConstants.REQ_ADD.equals(request.getType())) {
					return super.getConstraintFor(request, child);
				}
				request.setSizeDelta(new Dimension(0, 0));
				request.setMoveDelta(new Point(0, 0));
				return super.getConstraintFor(request, child);
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		ShapeCompartmentFigure figure = new ShapeCompartmentFigure(getCompartmentName(), getMapMode());
		figure.getContentPane().setLayoutManager(getLayoutManager());
		figure.getContentPane().addLayoutListener(LayoutAnimator.getDefault());
		figure.setBorder(null);
		figure.setFitContents(true);
		figure.setToolTip((String) null);
		return figure;
	}

	@Override
	public PreferencesHint getDiagramPreferencesHint() {
		return DiagramActivator.DIAGRAM_PREFERENCES_HINT;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		((ResizableCompartmentFigure) getFigure()).getScrollPane()
				.setScrollBarVisibility(org.eclipse.draw2d.ScrollPane.NEVER);
	}
	
	@Override
	public DragTracker getDragTracker(Request request) {
		return new RubberbandDragTracker();
	}
}
