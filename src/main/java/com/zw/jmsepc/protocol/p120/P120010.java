package com.zw.jmsepc.protocol.p120;

import com.zw.jmsepc.protocol.EpcPidConstant;
import com.zw.jmsepc.silkie.jms.message.field.ByteDataField;

/**
 * 在线账户充值
 *
 * 2016年5月25日下午2:52:49
 *
 * @author zw
 */
public class P120010 extends ByteDataField {

	/**
	 * 租户ID
	 */
	private int tenantId;
	/**
	 * 用户ID
	 * 冲突体  同一用户不能并发操作
	 */
	private int memberId;
	/**
	 * 充值ID
	 */
	private int rechargeId;
	
	@Override
	protected void readData() throws Exception {
		tenantId = readInt();
		memberId = readInt();
		rechargeId = readInt();
	}

	@Override
	protected void writeData() throws Exception {
		writeInt(tenantId);
		writeInt(memberId);
		writeInt(rechargeId);
	}
	
	@Override
	public int id() {
		return EpcPidConstant.P120010;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(int rechargeId) {
		this.rechargeId = rechargeId;
	}

}
