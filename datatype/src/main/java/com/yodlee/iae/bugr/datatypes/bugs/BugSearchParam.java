package com.yodlee.iae.bugr.datatypes.bugs;

import javax.inject.Named;

import lombok.Data;

/**
 * @author KChandrarajan
 *
 */
@Named
public @Data class BugSearchParam {
	private TimeStamp timeStamp;
	private Filters filters;
	private Sort sort;
	private Integer pageNum;
}
