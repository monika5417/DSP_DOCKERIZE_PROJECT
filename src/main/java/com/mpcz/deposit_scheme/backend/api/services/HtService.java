package com.mpcz.deposit_scheme.backend.api.services;

import org.modelmapper.ModelMapper;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxHt;

public interface HtService {

	CheckBoxHt save(CheckBoxHt ht);
	
	CheckBoxHt getHtDetails(long id);

}
