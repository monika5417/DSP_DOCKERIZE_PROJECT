package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCompletionChangStautsByDgmOAndMDto {
	
	private String workComplationChangedByDgmOrendum;
	private String workComplationChangedReason;
	private String dateOfDgmOandM;
	private int consumerApplicationId;
	
	public String getWorkComplationChangedByDgmOrendum() {
		return workComplationChangedByDgmOrendum;
	}
	public void setWorkComplationChangedByDgmOrendum(String workComplationChangedByDgmOrendum) {
		this.workComplationChangedByDgmOrendum = workComplationChangedByDgmOrendum;
	}
	public String getWorkComplationChangedReason() {
		return workComplationChangedReason;
	}
	public void setWorkComplationChangedReason(String workComplationChangedReason) {
		this.workComplationChangedReason = workComplationChangedReason;
	}
	public String getDateOfDgmOandM() {
		return dateOfDgmOandM;
	}
	public void setDateOfDgmOandM(String dateOfDgmOandM) {
		this.dateOfDgmOandM = dateOfDgmOandM;
	}
	public int getConsumerApplicationId() {
		return consumerApplicationId;
	}
	public void setConsumerApplicationId(int consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}

}
