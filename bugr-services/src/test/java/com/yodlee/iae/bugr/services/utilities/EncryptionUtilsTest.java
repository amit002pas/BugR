package com.yodlee.iae.bugr.services.utilities;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class EncryptionUtilsTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testDecryptBugAnalyserId() {
		EncryptionUtils.decryptBugAnalyserId("BAID-5c790490ae6f50699c87e235-BGCR");
		EncryptionUtils.encryptBugAnalyserId("5c790490ae6f50699c87e235");
		
		EncryptionUtils.decryptBugAnalyserId("");
		EncryptionUtils.encryptBugAnalyserId("");
	}
}
