package com.seckill.enums;

/**
 * 描述：使用枚举表示我们的常量数据字段
 * @author Hailin
 * @time 2016年7月10日 下午9:38:07
 */
public enum SeckillStatEnum {
	
	SUCCESS(1,"秒杀成功"),
	
	END(0,"秒杀结束"),
	
	REPEAT_KILL(-1,"重复秒杀"),
	
	INNER_ERROR(-2,"系统异常"),
	
	DATE_REWRITE(-3,"数据被篡改");
	
	//秒杀结果执行状态
	private int state;
	
	//状态标识
	private String stateInfo;

	SeckillStatEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillStatEnum stateOf(int index){
		for (SeckillStatEnum stat : values()) {
			if (stat.getState() == index) {
				return stat;
			}
		}
		
		return null;
	}
	
}
