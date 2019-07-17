package com.hs.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Http Servlet请求工具，获取值并转换数据
 */
public class HttpServletRequestUtil {
	/**
	 * 转换整型
	 * @param request
	 * @param key
	 * @return
	 */
	public static int getInt(HttpServletRequest request, String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * 转换long
	 * @param request
	 * @param key
	 * @return
	 */
	public static long getLong(HttpServletRequest request, String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * 转换Double
	 * @param request
	 * @param key
	 * @return
	 */
	public static Double getDouble(HttpServletRequest request, String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (NumberFormatException e) {
			return -1d;
		}
	}

	/**
	 * 转换Boolean
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 转换String
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key) {
		try {
			String result = request.getParameter(key);
			if (result != null) {
				result = result.trim();
			}
			if ("".equals(result)) {
				result = null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
