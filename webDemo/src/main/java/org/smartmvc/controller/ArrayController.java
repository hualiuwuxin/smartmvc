package org.smartmvc.controller;

import java.util.List;
import java.util.Map;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.model.User;

@Controller("array")
public class ArrayController {
	
	/**
	 * 参数 ：array[4]=5&array[2]=3
	 * 普通数组（没有指定的数组元素会被赋予null）
	 * @param array
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public int[] t1(int[]  array ){
		System.out.println( array );
		return array;
	}
	
	/**
	 * 参数:?array[1].name=zyk
	 * 对象数组
	 * @param array
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public User[]  t2(User[]  array ){
		System.out.println( array );
		return array;
	}
	
	/**
	 * 参数:?array[1].name=zyk
	 * 泛型数组(默认处理成泛型边界的上边界(一般不建议这样使用))
	 * @param array
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public <N extends User>  N[]  t3( N[]  array ){
		System.out.println( array );
		return array;
	}
	
	/**
	 * 参数:array[0].key1=zyk&array[1].key1=zyk2
	 * 
	 * 泛型数组的泛型边界带有泛型
	 * @param array
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public <N extends Map<String,String>>  N[]  t4( N[]  array ){
		System.out.println( array );
		return array;
	}
	
	/**
	 * 参数例子:array[1][1]=aaaa
	 * 在泛型数组中使用  边界泛型，并且上边界是 List
	 * @param array
	 * @return
	 */
	@Action
	@Response(type=ResponseType.JSON)
	public <N extends List<String>> N[] t5( N[] array){
		for( List<String> list: array){
			for(String v: list ){
				System.out.println("v" + v );
			}
		}
		
		return array;
	}

}
