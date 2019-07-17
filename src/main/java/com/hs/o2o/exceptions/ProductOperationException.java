package com.hs.o2o.exceptions;

public class ProductOperationException extends RuntimeException {

	private static final long serialVersionUID = 1750488957183218845L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
