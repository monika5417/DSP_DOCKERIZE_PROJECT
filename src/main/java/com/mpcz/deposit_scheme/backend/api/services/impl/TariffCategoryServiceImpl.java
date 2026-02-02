package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.TariffCategory;
import com.mpcz.deposit_scheme.backend.api.exception.TariffCategoryException;
import com.mpcz.deposit_scheme.backend.api.repository.TariffCategoryRepository;
import com.mpcz.deposit_scheme.backend.api.request.TariffCategoryForm;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.TariffCategoryService;

@Service
public class TariffCategoryServiceImpl implements TariffCategoryService {

	Logger logger = LoggerFactory.getLogger(TariffCategoryServiceImpl.class);

	@Autowired
	private TariffCategoryRepository tariffCategoryRepository;

	@Override
	public TariffCategory saveTariffCategory(TariffCategoryForm tariffCategoryForm) throws TariffCategoryException {

		final String method = "TariffCategoryServiceImpl : saveTariffCategory()";

		logger.info(method);

		Response<TariffCategory> response = new Response<>();

		TariffCategory tariffCategoryResponse = null;

		if (Objects.isNull(tariffCategoryForm)) {
			logger.error("Tariff Category object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new TariffCategoryException(response);
		} else {

			TariffCategory TariffCategory = new TariffCategory();

			TariffCategory.setTariffCategoryName(tariffCategoryForm.getTariffCategoryName().trim());
			TariffCategory.setTariffCategoryCode(tariffCategoryForm.getTariffCategoryCode().trim());
			TariffCategory.setTariffCategoryDescription(tariffCategoryForm.getTariffCategoryDescription().trim());

			tariffCategoryResponse = tariffCategoryRepository.save(TariffCategory);

			if (Objects.isNull(tariffCategoryResponse)) {
				logger.error("tariffCategoryRepository.save(TariffCategory) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new TariffCategoryException(response);
			} else {
				return tariffCategoryResponse;
			}

		}

	}

	@Override
	public TariffCategory findByTariffCategoryId(Long tariffCategoryId) throws TariffCategoryException {

		final String method = "TariffCategoryServiceImpl : findByTariffCategoryId(Long tariffCategoryId)";

		logger.info(method);

		final Optional<TariffCategory> tariffCategoryOptional = tariffCategoryRepository.findById(tariffCategoryId);
		final Response<TariffCategory> response = new Response<>();

		if (Objects.isNull(tariffCategoryOptional) || !tariffCategoryOptional.isPresent()) {
			logger.error("tariffCategoryRepository.findById is returning Null when findByTariffCategoryId call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new TariffCategoryException(response);
		} else {
			TariffCategory tariffCategory = tariffCategoryOptional.get();
			final List<TariffCategory> list = new ArrayList<>();
			list.add(tariffCategory);
			response.setList(list);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
			return tariffCategory;
		}
	}

	@Override
	public List<TariffCategory> findAllTariffCategory() throws TariffCategoryException {

		final String method = "TariffCategoryServiceImpl : findAllTariffCategory()";
		logger.info(method);

		List<TariffCategory> tariffCategory = tariffCategoryRepository.findAllActiveTariffCategory();

		final Response<TariffCategory> response = new Response<>();
		if (Objects.isNull(tariffCategory) || tariffCategory.isEmpty()) {
			logger.error("tariffCategoryRepository.findAllActiveTariffCategory is returning Null when findAllTariffCategory call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new TariffCategoryException(response);
		} else {
			logger.info("Response is returning successfully");
			return tariffCategory;
		}

	}
}
