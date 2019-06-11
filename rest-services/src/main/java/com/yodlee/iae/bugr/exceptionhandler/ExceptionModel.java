package com.yodlee.iae.bugr.exceptionhandler;

import java.util.Date;
import javax.ws.rs.core.UriInfo;
import org.json.simple.JSONObject;

/**
 * 
 * @author RRaj
 * Decription: Exception Bean to return as a response to client
 *
 */
public class ExceptionModel {
	Date timeStamp;
	int status;
	String error;
	String path;
	
	public ExceptionModel(int status, UriInfo uriInfo,Throwable ex) {
		this.timeStamp=new Date();
		this.status=status;
		this.error=ex.getMessage();
		this.path=uriInfo.getAbsolutePath().getPath();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString(){
		JSONObject obj=new JSONObject();
		obj.put("timestamp", timeStamp.toString());
		obj.put("code", status);
		obj.put("message", "Failed with unseen Exception");
		obj.put("error", error);
		obj.put("path", path);
		obj.put("bugs", "null");
		obj.put("status", "failed");
		return obj.toString();
	}
}
