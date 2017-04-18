/**
 * 
 */
package com.zw.jmsepc.silkie.jms.listener;

import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import com.zw.jmsepc.silkie.jms.EventFieldFactory;
import com.zw.jmsepc.silkie.jms.JmsMessageHandler;
import com.zw.jmsepc.silkie.jms.converter.DefaultMessageConverter;

/**
 * JMS消息适配器
 * @author zw
 * 2011-4-15 下午08:50:22
 * @since 1.0
 */
public class JmsMessageListenerAdapter extends MessageListenerAdapter {

	private String defaultListenerMethod = "recvMessage";
	
	public JmsMessageListenerAdapter(EventFieldFactory eventFieldFactory) {
		super();
		setMessageConverter(new DefaultMessageConverter(eventFieldFactory));
	}
	
	public JmsMessageListenerAdapter(JmsMessageHandler jmsMessageHandler, EventFieldFactory eventFieldFactory) {
		super(jmsMessageHandler);
		setMessageConverter(new DefaultMessageConverter(eventFieldFactory));
	}

	@Override
	public String getDefaultListenerMethod() {
		return defaultListenerMethod;
	}
	@Override
	public void setDefaultListenerMethod(String defaultListenerMethod) {
		this.defaultListenerMethod = defaultListenerMethod;
	}


}
