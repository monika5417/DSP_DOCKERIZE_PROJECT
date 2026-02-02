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

@Entity
@Table(name = "ERP_REV")
public class ErpRev extends Auditable<Long> {

	@Id
	@SequenceGenerator(name = "ERPREV_SEQ", sequenceName = "ERPREV_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERPREV_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;

	@Column(name = "ERP_NO", length = 20)
	private String newErpNo;

	@Column(name = "CONS_APP_NO", length = 20)
	private String consAppNo;

	@Column(name = "SUP_AMT")
	private BigDecimal newSupervisionAmt;

	@Column(name = "EST_AMT")
	private BigDecimal newEstimateAmt;

	@Column(name = "CGST")
	private BigDecimal newCgst;

	@Column(name = "SGST")
	private BigDecimal newSgst;

	@Column(name = "KW_AMT")
	private BigDecimal newKwAmt;

	// New KVA Amount
	@Column(name = "KVA_AMT")
	private BigDecimal newKvaAmt;

	@Column(name = "EST_SEC_NO", length = 20)
	private String EstimatsenctionNo;

	@Column(name = "SCHEME_CODE", length = 20)
	private String schemeCode;

	@Column(name = "ESTI_NAME", length = 20)
	private String estimateName;

	@Column(name = "ESTI_DATE", length = 20)
	private String estimateDate;

	@Column(name = "REM_SUP_AMT")
	private BigDecimal remSupervisionAmt;

	@Column(name = "REM_EST_AMT")
	private BigDecimal remEstimateAmt;

	@Column(name = "REM_CGST")
	private BigDecimal remCgst;

	@Column(name = "REM_SGST")
	private BigDecimal remSgst;

	@Column(name = "REM_KW_AMT")
	private BigDecimal remKwAmt;

	@Column(name = "REM_KVA_AMT")
	private BigDecimal remKvaAmt;

	// Is key ka data billdesk k paas jata h
	@Column(name = "PAY_AMT")
	private BigDecimal payAmt;

	@Column(name = "Location")
	private String location;

	@Column(name = "EST_STATUS")
	private String estimateStatus;

	@Column(name = "APPROVED_BY")
	private String approvedBy;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "DEPOSIT_AMT")
	private BigDecimal newDepositAmt;

	@Column(name = "REM_DEPOSIT_AMT")
	private BigDecimal remmDepositAmt;

	@Column(name = "GOV_MAF_AMT")
	private BigDecimal govMafAmt;

	@Column(name = "MPMK_MAF_AMT")
	private BigDecimal mpmkMafAmt;

	@Column(name = "MKMY_PAY_AMT")
	private BigDecimal mkmyPayAmt;

	@Column(name = "REM_GOV_MAF_AMT")
	private BigDecimal remGovMafAmt;

	@Column(name = "REM_MPMK_MAF_AMT")
	private BigDecimal remMpmkMafAmt;

	@Column(name = "REM_MKMY_PAY_AMT")
	private BigDecimal remMkmyPayAmt;

	@Column(name = "NEW_PAY_AMT")
	private BigDecimal newPayAmt;

	@Column(name = "NEW_COLONY_SLUM")
	private BigDecimal newColonyOrSlum;

	@Column(name = "REM_COLONY_SLUM")
	private BigDecimal remColonyOrSlum;

	@Column(name = "REM_RETURN_AMT")
	private BigDecimal remReturnAmt;
	
	@Column(name = "DISMENTALLING_COST")
	private BigDecimal dismentalingCost;
	
	

	@Transient
	private BigDecimal oAndMReturnAmt;

	@Transient
	private BigDecimal oldGovMafAmt;

	@Transient
	private BigDecimal oldMpmkMafAmt;

	@Transient
	private BigDecimal oldMkmyPayAmt;

	@Transient
	private BigDecimal oldSupervision;

	@Transient
	private BigDecimal oldEstimate;

	@Transient
	private BigDecimal oldCgst;

	@Transient
	private BigDecimal oldSgst;

	@Transient
	private BigDecimal oldDeposit;

	@Transient
	private BigDecimal oldJeReturnAmt;

	@Transient
	private BigDecimal oldColonyOrSlum; //

	@Transient
	private BigDecimal oldAvedanShulk;

