package com.hs.o2o.dao;

import com.hs.o2o.entity.ProductImg;

import java.util.List;
/**
 * author heshang.ink
 */
public interface ProductImgDao {
	/**
	 * 某个图片的详情图列表
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);

	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);

	/**
	 * 删除指定商品下的所有详情图
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
