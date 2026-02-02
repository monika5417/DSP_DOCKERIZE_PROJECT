package com.mpcz.deposit_scheme.backend.api.request;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Work Order Form .")
@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class WorkOrderTypeForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Select valid Consumer Application Id", required = true)
	@NotEmpty(message = "Please select Consumer Application Id")
	private Long consumerApplicationId;
	
	
	private Date workOrderGenerationDate;
	
	private String remark;



	public Long getConsumerApplicationId() {
		return consumerApplicationId;
	}



	public void setConsumerApplicationId(Long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}



	public Date getWorkOrderGenerationDate() {
		return workOrderGenerationDate;
	}



	public void setWorkOrderGenerationDate(Date workOrderGenerationDate) {
		this.workOrderGenerationDate = workOrderGenerationDate;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public static WorkOrderTypeForm stringToJson(String workOrderForm) {

		WorkOrderTypeForm workOrderJson = new WorkOrderTypeForm();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			workOrderJson = objectMapper.readValue(workOrderForm,
					WorkOrderTypeForm.class);
		} catch (IOException err) {
			System.out.printf("Error", err.toString());
		}

		return workOrderJson;

	}

}

