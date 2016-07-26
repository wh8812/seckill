package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seckill.po.Seckill;

/**
 * 描述：
 * @author Hailin
 * @time 2016年6月6日 下午9:28:00
 */
public interface SeckillDao {
	
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return
	 * @author Hailin
	 * @time 2016年6月6日 下午9:55:50
	 */
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
	Seckill queryById(long seckillId);
	
	/**
	 * List<Seckill> queryAll(int offset,int limit)这样是有问题的
	 * 因为java没有保留形参的记录:queryAll(int offset,int limit) -> queryAll(arg0,arg1)
	 */
	List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

}
