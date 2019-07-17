package com.hs.o2o.service;

import com.hs.o2o.entity.PersonInfo;

public interface LoginService {
	/**
	 * 登录操作
	 * @param
	 * @param password
	 * @return
	 */
	PersonInfo getLogin(String account, String password);
}
