package org.smartmvc.core.tools;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.smartmvc.core.annotation.Aspect;
import org.smartmvc.core.annotation.Clear;
import org.smartmvc.core.envi.RequesteEnvironmentHolder;
import org.smartmvc.core.envi.SystemConfiguration;
import org.smartmvc.core.exception.InterceptorNotAllowException;
import org.smartmvc.core.interceptor.Interceptor;
import org.smartmvc.core.interceptor.RegexInterceptorPair;

public class InterceptorTools {
	
	/**
	 * 全局过滤器Before
	 * @param targetMethod
	 * @param paramsBean
	 * @throws InterceptorNotAllowException 
	 */
	public static void globalInterceptorBefore(Method targetMethod ,Object[] paramsBean ) throws InterceptorNotAllowException {
		
		if( targetMethod.getDeclaringClass().isAnnotationPresent( Clear.class ) ||  targetMethod.isAnnotationPresent( Clear.class ) ){
			
			Set< Class<? extends Interceptor>> clearInterceptorSet = new HashSet<>();
			
			if( targetMethod.getDeclaringClass().isAnnotationPresent( Clear.class  ) ){
				Clear classClear = targetMethod.getDeclaringClass().getAnnotation( Clear.class );
				Class<? extends Interceptor>[] classClearArray = classClear.value();
				for(Class<? extends Interceptor>  interceptorcls:classClearArray){
					clearInterceptorSet.add( interceptorcls );
				}
			}
			
			if( targetMethod.isAnnotationPresent( Clear.class  ) ){
				Clear methodClear = targetMethod.getAnnotation( Clear.class );
				Class<? extends Interceptor>[] methodClearArray = methodClear.value();
				for(Class<? extends Interceptor>  interceptorcls:methodClearArray){
					clearInterceptorSet.add( interceptorcls );
				}
			}
			
			
			if( clearInterceptorSet.size() !=0  ){
				
				for( RegexInterceptorPair regexInterceptorPair: SystemConfiguration.getInterceptorChain().getInterceptors()){
					if( regexInterceptorPair.getRegix().matcher( RequesteEnvironmentHolder.getTargetUrl() ).find() ){
						if( !clearInterceptorSet.contains( regexInterceptorPair.getInterceptor().getClass()  ) ){
							Boolean isContinue = invokeBefore( regexInterceptorPair.getInterceptor(), RequesteEnvironmentHolder.getTargetUrl(), paramsBean );
							if( !isContinue ){
								throw new InterceptorNotAllowException( regexInterceptorPair.getInterceptor() );
							}
						}
					}
				}
			}
			
		}else{
			for( RegexInterceptorPair regexInterceptorPair: SystemConfiguration.getInterceptorChain().getInterceptors()){
				if( regexInterceptorPair.getRegix().matcher( RequesteEnvironmentHolder.getTargetUrl() ).find() ){
					Boolean isContinue = invokeBefore( regexInterceptorPair.getInterceptor(), RequesteEnvironmentHolder.getTargetUrl(), paramsBean );
					if( !isContinue ){
						throw new InterceptorNotAllowException( regexInterceptorPair.getInterceptor() );
					}
				}
			}
		}
		
	}
	
