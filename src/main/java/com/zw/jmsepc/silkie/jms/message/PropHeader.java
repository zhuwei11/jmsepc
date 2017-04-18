/**
 * Copyright (c) 2014 by pw186.com.
 * All right reserved.
 */
package com.zw.jmsepc.silkie.jms.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * activemq可指定的数据段
 *
 */
public class PropHeader {
	/**消息体类型 */
	public static final String BODY_KEY = "PW.MSG.JMS";//缺省就采用这种
	/**ZLIB压缩模式 */
	public static final String ZLIB_KEY = "PW.MSG.ZLIB";//待遇这个标记表示body采用zlib压缩
	
	private Map<String, Object> map;
	
	public PropHeader() {
		map = new HashMap<String, Object>();
	}
	
	public boolean isZlib() {
		return getBooleanProperty(ZLIB_KEY);
	}
	
	public void enableZlib() {
		setBooleanProperty(ZLIB_KEY, true);
	}
	
	public boolean propertyExists(String key) {
		return map.containsKey(key);
	}
	
	public void clearProperties() {
		map.clear();
	}
	
	public void setBooleanProperty(String key, boolean value) {
		map.put(key, value);
	}
	public boolean getBooleanProperty(String key) {
		Object obj = map.get(key);
		return obj == null?false:(Boolean)obj;
	}

	public void setByteProperty(String key, byte value) {
		map.put(key, value);
	}
	public byte getByteProperty(String key) {
		Object obj = map.get(key);
		return obj == null?0x00:(Byte)obj;
	}
	
	public void setDoubleProperty(String key, double value) {
		map.put(key, value);
	}
	public double getDoubleProperty(String key) {
		Object obj = map.get(key);
		return obj == null?0.0d:(Double)obj;
	}

	public float getFloatProperty(String key) {
		Object obj = map.get(key);
		return obj == null?0.0f:(Float)obj;
	}
	public void setFloatProperty(String key, float value) {
		map.put(key, value);
	}
	
	public void setIntProperty(String key, int value) {
		map.put(key, value);
	}
	public int getIntProperty(String key) {
		Object obj = map.get(key);
		return obj == null?0:(Integer)obj;
	}
	
	public String getStringProperty(String key) {
		Object obj = map.get(key);
		return obj == null?null:(String)obj;
	}
	public void setStringProperty(String key, String value) {
		map.put(key, value);
	}
	public void setLongProperty(String key, long value) {
		map.put(key, value);
	}
	public long getLongProperty(String key) {
		Object obj = map.get(key);
		return obj == null?0L:(Long)obj;
	}

	public void setShortProperty(String key, short value) {
		map.put(key, value);
	}
	public short getShortProperty(String key) {
		Object obj = map.get(key);
		return obj == null?0:(Short)obj;
	}
	
	public void setObjectProperty(String key, Object value) {
		if(value instanceof Boolean) {
			setBooleanProperty(key, (Boolean) value);
		} else if(value instanceof Byte) {
			setByteProperty(key, (Byte) value);
		} else if(value instanceof Double) {
			setDoubleProperty(key, (Double) value);
		} else if(value instanceof Float) {
			setFloatProperty(key, (Float) value);
		} else if(value instanceof Integer) {
			setIntProperty(key, (Integer) value);
		} else if(value instanceof String) {
			setStringProperty(key, (String) value);
		} else if(value instanceof Long) {
			setLongProperty(key, (Long) value);
		} else if(value instanceof Short) {
			setShortProperty(key, (Short) value);
		} else {
			throw new IllegalArgumentException("error value type");
		}
	}
	public Object getObjectProperty(String key) {
		return map.get(key);
	}
	
	public Set<String> getPropertyNames() {
		return map.keySet();
	}
	
	@Override	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
