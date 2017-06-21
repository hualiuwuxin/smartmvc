package org.smartmvc.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.smartmvc.core.envi.RequesteEnvironmentHolder;
import org.smartmvc.core.resolver.ControllerResolver;

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
		String configurationPath = servletContextEvent.getServletContext().getInitParameter("configurationPath");
		
		try {
			this.getClass().getClassLoader().loadClass( configurationPath).newInstance();
			RequesteEnvironmentHolder.setRealPath( servletContextEvent.getServletContext().getRealPath("/") );
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("不能实例化配置参数类");
			e.printStackTrace();
		}
		
		ControllerResolver.resolver();
		System.out.println("contextInitialized");
	}
	

	
}
