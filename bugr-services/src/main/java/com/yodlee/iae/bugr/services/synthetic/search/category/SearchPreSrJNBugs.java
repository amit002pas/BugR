package com.yodlee.iae.bugr.services.synthetic.search.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.datatypes.bugs.Portfolio;
import com.yodlee.iae.bugr.repository.SyntheticBugRepository;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.search.cache.JNBugsCache;

/**
 * @author KChandrarajan
 *
 */
@Named
@Scope("prototype")
public class SearchPreSrJNBugs extends SearchSyntheticBugs {

	@Inject
	private JNBugsCache jnBugsCache;

	@Inject
	private SyntheticBugRepository syntheticBugRepository;

	@Override
	public Optional<List<SyntheticBug>> getBugs(BugSearchParam bugSearchParam) {
		bugSearchParam.getFilters().setSourceProduct(Portfolio.PRE_SR);
		List<SyntheticBug> sBugs = syntheticBugRepository.searchBugs(bugSearchParam);
		sBugs = sBugs.stream()
				.filter(sBug -> jnBugsCache.getCachedBugs().get().keySet().contains(sBug.getSyntheticBugid()))
				.collect(Collectors.toList());
		return Optional.ofNullable(sBugs);
	}
}
