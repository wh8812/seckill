package com.seckill.context;

import lombok.Data;

@Data
public class ExposerContext {
	
	//是否開啟秒殺
	private boolean exposed;
	
	//加密手段
	private String md5;
	
	private long seckillId;
	
	//系统当前时间(毫秒)
	private long now;
	
	//开启时间
	private long start;
	
	//结束时间
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
