package com.mpcz.deposit_scheme.backend.api.services;

import org.modelmapper.ModelMapper;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxPtr;

public interface PtrService {

	CheckBoxPtr save(CheckBoxPtr ptr);
	
	CheckBoxPtr getptrDetails(long id);

}
