package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorDetail;
import com.mpcz.deposit_scheme.backend.api.exception.ContractorDetailException;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorDetailRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ContractorDetailService;

@Service
public class ContractorDetailServiceImpl implements ContractorDetailService {

	Logger logger = LoggerFactory.getLogger(ContractorDetailServiceImpl.class);

	@Autowired
	private ContractorDetailRepository contractorDetailRepository;

	@Override
	public ContractorDetail saveContractorDetail(ContractorDetail contractorDetail) throws ContractorDetailException {

		final String method = "ContractorDetailServiceImpl : saveContractorDetail()";

		logger.info(method);

		Response<ContractorDetail> response = new Response<>();

		if (Objects.isNull(contractorDetail)) {
			logger.error("Contractor Detail object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ContractorDetailException(response);
		} else {
			ContractorDetail contractorDetailResponse = new ContractorDetail();

			contractorDetail.setContractorUserId(contractorDetail.getContractorUserId());
			contractorDetail.setContractorAuthenticationId(contractorDetail.getContractorAuthenticationId().trim());
			contractorDetail.setContractorUserClass(contractorDetail.getContractorUserClass().trim());
			contractorDetail.setContractorUserZone(contractorDetail.getContractorUserZone().trim());
			contractorDetail.setContractorCompanyName(contractorDetail.getContractorCompanyName().trim());
			contractorDetail.setContractorMobile(contractorDetail.getContractorMobile().trim());
			contractorDetail.setContractorEmail(contractorDetail.getContractorEmail().trim());
			contractorDetail.setContractorAddress(contractorDetail.getContractorAddress().trim());

			contractorDetailResponse = contractorDetailRepository.save(contractorDetail);

			if (Objects.isNull(contractorDetailResponse)) {
				logger.error("contractorDetailRepository.save(contractorDetail) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ContractorDetailException(response);
			} else {
				return contractorDetailResponse;
			}

		}

	}

	@Override
	public ContractorDetail findByContractorUserId(Long contractorUserId) throws ContractorDetailException {

		final String method = "ContractorDetailServiceImpl : findByContractorUserId(Long contractorUserId)";

		logger.info(method);

		final Response<ContractorDetail> response = new Response<>();

		ContractorDetail contractorDetailDb = null;
		Optional<ContractorDetail> contractorDetailOptional = null;
		try {
			contractorDetailOptional = contractorDetailRepository.findByContractorUserId(contractorUserId);
			if (contractorDetailOptional.isPresent()) {
				contractorDetailDb = contractorDetailOptional.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contractorDetailDb;
	}

}
