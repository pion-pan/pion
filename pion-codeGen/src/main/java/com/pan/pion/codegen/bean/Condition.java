package com.pan.pion.codegen.bean;


/**
 * TODO
 * 
 * @author: guohm
 * @date:2015年1月12日 下午9:47:17
 * @since 1.0.0
 */
public class Condition {
	private String packageName;
	//private List<Field> fields;
	//private List<String> imports;
	private String clazzName;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

}
