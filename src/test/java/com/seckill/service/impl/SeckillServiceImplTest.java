package com.seckill.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.context.ExposerContext;
import com.seckill.context.SeckillExecutionContext;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.po.Seckill;
import com.seckill.service.SeckillService;

/**
 * 描述：
 * @author Hailin
 * @time 2016年7月11日 上午12:58:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
	
	private static final Logger logger = LoggerFactory.getLogger(SeckillServiceImplTest.class);
	
	@Resource
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		logger.info("list={}",seckills.toString());
	}

	@Test
	public void testGetById() {
		long seckillId = 3l;
		Seckill seckill = seckillService.getById(seckillId);
		logger.info("Seckill={}",seckill.toString());
	}

	@Test
	public void testExportSeckillUrl() {
		long seckillId = 3l;
		ExposerContext context = seckillService.exportSeckillUrl(seckillId);
		logger.info("ExposerContext={}",context.toString());
	}

	@Test
	public void testExecuteSeckill() {
		long seckillId = 2l;
		long userPhone = 13265456666l;
		String md5 = "bddfa1c8e94d0346c2af971f344ec392";
		SeckillExecutionContext context = seckillService.executeSeckill(seckillId, userPhone, md5);
		logger.info("result={}",context.toString());
	}
	
	@Test
	public void testSeckillLogic() {
		long seckillId = 2l;
		ExposerContext context = seckillService.exportSeckillUrl(seckillId);
		if (context.isExposed()) {
			logger.info("ExposerContext={}",context.toString());
			long userPhone = 13365456666l;
			String md5 = context.getMd5();
			
			try {
				SeckillExecutionContext seckillExecutionContext = seckillService.executeSeckill(seckillId, userPhone, md5);
				logger.info("result={}",seckillExecutionContext.toString());
			} catch(SeckillCloseException ce){
				logger.error(ce.getMessage());
			}catch(RepeatKillException re){
				logger.error(re.getMessage(), re);
			}
		}else{
			//秒杀未开启
			logger.warn("ExposerContext={}",context.toString());
		}
	}

}
