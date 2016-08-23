/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Andreas Mülder - Initial API and implementation
 *     Alexaner Nyßen - Refactored to use overriding module instead of child injector
 */
package org.yakindu.sct.generator.core.impl;

import static org.yakindu.sct.generator.core.filesystem.ISCTFileSystemAccess.API_TARGET_FOLDER_OUTPUT;
import static org.yakindu.sct.generator.core.filesystem.ISCTFileSystemAccess.LIBRARY_TARGET_FOLDER_OUTPUT;
import static org.yakindu.sct.generator.core.library.ICoreLibraryConstants.OUTLET_FEATURE_TARGET_PROJECT;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.yakindu.sct.generator.core.ISCTGenerator;
import org.yakindu.sct.generator.core.ISGraphGenerator;
import org.yakindu.sct.generator.core.console.IConsoleLogger;
import org.yakindu.sct.generator.core.filesystem.ISCTFileSystemAccess;
import org.yakindu.sct.generator.core.filesystem.ISCTWorkspaceAccess;
import org.yakindu.sct.generator.core.library.ICoreLibraryHelper;
import org.yakindu.sct.model.sexec.ExecutionFlow;
import org.yakindu.sct.model.sgen.FeatureParameterValue;
import org.yakindu.sct.model.sgen.GeneratorEntry;
import org.yakindu.sct.model.sgraph.Statechart;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.util.Modules;

/**
 * abstract base class for all code generators that want to generate code based
 * on the {@link ExecutionFlow}
 * 
 * @author Andreas Mülder - Initial contribution and API
 * @author Johannes Dicks - decouple EFS
 * 
 */
public abstract class AbstractSGraphModelGenerator implements ISCTGenerator {

	@Inject
	protected IConsoleLogger log;
	@Inject
	protected ICoreLibraryHelper coreFeatureHelper;
	@Inject
	protected ISCTFileSystemAccess sctFsa;
	@Inject
	protected ISCTWorkspaceAccess wsAccess;

