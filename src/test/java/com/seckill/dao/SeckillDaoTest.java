package com.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.po.Seckill;

/**
 * 描述：配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 * @author Hailin
 * @time 2016年7月6日 下午9:55:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	//注入dao实现类的依赖
	@Resource
	private SeckillDao seckillDao;
	

	@Test
	public void testQueryById() {
		long id = 1;
	Seckill seckill	= seckillDao.queryById(id);
	System.out.println(seckill.getName());
	System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckillList	= seckillDao.queryAll(0, 10);
		System.out.println(seckillList.toString());
		
//		for (Seckill seckill : seckillList) {
//			System.out.println(seckill);
//		}
	}

	@Test
	public void testReduceNumber() {
		int updateCount = seckillDao.reduceNumber(1, new Date());
		System.out.println(updateCount);
	}


}
