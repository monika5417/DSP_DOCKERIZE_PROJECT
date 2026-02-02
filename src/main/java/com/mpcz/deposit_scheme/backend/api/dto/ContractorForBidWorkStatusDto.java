package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ContractorForBidWorkStatusDto {

	private Long workStatusId;
	
	private Long userId;
	
	private Long consumerApplicationId;
	
	private String conWorkStartedDate;
	
	private String materialInstallStartDate;
	
	private String materialInstallFinishDate;
	
	private String conWorkCompleteDate;
	
	private String materialHandoverSiteDate;

	private String dateOfDgmStc;
	
	private String consumerApplicationNumber;
	
	private String vendorName;
	
	private String vendorMaterialSpecification;
	
	private String dtr;
	
	private String ptr;
	
	private String lt;
	
	private String ht11Kv;
	
	private String ht33Kv;
	
	private String ht132Kv;
	
	private String actualWorkCompletionDate;
	
	

	public String getActualWorkCompletionDate() {
		return actualWorkCompletionDate;
	}

	public void setActualWorkCompletionDate(String actualWorkCompletionDate) {
		this.actualWorkCompletionDate = actualWorkCompletionDate;
	}

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

	public String getDateOfDgmStc() {
		return dateOfDgmStc;
	}

	public void setDateOfDgmStc(String dateOfDgmStc) {
		this.dateOfDgmStc = dateOfDgmStc;
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
	
	
	

}
