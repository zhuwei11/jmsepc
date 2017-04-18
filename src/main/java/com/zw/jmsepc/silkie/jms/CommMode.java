/**
 * 
 */
package com.zw.jmsepc.silkie.jms;

/**
 * @author zw
 * 2011-4-20 下午06:35:19
 * @since 1.0
 */
public enum CommMode {
	
	COMM_EVENT_DIALOG_REQUEST(1, "对话模式请求 "),
	COMM_EVENT_DIALOG_RESPONSE(2, "对话模式响应"),
	COMM_EVENT_PRIVATE(3, "私有模式请求"),
	
	COMM_MODE_DIALOG(0, "对话模式 "),
	COMM_MODE_PRIVATE(1, "私有模式，通知模式"),
	COMM_MODE_DIALOG_REVERSE(2, "反对话模式"),
	COMM_MODE_PRIVATE_REVERSE(3, "反私有模式，反通知模式");
	
	private int value;

	private String name;
	
	CommMode(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
