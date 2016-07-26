package com.seckill.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.seckill.cache.RedisClient;
import com.seckill.context.ExposerContext;
import com.seckill.context.SeckillExecutionContext;
import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.po.Seckill;
import com.seckill.po.SuccessKilled;
import com.seckill.service.SeckillService;

/**
 * ������
 * @author Hailin
 * @time 2016��7��10�� ����6:33:09
 */
@Service
public class SeckillServiceImpl implements SeckillService {
	
	private static final Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);
	
	@Resource
	private SeckillDao seckillDao;
	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Resource
	private RedisClient redisClient	;
	
	private final String salt = "dfsdetsfa#@$%#%*^EdfaDDEAdg1322@@#$%";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public ExposerContext exportSeckillUrl(long seckillId) {
		//�Ż��㣺�����Ż�
        //Seckill seckill = seckillDao.queryById(seckillId);
		Seckill seckill = redisClient.getSeckill(seckillId);
		if (seckill == null) {
			//�������û�� ��ȥ�������ݿ�
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new ExposerContext(false, seckillId);
			}else {
				redisClient.putSeckill(seckill);
			}
		}
		
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date now = new Date();
		if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
			return new ExposerContext(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
		}
		
		String md5 = getMd5(seckillId);
		
		return new ExposerContext(true,md5, seckillId);
	}

	@Override
	@Transactional
	public SeckillExecutionContext executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if (null == md5 || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		
		Date now = new Date();
	    
		try {
			//��¼��ɱ������Ϊ
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if (insertCount <= 0) {
				//�ظ���ɱ
				throw new RepeatKillException("seckill repeated");
			}else {
				//ִ����ɱ�߼�������� + �ȵ���Ʒ����
				int updateCount = seckillDao.reduceNumber(seckillId, now);
				if (updateCount <= 0) {
					//û�и��µ���¼ ��ɱ����
					throw new SeckillCloseException("seckill is Closed");
				}else {
					//��ɱ�ɹ�
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				    return new SeckillExecutionContext(seckillId,SeckillStatEnum.SUCCESS,successKilled);

				}
			}
			
		}catch(SeckillCloseException ce){
			throw ce;
		}catch(RepeatKillException re){
			logger.error(re.getMessage(), re);
			throw re;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			//���б����쳣ת��Ϊ�������쳣
			throw new SeckillException("seckill inner error");
		}
	}
	
	private String getMd5(long seckillId){
		String base = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