	/**
	 * 调用class上面的拦截器 Before方法
	 * @param cut
	 * @return
	 */
	public static void  classInterceptorBefore( Method targetMethod ,Object[] paramsBean ) throws InterceptorNotAllowException{
		if(  targetMethod.getDeclaringClass().isAnnotationPresent( Aspect.class )   ){
			Aspect aspect = targetMethod.getDeclaringClass().getAnnotation( Aspect.class );

			if( targetMethod.isAnnotationPresent( Clear.class ) ){
				Clear clear = targetMethod.getDeclaredAnnotation( Clear.class );
				
				Class<? extends Interceptor>[] interceptorArray =  aspect.value();
				for( int i = 0;i<interceptorArray.length ; i++){
						Boolean isClear = false;
						
						for( Class<? extends Interceptor>  clearInterceptor: clear.value()){
							if( clearInterceptor.equals( interceptorArray[i] ) ){
								isClear = true;
								break;
							}
						}
						if( !isClear && clear.value().length != 0 ){
							Interceptor interceptor;
							try {
								interceptor = interceptorArray[i].newInstance();
								if( !invokeBefore( interceptor, RequesteEnvironmentHolder.getTargetUrl()  ,paramsBean ) ){
									throw new InterceptorNotAllowException(interceptor );
								}
							} catch (InstantiationException | IllegalAccessException e) {
								e.printStackTrace();
							}
							
						}
				}
				
			}else{
				Class<? extends Interceptor>[] interceptorArray =  aspect.value();
				for( int i = 0;i<interceptorArray.length ; i++){
					try {
						Interceptor interceptor = interceptorArray[i].newInstance();
						if( !invokeBefore( interceptor, RequesteEnvironmentHolder.getTargetUrl()  ,paramsBean ) ){
							throw new InterceptorNotAllowException(interceptor );
						}
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
	/**
	 * 调用action前面的拦截器
	 * @author ZHANGYUKUN
	 * @param targetMethod
	 * @param paramsBean
	 * @throws InterceptorNotAllowException
	 */
	public static void  actionInterceptorBefore( Method targetMethod ,Object[] paramsBean ) throws InterceptorNotAllowException{
		if(  targetMethod.isAnnotationPresent( Aspect.class ) ){
			Aspect aspect = targetMethod.getAnnotation( Aspect.class );
			
			for( Class<? extends Interceptor> interceptorClass: aspect.value()){
				try {
					Interceptor interceptor = interceptorClass.newInstance();
					if( !invokeBefore(interceptor,  RequesteEnvironmentHolder.getTargetUrl() , paramsBean) ){
						throw new InterceptorNotAllowException(interceptor );
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	/**
	 * action 调用拦截器后
	 * @author ZHANGYUKUN
	 * @param targetMethod
	 * @param reuslt
	 * @throws InterceptorNotAllowException 
	 */
	public static void actionInterceptorAfter(Method targetMethod, Object reuslt) throws InterceptorNotAllowException {
		if(  targetMethod.isAnnotationPresent( Aspect.class ) ){
			Aspect aspect = targetMethod.getAnnotation( Aspect.class );
			
			for( Class<? extends Interceptor> interceptorClass: aspect.value()){
				try {
					Interceptor interceptor = interceptorClass.newInstance();
					if( !invokeAfter(interceptor, reuslt ) ){
						throw new InterceptorNotAllowException(interceptor );
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	/**
	 * class过滤器调用后
	 * @author ZHANGYUKUN
	 * @param targetMethod
	 * @param reuslt
	 * @throws InterceptorNotAllowException 
	 */
	public static void classInterceptorAfter(Method targetMethod, Object reuslt) throws InterceptorNotAllowException {
		
		if(  targetMethod.getDeclaringClass().isAnnotationPresent( Aspect.class )   ){
			Aspect aspect = targetMethod.getDeclaringClass().getAnnotation( Aspect.class );

			if( targetMethod.isAnnotationPresent( Clear.class ) ){
				Clear clear = targetMethod.getDeclaredAnnotation( Clear.class );
				
				Class<? extends Interceptor>[] interceptorArray =  aspect.value();
				for( int i = 0;i<interceptorArray.length ; i++){
						Boolean isClear = false;
						
						for( Class<? extends Interceptor>  clearInterceptor: clear.value()){
							if( clearInterceptor.equals( interceptorArray[i] ) ){
								isClear = true;
								break;
							}
						}
						if( !isClear && clear.value().length != 0 ){
							Interceptor interceptor;
							try {
								interceptor = interceptorArray[i].newInstance();
								if( !invokeAfter( interceptor, reuslt) ){
									throw new InterceptorNotAllowException(interceptor );
								}
							} catch (InstantiationException | IllegalAccessException e) {
								e.printStackTrace();
							}
							
							
						}
					
				}
				
			}else{
				Class<? extends Interceptor>[] interceptorArray =  aspect.value();
				for( int i = 0;i<interceptorArray.length ; i++){
					try {
						Interceptor interceptor = interceptorArray[i].newInstance();
						if( !invokeAfter( interceptor, reuslt)  ){
							throw new InterceptorNotAllowException(interceptor );
						}
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
	/**
	 * 全局过滤器After
	 * @param targetMethod
	 * @param paramsBean
	 * @throws InterceptorNotAllowException 
	 */
	public static void globalInterceptorAfter(Method targetMethod , Object reuslt) throws InterceptorNotAllowException {
		
		if( targetMethod.getDeclaringClass().isAnnotationPresent( Clear.class ) ||  targetMethod.isAnnotationPresent( Clear.class ) ){
			
			Set< Class<? extends Interceptor>> clearInterceptorSet = new HashSet<>();
			
			if( targetMethod.getDeclaringClass().isAnnotationPresent( Clear.class  ) ){
				Clear classClear = targetMethod.getDeclaringClass().getAnnotation( Clear.class );
				Class<? extends Interceptor>[] classClearArray = classClear.value();
				for(Class<? extends Interceptor>  interceptorcls:classClearArray){
					clearInterceptorSet.add( interceptorcls );
				}
			}
			
			if( targetMethod.isAnnotationPresent( Clear.class  ) ){
				Clear methodClear = targetMethod.getAnnotation( Clear.class );
				Class<? extends Interceptor>[] methodClearArray = methodClear.value();
				for(Class<? extends Interceptor>  interceptorcls:methodClearArray){
					clearInterceptorSet.add( interceptorcls );
				}
			}
			
			if( clearInterceptorSet.size() !=0  ){
				
				for( RegexInterceptorPair regexInterceptorPair: SystemConfiguration.getInterceptorChain().getInterceptors()){
					if( regexInterceptorPair.getRegix().matcher( RequesteEnvironmentHolder.getTargetUrl() ).find() ){
						if( !clearInterceptorSet.contains( regexInterceptorPair.getInterceptor().getClass()  ) ){
							Boolean isContinue = invokeAfter( regexInterceptorPair.getInterceptor(), reuslt );
							if( !isContinue ){
								throw new InterceptorNotAllowException( regexInterceptorPair.getInterceptor() );
							}
						}
					}
				}
			}
			
		}else{
			for( RegexInterceptorPair regexInterceptorPair: SystemConfiguration.getInterceptorChain().getInterceptors()){
				if( regexInterceptorPair.getRegix().matcher( RequesteEnvironmentHolder.getTargetUrl() ).find() ){
					Boolean isContinue = invokeAfter( regexInterceptorPair.getInterceptor(), reuslt );
					if( !isContinue ){
						throw new InterceptorNotAllowException( regexInterceptorPair.getInterceptor() );
					}
				}
			}
		}
		
	}
	
	/**
	 * 调用过滤器的Before方法
	 * @author ZHANGYUKUN
	 * @param interceptor
	 */
	private static boolean invokeBefore( Interceptor interceptor ,String urlTarget ,Object[] paramsBean ){
		return interceptor.before(  urlTarget, paramsBean );
	}
	
	/**
	 * 调用过滤器的after方法
	 * @author ZHANGYUKUN
	 * @param interceptor
	 */
	private static boolean invokeAfter(Interceptor interceptor, Object result ){
		return interceptor.after( result );
	}

}
