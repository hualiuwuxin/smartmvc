package org.smartmvc.controller;

import java.util.Date;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.core.envi.SystemConfiguration;

@Controller
public class ConverterController {
	
	
	/**
	 * 参数例子: data=2013-01-01 11:11:11
	 * 在配置文件中注册了 转化器（converter.put( Date.class , new DateConverter() );），DateConverter是系统默认就有的。编写一个转化器很简单，继承抽象类 Converter就可以了
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public String date(Date date ){
		
		String rt = null;
		if( date != null ){
			rt = SystemConfiguration.getDefaultDateFormat().format(date) ;
			
		}
		 System.out.println(rt );	
		
		return rt;
	}


}
