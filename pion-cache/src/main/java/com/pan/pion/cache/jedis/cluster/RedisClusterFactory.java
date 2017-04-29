package com.pan.pion.cache.jedis.cluster;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import com.pan.pion.cache.redis.config.ClusterNode;
import com.pan.pion.cache.redis.config.HostAndPortSet;
import com.pan.pion.cache.redis.config.RedisClusterConfig;
import com.pan.pion.common.Utils.ConfigHelper;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClusterFactory {

	private static JedisPoolConfig poolConfig = new JedisPoolConfig();

	private static RedisCluster cluster;

	private static RedisClusterConfig clusterConfig;

	/**
	 * 初始化配置
	 * 
	 * @param configPath
	 * @throws JAXBException
	 */
	public static void initConfig(String configPath) throws JAXBException {
		clusterConfig = ConfigHelper.getConfig(RedisClusterConfig.class, configPath);
		poolConfig.setTestOnBorrow(true);
		// 最大空闲连接数,默认8
		poolConfig.setMaxIdle(clusterConfig.getMaxIdle());
		// 最大连接数, 默认8个
		poolConfig.setMaxTotal(clusterConfig.getMaxTotal());
	}

	/**
	 * 文件流形式初始化
	 * 
	 * @param is
	 * @throws JAXBException
	 */
	public static void initConfig(InputStream is) throws JAXBException {
		clusterConfig = ConfigHelper.getConfig(RedisClusterConfig.class, is);
		poolConfig.setTestOnBorrow(true);
		// 最大空闲连接数,默认8
		poolConfig.setMaxIdle(clusterConfig.getMaxIdle());
		// 最大连接数, 默认8个
		poolConfig.setMaxTotal(clusterConfig.getMaxTotal());
	}

	/**
	 * 获取redisCluster实例
	 * 
	 * @return
	 * @throws Exception
	 */
	public static RedisCluster getCluster() throws Exception {
		if (cluster == null) {
			synchronized (RedisClusterFactory.class) {
				if (cluster != null) {
					return cluster;
				}
				if (clusterConfig == null) {
					throw new Exception("clusterConfig does not inited");
				}
				List<RedisHostAndPortSet> jedisClusterNodeList = getClusterNodes();
				cluster = new RedisCluster(jedisClusterNodeList, clusterConfig.getTimeout(),
						clusterConfig.getMaxRedirections(), poolConfig);
			}
		}
		return cluster;
	}

	/**
	 * 获取所有节点配置
	 * 
	 * @return
	 * @throws Exception
	 */
	private static List<RedisHostAndPortSet> getClusterNodes() throws Exception {
		List<RedisHostAndPortSet> result = new ArrayList<>();
		// 获取配置中所有节点数据
		List<ClusterNode> nodes = clusterConfig.getNodes();
		for (ClusterNode node : nodes) {
			RedisHostAndPortSet setting = new RedisHostAndPortSet();
			Set<HostAndPort> hostAndPortSet = new HashSet<>();
			List<HostAndPortSet> clusterSet = node.getClusters();
			for (HostAndPortSet portSet : clusterSet) {
				HostAndPort hostAndPort = new HostAndPort(portSet.getHost(), portSet.getPort());
				hostAndPortSet.add(hostAndPort);
			}
			setting.setSet(hostAndPortSet);
			result.add(setting);
		}
		return result;
	}

}
