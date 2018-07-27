package org.yakindu.sct.examples.wizard.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.yakindu.sct.examples.wizard.service.ExampleData;

public class ExampleCategory implements Comparable<ExampleCategory>{
	
	public static final String CATEGORY_PROFESSIONAL = "Professional Examples";
	public static final String CATEGORY_LABS = "Labs Examples";
	public static final String CATEGORY_STANDARD = "Standard Examples";
	
	private String name;
	private List<ExampleData> children;
	private int priority;
	
	public ExampleCategory(String name) {
		this.name = name;
		switch (name) {
			case CATEGORY_PROFESSIONAL: this.priority = 1; break;
			case CATEGORY_STANDARD: this.priority = 2; break;
			case CATEGORY_LABS: this.priority = 3; break;
			default: this.priority = 4;
		}
		children = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExampleData> getChildren() {
		return children;
	}

	@Override
	public int compareTo(ExampleCategory o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}