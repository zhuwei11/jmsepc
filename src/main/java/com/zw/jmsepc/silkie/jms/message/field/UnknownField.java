/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message.field;


/**
 * 未知的Field处理，用于老版本对后续版本新增Field的支持
 * 
 */
public class UnknownField extends ByteDataField {
	
	private int id;
	private byte[] body;
	
	public UnknownField(int id) {
		super();
		this.id = id;
	}

	@Override
	protected void readData() {
		body = new byte[dataLen];
		buff.get(body);
	}

	@Override
	protected void writeData() {
		writeByteArray(body);
	}

	@Override
	public int id() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

}
