package org.smartmvc.controller;

import java.util.Map;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.model.User;

@Controller("map")
public class MapController {
	
	/**
	 * 参数格式大概就是  map.key1=v1&map.key2=v2
	 * 
	 * 自定义javaBean 对象 参数  
	 * @param user
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public Map<String,String> t1(Map<String,String>  map ){
		System.out.println( map );
		return map;
	}
	
	
	
	/**
	 * 参数格式大概就是  map.key1.kk1=v1&map.key2.kk2=v2
	 * 
	 * 多层map(框架可以解析多层泛型嵌套的类型)
	 * 自定义javaBean 对象 参数  
	 * @param user
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public Map<String,Map<String,String>>  t2(Map<String,Map<String,String>>  map ){
		System.out.println( map );
		return map;
	}
	
	/**
	 *参数格式大概就是  map.k1.name=name1&map.k2.name=name2&&map.k2.age=99
	 * 
	 * 装着对象的map
	 * @param map
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public Map<String,User>  t3(Map<String,User>  map ){
		System.out.println( map );
		return map;
	}

	
	/**
	 * 参数格式大概是：map.k1.name=name1&map.k2.name=name2&&map.k2.age=99
	 * 处理不指定明确泛型参数的map(直接把除去name 的 key 完整的 当做 map的key)
	 * @param map
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public Map  t4(Map  map ){
		System.out.println( map );
		return map;
	}

}
