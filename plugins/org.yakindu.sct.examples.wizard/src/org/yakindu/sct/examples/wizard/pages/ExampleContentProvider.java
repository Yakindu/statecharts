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
package org.yakindu.sct.examples.wizard.pages;

import static org.yakindu.sct.examples.wizard.pages.ExampleCategory.CATEGORY_LABS;
import static org.yakindu.sct.examples.wizard.pages.ExampleCategory.CATEGORY_PROFESSIONAL;
import static org.yakindu.sct.examples.wizard.pages.ExampleCategory.CATEGORY_STANDARD;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.yakindu.sct.examples.wizard.service.ExampleData;

import com.google.common.collect.Lists;
/**
 * 
 * @author t00manysecretss
 * 
 */
public class ExampleContentProvider implements ITreeContentProvider {

	private Map<String, ExampleCategory> categories;

	public ExampleContentProvider() {
		categories = new LinkedHashMap<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		categories.clear();
		if (newInput != null)
			groupCategories((List<ExampleData>) newInput);
	}

	protected void groupCategories(List<ExampleData> newInput) {
		for (ExampleData exampleData : newInput) {
			if (exampleData.isProfessional()) {
				addExampleToCategory(exampleData, CATEGORY_PROFESSIONAL);
			} else if (exampleData.isLabs()) {
				addExampleToCategory(exampleData, CATEGORY_LABS);
			} else {
				addExampleToCategory(exampleData, CATEGORY_STANDARD);
			}
		}
	}

	protected void addExampleToCategory(ExampleData exampleData, String category) {
		if (!categories.containsKey(category)) {
			categories.put(category, new ExampleCategory(category));
		}
		categories.get(category).getChildren().add(exampleData);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<ExampleCategory> values = Lists.newArrayList();
		values.addAll(categories.values());
		Collections.sort(values, (o1, o2) -> {
			if (o1.getPriority() == o2.getPriority()) {
				return 0;
			} else if (o1.getPriority() < o2.getPriority()) {
				return -1;
			}
			return 1;
		});
		return values.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return ((ExampleCategory) parentElement).getChildren().toArray();
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Object getParent(Object element) {
		if (element instanceof ExampleData) {
			return categories.get(((ExampleData) element).getCategory());
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof ExampleCategory;
	}

	@Override
	public void dispose() {
		categories.clear();
	}
}