	protected void runGenerator(Statechart statechart, GeneratorEntry entry) {
		if (this instanceof ISGraphGenerator) {
			ISGraphGenerator graphGenerator = (ISGraphGenerator) this;
			graphGenerator.generate(statechart, entry, sctFsa);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if (log != null)
			log.close();
		super.finalize();
	}

	/**
	 * This method should not be overridden anymore. It is meant to define a
	 * general process all generators might follow.
	 * 
	 * Use {@link #doGenerate(GeneratorEntry)} instead.
	 * 
	 * This method might become final in further versions. Please override
	 * {@link #doGenerate(GeneratorEntry)}
	 * 
	 * @see {@link #doGenerate(GeneratorEntry)}
	 */
	public void generate(GeneratorEntry entry) {
		if (canHandle(entry)) {
			try {
				logStart(entry);
				prepareGenerator(entry);
				doGenerate(entry);
				log.log("Done.");
			} catch (Exception e) {
				log.logError(e);
			} finally {
				finishGenerator(entry);
			}
		}
	}

	/**
	 * Logs some information about the following generation process.
	 * 
	 * @param entry
	 */
	protected void logStart(GeneratorEntry entry) {
		Statechart statechart = (Statechart) entry.getElementRef();
		log.log(String.format("Generating %s %s ...", Statechart.class.getSimpleName(), statechart.getName()));
	}

	/**
	 * Do generate for the given {@link GeneratorEntry}. Sub-classes might
	 * override this method if they do generation for elements other than
	 * {@link Statechart}.
	 * 
	 * @param entry
	 */
	protected void doGenerate(GeneratorEntry entry) {
		Statechart statechart = (Statechart) entry.getElementRef();
		runGenerator(statechart, entry);
	}

	/**
	 * Per default this method checks if the given {@link GeneratorEntry}
	 * includes a {@link Statechart}. Sub-classes may override this method if
	 * they handle elements with type other than @link {@link Statechart}.
	 * 
	 * @param entry
	 *            the upcomming GeneratorEntry
	 * @return true if this instance can handle the given {@link GeneratorEntry}
	 */
	protected boolean canHandle(GeneratorEntry entry) {
		EObject elementRef = entry.getElementRef();
		if (elementRef == null || !(elementRef instanceof Statechart)) {
			log.log("No Statechart selected in genmodel (" + entry + ")");
			return false;
		}
		return true;
	}

	/**
	 * Override this method to do any setup needed before generation. In normal
	 * case sub-classes will call super implementations.
	 */
	protected void prepareGenerator(GeneratorEntry entry) {
		initFileSystemAccess(entry);
	}

	/**
	 * Override this method to do any cleanup needed after generation. In normal
	 * case sub-classes will call super implementations.
	 */
	protected void finishGenerator(GeneratorEntry entry) {
		wsAccess.refreshProject(coreFeatureHelper.getTargetProjectValue(entry).getStringValue());
	}

	/**
	 * Sub-classes might override this method to add custom bindings for a
	 * specific {@link GeneratorEntry}.
	 * 
	 * In normal case sub-classes will just override or add custom bindings and
	 * ensure all other bindings of super-class implementations are still
	 * available.
	 * 
	 * @param entry
	 *            the next generator entry
	 * @return a module with custom bindings
	 * @see sub-classes often use {@link Modules#override(Module...)}
	 */
	public Module getOverridesModule(final GeneratorEntry entry) {
		return new Module() {
			@Override
			public void configure(Binder binder) {
				// currently, do not add additional bindings
			}
		};
	}

	/**
	 * Initialize the current file system access through generated files are
	 * written to a particular target location.
	 * 
	 * In case of overriding this method it has to be ensured that at least
	 * {@link IFileSystemAccess#DEFAULT_OUTPUT} will be initialized. Dependent
	 * on usage of FSA functionality
	 * <ul>
	 * <li>{@link IFileSystemAccess#generateFile(String, CharSequence)}</li>
	 * <li>{@link IFileSystemAccess#generateFile(String, String, CharSequence)}
	 * </li>
	 * </ul>
	 * Optional output configurations has to be registered. How output
	 * configurations are interpreted is defined by the concrete FSA
	 * implementation.
	 */
	protected void initFileSystemAccess(GeneratorEntry entry) {
		sctFsa.setContext(coreFeatureHelper.getTargetProjectValue(entry).getStringValue());
		initFsaTargetProject(entry);
		initDefaultOutput(entry);
		initLibraryTargetFolder(entry);
		initApiTargetFolder(entry);
	}

	protected void initLibraryTargetFolder(GeneratorEntry entry) {
		FeatureParameterValue libraryTargetFolderValue = coreFeatureHelper.getLibraryTargetFolderValue(entry);
		if (libraryTargetFolderValue != null) {
			sctFsa.setOutputPath(LIBRARY_TARGET_FOLDER_OUTPUT, libraryTargetFolderValue.getStringValue());
		}

		OutputConfiguration librarytargetFolderOutputConfiguration = sctFsa.getOutputConfigurations()
				.get(LIBRARY_TARGET_FOLDER_OUTPUT);
		if (librarytargetFolderOutputConfiguration != null) {
			librarytargetFolderOutputConfiguration.setCreateOutputDirectory(true);
			// do not overwrite existing resources and ensure the folder is not
			// cleaned.
			librarytargetFolderOutputConfiguration.setCanClearOutputDirectory(false);
			librarytargetFolderOutputConfiguration.setOverrideExistingResources(false);
		}
	}

	protected void initApiTargetFolder(GeneratorEntry entry) {
		FeatureParameterValue apiTargetFolderValue = coreFeatureHelper.getApiTargetFolderValue(entry);
		if (apiTargetFolderValue != null) {
			sctFsa.setOutputPath(API_TARGET_FOLDER_OUTPUT, apiTargetFolderValue.getStringValue());
		}
		OutputConfiguration apiTargetFolderOutputConfiguration = sctFsa.getOutputConfigurations()
				.get(API_TARGET_FOLDER_OUTPUT);
		if (apiTargetFolderOutputConfiguration != null) {
			apiTargetFolderOutputConfiguration.setCreateOutputDirectory(true);
		}
	}

	protected void initDefaultOutput(GeneratorEntry entry) {
		sctFsa.setOutputPath(IFileSystemAccess.DEFAULT_OUTPUT,
				coreFeatureHelper.getTargetFolderValue(entry).getExpression().toString());
		sctFsa.getOutputConfigurations().get(IFileSystemAccess.DEFAULT_OUTPUT).setCreateOutputDirectory(true);
	}

	protected void initFsaTargetProject(GeneratorEntry entry) {
		sctFsa.setOutputPath(OUTLET_FEATURE_TARGET_PROJECT,
				coreFeatureHelper.getTargetProjectValue(entry).getStringValue());
	}
}
