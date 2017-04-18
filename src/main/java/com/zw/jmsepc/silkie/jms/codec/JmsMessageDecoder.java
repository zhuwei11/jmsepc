/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.codec;

import javax.jms.Message;

import com.zw.jmsepc.silkie.jms.message.JmsMessage;

/**
 * 消息解码器
 * @author zw
 * @since 1.0
 */
public interface JmsMessageDecoder {

	/**
	 * 将 jms消息 转换成原文消息
	 */
	public JmsMessage decode(Message jmsMsg) throws Exception;
}
