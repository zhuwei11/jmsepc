package com.zw.jmsepc.silkie.jms.message;

import javax.jms.Message;

/**实现这个接口可以拿到javax.jms.Message*/
public interface MessageAware {
	public void setMessage(Message message);
	
	public Message getMessage();
}
