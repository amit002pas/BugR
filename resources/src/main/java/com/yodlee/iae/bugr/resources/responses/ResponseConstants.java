package com.yodlee.iae.bugr.resources.responses;

/**
 * @author Sanyam Jain
 *
 */

public final class ResponseConstants {

	private ResponseConstants() {
		//
	}

	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAILURE = "failed";
	
	public static final String FIELD_STR = "FIELD";

	public static final String MSG_CREATE_SYNTHETIC_BUG_CREATED = "Synthetic bug created";
	public static final String MSG_CREATE_SYNTHETIC_BUG_NOT_CREATED = "Synthetic bug not created";
	public static final String MSG_CREATE_SYNTHETIC_BUG_UPDATED = "Synthetic bug updated";
	public static final String MSG_CREATE_SYNTHETIC_BUG_CLOSED = "Synthetic bug closed";
	public static final String MSG_CREATE_SYNTHETIC_BUG_NOT_UPDATED = "Synthetic bug not updated";

	public static final String REQUEST_STATUS_IN_PROGRESS = "In Progress";
	public static final String REQUEST_STATUS_COMPLETED = "Completed";
	public static final String REQUEST_STATUS_CANCELLED = "Cancelled";

	public static final String BUG_CREATED_REQUEST_ACTION = "Created";
	public static final String BUG_UPDATED_REQUEST_ACTION = "Updated";

	public static final String ERRORCODE_BUGZILLAEXCEPTION = "Error 001";
	public static final String ERRORCODE_CONNECTIONEXCEPTION = "Error 002";
	public static final String ERRORCODE_IOEXCEPTION = "Error 003";
	public static final String ERRORCODE_PARSEEXCEPTION = "Error 004";
	public static final String ERRORCODE_ILLEGALSTATEEXCEPTION = "Error 005";
	public static final String ERRORCODE_GENERALEXCEPTION = "Error 006";
	public static final String ERRORCODE_SQLEXCEPTION = "Error 007";
	public static final String ERRORCODE_CLASSNOTFOUNDEXCEPTION = "Error 007";
	public static final String ERRORCODE_UNAUTHORIZEDREQUEST = "Error 401";

	public static final String MSG_ADD_ATTACHMENT_SUCCESS = "Attachment added successfully";
	public static final String MSG_ADD_ATTACHMENT_FAILED = "Attachment could not be added";
	public static final String MSG_READ_ATTACHMENT_FAILED = "There was an error while reading the Attachment";

	public static final String MSG_ADD_COMMENT_SUCCESS = "Comment added successfully";
	public static final String MSG_ADD_COMMENT_FAILED = "Comment could not be added";

	public static final String MSG_ADD_CREATE_BUG_REQUEST_TO_QUEUE_SUCCESS = "Bug request added to the queue";
	public static final String MSG_ADD_CREATE_BUG_REQUEST_IN_PROGRESS = "Bug request is in progress";
	public static final String MSG_ADD_CREATE_BUG_REQUEST_TO_QUEUE_FAILED = "Bug request could not be added to the queue";
	public static final String MSG_ADD_CREATE_BUG_REQUEST_NOT_FOUND = "Bug request not found for mentioned bugAnalyserId";

	public static final String MSG_CREATE_BUG_SUCCESS = "Bug created successfully";
	public static final String MSG_CREATE_BUG_FAILED = "Bug could not be created";
	public static final String MSG_BUG_CREATE_DUPLICATE_BUG_UPDATED = "Reported issue has been updated in the already existing bug with ID:";

	public static final String MSG_READ_BUG_ATTRIBUTES_FAILED = "Error while reading the provided bug attributes";

	public static final String MSG_UPDATE_BUG_SUCCESS = "Bug updated successfully";
	public static final String MSG_UPDATE_BUG_FAILED = "Bug could not be updated";

	public static final String MSG_BUGS_FIND_SUCCESS = "Bug(s) found";
	public static final String MSG_BUGS_FIND_FAILED = "Could not find bug(s)";

	public static final String MSG_BUGS_PARSE_FAILED = "Could not parse bug(s)";

	public static final String MSG_BUGS_CATEGORIZE_SUCCESS = "Bug(s) categorization successful";
	public static final String MSG_BUGS_CATEGORIZE_FAILED = "Bug(s) categorization failed";

	public static final String MSG_ALL_FIELDS_EMPTY_ERROR = "Provided input can't be empty/null";
	public static final String NO_BUGS_FOUND_ERROR = "Bugs are null for the searched query";
	public static final String MSG_BUG_CREATE_REQUIRED_FIELDS_EMPTY_ERROR = "product/component/summary/version can't be empty/null";
	public static final String MSG_BUG_CREATE_DUPLICATE_BUG_ERROR = "Bug already exists for the reported issue with ID:";
	public static final String MSG_SORTED_BUG_SEARCH_REQUIRED_FIELDS_EMPTY_ERROR = "numdays can't be empty/null";
	public static final String MSG_HOURLY_BUG_SEARCH_REQUIRED_FIELDS_EMPTY_ERROR = "numhours can't be empty/null";
	public static final String MSG_GET_BUG_SEARCH_REQUIRED_FIELDS_EMPTY_ERROR = "aggregation time can't be empty/null";
	public static final String MSG_BUG_DETAIL_REQUIRED_FIELDS_EMPTY_ERROR = "bugid can't be empty/null";
	public static final String MSG_ADD_ATTACHMENT_REQUIRED_FIELDS_EMPTY_ERROR = "bugid/attachment can't be empty/null";
	public static final String MSG_ADD_COMMENT_REQUIRED_FIELDS_EMPTY_ERROR = "bugid/comment can't be empty/null";
	public static final String MSG_BUG_UPDATE_REQUIRED_FIELDS_EMPTY_ERROR = "id can't be empty/null";
	public static final String MSG_SEARCH_REQUIRED_FIELDS_EMPTY_ERROR = "some of the required fields are null";
	public static final String MSG_SEARCH_REQUIRED_FIELDS_INVALID_ERROR = "numHours can't be < 0 and portfolio can't be empty/null";
	public static final String MSG_READ_SEARCH_BUG_ATTRIBUTES_FAILED = "Error while reading the provided search attributes";

	public static final String MSG_UNEXPECTED_ERROR = "Something went wrong";
	public static final String MSG_UNAUTHORIZED_ERROR = "Please provide a valid Token for authorization";

}