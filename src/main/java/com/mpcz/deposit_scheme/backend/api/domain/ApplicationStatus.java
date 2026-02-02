package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="APPLICATION_STATUS")
public class ApplicationStatus extends Auditable<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "APPLICATION_STATUS_SEQ", sequenceName = "APPLICATION_STATUS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_STATUS_SEQ")
	@Column(name="APPLICATION_STATUS_ID",columnDefinition = "serial", updatable = false)
	private Long applicationStatusId;
	
	@Column(name="APPLICATION_STATUS_NAME")
	private String applicationStatusName;
	
	@Column(name="APPLICATION_STATUS_DESCRIPTION")
	private String applicationStatusDescription;
	

	public Long getApplicationStatusId() {
		return applicationStatusId;
	}

	public void setApplicationStatusId(Long applicationStatusId) {
		this.applicationStatusId = applicationStatusId;
	}

	public String getApplicationStatusName() {
		return applicationStatusName;
	}

	public void setApplicationStatusName(String applicationStatusName) {
		this.applicationStatusName = applicationStatusName;
	}

	public String getApplicationStatusDescription() {
		return applicationStatusDescription;
	}

	public void setApplicationStatusDescription(String applicationStatusDescription) {
		this.applicationStatusDescription = applicationStatusDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
