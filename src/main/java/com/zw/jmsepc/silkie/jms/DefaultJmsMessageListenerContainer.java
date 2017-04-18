/**
 * 
 */
package com.zw.jmsepc.silkie.jms;

import javax.jms.MessageListener;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.zw.jmsepc.silkie.jms.listener.JmsMessageListenerAdapter;

/**
 * @author zw
 * 2011-4-17 下午06:49:35
 * @since 1.0
 */
public class DefaultJmsMessageListenerContainer extends DefaultMessageListenerContainer {
	
	private EventFieldFactory eventFieldFactory;
	
	public DefaultJmsMessageListenerContainer(EventFieldFactory eventFieldFactory) {
		this.eventFieldFactory = eventFieldFactory;
	}
	
	public void setJmsMessageHandler(JmsMessageHandler jmsMessageHandler) {
		MessageListener messageListener = new JmsMessageListenerAdapter(jmsMessageHandler, eventFieldFactory);
		setMessageListener(messageListener);
	}

}
