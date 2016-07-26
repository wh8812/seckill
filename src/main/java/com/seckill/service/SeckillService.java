package com.seckill.service;

import java.util.List;

import com.seckill.context.ExposerContext;
import com.seckill.context.SeckillExecutionContext;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.po.Seckill;

/**
 * ������ҵ��ӿ�:վ�ڡ�ʹ���ߡ��Ƕ���ƽӿ�
 * �������棺������������ȣ���������������
 * @author Hailin
 * @time 2016��7��8�� ����12:24:14
 */
public interface SeckillService {
	
  /**
   * ��ѯȫ����¼
   * @return
   * @author Hailin
   * @time 2016��7��8�� ����12:41:41
   */
  List<Seckill> getSeckillList();
  
  Seckill getById(long seckillId);
  
  /**
   * ��ɱ�ӿڿ���ʱ�����ɱ�ӿڵ�ַ���������ϵͳʱ�����ɱʱ��
   * @param seckillId
   * @author Hailin
   * @time 2016��7��8�� ����12:43:06
   */
  ExposerContext exportSeckillUrl(long seckillId);
  
  /**
   * ִ����ɱ����
   * @param seckillId
   * @param userPhone
   * @param md5
   * @author Hailin
   * @time 2016��7��9�� ����10:00:30
   */
  SeckillExecutionContext executeSeckill(long seckillId,long userPhone,String md5) 
		   throws SeckillException,RepeatKillException,SeckillCloseException;
	

}
