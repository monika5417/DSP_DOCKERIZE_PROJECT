package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.mpcz.deposit_scheme.backend.api.dto.JeRejectFileDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public @Data class ConsumerApplicationDcCorrectionForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Conusmer application id", required = true)
	@NotNull
	private Long consumerApplicationId;

	

	@ApiModelProperty(notes = "DC id")
	private Long changedDcId;

	@ApiModelProperty(notes = "dc change status", required = true)
	@NotNull
	private Boolean dcChanged;

	@ApiModelProperty(notes = "dc change status")
	private String dcChangedReason;

	private String scheduleSurveyDate;
	private String scheduleSurveyTime;
	private String surveyorName;
	private String surveyorMobile;
	private List<JeRejectFileDto> rejectFile;

	public List<JeRejectFileDto> getRejectFile() {
		return rejectFile;
	}
	public void setRejectFile(List<JeRejectFileDto> rejectFile) {
		this.rejectFile = rejectFile;
	}
	public String getScheduleSurveyDate() {
		return scheduleSurveyDate;
	}
	public void setScheduleSurveyDate(String scheduleSurveyDate) {
		this.scheduleSurveyDate = scheduleSurveyDate;
	}
	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}
	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}
	public Long getChangedDcId() {
		return changedDcId;
	}
	public void setChangedDcId(Long changedDcId) {
		this.changedDcId = changedDcId;
	}
	public Boolean getDcChanged() {
		return dcChanged;
	}
	public void setDcChanged(Boolean dcChanged) {
		this.dcChanged = dcChanged;
	}
	public String getDcChangedReason() {
		return dcChangedReason;
	}
	public void setDcChangedReason(String dcChangedReason) {
		this.dcChangedReason = dcChangedReason;
	}
	
	public String getScheduleSurveyTime() {
		return scheduleSurveyTime;
	}
	public void setScheduleSurveyTime(String scheduleSurveyTime) {
		this.scheduleSurveyTime = scheduleSurveyTime;
	}
	public String getSurveyorName() {
		return surveyorName;
	}
	public void setSurveyorName(String surveyorName) {
		this.surveyorName = surveyorName;
	}
	public String getSurveyorMobile() {
		return surveyorMobile;
	}
	public void setSurveyorMobile(String surveyorMobile) {
		this.surveyorMobile = surveyorMobile;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
