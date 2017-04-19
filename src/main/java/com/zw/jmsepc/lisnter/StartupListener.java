package com.zw.jmsepc.lisnter;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.Lifecycle;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.zw.jmsepc.silkie.jms.DefaultJmsMessageListenerContainer;
import com.zw.jmsepc.spring.SpringBeanManager;


@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger logger = Logger.getLogger(StartupListener.class);
//	@Resource
//	JobManager jobManager;
//	@Resource
//	RedisUtil redis;
	@Resource
	DefaultJmsMessageListenerContainer traderRequestListenerContainer;
	@Resource(name="traderNoticeListenerContainer")
	DefaultJmsMessageListenerContainer traderNoticeListenerContainer;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {

        // 防止启动两次

        if (evt.getApplicationContext().getParent() != null) {
    		Lifecycle traderRequestListener = traderRequestListenerContainer;
    		traderRequestListener.start();//启动监听请求消息队列
    		Lifecycle traderNoticeListener = traderNoticeListenerContainer;
    		traderNoticeListener.start();//启动监听通知消息队列
    		logger.info("StartupListener启动");
    		logger.info("activemq监听启动");
    		System.out.println(traderRequestListenerContainer == SpringBeanManager.getBean("traderRequestListenerContainer",DefaultJmsMessageListenerContainer.class));
//            buildIndex();
//            Thread subThread = new Thread(new Runnable() {  
//                @Override  
//                public void run() {  
//                    try{  
//                    	JedisPubSub listener = new ChatListener();
//                    	redis.sub(listener, "test");
//                    }catch(Exception e){  
//                        e.printStackTrace();  
//                    }  
//                      
//                }  
//            });  
//            subThread.start();

        }

    }


//    private void buildIndex() {
//    	logger.info("StartupListener启动");
//    	jobManager.startJob();
//
//    }
}