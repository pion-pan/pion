package com.pan.pion.zk;

import java.util.List;

import com.pan.pion.zk.util.URL;

public abstract interface ZookeeperClient {
	/**
	 * 创建节点
	 * 
	 * @param path
	 *            路径
	 * @param ephemeral
	 *            是否使用短暂模式 否则使用持久模式
	 */
	public abstract void create(String path, boolean ephemeral);

	/**
	 * 删除节点
	 * 
	 * @param paramString
	 */
	public abstract void delete(String paramString);

	/**
	 * 获取节点下的数据
	 * 
	 * @param paramString
	 * @return
	 */
	public abstract List<String> getChildren(String paramString);

	/**
	 * 添加子监听器
	 * 
	 * @param paramString
	 * @param paramChildListener
	 * @return
	 */
	public abstract List<String> addChildListener(String paramString, ChildListener paramChildListener);

	/**
	 * 移除子监听器
	 * 
	 * @param path
	 * @param listener
	 */
	public abstract void removeChildListener(String path, ChildListener listener);

	/**
	 * 添加状态监听器
	 * 
	 * @param listener
	 */
	public abstract void addStateListener(StateListener listener);

	/**
	 * 移除状态监听器
	 * 
	 * @param listener
	 */
	public abstract void removeStateListener(StateListener listener);

	/**
	 * 获取节点下的数据
	 * 
	 * @param paramString
	 * @return
	 */
	public abstract byte[] getDataByPath(String paramString);

	/**
	 * 设置节点数据
	 */
	public abstract void setPathData(String path, byte[] paramArrayOfByte);

	/**
	 * 判断是否已经连接
	 * 
	 * @return
	 */
	public abstract boolean isConnected();

	/**
	 * 关闭连接
	 */
	public abstract void close();

	/**
	 * 获取url
	 * 
	 * @return
	 */
	public abstract URL getUrl();

	/**
	 * 新增或更新数据
	 * 
	 * @param path
	 * @param data
	 */
	public void createOrUpdate(String path, byte[] data);

}
