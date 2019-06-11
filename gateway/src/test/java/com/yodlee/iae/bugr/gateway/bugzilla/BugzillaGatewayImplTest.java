/*package com.yodlee.iae.bugr.gateway.bugzilla;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.AttachmentFactory;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugFactory;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.Comment;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.rpc.AddAttachment;
import com.j2bugzilla.rpc.BugComments;
import com.j2bugzilla.rpc.BugSearch;
import com.j2bugzilla.rpc.CommentBug;
import com.j2bugzilla.rpc.GetAttachments;
import com.j2bugzilla.rpc.GetBug;
import com.j2bugzilla.rpc.LogIn;
import com.j2bugzilla.rpc.ReportBug;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
//
//@author Sanyam Jain
//
//@TestPropertySource("classpath:application.properties")
//@ContextConfiguration(classes = BugzillaGatewayImpl.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BugzillaGatewayImplTest {

	@InjectMocks
	BugzillaGatewayImpl bugzillagatewayImpl;

	@Mock
	BugzillaConnector bugzillaConnector;

	@Mock
	ResultSet rs;
	
	@Mock
	private BugzillaConnector conn;

	@Mock
	Statement stmt;

	@Mock
	Bug bug;

	@Mock
	ReportBug reportBug;

	@Mock
	UpdateBug updateBug;

	@Mock
	CommentBug commentBug;

	@Mock
	AddAttachment addAttachment;
	
	@Mock
	BugSearch bugSearch;
	
	@Mock
	BugComments bugComments;
	
	@Mock
	GetAttachments getAttachments;

	@Mock
	GetBug getBug;
	
	@Mock
	BugFactory bugFactory;
	
	@Mock
	LogIn login;
	
	@Value("${bugzilla.userName}")
	private String bugzillaUserName;

	@Value("${bugzilla.password}")
	private String bugzillaPassword;

	@Value("${bugzilla.url}")
	private String bugzillaHostName;

	@Before
	public void setUp() throws Exception{   
		MockitoAnnotations.initMocks(this);     
		ReflectionTestUtils.setField(bugzillagatewayImpl, "bugzillaHostName", "https://blrbugzilla.yodlee.com");
		ReflectionTestUtils.setField(bugzillagatewayImpl, "bugzillaUserName", "presruser");
		ReflectionTestUtils.setField(bugzillagatewayImpl, "bugzillaPassword", "Welcome@321");
	} 

	//
	@Test
	public void testConnectToBugzilla() throws ConnectionException, BugzillaException, FileNotFoundException, IOException {
		
		bugzillagatewayImpl.connectToBugzilla();

	}

	@Test
	public void testCreateBug() throws ConnectionException, BugzillaException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap = new HashMap<String,Object>();

		queryMap.put(BugzillaConstants.COMPONENT, "IAE-Data Agent");
		queryMap.put(BugzillaConstants.PRODUCT, "PFM");
		queryMap.put(BugzillaConstants.VERSION, "IAE-Unspecified");
		queryMap.put(BugzillaConstants.SUMMARY, "Proactive Monitoring Bugs created by PreSR AgentName: UKHalifax ErrorCode:412 COBRAND ID:5510005960");
		queryMap.put(BugzillaConstants.CF_IMPACT, "123");
		queryMap.put(BugzillaConstants.CF_WORKFLOW_STATUS, "IAE");
		queryMap.put(BugzillaConstants.CF_AGENT_VERSION, "v1");
		queryMap.put(BugzillaConstants.CF_WORKFLOW, "Test Bug");
		queryMap.put(BugzillaConstants.CF_AGENTS, "Test");
		queryMap.put(BugzillaConstants.STATUS, "NEW");
		queryMap.put(BugzillaConstants.COMMENT, "Test Bug");
		
		
		bugzillagatewayImpl.connectToBugzilla();

		bugzillagatewayImpl.createBug(queryMap);
	}

	//
	@Test
	public void testCreateBugWithBugzillaException() throws ConnectionException, BugzillaException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap = new HashMap<String,Object>();
		queryMap.put(BugzillaConstants.COMPONENT, "IAE-Data Agent");
		queryMap.put(BugzillaConstants.PRODUCT, "PFM");
		queryMap.put(BugzillaConstants.VERSION, "IAE-Unspecified");
		queryMap.put(BugzillaConstants.SUMMARY, "Proactive Monitoring Bugs created by PreSR AgentName: UKHalifax ErrorCode:412 COBRAND ID:5510005960");
		queryMap.put(BugzillaConstants.CF_IMPACT, "123");
		queryMap.put(BugzillaConstants.PRIORITY, "P3");
		queryMap.put(BugzillaConstants.CF_AGENT_VERSION, "v1");

		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(reportBug);
		bugzillagatewayImpl.createBug(queryMap);
	}

	//
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateBugWithIllegalStateException() throws ConnectionException, BugzillaException {
		when(bugzillagatewayImpl.createBug(Matchers.<Map<String, Object>>any())).thenThrow(IllegalStateException.class);
	}

	//
	@Test
	public void testCreateBugWithDefaultBugID() throws ConnectionException, BugzillaException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap = new HashMap<String,Object>();
		queryMap.put(BugzillaConstants.COMPONENT, "IAE-Data Agent");
		queryMap.put(BugzillaConstants.PRODUCT, "PFM");
		queryMap.put(BugzillaConstants.VERSION, "IAE-Unspecified");
		queryMap.put(BugzillaConstants.SUMMARY, "Proactive Monitoring Bugs created by PreSR AgentName: UKHalifax ErrorCode:412 COBRAND ID:5510005960");
		queryMap.put(BugzillaConstants.CF_IMPACT, "123");
		queryMap.put(BugzillaConstants.PRIORITY, "P3");
		queryMap.put(BugzillaConstants.CF_AGENT_VERSION, "v1");

		bugzillagatewayImpl.connectToBugzilla();
		when(reportBug.getID()).thenReturn(999004);

		bugzillagatewayImpl.createBug(queryMap);
	}


	@Test
	public void testUpdateBug() throws ConnectionException, BugzillaException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap = new HashMap<String,Object>();

		queryMap.put(BugzillaConstants.COMPONENT, "IAE-Data Agent");
		queryMap.put(BugzillaConstants.PRODUCT, "PFM");
		queryMap.put(BugzillaConstants.VERSION, "IAE-Unspecified");
		queryMap.put(BugzillaConstants.SUMMARY, "Proactive Monitoring Bugs created by PreSR AgentName: UKHalifax ErrorCode:412 COBRAND ID:5510005960");
		queryMap.put(BugzillaConstants.ID, 999004);
		queryMap.put(BugzillaConstants.CF_IMPACT, "123");
		queryMap.put(BugzillaConstants.PRIORITY, "P3");
		queryMap.put(BugzillaConstants.CF_AGENT_VERSION, "v1");
		
		bugzillagatewayImpl.connectToBugzilla();

		bugzillagatewayImpl.updateBug(queryMap);
	}

	//
	@Test
	public void testUpdateBugWithBugzillaException() throws ConnectionException, BugzillaException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap = new HashMap<String,Object>();

		queryMap.put(BugzillaConstants.COMPONENT, "IAE-Data Agent");
		queryMap.put(BugzillaConstants.PRODUCT, "PFM");
		queryMap.put(BugzillaConstants.VERSION, "IAE-Unspecified");
		queryMap.put(BugzillaConstants.SUMMARY, "Proactive Monitoring Bugs created by PreSR AgentName: UKHalifax ErrorCode:412 COBRAND ID:5510005960");
		queryMap.put(BugzillaConstants.ID, 999004);
		queryMap.put(BugzillaConstants.CF_IMPACT, "123");
		queryMap.put(BugzillaConstants.PRIORITY, "P3");
		queryMap.put(BugzillaConstants.CF_AGENT_VERSION, "v1");

		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(updateBug);
		bugzillagatewayImpl.updateBug(queryMap);
	}

	//
	@Test
	public void testUpdateBugWithIllegalStateException() throws ConnectionException, BugzillaException {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap = new HashMap<String,Object>();

		queryMap.put(BugzillaConstants.COMPONENT, "IAE-Data Agent");
		queryMap.put(BugzillaConstants.PRODUCT, "PFM");
		queryMap.put(BugzillaConstants.VERSION, "IAE-Unspecified");
		queryMap.put(BugzillaConstants.SUMMARY, "Proactive Monitoring Bugs created by PreSR AgentName: UKHalifax ErrorCode:412 COBRAND ID:5510005960");
		queryMap.put(BugzillaConstants.ID, 999004);
		queryMap.put(BugzillaConstants.CF_IMPACT, "123");
		queryMap.put(BugzillaConstants.PRIORITY, "P3");
		queryMap.put(BugzillaConstants.CF_AGENT_VERSION, "v1");

		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new IllegalStateException("must log in before using")).when(bugzillaConnector).executeMethod(updateBug);

		bugzillagatewayImpl.updateBug(queryMap);
	}

	@Test
	public void testAddBugComment() throws BugzillaException, ConnectionException {
		String bugId = "999004";
		String newComments = "Test Comments";

		bugzillagatewayImpl.connectToBugzilla();
		bugzillagatewayImpl.addBugComment(bugId, newComments);
	}


	@Test
	public void testAddBugCommentWithBugzillaException() throws BugzillaException, ConnectionException {
		String bugId = "999004";
		String newComments = "Test Comments";

		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(commentBug);

		bugzillagatewayImpl.addBugComment(bugId, newComments);
	}

	@Test
	public void testAddBugCommentWithIllegalStateException() throws BugzillaException, ConnectionException {
		String bugId = "999004";
		String newComments = "Test Comments";

		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new IllegalStateException("must log in before using")).when(bugzillaConnector).executeMethod(commentBug);

		bugzillagatewayImpl.addBugComment(bugId, newComments);
	}

	@Test
	public void testAddBugAttachment() throws BugzillaException, ConnectionException {
		String bugId = "999004";
		Attachment attachment = null;

		final Date date = new Date();
		String attachmentSummary = "Summary";
		AttachmentFactory attachmentFactory = new AttachmentFactory();
		FormDataContentDisposition fileMetaData = FormDataContentDisposition.name("testData").fileName("test.file").creationDate(date)
				.modificationDate(date).readDate(date).size(1222).build();

		byte[] bytes = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0,
				0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
				0x30, 0x30, (byte)0x9d };
		String fileName = fileMetaData.getFileName();
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeType = fileTypeMap.getContentType(fileName);

		attachment = attachmentFactory.newAttachment()
				.setData(bytes)
				.setName(fileName)
				.setMime(mimeType)	//Set the appropriate MIME type e.g. "text/html"
				.setSummary(attachmentSummary)
				.createAttachment();

		bugzillagatewayImpl.connectToBugzilla();
		bugzillagatewayImpl.addBugAttachment(bugId, attachment);
	}

	@Test
	public void testAddBugAttachmentWithBugzillaException() throws BugzillaException, ConnectionException {
		String bugId = "999004";
		Attachment attachment = null;

		final Date date = new Date();
		String attachmentSummary = "Summary";
		AttachmentFactory attachmentFactory = new AttachmentFactory();
		FormDataContentDisposition fileMetaData = FormDataContentDisposition.name("testData").fileName("test.file").creationDate(date)
				.modificationDate(date).readDate(date).size(1222).build();

		byte[] bytes = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0,
				0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
				0x30, 0x30, (byte)0x9d };
		String fileName = fileMetaData.getFileName();
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeType = fileTypeMap.getContentType(fileName);

		attachment = attachmentFactory.newAttachment()
				.setData(bytes)
				.setName(fileName)
				.setMime(mimeType)	//Set the appropriate MIME type e.g. "text/html"
				.setSummary(attachmentSummary)
				.createAttachment();

		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(addAttachment);

		bugzillagatewayImpl.addBugAttachment(bugId, attachment);
	}

	@Test
	public void testAddBugAttachmentWithIllegalStateException() throws BugzillaException, ConnectionException {
		String bugId = "999004";
		Attachment attachment = null;

		final Date date = new Date();
		String attachmentSummary = "Summary";
		AttachmentFactory attachmentFactory = new AttachmentFactory();
		FormDataContentDisposition fileMetaData = FormDataContentDisposition.name("testData").fileName("test.file").creationDate(date)
				.modificationDate(date).readDate(date).size(1222).build();

		byte[] bytes = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0,
				0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
				0x30, 0x30, (byte)0x9d };
		String fileName = fileMetaData.getFileName();
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeType = fileTypeMap.getContentType(fileName);

		attachment = attachmentFactory.newAttachment()
				.setData(bytes)
				.setName(fileName)
				.setMime(mimeType)	//Set the appropriate MIME type e.g. "text/html"
				.setSummary(attachmentSummary)
				.createAttachment();

		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new IllegalStateException("must log in before using")).when(bugzillaConnector).executeMethod(addAttachment);

		bugzillagatewayImpl.addBugAttachment(bugId, attachment);
	}

	@Test
	public void testGetBugsFromBugzilla() throws ConnectionException, BugzillaException {
		int numDays = 5;
		bugzillagatewayImpl.connectToBugzilla();
		bugzillagatewayImpl.getBugsFromBugzilla(numDays);
	}

	@Test
	public void testGetBugsFromBugzillaWithNegativeNumDays() throws ConnectionException, BugzillaException {
		int numDays = -5;
		bugzillagatewayImpl.connectToBugzilla();
		bugzillagatewayImpl.getBugsFromBugzilla(numDays);
	}

	@Test
	public void testGetBugsFromBugzillaWithBugzillaException() throws ConnectionException, BugzillaException {
		int numDays = 5;
		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(bugSearch);
		bugzillagatewayImpl.getBugsFromBugzilla(numDays);
	}

	@Test
	public void testGetBugsFromBugzillaWithIllegalStateException() throws ConnectionException, BugzillaException {
		int numDays = 5;
		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new IllegalStateException("must log in before using")).when(bugzillaConnector).executeMethod(bugSearch);
		bugzillagatewayImpl.getBugsFromBugzilla(numDays);
	}

	@Test
	public void testGetBugComments() throws BugzillaException, ConnectionException {
		bugzillagatewayImpl.connectToBugzilla();
		List<Comment> commentList = new ArrayList<>();
		when(bugComments.getComments()).thenReturn(commentList);
		bugzillagatewayImpl.getBugComments(bug);
	}

	@Test
	public void testGetBugCommentsWithBugzillaException() throws BugzillaException, ConnectionException {
		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(bugComments);
		bugzillagatewayImpl.getBugComments(bug);
	}

	@Test
	public void testGetBugCommentsWithIllegalStateException() throws BugzillaException, ConnectionException {
		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new IllegalStateException("must log in before using")).when(bugzillaConnector).executeMethod(bugComments);
		bugzillagatewayImpl.getBugComments(bug);
	}

	@Test
	public void testGetBugAttachments() throws BugzillaException, ConnectionException {
		bugzillagatewayImpl.connectToBugzilla();
		bugzillagatewayImpl.getBugAttachments(bug);
	}

	@Test
	public void testGetBugAttachmentsWithBugzillaException() throws BugzillaException, ConnectionException {
		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(getAttachments);
		bugzillagatewayImpl.getBugAttachments(bug);
	}

	@Test
	public void testGetBugAttachmentsWithIllegalStateException() throws BugzillaException, ConnectionException {
		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new IllegalStateException("must log in before using")).when(bugzillaConnector).executeMethod(getAttachments);
		bugzillagatewayImpl.getBugAttachments(bug);
	}

	@Test
	public void testFetchBug() throws ConnectionException, BugzillaException {
		bugzillagatewayImpl.connectToBugzilla();
		String bugId = "999004";
		bugzillagatewayImpl.fetchBug(bugId);
	}

	//
	@Test
	public void testFetchBugWithBugzillaException() throws ConnectionException, BugzillaException {
		bugzillagatewayImpl.connectToBugzilla();
		String bugId = "999004";
		GetBug bug = new GetBug(bugId);
		doThrow(new BugzillaException("must log in before using")).when(bugzillaConnector).executeMethod(bug);
		bugzillagatewayImpl.fetchBug(bugId);
	}

	//
	@Test
	public void testFetchBugWithIllegalStateException() throws ConnectionException, BugzillaException {
		String bugId = "999004";
		bugzillagatewayImpl.connectToBugzilla();
		doThrow(new IllegalStateException("must log in before using")).when(bugzillaConnector).executeMethod(getBug);
		bugzillagatewayImpl.fetchBug(bugId);
	}

	@Test
	public void testGetBugAdditionalInfo() throws ClassNotFoundException, SQLException {
		String agentName = "AMEX";
		bugzillagatewayImpl.getBugAdditionalInfo(agentName);
	}

	@Test
	public void testcheckSameIssueBug() throws ClassNotFoundException, SQLException {
		String agentName = "AMEX";
		String errorCode = "403";
		String exceptionName = "NoSuchElement";
		String exceptionDesc = "Element not found";
		bugzillagatewayImpl.checkSameIssueBug(agentName, errorCode, exceptionName, exceptionDesc);
	}

	//
	@Test
	public void testcheckSameIssueBugWithEmptyRS() throws ClassNotFoundException, SQLException {
		String agentName = "AMEX";
		String errorCode = "403";
		String exceptionName = "NoSuchElement";
		String exceptionDesc = "Element not found";

		when(rs.next()).thenReturn(true);
		bugzillagatewayImpl.checkSameIssueBug(agentName, errorCode, exceptionName, exceptionDesc);
	}

	//
	@Test
	public void testcheckSameIssueBugWithMySQLSyntaxErrorException() throws ClassNotFoundException, SQLException {
		String agentName = "AMEX";
		String errorCode = "403";
		String exceptionName = "NoSuchElement";
		String exceptionDesc = "Element not found";

		when(stmt.executeQuery(any(String.class))).thenThrow(new MySQLSyntaxErrorException("Exception"));
		bugzillagatewayImpl.checkSameIssueBug(agentName, errorCode, exceptionName, exceptionDesc);
	}

	@Test
	public void testDisconnectFromBugzilla() throws ConnectionException, BugzillaException {
				
		bugzillagatewayImpl.connectToBugzilla();
		bugzillagatewayImpl.disconnectFromBugzilla();
		bugzillagatewayImpl.connectToBugzilla();

	}

}
*/