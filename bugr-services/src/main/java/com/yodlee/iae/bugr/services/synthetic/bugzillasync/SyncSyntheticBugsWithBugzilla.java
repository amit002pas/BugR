/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.bugzillasync;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.mongodb.core.MongoOperations;

import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaConstants;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

/**
 * @author KChandrarajan
 *
 */
@Named
public class SyncSyntheticBugsWithBugzilla implements ServiceIO<Date, List<SyntheticBug>> {

	@Inject
	private SynUtil synUtil;
	@Inject
	private SyntheticBugRepository syntheticBugRepository;
	@Inject
	private MongoOperations mongoOperations;

	private List<Bug> bugzillaBugs;
	private List<SyntheticBug> syntheticBugs;
	private Date date;

	@Override
	public void executeImpl() {
		syntheticBugs.stream().forEach(sBug -> {
			int synBugzillaId = sBug.getSyntheticFields().getBugzillaBugId().intValue();
			Bug bug = bugzillaBugs.stream().filter(bugzillaBug -> bugzillaBug.getID() == synBugzillaId).findFirst()
					.get();
			Map<Object, Object> parameters = bug.getParameterMap();
			Optional.ofNullable(parameters.get(BugzillaConstants.BUG_STATUS))
					.ifPresent(val -> sBug.getBugFields().setBugStatus(val.toString()));
			sBug.getSyntheticFields().setSyntheticBugStatus(SyntheticBugStatus.CLOSED);
			mongoOperations.save(sBug);
		});
	}

	@Override
	public void mapInput() {
		bugzillaBugs = synUtil.getBugzillaClosedBugs(date);
		syntheticBugs = syntheticBugRepository
				.getSynBugsFromBugzillaIds(bugzillaBugs.stream().map(Bug::getID).collect(Collectors.toList()));
	}

	@Override
	public void mapOutput() {
		//
	}

	@Override
	public void validate() {
		//
	}

	@Override
	public void accept(Date date) {
		this.date = date;
	}

	@Override
	public List<SyntheticBug> get() {
		return syntheticBugs;
	}

}
