package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;

@Controller()
public class ExceptionController {
	
	
	/**
	 * 不指定 action 的 名字 默认使用 方法名(但是没有指定@Action的方法不会被影射)
	 * @return
	 * @throws Exception 
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public String e1(int i) throws Exception{
		if( i == 10  ){
			throw  new Exception("我的testException");
		}
		return "OK";
	}

	


}
