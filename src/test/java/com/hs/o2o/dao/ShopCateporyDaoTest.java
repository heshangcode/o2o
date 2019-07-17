package com.hs.o2o.dao;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCateporyDaoTest extends BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;


	@Test
	public void testQueryShopCategory() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
		Assert.assertEquals(2, shopCategoryList.size());

		ShopCategory testCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(1L);
		testCategory.setParentId(parentCategory);
		shopCategoryList = shopCategoryDao.queryShopCategory(testCategory);
//		System.out.println(shopCategoryList.get(0).getShopCategoryName());
		Assert.assertEquals(1, shopCategoryList.size());


	}
}
