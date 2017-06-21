package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.model.User;

@Controller("object")
public class ObjectController {
	
	/**
	 * 参数格式大概就是  user.name=name1&user.age=22&user.account.name=acname
	 * 
	 * 自定义javaBean 对象 参数  
	 * @param user
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public User t1(User  user ){
		System.out.println( user );
		return user;
	}


}
