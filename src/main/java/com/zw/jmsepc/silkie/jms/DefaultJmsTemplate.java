/**
 * 
 */
package com.zw.jmsepc.silkie.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

import com.zw.jmsepc.silkie.jms.converter.DefaultMessageConverter;

/**
 * 
 * @author zw
 * 2011-4-17 下午05:12:15
 * @since 1.0
 */
public class DefaultJmsTemplate extends JmsTemplate {
	
	
	public EventFieldFactory eventFieldFactory;
	
	public DefaultJmsTemplate() {
		super();
	}
	
	@Override
	public MessageConverter getMessageConverter() {
		MessageConverter messageConverter = super.getMessageConverter();
		if(messageConverter == null || !(messageConverter instanceof DefaultMessageConverter)) {
			setMessageConverter(new DefaultMessageConverter(eventFieldFactory));
		}
		return super.getMessageConverter();
	}

	public void setEventFieldFactory(EventFieldFactory eventFieldFactory) {
		this.eventFieldFactory = eventFieldFactory;
	}

	public EventFieldFactory getEventFieldFactory() {
		return eventFieldFactory;
	}
	

}
