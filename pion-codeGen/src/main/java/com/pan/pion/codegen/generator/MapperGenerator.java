package com.pan.pion.codegen.generator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.pan.pion.codegen.VelocityUtil;
import com.pan.pion.codegen.bean.Context;
import com.pan.pion.codegen.db.Table;

/**
 * TODO
 * @author: guohm 
 * @date:2015年1月12日 下午11:08:38
 * @since 1.0.0
 */
public class MapperGenerator extends AbstractGenerator {

	/* (non-Javadoc)
	 * @see com.dd.codegen.ICodeGenerator#generate(com.dd.codegen.db.Table, com.dd.codegen.bean.Context)
	 */
	@Override
	public void generate(Table table, Context context)throws Exception{
		Map<String, Context> data = new HashMap<String, Context>();
		data.put("data", context);
		String filePath = this.getFilePath(table, context);
		File file = new File(filePath);
		File pFile = file.getParentFile();
		if (!pFile.exists()) {
			pFile.mkdirs();
		}
		if(!file.exists()){
			file.createNewFile();
		}else{
			file.delete();
			file.createNewFile();
		}
		VelocityUtil.renderFile("mapper.vm", filePath,  data);
	}

	
	private String getFilePath(Table table, Context context) {
		String filePath = super.getFilePath(table);
		//String packagePath = super.getPackagePath(table);
		StringBuilder path = new StringBuilder(filePath);
		path.append("/conf/common/META-INF/spring/domain/mybatis/")
			.append(context.getEntity().getClazzName())
			.append(".mapper.xml");
		
		//String[] dirs = StringUtils.split(table.getDatabase().getCfg().getPackageName(), "\\.");
		StringBuilder mapperPath = new StringBuilder("");
		//mapperPath.append(dirs[0]);
		/*for (int i = 1; i < dirs.length; i++) {
			mapperPath.append("/").append(dirs[i]);
		}*/
		//mapperPath.append("")
		mapperPath.append(context.getEntity().getClazzName()).append(".mapper.xml");
		context.getMapper().setFilePath(mapperPath.toString());
		return path.toString();
	}
	
	
}
