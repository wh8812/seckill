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
 * 描述：
 * @author Hailin
 * @time 2016年7月10日 下午6:33:09
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
		//优化点：缓存优化
        //Seckill seckill = seckillDao.queryById(seckillId);
		Seckill seckill = redisClient.getSeckill(seckillId);
		if (seckill == null) {
			//如果缓存没有 则去访问数据库
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
			//记录秒杀购买行为
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if (insertCount <= 0) {
				//重复秒杀
				throw new RepeatKillException("seckill repeated");
			}else {
				//执行秒杀逻辑：减库存 + 热点商品竞争
				int updateCount = seckillDao.reduceNumber(seckillId, now);
				if (updateCount <= 0) {
					//没有更新到记录 秒杀结束
					throw new SeckillCloseException("seckill is Closed");
				}else {
					//秒杀成功
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
			//所有编译异常转换为运行期异常
			throw new SeckillException("seckill inner error");
		}
	}
	
	private String getMd5(long seckillId){
		String base = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
