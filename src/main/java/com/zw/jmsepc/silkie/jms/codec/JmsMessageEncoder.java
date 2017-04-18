/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.codec;

import javax.jms.Message;
import javax.jms.Session;

import com.zw.jmsepc.silkie.jms.message.JmsMessage;

/**
 * 消息编码器
 * @author zw
 * @since 1.0
 */
public interface JmsMessageEncoder {

	/**
	 * 将原文消息 转换成 jms消息
	 */
	public Message encode(Session session, JmsMessage jmsMsg) throws Exception;
}
