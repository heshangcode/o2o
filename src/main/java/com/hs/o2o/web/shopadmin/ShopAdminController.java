package com.hs.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * author heshang.ink
 */
@Controller
@RequestMapping(value = "shopadmin",method = {RequestMethod.GET})
/**
 * 主要用来解析路由并转发到相应的HTML中
 */
public class ShopAdminController {

	/*@GetMapping("/login")
	public String login() {
		return "shop/login";
	}*/
	/**
	 * 转发至店铺注册/编辑页面
	 * @return
	 */
	@GetMapping("/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}

	/**
	 * 转发至店铺列表页面
	 * @return
	 */
	@GetMapping("/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}

	/**
	 * 转发至店铺管理页面
	 * @return
	 */
	@GetMapping("/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}

	/**
	 * 转发至商品类别管理页面
	 * @return
	 */
	@GetMapping("/productcategorymanagement")
	public String productCategoryManagement() {
		return "shop/productcategorymanagement";
	}

	/**
	 * 转发至商品添加/编辑页面
	 * @return
	 */
	@GetMapping("/productoperation")
	public String productOperation() {
		return "shop/productoperation";
	}

	/**
	 * 转发至商品管理页面
	 * @return
	 */
	@GetMapping("/productmanagement")
	public String productManagement() {
		return "shop/productmanagement";
	}
}
