package com.hs.o2o.service;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest {

	@Autowired
	private AreaService areaService;

	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaService.getAreaList();
		//断言测试
		Assert.assertEquals("西苑",areaList.get(0).getAreaName());
	}

}
