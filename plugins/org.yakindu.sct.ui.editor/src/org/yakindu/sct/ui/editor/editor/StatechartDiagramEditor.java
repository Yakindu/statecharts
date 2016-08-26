/**
 * Copyright (c) 2011 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.ui.editor.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.internal.parts.DiagramGraphicalViewerKeyHandler;
import org.eclipse.gmf.runtime.gef.ui.internal.editparts.AnimatableZoomManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.yakindu.base.xtext.utils.gmf.resource.DirtyStateListener;
import org.yakindu.sct.domain.extension.DomainRegistry;
import org.yakindu.sct.domain.extension.IDomain;
import org.yakindu.sct.model.sgraph.SGraphPackage;
import org.yakindu.sct.ui.editor.DiagramActivator;
import org.yakindu.sct.ui.editor.partitioning.DiagramPartitioningEditor;
import org.yakindu.sct.ui.editor.partitioning.DiagramPartitioningUtil;
import org.yakindu.sct.ui.editor.preferences.StatechartPreferenceConstants;
import org.yakindu.sct.ui.editor.proposals.ContentProposalViewerKeyHandler;
import org.yakindu.sct.ui.editor.providers.ISCTOutlineFactory;
import org.yakindu.sct.ui.editor.utils.HelpContextIds;
import org.yakindu.sct.ui.editor.validation.SCTValidationJob;

import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * @author andreas muelder - Initial contribution and API
 * @author martin esser
 */
@SuppressWarnings("restriction")
public class StatechartDiagramEditor extends DiagramPartitioningEditor implements IGotoMarker {

	public static final String ID = "org.yakindu.sct.ui.editor.editor.StatechartDiagramEditor";
	private static final int DELAY = 200; // ms

	private KeyHandler keyHandler;

	private ResourceSetListener validationListener = new ResourceSetListenerImpl() {

		@Override
		public void resourceSetChanged(ResourceSetChangeEvent event) {
			for (Notification notification : event.getNotifications()) {
				if (notification.getNotifier() instanceof EObject
						&& notification.getEventType() != Notification.REMOVING_ADAPTER
						&& notification.getEventType() != Notification.RESOLVE) {
					EObject eObject = (EObject) notification.getNotifier();
					if (eObject.eClass().getEPackage() == SGraphPackage.eINSTANCE) {
						validationJob.cancel();
						if (liveValidationEnabled())
							validationJob.schedule(DELAY);
					} else
						for (EClass eClass : eObject.eClass().getEAllSuperTypes()) {
							if (SGraphPackage.eINSTANCE == eClass.getEPackage()) {
								validationJob.cancel();
								if (liveValidationEnabled())
									validationJob.schedule(DELAY);
								return;
							}
						}
				}
			}
		}

		protected boolean liveValidationEnabled() {
			return DiagramActivator.getDefault().getPreferenceStore()
					.getBoolean(StatechartPreferenceConstants.PREF_LIVE_VALIDATION);
		}
	};

	private DirtyStateListener domainAdapter;

	private SCTValidationJob validationJob;

	public StatechartDiagramEditor() {
		super(true);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class type) {
		if (IContentOutlinePage.class.equals(type)) {
			return createOutline(type);
		}
		return super.getAdapter(type);
	}

	protected Object createOutline(Class<?> type) {
		Injector editorInjector = getEditorInjector();
		boolean outlineBindingExists = null != editorInjector.getExistingBinding(Key.get(ISCTOutlineFactory.class));
		if (!outlineBindingExists) {
			// get the GMF default outline
			return super.getAdapter(type);
		}
		ISCTOutlineFactory outlineFactory = editorInjector.getInstance(ISCTOutlineFactory.class);
		return outlineFactory.createOutline(this);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		getEditingDomain().addResourceSetListener(validationListener);
		checkXtextNature();
		initValidationJob();
	}

	private void initValidationJob() {
		final IFile file = ((IFileEditorInput) getEditorInput()).getFile();
		validationJob = new SCTValidationJob();
		validationJob.setResource(getDiagram().eResource());
		Injector injector = getEditorInjector();
		injector.injectMembers(validationJob);
		validationJob.setRule(file);
	}

