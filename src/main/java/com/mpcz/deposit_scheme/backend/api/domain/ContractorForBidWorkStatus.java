package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="CONTRACTOR_FOR_BID_WORK_STATUS")
public class ContractorForBidWorkStatus {
	
	@Id
	@SequenceGenerator(name = "CONTRACTOR_WORK_STATUS_SEQ", sequenceName = "CONTRACTOR_WORK_STATUS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_WORK_STATUS_SEQ")
	@Column(name = "WORK_STATUS_ID", columnDefinition = "serial", updatable = false)
	private Long workStatusId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "CONSUMER_APPLICATION_ID")
	private Long consumerApplicationId;

	@Column(name = "CON_WORK_STARTED_DATE")
	private String conWorkStartedDate;

	@Column(name = "MATERIAL_INSTALL_START_DATE")
	private String materialInstallStartDate;
	
	@Column(name = "MATERIAL_INSTALL_FINISH_DATE")
	private String materialInstallFinishDate;
	
	@Column(name = "CON_WORK_COMPLETE_DATE")
	private String conWorkCompleteDate;
	
	@Column(name = "MATERIAL_HANDOVER_SITE_DATE")
	private String materialHandoverSiteDate;
	
	@Column(name = "DATE_OF_DGM_STC")
	private String dgmStcDate;
	
	@Column(name = "CONSUMER_APPLICATION_NUMBER")
	private String consumerApplicationNumber;
	
	@Column(name = "VENDOR_NAME")
	private String vendorName;
	
	@Column(name = "VENDOR_MATERIAL_SPECIFICATION")
	private String vendorMaterialSpecification;
	
	@Column(name = "DTR")
	private String dtr;
	
	@Column(name = "PTR")
	private String ptr;
	
	@Column(name = "LT")
	private String lt;

	@Column(name = "HT_11_KV")
	private String ht11Kv;
	
	@Column(name = "HT_33_KV")
	private String ht33Kv;
	
	@Column(name = "HT_132_KV")
	private String ht132Kv;
	
	@Column(name = "ACTUAL_WORK_COMPLETION_DATE")
	private String actualWorkCompletionDate;


	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}

	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}

	

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorMaterialSpecification() {
		return vendorMaterialSpecification;
	}

	public void setVendorMaterialSpecification(String vendorMaterialSpecification) {
		this.vendorMaterialSpecification = vendorMaterialSpecification;
	}

	public String getDtr() {
		return dtr;
	}

	public void setDtr(String dtr) {
		this.dtr = dtr;
	}

	public String getPtr() {
		return ptr;
	}

	public void setPtr(String ptr) {
		this.ptr = ptr;
	}

	public String getLt() {
		return lt;
	}

	public void setLt(String lt) {
		this.lt = lt;
	}

	public String getHt11Kv() {
		return ht11Kv;
	}

	public void setHt11Kv(String ht11Kv) {
		this.ht11Kv = ht11Kv;
	}

	public String getHt33Kv() {
		return ht33Kv;
	}

	public void setHt33Kv(String ht33Kv) {
		this.ht33Kv = ht33Kv;
	}

	public String getHt132Kv() {
		return ht132Kv;
	}

	public void setHt132Kv(String ht132Kv) {
		this.ht132Kv = ht132Kv;
	}


	

	public String getActualWorkCompletionDate() {
		return actualWorkCompletionDate;
	}

	public void setActualWorkCompletionDate(String actualWorkCompletionDate) {
		this.actualWorkCompletionDate = actualWorkCompletionDate;
	}

	public String getConWorkStartedDate() {
		return conWorkStartedDate;
	}

	public void setConWorkStartedDate(String conWorkStartedDate) {
		this.conWorkStartedDate = conWorkStartedDate;
	}

	public String getMaterialInstallStartDate() {
		return materialInstallStartDate;
	}

	public void setMaterialInstallStartDate(String materialInstallStartDate) {
		this.materialInstallStartDate = materialInstallStartDate;
	}

	public String getMaterialInstallFinishDate() {
		return materialInstallFinishDate;
	}

	public void setMaterialInstallFinishDate(String materialInstallFinishDate) {
		this.materialInstallFinishDate = materialInstallFinishDate;
	}

	public String getConWorkCompleteDate() {
		return conWorkCompleteDate;
	}

	public void setConWorkCompleteDate(String conWorkCompleteDate) {
		this.conWorkCompleteDate = conWorkCompleteDate;
	}

	public String getMaterialHandoverSiteDate() {
		return materialHandoverSiteDate;
	}

	public void setMaterialHandoverSiteDate(String materialHandoverSiteDate) {
		this.materialHandoverSiteDate = materialHandoverSiteDate;
	}

	public String getDgmStcDate() {
		return dgmStcDate;
	}

	public void setDgmStcDate(String dgmStcDate) {
		this.dgmStcDate = dgmStcDate;
	}
	
	
	
	
}
