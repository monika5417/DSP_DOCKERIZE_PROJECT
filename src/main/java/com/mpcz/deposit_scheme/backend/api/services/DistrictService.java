package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.District;
import com.mpcz.deposit_scheme.backend.api.exception.DistrictException;

public interface DistrictService {
	
	

	List<District> findAllDistrict()throws DistrictException;

	District findDistrictById(long districtId) throws DistrictException;

}
