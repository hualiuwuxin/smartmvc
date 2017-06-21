package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Aspect;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.interceptor.ControllerInterceptor;

@Controller("acc")
@Aspect( ControllerInterceptor.class )
public class AccountController {
	
	
	@Action("add")
	@Response(type=ResponseType.JSON)
	public String add(){
		
		return "addPage";
	}


}
