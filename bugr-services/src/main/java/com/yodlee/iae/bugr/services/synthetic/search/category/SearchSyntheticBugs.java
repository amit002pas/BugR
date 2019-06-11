package com.yodlee.iae.bugr.services.synthetic.search.category;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public abstract class SearchSyntheticBugs implements ISearchBugs {

	@Value("${presr.agents}")
	protected String preSRAgents;

	@Value("${errorcodes.reconwealth}")
	protected String reconWealthErrorCodes;

	@Value("${errorcodes.reconbank}")
	protected String reconBankErrorCodes;

	@Value("${yuva.recon.agents}")
	protected String yuvaReconAgents;

}
