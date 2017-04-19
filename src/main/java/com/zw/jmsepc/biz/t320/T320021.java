/**
 * Copyright (c) Since 2014, Power by Pw186.com
 */
package com.zw.jmsepc.biz.t320;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zw.jmsepc.biz.base.BaseTraderEvent;
import com.zw.jmsepc.protocol.EpcPidConstant;
import com.zw.jmsepc.protocol.EpcTidConstant;
import com.zw.jmsepc.protocol.p120.P120020;
import com.zw.jmsepc.silkie.epc.EpcErrCode;
import com.zw.jmsepc.silkie.epc.impl.Collision;
import com.zw.jmsepc.silkie.jms.epc.JmsEventParam;


/**
 * 帐户提现
 *
 * 2016年5月25日下午4:55:46
 *
 * @author zw
 */
public class T320021 extends BaseTraderEvent {
	
	private static final Log LOG = LogFactory.getLog(T320021.class);
	
	private P120020 p120020;
	
//	@Resource
//	private AllAccounts allAccounts;
	
	@Override
	protected int checkParam(JmsEventParam param) throws Exception {
		if(param == null) {
			if(LOG.isWarnEnabled()) LOG.warn("未收到事件参数");
			return EpcErrCode.INVALID_PARAM;
		}
		p120020 = (P120020)param.queryFirst(EpcPidConstant.P120020);
		if (p120020 == null) {
			return EpcErrCode.INVALID_PARAM;
		}
		if(p120020.getTenantId() <= 0 || p120020.getMemberId() <= 0) {
			return EpcErrCode.INVALID_PARAM;
		}
		setMemberId(p120020.getMemberId());
		return EpcErrCode.SUCCESS;
	}

	@Override
	protected void doBiz() throws Exception {
		
		try {
			responseCommonMessage(EpcTidConstant.T320022, EpcErrCode.SUCCESS, "提现成功");
		
		} catch (Exception e) {
			responseCommonMessage(EpcTidConstant.T320022, EpcErrCode.UNKNOWN_ERR, "在线充值失败，error:"+e.getMessage());
			LOG.error("在线充值异常", e);
		} finally {
		}
		
	}

	@Override
	public Collision getCollision() {
		p120020 = (P120020)param.queryFirst(EpcPidConstant.P120020);
		/**
		 * 提现事件的冲突体为会员id
		 */
		return Collision.generateCollision("memberId_"+p120020.getMemberId());
	}

}
