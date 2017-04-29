package com.pan.pion.zk;

import com.pan.pion.zk.util.URL;

public abstract interface ZookeeperTransporter {
	public abstract ZookeeperClient connect(URL paramURL);
}
