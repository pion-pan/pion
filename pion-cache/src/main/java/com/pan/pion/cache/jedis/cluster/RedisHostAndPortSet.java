package com.pan.pion.cache.jedis.cluster;

import java.util.Set;

import redis.clients.jedis.HostAndPort;

public class RedisHostAndPortSet {
	private Set<HostAndPort> set;

	public RedisHostAndPortSet() {
	}

	public RedisHostAndPortSet(Set<HostAndPort> set) {
		this.set = set;
	}

	public Set<HostAndPort> getSet() {
		return this.set;
	}

	public void setSet(Set<HostAndPort> set) {
		this.set = set;
	}
}
