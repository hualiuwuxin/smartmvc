package org.smartmvc.controller;

import java.util.Set;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.model.User;

@Controller
public class SetController {
	
	/**set和list 类似 ，无序，所以 没有指定元素不会用null 占位置
	 * 参数例子:set[5]=5&set[3]=3
	 * 装着基本对象的set
	 * @param set
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public Set<String>  t1(Set<String>  set  ){
		System.out.println( set );
		return set;
	}

	/**
	 * 参数例子set[0].name=name1&set[1].name=name2&set[0].age=1&set[1].age=2
	 * set 和list 参不多，后面就不一一 举例子了
	 * @param set
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public Set<User>  t2(Set<User>  set  ){
		System.out.println( set );
		return set;
	}

}
