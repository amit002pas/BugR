/*package com.yodlee.iae.bugr.services.attachment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.data.mongodb.core.MongoOperations;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.AttachmentFactory;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.yodlee.iae.bugr.datatypes.bugs.AttachmentRequest;
import com.yodlee.iae.bugr.gateway.bugzilla.BugzillaGatewayImpl;
import com.yodlee.iae.bugr.persistence.audit.AuditAddAttachment;
import com.yodlee.iae.bugr.resources.responses.BugResponse;

*//**
 *@author Sanyam Jain
 *//*
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(PowerMockRunner.class)
public class AddAttachmentServiceImplTest {

	@InjectMocks
	private AddAttachmentServiceImpl addAttachmentService;

	@Mock
	BugzillaGatewayImpl bugzillaGatewayImpl;

	@Mock
	BugResponse response;

	@Mock
	AttachmentFactory attachmentFactory;

	@Mock
	AuditAddAttachment addAttachment;

	@Mock
	MongoOperations mongoOperations;

	@Before
	public void setUp() throws Exception{   
		MockitoAnnotations.initMocks(this);     
	}  

	@Test
	public void testMapInputValidParams() throws IOException, ParseException{
		String bugId = "999004";
		AttachmentRequest[] attachmentsList = new AttachmentRequest[100];
		AttachmentRequest attachment = new AttachmentRequest();
		attachment.setAttachmentName("test.pdf");
		PowerMockito.mockStatic(IOUtils.class);
		InputStream fileInputStream = IOUtils.toInputStream("Some text file", "UTF-8");
		attachment.setAttachment(IOUtils.toByteArray(fileInputStream));
		attachmentsList[0] = attachment;
		addAttachmentService.mapInput(bugId, attachmentsList);
	}

	@SuppressWarnings("unchecked")
	@PrepareForTest({ IOUtils.class })
	@Test
	public void testMapInputWithIOException() throws IOException, ParseException{
		String bugId = "999004";
		AttachmentRequest[] attachmentsList = new AttachmentRequest[100];
		AttachmentRequest attachment = new AttachmentRequest();
		attachment.setAttachmentName("test.pdf");
		PowerMockito.mockStatic(IOUtils.class);
		when(IOUtils.toByteArray(any(InputStream.class))).thenThrow(IOException.class);
		InputStream fileInputStream = IOUtils.toInputStream("Some text file", "UTF-8");
		attachment.setAttachment(IOUtils.toByteArray(fileInputStream));
		attachmentsList[0] = attachment;
		addAttachmentService.mapInput(bugId, attachmentsList);
	}

	@Test
	public void testValidateInput() throws IOException, ParseException{
		String bugId = "999004";
		AttachmentRequest[] attachmentsList = new AttachmentRequest[100];
		AttachmentRequest attachment = new AttachmentRequest();
		attachment.setAttachmentName("test.pdf");
		PowerMockito.mockStatic(IOUtils.class);
		InputStream fileInputStream = IOUtils.toInputStream("Some text file", "UTF-8");
		attachment.setAttachment(IOUtils.toByteArray(fileInputStream));
		attachmentsList[0] = attachment;
		addAttachmentService.mapInput(bugId, attachmentsList);

		int validation = addAttachmentService.validateInput();
		assertEquals(0, validation);		
	}

	@Test
	public void testValidateInputWithNullBugID() throws IOException, ParseException{
		String bugId = null;
		AttachmentRequest[] attachmentsList = new AttachmentRequest[100];
		AttachmentRequest attachment = new AttachmentRequest();
		attachment.setAttachmentName("test.pdf");
		PowerMockito.mockStatic(IOUtils.class);
		InputStream fileInputStream = IOUtils.toInputStream("Some text file", "UTF-8");
		attachment.setAttachment(IOUtils.toByteArray(fileInputStream));
		attachmentsList[0] = attachment;
		addAttachmentService.mapInput(bugId, attachmentsList);

		int validation = addAttachmentService.validateInput();
		assertEquals(-1, validation);		
	}

	@Test
	public void testValidateInputWithNullAttachmentSummary() throws IOException, ParseException{
		String bugId = "999004";
		AttachmentRequest[] attachmentsList = new AttachmentRequest[100];
		AttachmentRequest attachment = new AttachmentRequest();
		attachment.setAttachmentName(null);
		PowerMockito.mockStatic(IOUtils.class);
		InputStream fileInputStream = IOUtils.toInputStream("Some text file", "UTF-8");
		attachment.setAttachment(IOUtils.toByteArray(fileInputStream));
		attachmentsList[0] = attachment;
		addAttachmentService.mapInput(bugId, attachmentsList);

		int validation = addAttachmentService.validateInput();
		assertEquals(-1, validation);		
	}

	@PrepareForTest({ IOUtils.class })
	@Test
	public void testValidateInputWithNullAttachments() throws IOException, ParseException{
		String bugId = "999004";
		addAttachmentService.mapInput(bugId, null);
		int validation = addAttachmentService.validateInput();
		assertEquals(-1, validation);		
	}

	@Test
	public void testExecuteImpl() throws BugzillaException, ConnectionException, IOException{
		String attachmentId = "567";
		String bugId = "999004";
		AttachmentRequest[] attachmentsList = new AttachmentRequest[100];
		AttachmentRequest attachment = new AttachmentRequest();
		attachment.setAttachmentName("test.pdf");
		PowerMockito.mockStatic(IOUtils.class);
		InputStream fileInputStream = IOUtils.toInputStream("Some text file", "UTF-8");
		attachment.setAttachment(IOUtils.toByteArray(fileInputStream));
		attachmentsList[0] = attachment;
		addAttachmentService.mapInput(bugId, attachmentsList);
		
		when(bugzillaGatewayImpl.addBugAttachment(any(String.class), any(Attachment.class))).thenReturn(attachmentId);
		addAttachmentService.executeImpl();
	}

	@Test
	public void testExecuteImplWithNullAttachmentID() throws BugzillaException, ConnectionException{
		String attachmentId = null;
		when(bugzillaGatewayImpl.addBugAttachment(any(String.class), any(Attachment.class))).thenReturn(attachmentId);
		addAttachmentService.executeImpl();
	}

	@Test
	public void testExecuteImplWithEmptyAttachmentID() throws BugzillaException, ConnectionException{
		String attachmentId = "";
		when(bugzillaGatewayImpl.addBugAttachment(any(String.class), any(Attachment.class))).thenReturn(attachmentId);
		addAttachmentService.executeImpl();
	}

	@Test
	public void testExecuteImplWithDefaultAttachmentID() throws BugzillaException, ConnectionException{
		String attachmentId = "-1";
		when(bugzillaGatewayImpl.addBugAttachment(any(String.class), any(Attachment.class))).thenReturn(attachmentId);
		addAttachmentService.executeImpl();
	}

	@Test
	public void testExecuteImplWithConnectionException() throws BugzillaException, ConnectionException{
		when(bugzillaGatewayImpl.addBugAttachment(any(String.class), any(Attachment.class))).thenThrow(new ConnectionException("Exception", null));
		addAttachmentService.executeImpl();
	}

	@Test
	public void testExecuteImplWithBugzillaException() throws BugzillaException, ConnectionException{
		when(bugzillaGatewayImpl.addBugAttachment(any(String.class), any(Attachment.class))).thenThrow(new BugzillaException("Exception"));
		addAttachmentService.executeImpl();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExecuteImplWithException() throws BugzillaException, ConnectionException{
		when(bugzillaGatewayImpl.addBugAttachment(any(String.class), any(Attachment.class))).thenThrow(Exception.class);
		addAttachmentService.executeImpl();
	}

	@Test
	public void testGetOutput() throws BugzillaException, ConnectionException{
		when(bugzillaGatewayImpl.addBugComment(any(String.class), any(String.class))).thenReturn("124346");
		addAttachmentService.executeImpl();
		addAttachmentService.getOutput();
		//assertEquals(ResponseConstants.STATUS_SUCCESS, output.getStatus());
	}

	@Test
	public void testEmptyMethods(){
		addAttachmentService.mapInput();
		addAttachmentService.mapOutput();
		addAttachmentService.validate();
	}
}
*/