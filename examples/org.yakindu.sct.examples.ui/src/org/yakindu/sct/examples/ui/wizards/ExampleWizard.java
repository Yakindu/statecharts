/**
 * Copyright (c) 2016 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.examples.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.yakindu.sct.examples.ui.wizards.pages.ExamplePreconditionPage;
import org.yakindu.sct.examples.ui.wizards.pages.SelectExamplePage;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * 
 * @author t00manysecretss
 * 
 */

public class ExampleWizard extends Wizard implements INewWizard, ExampleWizardConstants {

	@Inject
	private IExampleService exampleService;
	@Inject
	private ExamplePreconditionPage page1;
	@Inject
	private SelectExamplePage page2;

	public ExampleWizard() {
		super();
		setWindowTitle(WINDOW_TITLE);
		setNeedsProgressMonitor(true);
		Guice.createInjector(new ExampleWizardModule()).injectMembers(this);

	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// Nothing do do
	}

	public void addPages() {
//		addPage(page1);
		addPage(page2);
	}

	public boolean performFinish() {
		final ExampleData selection = page2.getSelection();
		if (selection != null) {
			try {
				getContainer().run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException {
						exampleService.importExample(selection, monitor);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}