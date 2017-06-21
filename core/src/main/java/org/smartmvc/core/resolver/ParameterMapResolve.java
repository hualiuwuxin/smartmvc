package org.smartmvc.core.resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.smartmvc.core.envi.RequesteEnvironmentHolder;
import org.smartmvc.core.envi.SystemConstants;

/**
 * 解析参数map
 * @author ZHANGYUKUN
 *
 */
public class ParameterMapResolve {

	/**
	 * 解析参数map 得到 集合 map key 对应的 最大长度,和最大深度
	 * @author ZHANGYUKUN
	 * @param parameterMap
	 */
	public static Map<String,Integer> resolveParameterMap( Map<String,String[]> parameterMap ){
		Map<String,Integer> parameterArrayMaxSubscript = new HashMap<>();
		
		for( String key  :parameterMap.keySet() ){
			calculateMaxSubscript( parameterArrayMaxSubscript ,key);
			int maxLevel = key.split("\\.").length;
			
			if(  RequesteEnvironmentHolder.getParameterMaxLevel() == null || RequesteEnvironmentHolder.getParameterMaxLevel() < maxLevel ){
				RequesteEnvironmentHolder.setParameterMaxLevel( maxLevel );
			}
		}
		
		return parameterArrayMaxSubscript;
	}
	
	
	
	
	/**
	 * 得到参数key最大的下标
	 * @author ZHANGYUKUN
	 * @param maxSubscript
	 * @param key
	 */
	private static void calculateMaxSubscript(Map<String,Integer> parameterArrayMaxSubscript,String key){
		
		Matcher matcher = SystemConstants.SUBSCRIPTPATTERN.matcher( key );
		
		String subKey;
		String subscriptStr;
		Integer subscript;
		while( matcher.find() ){
			subKey = key.substring(0,matcher.start() );
			subscriptStr = key.substring(matcher.start()+1,matcher.end() -1 );
			subscript = Integer.valueOf( subscriptStr );
			
			if( parameterArrayMaxSubscript.get( subKey ) == null || parameterArrayMaxSubscript.get( subKey ) <   subscript+1 ){
				parameterArrayMaxSubscript.put( subKey , subscript+1 );
			}
			
			if( RequesteEnvironmentHolder.getMaxSubscript() == null || RequesteEnvironmentHolder.getMaxSubscript() <   subscript+1 ){
				RequesteEnvironmentHolder.setMaxSubscript( subscript+1 );
			}
			
		}
		
	}
	
	
	
	
}
