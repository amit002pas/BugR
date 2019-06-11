package com.yodlee.iae.bugr.datatypes.bugs;

import javax.inject.Named;

import lombok.Data;

/**
 * @author KChandrarajan
 *
 */
@Named
public @Data class AttachmentRequest {
	public String attachmentName;
	public byte[] attachment;
}
