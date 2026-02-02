package com.mpcz.deposit_scheme.backend.api.response;

public class CaptchaResponse {
	private Long captchaId;

	private String captchaImgStr;

	public Long getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(Long captchaId) {
		this.captchaId = captchaId;
	}

	public String getCaptchaImgStr() {
		return captchaImgStr;
	}

	public void setCaptchaImgStr(String captchaImgStr) {
		this.captchaImgStr = captchaImgStr;
	}
}
