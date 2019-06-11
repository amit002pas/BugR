package com.yodlee.iae.bugr.datatypes.bugs;

import java.util.List;

import javax.inject.Named;

import lombok.Data;

/**
 * @author KChandrarajan
 *
 */
@Named
public @Data class SyntheticAttachmentRequest {
	public String bugId;
	public List<AttachmentRequest> attachments;
}
