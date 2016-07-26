package com.seckill.context;

import lombok.Data;

//·â×°json½á¹û
@Data
public class SeckillResultContext<T> {
	
	private boolean success;
	
	private T data;
	
	private String error;

	public SeckillResultContext(boolean success, String error) {
		this.success = success;
		this.error = error;
	}

	public SeckillResultContext(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
	
}