	@Transient
	private BigDecimal oldMkmySecurityDeposit;

	@Transient
	private BigDecimal oldCarryAmt; // Old 50 % carry amount of mkmy

	@Transient
	private BigDecimal oldPayableAmt;

	@Transient
	private BigDecimal oldkvaloadAmt; // SUPPly affording charges

	@Transient
	private BigDecimal oldkWloadAmt; // system development chargers

	@Transient
	private BigDecimal refundReviseDemandAmnt;
	@Transient
	private BigDecimal demandRefundAmnt;
	@Transient
	private BigDecimal reviseRefundAmnt;

	
	@Column(name="ERP_VERSION")
	private String erpVersion;

	public String getErpVersion() {
		return erpVersion;
	}

	public void setErpVersion(String erpVersion) {
		this.erpVersion = erpVersion;
	}

	// added this minus cost on 27- august - 2024
	@Column(name = "NEW_MINUS_COST")
	private BigDecimal newMinusCost;
	
	
	

//    17-October-2024
	@Column(name = "CON_REFUNDABLE_AMNT")
	private BigDecimal consumerRefundableAmnt;

	public BigDecimal getConsumerRefundableAmnt() {
		return consumerRefundableAmnt;
	}

	public void setConsumerRefundableAmnt(BigDecimal consumerRefundableAmnt) {
		this.consumerRefundableAmnt = consumerRefundableAmnt;
	}

	public BigDecimal getNewMinusCost() {
		return newMinusCost;
	}

	public void setNewMinusCost(BigDecimal newMinusCost) {
		this.newMinusCost = newMinusCost;
	}

	public BigDecimal getRefundReviseDemandAmnt() {
		return refundReviseDemandAmnt;
	}

	public void setRefundReviseDemandAmnt(BigDecimal refundReviseDemandAmnt) {
		this.refundReviseDemandAmnt = refundReviseDemandAmnt;
	}

	public BigDecimal getDemandRefundAmnt() {
		return demandRefundAmnt;
	}

	public void setDemandRefundAmnt(BigDecimal demandRefundAmnt) {
		this.demandRefundAmnt = demandRefundAmnt;
	}

	public BigDecimal getReviseRefundAmnt() {
		return reviseRefundAmnt;
	}

	public void setReviseRefundAmnt(BigDecimal reviseRefundAmnt) {
		this.reviseRefundAmnt = reviseRefundAmnt;
	}

	public BigDecimal getRemReturnAmt() {
		return remReturnAmt;
	}

	public void setRemReturnAmt(BigDecimal remReturnAmt) {
		this.remReturnAmt = remReturnAmt;
	}

	public BigDecimal getoAndMReturnAmt() {
		return oAndMReturnAmt;
	}

	public void setoAndMReturnAmt(BigDecimal oAndMReturnAmt) {
		this.oAndMReturnAmt = oAndMReturnAmt;
	}

	public BigDecimal getRemColonyOrSlum() {
		return remColonyOrSlum;
	}

	public void setRemColonyOrSlum(BigDecimal remColonyOrSlum) {
		this.remColonyOrSlum = remColonyOrSlum;
	}

	public BigDecimal getNewColonyOrSlum() {
		return newColonyOrSlum;
	}

	public void setNewColonyOrSlum(BigDecimal newColonyOrSlum) {
		this.newColonyOrSlum = newColonyOrSlum;
	}

	public BigDecimal getOldCarryAmt() {
		return oldCarryAmt;
	}

	public void setOldCarryAmt(BigDecimal oldCarryAmt) {
		this.oldCarryAmt = oldCarryAmt;
	}

	public BigDecimal getNewPayAmt() {
		return newPayAmt;
	}

	public void setNewPayAmt(BigDecimal newPayAmt) {
		this.newPayAmt = newPayAmt;
	}

	public BigDecimal getOldPayableAmt() {
		return oldPayableAmt;
	}

	public void setOldPayableAmt(BigDecimal oldPayableAmt) {
		this.oldPayableAmt = oldPayableAmt;
	}

	public BigDecimal getOldJeReturnAmt() {
		return oldJeReturnAmt;
	}

	public void setOldJeReturnAmt(BigDecimal oldJeReturnAmt) {
		this.oldJeReturnAmt = oldJeReturnAmt;
	}

