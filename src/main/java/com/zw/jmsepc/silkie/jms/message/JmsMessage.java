/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message;


/**
 * Gryphon使用的Message类
 * 将JMS的Message封装起来
 *
 */
public interface JmsMessage extends MessageAware {

	/** JMS固定的消息头 */
	JmsHeader getJmsHeader();
	/** JMS可自设置的消息头 */
	PropHeader getPropHeader();
	/** 消息体 */
	JmsBody getBody();
}
