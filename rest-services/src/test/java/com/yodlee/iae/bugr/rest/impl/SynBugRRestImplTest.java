package com.yodlee.iae.bugr.rest.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Matchers.any;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.resources.responses.SyntheticBaseResponse;
import com.yodlee.iae.bugr.rest.handler.HandleRest;
import com.yodlee.iae.bugr.rest.validator.RestValidator;
import com.yodlee.iae.bugr.services.synthetic.attachment.AddAttachmentSynthetic;
import com.yodlee.iae.bugr.services.synthetic.create.CreateBugzillaBug;
import com.yodlee.iae.bugr.services.synthetic.create.CreateSyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.fetch.FetchAnyBug;
import com.yodlee.iae.bugr.services.synthetic.search.SyntheticBugSearch;
import com.yodlee.iae.bugr.services.synthetic.update.UpdateSyntheticBug;

public class SynBugRRestImplTest {
	
	@InjectMocks
	private SynBugRRestImpl synBugRRestImpl;
	
	@Mock 
	private FetchAnyBug fetchAnyBug;
	
	@Mock
	private RestValidator restValidator;
	
	@Mock
	private HandleRest handleRest;
	
	@Mock
	private SyntheticBugSearch syntheticBugSearch;

	@Mock
	private CreateSyntheticBug createSyntheticBug;

	@Mock
	private UpdateSyntheticBug updateSyntheticBug;

	@Mock
	private AddAttachmentSynthetic addAttachmentSynthetic;

	@Mock
	private CreateBugzillaBug createBugzillaBug;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFetch(){
		String id="21321342";
		SyntheticBaseResponse syntheticBaseResponse = new SyntheticBaseResponse();
		syntheticBaseResponse.setStatus("failed");
		Mockito.when(restValidator.validateFetch(any(String.class))).thenReturn(syntheticBaseResponse);
		synBugRRestImpl.fetch(id);
	}
	
	@Test
	public void testSearch() throws Exception{
		String bugSearchParamString="{\"timeStamp\":{\"startTime\":\"2019-02-18 03:39:59\",\"endTime\":\"2019-02-19 03:39:59\"},\"filters\":{\"keyword\":\"\",\"whiteboard\":\"\",\"summary\":\"\",\"siteid\":\"\",\"sourceProduct\":\"PRE_SR\",\"agentNames\":[],\"errorCodes\":[]},\"sort\":{\"category\":\"FAILURE\",\"order\":\"DESCENDING\"},\"pageNum\":1}";
		SyntheticBaseResponse syntheticBaseResponse = new SyntheticBaseResponse();
		syntheticBaseResponse.setStatus("Success");
		Mockito.when(restValidator.validateSearch(any(BugSearchParam.class))).thenReturn(syntheticBaseResponse);
		synBugRRestImpl.search(bugSearchParamString);
	}
	
	@Test
	public void testCreate() throws Exception{
		String bugEntityString="{\r\n" + 
				"  \"cf_customer\": \"All\",\r\n" + 
				"  \"cf_memitem\": \"71588032\",\r\n" + 
				"  \"cf_department\": \"IAE\",\r\n" + 
				"  \"rep_platform\": \"All\",\r\n" + 
				"  \"cf_site_id\": \"8827\",\r\n" + 
				"  \"cf_backend_frontend\": \"Internal Testing\",\r\n" + 
				"  \"cf_agents\": \"SecurityServiceFCU\",\r\n" + 
				"  \"cf_portfolio\": \"Proactive Monitoring\",\r\n" + 
				"  \"summary\": \"Proactive Monitoring Bugs created by PreSR AgentName: SecurityServiceFCU ErrorCode:403 COBRAND ID:41610008380\",\r\n" + 
				"  \"product\": \"Proactive Monitoring\",\r\n" + 
				"  \"status_whiteboard\": \"PreSR_ErrorSegment\",\r\n" + 
				"  \"cf_impact\": \"Refresh Count: 0;Failure Count: 7;Predicted Failure: 0;Impact Percentage: 0.25;Updated At: 2019-02-22 14:21\",\r\n" + 
				"  \"cf_suminfo\": \"11013\",\r\n" + 
				"  \"cf_code_review_comments_gen\": \"Stack Trace:com.yodlee.dap.gatherer.gather.exceptions.GeneralException: Exception while parsingDEBIT CARD DEBIT 2674PEPSIVEN*7678 AURORA IL	at SecurityServiceFCU.getTransactionDetails(SecurityServiceFCU.java:7226)	at SecurityServiceFCU.grabTransactions(SecurityS||{}\",\r\n" + 
				"  \"cf_errorcode\": \"413\",\r\n" + 
				"  \"cf_initiative\": \"IAE\",\r\n" + 
				"  \"priority\": \"P3\",\r\n" + 
				"  \"version\": \"unspecified\",\r\n" + 
				"  \"cf_workflow\": \"IAE\",\r\n" + 
				"  \"op_sys\": \"Windows\",\r\n" + 
				"  \"cf_environment\": \"Production\",\r\n" + 
				"  \"component\": \"Agent\",\r\n" + 
				"  \"cf_bugtype\": \"Proactive\",\r\n" + 
				"  \"createBugzillaBug\": \"false\",\r\n" + 
				"  \"cf_workflow_status\": \"NEW\",\r\n" + 
				"  \"comment\": \"Comment as a part of Orphic testing.\"\r\n" + 
				"}";
		SyntheticBaseResponse syntheticBaseResponse = new SyntheticBaseResponse();
		syntheticBaseResponse.setStatus("Success");
		Mockito.when(restValidator.validateCreate(any(BugEntity.class))).thenReturn(syntheticBaseResponse);
		synBugRRestImpl.create(bugEntityString);
		
		bugEntityString="{\r\n" + 
				"	\"synId\":\"4328347689217319283\"\r\n" + 
				"}";
		synBugRRestImpl.create(bugEntityString);
	}
	
	@Test
	public void testUpdate() throws Exception{
		String synBugId="2asdadcasdjdas1313";
		String bugEntityString="{\r\n" + 
				"  \"cf_workflow_status\": \"Closed\",\r\n" + 
				"  \"bug_status\": \"RESOLVED\",\r\n" + 
				"  \"resolution\": \"INVALID\",\r\n" + 
				"  \"comment\": \"Testing from Orphic\"\r\n" + 
				"}";
		SyntheticBaseResponse syntheticBaseResponse = new SyntheticBaseResponse();
		syntheticBaseResponse.setStatus("Success");
		Mockito.when(restValidator.validateUpdate(any(BugEntity.class))).thenReturn(syntheticBaseResponse);
		synBugRRestImpl.update(synBugId,bugEntityString);
	}
	
	@Test
	public void testAddAttachment() throws Exception{
		String bugId="213213";
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		byte[] bytestream= {107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110};
		AttachmentRequest attachmentRequest= new AttachmentRequest();
		attachmentRequest.setAttachmentName(null);
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);
		SyntheticBaseResponse syntheticBaseResponse = new SyntheticBaseResponse();
		syntheticBaseResponse.setStatus("Success");
		Mockito.when(restValidator.validateAddAttachment(any(SyntheticAttachmentRequest.class))).thenReturn(syntheticBaseResponse);
		synBugRRestImpl.attachment(bugId, attachmentList);
		}

}
