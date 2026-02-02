package com.mpcz.deposit_scheme.backend.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

public @Data class ErpEstimateCalculatedDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal superVisionAmount;
	private BigDecimal cgst;
	private BigDecimal sgst;
	private BigDecimal kwLoadAmount;
	private BigDecimal kvaLoadAmount;
	private String actionPerformed;

	private BigDecimal depositAmount;

	private BigDecimal balanceRemaining;

	private BigDecimal supervisionBalanceRemaining;

	private BigDecimal totalamountOfSupervision;

	private BigDecimal totaldepositAmount;

	private BigDecimal parikshanCgst;
	private BigDecimal prayakshanShulk;
	private BigDecimal finalOyt;

	private String consumerApplicationNo;

	private BigDecimal registrationFee;

	private BigDecimal totalAmount;

	private BigDecimal govMaffBill;

	private BigDecimal mpmkvvclMaffBill;

	private BigDecimal totalAmountOfMKMY;

	private BigDecimal colonyOrSlum;

	private BigDecimal jeReturnAmount;

	private BigDecimal totalBalanceDepositAmount;

	private BigDecimal totalBalanceSupervisionAmount;

	private BigDecimal minusCost;

	private BigDecimal u_sec_194J_tds2;

	private BigDecimal avedanShulk;

	private BigDecimal avedanShulkFiveRupee;

	private BigDecimal securityDeposit;

	private BigDecimal oytMaterialtotalcostwithCgstAndSgst;

	private BigDecimal oytMaterialcostwithCgst;

	private BigDecimal oytMaterialcostwithSgst;

	private BigDecimal sspRegistrationCharge;

	private BigDecimal sspMeterCost;

	private BigDecimal sspTotalAmount;

	private BigDecimal registrationCharges;

	private BigDecimal u_sec_194C_tds2;

	// this key will contain supervision and deposit sums 2% tds charge
	private BigDecimal u_194C_tds2_fSupDep;

	public BigDecimal getU_sec_194C_tds2() {
		return u_sec_194C_tds2;
	}

	public void setU_sec_194C_tds2(BigDecimal u_sec_194C_tds2) {
		this.u_sec_194C_tds2 = u_sec_194C_tds2;
	}

	public BigDecimal getU_194C_tds2_fSupDep() {
		return u_194C_tds2_fSupDep;
	}

	public void setU_194C_tds2_fSupDep(BigDecimal u_194c_tds2_fSupDep) {
		u_194C_tds2_fSupDep = u_194c_tds2_fSupDep;
	}

	public BigDecimal getRegistrationCharges() {
		return registrationCharges;
	}

	public void setRegistrationCharges(BigDecimal registrationCharges) {
		this.registrationCharges = registrationCharges;
	}

	public BigDecimal getSspRegistrationCharge() {
		return sspRegistrationCharge;
	}

	public void setSspRegistrationCharge(BigDecimal sspRegistrationCharge) {
		this.sspRegistrationCharge = sspRegistrationCharge;
	}

	public BigDecimal getSspMeterCost() {
		return sspMeterCost;
	}

	public void setSspMeterCost(BigDecimal sspMeterCost) {
		this.sspMeterCost = sspMeterCost;
	}

	public BigDecimal getSspTotalAmount() {
		return sspTotalAmount;
	}

	public void setSspTotalAmount(BigDecimal sspTotalAmount) {
		this.sspTotalAmount = sspTotalAmount;
	}

	public BigDecimal getOytMaterialtotalcostwithCgstAndSgst() {
		return oytMaterialtotalcostwithCgstAndSgst;
	}

	public void setOytMaterialtotalcostwithCgstAndSgst(BigDecimal oytMaterialtotalcostwithCgstAndSgst) {
		this.oytMaterialtotalcostwithCgstAndSgst = oytMaterialtotalcostwithCgstAndSgst;
	}

	public BigDecimal getOytMaterialcostwithCgst() {
		return oytMaterialcostwithCgst;
	}

	public void setOytMaterialcostwithCgst(BigDecimal oytMaterialcostwithCgst) {
		this.oytMaterialcostwithCgst = oytMaterialcostwithCgst;
	}

	public BigDecimal getOytMaterialcostwithSgst() {
		return oytMaterialcostwithSgst;
	}

	public void setOytMaterialcostwithSgst(BigDecimal oytMaterialcostwithSgst) {
		this.oytMaterialcostwithSgst = oytMaterialcostwithSgst;
	}

	public BigDecimal getAvedanShulk() {
		return avedanShulk;
	}

	public void setAvedanShulk(BigDecimal avedanShulk) {
		this.avedanShulk = avedanShulk;
	}

	public BigDecimal getAvedanShulkFiveRupee() {
		return avedanShulkFiveRupee;
	}

	public void setAvedanShulkFiveRupee(BigDecimal avedanShulkFiveRupee) {
		this.avedanShulkFiveRupee = avedanShulkFiveRupee;
	}

	public BigDecimal getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(BigDecimal securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public BigDecimal getU_sec_194J_tds2() {
		return u_sec_194J_tds2;
	}

	public void setU_sec_194J_tds2(BigDecimal u_sec_194J_tds2) {
		this.u_sec_194J_tds2 = u_sec_194J_tds2;
	}

	public BigDecimal getMinusCost() {
		return minusCost;
	}

	public void setMinusCost(BigDecimal minusCost) {
		this.minusCost = minusCost;
	}

	public BigDecimal getTotalBalanceDepositAmount() {
		return totalBalanceDepositAmount;
	}

	public void setTotalBalanceDepositAmount(BigDecimal totalBalanceDepositAmount) {
		this.totalBalanceDepositAmount = totalBalanceDepositAmount;
	}

	public BigDecimal getTotalBalanceSupervisionAmount() {
		return totalBalanceSupervisionAmount;
	}

	public void setTotalBalanceSupervisionAmount(BigDecimal totalBalanceSupervisionAmount) {
		this.totalBalanceSupervisionAmount = totalBalanceSupervisionAmount;
	}

	public BigDecimal getColonyOrSlum() {
		return colonyOrSlum;
	}

	public void setColonyOrSlum(BigDecimal colonyOrSlum) {
		this.colonyOrSlum = colonyOrSlum;
	}

	public BigDecimal getSuperVisionAmount() {
		return superVisionAmount;
	}

	public void setSuperVisionAmount(BigDecimal superVisionAmount) {
		this.superVisionAmount = superVisionAmount;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getKwLoadAmount() {
		return kwLoadAmount;
	}

	public void setKwLoadAmount(BigDecimal kwLoadAmount) {
		this.kwLoadAmount = kwLoadAmount;
	}

	public BigDecimal getKvaLoadAmount() {
		return kvaLoadAmount;
	}

	public void setKvaLoadAmount(BigDecimal kvaLoadAmount) {
		this.kvaLoadAmount = kvaLoadAmount;
	}

	public String getActionPerformed() {
		return actionPerformed;
	}

	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public BigDecimal getBalanceRemaining() {
		return balanceRemaining;
	}

	public void setBalanceRemaining(BigDecimal balanceRemaining) {
		this.balanceRemaining = balanceRemaining;
	}

	public BigDecimal getSupervisionBalanceRemaining() {
		return supervisionBalanceRemaining;
	}

	public void setSupervisionBalanceRemaining(BigDecimal supervisionBalanceRemaining) {
		this.supervisionBalanceRemaining = supervisionBalanceRemaining;
	}

	public BigDecimal getTotalamountOfSupervision() {
		return totalamountOfSupervision;
	}

	public void setTotalamountOfSupervision(BigDecimal totalamountOfSupervision) {
		this.totalamountOfSupervision = totalamountOfSupervision;
	}

	public BigDecimal getTotaldepositAmount() {
		return totaldepositAmount;
	}

	public void setTotaldepositAmount(BigDecimal totaldepositAmount) {
		this.totaldepositAmount = totaldepositAmount;
	}

	public BigDecimal getParikshanCgst() {
		return parikshanCgst;
	}

	public void setParikshanCgst(BigDecimal parikshanCgst) {
		this.parikshanCgst = parikshanCgst;
	}

	public BigDecimal getPrayakshanShulk() {
		return prayakshanShulk;
	}

	public void setPrayakshanShulk(BigDecimal prayakshanShulk) {
		this.prayakshanShulk = prayakshanShulk;
	}

	public BigDecimal getFinalOyt() {
		return finalOyt;
	}

	public void setFinalOyt(BigDecimal finalOyt) {
		this.finalOyt = finalOyt;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public BigDecimal getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(BigDecimal registrationFee) {
		this.registrationFee = registrationFee;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getGovMaffBill() {
		return govMaffBill;
	}

	public void setGovMaffBill(BigDecimal govMaffBill) {
		this.govMaffBill = govMaffBill;
	}

	public BigDecimal getMpmkvvclMaffBill() {
		return mpmkvvclMaffBill;
	}

	public void setMpmkvvclMaffBill(BigDecimal mpmkvvclMaffBill) {
		this.mpmkvvclMaffBill = mpmkvvclMaffBill;
	}

	public BigDecimal getTotalAmountOfMKMY() {
		return totalAmountOfMKMY;
	}

	public void setTotalAmountOfMKMY(BigDecimal totalAmountOfMKMY) {
		this.totalAmountOfMKMY = totalAmountOfMKMY;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getJeReturnAmount() {
		return jeReturnAmount;
	}

	public void setJeReturnAmount(BigDecimal jeReturnAmount) {
		this.jeReturnAmount = jeReturnAmount;
	}

}
