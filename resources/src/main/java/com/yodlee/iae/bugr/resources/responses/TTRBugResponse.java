package com.yodlee.iae.bugr.resources.responses;
/**
 *@author Rajkumar Uppati
 */

import java.util.Map;

import javax.inject.Named;

import lombok.Data;

@Named
public @Data class TTRBugResponse {

	private Map<String, Integer> siteBugList;	
	private String status;
	private String message;
	private String error;

}
