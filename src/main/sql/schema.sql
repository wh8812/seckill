DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` varchar(120) NOT NULL DEFAULT '' COMMENT '商品名称',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `start_time` timestamp NOT NULL COMMENT '秒杀开启时间',
  `end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据
insert into seckill(name,number,start_time,end_time) values('1000元秒杀iPhone6s',100,'2016-06-06 00:00:00','2016-06-07 00:00:00');
insert into seckill(name,number,start_time,end_time) values('500元秒杀iPad air',200,'2016-06-06 00:00:00','2016-06-07 00:00:00');
insert into seckill(name,number,start_time,end_time) values('300元秒杀小米5',300,'2016-06-06 00:00:00','2016-06-07 00:00:00');
insert into seckill(name,number,start_time,end_time) values('200元秒杀红米note',400,'2016-06-06 00:00:00','2016-06-07 00:00:00');


--秒杀成功明细表
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
  `user_phone` bigint(11) NOT NULL COMMENT '秒杀商品ID',
  `state` tinyint(11) NOT NULL DEFAULT -1 COMMENT '状态表示：-1无效,0成功,1已付款,2已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
