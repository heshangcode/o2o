package com.hs.o2o.util;

/**
 * 获取图片的路径
 */
public class PathUtil {
	//获取系统的文件分隔符样式
	private static String separator = System.getProperty("file.separator");

	/**
	 * 获取图片路径
	 *
	 * @return
	 */
	public static String getImgBasePath() {
		//获取这个系统是什么系统
		String os = System.getProperty("os.name");
		String basePath = "";
		//判断系统
		if (os.toLowerCase().startsWith("win")) {
			//这里不放在项目的resources下是因为，每次项目启动，之前的图片都没有了，如果一开始放在resources下增加项目启动效率
			//也可以放在一个服务器上
			basePath = "F:/idea-Javaworkspace/SSMlearn/image/";
		} else {
			basePath = "/home/heshang/image/";
		}
		//替换成系统的文件分隔符
		basePath = basePath.replace("/", separator);
		return basePath;
	}

	/**
	 * 获取店铺图片存储路径，保存在各自店铺路径下
	 *
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", separator);
	}

}
