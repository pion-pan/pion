package com.pan.pion.codegen;

import com.pan.pion.codegen.bean.Context;
import com.pan.pion.codegen.db.Table;

/**
 * TODO
 * @author: guohm 
 * @date:2015年1月12日 下午9:00:27
 * @since 1.0.0
 */
public interface ICodeGenerator {
	void generate(Table table, Context context)throws Exception;
}
