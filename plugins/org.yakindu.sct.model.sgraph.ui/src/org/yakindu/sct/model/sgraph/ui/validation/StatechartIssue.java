/**
 * Copyright (c) 2017 committers of YAKINDU and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of YAKINDU - initial API and implementation
 * 
 */
package org.yakindu.sct.model.sgraph.ui.validation;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;

/**
 * 
 * @author andreas muelder - Initial contribution and API x
 */
public class StatechartIssue extends IssueImpl {

	private final Issue delegate;

	private final String notationViewURI;

	private boolean stale;

	public StatechartIssue(final Issue delegate, String notationViewURI) {
		this.delegate = delegate;
		this.notationViewURI = notationViewURI;
	}

	public boolean isStale() {
		return stale;
	}

	public void setStale(boolean stale) {
		this.stale = stale;
	}

	public String getNotationViewURI() {
		return notationViewURI;
	}

	public Severity getSeverity() {
		return delegate.getSeverity();
	}

	public String getMessage() {
		return delegate.getMessage();
	}

	public String getCode() {
		return delegate.getCode();
	}

	public CheckType getType() {
		return delegate.getType();
	}

	public URI getUriToProblem() {
		return delegate.getUriToProblem();
	}

	public Integer getLineNumber() {
		return delegate.getLineNumber();
	}

	public Integer getOffset() {
		return delegate.getOffset();
	}

	public Integer getLength() {
		return delegate.getLength();
	}

	public boolean isSyntaxError() {
		return delegate.isSyntaxError();
	}

	public String[] getData() {
		return delegate.getData();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
		result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
		result = prime * result + ((notationViewURI == null) ? 0 : notationViewURI.hashCode());
		result = prime * result + ((getSeverity() == null) ? 0 : getSeverity().hashCode());
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
		StatechartIssue other = (StatechartIssue) obj;
		if (getCode() == null) {
			if (other.getCode() != null)
				return false;
		} else if (!getCode().equals(other.getCode()))
			return false;
		if (getCode() == null) {
			if (other.getCode() != null)
				return false;
		} else if (!getMessage().equals(other.getMessage()))
			return false;
		if (notationViewURI == null) {
			if (other.notationViewURI != null)
				return false;
		} else if (!notationViewURI.equals(other.notationViewURI))
			return false;
		if (getSeverity() != other.getSeverity())
			return false;
		return true;
	}

}