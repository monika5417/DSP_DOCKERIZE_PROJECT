package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt11;
import com.mpcz.deposit_scheme.backend.api.repository.CheckBoxLt11Repositroy;
import com.mpcz.deposit_scheme.backend.api.services.Lt11Service;
@Service
public class Lt11ServiceIMP implements Lt11Service {
	
	@Autowired
	private CheckBoxLt11Repositroy checkBoxLt11Repository;

	@Override
	public CheckBoxLt11 save(CheckBoxLt11 lt11) {
		return checkBoxLt11Repository.save(lt11);
		 
	}

	
	@Override
	public CheckBoxLt11 getlt11Details(long id) {
		// TODO Auto-generated method stub
		CheckBoxLt11 bCheckBoxDtrBD = checkBoxLt11Repository.findByConsumerApplicationId(id);
		 return bCheckBoxDtrBD;
	}
}