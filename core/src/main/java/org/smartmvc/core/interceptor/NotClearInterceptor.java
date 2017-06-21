package org.smartmvc.core.interceptor;

public class NotClearInterceptor extends Interceptor {

	@Override
	public boolean before(String urlTarget, Object[] paramsBean) {
		return true;
	}

	@Override
	public boolean after(Object result) {
		return true;
	}

}
