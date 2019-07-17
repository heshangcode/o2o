package com.hs.o2o.dao;

import com.hs.o2o.BaseTest;
import com.hs.o2o.entity.ProductImg;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductImgDaoTest extends BaseTest {

	@Autowired
	private ProductImgDao productImgDao;

	@Test
	public void testBatchInsertProductImg() {
		// productId为1的商品里添加两个详情图片记录
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(1L);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		Assert.assertEquals(2,effectedNum);
	}

	@Test
	public void testDeleteProductImgByProductId() {
//		删除新增的两条商品详情图片记录
		long productId = 1;
		int effectedNum = productImgDao.deleteProductImgByProductId(productId);
		Assert.assertEquals(2,effectedNum);
	}
}
