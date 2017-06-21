package org.smartmvc.core;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.smartmvc.core.converter.Converter;
import org.smartmvc.core.envi.RequesteEnvironmentHolder;
import org.smartmvc.core.envi.SystemConfiguration;
import org.smartmvc.core.exception.ConverterException;
import org.smartmvc.core.resolver.TypeInfo;
import org.smartmvc.core.resolver.TypeResolver;
import org.smartmvc.core.tools.MethodTools;
import org.smartmvc.core.tools.TypeTools;

/**
 * 对象转载器,负责转载对象
 * 
 * @author ZHANGYUKUN
 *
 */
public class BeanLoader {

	/**
	 * 装载一个bean
	 * 
	 * @author ZHANGYUKUN
	 * @param methodParameterName
	 * @param cls
	 * @param type
	 * @param parameterName
	 * @param currentlevel
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws ConverterException 
	 */
	protected static Object loadingNameBean(String methodParameterName , Class<?> cls, Type type, String parameterName, Integer currentlevel)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConverterException {
		Object parameterBean = null;

		if (currentlevel > RequesteEnvironmentHolder.getParameterMaxLevel()) {
			return parameterBean;
		}

		if (TypeTools.isBaseType(cls)) {
			String strValue = RequesteEnvironmentHolder.getRequest().getParameter(parameterName);
			parameterBean = loadingBaseBean(cls, strValue);
		} else {

			boolean needInitObject = false;
			for (String key : RequesteEnvironmentHolder.getRequest().getParameterMap().keySet()) {
				if (key.startsWith(parameterName)) {
					needInitObject = true;
					break;
				}
			}

			if (needInitObject || (SystemConfiguration.isFirstFloorArgumentInit() && currentlevel == 1)) {
				TypeInfo typeInfo ;
				if( methodParameterName == null ){
					typeInfo = TypeResolver.resolverType(type);
				}else{
					//复制有问题，暂时这么处理
					typeInfo = TypeResolver.resolverType(type);
					//BeanUtils.copyProperties(typeInfo, ActionAware.getParameterTemplate().get( methodParameterName ));
				}
				
				Converter<?> converter = SystemConfiguration.getConverter().get( typeInfo.getCls() );
				if( converter != null ){
					String paramValue = RequesteEnvironmentHolder.getRequest().getParameter(parameterName) ;
					try {
						parameterBean = converter.conver( RequesteEnvironmentHolder.getRequest().getParameter(parameterName)  );
					} catch (Exception e) {
						throw new ConverterException(parameterName, paramValue ,converter);
					}
				}else{
					
					if (isCommonContainerType(typeInfo.getCls())) {
						BeanLoader.loadingCommonContainer(typeInfo, parameterName, currentlevel);
					} else {
						BeanLoader.loadingObject(typeInfo, parameterName, currentlevel);
					}
					
					parameterBean = typeInfo.getTypeBean();
				}
				
			}

		}

		return parameterBean;
	}

	/**
	 * 装载一个常见的容器类型
	 * 
	 * @author ZHANGYUKUN
	 * @param cls
	 * @param parameterBean
	 * @param parameterName
	 * @param request
	 * @param currentlevel
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ConverterException 
	 */
	protected static void loadingCommonContainer(TypeInfo typeInfo, String parameterName, int currentlevel)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConverterException {
		Class<?> cls = typeInfo.getCls();

		if (cls.isArray()) {
			loadingArray(typeInfo, parameterName, currentlevel);
		} else if (Collection.class.isAssignableFrom(cls)) {
			loadingCollection(typeInfo, parameterName, currentlevel);
		} else if (Map.class.isAssignableFrom(cls)) {
			loadingMap(typeInfo, parameterName, currentlevel);
		}

	}

