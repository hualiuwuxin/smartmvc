package org.smartmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.smartmvc.core.annotation.Action;
import org.smartmvc.core.annotation.Aspect;
import org.smartmvc.core.annotation.Clear;
import org.smartmvc.core.annotation.Controller;
import org.smartmvc.core.annotation.Param;
import org.smartmvc.core.annotation.Response;
import org.smartmvc.core.annotation.em.ResponseType;
import org.smartmvc.interceptor.ActionInterceptor;
import org.smartmvc.interceptor.ControllerInterceptor;
import org.smartmvc.interceptor.UserInterceptor;
import org.smartmvc.model.User;

@Controller("user")
@Clear(UserInterceptor.class)
@Aspect( ControllerInterceptor.class )
public class UserController {
	
	
	@Action("add")
	public String add(){
		
		return "addPage";
	}
	
	@Action("delete")
	@Response(type=ResponseType.JSON)
	public User delete(){
		User user = new User();	
		user.setAge(26);
		user.setId(1l);
		user.setName("zyk");
		
		return user;
	}
	
	@Action("update")
	public String update(){
		
		return "OK";
	}
	
	@Action
	@Response
	public List<User> list(){
		List<User> list = new ArrayList<>();
		
		User user = new User();	
		user.setAge(26);
		user.setId(1l);
		user.setName("zyk");
		
		list.add(  user );
		
		
		return list;
	}
	
	@Action
	@Response
	@Clear(value=ActionInterceptor.class)
	@Aspect( ActionInterceptor.class )
	public List<User> list2(
			String [] names,
			User pUser,
			List<String> pList,
			Set<String> set,
			Map<String,String> map,
			@Param("id") String id ,
			@Param("int1") Integer  int1,
			@Param("int2")  int int2 ,
			@Param("b1") Boolean b1,
			@Param("b2") boolean b2 ,
			@Param("f1") float f1,
			@Param("d1") double d1){
		
		
		System.out.println( "names:" +  names );
		System.out.println( "pUser:" +  pUser );
		System.out.println( "pList:" +  pList );
		System.out.println( "set:" +  set );
		System.out.println( "map:" +  map );
		System.out.println( "id:" +  id );
		System.out.println( "int1:" +  int1 );
		System.out.println( "int2:" +  int2 );
		System.out.println( "b1:" +  b1 );
		System.out.println( "b2:" +  b2 );
		System.out.println( "f1:" +  f1 );
		System.out.println( "d1:" +  d1 );
		
		return null;
	}
	
	@Action
	@Response
	public List<User> list3(List<User> list){
		System.out.println("list" + list);
		/*for(User user :  list){
			System.out.println( user );
		}*/
		
		return list;
	}
	
	@Action
	@Response
	public Set<User> list4(Set<User> set){
		
		System.out.println(  set.size() );
		
		return set;
	}
	
	@Action
	@Response
	public User[] list5(User[] users){
		
		System.out.println( users+"list5" );
		
		return users;
	}
	
	@Action
	@Response
	public <N extends User> N[] list6(N[] users){
		
		System.out.println( users+"list6" );
		
		return users;
	}
	
	@Action
	@Response
	public <N extends User> N list7(N users){
		
		System.out.println( users+"list7" );
		
		return users;
	}
	
	@Action
	@Response
	public User list8(User users){
		
		System.out.println( users+"list8" );
		
		return users;
	}
	
	@Action
	@Response
	public <N> N list9(N users){
		
		System.out.println( users+"list9" );
		
		return users;
	}
	
	@Action
	@Response
	public Set<User> list10(Set<User> users){
		
		System.out.println( users+"list10" );
		
		return users;
	}
	
	@Action
	@Response
	public Set<? extends User> list11(Set<? extends User> users){
		
		System.out.println( users+"list11" );
		
		return users;
	}
	
	@Action
	@Response
	public Set<? super User> list12(Set<? super User> users){
		
		System.out.println( users+"list12" );
		
		return users;
	}
	
	@Action
	@Response
	public String list13( String a ){
		
		System.out.println( a );
		
		return a;
	}
	
	@Action
	@Response
	public String list14( Map<String,User> map,Map map2 ){
		
		System.out.println( map );
		System.out.println( map2 );
		
		return "OK";
	}
	
	@Action
	@Response
	public String list15( User[] users ){
		
		System.out.println( users.length );
		for(User user :  users ){
			System.out.println( user );
		}
		return "OK";
	}
	
	
	@Action
	@Response
	public String list16( Map<String,Map<String,Map<String,Map<String,User>>>> map ){
		
		System.out.println( map );
		
		return "OK";
	}
	
	
	@Action
	@Response
	public String list17( List<User>  list){
		
		for( User user: list){
			System.out.println(user );
		}
		return "OK";
	}
	
	@Action
	@Response
	public String list18( List<List<User>>  list){
		
		for( List<User> item1 : list){
			System.out.println("--------------------------");
			for( User user: item1){
				System.out.println(user );
			}
		}
		
		return "OK";
	}
	
	@Action
	@Response
	public String list19( Set<Set<User>>  set){
		for( Set<User> item1 : set ){
			System.out.println("--------------------------");
			for( User user: item1){
				System.out.println(user );
			}
		}
		
		return "OK";
	}
	
	@Action
	@Response
	public String list20( Set<String>  set){
		System.out.println( set );
		
		return "OK";
	}
	
	@Action
	@Response
	public String list21( @Param("asdas")List<String>  list){
		System.out.println( list );
		
		return "OK";
	}
	
	@Action
	@Response
	public <Q extends List<String>> String list22( Q[] lists){
		for( List<String> list: lists){
			for(String v: list ){
				System.out.println("v" + v );
			}
		}
		
		return "OK";
	}
	
	@Action
	@Response
	public <N> String list23( N list){
		System.out.println( list );
		
		return "OK";
	}
	
	@Action
	@Response
	public  String list24( List<? extends User> list){
		for(User user : list ){
			System.out.println( user );
		}
		
		return "OK";
	}
	
	
	@Action
	@Response
	public  String list25( Date date){
		System.out.println("传过来的日期是:"+date);
		return "OK";
	}
	

}
