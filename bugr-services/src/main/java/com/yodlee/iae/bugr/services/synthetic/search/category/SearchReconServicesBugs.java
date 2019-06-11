package com.yodlee.iae.bugr.services.synthetic.search.category;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class SearchReconServicesBugs extends SearchSyntheticBugs {

	@Inject
	private SyntheticBugRepository syntheticBugRepository;

	@Override
	public Optional<List<SyntheticBug>> getBugs(BugSearchParam bugSearchParam) {
		List<SyntheticBug> bugList = syntheticBugRepository.searchBugs(bugSearchParam);
		return Optional.ofNullable(bugList);
	}

}
