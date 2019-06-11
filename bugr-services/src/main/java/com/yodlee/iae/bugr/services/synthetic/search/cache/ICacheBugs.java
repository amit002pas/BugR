package com.yodlee.iae.bugr.services.synthetic.search.cache;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author KChandrarajan
 *
 */
public interface ICacheBugs extends Supplier<Map<String, Set<Integer>>>, Consumer<Map<String, Set<Integer>>>, Runnable {
	

}
