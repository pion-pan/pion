package com.pan.pion.zk.curator;

import java.io.IOException;

import com.pan.pion.zk.ZookeeperClient;
import com.pan.pion.zk.ZookeeperTransporter;
import com.pan.pion.zk.util.URL;

public class CuratorZookeeperTransporter implements ZookeeperTransporter {

	@Override
	public ZookeeperClient connect(URL url) {
		try {
			return new CuratorZookeeperClient(url);
		} catch (IOException e) {
			throw new RuntimeException(e.toString());
		}
	}

}
