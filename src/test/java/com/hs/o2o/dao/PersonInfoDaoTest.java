package com.hs.o2o.dao;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonInfoDaoTest extends BaseTest {

	@Autowired
	private PersonInfoDao personInfoDao;

	@Test
	public void testGetPersonInfo() {
		String account = "1";
		String password = "1";
		PersonInfo personInfo = personInfoDao.getPersonInfo(account, password);
		System.out.println(personInfo);
	}
}
