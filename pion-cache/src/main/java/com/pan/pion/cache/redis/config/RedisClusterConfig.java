package com.pan.pion.cache.redis.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 集群配置
 * 
 * @author
 *
 */
@XmlRootElement(name = "redis-cluster")
@XmlAccessorType(XmlAccessType.FIELD)
public class RedisClusterConfig {

	/**
	 * 超时时间
	 */
	@XmlElement(name = "timeout")
	private int timeout;

	/**
	 * 最大连接数
	 */
	@XmlElement(name = "max_redirections")
	private int maxRedirections;

	@XmlElement(name = "maxIdle")
	private int maxIdle;

	@XmlElement(name = "maxTotal")
	private int maxTotal;

	@XmlElementWrapper(name = "nodes")
	@XmlElement(name = "node")
	List<ClusterNode> nodes;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaxRedirections() {
		return maxRedirections;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
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

	public List<ClusterNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<ClusterNode> nodes) {
		this.nodes = nodes;
	}

}
