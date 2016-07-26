package com.seckill.exception;

/**
 * 描述：秒杀关闭异常
 * @author Hailin
 * @time 2016年7月9日 下午10:18:57
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
