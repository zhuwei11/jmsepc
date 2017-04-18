/**
 * 
 */
package com.zw.jmsepc.silkie.jms.handler;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.zw.jmsepc.silkie.epc.Epc;
import com.zw.jmsepc.silkie.epc.EpcEvent;
import com.zw.jmsepc.silkie.jms.AppInfo;
import com.zw.jmsepc.silkie.jms.CommMode;
import com.zw.jmsepc.silkie.jms.DefaultJmsTemplate;
import com.zw.jmsepc.silkie.jms.DialogModeLock;
import com.zw.jmsepc.silkie.jms.EventFieldFactory;
import com.zw.jmsepc.silkie.jms.PidConstant;
import com.zw.jmsepc.silkie.jms.epc.JmsEventParam;
import com.zw.jmsepc.silkie.jms.message.JmsBody;
import com.zw.jmsepc.silkie.jms.message.JmsMessage;
import com.zw.jmsepc.silkie.jms.message.P999999;

/**
 * 消息处理器默认实现
 * @author zw
 * 2011-4-15 下午03:53:10
 * @since 1.0
 */
public class DefaultJmsMessageHandler extends BaseJmsMessageHandler {
	
	private final static Logger LOG = LoggerFactory.getLogger(DefaultJmsMessageHandler.class); 

	private DefaultJmsTemplate jmsTemplate;
	private Epc epc;
	private EventFieldFactory eventFieldFactory;
	private DialogModeLock dialogModeLock;
	
	private JmsEventParam dialogResponseParam;
	
	public DefaultJmsMessageHandler() {
		
	}
	
	@Override
	public void recvMessage(JmsMessage message) throws Exception {
		if(message.getBody() instanceof JmsBody){
			JmsBody body = (JmsBody)message.getBody();
			
			JmsEventParam param = new JmsEventParam();
			param.setFields(body.getFields());
			P999999 p999999 = (P999999)param.queryFirst(PidConstant.P999999);
			
			param.setTid(body.getTid());
			param.setSeqSeries(body.getSeqSeries());
			param.setSeqNo(body.getSeqNo());
			param.setServerType(p999999.getAppType());
			param.setServerId(p999999.getAppId());
			param.setMessageID(message.getJmsHeader().getJMSMessageID());
			param.setCorrelationID(message.getJmsHeader().getJMSCorrelationID());
			
			// 生成事件
			if(body.getSeqSeries() == CommMode.COMM_MODE_DIALOG.getValue()) { // 对话模式
				if(CommMode.COMM_EVENT_DIALOG_RESPONSE.getValue() == (body.getTid()%10)) { // 只有响应才需要解锁
					dialogResponseParam = param;
					dialogModeLock.singal();
					return;
				}
				
			} else if(body.getSeqSeries() == CommMode.COMM_MODE_PRIVATE.getValue()) { // 私有模式
				
			}
			
			EpcEvent event = eventFieldFactory.createEvent(body.getTid());
			if(event == null) {
				LOG.warn("Unknow Event, Tid:" + body.getTid());
			} else {
				event.setEventParam(param);
				epc.pushEvent(event, event.getCollision());
			}
		}
	}
	
	@Override
	public void sendPrivateMsg(Destination destination, JmsMessage message, AppInfo srcInfo, AppInfo destApp) throws Exception {
		message.getPropHeader().setIntProperty(AppInfo.APP_ID, destApp.getId());
		message.getPropHeader().setIntProperty(AppInfo.APP_TYPE, destApp.getType());
		message.getBody().setSeqSeries(CommMode.COMM_MODE_PRIVATE.getValue());
		sentMessage(destination, message, srcInfo);
	}
	
	@Override
	public void sendPrivateReverseMsg(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception {
		message.getBody().setSeqSeries(CommMode.COMM_MODE_PRIVATE_REVERSE.getValue());
		sentMessage(destination, message, appInfo);
	}

	@Override
	public void sendDialogRequest(Destination destination, JmsMessage message,  AppInfo srcInfo, AppInfo destApp) throws Exception {
		// 发送消息，要在属性头设置发送方的应用标识
		message.getPropHeader().setIntProperty(AppInfo.APP_ID, destApp.getId());
		message.getPropHeader().setIntProperty(AppInfo.APP_TYPE, destApp.getType());
		message.getBody().setSeqSeries(CommMode.COMM_MODE_DIALOG.getValue());
		
		sentMessage(destination, message, srcInfo);
	}

	@Override
	public void sendDialogResponse(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception {
		if(message == null || message.getPropHeader() == null 
				|| message.getPropHeader().getIntProperty(AppInfo.APP_ID) <= 0 
				|| message.getPropHeader().getIntProperty(AppInfo.APP_TYPE) <= 0 ) { // 应答消息必须设置接收方的appinfo
			throw new IllegalArgumentException("APP_ID or APP_TYPE is null !!!!!!");
		}
		message.getBody().setSeqSeries(CommMode.COMM_MODE_DIALOG.getValue());
		sentMessage(destination, message, appInfo);
	}

	@Override
	public void sendDialogReverseRequest(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception {
		message.getPropHeader().setIntProperty(AppInfo.APP_ID, appInfo.getId());
		message.getPropHeader().setIntProperty(AppInfo.APP_TYPE, appInfo.getType());
		message.getBody().setSeqSeries(CommMode.COMM_MODE_DIALOG_REVERSE.getValue());
		
		sentMessage(destination, message, appInfo);
	}

	@Override
	public void sendDialogReverseResponse(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception {
		if(message == null || message.getPropHeader() == null 
				|| message.getPropHeader().getIntProperty(AppInfo.APP_ID) <= 0 
				|| message.getPropHeader().getIntProperty(AppInfo.APP_TYPE) <= 0 ) { // 应答消息必须设置接收方的appinfo
			throw new IllegalArgumentException("APP_ID or APP_TYPE is null !!!!!!");
		}
		message.getBody().setSeqSeries(CommMode.COMM_MODE_DIALOG_REVERSE.getValue());
		
		sentMessage(destination, message, appInfo);
	}


	protected void sentMessage(Destination destination, JmsMessage message, AppInfo appInfo) throws Exception {
		if(jmsTemplate == null) {
			throw new IllegalArgumentException("jmsTemplate is null !!!!!!");
		}
		if(appInfo == null) {
			throw new IllegalArgumentException("appInfo is null !!!!!!");
		}
		P999999 p999999 = new P999999();
		p999999.setAppId(appInfo.getId());
		p999999.setAppType(appInfo.getType());
		message.getBody().addField(p999999);
		jmsTemplate.convertAndSend(destination, message);
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = (DefaultJmsTemplate)jmsTemplate;
		eventFieldFactory = this.jmsTemplate.getEventFieldFactory();
	}

	public void setDialogModeLock(DialogModeLock dialogModeLock) {
		this.dialogModeLock = dialogModeLock;
	}

	public JmsEventParam getDialogResponseParam() {
		return dialogResponseParam;
	}
	
	public void setDialogResponseParam(JmsEventParam param) {
		this.dialogResponseParam = param;
	}

	public void setEpc(Epc epc) {
		this.epc = epc;
	}

}
