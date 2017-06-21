package org.smartmvc.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.smartmvc.core.annotation.em.ResponseType;

/**
 * 系统配置
 * 
 * @author ZHANGYUKUN
 *
 */
public class SystemConfiguration {
	
	
	/**
	 * 默认的时间格式 
	 */
	private static DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 *控制器的包名
	 */
	private static String packageName = "org.smartmvc.controller";
	
	/**
	 * 指定默认的视图格式
	 */
	private static ResponseType defaultViewPostfix = ResponseType.JSP;
	

	public static String getPackageName() {
		return packageName;
	}

	public static void setPackageName(String packageName) {
		SystemConfiguration.packageName = packageName;
	}

	public static String getDefaultViewPostfix() {
		return defaultViewPostfix.getPostfix();
	}

	public static DateFormat getDefaultDateFormat() {
		return SystemConfiguration.defaultDateFormat;
	}

	public static void setDefaultDateFormat(SimpleDateFormat defaultDateFormat) {
		SystemConfiguration.defaultDateFormat = defaultDateFormat;
	}

}
