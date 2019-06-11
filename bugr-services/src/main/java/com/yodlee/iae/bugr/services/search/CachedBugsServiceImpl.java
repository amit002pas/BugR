/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.search;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.yodlee.iae.bugr.datatypes.bugs.CachedBugsData;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaGatewayImpl;
import com.yodlee.iae.bugr.services.utilities.SortFilterUtility;
import com.yodlee.iae.common.services.ServiceBase;
/**
 *@author Sanyam Jain, Rajkumar Uppati, Rohit Raj
 */
@Named
@EnableCaching
public class CachedBugsServiceImpl extends ServiceBase{

	@Inject
	private BugzillaGatewayImpl bugzillaGateway;

	public static CachedBugsData cachedBugsData = new CachedBugsData();
	
	@Value("${errorcodes.reconwealth}")
	private String reconWealthErrorCodes;

	@Value("${errorcodes.reconbank}")
	private String reconBankErrorCodes;
	
	private List<String> reconWealthErrorCodesList;
	private List<String> reconBankErrorCodesList;

	private static final Logger LOG = LoggerFactory.getLogger(CachedBugsServiceImpl.class);

	public void fetchBugs(int numDays) {
		LOG.debug("Caching Bugs in CachedBugsServiceImpl");
		reconWealthErrorCodesList = Arrays.asList(reconWealthErrorCodes.split(","));
		reconBankErrorCodesList = Arrays.asList(reconBankErrorCodes.split(","));
		List<Bug> bugsList = null;
		try {
			bugsList = bugzillaGateway.getBugsFromBugzilla(numDays);
					
			if(bugsList != null && !bugsList.isEmpty()) {
				List<Bug> iatBugs = SortFilterUtility.filterIATBugs(bugsList);
				List<Bug> preSRBugs = SortFilterUtility.filterPreSRBugs(bugsList, reconWealthErrorCodesList, reconBankErrorCodesList);
				iatBugs.removeAll(preSRBugs);
				/*Edit start
				 * Adding to get last 24 hrs presr bug mapping with TTR bugs instead of last 7 days bugs..
				 * Backload ID B-34224
				 * Added by Rohit Raj
				 */
				preSRBugs=SortFilterUtility.filterBugsByLastUpdatedTime(preSRBugs, 24);
				/*
				 * Edit end
				 */
				
				List<Bug> ttrBugs = SortFilterUtility.filterTTRBugs(bugsList);
				cachedBugsData.setBugs(bugsList);
				LOG.debug("Setting Bugs in Cache is completed");
				cachedBugsData.setSimilarJNBugsMap(SortFilterUtility.getSimilarBugsByStackTrace(iatBugs, preSRBugs));
				LOG.debug("Setting SimilarJNBugsMap in Cache is completed");
				cachedBugsData.setSimilarTTRBugsMap(SortFilterUtility.getSimilarBugsByStackTrace(ttrBugs, preSRBugs));
				LOG.debug("Setting SimilarTTRBugsMap in Cache is completed");
			}
		} catch (ConnectionException | BugzillaException | ParseException e) {
			LOG.debug("Bugs could not be fetched, so not updating the cache: "+e.getMessage());
		}
	}

	@Cacheable("cached_all_bugs")
	public CachedBugsData getCachedBugsData() {
		return cachedBugsData;
	}

	public void setCachedBugsData(CachedBugsData cachedBugsData) {
		CachedBugsServiceImpl.cachedBugsData = cachedBugsData;
	}

	@CacheEvict("cached_all_bugs")
	public void evictCache() {
		LOG.debug("Evicting Bugs Cache");
	}

	@Override
	public void mapInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mapOutput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeImpl() {
		// TODO Auto-generated method stub

	}



}