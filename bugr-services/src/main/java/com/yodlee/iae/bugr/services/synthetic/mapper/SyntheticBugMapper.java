/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.mapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticBugStatus;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.MiscellaneousFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class SyntheticBugMapper {


	/**
	 * Mapping bug entity to synthetic bug
	 * 
	 * @param bugList
	 * @return
	 * @throws Exception
	 */
	public Optional<List<SyntheticBug>> doMapping(List<BugEntity> bugList) {
		return Optional.ofNullable(bugList.stream().map(this::createSyntheticBug).collect(Collectors.toList()));
	}

	/**
	 * 
	 * Synthetic bug creation
	 * 
	 * @param bugEntity
	 * @param isValid
	 * @return
	 */
	private SyntheticBug createSyntheticBug(BugEntity bugEntity) {
		SyntheticBug sBug = new SyntheticBug();
		sBug.setCreatedDate(new Date());
		sBug.setUpdatedDate(new Date());
		BugFields synBugFields = Mapper.mapBugToSynthetic(bugEntity);
		sBug.setBugFields(synBugFields);
		SyntheticFields sF = new SyntheticFields();
		sF.setApiKey(bugEntity.getApiKey());
		MiscellaneousFields miscField = new MiscellaneousFields();
		miscField.setJnAnalysisId(bugEntity.getJnAnalysisId());
		miscField.setRoute(bugEntity.getRoute());
		sBug.setMiscellaneousFields(miscField);
		sBug.setSyntheticFields(sF);
		sBug.getSyntheticFields().setSyntheticBugStatus(SyntheticBugStatus.ACTIVE);
		sBug.getSyntheticFields().setBugzillaBugCreated(false);
		return sBug;
	}

}
