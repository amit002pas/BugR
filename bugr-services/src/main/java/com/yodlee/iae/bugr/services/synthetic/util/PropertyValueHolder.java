package com.yodlee.iae.bugr.services.synthetic.util;
public class PropertyValueHolder {
	public static final String HOURS = "#{new Integer('${syncSyntheticBug.hour}')}";
	public static final String DAYS = "#{new Integer('${syncSyntheticBug.Days}')}";

}
