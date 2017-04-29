package com.pan.pion.zk;

import java.util.List;

public abstract interface ChildListener {
	public abstract void childChanged(String paramString, List<String> paramList);
}
