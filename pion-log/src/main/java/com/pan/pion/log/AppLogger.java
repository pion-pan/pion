package com.pan.pion.log;

import org.apache.log4j.Logger;

public class AppLogger {
	private Logger logger = null;

	/**
	 * 私有构造方法。防止外部实例化
	 */
	private AppLogger() {
	}

	/**
	 * Shorthand for getLogger(clazz.getName()).
	 * 
	 * @param clazz
	 *            The name of clazz will be used as the name of the logger to
	 *            retrieve. See getLogger(String) for more detailed information.
	 * @return
	 */
	public static AppLogger getLogger(Class<?> clazz) {
		AppLogger appLogger = new AppLogger();
		appLogger.logger = Logger.getLogger(clazz);
		return appLogger;
	}

	/**
	 * Retrieve a logger named according to the value of the name parameter. If
	 * the named logger already exists, then the existing instance will be
	 * returned. Otherwise, a new instance is created. By default, loggers do
	 * not have a set level but inherit it from their neareast ancestor with a
	 * set level. This is one of the central features of log4j.
	 * 
	 * @param name
	 *            The name of the logger to retrieve.
	 * @return
	 */
	public static AppLogger getLogger(String name) {
		AppLogger appLogger = new AppLogger();
		appLogger.logger = Logger.getLogger(name);
		return appLogger;
	}

	/**
	 * Return the root logger for the current logger repository.
	 * 
	 * @return
	 */
	public static AppLogger getRootLogger() {
		AppLogger appLogger = new AppLogger();
		appLogger.logger = Logger.getRootLogger();
		return appLogger;
	}

	/**
	 * Check whether this category is enabled for the TRACE Level.
	 * 
	 * @return
	 */
	public boolean isTraceEnabled() {
		return this.logger.isTraceEnabled();
	}

	/**
	 * Log a message object with the TRACE level.
	 * 
	 * @param message
	 *            message - the message object to log.
	 */
	public void trace(Object message) {
		this.logger.trace(message);
	}

	/**
	 * Log a message object with the TRACE level including the stack trace of
	 * the Throwablet passed as parameter.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void trace(Object message, Throwable t) {
		this.logger.trace(message, t);
	}

	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	/**
	 * Log a message object with the DEBUG level.
	 * 
	 * @param message
	 *            the message object to log.
	 */
	public void debug(Object message) {
		this.logger.debug(message);
	}

	/**
	 * Log a message object with the DEBUG level including the stack trace of
	 * the Throwable t passed as parameter.
	 * 
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void debugException(Throwable t) {
		this.logger.debug("", t);
	}

	/**
	 * Log a message object with the DEBUG level including the stack trace of
	 * the Throwable t passed as parameter.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void debug(Object message, Throwable t) {
		this.logger.debug(message, t);
	}

	/**
	 * Log a message object with the ERROR Level.
	 * 
	 * @param message
	 *            the message object to log
	 */
	public void error(Object message) {
		this.logger.error(message);
	}

	/**
	 * Log a message object with the ERROR level including the stack trace of
	 * the Throwable t passed as parameter.
	 * 
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void errorException(Throwable t) {
		this.logger.error("", t);
	}

	/**
	 * Log a message object with the ERROR level including the stack trace of
	 * the Throwable t passed as parameter.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void error(Object message, Throwable t) {
		this.logger.error(message, t);
	}

	/**
	 * Log a message object with the FATAL Level.
	 * 
	 * @param message
	 *            the message object to log
	 */
	public void fatal(Object message) {
		this.logger.fatal(message);
	}

	/**
	 * Log a message object with the FATAL level including the stack trace of
	 * the Throwable t passed as parameter.
	 * 
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void fatalException(Throwable t) {
		this.logger.fatal("", t);
	}

	/**
	 * Log a message object with the FATAL level including the stack trace of
	 * the Throwable t passed as parameter.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void fatal(Object message, Throwable t) {
		this.logger.fatal(message, t);
	}

	/**
	 * Log a message object with the INFO Level.
	 * 
	 * @param message
	 *            message - the message object to log
	 */
	public void info(Object message) {
		this.logger.info(message);
	}

	/**
	 * Log a message object with the INFO level including the stack trace of the
	 * Throwable t passed as parameter.
	 * 
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void info(Throwable t) {
		this.logger.info("", t);
	}

	/**
	 * Log a message object with the INFO level including the stack trace of the
	 * Throwable t passed as parameter.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void info(Object message, Throwable t) {
		this.logger.info(message, t);
	}

	/**
	 * Log a message object with the WARN Level.
	 * 
	 * @param message
	 *            the message object to log.
	 */
	public void warn(Object message) {
		this.logger.warn(message);
	}

	/**
	 * Log a message with the WARN level including the stack trace of the
	 * Throwable t passed as parameter.
	 * 
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void warn(Throwable t) {
		this.logger.warn("", t);
	}

	/**
	 * Log a message with the WARN level including the stack trace of the
	 * Throwable t passed as parameter.
	 * 
	 * @param message
	 *            the message object to log.
	 * @param t
	 *            the exception to log, including its stack trace.
	 */
	public void warn(Object message, Throwable t) {
		this.logger.warn(message, t);
	}

	/**
	 * Return the category name.
	 * 
	 * @return
	 */
	public String getName() {
		return this.logger.getName();
	}
}
