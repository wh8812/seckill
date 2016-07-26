package com.seckill.context;

import com.seckill.enums.SeckillStatEnum;
import com.seckill.po.SuccessKilled;

import lombok.Data;

/**
 * ��������װ��ɱִ�к�Ľ��
 * @author Hailin
 * @time 2016��7��9�� ����10:01:56
 */
@Data
public class SeckillExecutionContext {
	
	private long seckillId;
	
	//��ɱ���ִ��״̬
	private int state;
	
	//״̬��ʶ
	private String stateInfo;
	
	//��ɱ�ɹ��Ķ���
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
