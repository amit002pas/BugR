package com.yodlee.iae.bugr.datatypes.bugs;

import java.util.Date;

import javax.inject.Named;

import lombok.Data;

/**
 * @author KChandrarajan
 *
 */
@Named
public @Data class TimeStamp {
	private Date startTime;
	private Date endTime;
}
