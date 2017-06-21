package org.smartmvc.configuration;

import java.util.Date;
import java.util.Map;

import org.smartmvc.core.SystemConfigurationAware;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.core.converter.Converter;
import org.smartmvc.core.converter.DateConverter;
import org.smartmvc.core.exception.ExceptionHandler;
import org.smartmvc.core.interceptor.InterceptorChain;
import org.smartmvc.exceptionHandler.MyExceptionHandler;
import org.smartmvc.interceptor.PassInterceptor;
/**
 * ������
 * @author ZHANGYUKUN
 *
 */
public class Configuration extends SystemConfigurationAware {

	/**
	 * 注册你统一处理异常的接口
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		return new MyExceptionHandler();
	}

	/**
	 * 默认希望返回的是师徒类型，json 合适 jsp(不指定 系统默认使用jsp)
	 */
	@Override
	public ResponseType getDefaultViewPostfix() {
		return ResponseType.JSON;
	}

	/**
	 * 没有赋值的参数，你可以指定他们的参数是null 或者是一个 初始的 对象
	 */
	@Override
	public Boolean getFirstFloorArgumentInit() {
		return super.getFirstFloorArgumentInit();
	}

	/**
	 * 在这里注册你的转化器
	 */
	@Override
	public void converterAware(Map<Class<?>, Converter<?>> converter) {
		converter.put( Date.class , new DateConverter() );
	}

	/**
	 * 在这里组成你的 全局过滤器
	 */
	@Override
	public void interceptorChainAware(InterceptorChain interceptorChain) {
		interceptorChain.addInterceptor( "^interceptor/.*", new PassInterceptor());
		//interceptorChain.addInterceptor("^interceptor/.*" , new NotPassInterceptor() );
	}

	/**
	 * 告诉系统你的 控制器包在哪里
	 */
	@Override
	public String getPackageName() {
		return "org.smartmvc.controller";
	}


}
