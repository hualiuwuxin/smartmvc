package org.smartmvc.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smartmvc.core.envi.RequesteEnvironmentHolder;

public abstract class Interceptor {
	
	protected HttpServletRequest getRequest(){
		return RequesteEnvironmentHolder.getRequest();
	}
	
	protected HttpServletResponse getResponse(){
		return RequesteEnvironmentHolder.getResponse();
	}
	
	public abstract boolean before( String urlTarget,Object[] paramsBean ); 
	public abstract boolean after( Object result ); 

}
