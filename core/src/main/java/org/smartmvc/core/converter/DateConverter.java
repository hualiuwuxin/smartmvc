package org.smartmvc.core.converter;

import java.text.ParseException;
import java.util.Date;

import org.smartmvc.core.envi.SystemConfiguration;

/**
 * 默认日期转化器
 * @author ZHANGYUKUN
 *
 */
public class DateConverter extends Converter<Date> {

	@Override
	public Date conver(Object source) throws ParseException {
		if( ! (source instanceof String) ){
			return null;
		}
		String strSource = (String) source;
		
		Date date = null;
		date = SystemConfiguration.getDefaultDateFormat().parse(  strSource );
		
		return date;
	}

}
