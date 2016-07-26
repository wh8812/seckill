package com.seckill.enums;

/**
 * ������ʹ��ö�ٱ�ʾ���ǵĳ��������ֶ�
 * @author Hailin
 * @time 2016��7��10�� ����9:38:07
 */
public enum SeckillStatEnum {
	
	SUCCESS(1,"��ɱ�ɹ�"),
	
	END(0,"��ɱ����"),
	
	REPEAT_KILL(-1,"�ظ���ɱ"),
	
	INNER_ERROR(-2,"ϵͳ�쳣"),
	
	DATE_REWRITE(-3,"���ݱ��۸�");
	
	//��ɱ���ִ��״̬
	private int state;
	
	//״̬��ʶ
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
