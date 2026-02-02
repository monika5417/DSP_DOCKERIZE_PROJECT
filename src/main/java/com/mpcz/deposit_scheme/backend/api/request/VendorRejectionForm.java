package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "VendorRejection Type Model Form.")
public @Data class VendorRejectionForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "consumerApplicationId must not be null", required = true)
	private String consumerApplicationNo;
	
	@ApiModelProperty(notes = "RejectionRemark must be string", required = true)
	private String rejectionRemark;
	
	@ApiModelProperty(notes = "contractorUserd must not be null", required = true)
	private Long contractorId;

	@ApiModelProperty(notes = "isRejected must be Boolean and not null", required = true)
	private Boolean isRejected;
	
	@ApiModelProperty(notes = "Contractor Started Date must be Boolean and not null", required = true)
	private String conWorkStartedDate;
	
	@ApiModelProperty(notes = "Contractor Work Complete Date must be Boolean and not null", required = true)
	private String conWorkCompleteDate;
	
	@ApiModelProperty(notes = "Material Handover Date must be Boolean and not null", required = true)
	private String materialHandoverSiteDate;
	
	@ApiModelProperty(notes = "Material Installation Started Date must be Boolean and not null", required = true)
	private String materialInstallStartDate;
	
	@ApiModelProperty(notes = "Material Installation Finished Date must be Boolean and not null", required = true)
	private String materialInstallFinishDate;
	
	@ApiModelProperty(notes = "Actual Work Completion Date must be Boolean and not null", required = true)
	private String actualWorkCompletionDate;
	
	
	public String getConWorkStartedDate() {
		return conWorkStartedDate;
	}

	public void setConWorkStartedDate(String conWorkStartedDate) {
		this.conWorkStartedDate = conWorkStartedDate;
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

	public String getActualWorkCompletionDate() {
		return actualWorkCompletionDate;
	}

	public void setActualWorkCompletionDate(String actualWorkCompletionDate) {
		this.actualWorkCompletionDate = actualWorkCompletionDate;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public String getRejectionRemark() {
		return rejectionRemark;
	}

	public void setRejectionRemark(String rejectionRemark) {
		this.rejectionRemark = rejectionRemark;
	}

	public Long getContractorId() {
		return contractorId;
	}

	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	public Boolean getIsRejected() {
		return isRejected;
	}

	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

