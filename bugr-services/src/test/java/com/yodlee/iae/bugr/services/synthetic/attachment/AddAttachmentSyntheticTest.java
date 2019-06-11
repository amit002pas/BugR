package com.yodlee.iae.bugr.services.synthetic.attachment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.datatypes.bugs.SyntheticAttachmentRequest;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
public class AddAttachmentSyntheticTest {

	@InjectMocks
	AddAttachmentSynthetic addAttachmentSynthetic;
	
	@Mock
	private MongoOperations mongoOperations;
	
	@Mock
	private AddBugzillaAttachment addBugzillaAttachment;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAddAttachSynBug(){
		SyntheticAttachmentRequest request = new SyntheticAttachmentRequest();
		request.setBugId("12323123");
		List<AttachmentRequest> attachmentList = new ArrayList<>();
		byte[] bytestream= {107, 97, 114, 116, 104, 105, 107, 101, 121, 97, 110};
		AttachmentRequest attachmentRequest= new AttachmentRequest();
		attachmentRequest.setAttachmentName(null);
		attachmentRequest.setAttachment(bytestream);
		attachmentList.add(attachmentRequest);
		request.setAttachments(attachmentList);	
		
		SyntheticBug sbug = new SyntheticBug();
		BugFields bugFields = new BugFields();
		bugFields.setComment("As a part of Orphic testing");
		bugFields.setErrorcode("403");
		bugFields.setWhiteboard("Recon_Services;SegmentId:6310c9a1-eaee-4415-8b0b-e2e293d70919;Action:More Analysis Needed;");
		bugFields.setCustomer("All");
		bugFields.setSuminfo("4479");
		bugFields.setImpact("Total Count:249;Failure Count:124;Success Count:212;Impact Percentage: 50.0;Updated At: 2019-03-01 02:48");
		sbug.setBugFields(bugFields);
		
		SyntheticFields syntheticFields = new SyntheticFields();
		syntheticFields.setBugzillaBugCreated(true);
		sbug.setSyntheticFields(syntheticFields);
		addAttachmentSynthetic.process(request);
		
		Mockito.when(mongoOperations.findById(any(String.class),eq(SyntheticBug.class))).thenReturn(sbug);
		addAttachmentSynthetic.process(request);
	}
}
