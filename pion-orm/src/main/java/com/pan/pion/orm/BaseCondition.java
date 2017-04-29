package com.pan.pion.orm;

public abstract class BaseCondition {
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
