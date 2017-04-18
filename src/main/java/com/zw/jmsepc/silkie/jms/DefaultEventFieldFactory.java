/**
 * 
 */
package com.zw.jmsepc.silkie.jms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zw.jmsepc.silkie.epc.EpcEvent;
import com.zw.jmsepc.silkie.jms.message.field.DataField;

/**
 * 协议解码编码、事件创建工厂
 * @author zw
 * 2011-4-15 下午02:27:58
 * @since 1.0
 */
public class DefaultEventFieldFactory implements EventFieldFactory {

	private String fieldPackageBase = "com.zw.jmsepc.silkie.jms.demo.fields";
	
	private String eventPackageBase = "com.zw.jmsepc.silkie.jms.demo.event";
	
	public final static String FIELD_HEAD = "P";
	
	public final static String EVENT_HEAD = "T";
	

	public DefaultEventFieldFactory() {
		
	}
	
	/**
	 * 有的进行加速
	 */
	private final Map<String, Class<?>> clazz = new ConcurrentHashMap<String, Class<?>>();
	/**
	 * 没有的进行加速
	 */
	private final Map<String, Boolean> clazz_null = new ConcurrentHashMap<String, Boolean>();

	/**
	 * 获取package
	 * @param head
	 * @param commandCode
	 * @return
	 */
	private String getBasePackage(String head, int commandCode) {
		StringBuffer sb = new StringBuffer();
		if(head.equalsIgnoreCase(FIELD_HEAD)) {
			sb.append(getFieldPackageBase());
		} else {
			sb.append(getEventPackageBase());
		}
		sb.append(".");
		sb.append(head.toLowerCase() + (commandCode / 1000)); // 说明
		sb.append(".");
		return sb.toString();
	}

	/**
	 * 按规则 组装全局 类名
	 * @param head
	 * @param commandCode
	 * @return
	 */
	private String generateSubPackage(String head, int commandCode) {
		return head + commandCode;
	}

	@Override
	public DataField createField(int fid) throws Exception {
		Class<?> c = getClazz(FIELD_HEAD, fid);
		if (c == null)
			return null;
		else
			return (DataField) c.newInstance();
	}

	@Override
	public EpcEvent createEvent(int tid) throws Exception {
		Class<?> c = getClazz(EVENT_HEAD, tid);
		if (c == null)
			return null;
		else
			return (EpcEvent) c.newInstance();
	}
	
	protected Class<?> getClazz(String head, int id) {
		String key = head+id;
		Class<?> c = clazz.get(key);
		if (c == null) {
			if (clazz_null.get(key) != null)
				return null;
			try {
				c = Class.forName(getBasePackage(head, id) + generateSubPackage(head, id));
			} catch (ClassNotFoundException e) {
				clazz_null.put(key, true);
				return null;
			}
			clazz.put(key, c);
		}
		return c;
	}

	public void setFieldPackageBase(String fieldPackageBase) {
		this.fieldPackageBase = fieldPackageBase;
	}

	public String getFieldPackageBase() {
		return fieldPackageBase;
	}

	public void setEventPackageBase(String eventPackageBase) {
		this.eventPackageBase = eventPackageBase;
	}

	public String getEventPackageBase() {
		return eventPackageBase;
	}
	
}
