/**
 * Copyright (c) 2015 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 *
 */
package org.yakindu.base.gmf.runtime.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableEditPolicyEx;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.yakindu.base.gmf.runtime.editpolicies.SetPreferredSizeRequest;

/**
 *
 * @author andreas muelder - Initial contribution and API
 *
 */
public class LiveFeedbackResizableEditPolicy extends ResizableEditPolicyEx {

	private boolean connectionStart = true;
	protected PrecisionRectangle originalBounds = null;
	private final ChangeBoundsRequest NULL_REQUEST = new ChangeBoundsRequest(REQ_MOVE_CHILDREN);
	private String lastRequest = "";

	@Override
	protected void eraseChangeBoundsFeedback(ChangeBoundsRequest request) {
		connectionStart = true;
		super.eraseChangeBoundsFeedback(request);
	}

	@Override
	protected Command getMoveCommand(ChangeBoundsRequest request) {
		if (RequestConstants.REQ_DROP.equals(request.getType())) {
			return super.getMoveCommand(request);
		}
		NULL_REQUEST.setEditParts(getHost());
		return getHost().getParent().getCommand(NULL_REQUEST);
	}

	protected Rectangle getOriginalBounds() {
		if (originalBounds == null) {
			updateOriginalBounds();
		}
		return originalBounds.getCopy();
	}

	protected void updateOriginalBounds() {
		originalBounds = new PrecisionRectangle(getHostFigure().getBounds().getCopy());
		getHostFigure().translateToAbsolute(originalBounds);
	}

	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		if (RequestConstants.REQ_DROP.equals(request.getType())) {
			return super.getMoveCommand(request);
		}

		if (request instanceof SetPreferredSizeRequest) {
			SetPreferredSizeRequest req = new SetPreferredSizeRequest(REQ_RESIZE_CHILDREN);
			req.setEditParts(getHost());
			req.setCenteredResize(request.isCenteredResize());
			req.setConstrainedMove(request.isConstrainedMove());
			req.setConstrainedResize(request.isConstrainedResize());
			req.setSnapToEnabled(request.isSnapToEnabled());
			req.setMoveDelta(request.getMoveDelta());
			req.setSizeDelta(request.getSizeDelta());
			req.setLocation(request.getLocation());
			req.setExtendedData(request.getExtendedData());
			req.setResizeDirection(request.getResizeDirection());
			return getHost().getParent().getCommand(req);
		}
		NULL_REQUEST.setEditParts(getHost());
		return getHost().getParent().getCommand(NULL_REQUEST);
	}

	@Override
	protected ResizeTracker getResizeTracker(int direction) {
		LiveFeedbackResizeTracker liveFeedbackResizeTracker = new LiveFeedbackResizeTracker(
				(GraphicalEditPart) getHost(), direction);
		liveFeedbackResizeTracker.setOriginalBounds(getOriginalBounds());
		return liveFeedbackResizeTracker;
	}

	protected void enforceConstraintForMove(ChangeBoundsRequest request) {
		Rectangle relativeBounds = getOriginalBounds();
		PrecisionRectangle manipulatedConstraint = new PrecisionRectangle(
				 request.getTransformedRectangle(relativeBounds));
		getHostFigure().translateToRelative(manipulatedConstraint);
		
		manipulatedConstraint.setX(Math.max(0, manipulatedConstraint.x));
		manipulatedConstraint.setY(Math.max(0, manipulatedConstraint.y));
		
		getHostFigure().translateToAbsolute(manipulatedConstraint);
		
		Dimension difference = manipulatedConstraint.getLocation().getDifference(originalBounds.getLocation());
		request.setMoveDelta(new Point(difference.width, difference.height));
	}

	@Override
	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		// If REQ_DROP is delivered 2 times in a row it is a "real" drop and not only a
		// hover over existing elements in the same region
		if (RequestConstants.REQ_DROP.equals(request.getType()) && RequestConstants.REQ_DROP.equals(lastRequest)) {
			Rectangle rect = getOriginalBounds();
			getHostFigure().getParent().translateToRelative(rect);
			getHostFigure().setBounds(rect);
			super.showChangeBoundsFeedback(request);
			lastRequest = (String) request.getType();
			return;
		}
		super.eraseChangeBoundsFeedback(request);
		enforceConstraintForMove(request);
		if (connectionStart) {
			updateOriginalBounds();
			connectionStart = false;
		}
		Rectangle rect = request.getTransformedRectangle(getOriginalBounds());
		getHostFigure().getParent().translateToRelative(rect);
		getHostFigure().setBounds(rect);
		getHostFigure().getParent().setConstraint(getHostFigure(), rect);
		lastRequest = (String) request.getType();
	}

	public class LiveFeedbackResizeTracker extends org.yakindu.base.gmf.runtime.tracker.ResizeTracker {

		public LiveFeedbackResizeTracker(GraphicalEditPart owner, int direction) {
			super(owner, direction);
		}

		@Override
		protected Rectangle getOriginalBounds() {
			return LiveFeedbackResizableEditPolicy.this.getOriginalBounds();
		}

		protected void enforceConstraintsForResize(ChangeBoundsRequest request) {
			super.enforceConstraintsForResize(request);
			final IFigure figure = getHostFigure();
			Dimension prefSize = figure.getPreferredSize().getCopy();
			figure.translateToAbsolute(prefSize);
			Rectangle bounds = getOriginalBounds();
			bounds = request.getTransformedRectangle(bounds);
			if (bounds.width < prefSize.width) {
				request.getSizeDelta().width = request.getSizeDelta().width + (prefSize.width - bounds.width);
			}
			if (bounds.height < prefSize.height) {
				request.getSizeDelta().height = request.getSizeDelta().height + (prefSize.height - bounds.height);
			}
			request.setSizeDelta(request.getSizeDelta());
		}

	};

}
