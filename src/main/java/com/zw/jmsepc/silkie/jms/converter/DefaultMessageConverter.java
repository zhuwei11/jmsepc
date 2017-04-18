package com.zw.jmsepc.silkie.jms.converter;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;

import com.zw.jmsepc.silkie.jms.EventFieldFactory;
import com.zw.jmsepc.silkie.jms.JmsMessageConverter;
import com.zw.jmsepc.silkie.jms.codec.DefaultJmsMessageDecoder;
import com.zw.jmsepc.silkie.jms.codec.DefaultJmsMessageEncoder;
import com.zw.jmsepc.silkie.jms.codec.JmsMessageDecoder;
import com.zw.jmsepc.silkie.jms.codec.JmsMessageEncoder;
import com.zw.jmsepc.silkie.jms.message.JmsMessage;

/**
 * 消息转化器
 * @author zw
 * @since 1.0
 */
public class DefaultMessageConverter implements JmsMessageConverter {
	
	private JmsMessageDecoder jmsMessageDecoder;
	
	private JmsMessageEncoder jmsMessageEncoder;
	
	private EventFieldFactory eventFieldFactory;
	
	private final static Logger LOG = LoggerFactory.getLogger(DefaultMessageConverter.class);

	public DefaultMessageConverter(EventFieldFactory eventFieldFactory) {
		this.eventFieldFactory = eventFieldFactory;
	}

	/**
	 * 发送消息转换，对实体对象进行消息编码
	 */
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		if(jmsMessageEncoder == null) {
			jmsMessageEncoder = new DefaultJmsMessageEncoder();
		}
		if (object instanceof JmsMessage) {
            try{	
            	Message bytesMsg = jmsMessageEncoder.encode(session, (JmsMessage)object); // 调用编码器
            	return bytesMsg;
            }catch (Exception e) {
            	LOG.error("toMessage(Object, Session)", e);  
			}  
        } else {
            throw new JMSException("Object:[" + object + "] is not JmsMessage");  
        }
		return null;
	}

	/**
	 * 收到消息，进行消息解码，转换成一个可用的消息实体对象
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		if(jmsMessageDecoder == null) {
			jmsMessageDecoder = new DefaultJmsMessageDecoder(eventFieldFactory);
		}
		if (message instanceof BytesMessage) {
           try {
        	   return jmsMessageDecoder.decode(message); // 调用解码器
           } catch (Exception e) {  
        	   LOG.error("fromMessage(Message)", e);
           }
           return null;  
       } else {
           throw new JMSException("Msg:[" + message + "] is not BytesMessage");  
       }
	}

	public void setJmsMessageDecoder(JmsMessageDecoder jmsMessageDecoder) {
		this.jmsMessageDecoder = jmsMessageDecoder;
	}

	public void setJmsMessageEncoder(JmsMessageEncoder jmsMessageEncoder) {
		this.jmsMessageEncoder = jmsMessageEncoder;
	}

}
