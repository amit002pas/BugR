/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */ 
package com.yodlee.iae.bugr.services.synthetic.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;

/**
 * @author KChandrarajan
 *
 */
public interface IAsyncSyntheticProcess {

	/**
	 * 
	 * Create/Update Bugzilla bug in the background
	 * 
	 * @param process
	 * @param syntheticBugId
	 * @return 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static CompletableFuture<Void> executeAsync(ServiceIO process, String syntheticBugId) {
		return CompletableFuture.runAsync(() -> process.process(syntheticBugId), Executors.newSingleThreadExecutor());
	}

}
