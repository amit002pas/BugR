package com.yodlee.iae.bugr.services.synthetic.processor;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.prioritize.BugPrioritizationServiceImpl;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.ServiceMethods;
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class ReconServicesBugProcessor implements SyntheticBugProcessor {

	@Inject
	private BugPrioritizationServiceImpl prioritizationService;

	@Override
	public void processBugContents(SyntheticBug sBug) {
		BugFields synBugFields = sBug.getBugFields();
		if (!ValidationUtility.isNullOrEmpty(synBugFields.getWhiteboard())
				&& !ValidationUtility.isNullOrEmpty(synBugFields.getImpact())) {
			Double impactPercentage = SynUtil.calImpactPercentage(synBugFields.getImpact());
			String finalImpactString = synBugFields.getImpact() + SynUtil.SEMI_COLON
					+ SynUtil.formImpactField(String.valueOf(impactPercentage));
			synBugFields.setImpact(finalImpactString);

			String newBugPriority = prioritizationService.getPriority(impactPercentage, synBugFields.getWhiteboard(),
					synBugFields.getCustomer());
			synBugFields.setPriority(newBugPriority);
		}

		if (synBugFields.getWhiteboard().contains(SynUtil.SEGMENT_ID)
				&& !ValidationUtility.isNullOrEmpty(synBugFields.getSuminfo())) {
			String whiteboard = synBugFields.getWhiteboard();
			String segID = whiteboard;
			if (whiteboard.contains(SynUtil.ACTION)) {
				segID = segID.substring(segID.indexOf(SynUtil.SEGMENT_ID) + SynUtil.SEGMENT_ID.length(),
						segID.indexOf(SynUtil.ACTION) - 1).trim();
				String action = whiteboard.substring(whiteboard.indexOf(SynUtil.ACTION) + SynUtil.ACTION.length(),
						whiteboard.length() - 1);
				sBug.getSyntheticFields().setAction(action);
			} else {
				segID = segID
						.substring(segID.indexOf(SynUtil.SEGMENT_ID) + SynUtil.SEGMENT_ID.length(), segID.length() - 1)
						.trim();
			}
			sBug.getSyntheticFields().setSegmentId(segID);
		}
		ServiceMethods.getUpdatedAtDate(synBugFields.getImpact())
				.ifPresent(date -> sBug.getSyntheticFields().setUpdatedAt(date));
	}

}
