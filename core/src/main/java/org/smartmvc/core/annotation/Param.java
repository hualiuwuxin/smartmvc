package org.smartmvc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数注解
 * @author ZHANGYUKUN
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
	
	/**
	 * 参数别名,不写默认是形参名，不指定@Param注解默认也是形参名
	 * @author ZHANGYUKUN
	 * @return
	 */
	String value() default "";
	
	
	/**
	 * 参数是否必须
	 * @author ZHANGYUKUN
	 * @return
	 */
	boolean required() default false;
	
	
}
