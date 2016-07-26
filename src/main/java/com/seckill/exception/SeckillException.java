package com.seckill.exception;

/**
 * ������
 * @author Hailin
 * @time 2016��7��9�� ����10:21:26
 */
public class SeckillException extends RuntimeException {
	
	private static final long serialVersionUID = -6359571484614465801L;

	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}
}
