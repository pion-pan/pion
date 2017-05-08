package com.pan.pion.codegen;

import java.util.ArrayList;
import java.util.List;

import com.pan.pion.codegen.bean.Context;
import com.pan.pion.codegen.bean.DaoBean;
import com.pan.pion.codegen.bean.TypeAlia;
import com.pan.pion.codegen.cfg.Config;
import com.pan.pion.codegen.db.DataBase;
import com.pan.pion.codegen.db.Table;
import com.pan.pion.codegen.generator.ConditionGenerator;
import com.pan.pion.codegen.generator.DaoBeanGenerator;
import com.pan.pion.codegen.generator.DaoGenerator;
import com.pan.pion.codegen.generator.EntityGenerator;
import com.pan.pion.codegen.generator.MapperGenerator;
import com.pan.pion.codegen.generator.SqlMapGenerator;

/**
 * TODO
 * @author: guohm 
 * @date:2015年1月12日 下午7:50:23
 * @since 1.0.0
 */
public class Engine {

	/**
	 * TODO
	 * <p><b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * @param args
	 * @author: guohm 
	 * @throws Exception 
	 * @date:2015年1月12日 下午7:50:23
	 * @since 1.0.0
	 */
	public static void main(String[] args) throws Exception {
		Config cfg = Config.init();
		DataBase db = new DataBase(cfg);
		List<Table> tables = db.getTables();
		if(tables == null || tables.isEmpty()){
			throw new Exception("请检查配置，没有要生成代码的表");
		}
		List<String> mappers = new ArrayList<String>();
		List<TypeAlia> typeAliases = new ArrayList<TypeAlia>();
		List<DaoBean> daobeanList = new ArrayList<DaoBean>();
		for(Table table: tables){
			Context context = new Context();
			context.parse(table);
			EntityGenerator eg = new EntityGenerator();
			eg.generate(table, context);
			ConditionGenerator cg = new ConditionGenerator();
			cg.generate(table, context);
			DaoGenerator dg = new DaoGenerator();
			dg.generate(table, context);
			MapperGenerator mg = new MapperGenerator();
			mg.generate(table, context);
			
			//生成daobean
			DaoBean bean = new DaoBean();
			bean.setBeanName(context.getDao().getBeanName());
			bean.setFullClazzName(context.getDao().getPackageName() + "." + context.getDao().getClazzName());
			daobeanList.add(bean);
			
			//生成主配置文件
			TypeAlia a = new TypeAlia();
			a.setAlias(context.getEntity().getClazzName());
			a.setType(context.getEntity().getPackageName()+"."+context.getEntity().getClazzName());
			TypeAlia b = new TypeAlia();
			b.setAlias(context.getCondition().getClazzName());
			b.setType(context.getCondition().getPackageName()+"."+context.getCondition().getClazzName());
			typeAliases.add(a);
			typeAliases.add(b);
			//mappers.add(context.getMapper().getFilePath());
			mappers.add("META-INF/spring/domain/mybatis/" + context.getMapper().getFilePath());
			System.out.println("生成表：" + table.getTableName() + "的代码。。。");
		}
		SqlMapGenerator smg = new SqlMapGenerator();
		smg.generate(typeAliases, mappers, cfg.getFilepath());
		DaoBeanGenerator dbg = new DaoBeanGenerator();
		dbg.generate(daobeanList, cfg.getFilepath());
	}
	
}
