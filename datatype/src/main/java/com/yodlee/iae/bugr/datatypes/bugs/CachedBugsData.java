package com.yodlee.iae.bugr.datatypes.bugs;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.j2bugzilla.base.Bug;

import lombok.Data;
/**
 * @author Sanyam Jain
 *
 */
@Named
public @Data class CachedBugsData {
	
	private List<Bug> bugs = null;
	private Map<Integer, List<Integer>> similarJNBugsMap = null;
	private Map<Integer, List<Integer>> similarTTRBugsMap = null;
	
}
