/**
 * Copyright (c) 2016 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.model.resource;

import org.eclipse.xtext.Constants;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.eclipse.xtext.ui.label.DefaultDescriptionLabelProvider;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.validation.IDiagnosticConverter;
import org.eclipse.xtext.validation.IResourceValidator;
import org.osgi.framework.Bundle;
import org.yakindu.sct.model.resource.validation.SCTResourceValidatorImpl;
import org.yakindu.sct.model.sgraph.naming.SGraphNameProvider;
import org.yakindu.sct.model.sgraph.ui.validation.SCTDiagnosticConverterImpl;
import org.yakindu.sct.model.sgraph.ui.validation.SCTMarkerCreator;
import org.yakindu.sct.model.sgraph.ui.validation.SCTMarkerTypeProvider;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class SCTExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

	@Override
	protected Injector getInjector() {
		// TODO: Provide a sharedDomainModule via DomainRegistry to allow
		// overriding of generic bindings
		return Guice.createInjector(new SharedStateModule(), new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(IResourceValidator.class).to(SCTResourceValidatorImpl.class);
				binder.bind(String.class).annotatedWith(Names.named(Constants.FILE_EXTENSIONS)).toInstance("sct");
				binder.bind(IEncodingProvider.class).to(IEncodingProvider.Runtime.class);
				binder.bind(IQualifiedNameProvider.class).to(SGraphNameProvider.class);
				binder.bind(org.eclipse.jface.viewers.ILabelProvider.class)
						.annotatedWith(org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider.class)
						.to(DefaultDescriptionLabelProvider.class);
				binder.bind(IDefaultResourceDescriptionStrategy.class).to(SCTResourceDescriptionStrategy.class);
				
				binder.bind(MarkerCreator.class).to(SCTMarkerCreator.class);
				binder.bind(MarkerTypeProvider.class).to(SCTMarkerTypeProvider.class);
				binder.bind(IDiagnosticConverter.class).to(SCTDiagnosticConverterImpl.class);
			}
		});
	}

}