	protected Injector getEditorInjector() {
		IDomain domain = DomainRegistry.getDomain(getDiagram().getElement());
		Injector injector = domain.getInjector(IDomain.FEATURE_EDITOR);
		return injector;
	}

	private void checkXtextNature() {
		IFileEditorInput editorInput = (IFileEditorInput) getEditorInput();
		IProject project = editorInput.getFile().getProject();
		if (project != null && !XtextProjectHelper.hasNature(project) && project.isAccessible()
				&& !project.isHidden()) {
			addNature(project);
		}
	}

	public void addNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = XtextProjectHelper.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected TransactionalEditingDomain createEditingDomain() {
		TransactionalEditingDomain domain = DiagramPartitioningUtil.getSharedDomain();
		domainAdapter = new DirtyStateListener();
		domain.addResourceSetListener(domainAdapter);
		return domain;
	}

	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	@Override
	protected PreferencesHint getPreferencesHint() {
		return DiagramActivator.DIAGRAM_PREFERENCES_HINT;
	}

	@Override
	protected void createGraphicalViewer(Composite parent) {
		super.createGraphicalViewer(parent);
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		helpSystem.setHelp(getGraphicalViewer().getControl(), HelpContextIds.SC_EDITOR_GRAPHICAL_VIEWER);

	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		disableAnimatedZoom();
		createContentProposalViewerKeyHandler();
	}

	// Disable the animated zoom, it is too slow for bigger models
	protected void disableAnimatedZoom() {
		AnimatableZoomManager zoomManager = (AnimatableZoomManager) getGraphicalViewer()
				.getProperty(ZoomManager.class.toString());
		zoomManager.setZoomAnimationStyle(ZoomManager.ANIMATE_NEVER);
	}

	protected void createContentProposalViewerKeyHandler() {
		ContentProposalViewerKeyHandler contentProposalHandler = new ContentProposalViewerKeyHandler(
				getGraphicalViewer());
		contentProposalHandler
				.setParent(new DiagramGraphicalViewerKeyHandler(getGraphicalViewer()).setParent(getKeyHandler()));
		getGraphicalViewer().setKeyHandler(contentProposalHandler);
	}

	/**
	 * Overrides the GMF key handler to fix key binding for zooming and to
	 * remove unused key bindings.
	 */
	@Override
	protected KeyHandler getKeyHandler() {
		if (keyHandler == null) {
			keyHandler = new KeyHandler();

			registerZoomActions();

			// Zoom out - all OS - German and English keyboard layout
			getKeyHandler().put(KeyStroke.getPressed('-', 0x2d, SWT.MOD1),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));

			// Zoom in - all OS - English keyboard layout
			getKeyHandler().put(KeyStroke.getPressed('=', 0x3d, SWT.MOD1),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

			// Zoom in - Unix - German layout ([CTRL++] propagates char '+')
			getKeyHandler().put(KeyStroke.getPressed('+', 0x2b, SWT.MOD1),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

			// Zoom in - Windows - German layout ([CTRL++] propagates char 0x1d)
			getKeyHandler().put(KeyStroke.getPressed((char) 0x1d, 0x2b, SWT.MOD1),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		}
		return keyHandler;
	}

	@SuppressWarnings("unchecked")
	protected void registerZoomActions() {
		IAction action;
		action = new ZoomInAction(getZoomManager());
		action.setText(""); //$NON-NLS-1$ // no text necessary since this
							// is not a visible action
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ZoomOutAction(getZoomManager());
		action.setText(""); //$NON-NLS-1$ // no text necessary since this
							// is not a visible action
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public String getContributorId() {
		return ID;
	}

	@Override
	public void dispose() {
		validationJob.cancel();
		getEditingDomain().removeResourceSetListener(validationListener);
		getEditingDomain().removeResourceSetListener(domainAdapter);
		if (domainAdapter != null)
			domainAdapter.dispose();
		IFileEditorInput editorInput = (IFileEditorInput) getEditorInput();
		try {
			// Touch the file for revalidation, when the user did not save
			// the changes
			if (isDirty() && editorInput.getFile() != null && editorInput.getFile().exists()) {
				editorInput.getFile().touch(new NullProgressMonitor());
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		super.dispose();
	}

	@Override
	protected int getInitialPaletteSize() {
		return 175;
	}
}
