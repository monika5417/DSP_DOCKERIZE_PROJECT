package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "LIST_DISTRIBUTION_CENTER")
public class ListDistributionCenter {

	@Id
	@SequenceGenerator(name = "LISTT_DISTRIBUTION_CENTER_SEQ", sequenceName = "LISTT_DISTRIBUTION_CENTER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LISTT_DISTRIBUTION_CENTER_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Integer id;
	
	
	@Column(name = "DISTRIBUTION_CENTER_ID")
	private Integer distributionCenterId;
	
	@Column(name = "DISTRIBUTION_CENTER_NAME")
	private String distributionCenterName;
	
	@Column(name = "USER_ID")
	private String userId;
	
	
	@Column(name = "ACCESS_LEVEL")
	private String accessLevel;
	
	@Column(name = "CREATED_DATE")
	private String date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDistributionCenterId() {
		return distributionCenterId;
	}

	public void setDistributionCenterId(Integer distributionCenterId) {
		this.distributionCenterId = distributionCenterId;
	}

	public String getDistributionCenterName() {
		return distributionCenterName;
	}

	public void setDistributionCenterName(String distributionCenterName) {
		this.distributionCenterName = distributionCenterName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	 
}
