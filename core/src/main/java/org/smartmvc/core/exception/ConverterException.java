package org.smartmvc.core.exception;

import org.smartmvc.core.converter.Converter;

/**
 * 转换异常
 * @author ZHANGYUKUN
 *
 */
public class ConverterException extends Exception {
	private static final long serialVersionUID = -6583428616187673968L;
	
	private String parameterName;
	private String paramValue;
	@Override
	public String toString() {
		return "试图使用: " + converter.getClass() + " 对参数名为: "+ parameterName +" 参数值为: "+ paramValue +" 进行转化，但是转化失败 ";
	}


	private Converter<?> converter;

	public ConverterException(String parameterName,String paramValue,Converter<?> converter) {
		super();
		this.converter = converter;
		this.parameterName = parameterName;
		this.paramValue = paramValue;
	}


	public Converter<?> getConverter() {
		return converter;
	}

	public void setConverter(Converter<?> converter) {
		this.converter = converter;
	}


	public String getParameterName() {
		return parameterName;
	}


	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}


	public String getParamValue() {
		return paramValue;
	}


	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	

	
	
}
