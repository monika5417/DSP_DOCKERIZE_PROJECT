package com.mpcz.deposit_scheme.backend.api.services;

import org.modelmapper.ModelMapper;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt11;

public interface Lt11Service {

	CheckBoxLt11 save(CheckBoxLt11 lt11);
	
	CheckBoxLt11 getlt11Details(long id);

}
