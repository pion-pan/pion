package com.pan.pion.codegen.bean;


/**
 * TODO
 * @author: guohm 
 * @date:2015年1月12日 下午9:48:14
 * @since 1.0.0
 */
public class Dao {
	private String packageName;
	private String clazzName;
	private String beanName;
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
	/**
	 * @return the beanName
	 */
	public String getBeanName() {
		return beanName;
	}
	/**
	 * @param beanName the beanName to set
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
}
