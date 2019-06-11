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
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class ReconHoldingDedupeImpl implements SyntheticBugDeduper {

	@Inject
	private SyntheticBugRepository syntheticBugRepository;

	@Inject
	private MongoOperations mongoOperations;

	@Override
	public Optional<SyntheticBug> findDuplicateBug(SyntheticBug sBug) {
		BugFields synBugFields = sBug.getBugFields();
		SyntheticBug sbug = null;
		if (!ValidationUtility.isNullOrEmpty(synBugFields.getErrorcode())
				&& !ValidationUtility.isNullOrEmpty(synBugFields.getWhiteboard())) {
			String[] cusipSymbol = SynUtil.getCusipAndSymbol(synBugFields.getWhiteboard());
			sbug = syntheticBugRepository.findDuplicateReconHoldingBugs(cusipSymbol[0], cusipSymbol[1]);
		}
		return Optional.ofNullable(sbug);
	}

	@Override
	public void updateDuplicateBug(SyntheticBug sbug, SyntheticBug duplicateBug) {
		BugFields synBugFields = sbug.getBugFields();
		BugFields duplicateBugFields = duplicateBug.getBugFields();

		if (!ValidationUtility.isNullOrEmpty(synBugFields.getPriority())
				&& !ValidationUtility.isNullOrEmpty(synBugFields.getWhiteboard())) {
			if (!ValidationUtility.isNullOrEmpty(synBugFields.getImpact())) {
				double impactSum = SynUtil.getImpactPercentageFromString(synBugFields.getImpact())
						+ SynUtil.getImpactPercentageFromString(duplicateBugFields.getImpact());
				duplicateBugFields.setImpact(SynUtil.formImpactField(String.valueOf(impactSum)));
			}

			if (!ValidationUtility.isNullOrEmpty(synBugFields.getCodeReviewComments())) {
				String codeRevComm = synBugFields.getCodeReviewComments().trim();
				String codeRevCommDuplBug = duplicateBugFields.getCodeReviewComments();
				String crc = ValidationUtility.isNullOrEmpty(duplicateBugFields.getCodeReviewComments()) ? codeRevComm
						: SynUtil.mergeReconHoldingDetails(codeRevComm, codeRevCommDuplBug.trim());
				duplicateBugFields.setCodeReviewComments(crc);
			}

			duplicateBugFields.setWhiteboard(synBugFields.getWhiteboard());
			duplicateBugFields.setPriority(synBugFields.getPriority());
		}
		duplicateBug.setBugFields(duplicateBugFields);
		duplicateBug.setUpdatedDate(new Date());
		mongoOperations.save(duplicateBug, mongoOperations.getCollectionName(SyntheticBug.class));
	}

}
