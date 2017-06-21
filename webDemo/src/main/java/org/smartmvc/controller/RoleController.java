package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Param;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;

@Controller
public class RoleController {
	
	@Action("add")
	public String add(){
		
		return "OK";
	}
	
	@Action("delete")
	public String delete(){
		
		return "OK";
	}
	
	@Action("update")
	public String update(){
		
		return "OK";
	}
	
	@Action
	public String list(@Param(required=true) int id  ){
		
		return "OK";
	}
	
	@Action("listB")
	@Response(type=ResponseType.JSON)
	public String list2(int id   ){
		
		return "OK";
	}

}
