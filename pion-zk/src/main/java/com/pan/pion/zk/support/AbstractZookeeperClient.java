package com.pan.pion.zk.support;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.pan.pion.zk.ChildListener;
import com.pan.pion.zk.StateListener;
import com.pan.pion.zk.ZookeeperClient;
import com.pan.pion.zk.util.URL;

public abstract class AbstractZookeeperClient<T> implements ZookeeperClient {

	private final URL url;

	private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<>();

	private final ConcurrentMap<String, ConcurrentMap<ChildListener, T>> childListeners = new ConcurrentHashMap<>();

	private volatile boolean closed = false;

	public AbstractZookeeperClient(URL url) {
		this.url = url;
	}

	@Override
	public void create(String path, boolean ephemeral) {
		int i = path.lastIndexOf('/');
		if (i > 0) {
			create(path.substring(0, i), false);
		}
		if (ephemeral)
			createEphemeral(path);
		else
			createPersistent(path);
	}

	@Override
	public List<String> addChildListener(String path, ChildListener listener) {
		ConcurrentMap<ChildListener, T> listeners = (ConcurrentMap<ChildListener, T>) this.childListeners.get(path);
		if (listeners == null) {
			// 如果不存在path这个数据的话就添加
			this.childListeners.putIfAbsent(path, new ConcurrentHashMap<ChildListener, T>());
			listeners = (ConcurrentMap<ChildListener, T>) this.childListeners.get(path);
		}
		T targetListener = listeners.get(listener);
		if (targetListener == null) {
			// 如果当前listener下没有子listener就新建一个子监听器
			listeners.putIfAbsent(listener, createTargetChildListener(path, listener));
			targetListener = listeners.get(listener);
		}
		return addTargetChildListener(path, targetListener);
	}

	@Override
	public void removeChildListener(String path, ChildListener listener) {
		ConcurrentMap<ChildListener, T> listeners = (ConcurrentMap<ChildListener, T>) this.childListeners.get(path);
		if (listeners != null) {
			T targetListener = listeners.remove(listener);
			if (targetListener != null)
				removeTargetChildListener(path, targetListener);
		}
	}

	@Override
	public void addStateListener(StateListener listener) {
		this.stateListeners.add(listener);
	}

	@Override
	public void removeStateListener(StateListener listener) {
		this.stateListeners.remove(listener);
	}

	@Override
	public void close() {
		if (this.closed) {
			return;
		}
		this.closed = true;
		try {
			doClose();
		} catch (Throwable t) {
		}

	}

	@Override
	public URL getUrl() {
		return this.url;
	}

	public Set<StateListener> getSessionListeners() {
		return this.stateListeners;
	}

	protected void stateChanged(int state) {
		for (StateListener sessionListener : getSessionListeners())
			sessionListener.stateChanged(state);
	}

	protected abstract void doClose();

	/**
	 * 新建持久模式的节点
	 * 
	 * @param path
	 */
	protected abstract void createPersistent(String path);

	/**
	 * 新建短暂模式的节点
	 * 
	 * @param path
	 */
	protected abstract void createEphemeral(String path);

	/**
	 * 添加
	 * 
	 * @param path
	 * @param targetListener
	 * @return
	 */
	protected abstract List<String> addTargetChildListener(String path, T targetListener);

	/**
	 * 为targetListener新增一个子监听器
	 * 
	 * @param path
	 * @param targetListener
	 * @return
	 */
	protected abstract T createTargetChildListener(String path, ChildListener targetListener);

	protected abstract void removeTargetChildListener(String path, T listener);

}
