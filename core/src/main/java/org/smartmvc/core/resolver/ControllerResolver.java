package org.smartmvc.core.resolver;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartmvc.core.ActionAware;
import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.envi.SystemConfiguration;
import org.smartmvc.core.tools.MethodTools;
import org.smartmvc.core.tools.TypeTools;
import org.smartmvc.tools.ParameterNameUtils;

/**
 * 控制器解析者
 * @author ZHANGYUKUN
 */
public class ControllerResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerResolver.class);
	
	private static final Map<String,Method> ACTIONMAPPINGHOLDER = new HashMap<>();
	private static final Map<Method,String[]> METHODDEFAULTPARAMNAMES = new HashMap<>();
	private static final Map<String,Object> BENAMAPPINGHOLDER = new HashMap<>();
	private static final Map<String,TypeInfo> PARAMETERTEMPLATE = new HashMap<>();
	
	public static void  resolver(){
		
		
		String packageName = SystemConfiguration.getPackageName();
		URL url =   ControllerResolver.class.getClassLoader().getResource( packageNameToUrlName( packageName ) );
		
		String path = null;
		try {
			path = URLDecoder.decode( url.getFile() ,"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		if( path == null ){ 
			logger.error("错误的路径:{}" , path);
		}
		
		File file = new File( path );
		String[] controllersFileNames = file.list();
		for(String controllerFileName : controllersFileNames ){
			String className = getClassName( packageName , controllerFileName);
			
			try {
				Class<?> cls =  ControllerResolver.class.getClassLoader().loadClass( className );
				Object controllerBean =  cls.newInstance();
				
				if( controllerBean.getClass().isAnnotationPresent( Controller.class ) ){
					
					Controller controller = controllerBean.getClass().getAnnotation( Controller.class );
					String controllerName = controller.value();
					
					if( "".equals( controllerName )  ){
						controllerName = flybackOriginalName( firstLetterToLowerCase( cls.getSimpleName() ) );
					}
					
					resolverAction( controllerName,controllerBean );
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
		
		ActionAware.setActionMappingHolder(ACTIONMAPPINGHOLDER);
		ActionAware.setBenaMappingHolder(BENAMAPPINGHOLDER);
		ActionAware.setMethodDefaultParamNames(METHODDEFAULTPARAMNAMES);
		ActionAware.setParameterTemplate(PARAMETERTEMPLATE);
		
	}
	
	/**
	 * 解析  controller 里面的 action
	 * @author ZHANGYUKUN
	 * @param controllerName
	 * @param controllerBean
	 */
	private static void  resolverAction( String controllerName,Object controllerBean ){
		
		Method[] methods =  controllerBean.getClass().getDeclaredMethods();
		for( Method method: methods){
			if( method.isAnnotationPresent( Action.class ) ){
				Action action =  method.getAnnotation( Action.class);
				String actionName = action.value();
				
				if( "".equals( actionName ) ){
					actionName = method.getName();
				}
				
				String servletPath = controllerName  + "/" + actionName;
				
				String[]  methodDefaultParamName = ParameterNameUtils.getMethodParameterNamesByAsm4( controllerBean.getClass() ,  method );
				
				METHODDEFAULTPARAMNAMES.put( method , methodDefaultParamName );
				ACTIONMAPPINGHOLDER.put( servletPath , method);
				BENAMAPPINGHOLDER.put(servletPath ,controllerBean );
				resolverParamter( method  );
			}
		}
		
		
	}
	
	/**
	 * 解析参数
	 * @author ZHANGYUKUN
	 * @param method
	 */
	private static void resolverParamter(Method method) {
		Parameter[] parameters =  method.getParameters();
		for(Parameter parameter : parameters){
			Type type = parameter.getParameterizedType();
			Class<?> cls = parameter.getType();
			try {
				if ( !TypeTools.isBaseType(cls)) {
					TypeInfo template = TypeResolver.resolverType(type);
					PARAMETERTEMPLATE.put(  MethodTools.getMethodParameterKey( method , parameter) ,  template );
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		
	}


	/**
	 * 转换包名到文件地址名
	 * @author ZHANGYUKUN
	 * @param packageName
	 * @return
	 */
	private static String packageNameToUrlName(String packageName){
		return packageName.replace(".", "/");
	}
	
	/**
	 * 通过文件名得到类名
	 * @author ZHANGYUKUN
	 * @param packageName
	 * @param controllerFileName
	 * @return
	 */
	private static String getClassName(String packageName,String controllerFileName){
		String className = packageName + "." +controllerFileName.substring(0,controllerFileName.length()-6  );
		return className;
	}
	
	/**
	 * 转成小驼峰
	 * 
	 * @author ZHANGYUKUN
	 * @param name
	 * @return
	 */
	private static String firstLetterToLowerCase(String name){
		
		if( name == null || "".equals( name ) ){
			return name;
		}else{
			return name.substring(0,1).toLowerCase() + name.substring(1) ;
		}
	}
	
	/**
	 * 回归到原始的名字(如果用 controller 结尾的 那么去掉 controller)
	 * 
	 * @author ZHANGYUKUN
	 * @param name
	 * @return
	 */
	private static String flybackOriginalName(String name){
		
		if( name.toLowerCase().endsWith( "controller" ) ){
			return name.substring(0,name.length()- 10 );
		}
		return name;
	}
	
	

}
