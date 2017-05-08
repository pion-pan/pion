package com.pan.pion.codegen.bean;

import java.util.List;

/**
 * TODO
 * 
 * @author: guohm
 * @date:2015年1月12日 下午9:48:30
 * @since 1.0.0
 */
public class Mapper {
	private String filePath;
	private String tableName;
	private List<MapperField> fields;
	private String resultMapId;

	public List<MapperField> getFields() {
		return fields;
	}

	public void setFields(List<MapperField> fields) {
		this.fields = fields;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getResultMapId() {
		return resultMapId;
	}

	public void setResultMapId(String resultMapId) {
		this.resultMapId = resultMapId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
