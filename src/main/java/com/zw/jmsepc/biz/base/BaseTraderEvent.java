/**
 * pw186.com Inc. Copyright (c) 2015 All Rights Reserved.
 */
package com.zw.jmsepc.biz.base;

import java.util.Date;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zw.jmsepc.config.SysConfig;
import com.zw.jmsepc.protocol.p110.P110990;
import com.zw.jmsepc.silkie.epc.impl.BaseEpcEvent;
import com.zw.jmsepc.silkie.epc.impl.EpcEventParam;
import com.zw.jmsepc.silkie.jms.AppInfo;
import com.zw.jmsepc.silkie.jms.JmsMessageHandler;
import com.zw.jmsepc.silkie.jms.PidConstant;
import com.zw.jmsepc.silkie.jms.epc.JmsEventParam;
import com.zw.jmsepc.silkie.jms.message.JmsBody;
import com.zw.jmsepc.silkie.jms.message.P999999;
import com.zw.jmsepc.silkie.jms.message.field.ByteDataField;
import com.zw.jmsepc.silkie.jms.message.impl.DefaultJmsMessage;


/**
 * 采集控制器JMS事件基类
 * 
 * @author zw.lai 2012-7-13 下午06:11:59
 * @version 1.0
 */
public abstract class BaseTraderEvent extends BaseEpcEvent {

  protected P999999 p999999;
  protected JmsEventParam eventParam;
  public int memberId;
  
//  @Autowired
//  SysConfig sysConfig;
//  @Autowired
//  Queue traderResponse;
//  @Autowired
//  JmsMessageHandler jmsMessageHandler;
  @Override
  protected int checkParam(EpcEventParam param) throws Exception {
    eventParam = (JmsEventParam) param;
    p999999 = (P999999) param.queryFirst(PidConstant.P999999);
    return checkParam(eventParam);
  }

  protected abstract int checkParam(JmsEventParam param) throws Exception;


  /**
   * 参数错误的处理方式
   */
  @Override
  public void doParamError(int errCode) throws Exception {

  }
  
  protected void responseCommonMessage(int tid, int errCode, String errMsg) throws Exception {
	  P110990 p110990 = new P110990();
	  p110990.setRtnCode((short)errCode);
	  p110990.setRtnMsg(errMsg);
	  responseMessage(tid, p110990);
  }
  
  /**
   * 响应消息
   * @param tid
   * @param fields
   * @throws Exception
   */
  protected void responseMessage(int tid, ByteDataField... fields) throws Exception {
	  if(p999999 == null) {
		  return;
	  }
//	  SysConfig sysConfig = SpringBeanManager.getBean("sysConfig", SysConfig.class);
//	  Queue traderResponse = SpringBeanManager.getBean("traderResponse", Queue.class);
//	  JmsMessageHandler jmsMessageHandler = SpringBeanManager.getBean("jmsMessageHandler", JmsMessageHandler.class);
	  @SuppressWarnings("resource")
	  ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-context.xml");
	  SysConfig sysConfig = (SysConfig) ac.getBean("sysConfig");
	  Queue traderResponse = (Queue) ac.getBean("traderResponse");
	  JmsMessageHandler jmsMessageHandler = (JmsMessageHandler) ac.getBean("jmsMessageHandler");
	  DefaultJmsMessage message = new DefaultJmsMessage();
	  JmsBody body = new JmsBody();
	  for(ByteDataField field : fields) {
		  body.addField(field);  
	  }
	  body.setTid(tid);
	  message.setBody(body);
	  message.getPropHeader().setIntProperty(AppInfo.APP_TYPE, p999999.getAppType());
	  message.getPropHeader().setIntProperty(AppInfo.APP_ID, p999999.getAppId());
		
	  AppInfo appInfo = new AppInfo();
	  appInfo.setId(sysConfig.getAppId());
	  appInfo.setType(sysConfig.getAppType());
		
	  jmsMessageHandler.sendDialogResponse(traderResponse, message, appInfo);
  }
  
  public void setMemberId(int memberId) {
	this.memberId = memberId;  
  }
  
