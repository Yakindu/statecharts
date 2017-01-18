/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.model.stext.scoping;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.FilteringScope;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.yakindu.base.types.Package;
import org.yakindu.base.types.TypesPackage;
import org.yakindu.base.types.typesystem.ITypeSystem;
import org.yakindu.sct.domain.extension.DomainRegistry;
import org.yakindu.sct.model.sgraph.SGraphPackage;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.util.ContextElementAdapter;
import org.yakindu.sct.model.stext.stext.ImportScope;
import org.yakindu.sct.model.stext.stext.StatechartSpecification;
import org.yakindu.sct.model.stext.stext.StextPackage;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author andreas muelder - Initial contribution and API
 * 
 */
public class STextGlobalScopeProvider extends ImportUriGlobalScopeProvider {

	@Inject
	private ITypeSystem typeSystem;
	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;
	@Inject
	private IResourceScopeCache cache;
	@Inject
	private DefaultGlobalScopeProvider delegate;

	public void setCache(IResourceScopeCache cache) {
		this.cache = cache;
	}

	public static final String FILE_EXTENSION = "sct";

	public IScope getScope(Resource context, EReference reference, Predicate<IEObjectDescription> filter) {
		if (reference.getEReferenceType() == TypesPackage.Literals.PACKAGE) {
			return delegate.getScope(context, reference, filter);
		}
		IScope parentScope = super.getScope(context, reference, filter);
		parentScope = filterExternalDeclarations(context, parentScope);
		final Statechart statechart = getStatechart(context);
		parentScope = new TypeSystemAwareScope(parentScope, typeSystem, qualifiedNameProvider,
				reference.getEReferenceType());
		return new FilteringScope(parentScope, new Predicate<IEObjectDescription>() {
			@Override
			public boolean apply(IEObjectDescription input) {
				String userData = input.getUserData(DomainRegistry.DOMAIN_ID);
				if (userData == null)
					return true;

				return statechart.getDomainID().equals(userData);
			}
		});
	}

	@Override
	public IScope getScope(Resource resource, EReference reference) {
		if (reference.getEReferenceType() == TypesPackage.Literals.PACKAGE) {
			return delegate.getScope(resource, reference);
		}
		return super.getScope(resource, reference);
	}

	protected LinkedHashSet<URI> getImportedUris(final Resource resource) {
		return cache.get(ImportUriGlobalScopeProvider.class.getName(), resource, new Provider<LinkedHashSet<URI>>() {
			@Override
			public LinkedHashSet<URI> get() {
				final LinkedHashSet<URI> uniqueImportURIs = new LinkedHashSet<URI>(5);
				IAcceptor<String> collector = createURICollector(resource, uniqueImportURIs);
				Collection<ImportScope> importScopes = getImportScopes(resource);
				for (ImportScope object : importScopes) {
					EList<Package> imports = object.getImports();
					for (Package package1 : imports) {
						EcoreUtil.resolveAll(package1);
						if (package1.eIsProxy())
							continue;
						collector.accept(EcoreUtil.getURI(package1).trimFragment().toString());
					}
				}
				Iterator<URI> uriIter = uniqueImportURIs.iterator();
				while (uriIter.hasNext()) {
					if (!EcoreUtil2.isValidUri(resource, uriIter.next()))
						uriIter.remove();
				}
				System.out.println(uniqueImportURIs);
				return uniqueImportURIs;
			}

			private Collection<ImportScope> getImportScopes(final Resource resource) {
				StatechartSpecification specification = (StatechartSpecification) EcoreUtil
						.getObjectByType(resource.getContents(), StextPackage.Literals.STATECHART_SPECIFICATION);
				if (specification != null) {
					return EcoreUtil.getObjectsByType(specification.getScopes(), StextPackage.Literals.IMPORT_SCOPE);
				} else {
					Statechart statechart = getStatechart(resource);
					return EcoreUtil.getObjectsByType(statechart.getScopes(), StextPackage.Literals.IMPORT_SCOPE);
				}
			}
		});
	}

	private Statechart getStatechart(Resource context) {
		final ContextElementAdapter provider = (ContextElementAdapter) EcoreUtil.getExistingAdapter(context,
				ContextElementAdapter.class);
		if (provider == null) {
			return (Statechart) EcoreUtil2.getObjectByType(context.getContents(), SGraphPackage.Literals.STATECHART);
		} else {
			return (Statechart) EcoreUtil.getObjectByType(provider.getElement().eResource().getContents(),
					SGraphPackage.Literals.STATECHART);
		}
	}

	/**
	 * Filter all Elements that are part of an SCT file from other resources to
	 * avoid cross document referencing
	 */
	protected IScope filterExternalDeclarations(Resource context, IScope parentScope) {
		final ContextElementAdapter provider = (ContextElementAdapter) EcoreUtil.getExistingAdapter(context,
				ContextElementAdapter.class);
		final URI resourceURI = provider != null ? provider.getElement().eResource().getURI() : context.getURI();
		parentScope = new FilteringScope(parentScope, new Predicate<IEObjectDescription>() {
			public boolean apply(IEObjectDescription input) {
				if (FILE_EXTENSION.equals(input.getEObjectURI().fileExtension())) {
					URI sourceURI = input.getEObjectURI().trimFragment();
					return sourceURI.equals(resourceURI);
				}
				return true;
			}
		});
		return parentScope;
	}
}
