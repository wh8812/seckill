package com.seckill.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.context.ExposerContext;
import com.seckill.context.SeckillExecutionContext;
import com.seckill.context.SeckillResultContext;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.po.Seckill;
import com.seckill.service.SeckillService;

/**
 * 描述：
 * @author Hailin
 * @time 2016年7月16日 上午12:27:23
 */
@Controller
public class SeckillController {
	//上面url: url:/模块/资源/{id}/细分 /seckill/list
	
	private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);
	
	@Resource
	private SeckillService seckillService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String list(Model model){
		//获取页面
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list",list);
		return "list";
	}
	
	@RequestMapping(value="/{seckillId}/detail",method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId,Model model){
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill",seckill);
		return "detail";
	}
	
	//ajax 返回json
	@RequestMapping(value="/{seckillId}/exposer",method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResultContext<ExposerContext> exposer(@PathVariable("seckillId") Long seckillId){
		SeckillResultContext<ExposerContext> resultContext;
		
		try {
			ExposerContext  exposerContext = seckillService.exportSeckillUrl(seckillId);
			resultContext = new SeckillResultContext<ExposerContext>(true, exposerContext);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			resultContext = new SeckillResultContext<ExposerContext>(false, e.getMessage());
		}
		
		return resultContext;
	}
	
	@RequestMapping(value="/{seckillId}/{md5}/execution",method = RequestMethod.POST,
			produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResultContext<SeckillExecutionContext> execute(
			@PathVariable("seckillId") Long seckillId,
			@CookieValue(value="killPhone",required =false) Long killPhone,
			@PathVariable("md5") String md5){
		SeckillResultContext<SeckillExecutionContext> resultContext;
		
		if (killPhone == null) {
			return new SeckillResultContext<SeckillExecutionContext>(false, " 卧槽，你还没有注册好吧！");
		}
		
		try {
			SeckillExecutionContext executionContext = seckillService.executeSeckill(seckillId, killPhone, md5);
			
			resultContext = new SeckillResultContext<SeckillExecutionContext>(true, executionContext);
		} catch (RepeatKillException e) {
			SeckillExecutionContext context = new SeckillExecutionContext(seckillId,SeckillStatEnum.REPEAT_KILL);
			return new SeckillResultContext<SeckillExecutionContext>(true, context);
		} catch (SeckillCloseException e) {
			SeckillExecutionContext context = new SeckillExecutionContext(seckillId,SeckillStatEnum.END);
			return new SeckillResultContext<SeckillExecutionContext>(true, context);
		}catch (Exception e) {
			SeckillExecutionContext context = new SeckillExecutionContext(seckillId,SeckillStatEnum.INNER_ERROR);
			return new SeckillResultContext<SeckillExecutionContext>(true, context);
		}
		
		return resultContext;
	}

	@RequestMapping(value="/time/now",method = RequestMethod.GET)
	@ResponseBody
	public SeckillResultContext<Long> time(){
		Date now = new Date();
		return new SeckillResultContext<Long>(true, now.getTime());
	}
}
