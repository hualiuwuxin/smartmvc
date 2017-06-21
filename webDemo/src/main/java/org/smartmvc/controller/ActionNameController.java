package org.smartmvc.controller;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.model.Account;

@Controller("actionName")
public class ActionNameController {
	
	
	/**
	 * 不指定 action 的 名字 默认使用 方法名(但是没有指定@Action的方法不会被影射)
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public Account a1(){
		Account account = new Account();
		account.setId(1);
		account.setName( "a1" );
		return account;
	}


}
