package org.smartmvc.core.interceptor;

import java.util.regex.Pattern;

public class RegexInterceptorPair {
	
	private Pattern regex;
	private Interceptor interceptor;
	
	public Interceptor getInterceptor() {
		return interceptor;
	}
	public void setInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
	}
	public Pattern getRegix() {
		return regex;
	}
	public void setRegix(Pattern regex) {
		this.regex = regex;
	}

}
