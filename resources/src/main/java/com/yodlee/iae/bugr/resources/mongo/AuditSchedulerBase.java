package com.yodlee.iae.bugr.resources.mongo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "AuditScheduler")
public @Data class AuditSchedulerBase {
	@Id
	private String id;
	private Date createdDate;
	private String schedularName;
}
