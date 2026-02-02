package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.CrossOrigin;

@Entity(name = "REJECT_PRO_APP_STA_H")
@Table(name = "REJECT_PRO_APP_STA_H")
public class RejectProposalApplicationStatusHis extends Auditable<Long>{
	
	@Id
	@SequenceGenerator(name = "REJECT_PRO_APP_STA_H_S", sequenceName = "REJECT_PRO_APP_STA_H_S", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REJECT_PRO_APP_STA_H_S")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)	
	private int id;
	
	
	@Column(name = "CON_APP_NO")
	private String consumerApplicationNumber;
	
	@Column(name = "APP_STATUS_ID")
	private int ApplicationStatusId;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "USER_ROLE")
	private String userRole;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public int getApplicationStatusId() {
		return ApplicationStatusId;
	}

	public void setApplicationStatusId(int applicationStatusId) {
		ApplicationStatusId = applicationStatusId;
	}

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

	
	
	

}
