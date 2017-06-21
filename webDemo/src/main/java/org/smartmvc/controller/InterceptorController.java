package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Aspect;
import org.smartmvc.core.annotation.Clear;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.interceptor.ActionInterceptor;
import org.smartmvc.interceptor.ControllerInterceptor;

@Controller("interceptor")


/**
 * 过滤器的执行顺序 全局过滤器 > 类控制器 >方法过滤器(类似于回链的执行方式，但是内部实现并不是回链，目的是做到 和方法过滤器格式统一)
 */

/**
 * 为之歌控制器的每一个方法指定了一个过滤器ControllerInterceptor
 * @author ZHANGYUKUN
 *
 */
@Aspect(ControllerInterceptor.class)
public class InterceptorController {
	
	/**
	 * 指定了一个方法过滤器
	 * @param name
	 * @return
	 */
	@Action
	@Aspect(ActionInterceptor.class)
	@Response(type=ResponseType.JSON)
	public String t1(  ){
		
		System.out.println( "t1执行");
		
		return "OK";
	}
	
	/**
	 * 指定了方法过滤器，但是清楚掉了上层过滤器
	 * @author ZHANGYUKUN
	 * @param name
	 * @return
	 */
	@Action
	@Clear(value={ControllerInterceptor.class})
	@Aspect(ActionInterceptor.class)
	@Response(type=ResponseType.JSON)
	public String t2(){
		
		System.out.println( "t2执行");
		
		return "OK";
	}
	
	/**
	 * @Clear()不指定清楚对象的时候默认清楚全部过滤器，但是@Clear() 永远只能清楚 上层 过滤器，不能清楚本层过滤器，因为完全没有必要清楚本层，本层直接在 @Aspect 中少些一个 就是最好的清除本层
	 * @return
	 */
	@Action
	@Clear()
	@Aspect(ActionInterceptor.class)
	@Response(type=ResponseType.JSON)
	public String t3(){
		
		System.out.println( "t2执行");
		
		return "OK";
	}
	
	
	
	


}
