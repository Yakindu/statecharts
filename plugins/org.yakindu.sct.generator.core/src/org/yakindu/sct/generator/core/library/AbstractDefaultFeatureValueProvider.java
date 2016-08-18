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
package org.yakindu.sct.generator.core.library;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.yakindu.sct.generator.core.GeneratorActivator;
import org.yakindu.sct.model.sgen.FeatureConfiguration;
import org.yakindu.sct.model.sgen.FeatureParameter;
import org.yakindu.sct.model.sgen.FeatureParameterValue;
import org.yakindu.sct.model.sgen.FeatureType;
import org.yakindu.sct.model.sgen.SGenFactory;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * 
 */
public abstract class AbstractDefaultFeatureValueProvider implements IDefaultFeatureValueProvider {

	protected static final SGenFactory factory = SGenFactory.eINSTANCE;

	protected abstract void setDefaultValue(FeatureType featureType, FeatureParameterValue parameterValue,
			EObject contextElement);

	public final FeatureConfiguration createDefaultFeatureConfiguration(FeatureType type, EObject contextElement) {
		FeatureConfiguration config = createConfiguration(type);
		EList<FeatureParameter> parameters = type.getParameters();
		for (FeatureParameter parameter : parameters) {
			FeatureParameterValue parameterValue = createParameterValue(type, parameter, contextElement);
			if (parameterValue != null && parameterValue.getExpression() != null) {
				config.getParameterValues().add(parameterValue);
			}
		}
		return config;
	}

	protected FeatureParameterValue createParameterValue(FeatureType featureType, FeatureParameter parameter,
			EObject contextElement) {
		FeatureParameterValue parameterValue = factory.createFeatureParameterValue();
		parameterValue.setParameter(parameter);
		setDefaultValue(featureType, parameterValue, contextElement);
		return parameterValue;
	}

	protected FeatureConfiguration createConfiguration(FeatureType type) {
		FeatureConfiguration result = factory.createFeatureConfiguration();
		result.setType(type);
		return result;
	}

	protected String getProjectName(EObject contextElement) {
		URI uri = EcoreUtil.getURI(contextElement);
		if (uri.isPlatformResource() && uri.segmentCount() > 1) {
			return uri.segment(1);
		}
		return "ProjectName";
	}

	protected IStatus error(String msg) {
		return new Status(IStatus.ERROR, GeneratorActivator.PLUGIN_ID, msg);
	}

	protected IStatus warning(String msg) {
		return new Status(IStatus.WARNING, GeneratorActivator.PLUGIN_ID, msg);
	}

	protected boolean projectExists(String value) {
		try {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(value).exists();
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	protected boolean projectOpened(String value) {
		try {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(value).isOpen();
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	protected boolean folderExists(String projectName, String folderPath) {
		try {
			IPath workspaceRelativePath = Path.fromPortableString(projectName).append(folderPath);
			return ResourcesPlugin.getWorkspace().getRoot().exists(workspaceRelativePath);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	protected boolean fileExists(String projectName, String folderPath) {
		try {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getFile(new Path(folderPath))
					.exists();
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
