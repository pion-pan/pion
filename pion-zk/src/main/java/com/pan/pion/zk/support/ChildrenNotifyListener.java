package com.pan.pion.zk.support;

import java.util.List;

public abstract interface ChildrenNotifyListener {
	public abstract void notify(String paramString, List<String> paramList) throws Exception;
}
