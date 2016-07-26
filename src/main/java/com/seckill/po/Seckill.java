package com.seckill.po;

import java.util.Date;

import lombok.Data;

/**
 * 描述：
 * @author Hailin
 * @time 2016年6月6日 下午9:10:05
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
