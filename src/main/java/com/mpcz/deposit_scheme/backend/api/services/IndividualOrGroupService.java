package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.IndividualOrGroup;
import com.mpcz.deposit_scheme.backend.api.domain.LandAreaUnit;
import com.mpcz.deposit_scheme.backend.api.exception.IndividualOrGroupException;
import com.mpcz.deposit_scheme.backend.api.exception.LandAreaUnitException;

public interface IndividualOrGroupService {
	
	List<IndividualOrGroup> findAllIndividualOrGroup() throws IndividualOrGroupException;

	IndividualOrGroup findById(Long indivisualId) throws IndividualOrGroupException;


}
