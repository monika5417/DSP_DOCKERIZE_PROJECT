package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "ErpEstimateAmountDataGet")
@Table(name = "ERP_BUD_WORK_AMOUNT_GET")
public class ErpEstimateAmountDataGet extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ERP_ESTIMATE_AMOUNT_S", sequenceName = "ERP_ESTIMATE_AMOUNT_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERP_ESTIMATE_AMOUNT_S")
	@Column(name = "ERP_BUDGET_WORKFLOW_ID", columnDefinition = "serial", updatable = false)
	private Long erpAmountBudgetWorkFlowId;

	@Column(name = "ERP_NO")
	private String erpNo;

	@Column(name = "ESTIMATE_SANCTION_NO")
	private String estimateSanctionNo;

	@Column(name = "ESTIMATE_NAME")
	private String estimateName;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "SCHEMA")
	private String schema;

	@Column(name = "SUPERVISION_AMOUNT")
	private BigDecimal supervisionAmount;

	@Column(name = "ESTIMATE_AMOUNT")
	private BigDecimal estimateAmount;

	@Column(name = "CGST")
	private BigDecimal cgst;

	@Column(name = "SGST")
	private BigDecimal sgst;

	@Column(name = "ESTIMATE_STATUS")
	private String estimateStatus;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@JsonIgnore
	@Column(name = "ERP_BUDGET_WORKFLOW_NUMBER")
	private String erpBudgetWorkFlowNumber;

	@Column(name = "SUPERVISION_BALANCE_REMAINING")
	private BigDecimal supervisionBalanceRemaining;

//	yaha se niche ki dono field supervision case or deposit ke case me in hi filed me data ja raha hai
	@Column(name = "SUPER_KWLOAD_BALANCE_REMAIN")
	private BigDecimal supervisionkwLoadBalanceRemaining;

	@Column(name = "SUPER_KVALOAD_BALANCE_REMAIN")
	private BigDecimal supervisionkvaLoadBalanceRemaining;

//	yaha se uper ki dono field supervision case or deposit ke case me in hi filed me data ja raha hai

	@Column(name = "TOTAL_BALANCE_SUPER_AMOUNT")
	private BigDecimal totalBalanceSupervisionAmount;

	@Column(name = "BALANCE_DEPOSIT_AMOUNT")
	private BigDecimal balanceDepositAmount;

	@Column(name = "TOTAL_BALANCE_DEPO_AMOUNT")
	private BigDecimal totalBalanceDepositAmount;

	@Column(name = "Deposit_Amount")
	private BigDecimal depositAmount;

	@Column(name = "ESTIMATE_DATE")
	private String estimateDate;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "KWLOAD")
	private BigDecimal kwLoad;

	@Column(name = "KVALOAD")
	private BigDecimal kvaLoad;

	@Column(name = "TOTAL_DEPO_AMOUNT")
	private BigDecimal totalDeposAmount;

	@Column(name = "TOTAL_OYT_AMOUNT")
	private BigDecimal totalOytAmount;

	@Column(name = "PRAYKSHAN_SHULK")
	private BigDecimal prayakshanShulk;

	@Column(name = "SCHEME_CODE")
	private String schemeCode;

	@Column(name = "COLONY_OR_SLUM")
	private BigDecimal colonyOrSlum;

	@Column(name = "JE_RETURN_AMOUNT")
	private BigDecimal jeReturnAmount;

	@Column(name = "U_SEC_194J_TDS2")
	private BigDecimal u_sec_194J_tds2;

	@Column(name = "U_SEC_194J_TDS10")
	private BigDecimal u_sec_194J_tds10;

	@Column(name = "U_SEC_51_TDS2")
	private BigDecimal u_sec_51_tds2;

	@Column(name = "minus_Cost")
	private BigDecimal minusCost;

	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;

	@Transient
	private BigDecimal refundDemandAmnt;

	@Transient
	private BigDecimal refundJeReturnAmnt;

//	BELOW 3 KEYS ADDED FOR oyt APPLICATIONS 14-Feb-2025nnnnnnnnnnnn
	@Column(name = "AVEDAN_SHULK")
	private BigDecimal avedanShulk;

	@Column(name = "AVEDAN_SHULK_FIVE")
	private BigDecimal avedanShulkFiveRupee;

	@Column(name = "SECURITY_DEPOSIT")
	private BigDecimal securityDeposit;

	@Column(name = "OYT_M_T_C_W_C_S")
	private BigDecimal oytMaterialTotalCostWithCsgstAndSgst;

	@Column(name = "U_SEC_194C_TDS2")
	private BigDecimal u_sec_194C_tds2;

	@Column(name = "U_194C_TDS2_F_SUP_DEP") // this key will contain supervision and deposit sums 2% tds charge
	private BigDecimal u_194C_tds2_fSupDep;

