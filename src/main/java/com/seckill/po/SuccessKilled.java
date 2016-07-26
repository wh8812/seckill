package com.seckill.po;

import java.util.Date;

import lombok.Data;

/**
 * 描述：
 * @author Hailin
 * @time 2016年6月6日 下午9:17:04
 */
@Data
public class SuccessKilled {
	
	  private long seckillId;
	  private long userPhone;
	  private short state;
	  private Date createTime;
	  
	  //变通 多对一
	  private Seckill seckill;

}
