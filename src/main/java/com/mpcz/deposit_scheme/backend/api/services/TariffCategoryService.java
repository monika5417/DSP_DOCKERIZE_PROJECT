package com.mpcz.deposit_scheme.backend.api.services;

import java.util.List;

import com.mpcz.deposit_scheme.backend.api.domain.TariffCategory;
import com.mpcz.deposit_scheme.backend.api.exception.TariffCategoryException;
import com.mpcz.deposit_scheme.backend.api.request.TariffCategoryForm;

public interface TariffCategoryService {
	
	public TariffCategory saveTariffCategory(TariffCategoryForm tariffCategoryForm) throws TariffCategoryException;

	public TariffCategory findByTariffCategoryId(final Long tariffCategoryId) throws TariffCategoryException;

	public List<TariffCategory> findAllTariffCategory() throws TariffCategoryException;
}
