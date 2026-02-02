package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name="REFUND_REJECTED_REMARK")
@Entity
public class RefundRejectedRemark extends Auditable<Long>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "REFUND_AMOUNT_SEQ", sequenceName = "REFUND_AMOUNT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFUND_AMOUNT_SEQ")
	@Column(name="ID")
	private Long id;
	
	@Column(name="APPLICATION_NO")
	private String applicationNo;
	
	@Column(name="REMARK")
	private String Remark;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="USER_ROLE")
	private String userRole;
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	
	
	
	

}
