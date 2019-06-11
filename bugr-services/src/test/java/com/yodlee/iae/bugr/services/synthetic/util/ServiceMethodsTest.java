/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.synthetic.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.StatusProgress;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;

public class ServiceMethodsTest {
	
	@InjectMocks
	private ServiceMethods serviceMethods;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void validateUpdateSuccess() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(StatusProgress.EMPTY);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(StatusProgress.INPROGRESS);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
	
	@Test
	public void validateUpdateSuccessToInProgress() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(StatusProgress.INPROGRESS);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(StatusProgress.SUCCESS);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
	
	@Test
	public void validateUpdateFailure() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(StatusProgress.FAILURE);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(StatusProgress.SUCCESS);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
	
	@Test
	public void validateUpdateFailuretoSuccess() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(StatusProgress.SUCCESS);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(StatusProgress.FAILURE);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
	
	@Test
	public void validateUpdateSynBugNull() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(StatusProgress.SUCCESS);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(null);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
	
	@Test
	public void validateUpdateBugEntitynull() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(null);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(null);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
	
	@Test
	public void validateUpdateSynBugNullNonFailCase() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(StatusProgress.INPROGRESS);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(null);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
	
	@Test
	public void validateUpdateEmptySuccessCase() {
		BugEntity bugEntity = new BugEntity();
		bugEntity.setProgressStatus(StatusProgress.SUCCESS);
		
		SyntheticBug sbugDup = new SyntheticBug();
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setStatusProgress(StatusProgress.INPROGRESS);
		sbugDup.setSyntheticFields(syntheticFields);
		
		ServiceMethods.validateUpdate(bugEntity, sbugDup);
	}
}
