package com.yodlee.iae.bugr.services.synthetic.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.yodlee.iae.bugr.datatypes.bugs.BugEntity;

public class SyntheticBugMapperTest {

	@InjectMocks
	SyntheticBugMapper syntheticBugMapper;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testDoMapping() {
		
		List<BugEntity> bugEntityList = new ArrayList<>();
		
		BugEntity bugEntity=  new BugEntity();
		bugEntity.setStatus_whiteboard("Recon_Services");
		bugEntity.setSummary("summary");
		bugEntity.setCf_agent_status("New");
		bugEntity.setCf_suminfo("1234");
		bugEntity.setCf_memitem("2374232");
		bugEntity.setCf_errorcode("412");
		bugEntity.setCf_impact("Stacktrace: Login Page not found");
		bugEntity.setCf_service_request_id("93284");
		bugEntity.setCf_mem_site_acc_id("2342");
		bugEntity.setCf_site_id("3242");
		bugEntity.setCf_agent_version("342");
		bugEntity.setCf_code_review_comments_gen("Stacktrace: Login Page not found");
		bugEntity.setCf_readme("873123");
		bugEntity.setCf_p4_label("234123");
		bugEntityList.add(bugEntity);
		
		syntheticBugMapper.doMapping(bugEntityList);
	}
}
