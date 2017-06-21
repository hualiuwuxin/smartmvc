package org.smartmvc.core.exception;

/**
 * 必填参数不存在异常
 * @author ZHANGYUKUN
 *
 */
public class RequiredParamNotExistException extends Exception {
	private static final long serialVersionUID = -6583428616587673968L;
	
	private String parameName;

	public RequiredParamNotExistException(String parameName) {
		this.parameName = parameName;
	}

	public String getParameName() {
		return parameName;
	}

	public void setParameName(String parameName) {
		this.parameName = parameName;
	}

	@Override
	public String toString() {
		return "必填参数: "+parameName + "不存在";
	}
	
}
