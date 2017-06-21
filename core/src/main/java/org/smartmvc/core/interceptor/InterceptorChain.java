package org.smartmvc.core.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 拦截器链，不使用责任链是因为让 对方法，控制器，全局 拦截器做到统一接口
 * @author ZHANGYUKUN
 *
 */
public class InterceptorChain {
	
	private List<RegexInterceptorPair> interceptors = new ArrayList<>();


	public void addInterceptor(String regex,Interceptor interceptor) {
		RegexInterceptorPair regexInterceptorPair = new RegexInterceptorPair();
		regexInterceptorPair.setRegix( Pattern.compile(regex) );
		regexInterceptorPair.setInterceptor( interceptor );
		interceptors.add( regexInterceptorPair  );
	}

	public  List<RegexInterceptorPair> getInterceptors() {
		return interceptors;
	}

}