	public BigDecimal getOldColonyOrSlum() {
		return oldColonyOrSlum;
	}

	public void setOldColonyOrSlum(BigDecimal oldColonyOrSlum) {
		this.oldColonyOrSlum = oldColonyOrSlum;
	}

	public BigDecimal getOldAvedanShulk() {
		return oldAvedanShulk;
	}

	public void setOldAvedanShulk(BigDecimal oldAvedanShulk) {
		this.oldAvedanShulk = oldAvedanShulk;
	}

	public BigDecimal getOldMkmySecurityDeposit() {
		return oldMkmySecurityDeposit;
	}

	public void setOldMkmySecurityDeposit(BigDecimal oldMkmySecurityDeposit) {
		this.oldMkmySecurityDeposit = oldMkmySecurityDeposit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsAppNo() {
		return consAppNo;
	}

	public void setConsAppNo(String consAppNo) {
		this.consAppNo = consAppNo;
	}

	public BigDecimal getNewCgst() {
		return newCgst;
	}

	public void setNewCgst(BigDecimal newCgst) {
		this.newCgst = newCgst;
	}

	public BigDecimal getNewSgst() {
		return newSgst;
	}

	public void setNewSgst(BigDecimal newSgst) {
		this.newSgst = newSgst;
	}

	public String getEstimatsenctionNo() {
		return EstimatsenctionNo;
	}

	public void setEstimatsenctionNo(String estimatsenctionNo) {
		EstimatsenctionNo = estimatsenctionNo;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getEstimateName() {
		return estimateName;
	}

	public void setEstimateName(String estimateName) {
		this.estimateName = estimateName;
	}

	public String getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(String estimateDate) {
		this.estimateDate = estimateDate;
	}

	public BigDecimal getRemCgst() {
		return remCgst;
	}

	public void setRemCgst(BigDecimal remCgst) {
		this.remCgst = remCgst;
	}

	public BigDecimal getRemSgst() {
		return remSgst;
	}

	public void setRemSgst(BigDecimal remSgst) {
		this.remSgst = remSgst;
	}

	public BigDecimal getRemKwAmt() {
		return remKwAmt;
	}

	public void setRemKwAmt(BigDecimal remKwAmt) {
		this.remKwAmt = remKwAmt;
	}

	public BigDecimal getRemKvaAmt() {
		return remKvaAmt;
	}

	public void setRemKvaAmt(BigDecimal remKvaAmt) {
		this.remKvaAmt = remKvaAmt;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEstimateStatus() {
		return estimateStatus;
	}

	public void setEstimateStatus(String estimateStatus) {
		this.estimateStatus = estimateStatus;
	}

	public BigDecimal getNewSupervisionAmt() {
		return newSupervisionAmt;
	}

	public void setNewSupervisionAmt(BigDecimal newSupervisionAmt) {
		this.newSupervisionAmt = newSupervisionAmt;
	}

	public BigDecimal getNewEstimateAmt() {
		return newEstimateAmt;
	}

	public void setNewEstimateAmt(BigDecimal newEstimateAmt) {
		this.newEstimateAmt = newEstimateAmt;
	}

	public BigDecimal getRemSupervisionAmt() {
		return remSupervisionAmt;
	}

	public void setRemSupervisionAmt(BigDecimal remSupervisionAmt) {
		this.remSupervisionAmt = remSupervisionAmt;
	}

	public BigDecimal getRemEstimateAmt() {
		return remEstimateAmt;
	}

	public void setRemEstimateAmt(BigDecimal remEstimateAmt) {
		this.remEstimateAmt = remEstimateAmt;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public BigDecimal getGovMafAmt() {
		return govMafAmt;
	}

	public void setGovMafAmt(BigDecimal govMafAmt) {
		this.govMafAmt = govMafAmt;
	}

	public BigDecimal getMpmkMafAmt() {
		return mpmkMafAmt;
	}

	public void setMpmkMafAmt(BigDecimal mpmkMafAmt) {
		this.mpmkMafAmt = mpmkMafAmt;
	}

	public BigDecimal getRemGovMafAmt() {
		return remGovMafAmt;
	}

	public void setRemGovMafAmt(BigDecimal remGovMafAmt) {
		this.remGovMafAmt = remGovMafAmt;
	}

	public BigDecimal getRemMpmkMafAmt() {
		return remMpmkMafAmt;
	}

	public void setRemMpmkMafAmt(BigDecimal remMpmkMafAmt) {
		this.remMpmkMafAmt = remMpmkMafAmt;
	}

	public BigDecimal getRemMkmyPayAmt() {
		return remMkmyPayAmt;
	}

	public void setRemMkmyPayAmt(BigDecimal remMkmyPayAmt) {
		this.remMkmyPayAmt = remMkmyPayAmt;
	}

	public BigDecimal getMkmyPayAmt() {
		return mkmyPayAmt;
	}

	public void setMkmyPayAmt(BigDecimal mkmyPayAmt) {
		this.mkmyPayAmt = mkmyPayAmt;
	}

	public BigDecimal getRemmDepositAmt() {
		return remmDepositAmt;
	}

	public void setRemmDepositAmt(BigDecimal remmDepositAmt) {
		this.remmDepositAmt = remmDepositAmt;
	}

	public BigDecimal getOldGovMafAmt() {
		return oldGovMafAmt;
	}

	public void setOldGovMafAmt(BigDecimal oldGovMafAmt) {
		this.oldGovMafAmt = oldGovMafAmt;
	}

	public BigDecimal getOldMpmkMafAmt() {
		return oldMpmkMafAmt;
	}

	public void setOldMpmkMafAmt(BigDecimal oldMpmkMafAmt) {
		this.oldMpmkMafAmt = oldMpmkMafAmt;
	}

	public BigDecimal getOldMkmyPayAmt() {
		return oldMkmyPayAmt;
	}

	public void setOldMkmyPayAmt(BigDecimal oldMkmyPayAmt) {
		this.oldMkmyPayAmt = oldMkmyPayAmt;
	}

	public BigDecimal getOldSupervision() {
		return oldSupervision;
	}

	public void setOldSupervision(BigDecimal oldSupervision) {
		this.oldSupervision = oldSupervision;
	}

	public BigDecimal getOldEstimate() {
		return oldEstimate;
	}

	public void setOldEstimate(BigDecimal oldEstimate) {
		this.oldEstimate = oldEstimate;
	}

	public BigDecimal getOldCgst() {
		return oldCgst;
	}

	public void setOldCgst(BigDecimal oldCgst) {
		this.oldCgst = oldCgst;
	}

	public BigDecimal getOldSgst() {
		return oldSgst;
	}

	public void setOldSgst(BigDecimal oldSgst) {
		this.oldSgst = oldSgst;
	}

	public BigDecimal getOldDeposit() {
		return oldDeposit;
	}

	public void setOldDeposit(BigDecimal oldDeposit) {
		this.oldDeposit = oldDeposit;
	}

	public BigDecimal getOldkvaloadAmt() {
		return oldkvaloadAmt;
	}

	public void setOldkvaloadAmt(BigDecimal oldkvaloadAmt) {
		this.oldkvaloadAmt = oldkvaloadAmt;
	}

	public BigDecimal getOldkWloadAmt() {
		return oldkWloadAmt;
	}

	public void setOldkWloadAmt(BigDecimal oldkWloadAmt) {
		this.oldkWloadAmt = oldkWloadAmt;
	}

	public String getNewErpNo() {
		return newErpNo;
	}

	public void setNewErpNo(String newErpNo) {
		this.newErpNo = newErpNo;
	}

	public BigDecimal getNewKwAmt() {
		return newKwAmt;
	}

	public void setNewKwAmt(BigDecimal newKwAmt) {
		this.newKwAmt = newKwAmt;
	}

	public BigDecimal getNewKvaAmt() {
		return newKvaAmt;
	}

	public void setNewKvaAmt(BigDecimal newKvaAmt) {
		this.newKvaAmt = newKvaAmt;
	}

	public BigDecimal getNewDepositAmt() {
		return newDepositAmt;
	}

	public void setNewDepositAmt(BigDecimal newDepositAmt) {
		this.newDepositAmt = newDepositAmt;
	}

	@Column(name = "NEW_SECURITY_AMT")
	private BigDecimal newSecurityAmt;

	@Column(name = "OLD_SECURITY_AMT")
	private BigDecimal oldSecurityAmt;

	@Column(name = "REM_SECURITY_AMT")
	private BigDecimal remSecurityAmt;

	public BigDecimal getNewSecurityAmt() {
		return newSecurityAmt;
	}

	public void setNewSecurityAmt(BigDecimal newSecurityAmt) {
		this.newSecurityAmt = newSecurityAmt;
	}

	public BigDecimal getOldSecurityAmt() {
		return oldSecurityAmt;
	}

	public void setOldSecurityAmt(BigDecimal oldSecurityAmt) {
		this.oldSecurityAmt = oldSecurityAmt;
	}

	public BigDecimal getRemSecurityAmt() {
		return remSecurityAmt;
	}

	public void setRemSecurityAmt(BigDecimal remSecurityAmt) {
		this.remSecurityAmt = remSecurityAmt;
	}

	// Getters and setters for all fields ...

//	SSP_REG_CHARGE
//	SSP_METER_COST
//	SSP_TOTAL_AMOUNT

	@Transient
	private BigDecimal oldSspRegCharge;
	@Transient
	private BigDecimal oldSspMeterCost;
	@Transient
	private BigDecimal oldSspTotalAmount;

	@Column(name = "REM_SSP_REG_CHARGE")
	private BigDecimal remSspRegCharge;

	@Column(name = "REM_SSP_METER_COST")
	private BigDecimal remSspMeterCost;

	@Column(name = "REM_SSP_TOTAL_AMOUNT")
	private BigDecimal remSspTotalAmount;

	@Column(name = "NEW_SSP_REG_CHARGE")
	private BigDecimal newSspRegCharge;

	@Column(name = "NEW_SSP_METER_COST")
	private BigDecimal newSspMeterCost;

	@Column(name = "NEW_SSP_TOTAL_AMOUNT")
	private BigDecimal newSspTotalAmount;

	public BigDecimal getOldSspRegCharge() {
		return oldSspRegCharge;
	}

	public void setOldSspRegCharge(BigDecimal oldSspRegCharge) {
		this.oldSspRegCharge = oldSspRegCharge;
	}

	public BigDecimal getOldSspMeterCost() {
		return oldSspMeterCost;
	}

	public void setOldSspMeterCost(BigDecimal oldSspMeterCost) {
		this.oldSspMeterCost = oldSspMeterCost;
	}

	public BigDecimal getRemSspRegCharge() {
		return remSspRegCharge;
	}

	public void setRemSspRegCharge(BigDecimal remSspRegCharge) {
		this.remSspRegCharge = remSspRegCharge;
	}

	public BigDecimal getRemSspMeterCost() {
		return remSspMeterCost;
	}

	public void setRemSspMeterCost(BigDecimal remSspMeterCost) {
		this.remSspMeterCost = remSspMeterCost;
	}

	public BigDecimal getNewSspRegCharge() {
		return newSspRegCharge;
	}

	public void setNewSspRegCharge(BigDecimal newSspRegCharge) {
		this.newSspRegCharge = newSspRegCharge;
	}

	public BigDecimal getNewSspMeterCost() {
		return newSspMeterCost;
	}

	public void setNewSspMeterCost(BigDecimal newSspMeterCost) {
		this.newSspMeterCost = newSspMeterCost;
	}

	public BigDecimal getNewSspTotalAmount() {
		return newSspTotalAmount;
	}

	public void setNewSspTotalAmount(BigDecimal newSspTotalAmount) {
		this.newSspTotalAmount = newSspTotalAmount;
	}

	public BigDecimal getOldSspTotalAmount() {
		return oldSspTotalAmount;
	}

	public void setOldSspTotalAmount(BigDecimal oldSspTotalAmount) {
		this.oldSspTotalAmount = oldSspTotalAmount;
	}

	public BigDecimal getRemSspTotalAmount() {
		return remSspTotalAmount;
	}

	public void setRemSspTotalAmount(BigDecimal remSspTotalAmount) {
		this.remSspTotalAmount = remSspTotalAmount;
	}

	public BigDecimal getDismentalingCost() {
		return dismentalingCost;
	}

	public void setDismentalingCost(BigDecimal dismentalingCost) {
		this.dismentalingCost = dismentalingCost;
	}

	
}