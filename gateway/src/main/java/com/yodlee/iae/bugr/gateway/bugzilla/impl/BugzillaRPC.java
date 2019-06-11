/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.gateway.bugzilla.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.j2bugzilla.rpc.AddAttachment;
import com.j2bugzilla.rpc.GetAttachments;
import com.j2bugzilla.rpc.GetBug;
import com.j2bugzilla.rpc.ReportBug;

import com.yodlee.iae.bugr.gateway.bugzilla.BugSearch;
import com.yodlee.iae.bugr.gateway.bugzilla.UpdateBug;
import com.yodlee.iae.bugr.gateway.bugzilla.BugSearch.SearchQuery;
import com.yodlee.iae.bugr.gateway.bugzilla.base.BugzillaConnector;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.gateway.bugzilla.resource.BugRequest;
import com.yodlee.iae.bugr.gateway.bugzilla.util.IntegrationConstant;


/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
@Configuration
public class BugzillaRPC implements IBugzillaRPCClient {

	@Value("${bugzilla.userName}")
	private String userName;

	@Value("${bugzilla.password}")
	private String password;

	@Inject
	private BugzillaConnector bugzillaConnector;

	@Override
	public Bug fetchBug(Integer bugId) {
		GetBug bug = new GetBug(bugId);
		bugzillaConnector.executeMethod(new BugRequest(bug, userName, password));
		return bug.getBug();
	}

	@Override
	public List<Bug> searchBug(SearchQuery... searchQuery) {
		BugSearch searchBug = new BugSearch(searchQuery);
		bugzillaConnector.executeMethod(new BugRequest(searchBug, userName, password));
		return searchBug.getSearchResults();
	}

	@Override
	public List<Bug> searchBug(Map<Object, Object> queryMap) {
		com.yodlee.iae.bugr.gateway.bugzilla.resource.BugSearch searchBug = new com.yodlee.iae.bugr.gateway.bugzilla.resource.BugSearch(
				queryMap);
		bugzillaConnector.executeMethod(new BugRequest(searchBug, userName, password));
		return searchBug.getSearchResults();
	}
	
	@Override
	public String createBug(Map<String, Object> queryMap, Attachment attachment) {
		Bug bug = new BugFactory().newBug().createBug(queryMap);
		ReportBug reportBug = new ReportBug(bug);
		bugzillaConnector.executeMethod(new BugRequest(reportBug, userName, password));
		if (attachment != null) {
			queryMap.put(IntegrationConstant.BUG_ID, reportBug.getID());
			addAttachment(attachment, queryMap);
		}
		return String.valueOf(reportBug.getID());
	}

	private void addAttachment(Attachment attachment, Map<String, Object> queryMap) {
		Bug bug = new BugFactory().newBug().createBug(queryMap);
		AddAttachment addAttachment = new AddAttachment(attachment, bug);
		bugzillaConnector.executeMethod(new BugRequest(addAttachment, userName, password));
	}

	@Override
	public void setAttachment(Attachment attachment, Integer bugId) {
		AddAttachment addAttachment = new AddAttachment(attachment, bugId);
		bugzillaConnector.executeMethod(new BugRequest(addAttachment, userName, password));
	}

	@Override
	public List<Attachment> getAttachments(Integer bugId) {
		GetAttachments getAttachments = new GetAttachments(bugId.intValue());
		new GetAttachments(bugId).getAttachments();
		bugzillaConnector.executeMethod(new BugRequest(getAttachments, userName, password));
		return getAttachments.getAttachments();
	}

	@Override
	public void updateBug(Map<String, Object> queryMap, Attachment attachment) {
		Bug bug = null;
		UpdateBug updateBug = null;
		BugFactory bugFactory = new BugFactory();
		bug = bugFactory.newBug().createBug(queryMap);
		updateBug = new UpdateBug(bug);
		bugzillaConnector.executeMethod(new BugRequest(updateBug, userName, password));
//		if (null != queryMap.get(IntegrationConstant.COMMENT)) {
//			CommentBug commentBug = new CommentBug(bug, queryMap.get(IntegrationConstant.COMMENT).toString());
//			bugzillaConnector.executeMethod(new BugRequest(commentBug, userName, password));
//		}
		if (attachment != null)
			addAttachment(attachment, queryMap);
	}

	/*@Override
	public List<Comment> bugComments(Integer bugId) {
		BugComments bugComments = new BugComments(bugId);
		bugzillaConnector.executeMethod(new BugRequest(bugComments, userName, password));
		return bugComments.getComments();
	}*/
}
