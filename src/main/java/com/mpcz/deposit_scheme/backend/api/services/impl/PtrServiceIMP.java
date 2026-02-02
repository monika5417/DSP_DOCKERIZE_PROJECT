package com.mpcz.deposit_scheme.backend.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxPtr;
import com.mpcz.deposit_scheme.backend.api.repository.CheckBoxPtrRepositroy;
import com.mpcz.deposit_scheme.backend.api.services.PtrService;
@Service
public class PtrServiceIMP implements PtrService {
	
	@Autowired
	private CheckBoxPtrRepositroy checkBoxPtrRepository;

	@Override
	public CheckBoxPtr save(CheckBoxPtr ptr) {
		return checkBoxPtrRepository.save(ptr);
		 
	}

	
	@Override
	public CheckBoxPtr getptrDetails(long id) {
		// TODO Auto-generated method stub
		CheckBoxPtr bCheckBoxDtrBD = checkBoxPtrRepository.findByConsumerApplicationId(id);
		 return bCheckBoxDtrBD;
		}
		
}