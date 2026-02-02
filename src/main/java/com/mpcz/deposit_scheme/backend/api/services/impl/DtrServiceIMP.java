package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxDtr;
import com.mpcz.deposit_scheme.backend.api.repository.CheckBoxDtrRepositroy;
import com.mpcz.deposit_scheme.backend.api.services.DtrService;
@Service
public class DtrServiceIMP implements DtrService {
	
	@Autowired
	private CheckBoxDtrRepositroy checkBoxDtrRepository;

	@Override
	public CheckBoxDtr save(CheckBoxDtr dtr) {
		return checkBoxDtrRepository.save(dtr);
		 
	}

	@Override
	public CheckBoxDtr getDtrDetails(long id) {
		// TODO Auto-generated method stub
		 CheckBoxDtr bCheckBoxDtrBD = checkBoxDtrRepository.findByConsumerApplicationId(id);
		 return bCheckBoxDtrBD;
	}

}
