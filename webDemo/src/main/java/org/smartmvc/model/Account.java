package org.smartmvc.model;

public class Account {
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + "]";
	}
	private long id;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
