package com.yodlee.iae.bugr.services.synthetic.dedupe;

import java.util.Optional;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class DefaultDeduperImpl implements SyntheticBugDeduper {

	@Override
	public Optional<SyntheticBug> findDuplicateBug(SyntheticBug sBug) {
		return Optional.ofNullable(null);
	}

	@Override
	public void updateDuplicateBug(SyntheticBug sbug, SyntheticBug duplicateBug) {
		// TODO: There is no default behavior as of now, please add any if required in future
	}

}
