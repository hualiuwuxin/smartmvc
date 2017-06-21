package org.smartmvc.core.envi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.core.converter.Converter;
import org.smartmvc.core.exception.ExceptionHandler;
import org.smartmvc.core.interceptor.InterceptorChain;

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
	private static String packageName;
	
	/**
	 * 指定默认的视图格式
	 */
	private static ResponseType defaultViewPostfix;

	/**
	 * 是否初始化第一层参数(决定第一层参数，如果没有对应的请求key是null值还是默认初始对象)
	 * @author ZHANGYUKUN
	 */
	private static boolean firstFloorArgumentInit;
	
	/**
	 * 过滤器链(全局)
	 */
	private static final InterceptorChain INTERCEPTORCHAIN = new InterceptorChain();
	
	/**
	 * 转化器
	 */
	private static final Map<Class<?>,Converter<?>> CONVERTER = new HashMap<>();
	
	/**
	 * 统一异常处理接口
	 */
	private static  ExceptionHandler exceptionHandler;
	

	public static String getPackageName() {
		return packageName;
	}

	public static void setPackageName(String packageName) {
		SystemConfiguration.packageName = packageName;
	}

	public static ResponseType getDefaultViewPostfix() {
		return defaultViewPostfix;
	}
	
	public static void setDefaultViewPostfix(ResponseType defaultViewPostfix) {
		SystemConfiguration.defaultViewPostfix = defaultViewPostfix;
	}


	public static DateFormat getDefaultDateFormat() {
		return SystemConfiguration.defaultDateFormat;
	}

	public static void setDefaultDateFormat(SimpleDateFormat defaultDateFormat) {
		SystemConfiguration.defaultDateFormat = defaultDateFormat;
	}

	public static boolean isFirstFloorArgumentInit() {
		return firstFloorArgumentInit;
	}

	public static void setFirstFloorArgumentInit(boolean firstFloorArgumentInit) {
		SystemConfiguration.firstFloorArgumentInit = firstFloorArgumentInit;
	}

	public static InterceptorChain getInterceptorChain() {
		return INTERCEPTORCHAIN;
	}

	public static Map<Class<?>,Converter<?>> getConverter() {
		return CONVERTER;
	}

	public static ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public static void setExceptionHandler(ExceptionHandler exceptionHandler) {
		SystemConfiguration.exceptionHandler = exceptionHandler;
	}


}
