package com.pan.pion.codegen.bean;

/**
 * TODO
 * 
 * @author: guohm
 * @date:2015年1月12日 下午10:41:41
 * @since 1.0.0
 */
public class Field {
	private String comment;
	private String type;
	private String name;
	private String setMethodName;
	private String getMethodName;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSetMethodName() {
		return setMethodName;
	}

	public void setSetMethodName(String setMethodName) {
		this.setMethodName = setMethodName;
	}

	public String getGetMethodName() {
		return getMethodName;
	}

	public void setGetMethodName(String getMethodName) {
		this.getMethodName = getMethodName;
	}


}
