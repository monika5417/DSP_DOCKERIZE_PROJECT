package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;

public interface SMSDirectService {
	public String sendMessage(SMSRequest smsRequest) throws Exception;
}
