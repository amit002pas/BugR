package com.yodlee.iae.bugr.resources.responses;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.BugType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author KChandrarajan
 *
 */
@Scope("prototype")
@Named
@EqualsAndHashCode(callSuper = true)
public @Data class FetchBugResp extends SyntheticBaseResponse {
	private BugType bugType;
	private Object bug;
}
