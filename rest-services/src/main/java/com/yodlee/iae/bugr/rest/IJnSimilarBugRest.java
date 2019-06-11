package com.yodlee.iae.bugr.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author vchhetri
 *
 */
@Path("/jn")
public interface IJnSimilarBugRest {

	@POST
	@Path("/getJnSimilarClosedBugs")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getJnSimilarClosedBugs(String bugSearchParamString);

	
}
