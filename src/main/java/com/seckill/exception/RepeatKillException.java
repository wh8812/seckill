package com.seckill.exception;

/**
 * 描述：重复秒杀异常(运行期异常)
 * @author Hailin
 * @time 2016年7月9日 下午10:14:55
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
