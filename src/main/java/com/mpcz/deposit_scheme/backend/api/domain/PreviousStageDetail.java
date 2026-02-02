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

@Entity(name = "PreviousStageDetail")
@Table(name = "PREVIOUS_STAGE_DETAIL")
public @Data class PreviousStageDetail extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PREVIOUS_STAGE_DETAIL_SEQ", sequenceName = "PREVIOUS_STAGE_DETAIL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREVIOUS_STAGE_DETAIL_SEQ")
	@Column(name = "PREVIOUS_STAGE_DETAIL_ID", columnDefinition = "serial", updatable = false)
	private Long previousStageDetailId;
	
	@ManyToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID")
	private ConsumerApplicationDetail consumersApplicationDetail;
	
	@ManyToOne
	@JoinColumn(name = "FROM_STAGE", referencedColumnName = "APPLICATION_STATUS_ID")
	private ApplicationStatus fromStage;
	
	@ManyToOne
	@JoinColumn(name = "TO_STAGE", referencedColumnName = "APPLICATION_STATUS_ID")
	private ApplicationStatus toStage;
	
	@Column(name = "BACKWARD_DATE")
	private Date backwardDate = new Date();
	
	@Column(name = "FORWARD_DATE")
	private Date forwardDate;
	
	@Column(name = "DAYS")
	private Integer days = 0;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "STATUS")
	private String status  = StatusEnum.PENDING.getValue();

	public Long getPreviousStageDetailId() {
		return previousStageDetailId;
	}

	public void setPreviousStageDetailId(Long previousStageDetailId) {
		this.previousStageDetailId = previousStageDetailId;
	}

	public ConsumerApplicationDetail getConsumersApplicationDetail() {
		return consumersApplicationDetail;
	}

	public void setConsumersApplicationDetail(ConsumerApplicationDetail consumersApplicationDetail) {
		this.consumersApplicationDetail = consumersApplicationDetail;
	}

	public ApplicationStatus getFromStage() {
		return fromStage;
	}

	public void setFromStage(ApplicationStatus fromStage) {
		this.fromStage = fromStage;
	}

	public ApplicationStatus getToStage() {
		return toStage;
	}

	public void setToStage(ApplicationStatus toStage) {
		this.toStage = toStage;
	}

	public Date getBackwardDate() {
		return backwardDate;
	}

	public void setBackwardDate(Date backwardDate) {
		this.backwardDate = backwardDate;
	}

	public Date getForwardDate() {
		return forwardDate;
	}

	public void setForwardDate(Date forwardDate) {
		this.forwardDate = forwardDate;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
     
 

}
