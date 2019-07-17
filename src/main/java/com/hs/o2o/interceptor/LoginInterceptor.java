package com.hs.o2o.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//      1.获取httpsession
		HttpSession session = request.getSession();
//		2.判断是否登录
		if (session.getAttribute("user") == null) {
			System.out.println("拦截器调用-------");

//			防止浏览器的后退功能
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			System.out.println(request.getContextPath());
			out.write("<script>");
			out.write("alert('您还没有登录过该系统，请登录！！！');");
			out.write("window.top.location.href='"+request.getContextPath()+"'");
			out.write("</script>");

			out.flush();
			out.close();
			return false;
		}
		return true;
	}
}
