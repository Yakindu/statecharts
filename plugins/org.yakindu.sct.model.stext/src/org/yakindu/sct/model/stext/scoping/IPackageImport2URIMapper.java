/**
 * Copyright (c) 2017 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     committers of YAKINDU - initial API and implementation
 */
package org.yakindu.sct.model.stext.scoping;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.yakindu.sct.model.stext.scoping.IPackageImport2URIMapper.NullImpl;

import com.google.inject.ImplementedBy;

/**
 * 
 * @author Andreas Mülder - Initial contribution and API
 *
 */
@ImplementedBy(NullImpl.class)
public interface IPackageImport2URIMapper {

	public PackageImport findPackageImport(Resource context, String packageImport);

	public Set<PackageImport> getAllImports(Resource context);

	public static class PackageImport {

		private String namespace;

		public PackageImport(String name, URI uri, String namespace) {
			this.name = name;
			this.uri = uri;
			this.namespace = namespace;
		}

		private String name;

		private URI uri;

		public String getName() {
			return name;
		}

		public URI getUri() {
			return uri;
		}

		public String getNamespace() {
			return namespace;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
			result = prime * result + ((uri == null) ? 0 : uri.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PackageImport other = (PackageImport) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (namespace == null) {
				if (other.namespace != null)
					return false;
			} else if (!namespace.equals(other.namespace))
				return false;
			if (uri == null) {
				if (other.uri != null)
					return false;
			} else if (!uri.equals(other.uri))
				return false;
			return true;
		}

	}

	public static class NullImpl implements IPackageImport2URIMapper {

		@Override
		public PackageImport findPackageImport(Resource context, String packageImport) {
			return null;
		}

		@Override
		public Set<PackageImport> getAllImports(Resource context) {
			return null;
		}
	}

}