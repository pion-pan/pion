package com.pan.pion.codegen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * TODO
 * 
 * @author: guohm
 * @date:2015年1月12日 下午7:58:57
 * @since 1.0.0
 */
public class VelocityUtil {
	static {
		try {
			Properties p = new Properties();
			p.setProperty("resource.loader", "class");
			p.setProperty("class.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			Velocity.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 渲染模板内容.
	 *
	 * @param templateContent
	 *            模板内容.
	 * @param context
	 *            变量Map.
	 */
	public static String renderTemplateContent(String templateContent,
			Map<String, ?> context) throws Exception {
		VelocityContext velocityContext = new VelocityContext(context);
		StringWriter result = new StringWriter();
		Velocity.evaluate(velocityContext, result, "", templateContent);
		return result.toString();
	}

	/**
	 * 渲染模板内容.输出到指定的文件中
	 * 
	 * @param templateStream
	 *            模板内容.
	 * @param fileName
	 *            输出文件名
	 * @param context
	 *            变量Map.
	 * @return
	 * @throws Exception
	 */
	public static String renderTemplateContent(InputStream templateStream,
			String fileName, Map<String, ?> context) throws Exception {
		VelocityContext velocityContext = new VelocityContext(context);
		Reader reader = new BufferedReader(
				new InputStreamReader(templateStream));
		Writer writer = new FileWriter(new File(fileName));
		Velocity.evaluate(velocityContext, writer, "", reader);
		return writer.toString();
	}

	public static String renderTemplateContent(String templateContent,
			String fileName, Map<String, ?> context) throws Exception {
		VelocityContext velocityContext = new VelocityContext(context);
		Writer writer = new FileWriter(new File(fileName));
		Velocity.evaluate(velocityContext, writer, "", templateContent);
		writer.flush();
		return writer.toString();
	}

	/**
	 * 渲染模板文件.
	 *
	 * @param velocityEngine
	 *            velocityEngine, 需经过VelocityEngineFactory处理,
	 *            绑定Spring的ResourceLoader.
	 * @param templateFilePName
	 *            模板文件名, loader会自动在前面加上velocityEngine的resourceLoaderPath.
	 * @param context
	 *            变量Map.
	 */
	public static String renderFile(String templateFilePName,
			VelocityEngine velocityEngine, String encoding,
			Map<String, ?> context) throws Exception {
		VelocityContext velocityContext = new VelocityContext(context);

		StringWriter result = new StringWriter();
		velocityEngine.mergeTemplate(templateFilePName, encoding,
				velocityContext, result);
		return result.toString();
	}

	

	public static void renderFile(String templateFileName, String targetFileName,
			Map<String, ?> context) throws Exception {
		VelocityContext velocityContext = new VelocityContext(context);
		Writer writer = new FileWriter(new File(targetFileName));
		//template.merge(velocityContext, writer);
		Velocity.mergeTemplate(templateFileName, "UTF8", velocityContext, writer);
		writer.flush();
	}
	
	public static String renderFile2(String templateFileName, 
			String encoding, Map<String, ?> context) throws Exception {

		VelocityContext velocityContext = new VelocityContext(context);
		StringWriter sw = new StringWriter();// 输出

		if (StringUtils.isEmpty(encoding)) {
			encoding = "UTF-8";	//默认使用UTF-8的字符编码格式
		}
		Velocity.mergeTemplate(templateFileName, encoding, velocityContext, sw);
		return sw.toString();
	}
	public static void main(String[] args) throws Exception{
		Map<String, String> context = new HashMap<String, String>();
		//String renderFile2 = VelocityUtil.renderFile2("entity.vm", null, context);
		//System.out.println(renderFile2);
		VelocityUtil.renderFile("entity.vm", "d:\\a.txt", context);
	}
}
