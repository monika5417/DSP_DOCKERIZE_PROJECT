package com.mpcz.deposit_scheme.backend.api.services;

import org.modelmapper.ModelMapper;

import com.mpcz.deposit_scheme.backend.api.domain.CheckBoxDtr;

public interface DtrService {

	CheckBoxDtr save(CheckBoxDtr dtr);

	CheckBoxDtr getDtrDetails(long id);

}
