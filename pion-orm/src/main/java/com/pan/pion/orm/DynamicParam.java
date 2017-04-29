package com.pan.pion.orm;

import java.util.List;

public class DynamicParam<T> extends Sort {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> selectField;
	private T condition;

	public List<String> getSelectField() {
		return this.selectField;
	}

	public void setSelectField(List<String> selectField) {
		this.selectField = selectField;
	}

	public T getCondition() {
		return this.condition;
	}

	public void setCondition(T condition) {
		this.condition = condition;
	}
}
