package com.yodlee.iae.bugr.resources.responses;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shreyas
 *
 */
@Named
@Scope("prototype")
@EqualsAndHashCode(callSuper = true)
public @Data class BugAttachmentResponse extends SyntheticBaseResponse {
	private Integer noOfAttachmentsAdded;
	private Integer totalAttachmentsCount;
}
