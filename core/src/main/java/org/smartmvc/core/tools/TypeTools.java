package org.smartmvc.core.tools;

public class TypeTools {
	
	/**
	 * 是否基本类型 （基本类型包含8大基础类型和封装类，和string）
	 * 
	 * @author ZHANGYUKUN
	 * @param cls
	 * @return
	 */
	public static boolean isBaseType(Class<?> cls) {

		if (cls.equals(String.class)) {
			return true;
		} else if (cls.equals(Integer.class) || cls.equals(int.class)) {
			return true;
		} else if (cls.equals(Long.class) || cls.equals(long.class)) {
			return true;
		} else if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
			return true;
		} else if (cls.equals(Double.class) || cls.equals(double.class)) {
			return true;
		} else if (cls.equals(Float.class) || cls.equals(float.class)) {
			return true;
		} else if (cls.equals(Character.class) || cls.equals(char.class)) {
			return true;
		} else if (cls.equals(Byte.class) || cls.equals(byte.class)) {
			return true;
		} else if (cls.equals(Short.class) || cls.equals(short.class)) {
			return true;
		}

		return false;
	}
}
