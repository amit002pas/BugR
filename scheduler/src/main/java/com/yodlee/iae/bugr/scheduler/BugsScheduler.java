package com.yodlee.iae.bugr.scheduler;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;

@Named
@EnableScheduling
@EnableAutoConfiguration
public class BugsScheduler {

	private static final Logger LOG = LoggerFactory.getLogger(BugsScheduler.class);

	@Inject
	private CachedBugsServiceImpl cachedBugsServiceImpl;

	@Inject
	private SyntheticBugsScheduler syntheticBugsScheduler;

	@Scheduled(fixedDelay = 2 * 60 * 1000)
	public void cacheBugsForSearchScheduler() {
		LOG.info(" -------> STARTED CACHEING BUGS");
		cachedBugsServiceImpl.fetchBugs(7);
		if (CachedBugsServiceImpl.cachedBugsData.getBugs() == null) {
			cachedBugsServiceImpl.fetchBugs(7);
			if (CachedBugsServiceImpl.cachedBugsData.getBugs() != null) {
				cachedBugsServiceImpl.evictCache();
			}
		} else {
			cachedBugsServiceImpl.evictCache();
		}
		CachedBugsServiceImpl.cachedBugsData.getBugs();
		syntheticBugsScheduler.cacheTTRandJNSyntheticBugs();
		LOG.info(" -------> CACHEING BUGS COMPLETED");
	}

}
