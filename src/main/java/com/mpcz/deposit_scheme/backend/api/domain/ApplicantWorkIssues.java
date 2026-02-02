package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="APPLICANT_WORK_ISSUES")
public class ApplicantWorkIssues extends Auditable<Long>{

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APPLICANT_WORK_ISSUES_SEQ", sequenceName = "APPLICANT_WORK_ISSUES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICANT_WORK_ISSUES_SEQ")
	@Column(name = "WORK_ISSUE_ID", columnDefinition = "serial", updatable = false)
	private Long workIssueId;
	
	@Column(name="CONSUMER_APPLICATON_NO")
	private String consumerApplicationNo;
	
	@Column(name="FEEDBACK")
	private String feedback;
	
	@Column(name="TYPE_OF_USER")
	private String typeOfUser;

	
	public String getTypeOfUser() {
		return typeOfUser;
	}

	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	public Long getWorkIssueId() {
		return workIssueId;
	}

	public void setWorkIssueId(Long workIssueId) {
		this.workIssueId = workIssueId;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
