/**
 * Copyright (c) 2012 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.ui.editor.validation;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.yakindu.sct.model.sgraph.resource.AbstractSCTResource;
import org.yakindu.sct.ui.editor.DiagramActivator;

/**
 * The default implementation executes the validation within a readonly
 * transaction on the transactional editing domain. during validation the model
 * is locked and can not be changed.
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class DefaultValidationJob extends ValidationJob {

	public static class TransactionalValidationRunner extends RunnableWithResult.Impl<List<Issue>> {

		private IResourceValidator validator;
		private Resource resource;
		private CheckMode checkMode;
		private CancelIndicator indicator;

		public TransactionalValidationRunner(IResourceValidator validator, Resource resource, CheckMode checkMode,
				CancelIndicator indicator) {
			this.validator = validator;
			this.resource = resource;
			this.checkMode = checkMode;
			this.indicator = indicator;

		}

		public void run() {
			try {
				List<Issue> result = validator.validate(resource, checkMode, indicator);
				setResult(result);
				setStatus(Status.OK_STATUS);
			} catch (OperationCanceledException ex) {
				setStatus(Status.CANCEL_STATUS);
			}
		}
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) {
		try {
			if (!resource.isLoaded())
				return Status.CANCEL_STATUS;
			if (resource instanceof AbstractSCTResource) {
				relinkModel(monitor, (AbstractSCTResource) resource);
			}
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
			TransactionalValidationRunner runner = new TransactionalValidationRunner(validator, resource,
					CheckMode.FAST_ONLY, new CancelIndicator() {
						public boolean isCanceled() {
							return monitor.isCanceled();

						}
					});
			TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(resource);
			if (editingDomain == null)
				return Status.CANCEL_STATUS;
			try {
				editingDomain.runExclusive(runner);
			} catch (Throwable ex) {
				// Since xtext 2.8 this may throw an OperationCanceledError
				return Status.CANCEL_STATUS;
			}
			final List<Issue> issues = runner.getResult();
			if (issues == null)
				return Status.CANCEL_STATUS;

			validationIssueProcessor.processIssues(issues, monitor);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new Status(IStatus.ERROR, DiagramActivator.PLUGIN_ID, ex.getMessage());
		}
		return Status.OK_STATUS;
	}

	protected void relinkModel(final IProgressMonitor monitor, final AbstractSCTResource eResource)
			throws ExecutionException {
		AbstractTransactionalCommand cmd = new AbstractTransactionalCommand(TransactionUtil.getEditingDomain(eResource),
				"", null) {
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				eResource.linkSpecificationElements();
				return CommandResult.newOKCommandResult();

			}
		};
		cmd.execute(monitor, null);
	}
}