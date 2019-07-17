package com.hs.o2o.entity;

/**
 * 用户
 */
public class PersonInfo {
	//ID
	private Long userId;

	//用户密码
	private String password;

	//用户姓名
	private String name;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PersonInfo{" +
				"userId=" + userId +
				", password='" + password + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
