/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */ 
package com.yodlee.iae.bugr.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.yodlee.iae.bugr.resources.mongo.AuditSchedulerBase;
import com.yodlee.iae.bugr.resources.mongo.ClosedBugsAuditScheduler;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.async.IAsyncSyntheticProcess;
import com.yodlee.iae.bugr.services.synthetic.bugzillasync.SyncSyntheticBugsWithBugzilla;
import com.yodlee.iae.bugr.services.synthetic.dependson.JNTTRDependsOnUpdate;
import com.yodlee.iae.bugr.services.synthetic.search.cache.ICacheBugs;

/**
 * @author KChandrarajan
 *
 */
@Named
@EnableScheduling
@EnableAutoConfiguration
public class SyntheticBugsScheduler {

	@Inject
	private SyncSyntheticBugsWithBugzilla syncSyntheticBugsWithBugzilla;

	@Inject
	private ApplicationContext context;

	@Inject
	private MongoOperations mongoOperations;

	@Inject
	JNTTRDependsOnUpdate jNTTRDependsOnUpdate;

	private static final Logger LOG = LoggerFactory.getLogger(SyntheticBugsScheduler.class);

	/**
	 * Bugzilla Sync with Synthetic
	 * 
	 */
	@Scheduled(fixedDelay = 15 * 60 * 1000)
	public void syncSyntheticBugsWithBugzilla() {
		LOG.info("##### STARTED syncSyntheticBugsWithBugzilla");
		Query query = new Query();
		query.limit(1);
		query.with(new Sort(Sort.Direction.DESC, "createdDate"));
		ClosedBugsAuditScheduler audit = mongoOperations.findOne(query, ClosedBugsAuditScheduler.class,
				mongoOperations.getCollectionName(AuditSchedulerBase.class));
		Date date;
		if (audit == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			date = cal.getTime();
		} else {
			date = audit.getCreatedDate();
		}

		List<SyntheticBug> list = syncSyntheticBugsWithBugzilla.process(date);
		audit = new ClosedBugsAuditScheduler();
		audit.setCreatedDate(new Date());
		audit.setSchedularName("SyncSyntheticBugsWithBugzilla");
		audit.setBugsClosed(list.size());
		audit.setSyntheticBugIds(list.stream().map(SyntheticBug::getSyntheticBugid).collect(Collectors.toList()));
		mongoOperations.save(audit);
		LOG.info("##### COMPLETED syncSyntheticBugsWithBugzilla");
	}

	/**
	 * Similar bug caching for JN and TTR
	 * 
	 */
	public void cacheTTRandJNSyntheticBugs() {
		LOG.info(" ***** STARTING cacheTTRandJNSyntheticBugs");
		context.getBeansOfType(ICacheBugs.class).values().stream().map(cacheBugsImpl -> CompletableFuture
				.supplyAsync(cacheBugsImpl).thenAccept(cacheBugsImpl).thenRun(cacheBugsImpl)).forEach(future -> {
					try {
						future.get();
					} catch (InterruptedException | ExecutionException e) {
						Thread.currentThread().interrupt();
						LOG.info("Exception in cacheTTRandJNSyntheticBugs", e);
					}
				});
		IAsyncSyntheticProcess.executeAsync(jNTTRDependsOnUpdate, null);
		LOG.debug(" ***** jNTTRDependsOnUpdate will finish in parallel");
		LOG.debug(" ***** cacheTTRandJNSyntheticBugs COMPLETED");
	}

}
