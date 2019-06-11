/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.search.cache;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.SortFilterUtility;

/**
 * @author KChandrarajan
 *
 */
@Named
@EnableCaching
public class TTRBugsCache implements ICacheBugs {

	private static final Logger LOG = LoggerFactory.getLogger(TTRBugsCache.class);

	@Inject
	private SyntheticBugRepository syntheticBugRepository;

	@Inject
	private CachedBugsServiceImpl cachedBugsServiceImpl;

	private static Map<String, Set<Integer>> cachedTTRBugs;

	@Cacheable("cachedTTRBugs")
	public Optional<Map<String, Set<Integer>>> getCachedBugs() {
		return Optional.ofNullable(cachedTTRBugs);
	}

	@Override
	public Map<String, Set<Integer>> get() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -24);
		Date hoursBack = cal.getTime();

		TimeStamp timeStamp = new TimeStamp();
		timeStamp.setStartTime(hoursBack);
		timeStamp.setEndTime(new Date());

		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setTimeStamp(timeStamp);
		List<SyntheticBug> sBugList = syntheticBugRepository.searchBugs(bugSearchParam);
		List<Bug> bugzillaBugs = cachedBugsServiceImpl.getCachedBugsData().getBugs();
		List<Bug> ttrBugs = SortFilterUtility.filterTTRBugs(bugzillaBugs);
		return SynUtil.getSimilarBugs(sBugList, ttrBugs);
	}

	@Override
	public void accept(Map<String, Set<Integer>> bugs) {
		LOG.debug("Total TTR Similar Presr Bugs: %d", bugs.size());
		cachedTTRBugs = bugs;
	}

	@Override
	@CacheEvict("cachedTTRBugs")
	public void run() {
		// for CacheEvict
	}

}
