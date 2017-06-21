package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Param;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;

@Controller("param")
public class ParamController {
	
	/**
	 * 方法里面的参数默认可以不填
	 * @param name
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public String t1( String name ){
		System.out.println( "name:" + name );
		return name;
	}
	
	/**
	 * 加了 @Param  注解 依旧默认可以不填
	 * @param name
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public String t2(@Param String name ){
		System.out.println( "name:" + name );
		return name;
	}
	
	/**
	 * 可以指定 参数必填,没有填写必填参数会抛出统一的异常 RequiredParamNotExistException ,可以在配置文件中 配置 统一异常处理类处理
	 * @param name
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public String t3(@Param(required=true) String name ){
		System.out.println( "name:" + name );
		return name;
	}
	
	/**
	 * 默认的参数名是 形参名，但是 我们可以指定一个不一样的名字  name 被指定为 myName
	 * @param name
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public String t4(@Param("myName")String name ){
		System.out.println( "name:" + name );
		return name;
	}
	
	


}
