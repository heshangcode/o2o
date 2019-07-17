package com.hs.o2o.dao;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.Product;
import com.hs.o2o.entity.ProductCategory;
import com.hs.o2o.entity.ProductImg;
import com.hs.o2o.entity.Shop;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testInsertProduct() {
		Shop shop1 = new Shop();
		shop1.setShopId(1L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(1L);
		// 初始化三个商品实例并添加进shopId为1的店铺里，
		// 同时商品类别Id也为1
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setImgAddr("test1");
		product1.setPriority(1);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试Desc2");
		product2.setImgAddr("test2");
		product2.setPriority(2);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc1);
		Product product3 = new Product();
		product3.setProductName("test3");
		product3.setProductDesc("测试Desc3");
		product3.setImgAddr("test3");
		product3.setPriority(3);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setShop(shop1);
		product3.setProductCategory(pc1);
		// 判断添加是否成功
		int effectedNum = productDao.insertProduct(product1);
		Assert.assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product2);
		Assert.assertEquals(1, effectedNum);
		effectedNum = productDao.insertProduct(product3);
		Assert.assertEquals(1, effectedNum);
	}

	@Test
	public void testQueryProductByProductId() {
		long productId = 1;
//		 初始化两个商品详情图实例作为productId为1的商品下的详情图片
//		 批量插入到商品详情图表中
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(productId);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(productId);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		Assert.assertEquals(2, effectedNum);
//		查询productId为1的商品信息并校验返回的详情图实例列表size是否为2
		Product product = productDao.queryProductByProductId(productId);
		Assert.assertEquals(2, product.getProductImgList().size());
//		删除新增的这两个商品详情图实例
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		Assert.assertEquals(2, effectedNum);
	}

	@Test
	public void testUpdateProduct() {
		Product product = new Product();
		ProductCategory pc = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(1L);
		pc.setProductCategoryId(2L);
		product.setProductId(1L);
		product.setShop(shop);
		product.setProductName("第二个产品");
		product.setProductCategory(pc);
//		修改productId为1的商品的名称
//		以及商品类别并校验影响的行数是否为1
		int effectedNum = productDao.updateProduct(product);
		Assert.assertEquals(1, effectedNum);
	}

	@Test
	public void testQueryProductList() throws Exception {
		Product productCondition = new Product();
//		分页查询
		List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
		Assert.assertEquals(3, productList.size());
//		查询商品总数
		int count = productDao.queryProductCount(productCondition);
		Assert.assertEquals(8, count);
//		使用商品名称模糊查询，
		productCondition.setProductName("测试");
		productDao.queryProductList(productCondition, 0, 3);
		Assert.assertEquals(3, productList.size());
		count = productDao.queryProductCount(productCondition);
		Assert.assertEquals(4, count);
	}

	@Test
	public void testUpdateProductCateoryToNull() {
//		将productCategoryId为2的商品类别下面的商品的商品类别置为空
		int effectedNum = productDao.updateProductCategoryToNull(2L);
		Assert.assertEquals(1, effectedNum);
	}
}
