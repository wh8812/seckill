package com.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seckill.po.Seckill;

/**
 * ������
 * @author Hailin
 * @time 2016��6��6�� ����9:28:00
 */
public interface SeckillDao {
	
	/**
	 * �����
	 * @param seckillId
	 * @param killTime
	 * @return
	 * @author Hailin
	 * @time 2016��6��6�� ����9:55:50
	 */
	int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);
	
	Seckill queryById(long seckillId);
	
	/**
	 * List<Seckill> queryAll(int offset,int limit)�������������
	 * ��Ϊjavaû�б����βεļ�¼:queryAll(int offset,int limit) -> queryAll(arg0,arg1)
	 */
	List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

}
