package com.mpcz.deposit_scheme.backend.api.services;

import java.util.Optional;

import com.mpcz.deposit_scheme.backend.api.domain.Otp;
import com.mpcz.deposit_scheme.backend.api.domain.OtpId;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.exception.OtpInvalidException;
import com.mpcz.deposit_scheme.backend.api.response.OTPResponse;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface OtpService {

	public Otp save(final Otp otp);

	public Optional<Otp> findByOtpId(final OtpId otpId);

	public void deleteAll();

	Response<OTPResponse> getLoginOtp(String consumerLoginId, Long tokenId)
			throws OtpInvalidException, InvalidAuthenticationException;

	Response<OTPResponse> getUserLoginOtp(String userLoginId, Long tokenId)
			throws OtpInvalidException, InvalidAuthenticationException;
	
	//Response<OTPResponse> getLoginOtp(String userId,Long tokenId) throws OtpInvalidException, InvalidAuthenticationException;

}
