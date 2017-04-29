package com.pan.pion.zk.curator;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundPathable;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pan.pion.zk.ChildListener;
import com.pan.pion.zk.support.AbstractZookeeperClient;
import com.pan.pion.zk.support.ChildrenNotifyListener;
import com.pan.pion.zk.support.DataNotifyListener;
import com.pan.pion.zk.util.URL;

public class CuratorZookeeperClient extends AbstractZookeeperClient<CuratorWatcher> {
	protected static final Logger logger = LoggerFactory.getLogger(CuratorZookeeperClient.class);

	private final CuratorFramework client;
	private ConcurrentHashMap<String, CuratorWatcher> childrenWatchers = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Set<ChildrenNotifyListener>> subscribed = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, CuratorWatcher> dataWatchers = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Set<DataNotifyListener>> subscribedData = new ConcurrentHashMap<>();

	public CuratorZookeeperClient(URL url) throws IOException {
		super(url);
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
				.connectString(url.getBackupAddress()).retryPolicy(new RetryNTimes(2147483647, 1000))
				.connectionTimeoutMs(5000).sessionTimeoutMs(10000);
		// 获取授权字符串 username:password
		String authority = url.getAuthority();
		if ((authority != null) && (authority.length() > 0)) {
			builder = builder.authorization("digest", authority.getBytes());
		}
		this.client = builder.build();
		// 添加连接状态监听
		this.client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			public void stateChanged(CuratorFramework client, ConnectionState state) {
				// 连接断开
				if (state == ConnectionState.LOST)
					CuratorZookeeperClient.this.stateChanged(0);
				// 连接成功
				else if (state == ConnectionState.CONNECTED)
					CuratorZookeeperClient.this.stateChanged(1);
				// 重连成功
				else if (state == ConnectionState.RECONNECTED)
					CuratorZookeeperClient.this.stateChanged(2);
			}
		});
		this.client.start();
	}

	@Override
	public void delete(String path) {
		try {
			this.client.delete().forPath(path);
		} catch (KeeperException.NoNodeException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public List<String> getChildren(String path) {
		try {
			return (List<String>) this.client.getChildren().forPath(path);
		} catch (KeeperException.NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public byte[] getDataByPath(String path) {
		try {
			return (byte[]) this.client.getData().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setPathData(String path, byte[] data) {
		try {
			this.client.create().creatingParentsIfNeeded().forPath(path, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isConnected() {
		return this.client.getZookeeperClient().isConnected();
	}

	@Override
	protected void doClose() {
		this.client.close();
	}

	@Override
	protected void createPersistent(String path) {
		try {
			this.client.create().creatingParentsIfNeeded().forPath(path);
		} catch (KeeperException.NodeExistsException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	protected void createEphemeral(String path) {
		try {
			this.client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
		} catch (KeeperException.NodeExistsException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	protected List<String> addTargetChildListener(String path, CuratorWatcher listener) {
		try {
			return this.client.getChildren().usingWatcher(listener).forPath(path);
		} catch (KeeperException.NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	protected CuratorWatcher createTargetChildListener(String path, ChildListener targetListener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void removeTargetChildListener(String path, CuratorWatcher listener) {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取path下的所有数据 TODO getNodeData相同
	 * 
	 * @param path
	 * @return
	 */
	public String getChildrenData(String path) {
		String result = null;
		try {
			result = new String((byte[]) this.client.getData().forPath(path));
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
		return result;
	}

	/**
	 * 在path下新增或更新数据为data
	 * 
	 * @param path
	 * @param data
	 *            不能为空
	 */
	public void createOrUpdate(String path, String data) {
		if (data == null)
			throw new RuntimeException("data is null ,pleast write data");
		try {
			if (this.client.checkExists().forPath(path) != null)
				this.client.setData().forPath(path, data.getBytes());
			else
				this.client.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
		} catch (KeeperException.NodeExistsException e) {
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}

	/**
	 * 获取指定路径下的数据 TODO getChildrenData相同
	 * 
	 * @param path
	 * @return
	 */
	public String getNodeData(String path) {
		String result = null;
		try {
			result = new String((byte[]) this.client.getData().forPath(path));
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
		return result;
	}

	public void doSubChildFocus(final String path, ChildrenNotifyListener listener) {
		subscribeChildren(path, listener);
		CuratorWatcher watcher = (CuratorWatcher) this.childrenWatchers.get(path);
		if (watcher != null) {
			addTargetChildListener(path, watcher);
		}
		watcher = new CuratorWatcher() {
			public void process(WatchedEvent event) throws Exception {
				for (ChildrenNotifyListener l : (Set<ChildrenNotifyListener>) subscribed.get(path)) {
					try {
						l.notify(event.getPath(), CuratorZookeeperClient.this.getChildren(event.getPath()));
					} catch (Exception e) {
						logger.error("doSubChildFocus" + event.getPath() + "for reason" + e.getMessage());
					}
				}
				addTargetChildListener(path, this);
			}
		};
		addTargetChildListener(path, watcher);
		this.childrenWatchers.put(path, watcher);
	}

	public void doSubscribeChildren(final String path, ChildrenNotifyListener listener) {
		subscribeChildren(path, listener);
		CuratorWatcher watcher = (CuratorWatcher) this.childrenWatchers.get(path);
		if (watcher != null) {
			addTargetChildListener(path, watcher);
		}
		watcher = new CuratorWatcher() {
			public void process(WatchedEvent event) throws Exception {
				for (ChildrenNotifyListener l : (Set<ChildrenNotifyListener>) subscribed.get(path)) {
					l.notify(event.getPath(), CuratorZookeeperClient.this.getChildren(event.getPath()));
				}
				addTargetChildListener(path, this);
			}
		};
		addTargetChildListener(path, watcher);
		this.childrenWatchers.put(path, watcher);
	}

	public void doSubscribeData(final String path, DataNotifyListener listener) {
		// 为path添加监听器
		subscribeData(path, listener);
		CuratorWatcher watcher = this.dataWatchers.get(path);
		if (watcher != null) {
			// 如果监听不为空的话重新添加监听 TODO如果监听存在是否就可以不用继续新创建监听了
			addTargetDataListener(path, watcher);
		}
		watcher = new CuratorWatcher() {
			public void process(WatchedEvent event) throws Exception {
				for (DataNotifyListener l : (Set<DataNotifyListener>) subscribedData.get(path)) {
					String nodeData = null;
					// 如果不是节点删除事件则获取节点数据
					if (event.getType() != Watcher.Event.EventType.NodeDeleted) {
						nodeData = CuratorZookeeperClient.this.getNodeData(event.getPath());
					}
					// 通知监听器
					l.notify(event.getPath(), nodeData);
				}
				addTargetDataListener(path, this);
			}
		};
		// 添加监听事件
		addTargetDataListener(path, watcher);
		this.dataWatchers.put(path, watcher);
	}

	public void doUnSubscribeData(String path, DataNotifyListener listener) {
		if (path == null) {
			throw new IllegalArgumentException("unsubscribe path == null");
		}
		if (listener == null) {
			throw new IllegalArgumentException("unsubscribe listener == null");
		}
		Set<DataNotifyListener> listeners = this.subscribedData.get(path);
		if (listeners != null) {
			listeners.remove(listener);
		}
		if (listeners.size() == 0)
			this.dataWatchers.remove(path);
	}

	public byte[] addTargetDataListener(String path, CuratorWatcher listener) {
		try {
			return ((BackgroundPathable<byte[]>) this.client.getData().usingWatcher(listener)).forPath(path);
		} catch (KeeperException.NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 为path添加监听
	 * 
	 * @param path
	 * @param listener
	 */
	private void subscribeData(String path, DataNotifyListener listener) {
		if (path == null) {
			throw new IllegalArgumentException("subscribe path == null");
		}
		if (listener == null) {
			throw new IllegalArgumentException("subscribe listener == null");
		}
		// 获取所有path的监听器
		Set<DataNotifyListener> listeners = this.subscribedData.get(path);
		if (listeners == null) {
			this.subscribedData.putIfAbsent(path, new HashSet<DataNotifyListener>());
			listeners = this.subscribedData.get(path);
		}
		listeners.add(listener);
	}

	/**
	 * 订阅path节点
	 * 
	 * @param path
	 * @param listener
	 */
	private void subscribeChildren(String path, ChildrenNotifyListener listener) {
		if (path == null) {
			throw new IllegalArgumentException("subscribe path == null");
		}
		if (listener == null) {
			throw new IllegalArgumentException("subscribe listener == null");
		}
		Set<ChildrenNotifyListener> listeners = this.subscribed.get(path);
		if (listeners == null) {
			this.subscribed.putIfAbsent(path, new HashSet<ChildrenNotifyListener>());
			listeners = this.subscribed.get(path);
		}
		listeners.add(listener);
	}

	public void doUnSubscribeChildren(String path, ChildrenNotifyListener listener) {
		if (path == null) {
			throw new IllegalArgumentException("unsubscribe path == null");
		}
		if (listener == null) {
			throw new IllegalArgumentException("unsubscribe listener == null");
		}
		Set<ChildrenNotifyListener> listeners = this.subscribed.get(path);
		if (listeners != null) {
			listeners.remove(listener);
		}
		if (listeners.size() == 0)
			this.childrenWatchers.remove(path);
	}

	protected void removeTargetDataListener(String path, CuratorWatcher listener) {
		if (path == null) {
			throw new IllegalArgumentException("unsubscribe path == null");
		}
		if (listener == null) {
			throw new IllegalArgumentException("unsubscribe listener == null");
		}
		Set<DataNotifyListener> listeners = this.subscribedData.get(path);
		if (listeners != null) {
			listeners.remove(listener);
		}
		if (listeners.size() == 0)
			this.dataWatchers.remove(path);
	}

	public void createPathIfNotExist(String path) {
		try {
			Stat stat = (Stat) this.client.checkExists().forPath(path);
			if (stat == null)
				createPersistent(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createOrUpdate(String path, byte[] data) {
		if (data == null)
			throw new RuntimeException("data is null ,pleast write data");
		try {
			if (this.client.checkExists().forPath(path) != null)
				this.client.setData().forPath(path, data);
			else
				this.client.create().creatingParentsIfNeeded().forPath(path, data);
		} catch (KeeperException.NodeExistsException e) {
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}
}
