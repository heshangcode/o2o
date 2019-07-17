package com.hs.o2o.service.impl;

import com.hs.o2o.dao.ShopDao;
import com.hs.o2o.dto.ImageHolder;
import com.hs.o2o.dto.ShopExecution;
import com.hs.o2o.entity.Shop;
import com.hs.o2o.enums.ShopStateEnum;
import com.hs.o2o.exceptions.ShopOperationException;
import com.hs.o2o.service.ShopService;
import com.hs.o2o.util.ImageUtil;
import com.hs.o2o.util.PageCalculator;
import com.hs.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	/**
	 * 分页查询店铺，可输入的条件有：店铺名(模糊)，店铺状态，店铺类别，区域ID，owner
	 * @param shopCondition
	 * @param pageIndex 第几页
	 * @param pageSize  一页多少条数据
	 * @return
	 */
	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		//获取从第几行开始取数据
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		//获取shop的列表
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		//获取数据的数量
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryShopById(shopId);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {
			//1.判断是否需要处理图片
			try {
				if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
//					首先查出需要修改的数据
					Shop tempShop = shopDao.queryShopById(shop.getShopId());
					//判断图片是否有
					if (tempShop.getShopImg() != null) {
//						如果有，则删除图片
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, thumbnail);
				}
				//2.更新店铺信息
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
//				System.out.println("------"+effectedNum);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryShopById(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				}
			} catch (Exception e) {
				throw  new ShopOperationException("modifyShop error:" + e.getMessage());
			}
		}
	}

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
		//空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋初始值
			shop.setEnableStatus(1);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (thumbnail.getImage() != null) {
					//存储图片
					try {
						addShopImg(shop, thumbnail);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error:" + e.getMessage());
					}
					//更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	/**
	 * 存储图片
	 * @param shop
	 * @param thumbnail
	 */
	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		//获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);
	}
}
