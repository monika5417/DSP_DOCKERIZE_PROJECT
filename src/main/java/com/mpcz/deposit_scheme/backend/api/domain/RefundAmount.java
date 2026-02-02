package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.RequestParam;

@Table(name="REFUND_AMOUNT")
@Entity
public class RefundAmount extends Auditable<Long>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "REFUND_AMOUNT_SEQ", sequenceName = "REFUND_AMOUNT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFUND_AMOUNT_SEQ")
	@Column(name="ID")
	private Long id;
	
	@Column(name="CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;
	
	@Column(name="DGM_RAISED_REQ_NAME")
	private String dgmRaisedRequestName;
	
	@Column(name="DGM_ID_RAISED_REQ")
	private String dgmIdRaisedRequest;
	
	@Column(name="REFUND_VOUCHER_NO")
	private String refundVoucherNo;
	
	@Column(name="REFUND_AMOUNT")
	private BigDecimal refundAmount;
	
	@Column(name="REFUND_TYPE")
	private String refundType;
	
	@Column(name="CONSUMER_NAME")
	private String consumerName;
	
	@Column(name="DGM_APPROVED_ID")
	private String dgmApprovedId; 
	
	@Column(name="DGM_APPROAL")
	private String dgmApproval;
	
	@Column(name="GM_APPROVED_ID")
	private String gmApprovedId;
	
	@Column(name="GM_APPROVAL")
	private String gmApproval;
	
	@Column(name="DGM_STC_APPROVED_ID")
	private String dgmStcApprovedId;
	
	@Column(name="DGM_STC_APPROVAL")
	private String dgmStcApproval;
	
	@Column(name="DEMAND_AMOUNT")
	private BigDecimal demandAmount;
	
	@Column(name="REVISE_AMOUNT")
	private BigDecimal reviseAmount;
	
	@Column(name="DEMAND_RETURN_MATERIAL")
	private BigDecimal demandReturnMaterialAmnt;
	
	@Column(name="REVISE_RETURN_MATERIAL")
	private BigDecimal reviseReturnMaterialAmnt;
	
	@Column(name="CONSUMER_APP_ID")
	private Long consumerAppId;
	
	@Column(name="DGM_APPROVED_NAME")
	private String dgmApprovedName;
	
	@Column(name="DGM_APPROVED_DATE")
	private String dgmApprovedDate;
	
	@Column(name="GM_APPROVED_NAME")
	private String gmApprovedName;
	
	@Column(name="GM_APPROVED_DATE")
	private String gmApprovedDate;
	
	@Column(name="DGM_STC_APPROVED_NAME")
	private String dgmStcApprovedName;
	
	@Column(name="DGM_STC_APPROVED_DATE")
	private String dgmStcApprovedDate;
	
	@Column(name ="FINANCE_ID")
	private String financeId;
	
	@Column(name ="FINANCE_NAME")
	private String financeName;
	
	@Column(name ="FINANCE_APPROVED_DATE")
	private String financeApprovedDate;
	
	@Column(name ="REFUND_STATUS")
	private String refundStatus;
	
	@Column(name ="FINANCE_APPROVAL")
	private String financeApproval;
	
	@Column(name ="DGM_REMARK")
	private String dgmRemark;
	
	@Column(name ="GM_REMARK")
	private String gmRemark;
	
	@Column(name ="FINANCE_REMARK")
	private String financeRemark;
	
	@Column(name ="DGM_STC_REMARK")
	private String dgmStcRemark;
	
	@Column(name ="SUPERVISION_AMOUNT")
	private BigDecimal supervisionAmount;
	
	@Column(name ="DEPOSIT_AMOUNT")
	private BigDecimal depositAmount;
	
	@Column(name ="KVA_AMOUNT")
	private BigDecimal kvaAmount;
	
	@Column(name ="COLONY_OR_SLUM_AMOUNT")
	private BigDecimal colonyOrSlumAmount;
	
	@Column(name ="RETURN_AMOUNT")
	private BigDecimal returnAmount;
	
	@Column(name = "SECURITY_DEPOSIT_AMOUNT")
	private BigDecimal securityDepositAmount;
	
	@Column(name = "MKMY_PAID_AMOUNT_50")
	private BigDecimal mkmyPaidAmount;
	
	@Column(name = "DEMAND_ERP_NO")
	private String demandErpNo;
	
	@Column(name = "REVISE_ERP_NO")
	private String reviseErpNo;
	
	@Column(name = "E_FILE_NO")
	private String eFileNo;
	
	@Column(name = "FINANCE_ERP_REMARK")
	private String financeErpRemark;
	
	@Column(name = "FINANCE_ERP_PAYMENT_DATE")
	private  String financeErpPaymentDate;
	
	@Column(name = "ERP_REFUND_VOUCHER_NUMBER")
	private String erpRefundVoucherNumber;
	
	@Column(name = "FINANCE_REVERT_TO_DISCOM")
	private String financeRevertToDiscom;
	
	@Column(name = "SSP_REG_CHARGE")
	private BigDecimal sspRegCharge;
	
	@Column(name = "SSP_METER_COST")
	private BigDecimal sspMeterCost;
	
	@Column(name = "APPLICATION_STATUS_ID")
	private Long applicationStatusId;
	
	
	

	
	public Long getApplicationStatusId() {
		return applicationStatusId;
	}
	public void setApplicationStatusId(Long applicationStatusId) {
		this.applicationStatusId = applicationStatusId;
	}
	public BigDecimal getSspRegCharge() {
		return sspRegCharge;
	}
	public void setSspRegCharge(BigDecimal sspRegCharge) {
		this.sspRegCharge = sspRegCharge;
	}
	public BigDecimal getSspMeterCost() {
		return sspMeterCost;
	}
	public void setSspMeterCost(BigDecimal sspMeterCost) {
		this.sspMeterCost = sspMeterCost;
	}
	public String getFinanceRevertToDiscom() {
		return financeRevertToDiscom;
	}
	public void setFinanceRevertToDiscom(String financeRevertToDiscom) {
		this.financeRevertToDiscom = financeRevertToDiscom;
	}
	public String getFinanceErpRemark() {
		return financeErpRemark;
	}
	public void setFinanceErpRemark(String financeErpRemark) {
		this.financeErpRemark = financeErpRemark;
	}
	public String getFinanceErpPaymentDate() {
		return financeErpPaymentDate;
	}
	public void setFinanceErpPaymentDate(String financeErpPaymentDate) {
		this.financeErpPaymentDate = financeErpPaymentDate;
	}
	public String getErpRefundVoucherNumber() {
		return erpRefundVoucherNumber;
	}
	public void setErpRefundVoucherNumber(String erpRefundVoucherNumber) {
		this.erpRefundVoucherNumber = erpRefundVoucherNumber;
	}
	public String geteFileNo() {
		return eFileNo;
	}
	public void seteFileNo(String eFileNo) {
		this.eFileNo = eFileNo;
	}
	public String getDemandErpNo() {
		return demandErpNo;
	}
	public void setDemandErpNo(String demandErpNo) {
		this.demandErpNo = demandErpNo;
	}
	public String getReviseErpNo() {
		return reviseErpNo;
	}
	public void setReviseErpNo(String reviseErpNo) {
		this.reviseErpNo = reviseErpNo;
	}
	public BigDecimal getSecurityDepositAmount() {
		return securityDepositAmount;
	}
	public void setSecurityDepositAmount(BigDecimal securityDepositAmount) {
		this.securityDepositAmount = securityDepositAmount;
	}
	public BigDecimal getMkmyPaidAmount() {
		return mkmyPaidAmount;
	}
	public void setMkmyPaidAmount(BigDecimal mkmyPaidAmount) {
		this.mkmyPaidAmount = mkmyPaidAmount;
	}
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}
	public BigDecimal getSupervisionAmount() {
		return supervisionAmount;
	}
	public void setSupervisionAmount(BigDecimal supervisionAmount) {
		this.supervisionAmount = supervisionAmount;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public BigDecimal getKvaAmount() {
		return kvaAmount;
	}
	public void setKvaAmount(BigDecimal kvaAmount) {
		this.kvaAmount = kvaAmount;
	}
	public BigDecimal getColonyOrSlumAmount() {
		return colonyOrSlumAmount;
	}
	public void setColonyOrSlumAmount(BigDecimal colonyOrSlumAmount) {
		this.colonyOrSlumAmount = colonyOrSlumAmount;
	}
	public String getDgmStcRemark() {
		return dgmStcRemark;
	}
	public void setDgmStcRemark(String dgmStcRemark) {
		this.dgmStcRemark = dgmStcRemark;
	}
	public String getDgmRemark() {
		return dgmRemark;
	}
	public void setDgmRemark(String dgmRemark) {
		this.dgmRemark = dgmRemark;
	}
	public String getGmRemark() {
		return gmRemark;
	}
	public void setGmRemark(String gmRemark) {
		this.gmRemark = gmRemark;
	}
	public String getFinanceRemark() {
		return financeRemark;
	}
	public void setFinanceRemark(String financeRemark) {
		this.financeRemark = financeRemark;
	}
	public String getFinanceApproval() {
		return financeApproval;
	}
	public void setFinanceApproval(String financeApproval) {
		this.financeApproval = financeApproval;
	}
	public String getFinanceId() {
		return financeId;
	}
	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}
	public String getFinanceName() {
		return financeName;
	}
	public void setFinanceName(String financeName) {
		this.financeName = financeName;
	}
	public String getFinanceApprovedDate() {
		return financeApprovedDate;
	}
	public void setFinanceApprovedDate(String financeApprovedDate) {
		this.financeApprovedDate = financeApprovedDate;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getDgmApprovedName() {
		return dgmApprovedName;
	}
	public String getDgmApprovedDate() {
		return dgmApprovedDate;
	}
	public void setDgmApprovedDate(String dgmApprovedDate) {
		this.dgmApprovedDate = dgmApprovedDate;
	}
	public void setDgmApprovedName(String dgmApprovedName) {
		this.dgmApprovedName = dgmApprovedName;
	}
	

	public String getGmApprovedName() {
		return gmApprovedName;
	}
	public void setGmApprovedName(String gmApprovedName) {
		this.gmApprovedName = gmApprovedName;
	}
	public String getGmApprovedDate() {
		return gmApprovedDate;
	}
	public void setGmApprovedDate(String gmApprovedDate) {
		this.gmApprovedDate = gmApprovedDate;
	}
	public String getDgmStcApprovedName() {
		return dgmStcApprovedName;
	}
	public void setDgmStcApprovedName(String dgmStcApprovedName) {
		this.dgmStcApprovedName = dgmStcApprovedName;
	}
	public String getDgmStcApprovedDate() {
		return dgmStcApprovedDate;
	}
	public void setDgmStcApprovedDate(String dgmStcApprovedDate) {
		this.dgmStcApprovedDate = dgmStcApprovedDate;
	}
	public Long getConsumerAppId() {
		return consumerAppId;
	}
	public void setConsumerAppId(Long consumerAppId) {
		this.consumerAppId = consumerAppId;
	}
	public BigDecimal getDemandReturnMaterialAmnt() {
		return demandReturnMaterialAmnt;
	}
	public void setDemandReturnMaterialAmnt(BigDecimal demandReturnMaterialAmnt) {
		this.demandReturnMaterialAmnt = demandReturnMaterialAmnt;
	}
	public BigDecimal getReviseReturnMaterialAmnt() {
		return reviseReturnMaterialAmnt;
	}
	public void setReviseReturnMaterialAmnt(BigDecimal reviseReturnMaterialAmnt) {
		this.reviseReturnMaterialAmnt = reviseReturnMaterialAmnt;
	}
	public BigDecimal getDemandAmount() {
		return demandAmount;
	}
	public void setDemandAmount(BigDecimal demandAmount) {
		this.demandAmount = demandAmount;
	}
	public BigDecimal getReviseAmount() {
		return reviseAmount;
	}
	public void setReviseAmount(BigDecimal reviseAmount) {
		this.reviseAmount = reviseAmount;
	}
	public String getDgmStcApprovedId() {
		return dgmStcApprovedId;
	}
	public void setDgmStcApprovedId(String dgmStcApprovedId) {
		this.dgmStcApprovedId = dgmStcApprovedId;
	}
	public String getDgmStcApproval() {
		return dgmStcApproval;
	}
	public void setDgmStcApproval(String dgmStcApproval) {
		this.dgmStcApproval = dgmStcApproval;
	}
	public String getDgmRaisedRequestName() {
		return dgmRaisedRequestName;
	}
	public void setDgmRaisedRequestName(String dgmRaisedRequestName) {
		this.dgmRaisedRequestName = dgmRaisedRequestName;
	}
	public String getDgmIdRaisedRequest() {
		return dgmIdRaisedRequest;
	}
	public void setDgmIdRaisedRequest(String dgmIdRaisedRequest) {
		this.dgmIdRaisedRequest = dgmIdRaisedRequest;
	}
	public String getDgmApprovedId() {
		return dgmApprovedId;
	}
	public void setDgmApprovedId(String dgmApprovedId) {
		this.dgmApprovedId = dgmApprovedId;
	}
	public String getDgmApproval() {
		return dgmApproval;
	}
	public void setDgmApproval(String dgmApproval) {
		this.dgmApproval = dgmApproval;
	}
	public String getGmApprovedId() {
		return gmApprovedId;
	}
	public void setGmApprovedId(String gmApprovedId) {
		this.gmApprovedId = gmApprovedId;
	}
	public String getGmApproval() {
		return gmApproval;
	}
	public void setGmApproval(String gmApproval) {
		this.gmApproval = gmApproval;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}
	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}
	
	public String getRefundVoucherNo() {
		return refundVoucherNo;
	}
	public void setRefundVoucherNo(String refundVoucherNo) {
		this.refundVoucherNo = refundVoucherNo;
	}
	
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	
}