  public int getMemberId() {
	  return memberId;
  }
  
//  protected void saveCreditPointLog(UserInfo user, TraderAccount account, int point, int pointType) {
//		TraderCreditPointLog log = new TraderCreditPointLog();
//		log.set("tenant_org_id", user.getInt("tenant_org_id"));
//		log.set("user_info_id", user.getInt("id"));
//		log.set("real_name", user.getStr("login_name"));
//		log.set("point", point);
//		log.set("point_type", pointType);
//		log.set("credit_point", account.getInt("credit_point"));
//		log.set("created_at", new Date());
//		log.save();
//  }
//  
//  /**
//	 * 获取借款标账单期数
//	 * @param bid
//	 * @return
//	 */
//	protected int getBillCount(BorrowBid bid) {
//		if(bid.getInt("refund_type") == BorrowRefundTypeEnum.ONE_TIME.getValue()) { // 一次性还款
//			return 1;
//		}
//		return getTimeCount(bid);
//	}
//	
//	/**
//	 * 获取借款标月数和天数
//	 * @param bid
//	 * @return
//	 */
//	protected int getTimeCount(BorrowBid bid) {
//		int timeType = bid.getInt("time_type");
//		int timeLimit = bid.getInt("time_limit");
//		int billCount = 0;
//		if(timeType == BorrowTimeTypeEnum.MONTH.getValue()) {
//			billCount = timeLimit;
//		} else if(timeType == BorrowTimeTypeEnum.YEAR.getValue()) {
//			billCount = timeLimit*12;
//		} else if(timeType == BorrowTimeTypeEnum.DAY.getValue()) {
//			billCount = timeLimit/30;
//		}
//		return billCount;
//	}
//	
//	/**
//	 * 计算总利息
//	 * @param yearRate 
//	 */
//	public double calInterest(double amount, double yearRate, int refundType, int timeType, int timeLimit) {
//		int billCount = 0;	//总期数
//		double rate = 0.0; //每月利率
//		double interest = 0;
//		if(timeType == BorrowTimeTypeEnum.YEAR.getValue()){	//按年计算
//			billCount = timeLimit * 12;
//			rate = DoubleUtil.divide(yearRate, 12);
//			interest = cal(amount, rate, refundType, billCount);
//		}else if (timeType == BorrowTimeTypeEnum.MONTH.getValue()) { //按月
//			billCount = timeLimit;
//			rate = DoubleUtil.divide(yearRate, 12);
//			interest = cal(amount, rate, refundType, billCount);
//		}else if (timeType == BorrowTimeTypeEnum.DAY.getValue()) { //按日,如果按日算只能一次性还款
//			billCount = timeLimit;
//			rate = DoubleUtil.divide(yearRate, 365);
//			interest = cal(amount, rate, BorrowRefundTypeEnum.ONE_TIME.getValue(), billCount);
//		}
//		return interest;
//	}
//	
//	/**
//	 * 计算
//	 */
//	public double cal(double amount, double rate, int refundType, int billCount) {
//		double interest = 0.0;
//		if(refundType == BorrowRefundTypeEnum.ONE_TIME.getValue()){	//一次性还款
//			interest = DoubleUtil.multiply(amount, rate, billCount);
//		}else if(refundType == BorrowRefundTypeEnum.MONTH_INTEREST.getValue()){	//按月付息、到期还本
//			interest = DoubleUtil.multiply(amount, rate, billCount);
//		}else if(refundType == BorrowRefundTypeEnum.MONTH_EQUAL.getValue()){	//按月还款、等额本息
//		    interest = DoubleUtil.multiply(DoubleUtil.divide(DoubleUtil.multiply(amount, rate,
//					(Math.pow((1+rate), billCount))),(Math.pow((1+rate), billCount)-1)), billCount) - amount; 
//		} else if(refundType == BorrowRefundTypeEnum.MONTH_ALL.getValue()){	//按月等额还款、全额利息
//			interest = DoubleUtil.multiply(amount, rate, billCount);
//		} else if(refundType == BorrowRefundTypeEnum.QUARTER_INTEREST.getValue()){	//按季度付息、到期还本
//			interest = DoubleUtil.multiply(amount, rate, billCount);
//		}else if(refundType == BorrowRefundTypeEnum.QUARTER_CAPITAL.getValue()){	//按月还款、等额本金
//			double monthAmount = DoubleUtil.divide(amount, billCount);
//			for (int i = 1; i <= billCount; i++) {
//				interest += DoubleUtil.multiply(DoubleUtil.subtract(amount, (i - 1) * monthAmount), rate);
//			}
//		}
//		return interest;
//	}
//	
//	public TraderOrder genRawardOrder(UserInfo user, TraderAccount account, double amount) {
//		TraderOrder order = new TraderOrder();
//		order.set("order_code", "");
//		order.set("tenant_org_id", user.getInt("tenant_org_id"));
//		order.set("initiator_user_id", user.getInt("id"));
//		order.set("real_name", user.getStr("login_name"));
//		order.set("recipient_user_id", TraderAccountType.DEPOSIT.getValue());
//		order.set("recipient_real_name", TraderAccountType.DEPOSIT.getName());
//		order.set("order_name", "借款标投标奖励");
//		order.set("amount", amount);
//		order.set("trader_type", TraderType.RAWARD.getValue());
//		order.set("fund_flow", FundFlowType.PAY.getValue());
//		order.set("order_time", new Date());
//		order.set("pay_source", PaySource.BALANCE.getValue());
//		order.set("thirdpay_code", "");
//		order.set("thirdpay_name", "");
//		order.set("account_balance", account.getBigDecimal("balance").doubleValue());
//		order.set("account_freeze", account.getBigDecimal("freeze").doubleValue());
//		order.set("account_usable", account.getAvailable());
//		order.set("delete_flag", FlagEnum.NO.getValue());
//		order.save();
//		order.set("order_code", NumCodeGenerateUtil.getTraderOrderCode(order.getInt("id")));
//		order.update();
//		return order;
//	}
//
//	public TraderOrder genServiceFeeOrder(UserInfo user, TraderAccount account, double amount) {
//		TraderOrder order = new TraderOrder();
//		order.set("order_code","");
//		order.set("tenant_org_id", user.getInt("tenant_org_id"));
//		order.set("initiator_user_id", user.getInt("id"));
//		order.set("real_name", user.getStr("login_name"));
//		order.set("recipient_user_id", TraderAccountType.SECURITY.getValue());
//		order.set("recipient_real_name", TraderAccountType.SECURITY.getName());
//		order.set("order_name", "借款标平台管理费");
//		order.set("amount", amount);
//		order.set("trader_type", TraderType.BORROW_FEE.getValue());
//		order.set("fund_flow", FundFlowType.PAY.getValue());
//		order.set("order_time", new Date());
//		order.set("pay_source", PaySource.BALANCE.getValue());
//		order.set("thirdpay_code", "");
//		order.set("thirdpay_name", "");
//		order.set("account_balance", account.getBigDecimal("balance").doubleValue());
//		order.set("account_freeze", account.getBigDecimal("freeze").doubleValue());
//		order.set("account_usable", account.getAvailable());
//		order.set("delete_flag", FlagEnum.NO.getValue());
//		order.save();
//		order.set("order_code", NumCodeGenerateUtil.getTraderOrderCode(order.getInt("id")));
//		order.update();
//		return order;
//	}
//
//	public TraderOrder genBorrowOrder(UserInfo user, TraderAccount account, double amount) {
//		TraderOrder order = new TraderOrder();
//		order.set("order_code","");
//		order.set("tenant_org_id", user.getInt("tenant_org_id"));
//		order.set("initiator_user_id", user.getInt("id"));
//		order.set("real_name", user.getStr("login_name"));
//		order.set("recipient_user_id", TraderAccountType.DEPOSIT.getValue());
//		order.set("recipient_real_name", TraderAccountType.DEPOSIT.getName());
//		order.set("order_name", "借款标放款");
//		order.set("amount", amount);
//		order.set("trader_type", TraderType.BORROW.getValue());
//		order.set("fund_flow", FundFlowType.INCOME.getValue());
//		order.set("order_time", new Date());
//		order.set("pay_source", PaySource.BALANCE.getValue());
//		order.set("thirdpay_code", "");
//		order.set("thirdpay_name", "");
//		order.set("account_balance", account.getBigDecimal("balance").doubleValue());
//		order.set("account_freeze", account.getBigDecimal("freeze").doubleValue());
//		order.set("account_usable", account.getAvailable());
//		order.set("delete_flag", FlagEnum.NO.getValue());
//		order.save();
//		order.set("order_code", NumCodeGenerateUtil.getTraderOrderCode(order.getInt("id")));
//		order.update();
//		return order;
//	}
//	
//	public TraderOrder genReceiptBorrowOrder(UserInfo user, TraderAccount account, BorrowBid bid, double amount) {
//		TraderOrder order = new TraderOrder();
//		order.set("order_code", "");
//		order.set("tenant_org_id", user.getInt("tenant_org_id"));
//		order.set("initiator_user_id", user.getInt("id"));
//		order.set("real_name", user.getStr("login_name"));
//		order.set("recipient_user_id", bid.getInt("user_info_id"));
//		order.set("recipient_real_name", bid.getStr("user_real_name"));
//		order.set("order_name", "投标扣款");
//		order.set("amount", amount);
//		order.set("trader_type", TraderType.BID.getValue());
//		order.set("fund_flow", FundFlowType.PAY.getValue());
//		order.set("order_time", new Date());
//		order.set("pay_source", PaySource.BALANCE.getValue());
//		order.set("thirdpay_code", "");
//		order.set("thirdpay_name", "");
//		order.set("account_balance", account.getBigDecimal("balance").doubleValue());
//		order.set("account_freeze", account.getBigDecimal("freeze").doubleValue());
//		order.set("account_usable", account.getAvailable());
//		order.set("delete_flag", FlagEnum.NO.getValue());
//		order.save();
//		order.set("order_code", NumCodeGenerateUtil.getTraderOrderCode(order.getInt("id")));
//		order.update();
//		return order;
//	}
//
//	public TraderOrder genRawardOrder(UserInfo user, TraderAccount account, BorrowBid bid, double amount) {
//		TraderOrder order = new TraderOrder();
//		order.set("order_code", "");
//		order.set("tenant_org_id", user.getInt("tenant_org_id"));
//		order.set("initiator_user_id", user.getInt("id"));
//		order.set("real_name", user.getStr("login_name"));
//		order.set("recipient_user_id", bid.getInt("user_info_id"));
//		order.set("recipient_real_name", bid.getStr("user_real_name"));
//		order.set("order_name", "借款标投标奖励");
//		order.set("amount", amount);
//		order.set("trader_type", TraderType.RAWARD.getValue());
//		order.set("fund_flow", FundFlowType.INCOME.getValue());
//		order.set("order_time", new Date());
//		order.set("pay_source", PaySource.BALANCE.getValue());
//		order.set("thirdpay_code", "");
//		order.set("thirdpay_name", "");
//		order.set("account_balance", account.getBigDecimal("balance").doubleValue());
//		order.set("account_freeze", account.getBigDecimal("freeze").doubleValue());
//		order.set("account_usable", account.getAvailable());
//		order.set("delete_flag", FlagEnum.NO.getValue());
//		order.save();
//		order.set("order_code", NumCodeGenerateUtil.getTraderOrderCode(order.getInt("id")));
//		order.update();
//		return order;
//	}
//	
//	/**
//	 * 生成推荐奖励通知
//	 * @param tenantId
//	 * @param userId 推荐人ID
//	 * @param presenteeId 被推荐人ID
//	 * @param serviceFee
//	 * @throws Exception
//	 */
//	protected void pushReferrerEvent(int tenantId, int userId, int presenteeId, double serviceFee, int level) throws Exception {
//		Epc epc = SpringBeanManager.getBean("epc", Epc.class);
//		EventFieldFactory eventFieldFactory = SpringBeanManager.getBean("eventFieldFactory", EventFieldFactory.class);
//		SysConfig sysConfig = SpringBeanManager.getBean("sysConfig", SysConfig.class);
//		
//		EpcEvent event = eventFieldFactory.createEvent(P2pTidConstant.T323033);
//		JmsEventParam param = new JmsEventParam();
//		P123030 p123030 = new P123030();
//		p123030.setTenantId(tenantId);
//		p123030.setUserId(userId);
//		p123030.setPresenteeId(presenteeId);
//		p123030.setServiceFee(serviceFee);
//		p123030.setCpsLevel(level);
//		param.addField(p123030);
//		
//		param.setTid(P2pTidConstant.T323033);
//		param.setServerType(sysConfig.getAppType());
//		param.setServerId(sysConfig.getAppId());
//		event.setEventParam(param);
//		
//		epc.pushEvent(event, event.getCollision());
//	}
//	
//	/**
//	 * 生成红包兑现交易单
//	 * @param user
//	 * @param account
//	 * @param amount
//	 */
//	protected TraderOrder genRedbonusOrder(UserInfo user, TraderAccount account, double amount) {
//		TraderOrder order = new TraderOrder();
//		order.set("order_code", "");
//		order.set("tenant_org_id", user.getInt("tenant_org_id"));
//		order.set("initiator_user_id", user.getInt("id"));
//		order.set("real_name", user.getStr("login_name"));
//		order.set("recipient_user_id", TraderAccountType.OFFLINE.getValue());
//		order.set("recipient_real_name", TraderAccountType.OFFLINE.getName());
//		order.set("order_name", "投标兑现红包");
//		order.set("amount", amount);
//		order.set("trader_type", TraderType.REDBONUS_FEE.getValue());
//		order.set("fund_flow", FundFlowType.INCOME.getValue());
//		order.set("order_time", new Date());
//		order.set("pay_source", PaySource.BALANCE.getValue());
//		order.set("thirdpay_code", "");
//		order.set("thirdpay_name", "");
//		order.set("account_balance", account.getBigDecimal("balance").doubleValue());
//		order.set("account_freeze", account.getBigDecimal("freeze").doubleValue());
//		order.set("account_usable", account.getAvailable());
//		order.set("delete_flag", FlagEnum.NO.getValue());
//		order.save();
//		order.set("order_code", NumCodeGenerateUtil.getTraderOrderCode(order.getInt("id")));
//		order.update();
//		return order;
//	}
//	
//	/**
//	 * 是否开通资金托管模式
//	 * @return
//	 */
//	protected boolean isTrusteeModule(int tenantOrgId) {
//		TenantProductModule productModule = TenantProductModule.dao.findFirst("select * from tenant_product_module where tenant_org_id = ? and product_code = ?", tenantOrgId, ProductModuleType.TRUSTEE.getValue());
//		if (productModule != null && !productModule.getBoolean("close_flag")) {
//			return true;
//		}
//		return false;
//	}
	
}
