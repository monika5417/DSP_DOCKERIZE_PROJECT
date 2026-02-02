package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



import lombok.Data;

@Data
@Entity
@Table(name="VENDOR_WORK_ORDER")
public class VendorWorkOrder  extends Auditable<Long>{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "vendor_work_order_seq", sequenceName = "vendor_work_order_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_work_order_seq")
	@Column(name = "work_order_id", columnDefinition = "serial", updatable = false)
	private Long workOrderId;

	
	@Column(name = "work_order_no")
	private String workOrderNo;

	@Column(name = "work_order_date")
	private String workOrderDate;
	
	@Column(name = "consumer_application_no")
	private String consumerApplicationNo;
	
	@Column(name="dgm_stc_id")
	private String dgmStcId;     // isme work order krne wale DGM ki id jati hai
	
	@Column(name="PREVIOUS_YEAR")
	private String previousYear;
	
	@Column(name="CURRENT_YEAR")
	private String currentYear;
	
	@Column(name="WO_VERSION")
	private String woVersion;
	
//	14-Oct-2024 start
	@Column(name="WO_GEN_ROLE_CODE")
	private String workOrderGeneratedRoleCode;
	
//	end
	
	
	public String getWorkOrderGeneratedRoleCode() {
		return workOrderGeneratedRoleCode;
	}

	public void setWorkOrderGeneratedRoleCode(String workOrderGeneratedRoleCode) {
		this.workOrderGeneratedRoleCode = workOrderGeneratedRoleCode;
	}

	public String getWoVersion() {
		return woVersion;
	}

	public void setWoVersion(String woVersion) {
		this.woVersion = woVersion;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(String workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public String getDgmStcId() {
		return dgmStcId;
	}

	public void setDgmStcId(String dgmStcId) {
		this.dgmStcId = dgmStcId;
	}

	public String getPreviousYear() {
		return previousYear;
	}

	public void setPreviousYear(String previousYear) {
		this.previousYear = previousYear;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
