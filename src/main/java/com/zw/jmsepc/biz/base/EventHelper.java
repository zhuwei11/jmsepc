/**
 * Copyright (c) Since 2015, Power by Pw186.com
 */
package com.zw.jmsepc.biz.base;


/**
 * @author zw
 * 2015年2月5日 下午6:09:11
 * @version 1.0
 */
public class EventHelper {
	
//	/**
//	 * 生成保障金帐户收入事件
//	 * @param epc
//	 * @param eventFieldFactory
//	 * @param user
//	 * @param serviceFee
//	 * @throws Exception
//	 */
//	public static void pushT324023(UserInfo user, double amount, int traderType, int appType, int appId) throws Exception {
//		Epc epc = SpringBeanManager.getBean("epc", Epc.class);
//		EventFieldFactory eventFieldFactory = SpringBeanManager.getBean("eventFieldFactory", EventFieldFactory.class);
//		EpcEvent event = eventFieldFactory.createEvent(P2pTidConstant.T324023);
//		JmsEventParam param = new JmsEventParam();
//		P124020 p124020 = new P124020();
//		p124020.setTenantId(user.getInt("tenant_org_id"));
//		p124020.setUserId(user.getInt("id"));
//		p124020.setAmount(amount);
//		p124020.setTraderType(traderType);
//		param.addField(p124020);
//		
//		param.setTid(P2pTidConstant.T324023);
//		param.setServerType(appType);
//		param.setServerId(appId);
//		event.setEventParam(param);
//		
//		epc.pushEvent(event, event.getCollision());
//	}
//	
//	
//	/**
//	 * 生成保证金支出事件
//	 * @param epc
//	 * @param eventFieldFactory
//	 * @param user
//	 * @param amount
//	 * @throws Exception
//	 */
//	public static void pushT324033(UserInfo user, double amount, int traderType, int appType, int appId) throws Exception {
//		Epc epc = SpringBeanManager.getBean("epc", Epc.class);
//		EventFieldFactory eventFieldFactory = SpringBeanManager.getBean("eventFieldFactory", EventFieldFactory.class);
//		
//		EpcEvent event = eventFieldFactory.createEvent(P2pTidConstant.T324033);
//		JmsEventParam param = new JmsEventParam();
//		P124030 p124030 = new P124030();
//		p124030.setTenantId(user.getInt("tenant_org_id"));
//		p124030.setUserId(user.getInt("id"));
//		p124030.setAmount(amount);
//		p124030.setTraderType(traderType);
//		param.addField(p124030);
//		
//		param.setTid(P2pTidConstant.T324033);
//		param.setServerType(appType);
//		param.setServerId(appId);
//		event.setEventParam(param);
//		
//		epc.pushEvent(event, event.getCollision());
//	}
//	
//	/**
//	 * 平台垫付后继续还款事件
//	 * @param receipt
//	 * @param appType
//	 * @param appId
//	 * @throws Exception
//	 */
//	public static void pushT321113(BorrowBillReceipt receipt, int appType, int appId) throws Exception {
//		Epc epc = SpringBeanManager.getBean("epc", Epc.class);
//		EventFieldFactory eventFieldFactory = SpringBeanManager.getBean("eventFieldFactory", EventFieldFactory.class);
//		
//		EpcEvent event = eventFieldFactory.createEvent(P2pTidConstant.T321113);
//		JmsEventParam param = new JmsEventParam();
//		P121110 p121110 = new P121110();
//		p121110.setReceiptId(receipt.getInt("id"));
//		p121110.setTenantId(receipt.getInt("tenant_org_id"));
//		p121110.setUserId(receipt.getInt("user_info_id"));
//		param.addField(p121110);
//		
//		param.setTid(P2pTidConstant.T321113);
//		param.setServerType(appType);
//		param.setServerId(appId);
//		event.setEventParam(param);
//		
//		epc.pushEvent(event, event.getCollision());
//	}
	

}
