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
package org.yakindu.sct.ui.editor.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.Issue;
import org.yakindu.base.gmf.runtime.decorators.AbstractMarkerBasedDecorationProvider;
import org.yakindu.sct.model.sgraph.FinalState;
import org.yakindu.sct.model.sgraph.Pseudostate;
import org.yakindu.sct.model.sgraph.ui.validation.SCTIssue;
import org.yakindu.sct.ui.editor.editor.StatechartDiagramEditor;
import org.yakindu.sct.ui.editor.validation.IValidationIssueStore;
import org.yakindu.sct.ui.editor.validation.IValidationIssueStore.IResourceIssueStoreListener;

public class StatechartValidationDecorationProvider extends AbstractMarkerBasedDecorationProvider
		implements IDecoratorProvider {

	private static final String KEY = "org.yakindu.sct.ui.editor.validation";

	private IValidationIssueStore issueStore;

	protected boolean shouldInstall(IEditorPart part) {
		if (part instanceof StatechartDiagramEditor) {
			issueStore = (IValidationIssueStore) part.getAdapter(IValidationIssueStore.class);
			return true;
		}
		return false;
	}

	protected String getDecoratorKey() {
		return KEY;
	}

	protected ValidationDecorator createStatusDecorator(IDecoratorTarget decoratorTarget) {
		return new ValidationDecorator(decoratorTarget);
	}

	public class ValidationDecorator extends AbstractDecorator implements IResourceIssueStoreListener {

		private String viewId;

		public ValidationDecorator(IDecoratorTarget decoratorTarget) {
			super(decoratorTarget);
			try {
				final View view = (View) getDecoratorTarget().getAdapter(View.class);
				TransactionUtil.getEditingDomain(view).runExclusive(new Runnable() {
					public void run() {
						ValidationDecorator.this.viewId = view != null ? ViewUtil.getIdStr(view) : null;
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void refresh() {
			removeDecoration();
			View view = (View) getDecoratorTarget().getAdapter(View.class);
			if (view == null || view.eResource() == null) {
				return;
			}
			EditPart editPart = (EditPart) getDecoratorTarget().getAdapter(EditPart.class);
			if (editPart == null || editPart.getViewer() == null || !(editPart instanceof IPrimaryEditPart)) {
				return;
			}
			decorate(view);
		}

		public void activate() {
			View view = (View) getDecoratorTarget().getAdapter(View.class);
			if (view == null || view.eResource() == null) {
				return;
			}
			// add self to global decorators registry
			List<IDecorator> list = allDecorators.get(viewId);
			if (list == null) {
				list = new ArrayList<IDecorator>(2);
				list.add(this);
				allDecorators.put(viewId, list);
			} else if (!list.contains(this)) {
				list.add(this);
			}
			issueStore.addIssueStoreListener(this);
		}

		public void deactivate() {
			if (viewId == null) {
				return;
			}
			List<IDecorator> list = allDecorators.get(viewId);
			if (list != null) {
				list.remove(this);
				if (list.isEmpty()) {
					allDecorators.remove(viewId);
				}
			}
			View view = (View) getDecoratorTarget().getAdapter(View.class);
			if (view == null || view.eResource() == null) {
				return;
			}
			issueStore.removeIssueStoreListener(this);
			super.deactivate();
		}

		protected void decorate(View view) {
			String elementId = ViewUtil.getIdStr(view);
			if (elementId == null) {
				return;
			}
			List<SCTIssue> issues = issueStore.getIssues(elementId);
			Severity severity = Severity.INFO;
			Label toolTip = null;
			if (issues.isEmpty())
				return;
			for (int i = 0; i < issues.size(); i++) {
				Issue issue = issues.get(i);
				Severity nextSeverity = issue.getSeverity();
				Image nextImage = getImage(nextSeverity);
				if (toolTip == null) {
					toolTip = new Label(issue.getMessage(), nextImage);
				} else {
					if (toolTip.getChildren().isEmpty()) {
						Label comositeLabel = new Label();
						FlowLayout fl = new FlowLayout(false);
						fl.setMinorSpacing(0);
						comositeLabel.setLayoutManager(fl);
						comositeLabel.add(toolTip);
						toolTip = comositeLabel;
					}
					toolTip.add(new Label(issue.getMessage(), nextImage));
				}
				severity = (nextSeverity.ordinal() < severity.ordinal()) ? nextSeverity : severity;
			}

			if (view instanceof Edge) {
				setDecoration(getDecoratorTarget().addConnectionDecoration(getImage(severity), 50, true));
				getDecoration().setToolTip(toolTip);
			} else {
				int margin = view.getElement() instanceof Pseudostate || view.getElement() instanceof FinalState ? 0
						: -1;
				setDecoration(getDecoratorTarget().addShapeDecoration(getImage(severity),
						IDecoratorTarget.Direction.NORTH_EAST, margin, true));
				getDecoration().setToolTip(toolTip);
			}
		}

		protected Image getImage(Severity severity) {
			String imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
			switch (severity) {
			case ERROR:
				imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
				break;
			case WARNING:
				imageName = ISharedImages.IMG_OBJS_WARN_TSK;
				break;
			default:
				imageName = ISharedImages.IMG_OBJS_INFO_TSK;
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageName);
		}

		@Override
		public void issuesChanged() {
			refresh();
		}
	}
}
