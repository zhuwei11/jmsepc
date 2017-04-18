package com.zw.jmsepc.protocol.p110;

import com.zw.jmsepc.protocol.EpcPidConstant;
import com.zw.jmsepc.silkie.jms.message.field.ByteDataField;

/**
 * 通用应答
 * @author zw
 * 2014年12月13日 下午4:48:17
 * @version 1.0
 */
public class P110990 extends ByteDataField {

	/**
	 * 错误码
	 */
	private short rtnCode;
	
	/**
	 * 错误说明，可不填
	 */
	private String rtnMsg;
	
	@Override
	protected void readData() throws Exception {
		rtnCode = readShort();
		rtnMsg = readString();
	}

	@Override
	protected void writeData() throws Exception {
		writeShort(rtnCode);
		writeString(rtnMsg);
	}

	@Override
	public int id() {
		return EpcPidConstant.P110990;
	}

	public short getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(short rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}
}
