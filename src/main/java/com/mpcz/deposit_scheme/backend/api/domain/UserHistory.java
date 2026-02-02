/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Amit
 *
 * 31-Aug-2020
 */

@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "user_history")
public  @Data class UserHistory extends Auditable<Long>{
	 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="user_history_seq")
	@SequenceGenerator(name="user_history_seq",sequenceName="user_history_seq",allocationSize=1)
	@Column(name = "user_history_id")
	private Long userHistoryId;

	@Column(name = "username")
	private String userName;
	
	@Column(name = "property_name")
	private String propertyName;
	
	@Column(name = "property_value")
	private String propertyValue;	
	
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public Long getUserHistoryId() {
		return userHistoryId;
	}

	public void setUserHistoryId(Long userHistoryId) {
		this.userHistoryId = userHistoryId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
