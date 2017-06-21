package org.smartmvc.core.resolver;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartmvc.core.BeanLoader;
import org.smartmvc.core.envi.RequesteEnvironmentHolder;
import org.smartmvc.core.tools.TypeTools;

/**
 * 泛型参数解析类
 * @author ZHANGYUKUN
 *
 */
public class TypeResolver {
	private static final Logger logger = LoggerFactory.getLogger(TypeResolver.class);
	
	/**
	 * 解析type 得到一个默认的 对象
	 * 
	 * @author ZHANGYUKUN
	 * @param type
	 * @param request
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static TypeInfo resolverType(Type type)throws InstantiationException, IllegalAccessException {
		TypeInfo typeInfo = new TypeInfo();
		typeInfo.setType(type);
		
		
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;

			TypeInfo rawTypeInfo = resolverType(parameterizedType.getRawType());
			
			typeInfo.setTypeBean( rawTypeInfo.getTypeBean() );
			
			for (int i = 0; i < parameterizedType.getActualTypeArguments().length; i++) {
				TypeInfo innerTypeInfo = resolverType(parameterizedType.getActualTypeArguments()[i]);
				typeInfo.getInnerType().add( innerTypeInfo );
				typeInfo.setHaveInnerType( true );
			}
			
		} else if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;
			
			TypeInfo innerTypeInfo = resolverType(genericArrayType.getGenericComponentType());
			typeInfo.getInnerType().add( innerTypeInfo );

			typeInfo.setTypeBean(  Array.newInstance( innerTypeInfo.getCls() ,  RequesteEnvironmentHolder.getMaxSubscript() )  );
		} else if (type instanceof TypeVariable) {
			TypeVariable<?> typeVariable = (TypeVariable<?>) type;
			
			return resolverType(typeVariable.getBounds()[0]);
		} else if (type instanceof WildcardType) {
			WildcardType wildcardType = (WildcardType) type;

			if (wildcardType.getLowerBounds().length == 0) {
				
				if( logger.isDebugEnabled() ){
					logger.debug("not LowerBounds type!");
				}
				return  resolverType(wildcardType.getUpperBounds()[0]);
			}else{
				return resolverType(wildcardType.getLowerBounds()[0]);
			}
			
		} else if (type instanceof Class) {
			Class<?> cls = (Class<?>) type;
			typeInfo.setTypeBean( getCommonContainerBean(cls) );
			typeInfo.setCls(  cls );
		}
		
		return typeInfo;
	}
	
	
	/**
	 * 得到常见的Java自带容器类型(数组，集合，map)
	 * 
	 * @author ZHANGYUKUN
	 * @param cls
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static Object getCommonContainerBean(Class<?> cls)throws InstantiationException, IllegalAccessException {
		if (cls.isArray()) {
			return Array.newInstance(cls.getComponentType(), 0);
		} else if (Collection.class.isAssignableFrom(cls)) {
			if (List.class.equals(cls)) {
				return new ArrayList<>();
			} else if (Set.class.equals(cls)) {
				return new HashSet<>();
			}
			return new ArrayList<>();
		} else if (Map.class.isAssignableFrom(cls)) {
			return new HashMap<Object, Object>();
		} else {
			if( TypeTools.isBaseType( cls ) ){
				return BeanLoader.loadingBaseBean(cls,null);
			}else{
				return cls.newInstance();
			}
		}
	}

}
