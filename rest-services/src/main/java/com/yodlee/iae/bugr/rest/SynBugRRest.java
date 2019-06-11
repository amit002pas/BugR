package com.yodlee.iae.bugr.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;

/**
 * @author KChandrarajan and Shreyas
 *
 */
@Path("/bug")
public interface SynBugRRest {

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(String bugEntityString);

	@POST
	@Path("/{bugId}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("bugId") String synBugid, String bugEntityString);

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetch(@PathParam("id") String id);

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(String bugSearchParamString);

	@POST
	@Path("/{id}/attachment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response attachment(@PathParam("id") String bugid, List<AttachmentRequest> attachments);

}