	/**
	 * 装在一个对象类型
	 * 
	 * @author ZHANGYUKUN
	 * @param parameterBean
	 * @param parameterName
	 * @param request
	 * @param currentlevel
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ConverterException 
	 */
	protected static void loadingObject(TypeInfo typeInfo, String parameterName, int currentlevel)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConverterException {
		Method[] methods = typeInfo.getCls().getDeclaredMethods();

		for (Method method : methods) {
			if (!MethodTools.isSetMethod(method)) {
				continue;
			}

			Parameter[] parameters = method.getParameters();
			if (parameters.length != 1) {
				continue;
			}

			String fieldName = MethodTools.getSetMethodFieldName(method);
			String nextParameterName = parameterName + "." + fieldName;

			Object methodValue = loadingNameBean( null , parameters[0].getType(), parameters[0].getParameterizedType(),nextParameterName, currentlevel + 1);

			if (methodValue != null) {
				method.invoke(typeInfo.getTypeBean(), methodValue);
			}
		}

	}

	/**
	 * 装载一个数组
	 * 
	 * @author ZHANGYUKUN
	 * @param parameterBean
	 * @param parameterName
	 * @param request
	 * @param currentlevel
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ConverterException 
	 */
	private static void loadingArray(TypeInfo typeInfo, String parameterName, int currentlevel)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConverterException {
		Class<?> componentType = typeInfo.getCls().getComponentType();

		Integer length = RequesteEnvironmentHolder.getParameterArrayLengthSubscript().get(RequesteEnvironmentHolder.toCommonKey(parameterName));
		typeInfo.setTypeBean(Array.newInstance(componentType, length == null ? 0 : length));
		
		for (int i = 0; i < Array.getLength(typeInfo.getTypeBean()); i++) {
			String nextParameterName = parameterName + "[" + i + "]";
			Object item = null;
			if( typeInfo.getInnerType().size() == 0 ){
				item = loadingNameBean(  null ,  componentType, componentType , nextParameterName,currentlevel);
			}else{
				item = loadingNameBean(  null ,  componentType, typeInfo.getInnerType().get(0).getType(), nextParameterName,currentlevel);
			}
			Array.set(typeInfo.getTypeBean(), i, item);
		}
	}

