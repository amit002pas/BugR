/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SyntheticSearchBugsResponse;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.manager.SyntheticBugManager;
import com.yodlee.iae.bugr.services.synthetic.search.cache.JNBugsCache;
import com.yodlee.iae.bugr.services.synthetic.search.cache.TTRBugsCache;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class SyntheticBugSearch implements ServiceIO<BugSearchParam, SyntheticSearchBugsResponse> {

	@Inject
	private SyntheticBugManager syntheticBugManager;

	@Inject
	private SyntheticSearchBugsResponse synSearchBugsResp;
	
	@Inject
	private SynUtil sunUtil;

	@Inject
	private TTRBugsCache ttrBugsCache;

	@Inject
	private JNBugsCache jnBugsCache;

	private BugSearchParam bugSearchParam;
	private boolean isStepSuccess;

	private boolean isCSVRequired;

	@Override
	public void accept(BugSearchParam obj) {
		this.bugSearchParam = obj;
	}

	@Override
	public SyntheticSearchBugsResponse get() {
		return synSearchBugsResp;
	}

	@Override
	public void executeImpl() {
		syntheticBugManager.getBugSearcher(bugSearchParam.getFilters().getSourceProduct()).getBugs(bugSearchParam)
				.ifPresent(synBugList -> {
					synSearchBugsResp.setBugCount(synBugList.size());
					setAllUniqueAgentNamesAndErrorCodes(synBugList);
					setSimilarJNandTTRBugs(synBugList);
					synBugList = applySortandPagination(synBugList);
					if (!isCSVRequired) {
						synBugList.stream().forEach(SynUtil::convertAttachmentsToBase64);
						synSearchBugsResp.setBugs(synBugList);
					} else {
						List<Map<String, String>> finalList = new ArrayList<Map<String, String>>();
						finalList = sunUtil.getDataForCSV(synBugList);
						synSearchBugsResp.setCsvDetails(finalList);
					}
					isStepSuccess = true;
				});
	}

	/**
	 * Set unique Agent Names and Error Codes values as map
	 * 
	 * @param synBugList
	 */
	private void setAllUniqueAgentNamesAndErrorCodes(List<SyntheticBug> synBugList) {
		Set<String> agentNames = new HashSet<>();
		Set<String> errorCodes = new HashSet<>();
		synBugList.stream().forEach(synBug -> {
			agentNames.add(synBug.getBugFields().getAgentName());
			errorCodes.add(synBug.getBugFields().getErrorcode());
		});
		synSearchBugsResp.setAgentValues(agentNames);
		synSearchBugsResp.setErrorCodeValues(errorCodes);
	}

	/**
	 * Set similar Juggernaut and TTR bugs
	 * 
	 * @param synBugList
	 */
	private void setSimilarJNandTTRBugs(List<SyntheticBug> synBugList) {
		synSearchBugsResp.setSimilarJNBugsMap(Collections.emptyMap());
		synSearchBugsResp.setSimilarTTRBugsMap(Collections.emptyMap());
		List<String> synBugIdList = synBugList.stream().map(SyntheticBug::getSyntheticBugid).distinct()
				.collect(Collectors.toList());
		jnBugsCache.getCachedBugs().ifPresent(jnMap -> {
			jnMap = jnMap.entrySet().stream().filter(entry -> synBugIdList.contains(entry.getKey()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			synSearchBugsResp.setSimilarJNBugsMap(jnMap);
		});
		ttrBugsCache.getCachedBugs().ifPresent(ttrMap -> {
			ttrMap = ttrMap.entrySet().stream().filter(entry -> synBugIdList.contains(entry.getKey()))
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			synSearchBugsResp.setSimilarTTRBugsMap(ttrMap);
		});
	}

	/**
	 * Sort filter and pagination handling
	 * 
	 * @param synBugList
	 * @return
	 */
	private List<SyntheticBug> applySortandPagination(List<SyntheticBug> synBugList) {
		Comparator<SyntheticBug> compare = (s1, s2) -> {
			int sort = 1;
			if (BugSortBy.FAILURE.equals(bugSearchParam.getSort().getCategory())) {
				try {
					sort = SynUtil.getImpactCountFromString(s1.getBugFields().getImpact())
							- SynUtil.getImpactCountFromString(s2.getBugFields().getImpact());
				} catch (Exception e) {
					sort = 1;
				}
			}
			if (BugSortOrder.DESCENDING.equals(bugSearchParam.getSort().getOrder()))
				sort = sort * -1;
			return sort;
		};

		synBugList = synBugList.stream().sorted(compare).collect(Collectors.toList());

		try {
			int[] list = synBugList.stream()
					.mapToInt(sBug -> SynUtil.getImpactCountFromString(sBug.getBugFields().getImpact())).toArray();
			synSearchBugsResp.setImpactCount(list);
		} catch (Exception e) {
			//
		}

		if (bugSearchParam.getPageNum() != -1) {
			synBugList = synBugList.stream().skip((long) 10 * (bugSearchParam.getPageNum() - 1)).limit(10)
					.collect(Collectors.toList());
		} else {
			isCSVRequired = true;
		}

		return synBugList;
	}

	@Override
	public void mapInput() {
		isStepSuccess = false;
		isCSVRequired = false;
	}

	@Override
	public void mapOutput() {
		if (isStepSuccess) {
			synSearchBugsResp.setStatus(ResponseConstants.STATUS_SUCCESS);
		} else {
			synSearchBugsResp.setStatus(ResponseConstants.STATUS_FAILURE);
		}
	}

	@Override
	public void validate() {
		//
	}

}
