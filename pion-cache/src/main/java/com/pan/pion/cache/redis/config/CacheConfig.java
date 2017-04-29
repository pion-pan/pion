package com.pan.pion.cache.redis.config;

import java.io.InputStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.pan.pion.cache.jedis.cluster.RedisClusterFactory;
import com.pan.pion.cache.jedis.standalone.StandaloneJedis;
import com.pan.pion.common.Utils.ConfigHelper;
import com.pan.pion.common.exception.PionException;

/**
 * 缓存配置
 * 
 * @author
 *
 */
@XmlRootElement(name = "cache")
@XmlAccessorType(XmlAccessType.FIELD)
public class CacheConfig {
	private static final String CONF_PATH = "cache-config.xml";

	/**
	 * 是否使用集群
	 */
	@XmlElement(name = "isCluster")
	private boolean isCluster = false;

	/**
	 * 环境标志
	 */
	@XmlElement(name = "profileEnv")
	private String profileEnv = "dev";

	/**
	 * 默认缓存失效时间
	 */
	@XmlElement(name = "defaultSeconds")
	private int defaultSeconds = 3600;

	/**
	 * 缓存配置文件地址 如果使用配置中心模式则该值为配置名
	 */
	@XmlElement(name = "configPath")
	private String configPath;

	public boolean isCluster() {
		return isCluster;
	}

	public void setCluster(boolean isCluster) {
		this.isCluster = isCluster;
	}

	public String getProfileEnv() {
		return profileEnv;
	}

	public void setProfileEnv(String profileEnv) {
		this.profileEnv = profileEnv;
	}

	public int getDefaultSeconds() {
		return defaultSeconds;
	}

	public void setDefaultSeconds(int defaultSeconds) {
		this.defaultSeconds = defaultSeconds;
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	public static CacheConfig init() {
		try {
			CacheConfig cacheConfig = ConfigHelper.getConfig(CacheConfig.class, CONF_PATH);
			// 使用集群
			if (cacheConfig.isCluster()) {
				RedisClusterFactory.initConfig(cacheConfig.getConfigPath());
			} else {
				// 非集群
				StandaloneJedis.initConfig(cacheConfig.getConfigPath());
			}
			return cacheConfig;
		} catch (Exception e) {
			throw new PionException("初始化redis配置失败");
		}
	}

	/**
	 * 通过文件流初始化配置
	 * 
	 * @param is
	 * @return
	 */
	public static CacheConfig init(InputStream is) {
		try {
			CacheConfig cacheConfig = ConfigHelper.getConfig(CacheConfig.class, is);
			// 使用集群
			if (cacheConfig.isCluster()) {
				RedisClusterFactory.initConfig(cacheConfig.getConfigPath());
			} else {
				// 非集群
				StandaloneJedis.initConfig(cacheConfig.getConfigPath());
			}
			return cacheConfig;
		} catch (Exception e) {
			throw new PionException("初始化redis配置失败");
		}
	}

	public static CacheConfig init(InputStream cacheStream, InputStream instanceStream) {
		try {
			CacheConfig cacheConfig = ConfigHelper.getConfig(CacheConfig.class, cacheStream);
			// 使用集群
			if (cacheConfig.isCluster()) {
				RedisClusterFactory.initConfig(instanceStream);
			} else {
				// 非集群
				StandaloneJedis.initConfig(instanceStream);
			}
			return cacheConfig;
		} catch (Exception e) {
			throw new PionException("初始化redis配置失败");
		}
	}

}
