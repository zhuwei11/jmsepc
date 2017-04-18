/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.zw.jmsepc.silkie.jms.message.field.DataField;

/**
 * 信息正文
 *
 */
public class JmsBody implements Cloneable {// extends Message
	
	/**报头长度 */
	public static final int HEADER_LEN =  16;
	
	/** 信息正文最大长度 */
	public static final int MAX_BODY_LEN =  524288;// 512k
	
	public static final byte VERSION = 0x10;
	
	/** 是否压缩该报文，由通讯层自由决定 */
	public static final int AUTO_ZIP = 0;
	/** 强迫通讯层压缩该报文 */
	public static final int FORCE_ZIP = 1;
	/** 禁止通讯层压缩该报文 */
	public static final int FORCE_NOT_ZIP = 2;
	
	/** 4位的tid保留 */
	public static final int MAX_CTRL_TID = 9999;
	/** 报文链中只有单个正文 */
	public static final byte CHAIN_SINGLE = 'S';
	/** 报文链的第一个正文 */
	public static final byte CHAIN_FIRST = 'F';
	/** 报文链的中间正文 */
	public static final byte CHAIN_CONTINUE = 'C';
	/** 报文链的最后一个正文 */
	public static final byte CHAIN_LAST = 'L';
	
	byte version; //版本号   0x10
	
	int tid; //交易号
	byte chain; // 报文链; S/F/C/L
	int seqSeries; //序列类别号 unsigned short
	long seqNo; // 序列号 unsigned int
	int fieldCount; //消息正文个数 unsigned short
	int contentLen; // 业务 消息正文长度 unsigned short
	
	DataField[] fields;//数据域
	
	// 0：不指定； 1：强迫通讯层压缩； 2：强迫通讯层不压缩
	int zipFlag;
	
	public JmsBody() {
		version = VERSION;
		chain = CHAIN_SINGLE;
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public byte getChain() {
		return chain;
	}

	public void setChain(byte chain) {
		if (chain != CHAIN_SINGLE && chain != CHAIN_FIRST
				&& chain != CHAIN_CONTINUE && chain != CHAIN_LAST)
			throw new IllegalArgumentException("Invalid ftdc chain: " + chain);
		this.chain = chain;
	}
	
	public int getSeqSeries() {
		return seqSeries;
	}

	public void setSeqSeries(int seqSeries) {
		this.seqSeries = seqSeries;
	}

	public long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}
	
	public void setZipFlag(int zip) {
		zipFlag = zip;
	}
	
	public int getZipFlag() {
		return zipFlag;
	}
	

	public int getFieldCount() {
		return fieldCount;
	}

	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	public int getContentLen() {
		return contentLen;
	}

	public void setContentLen(int contentLen) {
		if (contentLen > MAX_BODY_LEN)
			throw new IllegalArgumentException("Invalid DataField contentLen: " + contentLen);
		this.contentLen = contentLen;
	}

	public DataField[] getFields() {
		return fields;
	}

	public void setFields(DataField[] fields) {
		this.fields = fields;
		this.fieldCount = (fields == null)? 0 : (short)fields.length;
	}

	public void addField(DataField f) {
		if (f == null) return;
		
		if (fields == null) {
			fields = new DataField[] { f };
		} else {
			DataField[] tmp = new DataField[fields.length + 1];
			System.arraycopy(fields, 0, tmp, 0, fields.length);
			tmp[fields.length] = f;
			fields = tmp;
			tmp = null;
		}
		
		this.fieldCount = (short)fields.length;
	}

	@Override	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public JmsBody clone() {
		try {
			return (JmsBody)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
