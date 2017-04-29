package com.pan.pion.cache.redis.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * redis配置
 * 
 * @author
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HostAndPortSet {

	@XmlElement(name = "host")
	private String host;

	@XmlElement(name = "port")
	private int port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
