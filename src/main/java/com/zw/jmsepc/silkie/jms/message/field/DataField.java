package com.zw.jmsepc.silkie.jms.message.field;

import com.zw.jmsepc.silkie.jms.nio.IoBuffer;



public interface DataField extends Cloneable{

	/**报头长度 */
	public static final short FIELD_HEADER_LEN =  6;

	/**
	 * 唯一标识id
	 * 数据域标识
	 */
	public int id();
	
	/**
	 * 域长度（对应字节数），不包括6个头字节
	 * 调用该方法前，必须先调用encode()或者decode()。
	 * @return
	 */
	int dataLength();
	
	/**
	 * field解码
	 * @param ioBuffer
	 * @throws Exception
	 */
	public void decode(IoBuffer ioBuffer) throws Exception;
	
	/**
	 * field编码，自身put id, length
	 * @return
	 * @throws Exception
	 */
	public IoBuffer encode() throws Exception;
	
}
