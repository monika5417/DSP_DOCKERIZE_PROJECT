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
@Table(name = "CAPTCHA")
public class Captcha extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CAPTCHA_SEQ", sequenceName = "CAPTCHA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAPTCHA_SEQ")
	@Column(name = "CAPTCHA_ID")
	private Long captchaId;

	@Column(name = "CAPTCHA_TEXT")
	private String captchaText;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CAPTCHA_EXPIRED_TIME")
	private Date captchaExpiredTime;

	public Long getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(Long captchaId) {
		this.captchaId = captchaId;
	}

	public String getCaptchaText() {
		return captchaText;
	}

	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}

	public Date getCaptchaExpiredTime() {
		return captchaExpiredTime;
	}

	public void setCaptchaExpiredTime(Date captchaExpiredTime) {
		this.captchaExpiredTime = captchaExpiredTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
