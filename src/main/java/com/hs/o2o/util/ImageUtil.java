package com.hs.o2o.util;

import com.hs.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片处理
 */
public class ImageUtil {
	//通过线程获取到项目的绝对路径
//	会输出/F:/idea-Javaworkspace/SSMlearn/o2o/target/o2o/WEB-INF/classes/，，前面有个/
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//	//	截取路径，去掉 /
//	private static String basePath = basePath1.substring(1);
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();

	/**
	 * 给一个水平图片加上水印，再设置大小，透明度等，生成缩略图
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		//文件随机名
		String realFileName = getRandomFileName();
		//文件扩展名
		String extension = getFileExtension(thumbnail.getImageName());
		//如果目标路径不存在，则自动创建文件夹
		makeDirPath(targetAddr);
//		获取文件存储的相对路径(带文件名)
		String relativeAddr = targetAddr + realFileName + extension;
//		获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
//			System.out.println(basePath);
//			System.out.println(dest);
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT,
							ImageIO.read(new File(basePath + "/watermark.png")),
							0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}

	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
//		获取不重复的随机名
		String realFileName = getRandomFileName();
//		获取文件的扩展名如png,jpg
		String extension = getFileExtension(thumbnail.getImageName());
//		如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
//		获取文件存储的相对路径(带文件名)
		String relativeAddr = targetAddr + realFileName + extension;
//		 获取文件要保存到的目标路径
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
//		调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.png")), 0.25f)
					.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩图片失败：" + e.toString());
		}
//		 返回图片相对路径地址
		return relativeAddr;
	}
	/**
	 * 创建目标路径所涉及到的目录，
	 *
	 * @param targetAddr
	 */
	public static void makeDirPath(String targetAddr) {
		//目标所属全路径
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取输入文件流的扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名
	 *
	 * @return
	 */
	public static String getRandomFileName() {
		//获取随机的五位数
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * storePath是文件的路径还是目录的路径
	 * 如果storePath是文件路径则删除该文件
	 * 如果storePath是目录路径则删除该目录下的所有文件
	 *
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
//			判断是否是目录
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	/*public static void main(String[] args) throws IOException {

//		System.out.println(basePath);
//		给图片zang.png设置大小200x200，加上水印watermark.png在右下角，透明度为0.25，压缩度0.8，输出路径为xx
		Thumbnails.of(new File(basePath + "/zang.png")).size(200, 200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("F:/idea-Javaworkspace/SSMlearn/o2o/src/main/resources/watermark.png")), 0.25f)
				.outputQuality(0.8f).toFile("F:/idea-Javaworkspace/SSMlearn/o2o/src/main/resources/zangnew.png");
	}*/
}
