package com.zw.jmsepc.spring;

import org.springframework.context.ApplicationContext;

/**
 * spring 手动获取bean
 * 
 * @author defier.lai 2010-8-19 下午03:38:30
 * @since 1.0
 */
public class SpringBeanManager {

	private static ApplicationContext context;

	public static void initContext(ApplicationContext ctx) {
		context = ctx;
	}
	
	public static ApplicationContext getContext() {
		return context;
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> cls) {
		return context.getBean(name, cls);
	}

}
