package com.yodlee.iae.bugr.rest.handler;

import javax.ws.rs.core.Response;

import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.resources.responses.SyntheticBaseResponse;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

public interface HandleRest {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response apply(ServiceIO serviceIOImpl, Object input, SyntheticBaseResponse validationResponse) {
		if (ResponseConstants.STATUS_FAILURE.equals(validationResponse.getStatus())) {
			return SynUtil.generateResponse(validationResponse);
		}
		return SynUtil.generateResponse(serviceIOImpl.process(input));
	}
}
