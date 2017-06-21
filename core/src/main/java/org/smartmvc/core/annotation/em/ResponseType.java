package org.smartmvc.core.annotation.em;

public enum ResponseType {
	JSON("json"),JSP("jsp");
	
	ResponseType( String postfix ){
		this.postfix = postfix;
	}
	
	private String postfix;
	
	public String getPostfix() {
		return postfix;
	}
}
