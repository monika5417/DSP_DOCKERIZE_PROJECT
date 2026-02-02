
package com.mpcz.deposit_scheme.backend.api.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ibm.icu.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAD_DETAILS") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	
//    @Column(name = "ID")
//    private Integer id;
    
    @Id
    @Column(name = "CONSUMER_APPLICATION_NUMBER")
    private String consumerApplicationNumber;
    
    @Column(name = "ABEDAN_SULK")
    private BigDecimal abedanSulk;

    @Column(name = "APPLICATION_REJECTED_DATE")
    private String applicationRejectedDate;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "AUTH_STATUS")
    private String authStatus;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "APPLICATION_STATUS")
    private String applicationStatus;

    @Column(name = "AREA")
    private BigDecimal area;

    @Column(name = "BUILDING_TYPE")
    private String buildingType;

    @Column(name = "CARRY_AMOUNT_BY_APPLICANT")
    private BigDecimal carryAmountByApplicant;

    @Column(name = "CGST")
    private BigDecimal cgst;

    @Column(name = "COLONY_TYPE")
    private String colonyType;

    @Column(name = "CONSUMER_ID")
    private Integer consumerId;

    @Column(name = "CONSUMER_MOBILE_NO")
    private String consumerMobileNo;

    @Column(name = "CONTRACTOR_ID")
    private Long contractorId;

    @Column(name = "CONTRACTOR_STATE")
    private String contractorState;

    @Column(name = "CREATED")
    private String created;

    @Column(name = "CREATED_BY_LOGIN")
    private Integer createdByLogin;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "DATE_OF_CREATION")
    private String dateOfCreation;

    @Column(name = "DATE_OF_REGISTRATION")
    private Date dateOfRegistration;

    @Column(name = "DATE_OF_SUPERVISION_PAYMENT")
    private Date dateOfSupervisionPayment;

    @Column(name = "ESTIMATE_AMOUNT")
    private BigDecimal estimateAmount;

    @Column(name = "ESTIMATE_NAME")
    private String estimateName;

    @Column(name = "ESTIMATE_SANCTION_NO")
    private String estimateSanctionNo;

    @Column(name = "ESTIMATE_STATUS")
    private String estimateStatus;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "FATHERS_NAME")
    private String fathersName;

    @Column(name = "GOV_MAF_BILL")
    private Integer govMafBill;

    @Column(name = "GST_NUMBER")
    private String gstNumber;

    @Column(name = "INDIVIDUAL_OR_GROUPWISE_ID")
    private Long individualOrGroupwiseId;

    @Column(name = "IVRSNO")
    private String ivrsno;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Column(name = "JE_LOAD")
    private String jeLoad;

    @Column(name = "JE_LOAD_UNIT_KW_KVA")
    private String jeLoadUnitKwKva;

    @Column(name = "KHASRA")
    private String khasra;

    @Column(name = "KHATONI")
    private String khatoni;

    @Column(name = "KMY_ERP_Estimate_Number")
    private String kmyErpEstimateNumber;

    @Column(name = "LAND_AREA")
    private String landArea;

    @Column(name = "LOAD_REQUESTED")
    private Integer loadRequested;

    @Column(name = "LOAD_REQUESTED_PER_UNIT")
    private String loadRequestedPerUnit;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "MPMK_MAF_BILL")
    private BigDecimal mpmkMafBill;

    @Column(name = "NO_OF_PLOT")
    private String noOfPlot;

    @Column(name = "NAME_OF_CIRCLE")
    private String nameOfCircle;

    @Column(name = "NAME_OF_CONTRACTOR")
    private String nameOfContractor;

    @Column(name = "NAME_OF_DC")
    private String nameOfDc;

    @Column(name = "NAME_OF_DIVISION")
    private String nameOfDivision;

    @Column(name = "NAME_OF_SCHEME")
    private String nameOfScheme;

    @Column(name = "NAME_OF_APPLICANT")
    private String nameOfApplicant;

    @Column(name = "NATURE_OF_WORK")
    private String natureOfWork;

    @Column(name = "NATURE_OF_WORK_ID")
    private Long natureOfWorkLong;

    @Column(name = "PAYALE_AMOUNT")
    private BigDecimal payaleAmount;

    @Column(name = "PAYMENT_AMOUNT")
    private String paymentAmount;

    @Column(name = "REJECTED_REMARK")
    private String rejectedRemark;

    @Column(name = "REJECTION_PROPOSAL")
    private String rejectionProposal;

    @Column(name = "REJECT_APPLICATION_GM_NAME")
    private String rejectApplicationGmName;

    @Column(name = "RETURN_MATERIAL_AMOUNT")
    private BigDecimal returnMaterialAmount;

    @Column(name = "SAMAGRA_ID")
    private String samagraId;

    @Column(name = "SCHEMA")
    private String schema;

    @Column(name = "SCHEME_CODE")
    private String schemeCode;

    @Column(name = "SECURITY_DEPOSIT")
    private BigDecimal securityDeposit;

    @Column(name = "SGST")
    private BigDecimal sgst;

    @Column(name = "SHORT_DESCRIPTION_OF_WORK")
    private String shortDescriptionOfWork;

    @Column(name = "SUPPLY_VOLTAGE_ID")
    private Integer supplyVoltageId;

    @Column(name = "SUPERVISION_PAYMENT_AMOUNT")
    private String supervisionPaymentAmount;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "TRANSACTION_ERROR_DESC")
    private String transactionErrorDesc;

    @Column(name = "WORK_ALLOCATION_ADDRESS")
    private String workAllocationAddress;

    @Column(name = "WORK_COMPLETION_DATE")
    private String workCompletionDate;

    @Column(name = "WORK_COMP_DATE_BY_CONTRA")
    private String workCompDateByContra;

    @Column(name = "WORK_ORDER_GENERATE_BY")
    private String workOrderGenerateBy;

    @Column(name = "WORK_ORDER_DATE")
    private String workOrderDate;

    @Column(name = "WORK_ORDER_NO")
    private String workOrderNo;

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public BigDecimal getAbedanSulk() {
		return abedanSulk;
	}

	public void setAbedanSulk(BigDecimal abedanSulk) {
		this.abedanSulk = abedanSulk;
	}

	public String getApplicationRejectedDate() {
		return applicationRejectedDate;
	}

	public void setApplicationRejectedDate(String applicationRejectedDate) {
		this.applicationRejectedDate = applicationRejectedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public BigDecimal getCarryAmountByApplicant() {
		return carryAmountByApplicant;
	}

	public void setCarryAmountByApplicant(BigDecimal carryAmountByApplicant) {
		this.carryAmountByApplicant = carryAmountByApplicant;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public String getColonyType() {
		return colonyType;
	}

	public void setColonyType(String colonyType) {
		this.colonyType = colonyType;
	}

	public Integer getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Integer consumerId) {
		this.consumerId = consumerId;
	}

	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}

	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorState() {
		return contractorState;
	}

	public void setContractorState(String contractorState) {
		this.contractorState = contractorState;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Integer getCreatedByLogin() {
		return createdByLogin;
	}

	public void setCreatedByLogin(Integer createdByLogin) {
		this.createdByLogin = createdByLogin;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public Date getDateOfSupervisionPayment() {
		return dateOfSupervisionPayment;
	}

	public void setDateOfSupervisionPayment(Date dateOfSupervisionPayment) {
		this.dateOfSupervisionPayment = dateOfSupervisionPayment;
	}

	public BigDecimal getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(BigDecimal estimateAmount) {
		this.estimateAmount = estimateAmount;
	}

	public String getEstimateName() {
		return estimateName;
	}

	public void setEstimateName(String estimateName) {
		this.estimateName = estimateName;
	}

	public String getEstimateSanctionNo() {
		return estimateSanctionNo;
	}

	public void setEstimateSanctionNo(String estimateSanctionNo) {
		this.estimateSanctionNo = estimateSanctionNo;
	}

	public String getEstimateStatus() {
		return estimateStatus;
	}

	public void setEstimateStatus(String estimateStatus) {
		this.estimateStatus = estimateStatus;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public Integer getGovMafBill() {
		return govMafBill;
	}

	public void setGovMafBill(Integer govMafBill) {
		this.govMafBill = govMafBill;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public Long getIndividualOrGroupwiseId() {
		return individualOrGroupwiseId;
	}

	public void setIndividualOrGroupwiseId(Long individualOrGroupwiseId) {
		this.individualOrGroupwiseId = individualOrGroupwiseId;
	}

	public String getIvrsno() {
		return ivrsno;
	}

	public void setIvrsno(String ivrsno) {
		this.ivrsno = ivrsno;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getJeLoad() {
		return jeLoad;
	}

	public void setJeLoad(String jeLoad) {
		this.jeLoad = jeLoad;
	}

	public String getJeLoadUnitKwKva() {
		return jeLoadUnitKwKva;
	}

	public void setJeLoadUnitKwKva(String jeLoadUnitKwKva) {
		this.jeLoadUnitKwKva = jeLoadUnitKwKva;
	}

	public String getKhasra() {
		return khasra;
	}

	public void setKhasra(String khasra) {
		this.khasra = khasra;
	}

	public String getKhatoni() {
		return khatoni;
	}

	public void setKhatoni(String khatoni) {
		this.khatoni = khatoni;
	}

	public String getKmyErpEstimateNumber() {
		return kmyErpEstimateNumber;
	}

	public void setKmyErpEstimateNumber(String kmyErpEstimateNumber) {
		this.kmyErpEstimateNumber = kmyErpEstimateNumber;
	}

	public String getLandArea() {
		return landArea;
	}

	public void setLandArea(String landArea) {
		this.landArea = landArea;
	}

	public Integer getLoadRequested() {
		return loadRequested;
	}

	public void setLoadRequested(Integer loadRequested) {
		this.loadRequested = loadRequested;
	}

	public String getLoadRequestedPerUnit() {
		return loadRequestedPerUnit;
	}

	public void setLoadRequestedPerUnit(String loadRequestedPerUnit) {
		this.loadRequestedPerUnit = loadRequestedPerUnit;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getMpmkMafBill() {
		return mpmkMafBill;
	}

	public void setMpmkMafBill(BigDecimal mpmkMafBill) {
		this.mpmkMafBill = mpmkMafBill;
	}

	public String getNoOfPlot() {
		return noOfPlot;
	}

	public void setNoOfPlot(String noOfPlot) {
		this.noOfPlot = noOfPlot;
	}

	public String getNameOfCircle() {
		return nameOfCircle;
	}

	public void setNameOfCircle(String nameOfCircle) {
		this.nameOfCircle = nameOfCircle;
	}

	public String getNameOfContractor() {
		return nameOfContractor;
	}

	public void setNameOfContractor(String nameOfContractor) {
		this.nameOfContractor = nameOfContractor;
	}

	public String getNameOfDc() {
		return nameOfDc;
	}

	public void setNameOfDc(String nameOfDc) {
		this.nameOfDc = nameOfDc;
	}

	public String getNameOfDivision() {
		return nameOfDivision;
	}

	public void setNameOfDivision(String nameOfDivision) {
		this.nameOfDivision = nameOfDivision;
	}

	public String getNameOfScheme() {
		return nameOfScheme;
	}

	public void setNameOfScheme(String nameOfScheme) {
		this.nameOfScheme = nameOfScheme;
	}

	public String getNameOfApplicant() {
		return nameOfApplicant;
	}

	public void setNameOfApplicant(String nameOfApplicant) {
		this.nameOfApplicant = nameOfApplicant;
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public Long getNatureOfWorkLong() {
		return natureOfWorkLong;
	}

	public void setNatureOfWorkLong(Long natureOfWorkLong) {
		this.natureOfWorkLong = natureOfWorkLong;
	}

	public BigDecimal getPayaleAmount() {
		return payaleAmount;
	}

	public void setPayaleAmount(BigDecimal payaleAmount) {
		this.payaleAmount = payaleAmount;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getRejectedRemark() {
		return rejectedRemark;
	}

	public void setRejectedRemark(String rejectedRemark) {
		this.rejectedRemark = rejectedRemark;
	}

	public String getRejectionProposal() {
		return rejectionProposal;
	}

	public void setRejectionProposal(String rejectionProposal) {
		this.rejectionProposal = rejectionProposal;
	}

	public String getRejectApplicationGmName() {
		return rejectApplicationGmName;
	}

	public void setRejectApplicationGmName(String rejectApplicationGmName) {
		this.rejectApplicationGmName = rejectApplicationGmName;
	}

	public BigDecimal getReturnMaterialAmount() {
		return returnMaterialAmount;
	}

	public void setReturnMaterialAmount(BigDecimal returnMaterialAmount) {
		this.returnMaterialAmount = returnMaterialAmount;
	}

	public String getSamagraId() {
		return samagraId;
	}

	public void setSamagraId(String samagraId) {
		this.samagraId = samagraId;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public BigDecimal getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(BigDecimal securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public String getShortDescriptionOfWork() {
		return shortDescriptionOfWork;
	}

	public void setShortDescriptionOfWork(String shortDescriptionOfWork) {
		this.shortDescriptionOfWork = shortDescriptionOfWork;
	}

	public Integer getSupplyVoltageId() {
		return supplyVoltageId;
	}

	public void setSupplyVoltageId(Integer supplyVoltageId) {
		this.supplyVoltageId = supplyVoltageId;
	}

	public String getSupervisionPaymentAmount() {
		return supervisionPaymentAmount;
	}

	public void setSupervisionPaymentAmount(String supervisionPaymentAmount) {
		this.supervisionPaymentAmount = supervisionPaymentAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTransactionErrorDesc() {
		return transactionErrorDesc;
	}

	public void setTransactionErrorDesc(String transactionErrorDesc) {
		this.transactionErrorDesc = transactionErrorDesc;
	}

	public String getWorkAllocationAddress() {
		return workAllocationAddress;
	}

	public void setWorkAllocationAddress(String workAllocationAddress) {
		this.workAllocationAddress = workAllocationAddress;
	}

	public String getWorkCompletionDate() {
		return workCompletionDate;
	}

	public void setWorkCompletionDate(String workCompletionDate) {
		this.workCompletionDate = workCompletionDate;
	}

	public String getWorkCompDateByContra() {
		return workCompDateByContra;
	}

	public void setWorkCompDateByContra(String workCompDateByContra) {
		this.workCompDateByContra = workCompDateByContra;
	}

	public String getWorkOrderGenerateBy() {
		return workOrderGenerateBy;
	}

	public void setWorkOrderGenerateBy(String workOrderGenerateBy) {
		this.workOrderGenerateBy = workOrderGenerateBy;
	}

	public String getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(String workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	
  

}
