/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message.impl;

import javax.jms.Message;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zw.jmsepc.silkie.jms.message.JmsBody;
import com.zw.jmsepc.silkie.jms.message.JmsMessage;
import com.zw.jmsepc.silkie.jms.message.JmsHeader;
import com.zw.jmsepc.silkie.jms.message.PropHeader;

/**
 * 缺省的Message实现
 *
 */
public class DefaultJmsMessage implements JmsMessage {

	JmsHeader jmsHeader;
	PropHeader propHeader;
	JmsBody body;
	
	Message message;
	
	public DefaultJmsMessage() {
		this(new JmsHeader(), new PropHeader());
	}
	
	public DefaultJmsMessage(JmsHeader jmsHeader, PropHeader propHeader) {
		this.jmsHeader = jmsHeader;
		this.propHeader = propHeader;
	}
	
	public JmsHeader getJmsHeader() {
		return jmsHeader;
	}
//	public void setJmsHeader(JmsHeader jmsHeader) {
//		this.jmsHeader = jmsHeader;
//	}
	public PropHeader getPropHeader() {
		return propHeader;
	}
//	public void setPropHeader(PropHeader propHeader) {
//		this.propHeader = propHeader;
//	}
	public JmsBody getBody() {
		return body;
	}
	public void setBody(JmsBody body) {
		this.body = body;
	}
	
	@Override
	public Message getMessage() {
		return message;
	}

	@Override
	public void setMessage(Message message) {
		this.message = message;
	}

	@Override	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
