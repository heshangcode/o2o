package com.hs.o2o.dao;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {

	@Autowired
	private AreaDao areaDao;

	@Test
	public void testQueryArea() {
		List<Area> areaList = areaDao.queryArea();
		//断言测试
		Assert.assertEquals(2,areaList.size());
	}
}
