package com.seckill.context;

import lombok.Data;

@Data
public class ExposerContext {
	
	//�Ƿ��_���뚢
	private boolean exposed;
	
	//�����ֶ�
	private String md5;
	
	private long seckillId;
	
	//ϵͳ��ǰʱ��(����)
	private long now;
	
	//����ʱ��
	private long start;
	
	//����ʱ��
	private long end;

	public ExposerContext(boolean exposed, String md5, long seckillId) {
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	public ExposerContext(boolean exposed, long now, long start, long end) {
		this.exposed = exposed;
		this.now = now;
		this.start = start;
		this.end = end;
	}
	
	public ExposerContext(boolean exposed, long seckillId) {
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public ExposerContext(boolean exposed, long seckillId, long now, long start, long end) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

}
