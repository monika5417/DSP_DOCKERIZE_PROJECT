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

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_login_token")
public class UserLoginToken  extends Auditable<Long>{
	
	@Id
	@SequenceGenerator(name = "user_login_token_seq", sequenceName = "user_login_token_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_login_token_seq")
	@Column(name = "login_token_id")
	private Long loginTokenId;

	@Column(name = "token_text")
	private String tokenText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "token_expired_time")
	
	private Date tokenExpiredTime;

	public Long getLoginTokenId() {
		return loginTokenId;
	}

	public void setLoginTokenId(Long loginTokenId) {
		this.loginTokenId = loginTokenId;
	}

	public String getTokenText() {
		return tokenText;
	}

	public void setTokenText(String tokenText) {
		this.tokenText = tokenText;
	}

	public Date getTokenExpiredTime() {
		return tokenExpiredTime;
	}

	public void setTokenExpiredTime(Date tokenExpiredTime) {
		this.tokenExpiredTime = tokenExpiredTime;
	}
	

}
