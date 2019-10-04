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
package org.yakindu.sct.ui.editor.editor;

import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramActionBarContributor;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.yakindu.sct.ui.editor.commands.DocumentationMenuAction;
import org.yakindu.sct.ui.perspectives.IYakinduSctPerspectives;

/**
 * 
 * @author andreas muelder - Initial contribution and API
 * @author bohl - extracted actions
 */
public class StatechartDiagramActionbarContributor extends DiagramActionBarContributor {

	@Override
	public void init(IActionBars bars) {
		super.init(bars);
		// workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=346648
		bars.setGlobalActionHandler(GlobalActionId.SAVE, null);
		bars.getToolBarManager().add(new DocumentationMenuAction());
		
		   IWorkbench wb = PlatformUI.getWorkbench();
		   IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		   IWorkbenchPage page = win.getActivePage();
		   
		   IPerspectiveDescriptor bla = page.getPerspective();
		   
//		   if (IYakinduSctPerspectives.ID_PERSPECTIVE_SCT_SIMULATION.equals(bla.getId())) {
			   IToolBarManager toolBarManager = bars.getToolBarManager();
			   bars.getToolBarManager().remove(ActionIds.CUSTOM_FONT_NAME);
			   bars.getToolBarManager().remove(ActionIds.CUSTOM_FILL_COLOR);
			   bars.getToolBarManager().remove(ActionIds.CUSTOM_FONT_SIZE);
			   bars.getToolBarManager().remove(ActionIds.CUSTOM_LINE_COLOR);
			   bars.getToolBarManager().remove(ActionIds.CUSTOM_ZOOM);
//		   }
		   
		   
		
		
		// remove 'arrange all' and 'arrange selection' actions
		bars.getToolBarManager().remove(ActionIds.MENU_ARRANGE);
		bars.getMenuManager().findMenuUsingPath(ActionIds.MENU_DIAGRAM).remove(ActionIds.MENU_ARRANGE);
	}

	@Override
	protected String getEditorId() {
		return StatechartDiagramEditor.ID;
	}

	@Override
	protected Class<StatechartDiagramEditor> getEditorClass() {
		return StatechartDiagramEditor.class;
	}
	
}
