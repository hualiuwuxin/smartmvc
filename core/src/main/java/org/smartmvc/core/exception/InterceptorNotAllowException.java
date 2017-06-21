package org.smartmvc.core.exception;

import org.smartmvc.core.interceptor.Interceptor;

/**
 * 拦截器不允许通过的异常
 * @author ZHANGYUKUN
 *
 */
public class InterceptorNotAllowException extends Exception {

	private Interceptor interceptor;
	
	private static final long serialVersionUID = -3405511358621257118L;

	public InterceptorNotAllowException(Interceptor interceptor){
		this.interceptor = interceptor;
	}

	public Interceptor getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
	}
	
}
