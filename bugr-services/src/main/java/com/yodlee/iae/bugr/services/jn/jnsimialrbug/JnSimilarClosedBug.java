/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.jn.jnsimialrbug;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.search.CachedBugsServiceImpl;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.SortFilterUtility;

/**
 * @author vchhetri
 *
 */
@Named
@Scope("prototype")
public class JnSimilarClosedBug implements ServiceIO<TimeStamp, Map<String, Set<Integer>>> {

	@Inject
	private SyntheticBugRepository syntheticBugRepository;
	@Inject
	private CachedBugsServiceImpl cachedBugsServiceImpl;

	private TimeStamp timeStamp = new TimeStamp();
	private Map<String, Set<Integer>> response;

	@Override
	public void accept(TimeStamp arg0) {
		timeStamp = arg0;
	}

	@Override
	public Map<String, Set<Integer>> get() {
		return response;
	}

	@Override
	public void mapInput() {
		response = null;
	}

	@Override
	public void validate() {

	}

	@Override
	public void executeImpl() {
		BugSearchParam bugSearchParam = new BugSearchParam();
		bugSearchParam.setTimeStamp(timeStamp);

		List<SyntheticBug> sBugList = syntheticBugRepository.searchClosedBugs(bugSearchParam);
		List<Bug> bugzillaBugs = cachedBugsServiceImpl.getCachedBugsData().getBugs();
		List<Bug> jnBugs = SortFilterUtility.filterIATBugs(bugzillaBugs);

		response = SynUtil.getSimilarBugs(sBugList, jnBugs);
	}

	@Override
	public void mapOutput() {

	}

}
