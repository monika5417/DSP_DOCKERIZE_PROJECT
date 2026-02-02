package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.exception.LandAreaUnitException;


public interface LandAreaUnitService {

	List<LandAreaUnit> findAllLandAreaUnit() throws LandAreaUnitException;

	LandAreaUnit findById(long feederId) throws LandAreaUnitException;

}
