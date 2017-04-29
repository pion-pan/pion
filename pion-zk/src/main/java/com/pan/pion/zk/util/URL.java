package com.pan.pion.zk.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class URL implements Serializable {

	public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");

	private final String protocol;
	private final String host;
	private final int port;
	private final String username;
	private final String password;
	private final String path;
	private final Map<String, String> parameters;

	protected URL() {
		this.protocol = null;
		this.username = null;
		this.password = null;
		this.host = null;
		this.port = 0;
		this.parameters = null;
		this.path = null;
	}

	public URL(String protocol, String host, int port) {
		this(protocol, null, null, host, port, null, (Map<String, String>) null);
	}

	public URL(String protocol, String username, String password, String host, int port, String path,
			Map<String, String> parameters) {
		if (((username == null) || (username.length() == 0)) && (password != null) && (password.length() > 0)) {
			throw new IllegalArgumentException("Invalid url, password without username!");
		}
		this.protocol = protocol;
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = (port < 0 ? 0 : port);
		this.path = path;

		while ((path != null) && (path.startsWith("/"))) {
			path = path.substring(1);
		}
		if (parameters == null)
			parameters = new HashMap<>();
		else {
			parameters = new HashMap<>(parameters);
		}
		this.parameters = Collections.unmodifiableMap(parameters);
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 拼接默认端口号
	 * 
	 * @param address：
	 *            host:port
	 * @param defaultPort
	 * @return
	 */
	private String appendDefaultPort(String address, int defaultPort) {
		if ((address != null) && (address.length() > 0) && (defaultPort > 0)) {
			int i = address.indexOf(':');
			// 如果address没有指定端口号则使用defaultPort作为端口号
			if (i < 0)
				return address + ":" + defaultPort;
			// 如果address的端口号为0则使用defaultPort作为端口号
			if (Integer.parseInt(address.substring(i + 1)) == 0) {
				return address.substring(0, i + 1) + defaultPort;
			}
		}
		return address;
	}

	public String getBackupAddress() {
		return getBackupAddress(0);
	}

	/**
	 * 获取 host:port
	 * 
	 * @return
	 */
	public String getAddress() {
		return this.host + ":" + this.port;
	}

	public String getParameter(String key) {
		String value = (String) this.parameters.get(key);
		if ((value == null) || (value.length() == 0)) {
			value = (String) this.parameters.get("default." + key);
		}
		return value;
	}

	/**
	 * 获取参数值，没有则取默认值defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String[] getParameter(String key, String[] defaultValue) {
		String value = getParameter(key);
		if ((value == null) || (value.length() == 0)) {
			return defaultValue;
		}
		return COMMA_SPLIT_PATTERN.split(value);
	}

	/**
	 * 获取配置地址+备用的地址
	 * 
	 * @param defaultPort
	 * @return
	 */
	public String getBackupAddress(int defaultPort) {
		StringBuilder address = new StringBuilder(appendDefaultPort(getAddress(), defaultPort));
		String[] backups = getParameter("backup", new String[0]);
		if ((backups != null) && (backups.length > 0)) {
			for (String backup : backups) {
				address.append(",");
				address.append(appendDefaultPort(backup, defaultPort));
			}
		}
		return address.toString();
	}

	/**
	 * 获取权限字符串 :(username:password)
	 * 
	 * @return
	 */
	public String getAuthority() {
		if (((this.username == null) || (this.username.length() == 0))
				&& ((this.password == null) || (this.password.length() == 0))) {
			return null;
		}
		return (this.username == null ? "" : this.username) + ":" + (this.password == null ? "" : this.password);
	}
}
