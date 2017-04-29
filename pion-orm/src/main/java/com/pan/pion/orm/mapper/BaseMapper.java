package com.pan.pion.orm.mapper;

import java.util.List;

import com.pan.pion.orm.BaseCondition;
import com.pan.pion.orm.DynamicParam;
import com.pan.pion.orm.SortParam;
import com.pan.pion.orm.entity.BaseEntity;

public abstract interface BaseMapper<T extends BaseEntity> {
	public abstract int insert(T paramT);

	public abstract int deleteById(Long paramLong);

	public abstract int updateById(T paramT);

	public abstract T selectById(Long paramLong);

	public abstract T selectDynamicFieldById(DynamicParam<Long> paramDynamicParam);

	public abstract List<T> selectList(BaseCondition paramBaseCondition);

	public abstract List<T> selectListBySort(SortParam<? extends BaseCondition> paramSortParam);

	public abstract List<T> selectListDynamicField(DynamicParam<? extends BaseCondition> paramDynamicParam);

	public abstract int selectCount(BaseCondition paramBaseCondition);
}
