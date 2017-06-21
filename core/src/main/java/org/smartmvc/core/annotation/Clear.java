package org.smartmvc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.smartmvc.core.interceptor.Interceptor;


/**
 * 清除上层拦截器标志
 * @author ZHANGYUKUN
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Clear  {
	/**
	 * 需要清除的拦截器类,不写默认清除全部上级拦截器，写了值清除指定拦截器，不能清除本层拦截器
	 * @author ZHANGYUKUN
	 * @return
	 */
	Class<? extends Interceptor>[] value() default {};
}
