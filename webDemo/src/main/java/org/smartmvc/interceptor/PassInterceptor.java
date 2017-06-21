package org.smartmvc.interceptor;

import org.smartmvc.core.interceptor.Interceptor;


public class PassInterceptor extends Interceptor {

	/**
	 * before 在 策略方法执行之前调用
	 * 在过滤器中很容易获取 请求地址：urlTarget
	 * 请求方法封装的参数：paramsBean
	 * 如果 需要 你可以直接 获取 request:getRequest()
	 * 和响应: getResponse() 
	 */
	@Override
	public boolean before( String urlTarget,Object[] paramsBean  ){
		System.out.println("PassInterceptor----------before");
		return true;
	}

	/**
	 * after在策略方法执行后调用,返回false 会中断 请求，并抛出异常，你可以实现统一的异常接口来捕获它，
	 * 
	 * result 就是目标方法的返回值，你可对它做任何事情，检查或者修改
	 */
	@Override
	public boolean after( Object result) {
		System.out.println("PassInterceptor----------after");
		return true;
	}

}