//	11-09-2025 ADDED FOR TAKING SSP PAYMENT
	@Column(name = "SSP_REG_CHARGE", updatable = true)
	private BigDecimal sspRegistrationCharge;

	@Column(name = "SSP_METER_COST", updatable = true)
	private BigDecimal sspMeterCost;

	@Column(name = "SSP_TOTAL_AMOUNT", updatable = true)
	private BigDecimal sspTotalAmount;

	@Column(name = "SORT_CON_L_KV_LINE")
	private Long sortContratoreListByKvLine;

	@Column(name = "REGISTRATION_CHARGES")
	private BigDecimal registrationCharges;

	
	
	
	
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

	public BigDecimal getU_194C_tds2_fSupDep() {
		return u_194C_tds2_fSupDep;
	}

	public void setU_194C_tds2_fSupDep(BigDecimal u_194c_tds2_fSupDep) {
		u_194C_tds2_fSupDep = u_194c_tds2_fSupDep;
	}

	public BigDecimal getU_sec_194C_tds2() {
		return u_sec_194C_tds2;
	}

	public void setU_sec_194C_tds2(BigDecimal u_sec_194C_tds2) {
		this.u_sec_194C_tds2 = u_sec_194C_tds2;
	}

	public BigDecimal getOytMaterialTotalCostWithCsgstAndSgst() {
		return oytMaterialTotalCostWithCsgstAndSgst;
	}

	public void setOytMaterialTotalCostWithCsgstAndSgst(BigDecimal oytMaterialTotalCostWithCsgstAndSgst) {
		this.oytMaterialTotalCostWithCsgstAndSgst = oytMaterialTotalCostWithCsgstAndSgst;
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

	public BigDecimal getRefundDemandAmnt() {
		return refundDemandAmnt;
	}

	public void setRefundDemandAmnt(BigDecimal refundDemandAmnt) {
		this.refundDemandAmnt = refundDemandAmnt;
	}

	public BigDecimal getRefundJeReturnAmnt() {
		return refundJeReturnAmnt;
	}

	public void setRefundJeReturnAmnt(BigDecimal refundJeReturnAmnt) {
		this.refundJeReturnAmnt = refundJeReturnAmnt;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public BigDecimal getMinusCost() {
		return minusCost;
	}

	public void setMinusCost(BigDecimal minusCost) {
		this.minusCost = minusCost;
	}

	public BigDecimal getU_sec_194J_tds2() {
		return u_sec_194J_tds2;
	}

	public void setU_sec_194J_tds2(BigDecimal u_sec_194J_tds2) {
		this.u_sec_194J_tds2 = u_sec_194J_tds2;
	}

	public BigDecimal getU_sec_194J_tds10() {
		return u_sec_194J_tds10;
	}

	public void setU_sec_194J_tds10(BigDecimal u_sec_194J_tds10) {
		this.u_sec_194J_tds10 = u_sec_194J_tds10;
	}

	public BigDecimal getU_sec_51_tds2() {
		return u_sec_51_tds2;
	}

	public void setU_sec_51_tds2(BigDecimal u_sec_51_tds2) {
		this.u_sec_51_tds2 = u_sec_51_tds2;
	}

	public BigDecimal getJeReturnAmount() {
		return jeReturnAmount;
	}

	public void setJeReturnAmount(BigDecimal jeReturnAmount) {
		this.jeReturnAmount = jeReturnAmount;
	}

	public BigDecimal getColonyOrSlum() {
		return colonyOrSlum;
	}

	public void setColonyOrSlum(BigDecimal colonyOrSlum) {
		this.colonyOrSlum = colonyOrSlum;
	}

	public Long getErpAmountBudgetWorkFlowId() {
		return erpAmountBudgetWorkFlowId;
	}

	public void setErpAmountBudgetWorkFlowId(Long erpAmountBudgetWorkFlowId) {
		this.erpAmountBudgetWorkFlowId = erpAmountBudgetWorkFlowId;
	}

	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	public String getEstimateSanctionNo() {
		return estimateSanctionNo;
	}

	public void setEstimateSanctionNo(String estimateSanctionNo) {
		this.estimateSanctionNo = estimateSanctionNo;
	}

	public String getEstimateName() {
		return estimateName;
	}

	public void setEstimateName(String estimateName) {
		this.estimateName = estimateName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public BigDecimal getSupervisionAmount() {
		return supervisionAmount;
	}

	public void setSupervisionAmount(BigDecimal supervisionAmount) {
		this.supervisionAmount = supervisionAmount;
	}

	public BigDecimal getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(BigDecimal estimateAmount) {
		this.estimateAmount = estimateAmount;
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

	public String getEstimateStatus() {
		return estimateStatus;
	}

	public void setEstimateStatus(String estimateStatus) {
		this.estimateStatus = estimateStatus;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getErpBudgetWorkFlowNumber() {
		return erpBudgetWorkFlowNumber;
	}

	public void setErpBudgetWorkFlowNumber(String erpBudgetWorkFlowNumber) {
		this.erpBudgetWorkFlowNumber = erpBudgetWorkFlowNumber;
	}

	public BigDecimal getSupervisionBalanceRemaining() {
		return supervisionBalanceRemaining;
	}

	public void setSupervisionBalanceRemaining(BigDecimal supervisionBalanceRemaining) {
		this.supervisionBalanceRemaining = supervisionBalanceRemaining;
	}

	public BigDecimal getSupervisionkwLoadBalanceRemaining() {
		return supervisionkwLoadBalanceRemaining;
	}

	public void setSupervisionkwLoadBalanceRemaining(BigDecimal supervisionkwLoadBalanceRemaining) {
		this.supervisionkwLoadBalanceRemaining = supervisionkwLoadBalanceRemaining;
	}

	public BigDecimal getSupervisionkvaLoadBalanceRemaining() {
		return supervisionkvaLoadBalanceRemaining;
	}

	public void setSupervisionkvaLoadBalanceRemaining(BigDecimal supervisionkvaLoadBalanceRemaining) {
		this.supervisionkvaLoadBalanceRemaining = supervisionkvaLoadBalanceRemaining;
	}

	public BigDecimal getTotalBalanceSupervisionAmount() {
		return totalBalanceSupervisionAmount;
	}

	public void setTotalBalanceSupervisionAmount(BigDecimal totalBalanceSupervisionAmount) {
		this.totalBalanceSupervisionAmount = totalBalanceSupervisionAmount;
	}

	public BigDecimal getBalanceDepositAmount() {
		return balanceDepositAmount;
	}

	public void setBalanceDepositAmount(BigDecimal balanceDepositAmount) {
		this.balanceDepositAmount = balanceDepositAmount;
	}

	public BigDecimal getTotalBalanceDepositAmount() {
		return totalBalanceDepositAmount;
	}

	public void setTotalBalanceDepositAmount(BigDecimal totalBalanceDepositAmount) {
		this.totalBalanceDepositAmount = totalBalanceDepositAmount;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public BigDecimal getKwLoad() {
		return kwLoad;
	}

	public void setKwLoad(BigDecimal kwLoad) {
		this.kwLoad = kwLoad;
	}

	public BigDecimal getKvaLoad() {
		return kvaLoad;
	}

	public void setKvaLoad(BigDecimal kvaLoad) {
		this.kvaLoad = kvaLoad;
	}

	public BigDecimal getTotalDeposAmount() {
		return totalDeposAmount;
	}

	public void setTotalDeposAmount(BigDecimal totalDeposAmount) {
		this.totalDeposAmount = totalDeposAmount;
	}

	public BigDecimal getTotalOytAmount() {
		return totalOytAmount;
	}

	public void setTotalOytAmount(BigDecimal totalOytAmount) {
		this.totalOytAmount = totalOytAmount;
	}

	public BigDecimal getPrayakshanShulk() {
		return prayakshanShulk;
	}

	public void setPrayakshanShulk(BigDecimal prayakshanShulk) {
		this.prayakshanShulk = prayakshanShulk;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getSortContratoreListByKvLine() {
		return sortContratoreListByKvLine;
	}

	public void setSortContratoreListByKvLine(Long sortContratoreListByKvLine) {
		this.sortContratoreListByKvLine = sortContratoreListByKvLine;
	}

}
