package org.smartmvc.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始的监听器监听web容器启动
 * @author ZHANGYUKUN
 *
 */
public class WebInitListener implements ServletContextListener  {
	

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("contextDestroyed");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ControllerResolver.resolver();

		System.out.println("contextInitialized");
	}
	

	
}
