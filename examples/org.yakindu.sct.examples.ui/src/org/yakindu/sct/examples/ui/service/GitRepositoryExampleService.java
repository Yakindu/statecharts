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
package org.yakindu.sct.examples.ui.service;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.yakindu.sct.examples.ui.wizards.ExampleWizardConstants;

/**
 * 
 * @author t00manysecretss
 * 
 */

public class GitRepositoryExampleService implements ExampleWizardConstants {

	public static boolean updateExampleRepository(IProgressMonitor iMonitor) {
		try {
			InetAddress.getByName("github.com").getHostName();
			for (int i = 0; i < REMOTE_REPOS.length; i++) {
				if (!updateExampleRepository(REMOTE_REPOS[i], new GitProgressMonitor(iMonitor))) {
					return false;
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected static boolean updateExampleRepository(String remoteRepo, ProgressMonitor monitor) {
		try {
			File localRepo = new File(LOCAL_REPO_ROOT.getAbsolutePath() + System.getProperty("file.separator")
					+ remoteRepo.substring(remoteRepo.lastIndexOf("/"), remoteRepo.indexOf(".git")));
			if (localRepo.exists()) {

				// TODO Update Improvement
				delete(localRepo);
				Git.cloneRepository().setURI(remoteRepo).setDirectory(localRepo).setProgressMonitor(monitor).call();
			} else {
				Git.cloneRepository().setURI(remoteRepo).setDirectory(localRepo).setProgressMonitor(monitor).call();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected static void delete(File file) {
		for (File f : file.listFiles()) {
			f.getName();
			if (f.isDirectory()) {
				delete(f);
				f.delete();
			} else {
				f.delete();
			}
			file.delete();
		}
	}
}