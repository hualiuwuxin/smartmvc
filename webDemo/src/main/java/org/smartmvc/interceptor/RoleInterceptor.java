package org.smartmvc.interceptor;

import org.smartmvc.core.interceptor.Interceptor;


public class RoleInterceptor extends Interceptor {

	@Override
	public boolean before( String urlTarget,Object[] paramsBean  ){
		System.out.println("RoleInterceptor----------before");
		return false;
	}

	@Override
	public boolean after( Object result) {
		System.out.println("RoleMy1Interceptor----------after");
		return true;
	}

}
