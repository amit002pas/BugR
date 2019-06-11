package com.yodlee.iae.bugr.rest.validator;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;
import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortBy;
import com.yodlee.iae.bugr.datatypes.bugs.BugSortOrder;
import com.yodlee.iae.bugr.datatypes.bugs.Filters;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.datatypes.bugs.Sort;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.TimeStamp;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestValidatorTest {
	
	@InjectMocks
	RestValidator restValidator;
	
	@Mock
	private ValidationMessageReader validationMessageReader;
	
	@Mock
	private ValidationUtil validationUtil;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testValidateSearch() throws ParseException{
		BugSearchParam bugSearchParam= new BugSearchParam();
		TimeStamp timeStamp = new TimeStamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1="2019-02-24 14:04:33";
		String date2="2019-02-26 14:04:33";
		timeStamp.setStartTime(dateFormat.parse(date1));
		timeStamp.setEndTime(dateFormat.parse(date2));
		bugSearchParam.setTimeStamp(timeStamp);
		
		bugSearchParam.setPageNum(1);
		
		Sort sort= new Sort();
		BugSortBy bugSortBy = BugSortBy.FAILURE;
		BugSortOrder bugSortOrder = BugSortOrder.DESCENDING;
		sort.setCategory(bugSortBy);
		sort.setOrder(bugSortOrder);
		bugSearchParam.setSort(sort);
		
		Portfolio portfolio =Portfolio.PRE_SR; 
		Filters filters = new Filters();
		filters.setSourceProduct(portfolio);
		List<String> agentList = new ArrayList<>();
		agentList.add("CharlesSchwabPlan");
		List<String> errorList = new ArrayList<>();
		errorList.add("403");
		filters.setAgentNames(agentList);
		filters.setErrorCodes(errorList);
		bugSearchParam.setFilters(filters);
		
		restValidator.validateSearch(bugSearchParam);
		
		bugSearchParam.setPageNum(null);
		timeStamp.setStartTime(null);
		timeStamp.setEndTime(null);
		restValidator.validateSearch(bugSearchParam);
	}
	
	@Test
	public void testvalidateFetch(){
		String id= "1223212";
		restValidator.validateFetch(id);
		id=null;
		restValidator.validateFetch(id);
	}
	
	@Test
	public void testvalidateAttachment(){
		SyntheticAttachmentRequest request = new SyntheticAttachmentRequest();
		request.setBugId(null);
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		byte[] bytestream= {107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110};
		AttachmentRequest attachmentRequest= new AttachmentRequest();
		attachmentRequest.setAttachmentName(null);
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);
		request.setAttachments(attachmentList);	
		restValidator.validateAddAttachment(request);
		
		request.setBugId("329812");
		attachmentRequest.setAttachmentName("AuMacquire");
		attachmentRequest.setAttachment(null);
		attachmentList.add(attachmentRequest);
		request.setAttachments(attachmentList);	
		restValidator.validateAddAttachment(request);
		
		attachmentRequest.setAttachmentName(null);
		attachmentRequest.setAttachment(null);
		attachmentList.add(attachmentRequest);
		request.setAttachments(attachmentList);	
		restValidator.validateAddAttachment(request);
		
		attachmentRequest.setAttachmentName("AuMacquire");
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);
		request.setAttachments(attachmentList);	
		restValidator.validateAddAttachment(request);
		
	}
	
	@Test
	public void testvalidateUpdate(){
		BugEntity bugEntity = new BugEntity();
		bugEntity.setSynId("32487162312213");
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setBug_status("NEW");
		bugEntity.setResolution("");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setSynId(null);
		bugEntity.setComment("");
		bugEntity.setBug_status(null);
		bugEntity.setResolution("");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setSynId(null);
		bugEntity.setComment("");
		bugEntity.setBug_status("RESOLVED");
		bugEntity.setResolution("");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("");
		bugEntity.setResolution("INVALID");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("RESOLVED");
		bugEntity.setResolution("INVALID");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("ASSIGNED");
		bugEntity.setResolution("");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("ASSIGNED");
		bugEntity.setResolution("ASSIGNED");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("RESOLVED");
		bugEntity.setResolution("FIXED");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("RESOLVED");
		bugEntity.setResolution("DUPLICATE");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("RESOLVED");
		bugEntity.setResolution("MOVED");
		restValidator.validateUpdate(bugEntity);
		
		bugEntity.setBug_status("RESOLVED");
		bugEntity.setResolution("WORKSFORME");
		restValidator.validateUpdate(bugEntity);
		
	}
	
	@Test
	public void testValidateCreate(){
		BugEntity bugEntity = new BugEntity();
		
		bugEntity.setComment("");
		bugEntity.setCf_initiative("");
		bugEntity.setProduct("");
		bugEntity.setComponent("");
		bugEntity.setSummary("");
		bugEntity.setVersion("");
		bugEntity.setCf_environment("");
		bugEntity.setCf_department("");
		bugEntity.setCf_workflow("");
		bugEntity.setCf_customer("");
		bugEntity.setCf_portfolio("");
		restValidator.validateCreate(bugEntity);
		
		bugEntity.setComment("As a part of Orphic testing");
		bugEntity.setCf_initiative("IAE");
		bugEntity.setProduct("IAE");
		bugEntity.setComponent("Agent");
		bugEntity.setSummary("<Issue> Proactive monitoring created for 412");
		bugEntity.setVersion("2131");
		bugEntity.setCf_environment("Production");
		bugEntity.setCf_department("IAE");
		bugEntity.setCf_workflow("New");
		bugEntity.setCf_customer("All");
		bugEntity.setCf_portfolio("Recon_Servces");
		restValidator.validateCreate(bugEntity);
		
		bugEntity.setComment("As a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testingAs a part of Orphic testing");
		bugEntity.setSynId("32487162312213");
		restValidator.validateCreate(bugEntity);
		
		
	}
	

}
