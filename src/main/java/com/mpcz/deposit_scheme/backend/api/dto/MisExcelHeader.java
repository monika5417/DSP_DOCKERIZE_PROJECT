package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

import javax.persistence.Column;



import lombok.Data;

@Data
public class MisExcelHeader {
	
	


	private String distributionCenterName;
	
	private String circleName ;
	
	
	private String divisionName ;
	
	private String consumerApplicationNo ;
	
	private BigDecimal registrationCharges;
	
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


	public String getDistributionCenterName() {
		return distributionCenterName;
	}


	public void setDistributionCenterName(String distributionCenterName) {
		this.distributionCenterName = distributionCenterName;
	}


	public String getCircleName() {
		return circleName;
	}


	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}


	public String getDivisionName() {
		return divisionName;
	}


	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}


	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}


	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}


	public BigDecimal getRegistrationCharges() {
		return registrationCharges;
	}


	public void setRegistrationCharges(BigDecimal registrationCharges) {
		this.registrationCharges = registrationCharges;
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
	
	
	
}