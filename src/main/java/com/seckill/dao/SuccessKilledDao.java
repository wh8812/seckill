package com.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.seckill.po.SuccessKilled;

/**
 * ������
 * @author Hailin
 * @time 2016��6��6�� ����9:49:56
 */
public interface SuccessKilledDao {
	
	/**
	 * ���빺����ϸ���ɹ����ظ�
	 * @param seckillId
	 * @param userPhone
	 * @return
	 * @author Hailin
	 * @time 2016��6��6�� ����9:51:37
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	/**
	 * ����Id��ѯSuccessKilled��Я����ɱ��Ʒ����ʵ��
	 * @param seckillId
	 * @return
	 * @author Hailin
	 * @time 2016��6��6�� ����9:53:13
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
