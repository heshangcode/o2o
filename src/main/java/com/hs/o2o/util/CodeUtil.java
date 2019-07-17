package com.hs.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码的比对
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		//正确应该输入的验证码
		String verifyCodeExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//实际的验证码
		String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
		if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
