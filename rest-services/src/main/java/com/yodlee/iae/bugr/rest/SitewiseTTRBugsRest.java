package com.yodlee.iae.bugr.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
* @author draghuvanshi
*
*/

@Path("/ttr")
public interface SitewiseTTRBugsRest {

	@GET
	@Path("/sitewisettrbugs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTTRBugs();

}
