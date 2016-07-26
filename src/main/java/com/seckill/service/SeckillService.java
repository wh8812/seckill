package com.seckill.service;

import java.util.List;

import com.seckill.context.ExposerContext;
import com.seckill.context.SeckillExecutionContext;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.po.Seckill;

/**
 * 描述：业务接口:站在“使用者”角度设计接口
 * 三个方面：方法定义的粒度，参数，返回类型
 * @author Hailin
 * @time 2016年7月8日 上午12:24:14
 */
public interface SeckillService {
	
  /**
   * 查询全部记录
   * @return
   * @author Hailin
   * @time 2016年7月8日 上午12:41:41
   */
  List<Seckill> getSeckillList();
  
  Seckill getById(long seckillId);
  
  /**
   * 秒杀接口开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
   * @param seckillId
   * @author Hailin
   * @time 2016年7月8日 上午12:43:06
   */
  ExposerContext exportSeckillUrl(long seckillId);
  
  /**
   * 执行秒杀操作
   * @param seckillId
   * @param userPhone
   * @param md5
   * @author Hailin
   * @time 2016年7月9日 下午10:00:30
   */
  SeckillExecutionContext executeSeckill(long seckillId,long userPhone,String md5) 
		   throws SeckillException,RepeatKillException,SeckillCloseException;
	

}
