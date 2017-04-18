/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message.field;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zw.jmsepc.silkie.jms.nio.IoBuffer;


/**
 * 数据域的基本实现，提供一些共用方法
 *
 */
public abstract class ByteDataField extends BaseDataField implements DataField {

	protected short dataLen = -1;//数据部分长度，不包括6个头字节
	
	public ByteDataField() {

	}
	
	@Override
	public void decode(IoBuffer buff) throws Exception {
		this.buff = buff;
		buff.getInt();// pid;
		dataLen = buff.getShort();
		
		readData();
		
		this.buff = null;
	}

	/**
	 * 请注意个属性的排列顺序
	 */
	protected abstract void readData() throws Exception;
	
	@Override
	public IoBuffer encode() throws Exception {
		if (this.buff != null)
			return buff;
			
		IoBuffer buff = IoBuffer.allocate(2048);
		buff.setAutoExpand(true);
		this.buff = buff;
		
		buff.putInt(id());
		buff.putShort((short)dataLen);
		
		writeData();
		
		buff.flip();
		dataLen = (short)(buff.remaining() - FIELD_HEADER_LEN);
		buff.putShort(4, (short)dataLen);// set length
		
		return this.buff;
	}
	
	
	/**
	 * 不需要做 flip();
	 * @param buff 注意各属性的排列顺序
	 */
	protected abstract void writeData() throws Exception;
	
	@Override
	public BaseDataField clone() {
		BaseDataField bsff = null;
		try {
			bsff = (BaseDataField)super.clone();
			bsff.buff = null;
		} catch (CloneNotSupportedException e) {
		}
		return bsff;
	}

	@Override
	public int dataLength() {
		return dataLen;
	}
	
	/**
	 * 获得当前使用的临时IoBuffer，for debug only
	 * @return 提供debug用的方法
	 */
	protected IoBuffer curBuff() {
		return this.buff;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	
}
