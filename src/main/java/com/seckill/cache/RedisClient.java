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
 * 描述：Redis的数据访问对象
 * @author Hailin
 * @time 2016年7月23日 下午10:33:19
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
			 //没有实现内部序列化操作 get ->byte[] -> 反序列化 ->Object(Seckill)
			 //采用自定义序列化
			 //protostuff : pojo
			 byte[] bytes = jedis.get(key.getBytes());
			 //如果bytes不为空 则表示从缓存中获取到了
			 if (bytes != null) {
				 //空对象
				 Seckill seckill = schema.newMessage();
				ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
				//Seckill被反序列化
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
			 //超时缓存
			 int timeout = 60*60;//1小时
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
