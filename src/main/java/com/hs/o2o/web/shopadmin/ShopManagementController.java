package com.hs.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.o2o.dto.ImageHolder;
import com.hs.o2o.dto.ShopExecution;
import com.hs.o2o.entity.Area;
import com.hs.o2o.entity.PersonInfo;
import com.hs.o2o.entity.Shop;
import com.hs.o2o.entity.ShopCategory;
import com.hs.o2o.enums.ShopStateEnum;
import com.hs.o2o.service.AreaService;
import com.hs.o2o.service.ShopCategoryService;
import com.hs.o2o.service.ShopService;
import com.hs.o2o.util.CodeUtil;
import com.hs.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺管理
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	/**
	 * 判断到底有没有登录，然后就访问了登录的页面，管理session的操作
	 * @param request
	 * @return
	 */
	@GetMapping("getshopmanagementinfo")
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId <= 0) {
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			} else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		}else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}
	/**
	 * 获取店铺列表，含分页显示
	 * @param request
	 * @return
	 */
	@GetMapping("/getshoplist")
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
//		这里是模拟一个用户操作
		user.setUserId(1L);
//		String user_name = (String) request.getSession().getAttribute("user_name");
//		user.setName(user_name);
//		//首先默认设置一个user的session
//		request.getSession().setAttribute("user", user);

		System.out.println( request.getSession().getAttribute("user"));
		//获取request里session里的user值
		user = (PersonInfo) request.getSession().getAttribute("user");
		try {
			Shop shopCondition = new Shop();
			shopCondition.setOwner(user);
			//这里默认设置1页，1页显示100个数据
			ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
//			把数据一一存进去，最后返回给前台
			modelMap.put("shopList", se.getShopList());
			modelMap.put("user", user);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", true);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * 通过shopid查询出shop的信息
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/getshopbyid")
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	/**
	 * 获取店铺分类，地区分类
	 *
	 * @return
	 */
	@GetMapping("/getshopinitinfo")
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	/**
	 * 店铺注册，添加
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/registershop")
	@ResponseBody   //转换为json串
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//判断验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		//获取前台传过来的数据，并进行转化
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
//			把数据转换为实体类的json串
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			//如果出错，返回信息
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//处理图片相关的逻辑
		//处理图片使用spring自带的
		CommonsMultipartFile shopImg = null;
		//文件上传解析器，解析request里的文件信息
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断request里面是不是有上传的文件流
		if (commonsMultipartResolver.isMultipart(request)) {
			//转换
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2.注册店铺
		if (shop != null && shopImg != null) {
			//从session中获取用户的ID
			//session TODO
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			/*File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}*/
			/*try {
				inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("essMsg", e.getMessage());
				return modelMap;
			}*/
			ShopExecution se = null;
			try {
				ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
				se = shopService.addShop(shop,imageHolder );
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
//					该用户可以操作的店铺列表
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
//					如果这是第一次创建店铺
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				} else {
					modelMap.put("success", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("essMsg", e.getMessage());
			}

			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("essMsg", "请输入店铺信息");
			return modelMap;
		}

	}

	/**
	 * 修改店铺信息
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/modifyshop")
	@ResponseBody   //转换为json串
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		System.out.println("--------------");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//判断验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//1.接收并转化相应的参数，包括店铺信息以及图片信息
		//获取前台传过来的数据，并进行转化
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
//			把数据转换为实体类的json串
			shop = mapper.readValue(shopStr, Shop.class);
			System.out.println(shop);
		} catch (Exception e) {
			//如果出错，返回信息
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//处理图片相关的逻辑
		//处理图片使用spring自带的
		CommonsMultipartFile shopImg = null;
		//文件上传解析器，解析request里的文件信息
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断request里面是不是有上传的文件流
		if (commonsMultipartResolver.isMultipart(request)) {
			//转换
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		//2.修改店铺信息
		if (shop != null && shop.getShopId() != null) {
			ShopExecution se;
			try {
				if (shopImg == null) {
					se = shopService.modifyShop(shop, null);
				} else {
					ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop, imageHolder);
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("essMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("essMsg", "请输入店铺信息");
			return modelMap;
		}

	}

	/**
	 * inputStream转换File
	 *
	 * @param ins
	 * @param file
	 */
	/*private static void inputStreamToFile(InputStream ins, File file) {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("inputStreamToFile关闭io产生异常" + e.getMessage());
			}
		}

	}*/
}
