package org.smartmvc.controller;

import java.util.List;
import java.util.Map;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.model.User;

@Controller
public class ListController {
	
	/**
	 * 参数例子:list[0]=true&list[5]=true
	 * 装着基本对象的list
	 * @param list
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public List<Boolean>  t1(List<Boolean> list  ){
		System.out.println( list );
		return list;
	}

	/**参数例子:list[1].name=zyk&list[1].age=88
	 * 装着自定义对象的List
	 * @param list
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public List<User>  t2(List<User> list  ){
		System.out.println( list );
		return list;
	}
	
	/**
	 * 参数例子:list[1].name=zyk&list[1].age=88
	 * 泛型参数是边界泛型的list(指定上边界)
	 * @param list
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public List<? extends User>  t3(List<? extends User> list  ){
		System.out.println( list );
		return list;
	}
	
	/**
	 * 参数例子:list[1].name=zyk&list[1].age=88
	 * 泛型参数是边界泛型的list(指定下边界)
	 * @param list
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public List<? super User>  t4(List<? super User> list  ){
		System.out.println( list );
		return list;
	}
	
	/**
	 * 参数例子:list[0][0][0].name=name1
	 * 多层泛型
	 * @param list
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public List<List<List<User>>>  t5(List<List<List<User>>> list  ){
		System.out.println( list );
		return list;
	}
	
	/**
	 *参数例子: list[0][0][0].k1.name=name1
	 * 多层泛型里面嵌套别的泛型
	 * @param list
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public List<List<List<Map<String,User>>>>  t6(List<List<List<Map<String,User>>>> list  ){
		System.out.println( list );
		return list;
	}

	
}
