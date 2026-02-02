package com.mpcz.deposit_scheme.backend.api.request;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Demand Model Form .")
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class DemandTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Select valid Consumer Application Id", required = true)
	@NotEmpty(message = "Please select Consumer Application Id")
	private Long consumerApplicationId;

	@ApiModelProperty(notes = "consumer Amount cannot be null", required = true)
	@NotEmpty(message = "Please enter Amount")
	private BigDecimal demandAmount;

	private Date demandGenerationDate;

//	sandeep, start

	@ApiModelProperty(notes = "7% Supervision Charges on Cost of Material (Excluding Service Tax Amount) cannot be null", required = true)
	@NotEmpty(message = "Please enter 7% Supervision Charges on Cost of Material (Excluding Service Tax Amount)")
	private BigDecimal supervisionChargesOnCostOfMaterial;

	@ApiModelProperty(notes = "CGST-9% cannot be null", required = true)
	@NotEmpty(message = "Please enter CGST-9%")
	private BigDecimal cgst;

	@ApiModelProperty(notes = "SGST-9% cannot be null", required = true)
	@NotEmpty(message = "Please enter SGST-9%")
	private BigDecimal sgst;

	@ApiModelProperty(notes = "Krishi Cess @0.5% cannot be null", required = true)
	@NotEmpty(message = "Please enter Krishi Cess @0.5%")
	private BigDecimal krishiCess;

	@ApiModelProperty(notes = "Education Cess (As per applicable rate) cannot be null", required = true)
	@NotEmpty(message = "Please enter Education Cess (As per applicable rate)")
	private BigDecimal educationCess;

	@ApiModelProperty(notes = "Secondary Higher Edu.Cess (As per applicable rate) cannot be null", required = true)
	@NotEmpty(message = "Please enter Secondary Higher Edu.Cess (As per applicable rate)")
	private BigDecimal eecondaryHigherEduCess;

	@ApiModelProperty(notes = "System Development Charges@ 500/- Per KW for KW Load cannot be null", required = true)
	@NotEmpty(message = "Please enter System Development Charges@ 500/- Per KW for KW Load")
	private BigDecimal systemDevelopmentCharges;

	@ApiModelProperty(notes = "Cost of Estimate for 100% Deposit Work (CE) BR cannot be null", required = true)
	@NotEmpty(message = "Please enter Cost of Estimate for 100% Deposit Work (CE) BR")
	private BigDecimal costOfEstimate;

	@ApiModelProperty(notes = "Other Infrastructure related cost cannot be null", required = true)
	@NotEmpty(message = "Please enter Other Infrastructure related cost")
	private BigDecimal otherInfraStrucRelatedCost;

	@ApiModelProperty(notes = "Supply Affording Charges for 281.25 KVA @850 Per KVA cannot be null", required = true)
	@NotEmpty(message = "Please enter Supply Affording Charges for 281.25 KVA @850 Per KVA")
	private BigDecimal supplyAffordingCharges;

//	sandeep, end
	
	
	

	public static DemandTypeForm stringToJson(String demandForm) {

		DemandTypeForm demandDetailJson = new DemandTypeForm();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			demandDetailJson = objectMapper.readValue(demandForm, DemandTypeForm.class);
		} catch (IOException err) {
			System.out.printf("Error", err.toString());
		}

		return demandDetailJson;

	}

	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}

	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}

	public BigDecimal getDemandAmount() {
		return demandAmount;
	}

	public void setDemandAmount(BigDecimal demandAmount) {
		this.demandAmount = demandAmount;
	}

	public Date getDemandGenerationDate() {
		return demandGenerationDate;
	}

	public void setDemandGenerationDate(Date demandGenerationDate) {
		this.demandGenerationDate = demandGenerationDate;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
