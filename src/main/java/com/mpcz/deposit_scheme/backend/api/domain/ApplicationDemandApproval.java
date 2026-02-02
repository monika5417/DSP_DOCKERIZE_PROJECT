package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mpcz.deposit_scheme.backend.api.enums.StatusEnum;

import lombok.Data;

@Entity(name = "ApplicationDemandApproval")
@Table(name = "APPLICATION_DEMAND_APPROVAL")
public @Data class ApplicationDemandApproval extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "APPLICATION_DEMAND_APPROVAL_S", sequenceName = "APPLICATION_DEMAND_APPROVAL_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_DEMAND_APPROVAL_S")
	@Column(name = "APPLICATION_DEMAND_APPROVAL_ID", columnDefinition = "serial", updatable = false)
	private Long ApplicationDemandApprovalId;

	@ManyToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumersApplicationDetail;

	@Column(name = "ACTION_DATE")
	private Date actionDate = new Date();

	@Column(name = "STATUS")
	private String status = StatusEnum.PENDING.getValue();

	@Column(name = "REJECTED_REASON")
	private String rejectedReason;

	public Long getApplicationDemandApprovalId() {
		return ApplicationDemandApprovalId;
	}

	public void setApplicationDemandApprovalId(Long applicationDemandApprovalId) {
		ApplicationDemandApprovalId = applicationDemandApprovalId;
	}

	public ConsumerApplicationDetail getConsumersApplicationDetail() {
		return consumersApplicationDetail;
	}

	public void setConsumersApplicationDetail(ConsumerApplicationDetail consumersApplicationDetail) {
		this.consumersApplicationDetail = consumersApplicationDetail;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
