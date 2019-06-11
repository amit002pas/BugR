package com.yodlee.iae.bugr.rest.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.yodlee.iae.bugr.rest.SitewiseTTRBugsRest;
import com.yodlee.iae.bugr.services.synthetic.search.TTRBugSearch;

/**
* @author draghuvanshi
*
*/

@Named
@EnableAutoConfiguration
public class SitewiseTTRBugsRestImpl implements SitewiseTTRBugsRest {
	
	@Inject
	private TTRBugSearch getTTRBugs;

	@Override
	public Response getTTRBugs() {
		getTTRBugs.executeImpl();
		return Response.ok(getTTRBugs.get()).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS").header("Access-Control-Max-Age", "3600").header("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token").header("Access-Control-Expose-Headers", "xsrf-token").build();
	}

}
