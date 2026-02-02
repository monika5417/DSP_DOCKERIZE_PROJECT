package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "OTP")
public class Otp extends Auditable<Long> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OtpId otpId;

	@Column(name = "otp_val")
	private String otpVal;

	public OtpId getOtpId() {
		return otpId;
	}

	public void setOtpId(OtpId otpId) {
		this.otpId = otpId;
	}

	public String getOtpVal() {
		return otpVal;
	}

	public void setOtpVal(String otpVal) {
		this.otpVal = otpVal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
