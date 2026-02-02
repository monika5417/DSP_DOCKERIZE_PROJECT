package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt;
import com.mpcz.deposit_scheme.backend.api.repository.CheckBoxLtRepositroy;
import com.mpcz.deposit_scheme.backend.api.services.LtService;
@Service
public class LtServiceIMP implements LtService {
	
	@Autowired
	private CheckBoxLtRepositroy checkBoxLtRepository;

	@Override
	public CheckBoxLt save(CheckBoxLt lt) {
		return checkBoxLtRepository.save(lt);
		 
	}
	
	
	@Override
	public CheckBoxLt getLtDetails(long id) {
		// TODO Auto-generated method stub
		CheckBoxLt bCheckBoxDtrBD = checkBoxLtRepository.findByConsumerApplicationId(id);
		 return bCheckBoxDtrBD;
	}

}