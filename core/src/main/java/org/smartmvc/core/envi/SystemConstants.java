package org.smartmvc.core.envi;

import java.util.regex.Pattern;

public class SystemConstants {
	
	/**
	 * 下标正则
	 */
	public static final Pattern SUBSCRIPTPATTERN = Pattern.compile("\\[\\d+\\]");
	
	/**
	 * 下标标记
	 */
	public static final String SUBSCRIPSIGN = "[n]";

}
