package com.pan.pion.codegen.generator;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pan.pion.codegen.VelocityUtil;
import com.pan.pion.codegen.bean.DaoBean;

/**
 * TODO
 * @author: guohm 
 * @date:2015年1月12日 下午11:08:38
 * @since 1.0.0
 */
public class DaoBeanGenerator{

	public void generate(List<DaoBean> daobeanList, String filePath)throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("beans", daobeanList);
		filePath = filePath + "/conf/dao.xml";
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
		VelocityUtil.renderFile("dao.xml.vm", filePath,  data);
	}
}
