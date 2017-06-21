package org.smartmvc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制器标志
 * @author ZHANGYUKUN
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	
	/**
	 * 控制器模块名,不写默认是类名首字母小写，并且去掉Controller后缀
	 * @author ZHANGYUKUN
	 * @return
	 */
	String value() default "";
}
