package com.yodlee.iae.bugr.resources.responses;

import java.util.List;

import lombok.Data;

public @Data class SyntheticBaseResponse {
	private String status;
	private String message;
	private List<String> messages;
}
