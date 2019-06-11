package com.yodlee.iae.bugr.services.synthetic.dedupe;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.ServiceMethods;
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class ReconServicesDedupeImpl implements SyntheticBugDeduper {

	@Inject
	private SyntheticBugRepository syntheticBugRepository;

	@Inject
	private MongoOperations mongoOperations;

	@Override
	public Optional<SyntheticBug> findDuplicateBug(SyntheticBug sBug) {
		BugFields synBugFields = sBug.getBugFields();
		SyntheticBug sbug = null;
		String action = null;
		if (!ValidationUtility.isNullOrEmpty(synBugFields.getErrorcode())
				&& !ValidationUtility.isNullOrEmpty(synBugFields.getWhiteboard())) {
			String whiteboard = synBugFields.getWhiteboard();
			String segID = sBug.getSyntheticFields().getSegmentId();

			if (whiteboard.contains(SynUtil.ACTION)) {
				action = whiteboard.substring(whiteboard.indexOf(SynUtil.ACTION) + SynUtil.ACTION.length());
				action = action.substring(0, action.indexOf(SynUtil.SEMI_COLON));
			}
			sbug = syntheticBugRepository.findDuplicateReconServicesBugs(synBugFields.getSuminfo(),
					synBugFields.getErrorcode(), segID, action);
		}
		return Optional.ofNullable(sbug);
	}

	@Override
	public void updateDuplicateBug(SyntheticBug sbug, SyntheticBug duplicateBug) {
		BugFields synBugFields = sbug.getBugFields();
		BugFields duplicateBugFields = duplicateBug.getBugFields();
		if (!ValidationUtility.isNullOrEmpty(synBugFields.getPriority())
				&& !ValidationUtility.isNullOrEmpty(synBugFields.getWhiteboard())) {
			duplicateBugFields.setImpact(synBugFields.getImpact());
			duplicateBugFields.setCodeReviewComments(synBugFields.getCodeReviewComments());
			duplicateBugFields.setWhiteboard(synBugFields.getWhiteboard());
			duplicateBugFields.setPriority(synBugFields.getPriority());
		}
		ServiceMethods.getUpdatedAtDate(duplicateBugFields.getImpact())
				.ifPresent(date -> duplicateBug.getSyntheticFields().setUpdatedAt(date));
		duplicateBug.setBugFields(duplicateBugFields);
		duplicateBug.setUpdatedDate(new Date());
		mongoOperations.save(duplicateBug, mongoOperations.getCollectionName(SyntheticBug.class));
	}

}
