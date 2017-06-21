package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;

/**
 * 指定模块名为cn,不指定的时候默认是类名小写，去掉不区分大小写的Controller
 * @author ZHANGYUKUN
 *
 */
@Controller("cn")
public class ControllerNameController {
	
	@Action("t1")
	@Response(type=ResponseType.JSON)
	public String responseDefault(){
		return "responseDefault";
	}
	
	


}
