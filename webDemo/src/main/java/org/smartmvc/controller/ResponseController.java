package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.model.Account;

@Controller("response")
public class ResponseController {
	
	
	/**
	 * 指定返回json
	 * @return
	 */
	@Action("responseJson")
	@Response(type=ResponseType.JSON)
	public Account responseJson(){
		Account account = new Account();
		account.setId(1);
		account.setName( "responseJson" );
		
		return account;
	}
	
	/**
	 * 指定返回jsp
	 * @return
	 */
	@Action("responseJSP")
	@Response(type=ResponseType.JSP)
	public String responseJSP(){
		
		return "responseJSPPage";
	}
	
	/**
	 * 默认使用 配置文件里面的 getDefaultViewPostfix 的返回类型（不配置默认jsp）
	 * @return
	 */
	@Action("responseDefault")
	public String responseDefault(){
		return "responseDefault";
	}
	
	


}
