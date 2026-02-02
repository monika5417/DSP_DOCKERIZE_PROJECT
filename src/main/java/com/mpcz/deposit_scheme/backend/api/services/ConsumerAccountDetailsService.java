package com.mpcz.deposit_scheme.backend.api.services;

import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAccountDetails;
import com.mpcz.deposit_scheme.backend.api.domain.DuplicateRefund;
import com.mpcz.deposit_scheme.backend.api.domain.RefundRejectedRemark;
import com.mpcz.deposit_scheme.backend.api.dto.DuplicatePaymentRefundDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;

public interface ConsumerAccountDetailsService {

	ConsumerAccountDetails save(String consumerAccountDetails, MultipartFile chequeBookOrPaasbook,MultipartFile docPanNo, MultipartFile docAddressProof, MultipartFile docNotry, MultipartFile docAuthorizedLetter, MultipartFile docRequestLetter) throws DocumentTypeException, ConsumerApplicationDetailException;

	DuplicatePaymentRefundDto getConsumerApplicationDuplicateRefundDetails(String consumerApplicationNo) throws ConsumerApplicationDetailException;

	DuplicateRefund saveDuplicateRefundDetails(DuplicateRefund duplicateRefund) throws ConsumerApplicationDetailException, ErpEstimateAmountException;

	ConsumerAccountDetails getConsumerAccountDetails(String consumerApplicationNo);

	DuplicateRefund dgmApprovalForDuplicateRefund(String consumerApplicationNo, boolean dgmApproval, String dgmId, String dgmName) throws ConsumerApplicationDetailException;

	DuplicateRefund gmApprovalForDuplicateRefund(String consumerApplicationNo, boolean gmApproval, String gmId,
			String gmName) throws ConsumerApplicationDetailException;

	DuplicateRefund financeAoDuplicateRefund(String consumerApplicationNo, boolean financeApproval,
			String financeAoId, String financeName) throws ConsumerApplicationDetailException;

}
