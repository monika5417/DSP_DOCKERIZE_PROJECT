package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "ErpEstimateDomain")
@Table(name = "ERP_BUDGET_WORKFLOW")
public @Data class ErpEstimateDomain extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ERP_ESTIMATE_S", sequenceName = "ERP_ESTIMATE_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERP_ESTIMATE_S")
	@Column(name = "ERP_BUDGET_WORKFLOW_ID", columnDefinition = "serial", updatable = false)
	private Long erpBudgetWorkFlowId;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "PENDING_ON")
	private String pendingOn;

	@Column(name = "NOTIFICATION_ON_DATE")
	private String notificationOnDate;

	@Column(name = "ACTION_PERFORMED_DATE")
	private String  actionPerformedDate;
	
	

	@Column(name = "ACTION_PERFORMED")
	private String actionPerformed;

	@Column(name = "ERP_BUDGET_WORKFLOW_NUMBER")
	private String erpBudgetWorkFlowNumber;

	public Long getErpBudgetWorkFlowId() {
		return erpBudgetWorkFlowId;
	}

	public void setErpBudgetWorkFlowId(Long erpBudgetWorkFlowId) {
		this.erpBudgetWorkFlowId = erpBudgetWorkFlowId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPendingOn() {
		return pendingOn;
	}

	public void setPendingOn(String pendingOn) {
		this.pendingOn = pendingOn;
	}

	public String getNotificationOnDate() {
		return notificationOnDate;
	}

	public void setNotificationOnDate(String notificationOnDate) {
		this.notificationOnDate = notificationOnDate;
	}

	public String getActionPerformedDate() {
		return actionPerformedDate;
	}

	public void setActionPerformedDate(String actionPerformedDate) {
		this.actionPerformedDate = actionPerformedDate;
	}

	public String getActionPerformed() {
		return actionPerformed;
	}

	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public String getErpBudgetWorkFlowNumber() {
		return erpBudgetWorkFlowNumber;
	}

	public void setErpBudgetWorkFlowNumber(String erpBudgetWorkFlowNumber) {
		this.erpBudgetWorkFlowNumber = erpBudgetWorkFlowNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}