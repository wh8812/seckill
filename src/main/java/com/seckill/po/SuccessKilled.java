package com.seckill.po;

import java.util.Date;

import lombok.Data;

/**
 * ������
 * @author Hailin
 * @time 2016��6��6�� ����9:17:04
 */
@Data
public class SuccessKilled {
	
	  private long seckillId;
	  private long userPhone;
	  private short state;
	  private Date createTime;
	  
	  //��ͨ ���һ
	  private Seckill seckill;

}
