/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.codec;

import java.io.IOException;
import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;

import com.zw.jmsepc.silkie.jms.DefaultEventFieldFactory;
import com.zw.jmsepc.silkie.jms.EventFieldFactory;
import com.zw.jmsepc.silkie.jms.PidConstant;
import com.zw.jmsepc.silkie.jms.message.JmsBody;
import com.zw.jmsepc.silkie.jms.message.JmsHeader;
import com.zw.jmsepc.silkie.jms.message.JmsMessage;
import com.zw.jmsepc.silkie.jms.message.P999999;
import com.zw.jmsepc.silkie.jms.message.PropHeader;
import com.zw.jmsepc.silkie.jms.message.field.DataField;
import com.zw.jmsepc.silkie.jms.message.field.UnknownField;
import com.zw.jmsepc.silkie.jms.message.impl.DefaultJmsMessage;
import com.zw.jmsepc.silkie.jms.nio.IoBuffer;
import com.zw.jmsepc.silkie.util.ZipUtils;

/**
 * 消息解码器缺省实现
 * @author zw
 * @since 1.0
 */
public class DefaultJmsMessageDecoder implements JmsMessageDecoder {

	private EventFieldFactory fieldFactory;
	
	public DefaultJmsMessageDecoder() {
		this.fieldFactory = new DefaultEventFieldFactory();
	}
	
	public DefaultJmsMessageDecoder(EventFieldFactory fieldFactory) {
		this.fieldFactory = fieldFactory;
	}
	
	@Override
	public JmsMessage decode(Message message) throws Exception {
		BytesMessage byteMsg = (BytesMessage)message;

		DefaultJmsMessage jmsMessage = new DefaultJmsMessage();
		decodeJmsHeader(byteMsg, jmsMessage.getJmsHeader());
		decodePropHeader(byteMsg, jmsMessage.getPropHeader());
		
		byte[] data = new byte[(int)byteMsg.getBodyLength()];
		byteMsg.readBytes(data);
		
		IoBuffer in = IoBuffer.wrap(data);
		
		// 解压数据
		if (jmsMessage.getPropHeader().isZlib()) {
			in = decompress(in);			
		}
		JmsBody jmsBody = new JmsBody();
		parseHeader(in, jmsBody);
		
		// 域个数和域大小 不匹配
		if (jmsBody.getFieldCount() == 0 && jmsBody.getContentLen() != 0) {
			throw new IllegalArgumentException("Invalid Message header:filedCount="
					+jmsBody.getFieldCount()+ ", ContentLen=" + jmsBody.getContentLen());
		}
		
		// 现在采用的做法是直接解开 field， 如果出于性能考虑，field可以先不解开
		DataField[] fields = new DataField[jmsBody.getFieldCount()];
		for (int i = 0; i < fields.length; i++) {
			int pid = in.getInt(in.position());
			DataField field;
			if(pid == PidConstant.P999999) { // 处理公共包
				field = new P999999();
			} else {
				field = fieldFactory.createField(pid);
			}
			if (field == null)// factory 返回null，表示不认识的pid
				field = new UnknownField(pid);
			
			try {
				field.decode(in);
				
			} catch (Exception ex) {// 解某个filed异常
				throw new IllegalArgumentException("Decode field[pid=" 
						+ field.id()+ ",len=" + field.dataLength() + "] Error. Cause by: " + ex.getClass() , ex);
			}
			fields[i] = field;
		}
		jmsBody.setFields(fields);
		
		jmsMessage.setMessage(byteMsg);
		jmsMessage.setBody(jmsBody);
		return jmsMessage;
	}

	/**
	 * 解析包体头部
	 * @param jmsBody 
	 * @param in
	 */
	private void parseHeader(IoBuffer in, JmsBody jmsBody) {
		jmsBody.setVersion(in.get());
		jmsBody.setTid(in.getInt());
		jmsBody.setChain(in.get());
		jmsBody.setSeqSeries(in.getShort());
		jmsBody.setSeqNo(in.getInt());
		jmsBody.setFieldCount(in.getShort());
		jmsBody.setContentLen(in.getShort());
	}

	@SuppressWarnings("unchecked")
	private void decodePropHeader(BytesMessage byteMsg, PropHeader propHeader) throws JMSException {
		Enumeration<String> en = byteMsg.getPropertyNames();
		while(en.hasMoreElements()) {
			String key = en.nextElement();
			Object value = byteMsg.getObjectProperty(key);
			propHeader.setObjectProperty(key, value);
		}
	}

	private void decodeJmsHeader(BytesMessage byteMsg, JmsHeader header) throws JMSException {
		header.setJMSMessageID( byteMsg.getJMSMessageID() );
		header.setJMSCorrelationID( byteMsg.getJMSCorrelationID() );
		header.setJMSReplyTo( byteMsg.getJMSReplyTo() );
		header.setJMSExpiration( byteMsg.getJMSExpiration() );
		header.setJMSPriority( byteMsg.getJMSPriority() );
		header.setJMSType( byteMsg.getJMSType() );
		header.setJMSRedelivered( byteMsg.getJMSRedelivered() );
		header.setJMSDeliveryMode( byteMsg.getJMSDeliveryMode() );
		header.setJMSDestination( byteMsg.getJMSDestination() );
		header.setJMSTimestamp( byteMsg.getJMSTimestamp() );
	}

	private IoBuffer decompress(IoBuffer buff) throws IOException {
		byte[] inBytes = new byte[buff.remaining()];
		buff.get(inBytes).flip();
		byte[] outBytes = ZipUtils.unGZip(inBytes);
		buff = IoBuffer.wrap(outBytes);
		return buff;
	}

}
