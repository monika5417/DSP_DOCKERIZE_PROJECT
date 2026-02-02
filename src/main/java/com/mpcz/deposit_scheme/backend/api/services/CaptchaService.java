package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.Captcha;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;

public interface CaptchaService {

	public Captcha save(Captcha captcha);

	public Boolean isCaptchaValid(Long captchaId, String captchaText) throws InvalidAuthenticationException;
}
