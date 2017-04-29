package com.pan.pion.zk;

public abstract interface StateListener {
	public static final int DISCONNECTED = 0;
	public static final int CONNECTED = 1;
	public static final int RECONNECTED = 2;

	public abstract void stateChanged(int paramInt);
}
