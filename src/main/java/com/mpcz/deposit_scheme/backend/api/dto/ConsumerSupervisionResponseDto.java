package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import lombok.Data;

@Data
public class ConsumerSupervisionResponseDto {

	private static final long serialVersionUID = 1L;
	private String consumerApplicationNo;
	private String consumerName;

	private String shortDescriptionOfWork;

	private String feederName;

	private String feederCode;

	private String subStationName;

	private String subStationCode;

	private String dcName;

	private String dcCode;

	private String subDivisionName;
	private String subDivisionCode;
	private String divisionName;

	private String divisionCode;

	private String circle;
	private String circleCode;
	private String region;
	private String regionCode;
	private String discomName;
	private String discomCode;
	private String schemeType;
	private String address;

	private Double area;
	private String districtName;

//	private String billNo;
//
//	private Long dtrCode;
//
//	private String poleNo;
//
//	private ContractorDetail contractorId;
//
//	private String contractorName;
//
//	private Long contractorUserId;

	private String pincode;
	private String natureOfWorkName;

	private BigDecimal demandRs;

	private Date demandGenerationDate;

	private String demandStatus;

	private Date approvedDate;

	private Boolean isDemandRejected;

	private String rejectionRemark;

	private String chargesType;

	private BigDecimal supervisionChargesOnCostOfMaterial;

	private BigDecimal cgst;

	private BigDecimal sgst;

	private BigDecimal krishiCess;

	private BigDecimal educationCess;

	private BigDecimal eecondaryHigherEduCess;
	private BigDecimal systemDevelopmentCharges;

	private BigDecimal costOfEstimate;
	private BigDecimal otherInfraStrucRelatedCost;

	private BigDecimal supplyAffordingCharges;
	private String natureOfWork;

	private Long consumerId;

	private Long demandId;

	private Long consumerApplicationId;

	private String requestTime;

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getShortDescriptionOfWork() {
		return shortDescriptionOfWork;
	}

	public void setShortDescriptionOfWork(String shortDescriptionOfWork) {
		this.shortDescriptionOfWork = shortDescriptionOfWork;
	}

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public String getFeederCode() {
		return feederCode;
	}

	public void setFeederCode(String feederCode) {
		this.feederCode = feederCode;
	}

	public String getSubStationName() {
		return subStationName;
	}

	public void setSubStationName(String subStationName) {
		this.subStationName = subStationName;
	}

	public String getSubStationCode() {
		return subStationCode;
	}

	public void setSubStationCode(String subStationCode) {
		this.subStationCode = subStationCode;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

	public String getDcCode() {
		return dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}

	public String getSubDivisionName() {
		return subDivisionName;
	}

	public void setSubDivisionName(String subDivisionName) {
		this.subDivisionName = subDivisionName;
	}

	public String getSubDivisionCode() {
		return subDivisionCode;
	}

	public void setSubDivisionCode(String subDivisionCode) {
		this.subDivisionCode = subDivisionCode;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getDiscomName() {
		return discomName;
	}

	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}

	public String getDiscomCode() {
		return discomCode;
	}

	public void setDiscomCode(String discomCode) {
		this.discomCode = discomCode;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getNatureOfWorkName() {
		return natureOfWorkName;
	}

	public void setNatureOfWorkName(String natureOfWorkName) {
		this.natureOfWorkName = natureOfWorkName;
	}

	public BigDecimal getDemandRs() {
		return demandRs;
	}

	public void setDemandRs(BigDecimal demandRs) {
		this.demandRs = demandRs;
	}

	public Date getDemandGenerationDate() {
		return demandGenerationDate;
	}

	public void setDemandGenerationDate(Date demandGenerationDate) {
		this.demandGenerationDate = demandGenerationDate;
	}

	public String getDemandStatus() {
		return demandStatus;
	}

	public void setDemandStatus(String demandStatus) {
		this.demandStatus = demandStatus;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Boolean getIsDemandRejected() {
		return isDemandRejected;
	}

	public void setIsDemandRejected(Boolean isDemandRejected) {
		this.isDemandRejected = isDemandRejected;
	}

	public String getRejectionRemark() {
		return rejectionRemark;
	}

	public void setRejectionRemark(String rejectionRemark) {
		this.rejectionRemark = rejectionRemark;
	}

	public String getChargesType() {
		return chargesType;
	}

	public void setChargesType(String chargesType) {
		this.chargesType = chargesType;
	}

	public BigDecimal getSupervisionChargesOnCostOfMaterial() {
		return supervisionChargesOnCostOfMaterial;
	}

	public void setSupervisionChargesOnCostOfMaterial(BigDecimal supervisionChargesOnCostOfMaterial) {
		this.supervisionChargesOnCostOfMaterial = supervisionChargesOnCostOfMaterial;
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

	public BigDecimal getKrishiCess() {
		return krishiCess;
	}

	public void setKrishiCess(BigDecimal krishiCess) {
		this.krishiCess = krishiCess;
	}

	public BigDecimal getEducationCess() {
		return educationCess;
	}

	public void setEducationCess(BigDecimal educationCess) {
		this.educationCess = educationCess;
	}

	public BigDecimal getEecondaryHigherEduCess() {
		return eecondaryHigherEduCess;
	}

	public void setEecondaryHigherEduCess(BigDecimal eecondaryHigherEduCess) {
		this.eecondaryHigherEduCess = eecondaryHigherEduCess;
	}

	public BigDecimal getSystemDevelopmentCharges() {
		return systemDevelopmentCharges;
	}

	public void setSystemDevelopmentCharges(BigDecimal systemDevelopmentCharges) {
		this.systemDevelopmentCharges = systemDevelopmentCharges;
	}

	public BigDecimal getCostOfEstimate() {
		return costOfEstimate;
	}

	public void setCostOfEstimate(BigDecimal costOfEstimate) {
		this.costOfEstimate = costOfEstimate;
	}

	public BigDecimal getOtherInfraStrucRelatedCost() {
		return otherInfraStrucRelatedCost;
	}

	public void setOtherInfraStrucRelatedCost(BigDecimal otherInfraStrucRelatedCost) {
		this.otherInfraStrucRelatedCost = otherInfraStrucRelatedCost;
	}

	public BigDecimal getSupplyAffordingCharges() {
		return supplyAffordingCharges;
	}

	public void setSupplyAffordingCharges(BigDecimal supplyAffordingCharges) {
		this.supplyAffordingCharges = supplyAffordingCharges;
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public Long getDemandId() {
		return demandId;
	}

	public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}

	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}

	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
