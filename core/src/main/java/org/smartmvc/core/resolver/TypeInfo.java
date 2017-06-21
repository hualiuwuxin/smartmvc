package org.smartmvc.core.resolver;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeInfo implements Serializable{
	
	private static final long serialVersionUID = -2461053589796511069L;


	private Object typeBean;
	
	private Type type;
	
	private Class<?> cls;
	
	private boolean haveInnerType;
	
	private List<TypeInfo> innerType  = new ArrayList<>();

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<TypeInfo> getInnerType() {
		return innerType;
	}

	public void setInnerType(List<TypeInfo> innerType) {
		this.innerType = innerType;
	}
	
	public Object getTypeBean() {
		return typeBean;
	}

	public void setTypeBean(Object typeBean) {
		if( typeBean != null ){
			this.cls = typeBean.getClass();
		}
		this.typeBean = typeBean;
	}

	public boolean isHaveInnerType() {
		return haveInnerType;
	}

	public void setHaveInnerType(boolean haveInnerType) {
		this.haveInnerType = haveInnerType;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}


}
