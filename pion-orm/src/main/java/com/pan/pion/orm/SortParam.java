package com.pan.pion.orm;

public class SortParam<T> extends Sort {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected T condition;

	public T getCondition() {
		return this.condition;
	}

	public void setCondition(T condition) {
		this.condition = condition;
	}
}
