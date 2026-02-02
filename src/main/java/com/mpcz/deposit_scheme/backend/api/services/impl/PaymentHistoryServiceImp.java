package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentHistory;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.repository.PaymentHistoryRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.PaymentHistoryService;

@Service
public class PaymentHistoryServiceImp implements PaymentHistoryService {

	Logger logger = LoggerFactory.getLogger(PaymentHistoryServiceImp.class);

	@Autowired
	private PaymentHistoryRepository paymentHistoryRepository;

	@Override
	public Response<PaymentHistory> save(PaymentHistory history) throws PaymentHistoryException {
	

		final String method = "PaymentHistoryServiceImp : save()";
		logger.info(method);

		final Response<PaymentHistory> response = new Response<>();

		if (Objects.isNull(history)) {
			logger.error("Circle object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new PaymentHistoryException(response);
		} else {
System.out.println(history);
	PaymentHistory paymentHistroy =   paymentHistoryRepository.save(history);

			if (Objects.isNull(paymentHistroy)) {
				logger.error("repository.save(payment history ) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new PaymentHistoryException(response);
			} else {
				logger.info("Response is returning successfully");
				response.setCode(HttpCode.CREATED);
				response.setMessage(ResponseMessage.RECORD_INSERTED_MESSAGE);
				final List<PaymentHistory> list = new ArrayList<>();
				list.add(paymentHistroy);
				response.setList(list);

				return response;
			}
		}
		}

	@Override
	public PaymentHistory findByApplicationNo(String applicationNo) throws PaymentHistoryException {
		final String method = "PaymentHistoryServiceImp : save()";
		logger.info(method);

		final Response<PaymentHistory> response = new Response<>();

		if (Objects.isNull(applicationNo)) {
			logger.error("Circle object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new PaymentHistoryException(response);
		} else {

	PaymentHistory paymentHistroy =   paymentHistoryRepository.findByApplicationNo(applicationNo);

      	return paymentHistroy;
		}
	}
	
	@Override
	public List<PaymentHistory> findAllApplication() throws PaymentHistoryException {
		final String method = "DivisionServiceImpl : findAll()";
		logger.info(method);
		Response response = new Response<>();
		List<PaymentHistory> paymentHistories = paymentHistoryRepository.findAll();
		if (Objects.isNull(paymentHistories) || paymentHistories.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new PaymentHistoryException(response);
		} else {
			response.setList(paymentHistories);
			logger.info("Response is returning successfully");
			response.setCode(HttpCode.OK);
			response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
			return paymentHistories;
		}
	}

}
