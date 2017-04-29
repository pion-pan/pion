package com.pan.pion.cache.redis.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClusterNode {

	@XmlElementWrapper(name = "clusters")
	@XmlElement(name = "cluster")
	private List<HostAndPortSet> clusters;

	public List<HostAndPortSet> getClusters() {
		return clusters;
	}

	public void setClusters(List<HostAndPortSet> clusters) {
		this.clusters = clusters;
	}
}
