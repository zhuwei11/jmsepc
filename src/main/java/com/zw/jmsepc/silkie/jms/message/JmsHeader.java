/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message;

import javax.jms.DeliveryMode;
import javax.jms.Destination;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * activemq自带的数据段
 *
 */
public class JmsHeader {
	private long expiration = 0; //过期时间
	private int priority = 4; //优先级
	private String messageId;
	private String correlationId;
	private Destination replyTo;
	private String type;
	private boolean redelivered;
	private int deliveryMode = DeliveryMode.NON_PERSISTENT;
	private Destination destination;
	private long timestamp;
	
	public void setJMSExpiration(long expiration) {
		this.expiration = expiration;
	}
	public long getJMSExpiration() {
		return expiration;
	}

	public void setJMSPriority(int priority) {
		this.priority = priority;
	}
	public int getJMSPriority() {
		return priority;
	}
	
	public String getJMSMessageID() {
		return messageId;
	}
	public void setJMSMessageID(String messageId) {
		this.messageId = messageId;
	}
	
	public String getJMSCorrelationID() {
		return correlationId;
	}
	public void setJMSCorrelationID(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public Destination getJMSReplyTo() {
		return replyTo;
	}
	public void setJMSReplyTo(Destination replyTo) {
		this.replyTo = replyTo;
	}
	
	public String getJMSType() {
		return type;
	}
	public void setJMSType(String type) {
		this.type = type;
	}
	
	public boolean getJMSRedelivered() {
		return redelivered;
	}
	public void setJMSRedelivered(boolean redelivered) {
		this.redelivered = redelivered;
	}
	
	public int getJMSDeliveryMode() {
		return deliveryMode;
	}
	public void setJMSDeliveryMode(int deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	
	public Destination getJMSDestination() {
		return destination;
	}
	public void setJMSDestination(Destination destination) {
		this.destination = destination;
	}
	
	public long getJMSTimestamp() {
		return timestamp;
	}
	public void setJMSTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
