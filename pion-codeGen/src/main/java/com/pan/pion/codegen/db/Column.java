package com.pan.pion.codegen.db;

/**
 * TODO
 * @author: guohm 
 * @date:2015年1月12日 下午9:24:17
 * @since 1.0.0
 */
public class Column {
	private String colName;
	private String colType;
	private String comment;
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
