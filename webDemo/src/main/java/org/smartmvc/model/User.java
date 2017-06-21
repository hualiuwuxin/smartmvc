package org.smartmvc.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 7680962205512312586L;

	
	private Long id;
	
	private String name;
	
	private Integer age;
	
	private boolean smart;

	private Account account;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public boolean isSmart() {
		return smart;
	}

	public void setSmart(boolean smart) {
		this.smart = smart;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", smart=" + smart + ", account=" + account + "]";
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


}
