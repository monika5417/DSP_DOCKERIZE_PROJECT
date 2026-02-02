package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxHt;
import com.mpcz.deposit_scheme.backend.api.repository.CheckBoxHtRepositroy;
import com.mpcz.deposit_scheme.backend.api.services.HtService;
@Service
public class HtServiceIMP implements HtService {
	
	@Autowired
	private CheckBoxHtRepositroy checkBoxHtRepository;

	@Override
	public CheckBoxHt save(CheckBoxHt ht) {
		return checkBoxHtRepository.save(ht);
		 
	}
	
	@Override
	public CheckBoxHt getHtDetails(long id) {
		// TODO Auto-generated method stub
		CheckBoxHt bCheckBoxDtrBD = checkBoxHtRepository.findByConsumerApplicationId(id);
		 return bCheckBoxDtrBD;
	}

}