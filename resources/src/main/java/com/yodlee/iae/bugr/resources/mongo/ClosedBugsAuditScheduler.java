package com.yodlee.iae.bugr.resources.mongo;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public @Data class ClosedBugsAuditScheduler extends AuditSchedulerBase {
	private List<String> syntheticBugIds;
	private Integer bugsClosed;
}
