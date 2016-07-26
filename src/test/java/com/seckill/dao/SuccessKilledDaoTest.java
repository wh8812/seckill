package com.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.po.SuccessKilled;

/**
 * 描述：
 * @author Hailin
 * @time 2016年7月7日 上午12:38:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	//注入dao实现类的依赖
	@Resource
	private SuccessKilledDao successKilledDao;

	@Test
	public void testInsertSuccessKilled() {
		long seckillId = 2l;
		long userPhone = 13317219876l;
		int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println(insertCount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long seckillId = 2l;
		long userPhone = 13817219876l;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
	    System.out.println(successKilled);
	}

}
