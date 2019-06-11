/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.dedupe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.MiscellaneousFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.ServiceMethods;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class ErrorSegmentDedupeImpl implements SyntheticBugDeduper {

	@Inject
	private SyntheticBugRepository syntheticBugRepository;

	@Inject
	private MongoOperations mongoOperations;

	@Override
	public Optional<SyntheticBug> findDuplicateBug(SyntheticBug sBug) {
		BugFields synBugFields = sBug.getBugFields();
		SyntheticBug newSbug = null;

		if ((synBugFields.getWorkflowStatus() != null && (synBugFields.getWorkflowStatus() != "Closed" || synBugFields.getWorkflowStatus() != "Dependent on other teams"||synBugFields.getWorkflowStatus() != "Duplicate - Already Fixed"||synBugFields.getWorkflowStatus() != "Reassigned"))
				||(synBugFields.getBugStatus() != null && synBugFields.getBugStatus() != "RESOLVED")) {
			List<SyntheticBug> sBugList = syntheticBugRepository.getDuplicatePreSrBug(synBugFields.getAgentName(),
					synBugFields.getErrorcode());
			sBugList = sBugList.stream().filter(sB -> {
				return SynUtil.STACK_TRACE_MATCHER.test(sB.getBugFields().getCodeReviewComments(),
						synBugFields.getCodeReviewComments());
			}).collect(Collectors.toList());

			if (!sBugList.isEmpty()) {
				newSbug = sBugList.get(0);
			}
		}
		return Optional.ofNullable(newSbug);
	}

	@Override
	public void updateDuplicateBug(SyntheticBug sbug, SyntheticBug duplicateBug) {
		BugFields synBugFields = sbug.getBugFields();
		BugFields duplicateBugFields = duplicateBug.getBugFields();
		if(null!=duplicateBug.getMiscellaneousFields()){
			duplicateBug.getMiscellaneousFields().setJnAnalysisId(sbug.getMiscellaneousFields().getJnAnalysisId());
			duplicateBug.getMiscellaneousFields().setRoute(sbug.getMiscellaneousFields().getRoute());		
		}else{
			MiscellaneousFields miscField = new MiscellaneousFields();
			miscField.setRoute(sbug.getMiscellaneousFields().getRoute());
			miscField.setJnAnalysisId(sbug.getMiscellaneousFields().getJnAnalysisId());
			duplicateBug.setMiscellaneousFields(miscField);
		}
		
		duplicateBugFields.setCodeReviewComments(synBugFields.getCodeReviewComments());

		String impact = synBugFields.getImpact().trim();
		if (impact.contains(SynUtil.UPDATED_AT)) {
			impact = impact.substring(0, impact.indexOf(SynUtil.UPDATED_AT));
		}
		impact = impact + SynUtil.UPDATED_AT + new SimpleDateFormat(SynUtil.DATE_FORMAT).format(new Date());

		duplicateBugFields.setImpact(impact);
		duplicateBugFields.setLabel(synBugFields.getLabel());
		duplicateBugFields.setMemitem(synBugFields.getMemitem());
		duplicateBugFields.setPriority(synBugFields.getPriority());
		duplicateBugFields.setSummary(synBugFields.getSummary());
		duplicateBugFields.setComment(synBugFields.getComment());



		ServiceMethods.getUpdatedAtDate(duplicateBugFields.getImpact())
				.ifPresent(date -> duplicateBug.getSyntheticFields().setUpdatedAt(date));

		duplicateBug.setBugFields(duplicateBugFields);
		duplicateBug.setUpdatedDate(new Date());
		mongoOperations.save(duplicateBug, mongoOperations.getCollectionName(SyntheticBug.class));

	}

}
