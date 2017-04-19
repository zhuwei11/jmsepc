package com.zw.jmsepc.lisnter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zw.jmsepc.silkie.jms.DefaultJmsMessageListenerContainer;
import com.zw.jmsepc.spring.SpringBeanManager;



public class MyListener implements ServletContextListener {
	DefaultJmsMessageListenerContainer traderResponseListenerContainer;
	
	private static Logger logger = Logger.getLogger(MyListener.class);
	public void contextInitialized(ServletContextEvent evt) {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:spring-context.xml");
    	SpringBeanManager.initContext(ctx);
		logger.info("ApplicationListener Starting...............................");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
}
