package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.request.SchemeTypeForm;

public interface SchemeTypeService {

	public SchemeType saveSchemeType(SchemeTypeForm schemeTypeForm) throws SchemeTypeException;

	public SchemeType findBySchemeTypeId(final Long schemeTypeId) throws SchemeTypeException;

	public List<SchemeType> findAllSchemeType() throws SchemeTypeException;
}
