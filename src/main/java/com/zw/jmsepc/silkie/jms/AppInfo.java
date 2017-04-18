package com.zw.jmsepc.silkie.jms;

import java.net.InetAddress;


/**
 * 监听jms的应用信息
 * @author zw
 * 2010-12-19 下午03:58:25
 * @since 1.0
 */
public class AppInfo {
	
	public final static String APP_ID = "app_id";
	public final static String APP_TYPE = "app_type";
	/**
	 * 服务器Id
	 */
	private int id;
	/**
	 * 服务器名称
	 */
	private String name;
	/**
	 * 服务器类型
	 */
	private int type;
	/**
	 * 备注
	 */
	private String memo;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 获取服务器IP
	 * @return
	 */
	public final String getServerIp() {
		String ip = "";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress().toString();// 获得本机IP
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
