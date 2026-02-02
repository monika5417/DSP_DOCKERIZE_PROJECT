package com.mpcz.deposit_scheme.backend.api.request;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerApplicationDetailForm;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "Consumer Application Detail Model Form .")
@JsonIgnoreProperties(ignoreUnknown = true)
public  @Data  class ConsumerApplicationDetailSurveyForm  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long consumerApplicationId;
 
	private Boolean isRejected;
 
	private String rejectedRemark;
	
	private String surveyDate;
	
	
	
	
	
	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}





	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}





	public Boolean getIsRejected() {
		return isRejected;
	}





	public void setIsRejected(Boolean isRejected) {
		this.isRejected = isRejected;
	}





	public String getRejectedRemark() {
		return rejectedRemark;
	}





	public void setRejectedRemark(String rejectedRemark) {
		this.rejectedRemark = rejectedRemark;
	}








	public String getSurveyDate() {
		return surveyDate;
	}





	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}





	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	public static ConsumerApplicationDetailSurveyForm stringToJson(String consumerApplicationDetailSurveyForm) {

		ConsumerApplicationDetailSurveyForm consumerApplicationDetailJson = new ConsumerApplicationDetailSurveyForm();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			consumerApplicationDetailJson = objectMapper.readValue(consumerApplicationDetailSurveyForm,
					ConsumerApplicationDetailSurveyForm.class);
		} catch (IOException err) {
			System.out.printf("Error", err.toString());
		}

		return consumerApplicationDetailJson;

	}

}
