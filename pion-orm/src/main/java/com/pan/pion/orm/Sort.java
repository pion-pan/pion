package com.pan.pion.orm;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class Sort implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<SortField> sorts;

	public List<SortField> getSorts() {
		return this.sorts;
	}

	public void setSorts(List<SortField> sorts) {
		this.sorts = sorts;
	}

	public final Integer getSortCount() {
		if (CollectionUtils.isEmpty(this.sorts)) {
			return null;
		}
		return Integer.valueOf(this.sorts.size());
	}
}
