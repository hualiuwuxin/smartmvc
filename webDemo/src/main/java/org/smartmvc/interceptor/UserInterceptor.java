package org.smartmvc.interceptor;

import org.smartmvc.core.interceptor.Interceptor;


public class UserInterceptor extends Interceptor {

	public boolean before( String urlTarget,Object[] paramsBean  ) {
		System.out.println("UserInterceptor----------before");
		return true;
	}

	public boolean after( Object result) {
		System.out.println("UserInterceptor----------after");
		return true;
	}

}
