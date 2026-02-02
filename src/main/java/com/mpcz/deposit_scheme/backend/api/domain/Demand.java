package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "Demand")
@Table(name = "DEMAND")
public class Demand extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DEMAND_SEQ", sequenceName = "DEMAND_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMAND_SEQ")
	@Column(name = "DEMAND_ID", columnDefinition = "serial", updatable = false)
	private Long demandId;

	@Column(name = "DEMAND_IN_RS")
	private BigDecimal demandRs;

	@Column(name = "DEMAND_GENERATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date demandGenerationDate;

	@Column(name = "DEMAND_STATUS")
	private String demandStatus;

	@Column(name = "APPROVED_DATE")
	@Temporal(TemporalType.DATE)
	private Date approvedDate;

	@Column(name = "IS_DEMAND_REJECTED")
	private Boolean isDemandRejected;

	@Column(name = "REJECTION_REMARK")
	private String rejectionRemark;

	@Column(name = "CHARGES_TYPE")
	private String chargesType;

	@ManyToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumersApplicationDetail;

	@OneToOne
	@JoinColumn(name = "UPLOAD_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docDemand;

//	sandeep, start

	@Column(name = "SV_CHARGES_ON_COST_OF_MATERIAL")
	private BigDecimal supervisionChargesOnCostOfMaterial;

	@Column(name = "CGST")
	private BigDecimal cgst;

	@Column(name = "SGST")
	private BigDecimal sgst;

	@Column(name = "KRISHI_CESS")
	private BigDecimal krishiCess;

	@Column(name = "EDUCATION_CESS")
	private BigDecimal educationCess;

	@Column(name = "SECONDARY_HIGHER_EDU_CESS")
	private BigDecimal eecondaryHigherEduCess;

	@Column(name = "SYSTEM_DEVELOPMENT_CHARGES")
	private BigDecimal systemDevelopmentCharges;

	@Column(name = "COST_OF_ESTIMATE")
	private BigDecimal costOfEstimate;

	@Column(name = "OTHER_INFRA_STRUC_RELATED_COST")
	private BigDecimal otherInfraStrucRelatedCost;

	@Column(name = "SUPPLY_AFFORDING_CHARGES")
	private BigDecimal supplyAffordingCharges;

	public Long getDemandId() {
		return demandId;
	}

	public void setDemandId(Long demandId) {
		this.demandId = demandId;
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

	public ConsumerApplicationDetail getConsumersApplicationDetail() {
		return consumersApplicationDetail;
	}

	public void setConsumersApplicationDetail(ConsumerApplicationDetail consumersApplicationDetail) {
		this.consumersApplicationDetail = consumersApplicationDetail;
	}

	public Upload getDocDemand() {
		return docDemand;
	}

	public void setDocDemand(Upload docDemand) {
		this.docDemand = docDemand;
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
	
	
	

//	sandeep, end

}