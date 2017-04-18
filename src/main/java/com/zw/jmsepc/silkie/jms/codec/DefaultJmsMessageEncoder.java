/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.codec;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.zw.jmsepc.silkie.jms.CommMode;
import com.zw.jmsepc.silkie.jms.message.JmsBody;
import com.zw.jmsepc.silkie.jms.message.JmsHeader;
import com.zw.jmsepc.silkie.jms.message.JmsMessage;
import com.zw.jmsepc.silkie.jms.message.PropHeader;
import com.zw.jmsepc.silkie.jms.message.field.DataField;
import com.zw.jmsepc.silkie.jms.nio.IoBuffer;
import com.zw.jmsepc.silkie.util.ZipUtils;

/**
 * 消息编码器缺省实现
 * @author zw
 * @since 1.0
 */
public class DefaultJmsMessageEncoder implements JmsMessageEncoder {

	// 大于此阈值 自动压缩报文
	final static int AUTO_ZIP_THRESHOLD = 10240;//10k
	
	private AtomicLong dialogSeq = new AtomicLong(1);
	private AtomicLong privateSeq = new AtomicLong(1);
	
	
	public DefaultJmsMessageEncoder() {
		
	}
	
	@Override
	public Message encode(Session session, JmsMessage jmsMsg) throws Exception {
		BytesMessage bytesMsg = (BytesMessage) session.createBytesMessage();
		if (jmsMsg == null) {
			return bytesMsg;
		}
		JmsBody jmsBody = jmsMsg.getBody();
		if(jmsBody == null) {
			return bytesMsg;
		}
	
		IoBuffer buff = IoBuffer.allocate(2048);
		buff.setAutoExpand(true);
		
		if(jmsBody.getSeqSeries() == CommMode.COMM_MODE_DIALOG.getValue()) {
			jmsBody.setSeqNo(dialogSeq.getAndIncrement());
		} else {
			jmsBody.setSeqNo(privateSeq.getAndIncrement());
		}
		
		 // 包头编码
		encodeHeader(jmsBody, buff);
		
		// field逐个编码
		if (jmsBody.getFields() == null) {
			jmsBody.setFieldCount((short)0); // field为空
		} else {// 组装 fielsd
			for (int i = 0; i < jmsBody.getFields().length; i++) {
				DataField f = jmsBody.getFields()[i];
				IoBuffer buf = f.encode();
				buff.put(buf.rewind());
			}
			jmsBody.setFieldCount((short)jmsBody.getFields().length);
		}
		
		 // 定位到field数量位置，重新赋值
		buff.putShort(12, (short)jmsBody.getFieldCount());
		 // 定位到内容长度位置，重新赋值
		jmsBody.setContentLen(buff.position() - JmsBody.HEADER_LEN);
		buff.putShort(14, (short)jmsBody.getContentLen());
		
		buff.flip();
		
		if (buff.remaining() > JmsBody.MAX_BODY_LEN)
			throw new IllegalArgumentException("Invalid bodyLen=" +  buff.remaining() +"; MAX_BODY_LEN="+JmsBody.MAX_BODY_LEN);

		// 处理压缩包
		if (buff.remaining() > 0 && jmsBody.getZipFlag() != JmsBody.FORCE_NOT_ZIP) {
			if (jmsBody.getZipFlag() == JmsBody.FORCE_ZIP // 强迫压缩
					|| (jmsBody.getZipFlag() == JmsBody.AUTO_ZIP // 自动时，大于阈值
							&& buff.remaining() > AUTO_ZIP_THRESHOLD)) {
				
				jmsMsg.getPropHeader().enableZlib();
				buff = compress(buff);
			}
		}
		
		byte[] out = new byte[buff.remaining()];
		buff.get(out);
		bytesMsg.writeBytes(out);
		
		// 处理JMS本身属性与自定义属性
		encodeJmsHeader(bytesMsg, jmsMsg.getJmsHeader());
		encodePropHeader(bytesMsg, jmsMsg.getPropHeader());
		
		return bytesMsg;
	}

	/**
	 * 设置包体头部信息
	 * @param buff 
	 * @param jmsBody
	 */
	private void encodeHeader(JmsBody jmsBody, IoBuffer buff) {
		buff.put(jmsBody.getVersion());
		buff.putInt(jmsBody.getTid());
		buff.put(jmsBody.getChain());
		buff.putShort((short)jmsBody.getSeqSeries());
		buff.putInt((int)jmsBody.getSeqNo());
		buff.putShort((short)jmsBody.getFieldCount());
		buff.putShort((short)jmsBody.getContentLen());
	}

	private void encodePropHeader(BytesMessage tm, PropHeader propHeader) throws JMSException {
		if (propHeader == null)
			return;

		for(String key : propHeader.getPropertyNames()) {
			tm.setObjectProperty(key, propHeader.getObjectProperty(key));
		}
	}

	private void encodeJmsHeader(BytesMessage tm, JmsHeader jmsHeader) throws JMSException {
		if (jmsHeader == null)
			return;
		if (jmsHeader.getJMSCorrelationID() != null) tm.setJMSCorrelationID(jmsHeader.getJMSCorrelationID());
		tm.setJMSDeliveryMode(jmsHeader.getJMSDeliveryMode());
		if (jmsHeader.getJMSDestination() != null) tm.setJMSDestination(jmsHeader.getJMSDestination());
		tm.setJMSExpiration(jmsHeader.getJMSExpiration());
		if (jmsHeader.getJMSMessageID() != null) tm.setJMSMessageID(jmsHeader.getJMSMessageID());
		tm.setJMSPriority(jmsHeader.getJMSPriority());
		tm.setJMSRedelivered(jmsHeader.getJMSRedelivered());
		if (jmsHeader.getJMSReplyTo() != null) tm.setJMSReplyTo(jmsHeader.getJMSReplyTo());
		tm.setJMSTimestamp(jmsHeader.getJMSTimestamp());
		if (jmsHeader.getJMSType() != null) tm.setJMSType(jmsHeader.getJMSType());
	}
	
	/**
	 * 压缩buffer
	 * @param buff
	 * @return
	 * @throws IOException
	 */
	private IoBuffer compress(IoBuffer buff) throws IOException {
		byte[] inBytes = new byte[buff.remaining()];
		buff.get(inBytes).flip();
		byte[] outBytes = ZipUtils.gZip(inBytes);
		buff = IoBuffer.wrap(outBytes);
		return buff;
	}
	
}
