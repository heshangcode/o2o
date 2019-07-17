package com.hs.o2o.service;

import com.hs.o2o.BaseTest;
import com.hs.o2o.dto.ImageHolder;
import com.hs.o2o.dto.ShopExecution;
import com.hs.o2o.entity.Area;
import com.hs.o2o.entity.PersonInfo;
import com.hs.o2o.entity.Shop;
import com.hs.o2o.entity.ShopCategory;
import com.hs.o2o.enums.ShopStateEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 2, 2);
		System.out.println("店铺列表数为："+se.getShopList().size());
		System.out.println("店铺总数为："+se.getCount());
	}
	@Test
	public void testGetByShopId() {
		long shopId = 1;
		System.out.println(shopService.getByShopId(shopId).getShopName());
	}

	@Test
	public void testModifyShop() throws Exception {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("F:/idea-Javaworkspace/SSMlearn/o2o/src/test/resources/watermark.png");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("watermark.png", is);
		ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
		System.out.println(shopExecution.getShop().getShopImg());
	}
	@Test
	public void testAddShop() throws Exception {
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
		shop.setShopName("测试的店铺1");
		shop.setShopDesc("test1");
		shop.setShopAddr("test1");
		shop.setPhone("test1");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		//读取文件
		File shopImg = new File("F:/idea-Javaworkspace/SSMlearn/o2o/src/test/resources/zang.png");
		//File转inputstream
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
		ShopExecution se = shopService.addShop(shop, imageHolder);
		//断言
		Assert.assertEquals(ShopStateEnum.CHECK.getState(),se);
	}


}
