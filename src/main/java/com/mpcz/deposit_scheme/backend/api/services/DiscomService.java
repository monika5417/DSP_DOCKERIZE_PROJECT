package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.Discom;
import com.mpcz.deposit_scheme.backend.api.exception.DiscomException;
import com.mpcz.deposit_scheme.backend.api.request.DiscomForm;

public interface DiscomService {

	public Discom saveDiscom(DiscomForm discomForm) throws DiscomException;

	public Discom findByDiscomId(final Long discomId) throws DiscomException;

	public List<Discom> findAllDiscom() throws DiscomException;

	public Discom findDiscomById(Long discomId) throws DiscomException;

}
