package com.hs.o2o.dao;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.Area;
import com.hs.o2o.entity.PersonInfo;
import com.hs.o2o.entity.Shop;
import com.hs.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class ShopDaoTest extends BaseTest {

	@Autowired
	private ShopDao shopDao;

	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 2);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表的大小:"+shopList.size());
		System.out.println("店铺总数:"+count);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		shopList = shopDao.queryShopList(shopCondition, 0, 1);
		count = shopDao.queryShopCount(shopCondition);
		System.out.println("xin店铺列表的大小:"+shopList.size());
		System.out.println("xin店铺总数:"+count);
	}

	@Test
	public void testQueryShopById() {
		long shopId = 1;
		Shop shop = shopDao.queryShopById(shopId);
		System.out.println("AreaId:" + shop.getArea().getAreaId());
		System.out.println("AreaName:" + shop.getArea().getAreaName());
	}

	@Test
	//@Ignore //忽略这个测试，相当于注释了这个方法
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int effectNum = shopDao.insertShop(shop);
		//断言
		Assert.assertEquals(1, effectNum);
	}

	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectNum = shopDao.updateShop(shop);
		//断言
		Assert.assertEquals(1, effectNum);

	}
}
