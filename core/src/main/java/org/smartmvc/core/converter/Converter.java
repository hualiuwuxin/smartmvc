package org.smartmvc.core.converter;

/**
 * 转化器
 * @author ZHANGYUKUN
 *
 * @param <T>
 */
public abstract class Converter<T> {
	
	private Class<T> targetCls;

	public abstract T conver(Object source) throws Exception ;

	public Class<T> getTargetCls() {
		return targetCls;
	}

	public void setTargetCls(Class<T> targetCls) {
		this.targetCls = targetCls;
	}
	
}
