/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Monika Rajpoot
 * @since 02-Jan-2026
 * @description ErpSurveyEstimationDetail.java class description
 */

@Entity(name = "ERP_SURVEY_ESTIMATION_DETAIL")
@Table(name = "ERP_SURVEY_ESTIMATION_DETAIL")
public class ErpSurveyEstimationDetail extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ERP_SURVEY_SEQ", sequenceName = "ERP_SURVEY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERP_SURVEY_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;

	@ApiModelProperty(notes = "Application No. cannot be null", required = true)
	@NotEmpty
	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;

	@ApiModelProperty(notes = "Proposed Online cannot be null", required = true)
	@NotEmpty
	@Column(name = "PROPOSED_ONLINE")
	private String proposedOnline;

	@ApiModelProperty(notes = "CAPACITY cannot be null", required = true)
	@NotEmpty
	@Column(name = "CAPACITY")
	private String capacity;

	@Column(name = "KV_11_LINE_DISTANCE")
	private Long kv11LineDistance;

	@Column(name = "LT_LINE_DISTANCE")
	private Long ltLineDistance;

	@Column(name = "TAPPING_DP")
	private Long tappingDp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	
	public String getProposedOnline() {
		return proposedOnline;
	}

	public void setProposedOnline(String proposedOnline) {
		this.proposedOnline = proposedOnline;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public Long getKv11LineDistance() {
		return kv11LineDistance;
	}

	public void setKv11LineDistance(Long kv11LineDistance) {
		this.kv11LineDistance = kv11LineDistance;
	}

	public Long getLtLineDistance() {
		return ltLineDistance;
	}

	public void setLtLineDistance(Long ltLineDistance) {
		this.ltLineDistance = ltLineDistance;
	}

	public Long getTappingDp() {
		return tappingDp;
	}

	public void setTappingDp(Long tappingDp) {
		this.tappingDp = tappingDp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
