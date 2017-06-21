package org.smartmvc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * action标志
 * @author ZHANGYUKUN
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	
	/**
	 * action 名字，不写默认是方法名
	 * @author ZHANGYUKUN
	 * @return
	 */
	String value() default "";
}
