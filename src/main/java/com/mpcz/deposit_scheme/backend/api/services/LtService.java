package com.mpcz.deposit_scheme.backend.api.services;

import org.modelmapper.ModelMapper;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxLt;

public interface LtService {

	CheckBoxLt save(CheckBoxLt lt);

	CheckBoxLt getLtDetails(long id);
}
