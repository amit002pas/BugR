/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.gateway.bugzilla.base;

import java.util.List;
import java.util.Map;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.Bug;
import com.yodlee.iae.bugr.gateway.bugzilla.BugSearch.SearchQuery;
/**
 * class implements bugzilla services
 * 
 * @author kgupta1
 *
 */
public interface IBugzillaRPCClient {

	/**
	 * This method will fetch bug whose id given as input
	 * 
	 * @param bugId
	 * @return Bug corresponding to given bug id @
	 */
	public Bug fetchBug(final Integer bugId);

	/**
	 * This method will search the bug based on search query
	 * 
	 * @param queryMap
	 * @return List of bug corresponding to query map @
	 */
	public List<Bug> searchBug(SearchQuery... searchQuery);
	/**
	 * This method will search the bug based on query map
	 * 
	 * @param queryMap
	 * @return List of bug corresponding to query map @
	 */
	public List<Bug> searchBug(Map<Object, Object> queryMap);
	
	/**
	 * This method will crate bug based on given input
	 * 
	 * @param queryMap
	 * @param attachment
	 * @return Bug id of the created bug @
	 */
	public String createBug(Map<String, Object> queryMap, Attachment attachment);

	/**
	 * This method will update the bug only if query map contains bug id else will
	 * create new bug
	 * 
	 * @param queryMap
	 * @param attachment @
	 */
	public void updateBug(Map<String, Object> queryMap, Attachment attachment);

	/**
	 * This method will add attachment to bug only if query map contains bug id else
	 * will create new bug and then will add attachment
	 * 
	 * @param bugId
	 * @return List of comment
	 */
	//public List<Comment> bugComments(final Integer bugId);

	/**
	 * 
	 * This method will return all the attachments
	 * 
	 * @param bugId @ @throws
	 */
	public List<Attachment> getAttachments(Integer bugId);

	/**
	 * 
	 * This method will set attachments
	 * 
	 * @param attachment @param queryMap @ @throws
	 */
	public void setAttachment(Attachment attachment, Integer bugId);

}
