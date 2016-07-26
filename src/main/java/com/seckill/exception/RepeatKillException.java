package com.seckill.exception;

/**
 * �������ظ���ɱ�쳣(�������쳣)
 * @author Hailin
 * @time 2016��7��9�� ����10:14:55
 */
public class RepeatKillException extends SeckillException{

	private static final long serialVersionUID = -283567180899385215L;

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatKillException(String message) {
		super(message);
	}
	
}