	/**
	 * 装载一个集合
	 * 
	 * @author ZHANGYUKUN
	 * @param parameterBean
	 * @param parameterName
	 * @param request
	 * @param currentlevel
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ConverterException 
	 */
	private static void loadingCollection(TypeInfo typeInfo, String parameterName, int currentlevel)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConverterException {

		if (typeInfo.getTypeBean() instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) typeInfo.getTypeBean();
			list.clear();

			int length = RequesteEnvironmentHolder.getParameterArrayMaxSubscript().get(parameterName) == null ? 0
					: RequesteEnvironmentHolder.getParameterArrayMaxSubscript().get(parameterName);
			for (int i = 0; i < length; i++) {
				String nextParameterName = parameterName + "[" + i + "]";
				Object item = loadingNameBean(  null , typeInfo.getInnerType().get(0).getCls(),typeInfo.getInnerType().get(0).getType(), nextParameterName, currentlevel);

				list.add(item);
			}
		} else if (typeInfo.getTypeBean() instanceof Set) {
			@SuppressWarnings("unchecked")
			Set<Object> set = (Set<Object>) typeInfo.getTypeBean();
			set.clear();

			int length = RequesteEnvironmentHolder.getParameterArrayMaxSubscript().get(parameterName) == null ? 0
					: RequesteEnvironmentHolder.getParameterArrayMaxSubscript().get(parameterName);
			for (int i = 0; i < length; i++) {
				String nextParameterName = parameterName + "[" + i + "]";
				Object item = loadingNameBean(  null , typeInfo.getInnerType().get(0).getCls(),typeInfo.getInnerType().get(0).getType(), nextParameterName, currentlevel);
				if (item != null) {
					set.add(item);
				}
			}
		}

	}

	/**
	 * 装载一个map
	 * 
	 * @author ZHANGYUKUN
	 * @param map
	 * @param parameterName
	 * @param request
	 * @param currentlevel
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ConverterException 
	 */
	private static void loadingMap(TypeInfo typeInfo, String parameterName, int currentlevel)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConverterException {
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = (Map<Object, Object>) typeInfo.getTypeBean();
		Set<String> keys = RequesteEnvironmentHolder.getRequest().getParameterMap().keySet();

		
		if ( typeInfo.getInnerType().size() != 0 ) {
			loadingTypeMap(keys, typeInfo, parameterName, currentlevel);
		} else {
			loadingNotTypeMap(keys, map, parameterName);
		}
	}

	/**
	 * 装载泛型map
	 * 
	 * @author ZHANGYUKUN
	 * @param keys
	 * @param map
	 * @param parameterName
	 * @param request
	 * @param valueCls
	 * @param currentlevel
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ConverterException 
	 */
	private static void loadingTypeMap(Set<String> keys, TypeInfo typeInfo, String parameterName,int currentlevel)throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConverterException {
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = (Map<Object, Object>) typeInfo.getTypeBean();

		for (String key : keys) {
			if (key.startsWith(parameterName + ".")) {
				int startDotIndex = parameterName.length() + 1;
				int nextDotIndex = key.indexOf(".", startDotIndex);
				if (nextDotIndex == -1) {
					nextDotIndex = key.length();
				}

				if (nextDotIndex > startDotIndex) {
					String mapKeyName = key.substring(startDotIndex, nextDotIndex);
					map.put(mapKeyName, loadingNameBean(  null , typeInfo.getInnerType().get(1).getCls() , typeInfo.getInnerType().get(1).getType(),parameterName + "." + mapKeyName, currentlevel + 1));
				}
			}
		}

	}

	/**
	 * 装载一个非泛型map
	 * 
	 * @author ZHANGYUKUN
	 * @param keys
	 * @param map
	 * @param parameterName
	 * @param request
	 */
	private static void loadingNotTypeMap(Set<String> keys, Map<Object, Object> map, String parameterName) {

		for (String key : keys) {
			if (key.startsWith(parameterName)) {
				String mapKeyName = key.substring(parameterName.length() + 1);
				map.put(mapKeyName, RequesteEnvironmentHolder.getRequest().getParameter(key));
			}
		}

	}

	/**
	 * 装载一个基础类型
	 * 
	 * @author ZHANGYUKUN
	 * @param cls
	 * @param strValue
	 * @return
	 */
	public static Object loadingBaseBean(Class<?> cls, String strValue) {
		Object parameterBean = null;

		if (cls.equals(String.class)) {
			parameterBean = strValue;
		} else if (cls.equals(Integer.class) || cls.equals(int.class)) {
			parameterBean = strValue == null ? 0 : Integer.valueOf(strValue);
		} else if (cls.equals(Long.class) || cls.equals(long.class)) {
			parameterBean = strValue == null ? 0l : Long.valueOf(strValue);
		} else if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
			parameterBean = strValue == null ? false : Boolean.valueOf(strValue);
		} else if (cls.equals(Double.class) || cls.equals(double.class)) {
			parameterBean = strValue == null ? 0 : Double.valueOf(strValue);
		} else if (cls.equals(Float.class) || cls.equals(float.class)) {
			parameterBean = strValue == null ? 0 : Float.valueOf(strValue);
		} else if (cls.equals(Character.class) || cls.equals(char.class)) {
			parameterBean = strValue == null ? 0 : Integer.valueOf(strValue);
		} else if (cls.equals(Byte.class) || cls.equals(byte.class)) {
			parameterBean = strValue == null ? 0 : Byte.valueOf(strValue);
		} else if (cls.equals(Short.class) || cls.equals(short.class)) {
			parameterBean = Short.valueOf(strValue);
		}

		return parameterBean;
	}

	/**
	 * 是否是常见的容器类型(数组，集合，map)
	 * 
	 * @author ZHANGYUKUN
	 * @param cls
	 * @return
	 */
	private static boolean isCommonContainerType(Class<?> cls) {

		if (cls.isArray()) {
			return true;
		} else if (Collection.class.isAssignableFrom(cls)) {
			return true;
		} else if (Map.class.isAssignableFrom(cls)) {
			return true;
		}
		return false;
	}


}
