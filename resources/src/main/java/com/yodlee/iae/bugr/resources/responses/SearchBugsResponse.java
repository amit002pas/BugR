package com.yodlee.iae.bugr.resources.responses;

import java.util.List;
import java.util.Map;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;
import com.j2bugzilla.base.Bug;
import lombok.Data;
/**
 * @author Sanyam Jain
 *
 */
@Scope("prototype")
@Named
public @Data class SearchBugsResponse {

	private String status;
	private String message;
	private List<String> messages;
	private List<Bug> bugs;
	private List<String> agentValues;
	private List<String> errorCodeValues;
	private String error;
	private int bugCount;
	private Map<Integer,List<Integer>> similarJNBugsMap;
	private Map<Integer,List<Integer>> similarTTRBugsMap;
	private Map<Integer, String> bugsAPIKeyMap;
}