/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Amit
 *
 * 07-Sep-2020
 */
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = { "user" })
@Data
@Entity
@Table(name="user_login_attempts")
public class UserLoginAttempts extends Auditable<Long> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_login_attempts_seq")
	@SequenceGenerator(name="user_login_attempts_seq", sequenceName="user_login_attempts_seq" , allocationSize = 1)
	@Column(name="user_login_attempts_id")
	private Long userLoginAttemptId;
	
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_user_id", referencedColumnName = "ad_user_id", nullable = false)
	private User user;
	
	@Column(name="attempts")
	private Long attempts;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_attempt_time")
	private Date lastAttemptTime;

	public Long getUserLoginAttemptId() {
		return userLoginAttemptId;
	}

	public void setUserLoginAttemptId(Long userLoginAttemptId) {
		this.userLoginAttemptId = userLoginAttemptId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getAttempts() {
		return attempts;
	}

	public void setAttempts(Long attempts) {
		this.attempts = attempts;
	}

	public Date getLastAttemptTime() {
		return lastAttemptTime;
	}

	public void setLastAttemptTime(Date lastAttemptTime) {
		this.lastAttemptTime = lastAttemptTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
