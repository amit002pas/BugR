package com.yodlee.iae.bugr.resources.responses;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author KChandrarajan
 *
 */
@Scope("prototype")
@Named
@EqualsAndHashCode(callSuper = true)
public @Data class SyntheticSearchBugsResponse extends SyntheticBaseResponse {
	private Integer bugCount;
	private int[] impactCount;
	private Set<String> agentValues;
	private Set<String> errorCodeValues;
	private List<SyntheticBug> bugs;
	private Map<String, Set<Integer>> similarJNBugsMap;
	private Map<String, Set<Integer>> similarTTRBugsMap;
	private Map<Integer, String> bugsAPIKeyMap;
	private String error;
	private List<Map<String, String>> csvDetails;
}