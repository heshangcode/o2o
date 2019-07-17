package com.hs.o2o.web.shopadmin;

import com.hs.o2o.dto.ProductCategoryExecution;
import com.hs.o2o.dto.Result;
import com.hs.o2o.entity.ProductCategory;
import com.hs.o2o.entity.Shop;
import com.hs.o2o.enums.ProductCategoryStateEnum;
import com.hs.o2o.exceptions.ProductCategoryOperationException;
import com.hs.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * author heshang.ink
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 通过shopId获取到店铺类别
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/getproductcategorylist")
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		//实际开发中，这里可以移除，这是没有登录，不能获取到shopId，这是模拟一个session，to be removed
		/*Shop shop = new Shop();
		shop.setShopId(1L);
		request.getSession().setAttribute("currentShop", shop);*/

//从session中获取shopId
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true, list);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}
	}

	/**
	 * 批量添加商品分类
	 *
	 * @param productCategoryList
	 * @param request
	 * @return
	 */
	@PostMapping("/addproductcategorys")
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//不直接从前台传入shopId，是从session里获取，前后端分离，也为了一定的安全性
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		//遍历，设置每一个的shopId
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		//判断是否有数据
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}

		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		return modelMap;
	}

	/**
	 * 删除商品类别
	 * @param productCategoryId
	 * @param request
	 * @return
	 */
	@PostMapping("/removeproductcategory")
	@ResponseBody
	private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//判断是否有商品类别
		if (productCategoryId != null && productCategoryId > 0 ) {
			try {
				//从session获取到shop信息
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
//				执行删除操作，返回状态值
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
//				判断状态值，是否删除成功
				if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success",false);
			modelMap.put("errMsg", "请至少选择一个商品类别");
		}
		return modelMap;
	}
}
