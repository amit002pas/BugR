package com.yodlee.iae.bugr.services.synthetic.fetch;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.datatypes.bugs.BugType;
import com.yodlee.iae.bugr.gateway.bugzilla.base.IBugzillaRPCClient;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.responses.FetchBugResp;
import com.yodlee.iae.bugr.resources.responses.ResponseConstants;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.utilities.EncryptionUtils;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class FetchAnyBug implements ServiceIO<String, FetchBugResp> {

	@Inject
	private MongoOperations mongoOperations;

	@Inject
	private IBugzillaRPCClient bugzillaGateway;

	private String anyId;
	private Optional<Object> bugObjectOpt;
	private FetchBugResp fetchBugResp;

	@Override
	public void accept(String obj) {
		this.anyId = obj;
	}

	@Override
	public FetchBugResp get() {
		return fetchBugResp;
	}

	@Override
	public void executeImpl() {
		Object bug;
		if (StringUtils.isNumeric(anyId)) {
			try {
				bug = bugzillaGateway.fetchBug(Integer.valueOf(anyId));
			} catch (Exception e) {
				fetchBugResp.setMessage("Bugzilla Exception occupred: " + e.getMessage());
				bug = null;
			}
		} else {
			anyId = EncryptionUtils.decryptBugAnalyserId(anyId);
			bug = mongoOperations.findById(anyId, SyntheticBug.class);
		}
		bugObjectOpt = Optional.ofNullable(bug);
	}

	@Override
	public void mapInput() {
		fetchBugResp = new FetchBugResp();
	}

	@Override
	public void mapOutput() {
		if (bugObjectOpt.isPresent()) {
			fetchBugResp.setStatus(ResponseConstants.STATUS_SUCCESS);
			Object obj = bugObjectOpt.get();
			fetchBugResp.setBug(obj);
			BugType bugT = obj instanceof SyntheticBug ? BugType.SYNTHETIC : BugType.BUGZILLA;
			fetchBugResp.setBugType(bugT);
		} else {
			fetchBugResp.setStatus(ResponseConstants.STATUS_FAILURE);
		}
	}

	@Override
	public void validate() {
		//
	}

}
