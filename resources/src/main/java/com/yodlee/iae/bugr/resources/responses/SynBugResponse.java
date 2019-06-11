package com.yodlee.iae.bugr.resources.responses;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author KChandrarajan
 *
 */
@Scope("prototype")
@Named
@EqualsAndHashCode(callSuper = true)
public @Data class SynBugResponse extends SyntheticBaseResponse {
	private String bugID;
	private String syntheticBugId;
}
