package com.pan.pion.codegen.generator;

import org.apache.commons.lang.StringUtils;

import com.pan.pion.codegen.ICodeGenerator;
import com.pan.pion.codegen.bean.Context;
import com.pan.pion.codegen.cfg.Config;
import com.pan.pion.codegen.db.Table;

/**
 * TODO
 * @author: guohm 
 * @date:2015年1月12日 下午11:17:37
 * @since 1.0.0
 */
public abstract class AbstractGenerator  implements ICodeGenerator{
	
	public abstract void generate(Table table, Context context)throws Exception ;

	protected String getFilePath(Table table) {
		Config cfg = table.getDatabase().getCfg();
		return cfg.getFilepath();
	}
	
	protected String getPackagePath(Table table) {
		StringBuilder path = new StringBuilder();
		Config cfg = table.getDatabase().getCfg();
		String[] dirs = StringUtils.split(cfg.getPackageName(), "\\.");
		for (int i = 0; i < dirs.length; i++) {
			path.append("/").append(dirs[i]);
		}
		return path.toString();
	}
	
}
