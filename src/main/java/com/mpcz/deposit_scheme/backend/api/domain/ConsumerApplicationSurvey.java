package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Entity(name = "ConsumerApplicationSurvey")
@Table(name = "CONSUMER_APPLICATION_SURVEY")
public @Data class ConsumerApplicationSurvey extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONSUMER_APPLICATION_SURVEY_S", sequenceName = "CONSUMER_APPLICATION_SURVEY_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_APPLICATION_SURVEY_S")
	@Column(name = "CONSUMER_APPLICATION_SURVEY_ID", columnDefinition = "serial", updatable = false)
	private Long consumerApplicationSurveyId;

	@Column(name = "REJECTED_REASON")
	private String rejectedReason;

	@Column(name = "SURVEY_STATUS")
	@JsonFormat(shape=Shape.STRING,pattern="dd-MM-yyyy",timezone="IST")
	private String surveyStatus;

	@Column(name = "SURVEY_DATE")
	private String surveyDate;

	@OneToOne
	@JoinColumn(name = "DOC_SURVEY_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docSurvey;

	@ManyToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumersApplicationDetail;

	
	@Column(name = "SCHEDULE_SURVEY_DATE")
	private String scheduleSurveyDate;
	
	@Column(name = "SCHEDULE_SURVEY_TIME")
	private String scheduleSurveyTime;
	
	@Column(name = "SURVEYOR_NAME")
	private String surveyorName;
	
	@Column(name = "SURVEYOR_MOBILE")
	private String surveyorMobile;
	
	public String getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getScheduleSurveyDate() {
		return scheduleSurveyDate;
	}

	public void setScheduleSurveyDate(String scheduleSurveyDate) {
		this.scheduleSurveyDate = scheduleSurveyDate;
	}

	public Long getConsumerApplicationSurveyId() {
		return consumerApplicationSurveyId;
	}

	public void setConsumerApplicationSurveyId(Long consumerApplicationSurveyId) {
		this.consumerApplicationSurveyId = consumerApplicationSurveyId;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getSurveyStatus() {
		return surveyStatus;
	}

	public void setSurveyStatus(String surveyStatus) {
		this.surveyStatus = surveyStatus;
	}

	

	public Upload getDocSurvey() {
		return docSurvey;
	}

	public void setDocSurvey(Upload docSurvey) {
		this.docSurvey = docSurvey;
	}

	public ConsumerApplicationDetail getConsumersApplicationDetail() {
		return consumersApplicationDetail;
	}

	public void setConsumersApplicationDetail(ConsumerApplicationDetail consumersApplicationDetail) {
		this.consumersApplicationDetail = consumersApplicationDetail;
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
