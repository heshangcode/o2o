package com.hs.o2o.web.superadmin;

import com.hs.o2o.entity.Area;
import com.hs.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;

	@GetMapping(value = "/listarea")
	@ResponseBody   //让map自动转换为json返回给前台
	private Map<String, Object> listArea() {
		logger.info("===start===");
		long startTime = System.currentTimeMillis();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			areaList = areaService.getAreaList();
			//key这样命名是为了easyUI框架用
			modelMap.put("rows", areaList);
			modelMap.put("total", areaList.size());
			System.out.println(modelMap);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		logger.error("test error!");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[{}ms]",endTime - startTime );
		logger.info("===end===");
		return modelMap;
	}

}
