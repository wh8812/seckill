package com.seckill.exception;

/**
 * ��������ɱ�ر��쳣
 * @author Hailin
 * @time 2016��7��9�� ����10:18:57
 */
public class SeckillCloseException extends SeckillException {

	private static final long serialVersionUID = -4775663368199129583L;

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
