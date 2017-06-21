package org.smartmvc.core.envi;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 存放请求环境的对象
 * @author ZHANGYUKUN
 *
 */
public class RequesteEnvironment {
	
	/**
	 * 本次请求request地址
	 * @author ZHANGYUKUN
	 */
	private String targetUrl;
	
	/**
	 * 本次请求request 对象
	 * @author ZHANGYUKUN
	 */
	private HttpServletRequest request;
	
	/**
	 * 本次请求response 对象
	 * @author ZHANGYUKUN
	 */
	private HttpServletResponse response;
	
	
	/**
	 * 数组对象最大下标对应关系
	 * @author ZHANGYUKUN
	 */
	private Map<String,Integer> parameterArrayMaxSubscript = new HashMap<>();
	
	/**
	 * 数组长度关系
	 * @author ZHANGYUKUN
	 */
	private Map<String,Integer> parameterArrayLengthSubscript = new HashMap<>();
	
	/**
	 * 最大的参数层级
	 * @author ZHANGYUKUN
	 */
	private Integer  parameterMaxLevel = new Integer(1);
	
	/**
	 * 最大的下标
	 * @author ZHANGYUKUN
	 */
	private Integer  maxSubscript = new Integer(0);
	
	/**
	 * tomcat路径
	 * 
	 */
	private String realPath;
	

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, Integer> getParameterArrayMaxSubscript() {
		return parameterArrayMaxSubscript;
	}

	public void setParameterArrayMaxSubscript(Map<String, Integer> parameterArrayMaxSubscript) {
		this.parameterArrayMaxSubscript = parameterArrayMaxSubscript;
	}

	public Map<String, Integer> getParameterArrayLengthSubscript() {
		return parameterArrayLengthSubscript;
	}

	public void setParameterArrayLengthSubscript(Map<String, Integer> parameterArrayLengthSubscript) {
		this.parameterArrayLengthSubscript = parameterArrayLengthSubscript;
	}

	public Integer getMaxSubscript() {
		return maxSubscript;
	}

	public void setMaxSubscript(Integer maxSubscript) {
		this.maxSubscript = maxSubscript;
	}

	public Integer getParameterMaxLevel() {
		return parameterMaxLevel;
	}

	public void setParameterMaxLevel(Integer parameterMaxLevel) {
		this.parameterMaxLevel = parameterMaxLevel;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	
}
