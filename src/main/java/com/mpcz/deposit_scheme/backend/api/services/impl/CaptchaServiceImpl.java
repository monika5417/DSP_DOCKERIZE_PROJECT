package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.ResponseCode;
import com.mpcz.deposit_scheme.backend.api.domain.Captcha;
import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.repository.CaptchaRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.CaptchaService;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	@Autowired
	CaptchaRepository captchaRepository;

	@Override
	public Captcha save(Captcha captcha) {
		return captchaRepository.save(captcha);
	}

	@Override
	public Boolean isCaptchaValid(Long captchaId, String captchaText) throws InvalidAuthenticationException {
		Response<Consumer> response = new Response<>();
		Optional<Captcha> captcha = captchaRepository.findByCaptchaId(captchaId);
		if (captcha.isPresent()) {
			Captcha dbCaptcha = captcha.get();
			Date currentTime = new Date();
			Date captchaTime = dbCaptcha.getCaptchaExpiredTime();
			if (captchaTime.compareTo(currentTime) < 0) {
				response.setMessage(" Captcha Expired !!");
				response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
				throw new InvalidAuthenticationException(response);
			}
			if (!dbCaptcha.getCaptchaText().equals(captchaText)) {
				response.setMessage(" Captcha Not Match!!");
				response.setCode(ResponseCode.FORM_VALIDATION_ERROR);
				throw new InvalidAuthenticationException(response);
			}
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
