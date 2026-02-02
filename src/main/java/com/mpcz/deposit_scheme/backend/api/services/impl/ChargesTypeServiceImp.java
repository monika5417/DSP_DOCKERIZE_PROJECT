package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.ChargesType;
import com.mpcz.deposit_scheme.backend.api.repository.ChargesTypeRepository;
import com.mpcz.deposit_scheme.backend.api.services.ChargesTypeService;
@Service
public class ChargesTypeServiceImp implements ChargesTypeService {
	
	@Autowired
	private ChargesTypeRepository chargesTypeRepository;

	@Override
	public ChargesType findById(long l) {
		
	Optional<ChargesType> chargesType=	 chargesTypeRepository.findById(l);
	return chargesType.get();
	}

}
