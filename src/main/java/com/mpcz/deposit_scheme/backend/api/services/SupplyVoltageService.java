package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.SupplyVoltage;
import com.mpcz.deposit_scheme.backend.api.exception.SupplyVoltageException;
import com.mpcz.deposit_scheme.backend.api.request.SupplyVoltageForm;

public interface SupplyVoltageService {

	public SupplyVoltage saveSupplyVoltage(SupplyVoltageForm supplyVoltageForm) throws SupplyVoltageException;

	public SupplyVoltage findBySupplyVoltageId(final Long supplyVoltageId) throws SupplyVoltageException;

	public List<SupplyVoltage> findAllSupplyVoltage() throws SupplyVoltageException;
}
