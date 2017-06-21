package org.smartmvc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.smartmvc.core.interceptor.Interceptor;


/**
 * 拦截器标志
 * @author ZHANGYUKUN
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect  {
	
	/**
	 * 拦截器类
	 * @author ZHANGYUKUN
	 * @return
	 */
	Class<? extends Interceptor>[] value();
}
