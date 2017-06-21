package org.smartmvc.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartmvc.core.annotation.Param;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.core.envi.RequesteEnvironmentHolder;
import org.smartmvc.core.envi.SystemConfiguration;
import org.smartmvc.core.exception.ConverterException;
import org.smartmvc.core.exception.RequiredParamNotExistException;
import org.smartmvc.core.resolver.TypeInfo;
import org.smartmvc.core.tools.InterceptorTools;
import org.smartmvc.core.tools.MethodTools;

import com.alibaba.fastjson.JSONObject;

public class ActionAware {
	
	private static final Logger logger = LoggerFactory.getLogger(ActionAware.class);

	/**
	 * 存放action地址和 method 策略的映射关系
	 */
	private static Map<String, Method> actionMappingHolder = new HashMap<>();

	/**
	 * 存放 Controller bean 实例 和 action地址的映射关系
	 * 
	 * @author ZHANGYUKUN
	 */
	private static Map<String, Object> benaMappingHolder = new HashMap<>();

	/**
	 * 存放默认的 方法对应的参数名
	 * 
	 * @author ZHANGYUKUN
	 */
	private static Map<Method, String[]> methodDefaultParamNames = new HashMap<>();
	
	/**
	 * 方法参数对象模板
	 */
	private static Map<String,TypeInfo> PARAMETERTEMPLATE = new HashMap<>();;
	
	

	public static void setActionMappingHolder(Map<String, Method> actionMappingHolder) {
		ActionAware.actionMappingHolder = actionMappingHolder;
	}

	public static void setBenaMappingHolder(Map<String, Object> benaMappingHolder) {
		ActionAware.benaMappingHolder = benaMappingHolder;
	}

	public static Map<String, Method> getActionMappingHolder() {
		return actionMappingHolder;
	}

	public static Map<String, Object> getBenaMappingHolder() {
		return benaMappingHolder;
	}

	public static Map<Method, String[]> getMethodDefaultParamNames() {
		return methodDefaultParamNames;
	}

	public static void setMethodDefaultParamNames(Map<Method, String[]> methodDefaultParamNames) {
		ActionAware.methodDefaultParamNames = methodDefaultParamNames;
	}
	
	public static Map<String,TypeInfo> getParameterTemplate() {
		return PARAMETERTEMPLATE;
	}

	public static void setParameterTemplate(Map<String,TypeInfo> pARAMETERTEMPLATE) {
		PARAMETERTEMPLATE = pARAMETERTEMPLATE;
	}

	/**
	 * 处理分发
	 * 
	 * @param target
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public static void dispatche(String targetUrl) throws Exception {
		
		if( logger.isDebugEnabled() ){
			logger.debug( "正在请求:{}",targetUrl );
		}

		Method targetMethod = actionMappingHolder.get(targetUrl);
		if( targetMethod== null ){
			if(  RequesteEnvironmentHolder.getRequest().getDispatcherType().equals( DispatcherType.FORWARD )  ){
				
				dispatcherTo404( "/" +targetUrl,  RequesteEnvironmentHolder.getRequest() ,RequesteEnvironmentHolder.getResponse() );
				
			}else{
				dispatcherToOther("/" +targetUrl,  RequesteEnvironmentHolder.getRequest() ,RequesteEnvironmentHolder.getResponse() );	
			}
			return;
		}
		Object targetBean = benaMappingHolder.get(targetUrl);
		
		
		String[] defaultParamNames = methodDefaultParamNames.get(targetMethod);
		Parameter[] params = targetMethod.getParameters();
		Object[] paramsBean = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			Parameter parameter = params[i];
			Object parameterBean = loadingParameterBean( MethodTools.getMethodParameterKey( targetMethod , parameter), parameter , defaultParamNames[i]);
			paramsBean[i] = parameterBean;
		}
		Object reuslt = null;
		
		reuslt = targetMethodInvoke(targetMethod,targetBean,paramsBean);
		
		
		ResponseType responseType = null;
		if (targetMethod.isAnnotationPresent(Response.class)) {
			Response responseAnno = targetMethod.getAnnotation(Response.class);
			responseType = responseAnno.type();
		} else {
			 responseType = SystemConfiguration.getDefaultViewPostfix();
		}
		
		if (responseType.equals(ResponseType.JSON)) {
			responseJson(reuslt, RequesteEnvironmentHolder.getResponse());
		} else if (responseType.equals(ResponseType.JSP)) {
			responseJspView(reuslt.toString(), RequesteEnvironmentHolder.getRequest(),RequesteEnvironmentHolder.getResponse());
		} else {
			if( logger.isDebugEnabled() ){
				logger.debug( "未实现的返回值类型");
			}
		}
	}

	/**
	 * 执行目标策略方法
	 * @author ZHANGYUKUN
	 * @param targetMethod
	 * @param targetBean
	 * @param paramsBean
	 * @throws Exception 
	 */
	private static Object targetMethodInvoke(Method targetMethod, Object targetBean, Object[] paramsBean) throws Exception{
		Object reuslt = null;
		
		InterceptorTools.globalInterceptorBefore(targetMethod,paramsBean);
		InterceptorTools.classInterceptorBefore( targetMethod, paramsBean);
		InterceptorTools.actionInterceptorBefore( targetMethod, paramsBean);
		
		
		try {
			reuslt = targetMethod.invoke(targetBean, paramsBean);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw e;
		}
		
		InterceptorTools.actionInterceptorAfter(targetMethod,reuslt);
		InterceptorTools.classInterceptorAfter(targetMethod,reuslt);
		InterceptorTools.globalInterceptorAfter( targetMethod , reuslt );
		
		
		return reuslt;
	}
	

