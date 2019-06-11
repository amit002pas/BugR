/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.synthetic.validation;

/**
 * @author Aojha
 *
 */
public class ServiceValidationConstants {

	/**
	 * 
	 */
	private ServiceValidationConstants() {
		//
	}

	public static final String RECON_SERVICES_SUMINFO_ERROR = "reconservices.suminfoError";
	public static final String RECON_SERVICES_CUSTOMER_ERROR = "reconservices.customerError";
	public static final String RECON_SERVICES_SEGMENT_ID_ERROR = "reconservices.segmenIdError";
	public static final String RECON_SERVICES_SEGMENT_ID_NA = "reconservices.na";
	public static final String RECON_SERVICES_SEGMENT_ID_LENGTH_ERROR = "reconservices.segmenIdLengthError";

	public static final String ERROR_SEGMENT_IMPACT_ERROR = "errorsegment.impactError";
	public static final String ERROR_SEGMENT_AGENT_ERROR = "errorsegment.agentError";
	public static final String ERROR_SEGMENT_CUSTOMER_ERROR = "reconservices.customerError";
	
	public static final String UPDATE_SAME_STATE="update.samestate";
	public static final String UPDATE_SUCCESS_TO_INPROGRESS="update.successtoinprogress";
	public static final String UPDATE_FAILURE_TO_SUCCESS="update.failuretosucess";
	public static final String UPDATE_SUCCESS_TO_FAILURE="update.successtofailure";
	public static final String UPDATE_EMPTY_TO_SUCCESS_OR_FAILURE = "update.emptytosuccessorfailure";


}
