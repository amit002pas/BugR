package com.yodlee.iae.bugr.services.synthetic.validation;

import javax.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:ServiceValidation.properties")
public class ServiceMessageReader {

	@Inject
	private Environment env;

	/**
	 * Return property value based on the key
	 * 
	 * @param key
	 * @return property value
	 */
	public String getPropertyByKey(String key) {
		return env.getProperty(key);
	}

}
