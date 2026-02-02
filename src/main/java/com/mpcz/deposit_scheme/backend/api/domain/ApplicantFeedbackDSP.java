package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="APPLICANT_FEEDBACK_DSP")
public class ApplicantFeedbackDSP extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APPLICANT_FEEDBACK_SEQ", sequenceName = "APPLICANT_FEEDBACK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICANT_FEEDBACK_SEQ")
	@Column(name = "FEEDBACK_ID", columnDefinition = "serial", updatable = false)
	private Long feedbackId;
	
	@Column(name="CONSUMER_APPLICATON_NO")
	private String consumerApplicationNo;
	
	@Column(name="FEEDBACK")
	private String feedback;
	
	@Column(name="RATINGS")
	private String ratings;

	@Column(name="FEEDBACK_OPTION")
	private String feedbackOption;
	

	public String getFeedbackOption() {
		return feedbackOption;
	}

	public void setFeedbackOption(String feedbackOption) {
		this.feedbackOption = feedbackOption;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
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
	
	
	
	
}
