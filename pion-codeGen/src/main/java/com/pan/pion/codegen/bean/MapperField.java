package com.pan.pion.codegen.bean;

import com.google.common.base.CaseFormat;

/**
 * TODO
 * @author: guohm 
 * @date:2015年1月13日 上午2:04:10
 * @since 1.0.0
 */
public class MapperField {
	CaseFormat fromFormat = CaseFormat.LOWER_CAMEL;
	 CaseFormat toFormat = CaseFormat.UPPER_UNDERSCORE;
	private String col;
	private String property;
	private String jdbcType;
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
	public String getUpperCol() {
		return col.toUpperCase();
	}
}
