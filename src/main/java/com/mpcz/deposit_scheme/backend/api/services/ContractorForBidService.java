package com.mpcz.deposit_scheme.backend.api.services;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerException;
import com.mpcz.deposit_scheme.backend.api.exception.DataNotFoundException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.InvalidAuthenticationException;
import com.mpcz.deposit_scheme.backend.api.request.ConsumerSignUpForm;
import com.mpcz.deposit_scheme.backend.api.request.DataStatusDTO;
import com.mpcz.deposit_scheme.backend.api.response.Response;

public interface ContractorForBidService {

	public Consumer saveConsumer(ConsumerSignUpForm consumerSignUpForm
//			MultipartFile docElectricityBill,
//			MultipartFile docResidentialProof, 
		/*	MultipartFile docAadhar, MultipartFile docPan*/) throws ConsumerException, DocumentTypeException;

	public Response<Consumer> save(final Consumer Consumer) throws ConsumerException;

	public Consumer findByConsumerLoginId(final String consumerLoginId) throws DataNotFoundException;

	public Consumer findByConsumerLoginIdAndAccountNonLocked(final String consumerLoginId,
			final Boolean accountNonLocked) throws InvalidAuthenticationException;

	public Consumer findByConsumerLoginIdAndAccountNonExpired(final String consumerLoginId,
			final Boolean accountNonExpired) throws InvalidAuthenticationException;

	public Response<Consumer> update(final Consumer consumer) throws ConsumerException;

	public Consumer findByMobileNo(final String consumerMobileNo) throws DataNotFoundException;

	public Consumer getCurrentLoginConsumerByLoginId() throws DataNotFoundException;

	public String isConsumerRecordExist(DataStatusDTO dataStatusDTO) throws ConsumerException;

}
