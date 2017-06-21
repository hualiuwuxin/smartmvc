package org.smartmvc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.smartmvc.core.annotation.em.ResponseType;

/**
 * 定义返回值的类型
 * @author ZHANGYUKUN
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Response {
	
	/**
	 * 定义响应是什么类型的数据
	 * @author ZHANGYUKUN
	 * @return
	 */
	ResponseType type() default ResponseType.JSON;
	
}
