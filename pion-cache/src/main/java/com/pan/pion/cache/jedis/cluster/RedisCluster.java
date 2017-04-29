package com.pan.pion.cache.jedis.cluster;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisCluster;

public class RedisCluster implements Closeable {

	private JedisCluster cluster;

	private volatile boolean isClosed;

	public RedisCluster(List<RedisHostAndPortSet> jedisClusterNodeList, int timeout, int maxRedirections,
			GenericObjectPoolConfig pool) {

		for (RedisHostAndPortSet hostAndPortSet : jedisClusterNodeList) {
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			try {
				BeanUtils.copyProperties(pool, poolConfig);
			} catch (Exception e) {
				// TODO ignore
			}

			cluster = new JedisCluster(hostAndPortSet.getSet(), timeout, maxRedirections, poolConfig);
		}
	}

	@Override
	public void close() throws IOException {
		if (this.isClosed) {
			return;
		}
		if (cluster != null) {
			cluster.close();
		}
		isClosed = true;
	}

	public JedisCluster getCluster() {
		return cluster;
	}
}
