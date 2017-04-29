package com.pan.pion.common.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.pan.pion.log.AppLogger;

public class ExecutorManager {
	private static AppLogger logger = AppLogger.getLogger(ExecutorManager.class);

	// 池中保留的线程数
	private static int taskMinNumber = 5;
	// 池中最大线程数
	private static int taskMaxNumber = 20;
	// 队列最大接收任务数
	private static int queueCapacity = 30;
	// 线程空闲时间
	private static long keepAliveTime = 60l;
	private static ExecutorService instance;

	/**
	 * 单例，防止外部实例化
	 */
	private ExecutorManager() {

	}

	/**
	 * 初始化
	 */
	public static void init() {
		// 如果线程数小于taskMinNumber则直接新建线程执行
		// 如果线程数大于taskMinNumber且LinkedBlockingQueue未满时加入队列
		// 如果队列已满则新建线程
		// 如果线程总量大于taskMaxNumber 则进行reject（默认抛异常）
		// 为保证任务被执行重写reject机制
		instance = new ThreadPoolExecutor(taskMinNumber, taskMaxNumber, keepAliveTime, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queueCapacity), new RejectedExecutionHandler() {

					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						try {
							executor.getQueue().put(r);
						} catch (InterruptedException e) {
							logger.error("thread rejectHandler failed");
						}
					}

				});
		// 允许核心线程超时自动关闭
		((ThreadPoolExecutor) instance).allowCoreThreadTimeOut(true);
	}

	/**
	 * 关闭
	 */
	public static void shutdown() {
		if (instance != null) {
			instance.shutdown();
		}
	}

	public static ExecutorService getInstance() {
		return instance;
	}

	public static void execute(Runnable target) {
		if (instance != null) {
			instance.execute(target);
		} else {
			new Thread(target).start();
		}
	}
}
