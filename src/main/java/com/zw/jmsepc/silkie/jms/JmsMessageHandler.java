/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms;

import javax.jms.Destination;

import com.zw.jmsepc.silkie.jms.message.JmsMessage;


/**
 * 接收消息和发送消息回调接口
 * @author zw
 * 2011-4-15 下午03:47:49
 * @since 1.0
 */
public interface JmsMessageHandler {
	
	/**
	 * 收到已解码的消息
	 * @param message
	 * @throws Exception
	 */
	public void recvMessage(JmsMessage message) throws Exception;
	 
	 /**
	  * 发送私有消息
	  * @param destination
	  * @param message
	  * @throws Exception
	  */
	public void sendPrivateMsg(Destination destination, JmsMessage message, AppInfo srcInfo, AppInfo destApp) throws Exception;
	 /**
	  * 发送反私有消息
	  * @param destination
	  * @param message
	  * @throws Exception
	  */
	public void sendPrivateReverseMsg(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception;
	
	/**
	  * 发送对话请求消息
	  * @param destination
	  * @param message
	  * @throws Exception
	  */
	public void sendDialogRequest(Destination destination, JmsMessage message, AppInfo srcInfo, AppInfo destApp) throws Exception;
	
	/**
	  * 发送对话应答消息
	  * @param destination
	  * @param message
	  * @throws Exception
	  */
	public void sendDialogResponse(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception;
	/**
	  * 发送反对话请求消息
	  * @param destination
	  * @param message
	  * @throws Exception
	  */
	public void sendDialogReverseRequest(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception;
	/**
	  * 发送反对话应答消息
	  * @param destination
	  * @param message
	  * @throws Exception
	  */
	public void sendDialogReverseResponse(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception;
	
	
	
	
	 
}
