package com.hs.o2o.entity;

import java.util.Date;

/**
 * 店铺类别
 */
public class ShopCategory {

	//店铺类别的ID
	private Long shopCategoryId;
	//店铺类别的名称
	private String shopCategoryName;
	//店铺类别描述
	private String shopCategoryDesc;
	//店铺类别图片的链接地址
	private String shopCategoryImg;
	//权重
	private Integer priority;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;
	//上级ID(实体类)
	private ShopCategory parentId;

	public Long getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public String getShopCategoryName() {
		return shopCategoryName;
	}

	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}

	public String getShopCategoryDesc() {
		return shopCategoryDesc;
	}

	public void setShopCategoryDesc(String shopCategoryDesc) {
		this.shopCategoryDesc = shopCategoryDesc;
	}

	public String getShopCategoryImg() {
		return shopCategoryImg;
	}

	public void setShopCategoryImg(String shopCategoryImg) {
		this.shopCategoryImg = shopCategoryImg;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public ShopCategory getParentId() {
		return parentId;
	}

	public void setParentId(ShopCategory parentId) {
		this.parentId = parentId;
	}

}
