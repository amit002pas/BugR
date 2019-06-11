/**
 * 
 */
package com.yodlee.iae.bugr.rest.impl;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;
import com.yodlee.iae.bugr.resources.responses.SyntheticBaseResponse;
import com.yodlee.iae.bugr.rest.IJnSimilarBugRest;
import com.yodlee.iae.bugr.rest.handler.HandleRest;
import com.yodlee.iae.bugr.services.jn.jnsimialrbug.JnSimilarClosedBug;

/**
 * @author vchhetri
 *
 */
@Named
@EnableAutoConfiguration
public class JnSimilarBugRestImpl implements IJnSimilarBugRest {

	@Inject
	private JnSimilarClosedBug jnSimilarClosedBug;

	@Override
	public Response getJnSimilarClosedBugs(String param) {
		JSONObject jsonObj;
		Date startTime = null;
		Date endTime = null;
		TimeStamp ts = null;
		try {
			jsonObj = new JSONObject(param);
			startTime = new Date(Long.parseLong(jsonObj.get("startTime").toString()));
			endTime = new Date(Long.parseLong(jsonObj.get("endTime").toString()));
			ts = new TimeStamp();
			ts.setStartTime(startTime);
			ts.setEndTime(endTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return HandleRest.apply(jnSimilarClosedBug, ts, new SyntheticBaseResponse());
	}

}
