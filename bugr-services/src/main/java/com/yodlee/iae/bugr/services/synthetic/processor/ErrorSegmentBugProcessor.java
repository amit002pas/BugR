package com.yodlee.iae.bugr.services.synthetic.processor;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.categorize.FindBugCategoryServiceImpl;
import com.yodlee.iae.bugr.services.prioritize.BugPrioritizationServiceImpl;
import com.yodlee.iae.bugr.services.utilities.ServiceMethods;
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class ErrorSegmentBugProcessor implements SyntheticBugProcessor {

	@Inject
	private BugPrioritizationServiceImpl prioritizationService;

	@Inject
	private FindBugCategoryServiceImpl categorizerService;

	@Inject
	private ServiceMethods serviceMethods;

	@Override
	public void processBugContents(SyntheticBug sBug) {
		BugFields synBugFields = sBug.getBugFields();
		if (!ValidationUtility.isNullOrEmpty(synBugFields.getComment())) {
			String bugCategory = categorizerService.getCategory(synBugFields.getComment(), synBugFields.getErrorcode());
			if (ValidationUtility.isNullOrEmpty(synBugFields.getWhiteboard()))
				synBugFields.setWhiteboard("IAE_Category:" + bugCategory);
			else {
				synBugFields.setWhiteboard(synBugFields.getWhiteboard() + ", IAE_Category:" + bugCategory);
			}
		}

		if (!ValidationUtility.isNullOrEmpty(synBugFields.getWhiteboard())
				&& !ValidationUtility.isNullOrEmpty(synBugFields.getCustomer())) {
			double impactPercentageFP = 0.0; // Impact percentage to be calculated from the impact field

			if (!ValidationUtility.isNullOrEmpty(synBugFields.getImpact())) {
				impactPercentageFP = serviceMethods.getImpactPercentage(synBugFields.getImpact().toString());
			}

			int impactPercentageInt = (int) impactPercentageFP;
			String newBugPriority = prioritizationService.getPriority(impactPercentageInt, synBugFields.getWhiteboard(),
					synBugFields.getCustomer());
			synBugFields.setPriority(newBugPriority);
		}
		ServiceMethods.getUpdatedAtDate(synBugFields.getImpact())
				.ifPresent(date -> sBug.getSyntheticFields().setUpdatedAt(date));
	}

}
