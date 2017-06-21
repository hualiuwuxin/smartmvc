package org.smartmvc.core;

import java.util.Date;
import java.util.Map;

import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.core.converter.Converter;
import org.smartmvc.core.converter.DateConverter;
import org.smartmvc.core.envi.SystemConfiguration;
import org.smartmvc.core.exception.ExceptionHandler;
import org.smartmvc.core.interceptor.InterceptorChain;

/**
 * 提供给用户修改配置接口的类
 * @author ZHANGYUKUN
 *
 */
public abstract class SystemConfigurationAware {

	public SystemConfigurationAware() {
		
		SystemConfiguration.setPackageName(getPackageName());
		
		SystemConfiguration.setDefaultViewPostfix(getDefaultViewPostfix());
		
		SystemConfiguration.setFirstFloorArgumentInit(getFirstFloorArgumentInit());
	
		interceptorChainAware( SystemConfiguration.getInterceptorChain() );
		
		SystemConfiguration.getConverter().put( Date.class , new DateConverter());
		converterAware(  SystemConfiguration.getConverter());
		
		if( getExceptionHandler() != null ){
			SystemConfiguration.setExceptionHandler( getExceptionHandler() );
		}
		
	}

	public abstract String getPackageName();

	public ResponseType getDefaultViewPostfix() {
		return ResponseType.JSP;
	}

	public Boolean getFirstFloorArgumentInit() {
		return true;
	}
	
	public void interceptorChainAware(InterceptorChain interceptorChain  ) {
	}
	
	public void converterAware(Map<Class<?>,Converter<?>>  converter ) {
	}
	
	public ExceptionHandler getExceptionHandler() {
		return null;
	}

}
