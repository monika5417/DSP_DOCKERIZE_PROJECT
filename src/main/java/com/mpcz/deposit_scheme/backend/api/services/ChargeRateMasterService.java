package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.ChargeRateMaster;

public interface ChargeRateMasterService {

	ChargeRateMaster findChargeRateByType(Long chargeTypeId);

	List<ChargeRateMaster> findAllChargeRates();

}
