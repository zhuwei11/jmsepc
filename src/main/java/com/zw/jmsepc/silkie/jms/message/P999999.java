package com.zw.jmsepc.silkie.jms.message;

import com.zw.jmsepc.silkie.jms.PidConstant;
import com.zw.jmsepc.silkie.jms.message.field.ByteDataField;

/**
 * 公共域信息，所有的协议包都必须携带此域
 * @author zw
 * 2011-4-20 下午05:51:21
 * @since 1.0
 */
public class P999999 extends ByteDataField {
	
	/**
	 * 应用类型，使用编码
	 */
	private int appType;
	/**
	 * 应用ID
	 */
	private int appId;
	
	@Override
	public int id() {
		return PidConstant.P999999;
	}
	
	@Override
	protected void readData() throws Exception {
		appType = readByte();
        appId = readShort();
	}

	@Override
	protected void writeData() throws Exception {
		writeByte((byte)appType);
		writeShort((short)appId);
	}
	
	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public int getAppId() {
		return appId;
	}
	
	public void setAppId(int appId) {
		this.appId = appId;
	}

}
