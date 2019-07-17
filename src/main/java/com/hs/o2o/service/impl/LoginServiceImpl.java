package com.hs.o2o.service.impl;

import com.hs.o2o.dao.PersonInfoDao;
import com.hs.o2o.entity.PersonInfo;
import com.hs.o2o.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	PersonInfoDao personInfoDao;

	@Override
	public PersonInfo getLogin(String account, String password) {
		return personInfoDao.getPersonInfo(account, password);
	}
}
