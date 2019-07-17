package com.hs.o2o.dao;

import com.hs.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * author heshang.ink
 */
public interface ProductCategoryDao {
	/**
	 * 通过shop id查询店铺商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);

	/**
	 * 批量添加商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	/**
	 * 删除指定商品类别
	 * @param productCategory
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
