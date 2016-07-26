package com.seckill.po;

import java.util.Date;

import lombok.Data;

/**
 * ������
 * @author Hailin
 * @time 2016��6��6�� ����9:10:05
 */
@Data
public class Seckill {
	
	  private long seckillId;	
	  private String name;
	  private int number;
	  private Date startTime;
	  private Date endTime;
	  private Date createTime;

}
