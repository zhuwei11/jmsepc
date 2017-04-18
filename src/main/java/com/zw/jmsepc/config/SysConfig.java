/**
 * Copyright (c) Since 2014, Power by Pw186.com
 */
package com.zw.jmsepc.config;

import org.springframework.stereotype.Component;

/**
 * 系统配置
 * @author zw
 * 2014年4月14日 下午3:55:09
 * @version 1.0
 */
@Component
public class SysConfig  {
	
	private boolean devMode;
	private int appId = 1;
	private int appType = 1; 
	private String bindIp = "127.0.0.1"; // 绑定IP
	private String version; // 产品版本号
	private String jdbcUrl; // 数据库链接URL
	private String dbUsername; // 数据库用户名
	private String dbPassword; // 数据库密码
	
	public String getBindIp() {
		return bindIp;
	}
	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getDbUsername() {
		return dbUsername;
	}
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getAppType() {
		return appType;
	}
	public void setAppType(int appType) {
		this.appType = appType;
	}
	public boolean isDevMode() {
		return devMode;
	}
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}
	
}
