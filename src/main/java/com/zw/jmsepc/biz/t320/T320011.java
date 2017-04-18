/**
 * Copyright (c) Since 2014, Power by Pw186.com
 */
package com.zw.jmsepc.biz.t320;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zw.jmsepc.biz.base.BaseTraderEvent;
import com.zw.jmsepc.module.AllAccounts;
import com.zw.jmsepc.module.MemberAccount;
import com.zw.jmsepc.protocol.EpcPidConstant;
import com.zw.jmsepc.protocol.EpcTidConstant;
import com.zw.jmsepc.protocol.p120.P120010;
import com.zw.jmsepc.silkie.epc.EpcErrCode;
import com.zw.jmsepc.silkie.epc.impl.Collision;
import com.zw.jmsepc.silkie.jms.epc.JmsEventParam;



/**
 * 帐户充值
 *
 * 2016年5月25日下午4:55:46
 *
 * @author zw
 */
public class T320011 extends BaseTraderEvent {
	
	private static final Log LOG = LogFactory.getLog(T320011.class);
	
	private P120010 p120010;
	
	@Override
	protected int checkParam(JmsEventParam param) throws Exception {
		if(param == null) {
			if(LOG.isWarnEnabled()) LOG.warn("未收到事件参数");
			return EpcErrCode.INVALID_PARAM;
		}
		p120010 = (P120010)param.queryFirst(EpcPidConstant.P120010);
		if (p120010 == null) {
			return EpcErrCode.INVALID_PARAM;
		}
		if(p120010.getTenantId() <= 0 || p120010.getMemberId() <= 0 || p120010.getRechargeId() <= 0) {
			return EpcErrCode.INVALID_PARAM;
		}
		setMemberId(p120010.getMemberId());
		return EpcErrCode.SUCCESS;
	}

	@Override
	protected void doBiz() throws Exception {
		
		try {
			System.out.println("-----------充值成功---------------");
			responseCommonMessage(EpcTidConstant.T320012, EpcErrCode.SUCCESS, "充值成功");
		} catch (Exception e) {
			responseCommonMessage(EpcTidConstant.T320012, EpcErrCode.UNKNOWN_ERR, "在线充值失败，error:"+e.getMessage());
			LOG.error("在线充值异常", e);
		} finally {
		}
		
	}

	@Override
	public Collision getCollision() {
		p120010 = (P120010)param.queryFirst(EpcPidConstant.P120010);
		/**
		 * 充值事件的冲突体为会员id
		 */
		return Collision.generateCollision("memberId_"+p120010.getMemberId());
	}

}
