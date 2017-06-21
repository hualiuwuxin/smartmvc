package org.smartmvc.core.envi;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RequesteEnvironment 持有类,持有当前请求环境
 * @author ZHANGYUKUN
 * @author ZHANGYUKUN
 *
 */
public class RequesteEnvironmentHolder {
	
	private  final static ThreadLocal<RequesteEnvironment> requesEnvironment = new ThreadLocal<RequesteEnvironment>(){
		@Override
		protected RequesteEnvironment initialValue() {
			return new RequesteEnvironment();
		}
		
	};
	
	/**
	 * 得到请求
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return requesEnvironment.get().getRequest();
	}

	public static void setRequest(HttpServletRequest request) {
		requesEnvironment.get().setRequest(request);
	}
	
	
	/**
	 * 响应
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return requesEnvironment.get().getResponse();
	}

	public static void setResponse(HttpServletResponse response) {
		requesEnvironment.get().setResponse(response);
	}
	
	
	/**
	 * 请求url
	 * @param targetUrl
	 */
	public static void setTargetUrl(String targetUrl) {
		requesEnvironment.get().setTargetUrl(targetUrl);;
	}
	public static String getTargetUrl() {
		return requesEnvironment.get().getTargetUrl();
	}
	
	/**
	 * realPath
	 * @param targetUrl
	 */
	public static void setRealPath(String realPath) {
		requesEnvironment.get().setRealPath(realPath);;
	}
	public static String getRealPath() {
		return requesEnvironment.get().getRealPath();
	}
	
	
	
	/**
	 * 参数数组最大下标
	 * @return
	 */
	public static Map<String, Integer> getParameterArrayMaxSubscript() {
		return requesEnvironment.get().getParameterArrayMaxSubscript();
	}

	public static void setParameterArrayMaxSubscript(Map<String, Integer> parameterArrayMaxSubscript) {
		requesEnvironment.get().setParameterArrayMaxSubscript(parameterArrayMaxSubscript);
		
		for( String key : parameterArrayMaxSubscript.keySet()){
			String newKey = toCommonKey( key );
			if( getParameterArrayLengthSubscript().get(newKey) == null || getParameterArrayLengthSubscript().get(newKey) <   parameterArrayMaxSubscript.get(key)  ){
				getParameterArrayLengthSubscript().put(newKey,  parameterArrayMaxSubscript.get(key) );
			}
		}
		
	}
	

	/**
	 * 参数数组长度
	 * @return
	 */
	public static Map<String, Integer> getParameterArrayLengthSubscript() {
		return requesEnvironment.get().getParameterArrayLengthSubscript();
	}
	
	
	/**
	 * 全部参数最大下标
	 * @return
	 */
	public static Integer getMaxSubscript() {
		return requesEnvironment.get().getMaxSubscript();
	}

	public static void setMaxSubscript(Integer maxSubscript) {
		requesEnvironment.get().setMaxSubscript(maxSubscript);
	}

	
	/**
	 * 需要初始化的最大层数
	 * @return
	 */
	public static Integer getParameterMaxLevel() {
		return requesEnvironment.get().getParameterMaxLevel();
	}

	public static void setParameterMaxLevel(Integer parameterMaxLevel) {
		requesEnvironment.get().setParameterMaxLevel(parameterMaxLevel);
	}


	public static String toCommonKey(String  key){
		return  key.replaceAll( SystemConstants.SUBSCRIPTPATTERN.pattern() , SystemConstants.SUBSCRIPSIGN );
	}
	
}
