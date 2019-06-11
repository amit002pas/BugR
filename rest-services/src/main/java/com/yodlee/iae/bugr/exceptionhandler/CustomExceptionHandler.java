package com.yodlee.iae.bugr.exceptionhandler;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author RRaj
 * Description: Class to route all exception here and to return a JSON object 
 *
 */
@Provider
public class CustomExceptionHandler implements ExceptionMapper<Throwable> {
	 
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	    @Context private UriInfo uriInfo;
	    @Context private HttpServletResponse response;
		public Response toResponse(Throwable ex) {
			if(ex instanceof InternalServerErrorException) {
				response.setStatus(500);
			}else {
				response.setStatus(404);
			}
			ExceptionModel model=new ExceptionModel(response.getStatus(), uriInfo, ex);
			logger.warn(model.toString());
			return Response.status(response.getStatus()).entity(model.toString()).type(MediaType.APPLICATION_JSON).build();
		}
}

