/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.epc;

import com.zw.jmsepc.silkie.epc.impl.EpcEventParam;


/**
 * JMS事件参数
 * @author zw
 * 2010-12-20 下午07:47:36
 * @since 1.0
 */
public class JmsEventParam extends EpcEventParam {
	
	private int appType;// 来源服务器类型
	private int appId;	// 来源服务器id
	
	private String correlationID;
	private String messageID;
	private String onlineUid;
	
	public int getServerType() {
		return appType;
	}

	public void setServerType(int serverType) {
		this.appType = serverType;
	}

	public int getServerId() {
		return appId;
	}

	public void setServerId(int serverId) {
		this.appId = serverId;
	}

	public String getCorrelationID() {
		return correlationID;
	}

	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getOnlineUid() {
		return onlineUid;
	}

	public void setOnlineUid(String onlineUid) {
		this.onlineUid = onlineUid;
	}
}
