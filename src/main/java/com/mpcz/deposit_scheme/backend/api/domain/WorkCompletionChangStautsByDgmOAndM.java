package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "WorkCompletionChangStautsByDgmOAndM")
@Table(name = "CHANGE_STATUS_BY_O_AND_M")
public class WorkCompletionChangStautsByDgmOAndM {
	
	
	@Id
	@SequenceGenerator(name = "CHANGE_STAUTS_BYDGMOREDAM_SEQ",sequenceName = "CHANGE_STAUTS_BYDGMOREDAM_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "CHANGE_STAUTS_BYDGMOREDAM_SEQ")
	@Column(name = "ID")
	private long id;

	
	@Column(name = "CHANGED_BY_DGM_O_M")
	String workComplationChangedByDgmOrendum;
	
	@Column(name = "CHANGED_REASON")
	String workComplationChangedReason;
	
	@Column(name = "CONSUMER_APPLICATION_ID")
	long consumerApplicationId;

	@Column(name = "DATE_OF_DGM_O_M")
	private String dateOfDgmOandM;
	
	
	public String getDateOfDgmOandM() {
		return dateOfDgmOandM;
	}

	public void setDateOfDgmOandM(String dateOfDgmOandM) {
		this.dateOfDgmOandM = dateOfDgmOandM;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public long getConsumerApplicationId() {
		return consumerApplicationId;
	}

	public void setConsumerApplicationId(long consumerApplicationId) {
		this.consumerApplicationId = consumerApplicationId;
	}
	
	
	
}
