/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.yodlee.iae.bugr.services.prioritize;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *@author Karthik, Sanjana & Samrat
 */
@Named
public class BugPrioritizationServiceImpl{

	@Inject
	private BugPrioritizerUtility bugPrioritizerUtility;

	private Integer cobrandBasedKey = 0;
	private Integer impactBasedWeight = 0;
	private Integer keyBasedWeight = 0;
	private Integer weightComputed = 0;

	private String priority;

	private static final Logger LOG = LoggerFactory.getLogger(BugPrioritizationServiceImpl.class);

	public String getPriority(double impactPercentage, String whiteboardDetails, String customers) {

		cobrandBasedKey = weightCobrandBased(customers);
		impactBasedWeight = weightImpactBased(impactPercentage);
		keyBasedWeight = weightKeyBased(whiteboardDetails);
		weightComputed=(cobrandBasedKey+impactBasedWeight+keyBasedWeight);
		if(weightComputed>bugPrioritizerUtility.PRIORITY_ONE){
			weightComputed=bugPrioritizerUtility.PRIORITY_ONE;
		}

		for(Integer weight:bugPrioritizerUtility.getPriorityMap().keySet()){
			if(weightComputed.equals(weight)){
				priority=bugPrioritizerUtility.getPriorityMap().get(weight);
				LOG.debug("Priority to be set : " + priority);
			}
		}
		return priority;
	}

	private Integer weightKeyBased(String whiteboardDetails) {

		for(String key:bugPrioritizerUtility.getKeyWeightMap().keySet()){
			if(whiteboardDetails.contains(key)){
				keyBasedWeight=bugPrioritizerUtility.getKeyWeightMap().get(key);
			}
		}
		return keyBasedWeight;
	}

	private Integer weightImpactBased(double impactPercentage) {

		for(Integer impact:bugPrioritizerUtility.getImpactWeightMap().keySet()){
			if(impactPercentage>=impact){
				impactBasedWeight=bugPrioritizerUtility.getImpactWeightMap().get(impact);
			}
		}
		return impactBasedWeight;
	}

	private Integer weightCobrandBased(String customers) {

		for(String customer:bugPrioritizerUtility.getCobrandWeightMap().keySet()){
			if(customers.toLowerCase().contains(customer)){
				cobrandBasedKey=bugPrioritizerUtility.getCobrandWeightMap().get(customer);
			}
		}
		return cobrandBasedKey;
	}

}
