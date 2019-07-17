package com.hs.o2o.web.shopadmin;

import com.hs.o2o.entity.PersonInfo;
import com.hs.o2o.service.LoginService;
import com.hs.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
/**
 * author heshang.ink
 */
@Controller
@RequestMapping("/person")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
//	    存session用
		HttpSession session = request.getSession();
		String account = HttpServletRequestUtil.getString(request, "account");
		System.out.println("账号"+account);
		String password = HttpServletRequestUtil.getString(request, "password");
		user = loginService.getLogin(account, password);
		if (user != null ) {
			session.setAttribute("user", user);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
		}
		return modelMap;
	}

}
