package com.hs.o2o.dao;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void testQueryByShopId() {
		long shopId = 1;
		List<ProductCategory> productCategoriesList = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("该店铺自定义类别数为：" + productCategoriesList.size());
	}

	@Test
	public void testBatchInsertProductCategory() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1L);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(1L);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		Assert.assertEquals(2, effectedNum);
	}

	@Test
	public void testDeleteProductCategory() {
		long shopId = 1;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory pc : productCategoryList) {
			if ("商品类别1".equals(pc.getProductCategoryName()) || "商品类别2".equals(pc.getProductCategoryName())) {
				int effetedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), pc.getShopId());
				Assert.assertEquals(1, effetedNum);
			}
		}
	}
}
