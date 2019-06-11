package com.yodlee.iae.bugr.rest.validator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Class reads the property values from the StepLogMessages.properties file
 * 
 * @author Shreyas
 *
 */
@Configuration
@PropertySource("classpath:Validation.properties")
public class ValidationMessageReader {

	@Inject
	private Environment env;

	/**
	 * Return a list of property value based on the key
	 * 
	 * @param key
	 * @return property value
	 */
	public List<String> getPropertyByKey(List<String> key) {
		List<String> strList = new ArrayList<>();
		for (int i = 0; i < key.size(); i++)
			strList.add(env.getProperty(key.get(i)));
		return strList;
	}

}
