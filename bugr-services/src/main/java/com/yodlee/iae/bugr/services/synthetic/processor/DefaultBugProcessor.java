package com.yodlee.iae.bugr.services.synthetic.processor;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class DefaultBugProcessor implements SyntheticBugProcessor {

	@Override
	public void processBugContents(SyntheticBug sBug) {
		// TODO: There is no default behavior as of now, please add any if required in future
	}

}
