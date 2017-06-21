package org.smartmvc.core.tools;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodTools {
	
	/**
	 * 这个方法是不是set 方法
	 * 
	 * @author ZHANGYUKUN
	 * @param method
	 * @return
	 */
	public static boolean isSetMethod(Method method) {
		if (method.getName().startsWith("set")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 得到 set 推测的 字段名
	 * 
	 * @author ZHANGYUKUN
	 * @param method
	 * @return
	 */
	public static String getSetMethodFieldName(Method method) {
		String methodName = method.getName();
		String fieldName = "";
		if (methodName.length() > 3) {
			fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
		}
		return fieldName;
	}
	
	/**
	 * 得到方法参数唯一标志
	 * @author ZHANGYUKUN
	 * @param method
	 * @param parameter
	 * @return
	 */
	public static String getMethodParameterKey(Method method,Parameter parameter) {
		return method.toString()+parameter.toString();
	}

}
