package com.pan.pion.cache.redis.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

/**
 * 单机配置
 * 
 * @author
 *
 */
@XmlRootElement(name = "redis-standone")
@XmlAccessorType(XmlAccessType.FIELD)
public class RedisStandloneConfig {

	@XmlElement(name = "host")
	private String host;

	@XmlElement(name = "port")
	private int port;

	/**
	 * 授权密码
	 */
	@XmlElement(name = "password")
	private String password;

	/**
	 * 最大空闲连接数
	 */
	@XmlElement(name = "maxIdle")
	private int maxIdle;

	/**
	 * 最大连接数
	 */
	@XmlElement(name = "maxTotal")
	private int maxTotal;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		if (StringUtils.isEmpty(password)) {
			return null;
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
