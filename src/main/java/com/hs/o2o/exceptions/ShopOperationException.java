package com.hs.o2o.exceptions;

/**
 * 店铺的异常
 */
public class ShopOperationException extends RuntimeException{

	//通过idea自动工具生成序列化ID
	private static final long serialVersionUID = 1486990484467363460L;

	public ShopOperationException(String msg) {
		super(msg);
	}
}
