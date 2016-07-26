DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���ID',
  `name` varchar(120) NOT NULL DEFAULT '' COMMENT '��Ʒ����',
  `number` int(11) NOT NULL DEFAULT '0' COMMENT '�������',
  `start_time` timestamp NOT NULL COMMENT '��ɱ����ʱ��',
  `end_time` timestamp NOT NULL COMMENT '��ɱ����ʱ��',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (`seckill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ɱ����';

--��ʼ������
insert into seckill(name,number,start_time,end_time) values('1000Ԫ��ɱiPhone6s',100,'2016-06-06 00:00:00','2016-06-07 00:00:00');
insert into seckill(name,number,start_time,end_time) values('500Ԫ��ɱiPad air',200,'2016-06-06 00:00:00','2016-06-07 00:00:00');
insert into seckill(name,number,start_time,end_time) values('300Ԫ��ɱС��5',300,'2016-06-06 00:00:00','2016-06-07 00:00:00');
insert into seckill(name,number,start_time,end_time) values('200Ԫ��ɱ����note',400,'2016-06-06 00:00:00','2016-06-07 00:00:00');


--��ɱ�ɹ���ϸ��
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '��ɱ��ƷID',
  `user_phone` bigint(11) NOT NULL COMMENT '��ɱ��ƷID',
  `state` tinyint(11) NOT NULL DEFAULT -1 COMMENT '״̬��ʾ��-1��Ч,0�ɹ�,1�Ѹ���,2�ѷ���',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  PRIMARY KEY (seckill_id,user_phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ɱ�ɹ���ϸ��';
