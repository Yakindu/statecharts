/** 
 * Copyright (c) 2016 committers of YAKINDU and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * Contributors:
 * committers of YAKINDU - initial API and implementation
 *
*/
package org.yakindu.sct.ui.editor.validation;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.xtext.ui.editor.validation.IValidationIssueProcessor;
import org.yakindu.sct.model.sgraph.SGraphPackage;
import org.yakindu.sct.ui.editor.DiagramActivator;
import org.yakindu.sct.ui.editor.preferences.StatechartPreferenceConstants;

import com.google.inject.Inject;

/**
 * @author andreas muelder - Initial contribution and API
 */
public class LiveValidationListener extends ResourceSetListenerImpl {

	private static final int DELAY = 200; // ms

	@Inject
	private ValidationJob validationJob;

	private Resource resource;

	@Override
	public void resourceSetChanged(ResourceSetChangeEvent event) {
		for (Notification notification : event.getNotifications()) {
			if (notification.getNotifier() instanceof EObject
					&& notification.getEventType() != Notification.REMOVING_ADAPTER
					&& notification.getEventType() != Notification.RESOLVE) {
				EObject eObject = (EObject) notification.getNotifier();
				if (eObject.eClass().getEPackage() == SGraphPackage.eINSTANCE) {
					rescheduleJob();
					break;
				} else
					for (EClass eClass : eObject.eClass().getEAllSuperTypes()) {
						if (SGraphPackage.eINSTANCE == eClass.getEPackage()) {
							rescheduleJob();
							return;
						}
					}
			}
			else if (notification.getNotifier() instanceof Resource && this.resource != null
					&& !this.resource.equals(notification.getNotifier())) {
				rescheduleJob();
			}
		}
	}

	protected void rescheduleJob() {
		validationJob.cancel();
		if (liveValidationEnabled()) {
			validationJob.schedule(DELAY);
		}
	}

	public void scheduleValidation() {
		if (liveValidationEnabled()) {
			validationJob.schedule(DELAY);
		}
	}

	protected boolean liveValidationEnabled() {
		return DiagramActivator.getDefault().getPreferenceStore()
				.getBoolean(StatechartPreferenceConstants.PREF_LIVE_VALIDATION);
	}

	public void setResource(Resource resource) {
		this.resource = resource;
		validationJob.setResource(resource);
	}

	public void setValidationIssueProcessor(IValidationIssueProcessor validationIssueProcessor) {
		validationJob.setValidationIssueProcessor(validationIssueProcessor);
	}

	public void dispose() {
		if (validationJob != null)
			validationJob.cancel();
	}

}
