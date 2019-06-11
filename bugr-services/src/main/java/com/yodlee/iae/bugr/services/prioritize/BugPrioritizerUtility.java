/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */

package com.yodlee.iae.bugr.services.prioritize;

import java.util.HashMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
/**
 *@author Karthik, Sanjana & Samrat
 */
@Named
public class BugPrioritizerUtility {

	@Value("${weight.key.VIP}")
	private Integer VIP;

	@Value("${weight.key.preSR}")
	private Integer preSR;

	@Value("${weight.key.postSR}")
	private Integer postSR;

	@Value("${weight.key.TTR}")
	private Integer TTR;

	@Value("${weight.cobrand.bofa}")
	private  Integer bofa;

	@Value("${priority.p1}")
	private  String p1;

	@Value("${priority.p2}")
	private  String p2;

	@Value("${priority.p3}")
	private  String p3;

	@Value("${priority.p4}")
	private  String p4;

	@Value("${priority.p5}")
	private  String p5;

	@Value("${weight.impact.zero-five}")
	private  Integer zeroToFive;

	@Value("${weight.impact.five-ten}")
	private  Integer fiveToTen;

	@Value("${weight.impact.ten-thirty}")
	private  Integer tenToThirty;

	@Value("${weight.impact.thirty-fifty}")
	private  Integer thirtyToFifty;

	@Value("${weight.impact.fifty-more}")
	private  Integer moreThanFifty;

	private HashMap<String, Integer> keyWeightMap = null;
	private  HashMap<String, Integer> cobrandWeightMap = null;
	private  HashMap<Integer, String> priorityMap = null;
	private  TreeMap<Integer, Integer> impactWeightMap = null;
	private  HashMap<String,Integer> priorityInputMap = null;

	public final Integer PRIORITY_ONE=5;
	public final Integer PRIORITY_TWO=4;
	public final Integer PRIORITY_THREE=3;
	public final Integer PRIORITY_FOUR=2;
	public final Integer PRIORITY_FIVE=1;

	private  Integer ZEROTOFIVE_IMPACT=0;
	private  Integer FIVETOTEN_IMPACT=5;
	private  Integer TENTOTHIRTY_IMPACT=10;
	private  Integer THIRTYTOFIFTY_IMPACT=30;
	private  Integer MORETHANFIFTY_IMPACT=50;

	private String TTR_KEY="TTR";
	private String VIP_KEY="VIP";
	private String PRESR_KEY="PreSR";
	private String POSTSR_KEY="IAT";

	private String BOFA_COBRAND_bofa="bofa";
	private String BOFA_COBRAND_bofa2="bank of america";

	@PostConstruct
	public void initializeValues()
	{
		cobrandWeightMap = new HashMap<String, Integer>();
		keyWeightMap = new HashMap<String, Integer>();
		impactWeightMap = new TreeMap<Integer, Integer>();
		priorityInputMap = new HashMap<String,Integer>();
		priorityMap = new HashMap<Integer, String>();

		keyWeightMap.put(TTR_KEY, TTR);
		keyWeightMap.put(POSTSR_KEY, postSR);
		keyWeightMap.put(PRESR_KEY, preSR);
		keyWeightMap.put(VIP_KEY, VIP);

		cobrandWeightMap.put(BOFA_COBRAND_bofa, bofa);
		cobrandWeightMap.put(BOFA_COBRAND_bofa2, bofa);

		priorityMap.put(PRIORITY_ONE, p1);
		priorityMap.put(PRIORITY_TWO, p2);
		priorityMap.put(PRIORITY_THREE, p3);
		priorityMap.put(PRIORITY_FOUR, p4);
		priorityMap.put(PRIORITY_FIVE, p5);

		priorityInputMap.put(p1,PRIORITY_ONE);
		priorityInputMap.put(p2,PRIORITY_TWO);
		priorityInputMap.put(p3,PRIORITY_THREE);
		priorityInputMap.put(p4,PRIORITY_FOUR);
		priorityInputMap.put(p5,PRIORITY_FIVE);

		impactWeightMap.put(MORETHANFIFTY_IMPACT, moreThanFifty);
		impactWeightMap.put(THIRTYTOFIFTY_IMPACT, thirtyToFifty);
		impactWeightMap.put(TENTOTHIRTY_IMPACT, tenToThirty);
		impactWeightMap.put(FIVETOTEN_IMPACT, fiveToTen);
		impactWeightMap.put(ZEROTOFIVE_IMPACT, zeroToFive);

	}

	public TreeMap<Integer, Integer> getImpactWeightMap()
	{
		return impactWeightMap;
	}

	public HashMap<String,Integer> getPriorityInputMap()
	{
		return priorityInputMap;
	} 

	public HashMap<String, Integer> getCobrandWeightMap(){
		return cobrandWeightMap;
	}

	public HashMap<String, Integer> getKeyWeightMap(){
		return keyWeightMap;
	}

	public HashMap<Integer, String> getPriorityMap()
	{
		return priorityMap;
	}



}
