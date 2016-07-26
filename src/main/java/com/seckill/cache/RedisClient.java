package com.seckill.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.po.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * ������Redis�����ݷ��ʶ���
 * @author Hailin
 * @time 2016��7��23�� ����10:33:19
 */
public class RedisClient {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);
	
	private final JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public RedisClient(String ip,int port){
		jedisPool = new JedisPool(ip, port);
	}
	
	public Seckill getSeckill(long seckillId){
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			 String key = "seckill:"+seckillId;
			 //û��ʵ���ڲ����л����� get ->byte[] -> �����л� ->Object(Seckill)
			 //�����Զ������л�
			 //protostuff : pojo
			 byte[] bytes = jedis.get(key.getBytes());
			 //���bytes��Ϊ�� ���ʾ�ӻ����л�ȡ����
			 if (bytes != null) {
				 //�ն���
				 Seckill seckill = schema.newMessage();
				ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
				//Seckill�������л�
				return seckill;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill){
		Jedis jedis = null;
		try {
			 jedis = jedisPool.getResource();
			 String key = "seckill:"+seckill.getSeckillId();
			 byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
					 LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			 //��ʱ����
			 int timeout = 60*60;//1Сʱ
			 String result = jedis.setex(key.getBytes(), timeout, bytes);
			 return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

}
