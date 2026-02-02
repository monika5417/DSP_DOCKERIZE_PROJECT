package com.mpcz.deposit_scheme.backend.api.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAccountDetails;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;
import com.mpcz.deposit_scheme.backend.api.exception.BillDeskPaymentResponseException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.RefundAmountException;

public interface RefundAmountService {
	public RefundAmount saveRefundDetails(RefundAmount refundAmount) throws ConsumerApplicationDetailException;

	public RefundAmount getRefundApplicationDetails(String consumerApplicationNo);
	
	public List<RefundAmount> getAllRefundApplication();
	
	public RefundAmount saveConsumerApplicationCancel(RefundAmount refundAmount) throws ConsumerApplicationDetailException, BillDeskPaymentResponseException;

	public RefundAmount dgmApprovalForRefund(String consumerApplicationNo, boolean dgmApproval, String dgmId, String dgmName, String dgmRemark) throws ConsumerApplicationDetailException;

	public RefundAmount gmApprovalForRefund(String consumerApplicationNo, boolean gmApproval, String gmId, String gmName, String gmRemark) throws ConsumerApplicationDetailException;
	public List<ConsumerApplicationDetail> getAllApplicationByConsumerId(Long consumerId);
	
	public List<ConsumerApplicationDetail> getAllJeReturnAmountApp(Long consumerId);
	
	public RefundAmount dgmStcApprovalForRefund(String consumerApplicationNo, boolean dgmStcApproval, String dgmStcId, String dgmStcName, String dgmStcRemark) throws RefundAmountException, ConsumerApplicationDetailException;
	
	public Map<String, BigDecimal> getPaymentDetailForRefund(String consumerApplicationNo, Long value) throws ConsumerApplicationDetailException;

	public RefundAmount saveReturnAmntApplication(RefundAmount refundAmount) throws ConsumerApplicationDetailException;

	public RefundAmount financeAoRefundReject(String consumerApplicationNo, boolean financeAoRefundReject,
			String financeAoId, String financeRemark, String financeName) throws ConsumerApplicationDetailException, RefundAmountException;

	public ConsumerAccountDetails saveAddressProofData(String consumerApplicationNo, String idProofNo, MultipartFile docAddressProof, MultipartFile docNotry, MultipartFile docAuthorizedLetter,
			MultipartFile docMRA, MultipartFile docRequestLetter, MultipartFile docDebitSlipFile) throws ConsumerApplicationDetailException, DocumentTypeException;

	public RefundAmount financeRejectRefundApplicationAtDiscomOfficer(String consumerApplicationNo,
			boolean financeAoRefundReject, String financeAoId, String financeRemark, String financeName, String retrunDiscomName) throws ConsumerApplicationDetailException, RefundAmountException;

	public RefundAmount submitEFileNoByDGM(String consumerApplicationNo, String eFileNo) throws ConsumerApplicationDetailException;

	public RefundAmount financeAoAcceptanceAfterErpCompletion(String consumerApplicationNo, String financeErpRemark,
			String financeErpPaymentDate, String erpRefundVoucherNumber) throws ConsumerApplicationDetailException;

}
