package com.hs.o2o.service;

import com.hs.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
	/**
	 * 根据查询条件获取ShopCategory列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
