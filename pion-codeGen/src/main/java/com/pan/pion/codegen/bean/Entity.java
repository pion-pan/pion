package com.pan.pion.codegen.bean;

import java.util.List;

/**
 * TODO
 * 
 * @author: guohm
 * @date:2015年1月12日 下午9:47:17
 * @since 1.0.0
 */
public class Entity {
	private String packageName;
	private List<Field> fields;
	private List<String> imports;
	private String clazzName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports = imports;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

}