	/**
	 * 请求到jsp视图
	 * 
	 * @author ZHANGYUKUN
	 * @param view
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private static void responseJspView(String view, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.getRequestDispatcher( view +"." +ResponseType.JSP.getPostfix() ).forward(request, response);
	}
	
	/**
	 * 交个tomcat 处理
	 * @author ZHANGYUKUN
	 * @param view
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private static void dispatcherToOther(String view, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.getRequestDispatcher( view ).forward(request, response);
	}
	/**
	 * 到404页面
	 * @param view
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private static void dispatcherTo404(String view, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		response.setStatus(404);
		PrintWriter out = response.getWriter();

		InputStream is = ActionAware.class.getResourceAsStream("/org/smartmvc/core/page/404.html");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader bis = new BufferedReader(isr);
		
		//非常固定应该在外面
		StringBuilder sb =new StringBuilder();
		
		String line = bis.readLine();
		while (line != null ) {
			sb.append( line );
			line = bis.readLine();
		}
		String  ouStr = sb.toString();
		ouStr = ouStr.replace("#{actionPath}",  view );
		ouStr = ouStr.replace("#{tomcatVersion}",  request.getServletContext().getServerInfo()   );
		
		out.write( ouStr );
		out.flush();
		out.close();
	}

	/**
	 * 放回 在响应流里面回写json数据
	 * 
	 * @author ZHANGYUKUN
	 * @param result
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private static void responseJson(Object result, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");

		PrintWriter printWriter = response.getWriter();

		String rt = "";
		if (result instanceof String) {
			rt = result.toString();
		} else {

			rt = JSONObject.toJSONString(result);
		}

		printWriter.write(rt);
		printWriter.flush();
		printWriter.close();

	}

	/**
	 * 装载参数,得到bean
	 * 
	 * @author ZHANGYUKUN
	 * @param parameterBean
	 * @param currentlevel
	 *            当前等级
	 * @param request
	 * @param parameterBeanName
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws RequiredParamNotExistException 
	 * @throws ConverterException 
	 */
	private static Object loadingParameterBean(String methodParameterKey, Parameter parameter, String defaultParamName)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, RequiredParamNotExistException, ConverterException {
		Class<?> cls = parameter.getType();
		Type type = parameter.getParameterizedType();

		Object parameterBean = new Object();
		String parameterName = defaultParamName;

		if (parameter.isAnnotationPresent(Param.class)) {
			Param param = parameter.getAnnotation(Param.class);
			
			if( !"".equals( param.value()) ){
				parameterName = param.value();
			}
			
			if( param.required() && !RequesteEnvironmentHolder.getRequest().getParameterMap().keySet().contains( parameterName ) ){
				throw new RequiredParamNotExistException( parameterName  );
			}
		}
		

		parameterBean = BeanLoader.loadingNameBean( methodParameterKey, cls, type, parameterName, 1);

		return parameterBean;
	}
	
	

}
