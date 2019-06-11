/**
 *Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms. 
 */
package com.yodlee.iae.bugr.resources.mongo;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;

import lombok.Data;

@Document(collection = "SyntheticBug")
@Named
public @Data class SyntheticBug {

	@Id
	private String syntheticBugid;

	private Date createdDate;
	private Date updatedDate;

	private SyntheticFields syntheticFields;
	private BugFields bugFields;
	private MiscellaneousFields miscellaneousFields;

	private List<AttachmentRequest> attachments;

}
