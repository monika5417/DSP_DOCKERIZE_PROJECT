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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity(name = "WorkOrder")
@Table(name = "WORK_ORDER")
public class WorkOrder extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "WORK_ORDER_SEQ", sequenceName = "WORK_ORDER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORK_ORDER_SEQ")
	@Column(name = "WORK_ORDER_ID", columnDefinition = "serial", updatable = false)
	private Long workOrderId;

	
	
	@Column(name = "WORK_ORDER_STATUS")
	private String workOrderStatus;
	
	@Column(name = "REMARK")
	private String  remark;
	

	@Column(name = "WORK_ORDER_GENERATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date workOrderGenerationDate;


	@ManyToOne
	@JoinColumn(name = "CONSUMER_APPLICATION_ID", referencedColumnName = "CONSUMER_APPLICATION_ID" )
	private ConsumerApplicationDetail consumersApplicationDetail;
	
	@OneToOne
	@JoinColumn(name = "UPLOAD_ID", referencedColumnName = "UPLOAD_ID")
	private Upload docWorkOrder;

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getWorkOrderGenerationDate() {
		return workOrderGenerationDate;
	}

	public void setWorkOrderGenerationDate(Date workOrderGenerationDate) {
		this.workOrderGenerationDate = workOrderGenerationDate;
	}

	public ConsumerApplicationDetail getConsumersApplicationDetail() {
		return consumersApplicationDetail;
	}

	public void setConsumersApplicationDetail(ConsumerApplicationDetail consumersApplicationDetail) {
		this.consumersApplicationDetail = consumersApplicationDetail;
	}

	public Upload getDocWorkOrder() {
		return docWorkOrder;
	}

	public void setDocWorkOrder(Upload docWorkOrder) {
		this.docWorkOrder = docWorkOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
}