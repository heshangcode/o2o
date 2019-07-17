package com.hs.o2o.util;

/**
 * 把前面的页面转换为后台的查询条数
 */
public class PageCalculator {
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
	}
}
