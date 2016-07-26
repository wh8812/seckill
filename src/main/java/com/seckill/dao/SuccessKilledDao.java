package com.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.seckill.po.SuccessKilled;

/**
 * 描述：
 * @author Hailin
 * @time 2016年6月6日 下午9:49:56
 */
public interface SuccessKilledDao {
	
	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return
	 * @author Hailin
	 * @time 2016年6月6日 下午9:51:37
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	/**
	 * 根据Id查询SuccessKilled并携带秒杀产品对象实体
	 * @param seckillId
	 * @return
	 * @author Hailin
	 * @time 2016年6月6日 下午9:53:13
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
