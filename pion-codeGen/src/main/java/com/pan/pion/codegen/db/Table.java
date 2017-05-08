package com.pan.pion.codegen.db;

import java.util.List;

/**
 * TODO
 * 
 * @author: guohm
 * @date:2015年1月12日 下午8:20:20
 * @since 1.0.0
 */
public class Table {
//	private boolean hasCol = false;
	private DataBase database;
	private String tableName;
	private List<Column> columns;

	public Table(DataBase database) {
		this.database = database;
	}

	public DataBase getDatabase() {
		return database;
	}

	public void setDatabase(DataBase database) {
		this.database = database;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
