/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.gateway.bugzilla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.j2bugzilla.base.BugzillaMethod;

/**
 * This class provides convenience methods for searching for {@link Bug Bugs} on your installation.
 * @author Tom and Rajkumar Uppati
 *
 */
public class BugSearch implements BugzillaMethod {

	/**
	 * The method Bugzilla will execute via XML-RPC
	 */
	private static final String METHOD_NAME = "Bug.search";
	
	/**
	 * A {@code Map} returned by the XML-RPC method.
	 */
	private Map<Object, Object> hash = new HashMap<Object, Object>();
	
	/**
	 * A {@code Map} used by the XML-RPC method containing the required object parameters.
	 */
	private final Map<Object, Object> params = new HashMap<Object, Object>();
	
	/**
	 * Defines a limit to a search for particular {@link Bug}s.
	 * 
	 * @author Tom
	 *
	 */
	public enum SearchLimiter {
		
		/**
		 * The email of the assignee
		 */
		OWNER("assigned_to"), 
		
		/**
		 * The email of the reporting user
		 */
		REPORTER("reporter"), 
		
		/**
		 * The {@link jbugz.base.Bug.Status} field value
		 */
		STATUS("status"),
		
		/**
		 * The resolution field, if the bug's status is closed. You can search
		 * for all open bugs by searching for a blank resolution.
		 */
		RESOLUTION("resolution"),
		
		/**
		 * The {@link jbugz.base.Bug.Priority} field value
		 */
		PRIORITY("priority"), 
		
		/**
		 * The product affected by this bug
		 */
		PRODUCT("product"),
		
		/**
		 * The component affected by this bug
		 */
		COMPONENT("component"),
		
		/**
		 * The operating system affected by this bug
		 */
		OPERATING_SYSTEM("op_sys"),
		
		/**
		 * The hardware affected by this bug
		 */
		PLATFORM("platform"),
		
		/**
		 * The initial summary comment
		 */
		SUMMARY("summary"),
		
		/**
		 * The version affected by this bug
		 */
		VERSION("version"),
		
		/**
		 * The unique alias for a bug
		 */
		ALIAS("alias"),
		
		/**
		 * The maximum number of bugs to return.
		 */
		LIMIT("limit"),
		
		/**
		 * An offset into bugs returned by search.
		 */
		OFFSET("offset"),
		
		/**
		 * An time stamp for which the bugs are returned by search.
		 */
		TIMESTAMP("last_change_time"),
		
		/**
		 * 	Workflow Status field of Bugzilla
		 */
		WORKFLOW_STATUS("cf_workflow_status"),
		
		/**
		 * 	Initiative field of Bugzilla
		 */
		CF_INITIATIVE("cf_initiative");
		
		private final String name;
		/**
		 * Creates a new {@link SearchLimiter} with the
		 * designated name
		 * @param name The name Bugzilla expects for this search limiter
		 */
		SearchLimiter(String name) {
			this.name = name;
		}
		/**
		 * Get the name Bugzilla expects for this search limiter
		 * @return A <code>String</code> representing the search limiter
		 */
		String getName() {
			return this.name;
		}
	}
	
	/**
	 * Creates a new {@link BugSearch} object to query the Bugzilla installation based on one or more
	 * {@link SearchQuery SearchQueries}.
	 * @param queries One or more {@code SearchQuery} objects to narrow the search by.
	 */
	public BugSearch(SearchQuery... queries) {
		if(queries.length == 0) { throw new IllegalArgumentException("At least one search query is required"); }
		for(SearchQuery query : queries) {
			params.put(query.getLimiter().getName(), query.getQuery());
		}
	}
	

	/**
	 * Returns the {@link Bug Bugs} found by the query as a <code>List</code>
	 * @return a {@link List} of {@link Bug Bugs} that match the query and limit
	 */
	public List<Bug> getSearchResults() {
		List<Bug> results = new ArrayList<Bug>();
		/*
		 * The following is messy, but necessary due to how the returned XML document nests
		 * Maps.
		 */
		if(hash.containsKey("bugs")) {
			Object[] bugs = (Object[])hash.get("bugs");
			for(Object o : bugs) {
				@SuppressWarnings("unchecked")
				Map<String, Object> bugMap = (HashMap<String, Object>)o;
				if(!bugMap.containsKey("version")) {
					Map<?, ?> internals = (Map<?, ?>) bugMap.get("internals");
					bugMap.put("version", internals.get("version"));
				}
				Bug bug = new BugFactory().createBug(bugMap);
				results.add(bug);
			}
		}
		return results;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResultMap(Map<Object, Object> hash) {
		this.hash = hash;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Object, Object> getParameterMap() {
		return Collections.unmodifiableMap(params);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMethodName() {
		return METHOD_NAME;
	}
	
	/**
	 * The {@code SearchQuery} class encapsulates a query against the bug collection on a given
	 * Bugzilla database. It consists of a limiter to apply and the value for that limiter. For
	 * example, a valid {@code SearchQuery} might consist of the limiter 
	 * {@link SearchLimiter#PRODUCT "Product"} and the query {@code "J2Bugzilla"}.
	 * 
	 * When a {@code SearchQuery} is applied within the {@link BugSearch} class, it is joined with the
	 * other queries in a logical AND. That is, bugs will be returned that match all the criteria, not
	 * any of them.
	 * 
	 * @author Tom
	 *
	 */
	public static class SearchQuery {
		
		private final SearchLimiter limiter;
		
		private final String query;
		
		/**
		 * Creates a new {@link SearchQuery} to filter the bug database through.
		 * @param limiter A {@link SearchLimiter} enum.
		 * @param query A {@code String} to filter with. The whole string will be matched, or not at all --
		 * Bugzilla does not perform substring matching.
		 */
		public SearchQuery(SearchLimiter limiter, String query) {
			this.limiter = limiter;
			this.query = query;
		}
		
		/**
		 * Returns the {@link SearchLimiter} to apply to this query.
		 * @return A facet of a bug to search against.
		 */
		public SearchLimiter getLimiter() { 
			return limiter; 
		}
		
		/**
		 * Returns the value of the specified query.
		 * @return A {@code String} to query for within the specified limiter.
		 */
		public String getQuery() { 
			return query; 
		}
	}
}
