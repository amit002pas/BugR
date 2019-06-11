/*
 * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */ 
package com.yodlee.iae.bugr.services.synthetic.dependson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.yodlee.iae.bugr.resources.mongo.SyntheticBug;
import com.yodlee.iae.bugr.services.synthetic.async.IAsyncSyntheticProcess;
import com.yodlee.iae.bugr.services.synthetic.base.ServiceIO;
import com.yodlee.iae.bugr.services.synthetic.search.cache.JNBugsCache;
import com.yodlee.iae.bugr.services.synthetic.search.cache.TTRBugsCache;
import com.yodlee.iae.bugr.services.synthetic.update.UpdateBugzillaBug;

@Named
public class JNTTRDependsOnUpdate implements ServiceIO<Object, Object> {

	private static final Logger LOG = LoggerFactory.getLogger(JNTTRDependsOnUpdate.class);

	@Inject
	private JNBugsCache jNBugsCache;

	@Inject
	private TTRBugsCache tTRBugsCache;

	@Inject
	private MongoOperations mongoOperations;
	
	@Inject
	private ApplicationContext ctx;

	Map<String, Set<Integer>> similarOrphicBugs;

	@Override
	public void executeImpl() {
		similarOrphicBugs.entrySet().forEach(entry -> {
			SyntheticBug synBug = mongoOperations.findById(entry.getKey(), SyntheticBug.class);
			Set<String> jnSetString = new HashSet<>();
			entry.getValue().forEach(bug -> jnSetString.add(bug.toString()));
			if(synBug.getBugFields().getDepends_on()!=null){
				String[] arrayDepends = synBug.getBugFields().getDepends_on().split(",");
				boolean toUpdate = false;
				for(String dependsString: arrayDepends){
					if(!jnSetString.contains(dependsString)){
						toUpdate = true;
					}
				}
				if(toUpdate){
					udpateMongoAndBugDB(synBug,jnSetString);
				}
			} else {
				udpateMongoAndBugDB(synBug,jnSetString);
			}
		}); 
	}

	@Override
	public void mapInput() {
		Map<String, Set<Integer>> jnSimilarOrphicBugs = jNBugsCache.getCachedBugs().get();
		Map<String, Set<Integer>> ttrSimilarOrphicBugs = tTRBugsCache.getCachedBugs().get();
		similarOrphicBugs = new HashMap<>(jnSimilarOrphicBugs);
		ttrSimilarOrphicBugs.forEach((k,v) -> similarOrphicBugs.merge(k, v, (l1,l2) -> {l1.addAll(l2); return l1;}));
	}

	private void udpateMongoAndBugDB(SyntheticBug synBug, Set<String> entry) {
		synBug.getBugFields().setDepends_on(String.join(",", entry));
		synBug.getBugFields().setComment("Updating the dependson field with the similar JN/TTR mapping");
		mongoOperations.save(synBug);
		if(synBug.getSyntheticFields().isBugzillaBugCreated()){
			try {
				IAsyncSyntheticProcess.executeAsync(ctx.getBean(UpdateBugzillaBug.class), synBug.getSyntheticBugid()).get();
			} catch (InterruptedException | ExecutionException e) {
				LOG.debug("Failed to udpate BugDB for bug : " + synBug.getSyntheticBugid());
				return;
			}
		}
	}

	@Override
	public void mapOutput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void accept(Object t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

}
