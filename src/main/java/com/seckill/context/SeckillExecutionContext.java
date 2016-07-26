package com.seckill.context;

import com.seckill.enums.SeckillStatEnum;
import com.seckill.po.SuccessKilled;

import lombok.Data;

/**
 * 描述：封装秒杀执行后的结果
 * @author Hailin
 * @time 2016年7月9日 下午10:01:56
 */
@Data
public class SeckillExecutionContext {
	
	private long seckillId;
	
	//秒杀结果执行状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//秒杀成功的对象
	private SuccessKilled successKilled;

	public SeckillExecutionContext(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.successKilled = successKilled;
	}

	public SeckillExecutionContext(long seckillId,  SeckillStatEnum statEnum) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}

}
