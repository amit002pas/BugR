package com.yodlee.iae.bugr.services.synthetic.search.category;

import java.util.List;
import java.util.Optional;

import com.yodlee.iae.bugr.datatypes.bugs.BugSearchParam;
import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;

/**
 * @author KChandrarajan
 *
 */
public interface ISearchBugs {

	/**
	 * Search bugs based on the Portfolio category
	 * 
	 * @param bugSearchParam
	 * @return
	 */
	public Optional<List<SyntheticBug>> getBugs(BugSearchParam bugSearchParam);

}
