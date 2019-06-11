package com.yodlee.iae.bugr.services.synthetic.processor;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.google.gson.JsonParser;
import com.yodlee.iae.bugr.resources.mongo.BugFields;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.resources.mongo.SyntheticFields;
import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;
import com.yodlee.iae.bugr.services.utilities.ValidationUtility;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class ReconHoldingBugProcessor implements SyntheticBugProcessor {

	private JsonParser jsonParser = new JsonParser();

	@Override
	public void processBugContents(SyntheticBug sBug) {
		BugFields bugFields = sBug.getBugFields();
		SyntheticFields synFields = sBug.getSyntheticFields();
		if (!ValidationUtility.isNullOrEmpty(bugFields.getWhiteboard())
				&& !ValidationUtility.isNullOrEmpty(bugFields.getImpact())) {
			bugFields.setPriority(SynUtil.NEW_PRIORITY);
			bugFields.setImpact(SynUtil.formImpactField(bugFields.getImpact()));
			bugFields.setCodeReviewComments(
					jsonParser.parse(bugFields.getCodeReviewComments()).getAsJsonObject().toString());
			String[] cusipSymbol = SynUtil.getCusipAndSymbol(bugFields.getWhiteboard().trim());
			synFields.setCusip(cusipSymbol[0]);
			synFields.setSymbol(cusipSymbol[1]);
		}

	}
}
