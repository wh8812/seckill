package com.seckill.cache;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dao.SeckillDao;
import com.seckill.po.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisClientTest {
	
	@Resource
	private RedisClient redisClient	;
	
	@Resource
	private SeckillDao seckillDao;
	
	private long seckillId = 3;
	
	@Test
	public void testPutAndGetSeckill() {
		Seckill seckill = redisClient.getSeckill(seckillId);
		
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill != null) {
				String result = redisClient.putSeckill(seckill);
				System.out.println(result);
			}
			
		   seckill = redisClient.getSeckill(seckillId);
		   System.out.println(seckill);
		}
		
	}

}
