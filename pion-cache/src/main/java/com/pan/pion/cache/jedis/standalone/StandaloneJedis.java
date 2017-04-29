package com.pan.pion.cache.jedis.standalone;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import com.pan.pion.cache.redis.config.RedisStandloneConfig;
import com.pan.pion.common.Utils.ConfigHelper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class StandaloneJedis {
	private static JedisPool jedisPool;

	/**
	 * 初始化
	 * 
	 * @param configPath
	 * @throws JAXBException
	 */
	public static void initConfig(String configPath) throws JAXBException {
		RedisStandloneConfig config = ConfigHelper.getConfig(RedisStandloneConfig.class, configPath);
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setTestOnBorrow(true);
		// 最大空闲连接数,默认8
		config.setMaxIdle(config.getMaxIdle());
		// 最大连接数, 默认8个
		config.setMaxTotal(config.getMaxTotal());
		jedisPool = new JedisPool(poolConfig, config.getHost(), config.getPort(), Protocol.DEFAULT_TIMEOUT,
				config.getPassword());
	}

	public static void initConfig(InputStream is) throws JAXBException {
		RedisStandloneConfig config = ConfigHelper.getConfig(RedisStandloneConfig.class, is);
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setTestOnBorrow(true);
		// 最大空闲连接数,默认8
		config.setMaxIdle(config.getMaxIdle());
		// 最大连接数, 默认8个
		config.setMaxTotal(config.getMaxTotal());
		jedisPool = new JedisPool(poolConfig, config.getHost(), config.getPort(), Protocol.DEFAULT_TIMEOUT,
				config.getPassword());
	}

	/**
	 * 关闭连接池
	 */
	public static void close() {
		if (jedisPool != null && !jedisPool.isClosed()) {
			jedisPool.close();
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	public static Jedis getInstance() {
		return jedisPool.getResource();
	}
}
