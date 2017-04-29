package com.pan.pion.common.Utils;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.pan.pion.common.exception.PionException;

public class ConfigHelper {
	/**
	 * 
	 * 获取配置
	 * 
	 * @param clazz
	 * @param configPath
	 * @return
	 * @throws JAXBException
	 */
	public static <T> T getConfig(Class<T> clazz, String configPath) throws JAXBException {
		InputStream configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);
		if (configStream == null) {
			// 获取配置失败返回异常或者直接抛出异常
			throw new PionException("获取配置文件失败");
		}
		return xmlToBean(clazz, configStream);
	}

	public static <T> T getConfig(Class<T> clazz, InputStream is) throws JAXBException {
		return xmlToBean(clazz, is);
	}

	/**
	 * xml转对象
	 * 
	 * @param clazz
	 * @param is
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(Class<T> clazz, InputStream is) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T) unmarshaller.unmarshal(is);
	}
}
