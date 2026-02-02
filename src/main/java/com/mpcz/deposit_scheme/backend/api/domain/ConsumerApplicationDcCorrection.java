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

import lombok.Data;

@Entity(name = "ConsumerApplicationDcCorrection")
@Table(name = "CONSUMER_APP_DC_CORRECTION")
public @Data class ConsumerApplicationDcCorrection extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONSUMER_APP_DC_CORRECTION_SEQ", sequenceName = "CONSUMER_APP_DC_CORRECTION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSUMER_APP_DC_CORRECTION_SEQ")
	@Column(name = "CONSUMER_APP_DC_CORRECTION_ID", columnDefinition = "serial", updatable = false)
	private Long consumerApplicationDcCorrectionId;

	@ManyToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumersApplicationDetail;

	@ManyToOne
	@JoinColumn(name = "OLD_DC_ID", referencedColumnName = "dc_id")
	private DistributionCenter oldDc;

	@ManyToOne
	@JoinColumn(name = "CHANGED_DC_ID", referencedColumnName = "dc_id")
	private DistributionCenter changedDc;

	@Column(name = "DC_CHANGED")
	private Boolean dcChanged = false;

	@Column(name = "DC_CHANGED_REASON")
	private String dcChangedReason;

	public Long getConsumerApplicationDcCorrectionId() {
		return consumerApplicationDcCorrectionId;
	}

	public void setConsumerApplicationDcCorrectionId(Long consumerApplicationDcCorrectionId) {
		this.consumerApplicationDcCorrectionId = consumerApplicationDcCorrectionId;
	}

	public ConsumerApplicationDetail getConsumersApplicationDetail() {
		return consumersApplicationDetail;
	}

	public void setConsumersApplicationDetail(ConsumerApplicationDetail consumersApplicationDetail) {
		this.consumersApplicationDetail = consumersApplicationDetail;
	}

	public DistributionCenter getOldDc() {
		return oldDc;
	}

	public void setOldDc(DistributionCenter oldDc) {
		this.oldDc = oldDc;
	}

	public DistributionCenter getChangedDc() {
		return changedDc;
	}

	public void setChangedDc(DistributionCenter changedDc) {
		this.changedDc = changedDc;
	}

	public Boolean getDcChanged() {
		return dcChanged;
	}

	public void setDcChanged(Boolean dcChanged) {
		this.dcChanged = dcChanged;
	}

	public String getDcChangedReason() {
		return dcChangedReason;
	}

	public void setDcChangedReason(String dcChangedReason) {
		this.dcChangedReason = dcChangedReason;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
