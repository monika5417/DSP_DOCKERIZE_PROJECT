package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class OtpId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "SOURCE")
	private String source;

	public OtpId() {
		// TODO Auto-generated constructor stub
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getSource() {
		return source;
	}

	public OtpId(String mobileNo, String source) {
		super();
		this.mobileNo = mobileNo;
		this.source = source;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof OtpId))
			return false;
		OtpId that = (OtpId) o;
		return Objects.equals(getMobileNo(), that.getMobileNo()) && Objects.equals(getSource(), that.getSource());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMobileNo(), getSource());
	}

}
