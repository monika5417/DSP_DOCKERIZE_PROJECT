package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationDocument;
import com.mpcz.deposit_scheme.backend.api.domain.BeforeRefundAmountCheck;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAccountDetails;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAppCancellationRefundAmount;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerAppReturnMaterialRefundAmnt;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.domain.ManualPayment;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.domain.RefundAmount;
import com.mpcz.deposit_scheme.backend.api.domain.Upload;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DocumentTypeException;
import com.mpcz.deposit_scheme.backend.api.exception.RefundAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationDocumentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ApplicationStatusRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BeforeRefundAmountCheckRepository;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAccountDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAppCancellationRefundAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerAppReturnMaterialRefundAmntRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ManualPaymentRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.repository.RefundAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.RefundRejectedRemarkRepository;
import com.mpcz.deposit_scheme.backend.api.repository.UserRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.RefundAmountService;
import com.mpcz.deposit_scheme.backend.api.services.UploadService;
import com.mpcz.deposit_scheme.dryprincipalutility.DryUtility;

@Service
public class RefundAmountServiceImpl implements RefundAmountService {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private ErpRevRepository erpRevRepository;

	@Autowired
	private RefundAmountRepository refundAmountRepository;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ConsumerAppCancellationRefundAmountRepository consumerAppCancellationRefundAmountRepository;

	@Autowired
	private ConsumerAppReturnMaterialRefundAmntRepository consumerAppReturnMaterialRefundAmntRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MmkyPayAmountRespository mmkyPayAmountRespository;

	private DryUtility dryUtility;

	@Autowired
	private WorkCompletionChangStautsByDgmOAndMServiceIMp workCompletionChangStautsByDgmOAndMServiceIMp;

	@Autowired
	private RefundRejectedRemarkRepository refundRejectedRemarkRepository;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDataRepository;

	@Autowired
	private ManualPaymentRepository manualPaymentRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RefundAmount saveRefundDetails(RefundAmount refundAmount) throws ConsumerApplicationDetailException {

		ConsumerApplicationDetail consumerAppDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
		if (Objects.isNull(consumerAppDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application not found in database"));
		} else {
			RefundAmount refund = refundAmountRepository
					.findByConsumerApplicationNo(refundAmount.getConsumerApplicationNo());
			if (!Objects.isNull(refund)) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NOT_ACCEPTABLE, "Refund request already initiated for application no.: "
								+ refund.getRefundType() + refundAmount.getConsumerApplicationNo()));
			}
			ErpRev erpRev = erpRevRepository.findByConsAppNo(refundAmount.getConsumerApplicationNo());
			if (Objects.isNull(erpRev)) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Application not found in Revised Estimate database"));
			}
			if (erpRev.getPayAmt() != null && erpRev.getPayAmt().compareTo(BigDecimal.ZERO) < 0) {

				BigDecimal payAmt = erpRev.getPayAmt();
				BigDecimal remCgst = erpRev.getRemCgst();
				BigDecimal remSgst = erpRev.getRemSgst();
				BigDecimal remCgstSgst = remCgst.add(remSgst);
//				System.err.println("aaaaaaaaaaaa : " + payAmt.subtract(remCgstSgst));

				if ((erpRev.getPayAmt().compareTo(BigDecimal.ZERO) < 0)
						&& (erpRev.getRemCgst().compareTo(BigDecimal.ZERO) < 0)) {
					refundAmount.setRefundAmount(payAmt.subtract(remCgstSgst));
				} else {
					refundAmount.setRefundAmount(payAmt.add(remCgstSgst));
				}

				System.out.println("Pay amount is negative.");
			} else {
				// payAmt is either null or non-negative
				System.out.println("Pay amount is either null or non-negative.");
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NOT_ACCEPTABLE, "Revised amount is not negative"));
			}

			if (consumerAppDetail.getErpVersion() != null && consumerAppDetail.getErpVersion().equals("V2")) {
				refundAmount.setReviseErpNo(consumerAppDetail.getRevisedErpNumber());
			} else {
				refundAmount.setDemandErpNo(consumerAppDetail.getErpWorkFlowNumber());
			}

			refundAmount.setRefundType("Revise_Amount");
			double digit = Math.random();
			double digit1 = Math.random();
			String valueOf = String.valueOf(digit);
			String valueOf1 = String.valueOf(digit1);
			String substring = valueOf.substring(2, 8);
			String substring1 = valueOf1.substring(2, 8);
			refundAmount.setRefundVoucherNo(substring + substring1);
			refundAmount.setConsumerAppId(consumerAppDetail.getConsumerApplicationId());
			RefundAmount save = refundAmountRepository.save(refundAmount);
			if (save != null) {
				consumerAppDetail.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId()).get());
				consumerApplictionDetailRepository.save(consumerAppDetail);
			}
			return save;
		}
	}

	@Override
	public RefundAmount getRefundApplicationDetails(String consumerApplicationNo) {
		return refundAmountRepository.findByConsumerApplicationNoIsActive(consumerApplicationNo);
	}

	@Override
	public List<RefundAmount> getAllRefundApplication() {
		return refundAmountRepository.findAll();
	}

//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public RefundAmount saveConsumerApplicationCancel(RefundAmount refundAmount)
//			throws ConsumerApplicationDetailException {
//
//		BigDecimal refundableAmount = BigDecimal.ZERO;
//		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
//		if (Objects.isNull(consumerApplicationDetail)) {
//			throw new ConsumerApplicationDetailException(
//					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
//		} else {
//			List<BillDeskPaymentResponse> allPaymentDetails = billPaymentResponseeeeeeeRepository
//					.getAllPaymentDetails(refundAmount.getConsumerApplicationNo());
//
//			for (BillDeskPaymentResponse bill : allPaymentDetails) {
//				ConsumerAppCancellationRefundAmount conAppCancel = new ConsumerAppCancellationRefundAmount();
//				conAppCancel.setApplicationNo(refundAmount.getConsumerApplicationNo());
//				conAppCancel.setMerchantId(bill.getMercid());
//				conAppCancel.setOrderId(bill.getOrderid());
//				conAppCancel.setPaymentType(bill.getAdditionalInfo());
//				conAppCancel.setTxnAmount(new BigDecimal(bill.getAmount()));
//				conAppCancel.setTxnId(bill.getTranId());
//				conAppCancel.setTransactionDate(bill.getTransactionDate());
//				ConsumerAppCancellationRefundAmount byTxnId = consumerAppCancellationRefundAmountRepository
//						.findByTxnIdIsActive(bill.getTranId());
//				if (byTxnId == null) {
//					ConsumerAppCancellationRefundAmount save = consumerAppCancellationRefundAmountRepository.save(conAppCancel);
//					System.err.println("aaaaaaaaaaa: " +new Gson().toJson(save));
//				}
//
//			}
//
//			if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
//				MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
//						.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
//
//				refundAmount.setRefundAmount(
//						findByConsumerApplicationNumber.getPayableAmount().subtract(new BigDecimal(2500)));
//
//			} else {
//				List<ErpRev> erpRevDB = erpRevRepository.findByConsumerAppNo(refundAmount.getConsumerApplicationNo());
//				if (!erpRevDB.isEmpty()) {
//					for (ErpRev erpRev : erpRevDB) {
//						System.err.println("erp aaaaaa : " + erpRev.getPayAmt());
//
//						ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//								.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
//										erpRev.getPayAmt());
//						if (erpRev.getRemCgst() != null) {
//							BigDecimal remCgst = erpRev.getRemCgst();
//							BigDecimal remSgst = erpRev.getRemSgst();
//							BigDecimal totalCgstSgst = remCgst.add(remSgst);
//
//							refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
//						} else {
//							refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
//						}
//						applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
//						consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);
//
//						totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//					}
//				}
//				ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//						.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//				ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = null;
//				if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
//					applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//							.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
//									erpEstimateDB.getTotalBalanceSupervisionAmount());
//				} else {
//					applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//							.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
//									erpEstimateDB.getTotalBalanceDepositAmount());
//				}
//
//				if (erpEstimateDB.getCgst() != null) {
//
//					BigDecimal cgst = erpEstimateDB.getCgst();
//					BigDecimal sgst = erpEstimateDB.getSgst();
//					BigDecimal totalCgstSgst = cgst.add(sgst);
//					refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
//				} else {
//					refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
//				}
//				applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
//				consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);
//				totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//				refundAmount.setRefundAmount(totalWapaskrneWalaPaisa);
//			}
//
//		}
//
//		refundAmount.setConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo());
//		refundAmount.setConsumerAppId(consumerApplicationDetail.getConsumerApplicationId());
//		refundAmount.setRefundType("Cancellation_Amount");
//		double digit = Math.random();
//		double digit1 = Math.random();
//		String valueOf = String.valueOf(digit);
//		String valueOf1 = String.valueOf(digit1);
//		String substring = valueOf.substring(2, 8);
//		String substring1 = valueOf1.substring(2, 8);
//		refundAmount.setRefundVoucherNo(substring + substring1);
//
//		RefundAmount refundAmntDB = null;
//		RefundAmount byConsumerApplicationNoDB = refundAmountRepository
//				.findByConsumerApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo());
//		if (byConsumerApplicationNoDB == null) {
//			refundAmntDB = refundAmountRepository.save(refundAmount);
//		} else {
//			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
//					"Refund already initiated for this application no. "
//							+ consumerApplicationDetail.getConsumerApplicationNo() + " for refund Type : "
//							+ byConsumerApplicationNoDB.getRefundType()));
//		}
//		if (Objects.isNull(refundAmntDB)) {
//			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
//		} else {
//			consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
//					.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
//			consumerApplictionDetailRepository.save(consumerApplicationDetail);
//			return refundAmntDB;
//		}
//
//	}
// above code commented because the code is only written for fetching the data from billdesk
	@Override
	public RefundAmount dgmApprovalForRefund(String consumerApplicationNo, boolean dgmApproval, String dgmId,
			String dgmName, String dgmRemark) throws ConsumerApplicationDetailException {
		RefundAmount save = null;
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {
			RefundAmount refundAmount = refundAmountRepository
					.findByConsumerApplicationNoIsActive(consumerApplicationNo);
			if (Objects.isNull(refundAmount)) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "No Active application found in Refund Table"));
			} else {
				if (dgmApproval == true) {
					refundAmount.setDgmApproval("true");
					refundAmount.setDgmApprovedId(dgmId);
					refundAmount.setDgmApprovedName(dgmName);
					refundAmount.setDgmApprovedDate(Date.valueOf(LocalDate.now()).toString());
					refundAmount.setDgmRemark(dgmRemark);
					save = refundAmountRepository.save(refundAmount);
					consumerApplicationDetail
							.setApplicationStatus(refundAmount.getRefundType().equals("Cancellation_Amount")
									? applicationStatusRepository
											.findById(
													ApplicationStatusEnum.APPLICATION_PENDING_AT_GM_FOR_REFUND.getId())
											.get()
									: applicationStatusRepository.findById(
											ApplicationStatusEnum.APPLICATION_PENDING_AT_FINANCE_AO_FOR_REFUND.getId())
											.get());
					consumerApplictionDetailRepository.save(consumerApplicationDetail);
				} else {
					refundAmount.setDgmApproval("false");
					refundAmount.setDgmApprovedId(dgmId);
					refundAmount.setDgmApprovedName(dgmName);
					refundAmount.setDgmRemark(dgmRemark);
					refundAmount.setDgmApprovedDate(Date.valueOf(LocalDate.now()).toString());
					save = refundAmountRepository.save(refundAmount);
					deactivePreviousRefundApplication(refundAmount.getRefundType(), consumerApplicationDetail);

				}
				return save;
			}
		}

	}

	@Override
	public RefundAmount gmApprovalForRefund(String consumerApplicationNo, boolean gmApproval, String gmId,
			String gmName, String gmRemark) throws ConsumerApplicationDetailException {
		RefundAmount save = null;

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {
			RefundAmount refundAmount = refundAmountRepository
					.findByConsumerApplicationNoIsActive(consumerApplicationNo);
			if (Objects.isNull(refundAmount)) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Application Not Found Refund Table"));
			} else {
				if (gmApproval == true) {
					refundAmount.setGmApproval("true");
					refundAmount.setGmApprovedId(gmId);
					refundAmount.setGmApprovedName(gmName);
					refundAmount.setGmApprovedDate(Date.valueOf(LocalDate.now()).toString());
					refundAmount.setGmRemark(gmRemark);
					save = refundAmountRepository.save(refundAmount);
					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
							.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_FINANCE_AO_FOR_REFUND.getId())
							.get());
					consumerApplictionDetailRepository.save(consumerApplicationDetail);
				} else {
					refundAmount.setGmApproval("false");
					refundAmount.setGmApprovedId(gmId);
					refundAmount.setGmApprovedName(gmName);
					refundAmount.setGmApprovedDate(Date.valueOf(LocalDate.now()).toString());
					refundAmount.setGmRemark(gmRemark);
					save = refundAmountRepository.save(refundAmount);
//					TOOK "Cancellation_Amount" HERE because dgm can only accept or reject cancellation application 29-04-2025
					deactivePreviousRefundApplication("Cancellation_Amount", consumerApplicationDetail);
//					29-04-2025
				}

				return save;
			}
		}

	}

	@Override
	public RefundAmount dgmStcApprovalForRefund(String consumerApplicationNo, boolean dgmStcApproval, String dgmStcId,
			String dgmStcName, String dgmStcRemark) throws RefundAmountException, ConsumerApplicationDetailException {
		RefundAmount save = null;
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {
			RefundAmount refundAmount = refundAmountRepository
					.findByConsumerApplicationNoIsActive(consumerApplicationNo);
			if (Objects.isNull(refundAmount)) {
				throw new RefundAmountException(
						new Response(HttpCode.NULL_OBJECT, "Application not found in database"));
			} else {
				if (dgmStcApproval) {
					refundAmount.setDgmStcApprovedId(dgmStcId);
					refundAmount.setDgmStcApproval("true");
					refundAmount.setDgmStcApprovedName(dgmStcName);
					refundAmount.setDgmStcRemark(dgmStcRemark);
					refundAmount.setDgmStcApprovedDate(Date.valueOf(LocalDate.now()).toString());
					save = refundAmountRepository.save(refundAmount);
//					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
//							.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
							.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_FINANCE_AO_FOR_REFUND.getId())
							.get());
//					above status change for return amount because now the application will directly move to Finance 18-11-2025
					consumerApplictionDetailRepository.save(consumerApplicationDetail);
				} else {
					refundAmount.setDgmStcApprovedId(dgmStcId);
					refundAmount.setDgmStcApproval("false");
					refundAmount.setDgmStcApprovedName(dgmStcName);
					refundAmount.setDgmStcApprovedDate(Date.valueOf(LocalDate.now()).toString());
					refundAmount.setDgmStcRemark(dgmStcRemark);
					save = refundAmountRepository.save(refundAmount);
//						TOOK "Cancellation_Amount" HERE because dgmStc can only accept or reject Return_Amount application 29-04-2025
					deactivePreviousRefundApplication("Return_Amount", consumerApplicationDetail);
//						29-04-2025
				}
				return save;
			}

		}
	}

	@Override
	public List<ConsumerApplicationDetail> getAllApplicationByConsumerId(Long consumerId) {
		return consumerApplictionDetailRepository.findByConsumerId(consumerId);
	}

	@Override
	public List<ConsumerApplicationDetail> getAllJeReturnAmountApp(Long consumerId) {
		return consumerApplictionDetailRepository.findAllJeReturnAmountApp(consumerId);
	}

	@Autowired
	private BeforeRefundAmountCheckRepository beforeRefundAmountCheckRepository;

//	getPaymentDetailForRefund and saveReturnAmntApplication y method comment ki hai kyuki inme Sirf Billdesk payment refund
//	code hai Sanchay wala nahi isliye updated method neeche likhi hui hai 27-06-2025

//	@Override
//	public Map<String, BigDecimal> getPaymentDetailForRefund(String consumerApplicationNo, Long value)
//			throws ConsumerApplicationDetailException {
//		BigDecimal refundableAmount = BigDecimal.ZERO;
//		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//
//		Map<String, BigDecimal> result = new HashMap<>();
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(consumerApplicationNo);
//		if (Objects.isNull(consumerApplicationDetail)) {
//			throw new ConsumerApplicationDetailException(
//					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
//		} else {
//			List<BillDeskPaymentResponse> allPaymentDetails = null;
//			try {
//				allPaymentDetails = billPaymentResponseeeeeeeRepository.getAllPaymentDetails(consumerApplicationNo);
//				System.err.println(allPaymentDetails);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
////			value == 1 matlb (application cancle karna hai)
//			if (value == 1L) {
//				for (BillDeskPaymentResponse bill : allPaymentDetails) {
//					// cancellation ka total amount dena hai
//
//					if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
//						ErpRev erpRevDB = erpRevRepository.findByConsAppNoAndPayAmt(consumerApplicationNo,
//								new BigDecimal(bill.getAmount()));
//						if (erpRevDB != null) {
//
//							System.err.println("erp aaaaaa : " + erpRevDB.getPayAmt());
//// erpRevDB.getRemCgst() != null y check isliye lagaya h kyuki kabhi kabhi remCgst remSgst me null hota kyuki data remColonyOrSlum me hota hai
//							if (erpRevDB.getRemCgst() != null) {
//								BigDecimal remCgst = erpRevDB.getRemCgst();
//								BigDecimal remSgst = erpRevDB.getRemSgst();
//								BigDecimal totalCgstSgst = remCgst.add(remSgst);
//
//								BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//								refundableAmount = billdeskAmount.subtract(totalCgstSgst);
//							} else {
//								BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//								refundableAmount = billdeskAmount;
//							}
//							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//						}
//					} else {
//						if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
//							MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
//									.findByConsumerApplicationNumber(consumerApplicationNo);
//
//							BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//							if (billdeskAmount.compareTo(findByConsumerApplicationNumber.getPayableAmount()) == 0) {
//								totalWapaskrneWalaPaisa = new BigDecimal(bill.getAmount())
//										.subtract(new BigDecimal(2500));
//							}
//
//							else {
//								totalWapaskrneWalaPaisa = new BigDecimal(0.0);
//							}
//						} else {
//
//							ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//									.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//							if (erpEstimateDB.getCgst() != null) {
//
//								BigDecimal cgst = erpEstimateDB.getCgst();
//								BigDecimal sgst = erpEstimateDB.getSgst();
//								BigDecimal totalCgstSgst = cgst.add(sgst);
//								BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//								refundableAmount = billdeskAmount.subtract(totalCgstSgst);
//							} else {
//								BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//								refundableAmount = billdeskAmount;
//							}
//
//							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//						}
//					}
//				}
//			} else { // return ka amount dena hai
//				for (BillDeskPaymentResponse bill : allPaymentDetails) {
//
//					if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
//						ErpRev erpRev = erpRevRepository.findByConsAppNo(consumerApplicationNo);
//						if (erpRev != null && erpRev.getRemReturnAmt() != null) {
//							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpRev.getRemReturnAmt());
//						}
//					} else {
//						ErpEstimateAmountData erpData = estimateAmountRepository
//								.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//						if (erpData != null && erpData.getJeReturnAmount() != null) {
//							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//						}
//					}
//
//				}
//			}
//			BeforeRefundAmountCheck byApplicationNo = beforeRefundAmountCheckRepository
//					.findByApplicationNo(consumerApplicationNo);
//			if (byApplicationNo == null) {
//				BeforeRefundAmountCheck check = new BeforeRefundAmountCheck();
//				check.setApplicationNo(consumerApplicationNo);
//				check.setRefundableAmount(totalWapaskrneWalaPaisa);
//				beforeRefundAmountCheckRepository.save(check);
//			} else {
//				byApplicationNo.setRefundableAmount(totalWapaskrneWalaPaisa);
//				beforeRefundAmountCheckRepository.save(byApplicationNo);
//			}
//
//			result.put("totalRefundAmount", totalWapaskrneWalaPaisa);
//			return result;
//		}
//	}

	@Override
	public RefundAmount financeAoRefundReject(String consumerApplicationNo, boolean financeAoRefundReject,
			String financeAoId, String financeRemark, String financeName)
			throws ConsumerApplicationDetailException, RefundAmountException {
		RefundAmount save = null;
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {

			RefundAmount refundAmount = refundAmountRepository
					.findByConsumerApplicationNoIsActive(consumerApplicationNo);
			if (Objects.isNull(refundAmount)) {
				throw new RefundAmountException(
						new Response(HttpCode.NULL_OBJECT, "Active Application not found in Refund database"));
			} else {
				if (financeAoRefundReject) {
					refundAmount.setFinanceId(financeAoId);
					refundAmount.setFinanceApproval("true");
					refundAmount.setFinanceName(financeAoId);
					refundAmount.setFinanceName(financeName);
					refundAmount.setFinanceRemark(financeRemark);
					refundAmount.setFinanceApprovedDate(Date.valueOf(LocalDate.now()).toString());
					refundAmount.setApplicationStatusId(49L);
					save = refundAmountRepository.save(refundAmount);
					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
							.findById(ApplicationStatusEnum.PENDING_AT_ERP_END_FOR_INVOICE_CREATION.getId()).get());
					consumerApplictionDetailRepository.save(consumerApplicationDetail);

					if (consumerApplicationDetail.getNscApplicationNo() != null) {
						workCompletionChangStautsByDgmOAndMServiceIMp.sendDataToSSp1(consumerApplicationDetail);
					}

				} else {
					refundAmount.setFinanceId(financeAoId);
					refundAmount.setFinanceApproval("false");
					refundAmount.setFinanceName(financeAoId);
					refundAmount.setFinanceName(financeName);
					refundAmount.setFinanceRemark(financeRemark);
					refundAmount.setFinanceApprovedDate(Date.valueOf(LocalDate.now()).toString());
					save = refundAmountRepository.save(refundAmount);

					deactivePreviousRefundApplication(refundAmount.getRefundType(), consumerApplicationDetail);

				}
			}

			return save;
		}
	}

	@Autowired
	private ConsumerAccountDetailsRepository consumerAccountDetailsRepository;

	@Transactional(rollbackFor = Exception.class)
	public void deactivePreviousRefundApplication(String refundType,
			ConsumerApplicationDetail consumerApplicationDetail) {

		RefundAmount refundApplicationNoDB = refundAmountRepository
				.findByConsumerApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo());
		if (refundApplicationNoDB != null) {
			refundApplicationNoDB.setActive(false);
			refundApplicationNoDB.setDeleted(true);
			refundAmountRepository.save(refundApplicationNoDB);
		}
		beforeRefundAmountCheckRepository
				.findByApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo()).stream()
				.forEach(beforeApp -> {
					beforeApp.setActive(false);
					beforeApp.setDeleted(true);
					beforeRefundAmountCheckRepository.save(beforeApp);
				});

		ConsumerAccountDetails conusmerAccountDetailsDB = consumerAccountDetailsRepository
				.findByConsumerApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo());
		if (conusmerAccountDetailsDB != null) {
			conusmerAccountDetailsDB.setActive(false);
			conusmerAccountDetailsDB.setDeleted(true);
			consumerAccountDetailsRepository.save(conusmerAccountDetailsDB);
		}

		if ("Cancellation_Amount".equals(refundType)) {
			consumerAppCancellationRefundAmountRepository
					.findByApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo()).stream()
					.forEach(app -> {
						app.setActive(false);
						app.setDeleted(true);
						consumerAppCancellationRefundAmountRepository.save(app);
					});
			consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.DGM_OR_DGM_STC_REJECTED_APPLICATION_AT_CONSUMER_END.getId()).get());
			consumerApplictionDetailRepository.save(consumerApplicationDetail);

		} else if ("Return_Amount".equals(refundType)) {
			consumerAppReturnMaterialRefundAmntRepository
					.findByApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo()).stream()
					.forEach(retApp -> {
						retApp.setActive(false);
						retApp.setDeleted(true);
						consumerAppReturnMaterialRefundAmntRepository.save(retApp);
					});
			consumerApplicationDetail.setApplicationStatus(applicationStatusRepository.findById(
					ApplicationStatusEnum.DGM_OR_DGM_STC_REJECTED_RETURN_AMOUNT_APPLICATION_AT_CONSUMER_END.getId())
					.get());
			consumerApplictionDetailRepository.save(consumerApplicationDetail);
		}

	}

	@Autowired
	private UploadService uploadService;

	@Autowired
	private ApplicationDocumentRepository applicationDocumentRepository;

	@Override
	@Transactional
	public ConsumerAccountDetails saveAddressProofData(String consumerApplicationNo, String idProofNo,
			MultipartFile docAddressProof, MultipartFile docNotry, MultipartFile docAuthorizedLetter,
			MultipartFile docMRA, MultipartFile docRequestLetter, MultipartFile docDebitSlipFile)
			throws ConsumerApplicationDetailException, DocumentTypeException {

		Upload docAddressProofUpload = null;
		Upload docNotryUpload = null;
		Upload docAuthorizedLetterUpload = null;
		Upload docMRAUpload = null;
		Upload docRequestLetteUpload = null;
		Upload docDebitSlipFileUpload = null;

		ConsumerApplicationDetail consumerApplicationNumberDB = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationNumberDB)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Consumer application not found in database"));
		}

		if (docNotry != null) {
			docNotryUpload = uploadService.uploadFile(docNotry, "DOC_NOTRY");
			if (docNotryUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Notry Not Uploaded"));
			}

		}
		if (docAuthorizedLetter != null) {
			docAuthorizedLetterUpload = uploadService.uploadFile(docAuthorizedLetter, "DOC_AUTHORIZED_LETTER");
			if (docAuthorizedLetterUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Authorized Letter Not Uploaded"));
			}
		}

		if (docMRA != null) {
			docMRAUpload = uploadService.uploadFile(docMRA, "DOC_MRA");
			if (docMRAUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Address Proof Not Uploaded"));
			}

		}

		if (docAddressProof != null) {
			docAddressProofUpload = uploadService.uploadFile(docAddressProof, "DOC_ADDRESS_PROOF");
			if (docAddressProofUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Address Proof Not Uploaded"));
			}
		}

		if (docRequestLetter != null) {
			docRequestLetteUpload = uploadService.uploadFile(docRequestLetter, "DOC_REQUEST_LETTER");
			if (docRequestLetteUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Request Letter Not Uploaded"));
			}
		}

		if (Objects.nonNull(docDebitSlipFile)) {
			docDebitSlipFileUpload = uploadService.uploadFile(docDebitSlipFile, "DOC_DEBIT_SLIP_FILE");
			if (docDebitSlipFileUpload == null) {
				throw new ConsumerApplicationDetailException(
						new Response(HttpCode.NULL_OBJECT, "Document Debit Slip Not Uploaded"));
			}
		}

		List<ApplicationDocument> allDataByConsumerApplicationId = applicationDocumentRepository
				.findAllDataByConsumerApplicationId(consumerApplicationNumberDB.getConsumerApplicationId());
		if (Objects.isNull(allDataByConsumerApplicationId) || allDataByConsumerApplicationId.isEmpty()) {
			ApplicationDocument appDoc = new ApplicationDocument();
			appDoc.setDocAddressProofFile(docAddressProofUpload);
			appDoc.setDocNotry(docNotryUpload);
			appDoc.setDocGovAuthorizedLetterFile(docAuthorizedLetterUpload);
			appDoc.setDocMraFile(docMRAUpload);
			appDoc.setConsumerApplicationDetail(consumerApplicationNumberDB);
			appDoc.setDocRequestLetter(docRequestLetteUpload);
			if (Objects.nonNull(docDebitSlipFileUpload))
				appDoc.setDocDebitSlipFile(docDebitSlipFileUpload);
			applicationDocumentRepository.save(appDoc);
		} else {
			final Upload finalDocAddressProofUpload = docAddressProofUpload;
			final Upload finalDocNotryUpload = docNotryUpload;
			final Upload finalDocAuthorizedLetterUpload = docAuthorizedLetterUpload;
			final Upload finalDocMRAUpload = docMRAUpload;
			final Upload finalDocRequestLetteUpload = docRequestLetteUpload;
			final Upload finalDocDebitSlipFileUpload = docDebitSlipFileUpload;
			allDataByConsumerApplicationId.stream().forEach(appDoc -> {
				if (finalDocAddressProofUpload != null) {
					appDoc.setDocAddressProofFile(finalDocAddressProofUpload);
				}
				if (finalDocNotryUpload != null) {
					appDoc.setDocNotry(finalDocNotryUpload);
				}
				if (finalDocAuthorizedLetterUpload != null) {
					appDoc.setDocGovAuthorizedLetterFile(finalDocAuthorizedLetterUpload);
				}
				if (finalDocMRAUpload != null) {
					appDoc.setDocMraFile(finalDocMRAUpload);
				}
				if (finalDocRequestLetteUpload != null) {
					appDoc.setDocRequestLetter(finalDocRequestLetteUpload);
				}
				if (finalDocDebitSlipFileUpload != null)
					appDoc.setDocDebitSlipFile(finalDocDebitSlipFileUpload);

				System.err.println("finalDocDebitSlipFileUpload ::" + finalDocDebitSlipFileUpload);

			});
			applicationDocumentRepository.saveAll(allDataByConsumerApplicationId);
		}

		ConsumerAccountDetails consumerApplicationNoIsActiveDB = consumerAccountDetailsRepository
				.findByConsumerApplicationNoIsActive(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationNoIsActiveDB)) {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
					"Bank details not found for this application No. : " + consumerApplicationNo));
		}
		if (idProofNo != null) {
			consumerApplicationNoIsActiveDB.setAddressProofNo(idProofNo);
			return consumerAccountDetailsRepository.save(consumerApplicationNoIsActiveDB);
		}
		return consumerApplicationNoIsActiveDB;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RefundAmount saveReturnAmntApplication(RefundAmount refundAmount) throws ConsumerApplicationDetailException {
		BigDecimal totalReturnKrneWalaPaisa = BigDecimal.ZERO;
		List<PoseMachinePostData> demandDataFromPoseMachine = null;
		ManualPayment demandDataFromManualPayment = null;
		BillDeskPaymentResponse checkReviseDemandExistOrNot = null;
		BillDeskPaymentResponse checkDemandFeesExistOrNot = null;
		List<PoseMachinePostData> revisePaymentFromPoseMachineData = null;
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {
			if (consumerApplicationDetail.getErpVersion() != null
					&& consumerApplicationDetail.getErpVersion().equals("V2")) {

				checkReviseDemandExistOrNot = billPaymentResponseeeeeeeRepository
						.checkReviseDemandExistOrNot(refundAmount.getConsumerApplicationNo());
				if (Objects.nonNull(checkReviseDemandExistOrNot)) {
					ErpRev erpRevDB = erpRevRepository.findByConsAppNo(refundAmount.getConsumerApplicationNo());
					if (erpRevDB.getRemReturnAmt() != null) {
						BigDecimal reviseReturnAmtDB = erpRevDB.getRemReturnAmt();
						String revisePaymAmnt = String.valueOf(erpRevDB.getPayAmt());
						if (reviseReturnAmtDB.compareTo(BigDecimal.ZERO) > 0) {
							String reviseReturn = revisePaymAmnt.toString();
							String newReviseReturn = reviseReturn.concat(".%");

							System.err.println("   newReviseReturn    : " + newReviseReturn);
							BillDeskPaymentResponse billdeskSeReviseDemandKaAmount = billPaymentResponseeeeeeeRepository
									.getReviseAmountData(refundAmount.getConsumerApplicationNo(), newReviseReturn);
							System.err.println("aaaaaaaaa : " + new Gson().toJson(billdeskSeReviseDemandKaAmount));
							ConsumerAppReturnMaterialRefundAmnt conKoReturnKrneWalaAmount = new ConsumerAppReturnMaterialRefundAmnt();
							conKoReturnKrneWalaAmount.setApplicationNo(refundAmount.getConsumerApplicationNo());
							conKoReturnKrneWalaAmount.setMerchantId(billdeskSeReviseDemandKaAmount.getMercid());
							conKoReturnKrneWalaAmount.setOrderId(billdeskSeReviseDemandKaAmount.getOrderid());
							conKoReturnKrneWalaAmount
									.setPaymentType(billdeskSeReviseDemandKaAmount.getAdditionalInfo());
							conKoReturnKrneWalaAmount
									.setTransactionDate(billdeskSeReviseDemandKaAmount.getTransactionDate());
							conKoReturnKrneWalaAmount.setTxnAmount(billdeskSeReviseDemandKaAmount.getAmount());
							conKoReturnKrneWalaAmount.setTxnId(billdeskSeReviseDemandKaAmount.getTranId());
							conKoReturnKrneWalaAmount.setRefundableAmount(reviseReturnAmtDB);
							ConsumerAppReturnMaterialRefundAmnt byTxnIdDB = consumerAppReturnMaterialRefundAmntRepository
									.findByTxnIdAndIsActive(billdeskSeReviseDemandKaAmount.getTranId());
							if (byTxnIdDB == null) {
								consumerAppReturnMaterialRefundAmntRepository.save(conKoReturnKrneWalaAmount);
							}
							totalReturnKrneWalaPaisa = totalReturnKrneWalaPaisa.add(reviseReturnAmtDB);

						}
					}
					ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
					if (erpEstimateDB.getJeReturnAmount() != null) {
						String totalAmount = null;
						BigDecimal jeReturnAmountDB = erpEstimateDB.getJeReturnAmount();
						if (jeReturnAmountDB.compareTo(BigDecimal.ZERO) > 0) {
							if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
								totalAmount = erpEstimateDB.getTotalBalanceSupervisionAmount().toString().concat(".%");
							} else {
								totalAmount = erpEstimateDB.getTotalBalanceDepositAmount().toString().concat(".%");
							}
							if (totalAmount != null) {
								BillDeskPaymentResponse demandAmountDataForReturnMaterial = billPaymentResponseeeeeeeRepository
										.getDemandAmountDataForReturnMaterial(refundAmount.getConsumerApplicationNo(),
												totalAmount);
								ConsumerAppReturnMaterialRefundAmnt conKoReturnKrneWalaAmount = new ConsumerAppReturnMaterialRefundAmnt();
								conKoReturnKrneWalaAmount.setApplicationNo(refundAmount.getConsumerApplicationNo());
								conKoReturnKrneWalaAmount.setMerchantId(demandAmountDataForReturnMaterial.getMercid());
								conKoReturnKrneWalaAmount.setOrderId(demandAmountDataForReturnMaterial.getOrderid());
								conKoReturnKrneWalaAmount
										.setPaymentType(demandAmountDataForReturnMaterial.getAdditionalInfo());
								conKoReturnKrneWalaAmount
										.setTransactionDate(demandAmountDataForReturnMaterial.getTransactionDate());
								conKoReturnKrneWalaAmount.setTxnAmount(demandAmountDataForReturnMaterial.getAmount());
								conKoReturnKrneWalaAmount.setTxnId(demandAmountDataForReturnMaterial.getTranId());
								conKoReturnKrneWalaAmount.setRefundableAmount(jeReturnAmountDB);

								ConsumerAppReturnMaterialRefundAmnt byTxnIdDB = consumerAppReturnMaterialRefundAmntRepository
										.findByTxnIdAndIsActive(demandAmountDataForReturnMaterial.getTranId());
								if (byTxnIdDB == null) {
									consumerAppReturnMaterialRefundAmntRepository.save(conKoReturnKrneWalaAmount);
								}

							}
							totalReturnKrneWalaPaisa = totalReturnKrneWalaPaisa.add(jeReturnAmountDB);
						}
					}
				} else {
					revisePaymentFromPoseMachineData = poseMachinePostDataRepository
							.getDemandDataFromPoseMachineData(refundAmount.getConsumerApplicationNo());

					Map<String, Object> paymentFromSanchayOrManualPayment = getPaymentFromSanchayOrManualPayment(
							revisePaymentFromPoseMachineData, demandDataFromManualPayment, 2L,
							consumerApplicationDetail);
					totalReturnKrneWalaPaisa = (BigDecimal) paymentFromSanchayOrManualPayment
							.get("totalWapaskrneWalaPaisa");

					// Demand Fees Refund
					if (paymentFromSanchayOrManualPayment.containsKey("totalDemandZamaKiyaHuaPaisa")) {
						String amountStr = paymentFromSanchayOrManualPayment.get("totalDemandZamaKiyaHuaPaisa")
								.toString();

						ConsumerAppReturnMaterialRefundAmnt demandRefund = new ConsumerAppReturnMaterialRefundAmnt();
						demandRefund.setApplicationNo(refundAmount.getConsumerApplicationNo());
						demandRefund.setPaymentType("Demand_fees (Payment not received on billdesk)");
						demandRefund.setTransactionDate(
								(String) paymentFromSanchayOrManualPayment.get("demandPaymentTransactionDate"));
						demandRefund.setTxnAmount(amountStr);
						demandRefund.setRefundableAmount(
								(BigDecimal) paymentFromSanchayOrManualPayment.get("demandReturnAmount"));

						ConsumerAppReturnMaterialRefundAmnt check1 = consumerAppReturnMaterialRefundAmntRepository
								.checkApplicationExistOrNot(refundAmount.getConsumerApplicationNo(), amountStr,
										(BigDecimal) paymentFromSanchayOrManualPayment.get("demandReturnAmount"));

						if (check1 == null) {
							consumerAppReturnMaterialRefundAmntRepository.save(demandRefund);
						}
					}

					// Revise Fees Refund
					if (paymentFromSanchayOrManualPayment.containsKey("totalReviseZamaKiyaHuaPaisa")) {
						String reviseAmountStr = paymentFromSanchayOrManualPayment.get("totalReviseZamaKiyaHuaPaisa")
								.toString();

						ConsumerAppReturnMaterialRefundAmnt reviseRefund = new ConsumerAppReturnMaterialRefundAmnt();
						reviseRefund.setApplicationNo(refundAmount.getConsumerApplicationNo());
						reviseRefund.setPaymentType("Revise_fees (Payment not received on billdesk)");
						reviseRefund.setTransactionDate(
								(String) paymentFromSanchayOrManualPayment.get("revisePaymentTransactionDate"));
						reviseRefund.setTxnAmount(reviseAmountStr);
						reviseRefund.setRefundableAmount(
								(BigDecimal) paymentFromSanchayOrManualPayment.get("reviseReturnAmount"));

						ConsumerAppReturnMaterialRefundAmnt check2 = consumerAppReturnMaterialRefundAmntRepository
								.checkApplicationExistOrNot(refundAmount.getConsumerApplicationNo(), reviseAmountStr,
										(BigDecimal) paymentFromSanchayOrManualPayment.get("reviseReturnAmount"));

						if (check2 == null) {
							consumerAppReturnMaterialRefundAmntRepository.save(reviseRefund);
						}
					}
				}

			} else {
				checkDemandFeesExistOrNot = billPaymentResponseeeeeeeRepository
						.checkDemandFeesExistOrNot(refundAmount.getConsumerApplicationNo());
				if (Objects.nonNull(checkDemandFeesExistOrNot)) {

					ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
					if (erpEstimateDB.getJeReturnAmount() != null) {
						String totalAmount = null;
						BigDecimal jeReturnAmountDB = erpEstimateDB.getJeReturnAmount();
						if (jeReturnAmountDB.compareTo(BigDecimal.ZERO) > 0) {
							if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
								totalAmount = erpEstimateDB.getTotalBalanceSupervisionAmount().toString().concat(".%");
							} else {
								totalAmount = erpEstimateDB.getTotalBalanceDepositAmount().toString().concat(".%");
							}
							if (totalAmount != null) {
								int dotIndex = totalAmount.indexOf('.'); // Find the first dot

								if (dotIndex != -1) {
									totalAmount = totalAmount.substring(0, dotIndex).concat(".%"); // Extract substring
																									// before the first
																									// dot
								}
								BillDeskPaymentResponse demandAmountDataForReturnMaterial = billPaymentResponseeeeeeeRepository
										.getDemandAmountDataForReturnMaterial(refundAmount.getConsumerApplicationNo(),
												totalAmount);
								ConsumerAppReturnMaterialRefundAmnt conKoReturnKrneWalaAmount = new ConsumerAppReturnMaterialRefundAmnt();
								conKoReturnKrneWalaAmount.setApplicationNo(refundAmount.getConsumerApplicationNo());
								conKoReturnKrneWalaAmount.setMerchantId(demandAmountDataForReturnMaterial.getMercid());
								conKoReturnKrneWalaAmount.setOrderId(demandAmountDataForReturnMaterial.getOrderid());
								conKoReturnKrneWalaAmount
										.setPaymentType(demandAmountDataForReturnMaterial.getAdditionalInfo());
								conKoReturnKrneWalaAmount
										.setTransactionDate(demandAmountDataForReturnMaterial.getTransactionDate());
								conKoReturnKrneWalaAmount.setTxnAmount(demandAmountDataForReturnMaterial.getAmount());
								conKoReturnKrneWalaAmount.setTxnId(demandAmountDataForReturnMaterial.getTranId());
								conKoReturnKrneWalaAmount.setRefundableAmount(jeReturnAmountDB);
								ConsumerAppReturnMaterialRefundAmnt byTxnIdDB = consumerAppReturnMaterialRefundAmntRepository
										.findByTxnIdAndIsActive(demandAmountDataForReturnMaterial.getTranId());
								if (byTxnIdDB == null) {
									consumerAppReturnMaterialRefundAmntRepository.save(conKoReturnKrneWalaAmount);
								}
								totalReturnKrneWalaPaisa = totalReturnKrneWalaPaisa.add(jeReturnAmountDB);
							}
						}
					}
				} else {

					demandDataFromPoseMachine = poseMachinePostDataRepository
							.getDemandDataFromPoseMachineData(refundAmount.getConsumerApplicationNo());

					if (demandDataFromPoseMachine.isEmpty() && Objects.isNull(checkReviseDemandExistOrNot)) {
						demandDataFromManualPayment = manualPaymentRepository
								.getDemandDataFromManualPayment(refundAmount.getConsumerApplicationNo());
					}

					if (Objects.isNull(checkReviseDemandExistOrNot) && demandDataFromPoseMachine.isEmpty()
							&& Objects.isNull(demandDataFromManualPayment)) {
						throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
								"Revise Demand Fees not received for application no. : "
										+ refundAmount.getConsumerApplicationNo()));
					}

					Map<String, Object> paymentFromSanchayOrManualPayment = getPaymentFromSanchayOrManualPayment(
							demandDataFromPoseMachine, demandDataFromManualPayment, 2L, consumerApplicationDetail);
					System.err.println("aaaaaaaaaaaaaaaaaa : " + new Gson().toJson(paymentFromSanchayOrManualPayment));

					totalReturnKrneWalaPaisa = (BigDecimal) paymentFromSanchayOrManualPayment
							.get("totalWapaskrneWalaPaisa");
					ConsumerAppReturnMaterialRefundAmnt conKoReturnKrneWalaAmount = new ConsumerAppReturnMaterialRefundAmnt();
					conKoReturnKrneWalaAmount.setApplicationNo(refundAmount.getConsumerApplicationNo());

					conKoReturnKrneWalaAmount.setPaymentType("Demand_fees (Payment not received on billdesk)");
					conKoReturnKrneWalaAmount
							.setTransactionDate((String) paymentFromSanchayOrManualPayment.get("transactionDate"));
					conKoReturnKrneWalaAmount.setTxnAmount(
							paymentFromSanchayOrManualPayment.get("totalDemandZamaKiyaHuaPaisa").toString());
					conKoReturnKrneWalaAmount.setRefundableAmount(totalReturnKrneWalaPaisa);

					ConsumerAppReturnMaterialRefundAmnt checkApplicationExistOrNotDB = consumerAppReturnMaterialRefundAmntRepository
							.checkApplicationExistOrNot(refundAmount.getConsumerApplicationNo(),
									paymentFromSanchayOrManualPayment.get("totalDemandZamaKiyaHuaPaisa").toString(),
									totalReturnKrneWalaPaisa);
					if (checkApplicationExistOrNotDB == null) {
						consumerAppReturnMaterialRefundAmntRepository.save(conKoReturnKrneWalaAmount);
					}

				}
			}

			if (consumerApplicationDetail.getErpVersion() != null
					&& consumerApplicationDetail.getErpVersion().equals("V2")) {
				refundAmount.setReviseErpNo(consumerApplicationDetail.getRevisedErpNumber());
			} else {
				refundAmount.setDemandErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
			}
			refundAmount.setRefundAmount(totalReturnKrneWalaPaisa);
			refundAmount.setRefundType("Return_Amount");
			double digit = Math.random();
			double digit1 = Math.random();
			String valueOf = String.valueOf(digit);
			String valueOf1 = String.valueOf(digit1);
			String substring = valueOf.substring(2, 8);
			String substring1 = valueOf1.substring(2, 8);
			refundAmount.setRefundVoucherNo(substring + substring1);
			refundAmount.setConsumerAppId(consumerApplicationDetail.getConsumerApplicationId());
			RefundAmount save = null;
			RefundAmount byConsumerApplicationNoDB = refundAmountRepository
					.findByConsumerApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo());
			if (byConsumerApplicationNoDB == null) {
				save = refundAmountRepository.save(refundAmount);
			} else {
				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Refund already initiated for this application no. "
								+ consumerApplicationDetail.getConsumerApplicationNo() + " for refund Type : "
								+ byConsumerApplicationNoDB.getRefundType()));
			}
			if (Objects.isNull(save)) {
				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
			} else {
				consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
						.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_STC_FOR_REFUND.getId()).get());
				consumerApplictionDetailRepository.save(consumerApplicationDetail);
				return save;
			}
		}
	}

//	public Map<String, Object> getPaymentFromSanchayOrManualPayment(List<PoseMachinePostData> demandDataFromPoseMachine,
//			ManualPayment demandDataFromManualPayment, Long value, ConsumerApplicationDetail consumerApplicationDetail)
//			throws ConsumerApplicationDetailException {
//
//		BigDecimal refundableAmount = BigDecimal.ZERO;
//		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//		BigDecimal totalZamaKiyaHuaPaisa = BigDecimal.ZERO;
//		String transactionDate = null;
//		BigDecimal supervisionAmount = BigDecimal.ZERO;
//		BigDecimal depositAmount = BigDecimal.ZERO;
//		BigDecimal kvaAmount = BigDecimal.ZERO;
//		BigDecimal colonyOrSlumAmount = BigDecimal.ZERO;
//		BigDecimal returnAmount = BigDecimal.ZERO;
//		BigDecimal mkmyPaidAmount = BigDecimal.ZERO;
//		BigDecimal securityDepositAmount = BigDecimal.ZERO;
//		Map<String, Object> result = new HashMap<>();
//
//		boolean hasPoseData = demandDataFromPoseMachine != null && !demandDataFromPoseMachine.isEmpty();
//		boolean hasManualPayment = demandDataFromManualPayment != null;
//
//		if (hasPoseData || hasManualPayment) {
//
//			if (value == 1L) {
//				if (hasPoseData) {
//					for (PoseMachinePostData pose : demandDataFromPoseMachine) {
//						String paymentType = pose.getPaymentType();
//						totalZamaKiyaHuaPaisa = pose.getTxnAmount();
//						transactionDate = pose.getDateOfPayment().toString();
//
//						if ("Revised_Demand_fees".equals(paymentType)) {
//							ErpRev erpRev = erpRevRepository
//									.findByConsAppNo(consumerApplicationDetail.getConsumerApplicationNo());
//
//							if (erpRev != null) {
//
//								if (totalZamaKiyaHuaPaisa.compareTo(erpRev.getPayAmt()) >= 0) {
//									BigDecimal cgst = erpRev.getRemCgst();
//									BigDecimal sgst = erpRev.getRemSgst();
//									BigDecimal totalReviseTax = (cgst != null && sgst != null) ? cgst.add(sgst)
//											: BigDecimal.ZERO;
//									refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalReviseTax);
//									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//									supervisionAmount = supervisionAmount.add(erpRev.getRemSupervisionAmt());
//									if (Objects.nonNull(erpRev.getRemmDepositAmt())) {
//										depositAmount = depositAmount.add(erpRev.getRemmDepositAmt());
//									}
//									if (Objects.nonNull(erpRev.getRemKvaAmt())) {
//										kvaAmount = kvaAmount.add(erpRev.getRemKvaAmt());
//									}
//									if (Objects.nonNull(erpRev.getRemColonyOrSlum())) {
//										colonyOrSlumAmount = colonyOrSlumAmount.add(erpRev.getRemColonyOrSlum());
//									}
//									if (Objects.nonNull(erpRev.getRemReturnAmt())) {
//										returnAmount = returnAmount.add(erpRev.getRemReturnAmt());
//									}
//
//								} else {
//									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//											"Revise Payment paid less through sanchay Portal"));
//								}
//							}
//						} else {
//							if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
//								MmkyPayAmount mmkyData = mmkyPayAmountRespository.findByConsumerApplicationNumber(
//										consumerApplicationDetail.getConsumerApplicationNo());
//
//								if (mmkyData != null
//										&& totalZamaKiyaHuaPaisa.compareTo(mmkyData.getPayableAmount()) == 0) {
//
//									totalWapaskrneWalaPaisa = totalZamaKiyaHuaPaisa.subtract(BigDecimal.valueOf(2500));
//									result.put("demandPaymentTransactionDate", transactionDate);
//									result.put("totalDemandZamaKiyaHuaPaisa", totalZamaKiyaHuaPaisa);
//									result.put("demandRefundableAmount", totalWapaskrneWalaPaisa);
//									result.put("mkmyPaidAmount", mmkyData.getCarryAmountByApplicant());
//									result.put("securityDeposit", mmkyData.getSecurityDeposit());
//
//								}
//							} else {
//								ErpEstimateAmountData erpData = estimateAmountRepository
//										.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//								if (erpData != null) {
//									Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
//									BigDecimal compareAmount = (schemeId.equals(1L))
//											? erpData.getTotalBalanceSupervisionAmount()
//											: erpData.getTotalBalanceDepositAmount();
//
//									boolean isValidAmount = (schemeId.equals(1L)
//											&& totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0)
//											|| (schemeId.equals(2L)
//													&& totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0);
//
//									if (isValidAmount) {
//										if (erpData != null) {
//											BigDecimal cgst = erpData.getCgst();
//											BigDecimal sgst = erpData.getSgst();
//											BigDecimal totalTax = (cgst != null && sgst != null) ? cgst.add(sgst)
//													: BigDecimal.ZERO;
//											refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalTax);
//
//											totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//											supervisionAmount = supervisionAmount.add(erpData.getSupervisionAmount());
//											if (Objects.nonNull(erpData.getDepositAmount())) {
//												depositAmount = depositAmount.add(erpData.getDepositAmount());
//											}
//											if (Objects.nonNull(erpData.getKvaLoad())) {
//												kvaAmount = kvaAmount.add(erpData.getKvaLoad());
//											}
//											if (Objects.nonNull(erpData.getColonyOrSlum())) {
//												colonyOrSlumAmount = colonyOrSlumAmount.add(erpData.getColonyOrSlum());
//											}
//											if (Objects.nonNull(erpData.getJeReturnAmount())) {
//												returnAmount = returnAmount.add(erpData.getJeReturnAmount());
//											}
//											if (Objects.nonNull(erpData.getSecurityDeposit())) {
//												securityDepositAmount = securityDepositAmount
//														.add(erpData.getSecurityDeposit());
//											}
//										}
//
//									} else {
//										throw new ConsumerApplicationDetailException(
//												new Response(HttpCode.NOT_ACCEPTABLE,
//														"Demand Payment paid less through sanchay Portal"));
//									}
//								}
//							}
//						}
//					}
//					result.put("securityDeposit", securityDepositAmount);
//					result.put("returnAmount", returnAmount);
//					result.put("supervisionAmount", supervisionAmount);
//					result.put("depositAmount", depositAmount);
//					result.put("kvaAmount", kvaAmount);
//					result.put("colonyOrSlumAmount", colonyOrSlumAmount);
//					result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//				} else if (hasManualPayment) {
//					totalZamaKiyaHuaPaisa = new BigDecimal(demandDataFromManualPayment.getAmount());
//					System.err.println("totalZamaKiyaHuaPaisa" + totalZamaKiyaHuaPaisa);
//					transactionDate = demandDataFromManualPayment.getPaymentDate();
//					if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
//						MmkyPayAmount mmkyData = mmkyPayAmountRespository
//								.findByConsumerApplicationNumber(consumerApplicationDetail.getConsumerApplicationNo());
//
//						if (mmkyData != null && totalZamaKiyaHuaPaisa.compareTo(mmkyData.getPayableAmount()) == 0) {
//							totalWapaskrneWalaPaisa = totalZamaKiyaHuaPaisa.subtract(BigDecimal.valueOf(2500));
//							result.put("demandPaymentTransactionDate", transactionDate);
//							result.put("totalDemandZamaKiyaHuaPaisa", totalZamaKiyaHuaPaisa);
//							result.put("demandRefundableAmount", totalWapaskrneWalaPaisa);
//							result.put("mkmyPaidAmount", mmkyData.getCarryAmountByApplicant());
//							result.put("securityDeposit", mmkyData.getSecurityDeposit());
//						}
//					} else {
//						ErpEstimateAmountData erpData = estimateAmountRepository
//								.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//						if (erpData != null) {
//							Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
//							BigDecimal compareAmount = (schemeId.equals(1L))
//									? erpData.getTotalBalanceSupervisionAmount()
//									: erpData.getTotalBalanceDepositAmount();
//
//							boolean isValidAmount = (schemeId.equals(1L)
//									&& totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0)
//									|| (schemeId.equals(2L) && totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0);
//
//							if (isValidAmount) {
//								if (erpData != null) {
//									BigDecimal cgst = erpData.getCgst();
//									BigDecimal sgst = erpData.getSgst();
//									BigDecimal totalTax = (cgst != null && sgst != null) ? cgst.add(sgst)
//											: BigDecimal.ZERO;
//									refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalTax);
//									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//									supervisionAmount = supervisionAmount.add(erpData.getSupervisionAmount());
//									if (Objects.nonNull(erpData.getDepositAmount())) {
//										depositAmount = depositAmount.add(erpData.getDepositAmount());
//									}
//									if (Objects.nonNull(erpData.getKvaLoad())) {
//										kvaAmount = kvaAmount.add(erpData.getKvaLoad());
//									}
//									if (Objects.nonNull(erpData.getColonyOrSlum())) {
//										colonyOrSlumAmount = colonyOrSlumAmount.add(erpData.getColonyOrSlum());
//									}
//									if (Objects.nonNull(erpData.getJeReturnAmount())) {
//										returnAmount = returnAmount.add(erpData.getJeReturnAmount());
//									}
//									if (Objects.nonNull(erpData.getSecurityDeposit())) {
//										securityDepositAmount = securityDepositAmount.add(erpData.getSecurityDeposit());
//									}
//								}
//
//								result.put("demandPaymentTransactionDate", transactionDate);
//								result.put("totalDemandZamaKiyaHuaPaisa", totalZamaKiyaHuaPaisa);
//								result.put("demandRefundableAmount", refundableAmount);
//
//								System.err.println(" demand totalWapaskrneWalaPaisa : " + totalWapaskrneWalaPaisa);
//							} else {
//								throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//										"Demand Payment paid less through Manual Payment"));
//							}
//						}
//
//						result.put("securityDeposit", securityDepositAmount);
//						result.put("returnAmount", returnAmount);
//						result.put("supervisionAmount", supervisionAmount);
//						result.put("depositAmount", depositAmount);
//						result.put("kvaAmount", kvaAmount);
//						result.put("colonyOrSlumAmount", colonyOrSlumAmount);
//						result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//					}
//				}
//			} else {
//				// For value != 1
//				if (hasPoseData) {
//					for (PoseMachinePostData pose : demandDataFromPoseMachine) {
//						String paymentType = pose.getPaymentType();
//						BigDecimal txnAmount = pose.getTxnAmount();
//						transactionDate = pose.getDateOfPayment().toString();
//
//						if ("Revised_Demand_fees".equals(paymentType)) {
//							ErpRev erpRev = erpRevRepository
//									.findByConsAppNo(consumerApplicationDetail.getConsumerApplicationNo());
//
//							if (erpRev != null && erpRev.getRemReturnAmt() != null) {
//								if (txnAmount.compareTo(erpRev.getPayAmt()) >= 0) {
//									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpRev.getRemReturnAmt());
//									result.put("revisePaymentTransactionDate", transactionDate);
//									result.put("totalReviseZamaKiyaHuaPaisa", txnAmount);
//									result.put("reviseReturnAmount", erpRev.getRemReturnAmt());
//								} else {
//									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//											"Revise Payment paid less through sanchay Portal"));
//								}
//							}
//						} else {
//							ErpEstimateAmountData erpData = estimateAmountRepository
//									.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//							if (erpData != null && erpData.getJeReturnAmount() != null) {
//								Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
//								BigDecimal compareAmount = (schemeId.equals(1L))
//										? erpData.getTotalBalanceSupervisionAmount()
//										: erpData.getTotalBalanceDepositAmount();
//
//								boolean isValidAmount = (schemeId.equals(1L) && txnAmount.compareTo(compareAmount) >= 0)
//										|| (schemeId.equals(2L) && txnAmount.compareTo(compareAmount) >= 0);
//
//								if (isValidAmount) {
//									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//									result.put("demandPaymentTransactionDate", transactionDate);
//									result.put("totalDemandZamaKiyaHuaPaisa", txnAmount);
//									result.put("demandReturnAmount", erpData.getJeReturnAmount());
//								} else {
//									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//											"Demand Payment paid less through sanchay Portal"));
//								}
//							}
//						}
//					}
//					result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//				} else if (hasManualPayment) {
//					ErpEstimateAmountData erpData = estimateAmountRepository
//							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//					if (erpData != null && erpData.getJeReturnAmount() != null) {
//						Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
//						BigDecimal manualAmount = new BigDecimal(demandDataFromManualPayment.getAmount());
//						BigDecimal compareAmount = (schemeId.equals(1L)) ? erpData.getTotalBalanceSupervisionAmount()
//								: erpData.getTotalBalanceDepositAmount();
//
//						boolean isValidAmount = (schemeId.equals(1L) && manualAmount.compareTo(compareAmount) >= 0)
//								|| (schemeId.equals(2L) && manualAmount.compareTo(compareAmount) >= 0);
//
//						if (isValidAmount) {
//							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//							transactionDate = demandDataFromManualPayment.getPaymentDate();
//							result.put("demandPaymentTransactionDate", transactionDate);
//							result.put("totalDemandZamaKiyaHuaPaisa", manualAmount);
//							result.put("demandReturnAmount", erpData.getJeReturnAmount());
//							result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//						} else {
//							throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//									"Demand Payment paid less through sanchay Portal"));
//						}
//					}
//				}
//			}
//		}
//
//		return result;
//	}

//	ABOVE getPaymentFromSanchayOrManualPayment CODE COMMENTED KYUKI UAT PR TESTING HOGI 06-01-2026
	
	
	@Override
	@Transactional
	public RefundAmount financeRejectRefundApplicationAtDiscomOfficer(String consumerApplicationNo,
			boolean financeAoRefundReject, String financeAoId, String financeRemark, String financeName,
			String retrunDiscomName) throws ConsumerApplicationDetailException, RefundAmountException {
		RefundAmount save = null;
		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {

			if (consumerApplicationDetail.getApplicationStatus().getApplicationStatusId() != 41l)
				throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
						"API call not allowed for the current application status: "
								+ consumerApplicationDetail.getApplicationStatus().getApplicationStatusName()));

			RefundAmount refundAmount = refundAmountRepository
					.findByConsumerApplicationNoIsActive(consumerApplicationNo);
			if (Objects.isNull(refundAmount)) {
				throw new RefundAmountException(
						new Response(HttpCode.NULL_OBJECT, "Active Application not found in Refund database"));
			} else {
				if (!financeAoRefundReject) {
					refundAmount.setFinanceId(financeAoId);
					refundAmount.setFinanceApproval("false");
					refundAmount.setFinanceName(financeAoId);
					refundAmount.setFinanceName(financeName);
					refundAmount.setFinanceRemark(financeRemark);
					refundAmount.setFinanceRevertToDiscom(retrunDiscomName);
					refundAmount.setFinanceApprovedDate(Date.valueOf(LocalDate.now()).toString());

					if (Objects.equals(retrunDiscomName, "DGM (O&M)")) {
						refundAmount.setDgmApproval(null);
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
					} else if (Objects.equals(retrunDiscomName, "DGM (STC)")) {
						refundAmount.setDgmStcApproval(null);
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_STC_FOR_REFUND.getId())
								.get());
					} else if (Objects.equals(retrunDiscomName, "GM (O&M)")) {
						refundAmount.setGmApproval(null);
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_GM_FOR_REFUND.getId()).get());
					} else {
						consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
								.findById(consumerApplicationDetail.getApplicationStatus().getApplicationStatusId())
								.get());
					}
					save = refundAmountRepository.save(refundAmount);
					consumerApplictionDetailRepository.save(consumerApplicationDetail);

				} else {
					throw new RefundAmountException(
							new Response(HttpCode.NOT_ACCEPTABLE, "Discom refund request can not be TRUE"));
				}
			}

			return save;
		}
	}

	@Override
	public RefundAmount submitEFileNoByDGM(String consumerApplicationNo, String eFileNo)
			throws ConsumerApplicationDetailException {
		consumerApplictionDetailRepository.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(
						new Response<>(HttpCode.NULL_OBJECT, "Application not found in consumerApplication detail")));

		RefundAmount byConsumerApplicationNoIsActive = refundAmountRepository
				.findByConsumerApplicationNoIsActive(consumerApplicationNo);
		if (Objects.isNull(byConsumerApplicationNoIsActive))
			throw new ConsumerApplicationDetailException(
					new Response<>(HttpCode.NULL_OBJECT, "Application no. not found in refundAmount"));
		byConsumerApplicationNoIsActive.seteFileNo(eFileNo);
		return refundAmountRepository.save(byConsumerApplicationNoIsActive);

	}

	@Override
	public RefundAmount financeAoAcceptanceAfterErpCompletion(String consumerApplicationNo, String financeErpRemark,
			String financeErpPaymentDate, String erpRefundVoucherNumber) throws ConsumerApplicationDetailException {
		ConsumerApplicationDetail consumerApplicationData = consumerApplictionDetailRepository
				.findByConsumerApplicationNo(consumerApplicationNo)
				.orElseThrow(() -> new ConsumerApplicationDetailException(
						new Response<>(HttpCode.NULL_OBJECT, "Application not found in consumerApplication detail")));

		RefundAmount byConsumerApplicationNoIsActive = refundAmountRepository
				.findByConsumerApplicationNoIsActive(consumerApplicationNo);
		if (Objects.isNull(byConsumerApplicationNoIsActive))
			throw new ConsumerApplicationDetailException(
					new Response<>(HttpCode.NULL_OBJECT, "Application no. not found in refundAmount"));
		byConsumerApplicationNoIsActive.setErpRefundVoucherNumber(erpRefundVoucherNumber);
		byConsumerApplicationNoIsActive.setFinanceErpPaymentDate(financeErpPaymentDate);
		byConsumerApplicationNoIsActive.setFinanceErpRemark(financeErpRemark);
		RefundAmount save = refundAmountRepository.save(byConsumerApplicationNoIsActive);

		consumerApplicationData.setApplicationStatus(applicationStatusRepository
				.findById(ApplicationStatusEnum.CANCELLATION_AMOUNT_REFUNDED_TO_APPLICANT_SUCCESSFULLY.getId()).get());
		consumerApplictionDetailRepository.save(consumerApplicationData);

		return save;
	}

//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public RefundAmount saveConsumerApplicationCancel(RefundAmount refundAmount)
//			throws ConsumerApplicationDetailException {
//		BigDecimal totalReturnKrneWalaPaisa = BigDecimal.ZERO;
//		BigDecimal refundableAmount = BigDecimal.ZERO;
//		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//
//		BigDecimal supervisionAmount = BigDecimal.ZERO;
//		BigDecimal depositAmount = BigDecimal.ZERO;
//		BigDecimal kvaAmount = BigDecimal.ZERO;
//		BigDecimal colonyOrSlumAmount = BigDecimal.ZERO;
//		BigDecimal returnAmount = BigDecimal.ZERO;
//		BigDecimal securityDepositAmount = BigDecimal.ZERO;
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
//		if (Objects.isNull(consumerApplicationDetail)) {
//			throw new ConsumerApplicationDetailException(
//					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
//		} else {
//			List<BillDeskPaymentResponse> allPaymentDetails = null;
//			List<PoseMachinePostData> demandDataFromPoseMachine = null;
//			ManualPayment demandDataFromManualPayment = null;
//			try {
//				allPaymentDetails = billPaymentResponseeeeeeeRepository
//						.getAllPaymentDetails(refundAmount.getConsumerApplicationNo());
//				if (allPaymentDetails.isEmpty()) {
//					demandDataFromPoseMachine = poseMachinePostDataRepository
//							.getDemandDataFromPoseMachineData(refundAmount.getConsumerApplicationNo());
//				}
//				if (demandDataFromPoseMachine.isEmpty() && allPaymentDetails.isEmpty()) {
//					demandDataFromManualPayment = manualPaymentRepository
//							.getDemandDataFromManualPayment(refundAmount.getConsumerApplicationNo());
//				}
//				System.err.println(allPaymentDetails);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			if (allPaymentDetails.isEmpty() && demandDataFromPoseMachine.isEmpty()
//					&& Objects.isNull(demandDataFromManualPayment)) {
//				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
//						"Demand Fees not received for application no. : " + refundAmount.getConsumerApplicationNo()));
//			}
//			if (!allPaymentDetails.isEmpty()) {
//				for (BillDeskPaymentResponse bill : allPaymentDetails) {
//					ConsumerAppCancellationRefundAmount conAppCancel = new ConsumerAppCancellationRefundAmount();
//					conAppCancel.setApplicationNo(refundAmount.getConsumerApplicationNo());
//					conAppCancel.setMerchantId(bill.getMercid());
//					conAppCancel.setOrderId(bill.getOrderid());
//					conAppCancel.setPaymentType(bill.getAdditionalInfo());
//					conAppCancel.setTxnAmount(new BigDecimal(bill.getAmount()));
//					conAppCancel.setTxnId(bill.getTranId());
//					conAppCancel.setTransactionDate(bill.getTransactionDate());
//					ConsumerAppCancellationRefundAmount byTxnId = consumerAppCancellationRefundAmountRepository
//							.findByTxnIdIsActive(bill.getTranId());
//					if (byTxnId == null) {
//						consumerAppCancellationRefundAmountRepository.save(conAppCancel);
//					}
//
//				}
//				BigDecimal billdeskPaidAmount = new BigDecimal(allPaymentDetails.get(0).getAmount());
//				if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
//					MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
//							.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
//
//					refundAmount.setDemandErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//					refundAmount.setMkmyPaidAmount(findByConsumerApplicationNumber.getCarryAmountByApplicant());
//					refundAmount.setSecurityDepositAmount(findByConsumerApplicationNumber.getSecurityDeposit());
//					refundAmount.setRefundAmount(billdeskPaidAmount.subtract(new BigDecimal(2500)));
////					added this given check on 21-08-2025 Monika Rajpoot 
//					BigDecimal payableMinus2500 = billdeskPaidAmount.subtract(new BigDecimal(2500));
//
//					BigDecimal carryPlusSecurity = findByConsumerApplicationNumber.getCarryAmountByApplicant()
//							.add(findByConsumerApplicationNumber.getSecurityDeposit());
//
//					if (payableMinus2500.compareTo(carryPlusSecurity) != 0) {
//						throw new ConsumerApplicationDetailException(
//								new Response(HttpCode.NOT_ACCEPTABLE, "Amount not matched : Refundalable Amount is : "
//										+ payableMinus2500 + " and Total head amount is : " + carryPlusSecurity));
//					}
////					check end 21-08-2025 Monika Rajpoot 
//
//				} else {
//					List<ErpRev> erpRevDB = erpRevRepository
//							.findByConsumerAppNo(refundAmount.getConsumerApplicationNo());
//					if (!erpRevDB.isEmpty()) {
//						for (ErpRev erpRev : erpRevDB) {
//							System.err.println("erp aaaaaa : " + erpRev.getPayAmt());
//
//							ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//									.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
//											erpRev.getPayAmt());
//							if (erpRev.getRemCgst() != null) {
//								BigDecimal remCgst = erpRev.getRemCgst();
//								BigDecimal remSgst = erpRev.getRemSgst();
//								BigDecimal totalCgstSgst = remCgst.add(remSgst);
//
//								refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
//							} else {
//								refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
//							}
//
//							supervisionAmount = erpRev.getRemSupervisionAmt();
//							if (Objects.nonNull(erpRev.getRemmDepositAmt())) {
//								depositAmount = erpRev.getRemmDepositAmt();
//							}
//							if (Objects.nonNull(erpRev.getRemKvaAmt())) {
//								kvaAmount = erpRev.getRemKvaAmt();
//							}
//							if (Objects.nonNull(erpRev.getRemColonyOrSlum())) {
//								colonyOrSlumAmount = erpRev.getRemColonyOrSlum();
//							}
//							if (Objects.nonNull(erpRev.getRemReturnAmt())) {
//								returnAmount = erpRev.getRemReturnAmt();
//							}
//
//							applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
//							consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);
//
//							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//							refundAmount.setReviseErpNo(consumerApplicationDetail.getRevisedErpNumber());
//						}
//					}
//					ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//					ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = null;
//					if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
//						applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//								.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
//										erpEstimateDB.getTotalBalanceSupervisionAmount());
//					} else {
//						applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//								.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
//										erpEstimateDB.getTotalBalanceDepositAmount());
//					}
//
//					if (erpEstimateDB.getCgst() != null) {
//
//						BigDecimal cgst = erpEstimateDB.getCgst();
//						BigDecimal sgst = erpEstimateDB.getSgst();
//						BigDecimal totalCgstSgst = cgst.add(sgst);
//						refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
//					} else {
//						refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
//					}
//
//					supervisionAmount = supervisionAmount.add(erpEstimateDB.getSupervisionAmount());
//					if (Objects.nonNull(erpEstimateDB.getDepositAmount())) {
//						depositAmount = depositAmount.add(erpEstimateDB.getDepositAmount());
//					}
//					if (Objects.nonNull(erpEstimateDB.getKvaLoad())) {
//						kvaAmount = kvaAmount.add(erpEstimateDB.getKvaLoad());
//					}
//					if (Objects.nonNull(erpEstimateDB.getColonyOrSlum())) {
//						colonyOrSlumAmount = colonyOrSlumAmount.add(erpEstimateDB.getColonyOrSlum());
//					}
//					if (Objects.nonNull(erpEstimateDB.getJeReturnAmount())) {
//						returnAmount = erpEstimateDB.getJeReturnAmount();
//					}
//					if (Objects.nonNull(erpEstimateDB.getSecurityDeposit())) {
//						securityDepositAmount = securityDepositAmount.add(erpEstimateDB.getSecurityDeposit());
//					}
//
//					refundAmount.setDemandErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//					refundAmount.setSecurityDepositAmount(securityDepositAmount);
//					refundAmount.setSupervisionAmount(supervisionAmount);
//					refundAmount.setDepositAmount(depositAmount);
//					refundAmount.setKvaAmount(kvaAmount);
//					refundAmount.setColonyOrSlumAmount(colonyOrSlumAmount);
//					refundAmount.setReturnAmount(returnAmount);
//					applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
//					totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//					refundAmount.setRefundAmount(totalWapaskrneWalaPaisa);
////					added this given check on 21-08-2025 Monika Rajpoot 
//					BigDecimal totalHeadAmount = supervisionAmount.add(depositAmount).add(securityDepositAmount)
//							.add(kvaAmount).add(colonyOrSlumAmount).add(returnAmount);
//					if (totalWapaskrneWalaPaisa.compareTo(totalHeadAmount) != 0) {
//						throw new ConsumerApplicationDetailException(
//								new Response(HttpCode.NOT_ACCEPTABLE, "Amount not matched: Refundalable Amount is : "
//										+ totalWapaskrneWalaPaisa + " and Total head amount is : " + totalHeadAmount));
//					}
//
////					check end 21-08-2025 Monika Rajpoot 
//					consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);
//
//				}
//			} else {
////				demandDataFromPoseMachine = poseMachinePostDataRepository
////						.getDemandDataFromPoseMachineData(refundAmount.getConsumerApplicationNo());
//
//				Map<String, Object> paymentFromSanchayOrManualPayment = getPaymentFromSanchayOrManualPayment(
//						demandDataFromPoseMachine, demandDataFromManualPayment, 1L, consumerApplicationDetail);
//				totalReturnKrneWalaPaisa = (BigDecimal) paymentFromSanchayOrManualPayment
//						.get("totalWapaskrneWalaPaisa");
//				if (paymentFromSanchayOrManualPayment.containsKey("totalDemandZamaKiyaHuaPaisa")) {
//					String amountStr = paymentFromSanchayOrManualPayment.get("totalDemandZamaKiyaHuaPaisa").toString();
//					BigDecimal amount = new BigDecimal(amountStr);
//					ConsumerAppCancellationRefundAmount demandRefund = new ConsumerAppCancellationRefundAmount();
//					demandRefund.setApplicationNo(refundAmount.getConsumerApplicationNo());
//					demandRefund.setPaymentType("Demand_fees (Payment not received on billdesk)");
//					demandRefund.setTransactionDate(
//							(String) paymentFromSanchayOrManualPayment.get("demandPaymentTransactionDate"));
//					demandRefund.setTxnAmount(amount);
//					demandRefund.setRefundableAmount(
//							(BigDecimal) paymentFromSanchayOrManualPayment.get("demandRefundableAmount"));
//
//					ConsumerAppReturnMaterialRefundAmnt check1 = consumerAppReturnMaterialRefundAmntRepository
//							.checkApplicationExistOrNot(refundAmount.getConsumerApplicationNo(), amountStr,
//									(BigDecimal) paymentFromSanchayOrManualPayment.get("demandRefundableAmount"));
//
//					if (check1 == null) {
//						consumerAppCancellationRefundAmountRepository.save(demandRefund);
//					}
//				}
//
//				// Revise Fees Refund
//				if (paymentFromSanchayOrManualPayment.containsKey("totalReviseZamaKiyaHuaPaisa")) {
//					String reviseAmountStr = paymentFromSanchayOrManualPayment.get("totalReviseZamaKiyaHuaPaisa")
//							.toString();
//					BigDecimal amount = new BigDecimal(reviseAmountStr);
//					ConsumerAppCancellationRefundAmount reviseRefund = new ConsumerAppCancellationRefundAmount();
//					reviseRefund.setApplicationNo(refundAmount.getConsumerApplicationNo());
//					reviseRefund.setPaymentType("Revise_fees (Payment not received on billdesk)");
//					reviseRefund.setTransactionDate(
//							(String) paymentFromSanchayOrManualPayment.get("revisePaymentTransactionDate"));
//					reviseRefund.setTxnAmount(amount);
//					reviseRefund.setRefundableAmount(
//							(BigDecimal) paymentFromSanchayOrManualPayment.get("reviseRefundableAmount"));
//
//					ConsumerAppReturnMaterialRefundAmnt check2 = consumerAppReturnMaterialRefundAmntRepository
//							.checkApplicationExistOrNot(refundAmount.getConsumerApplicationNo(), reviseAmountStr,
//									(BigDecimal) paymentFromSanchayOrManualPayment.get("reviseRefundableAmount"));
//
//					if (check2 == null) {
//						consumerAppCancellationRefundAmountRepository.save(reviseRefund);
//					}
//				}
//
//				if (!demandDataFromPoseMachine.isEmpty()) {
//					demandDataFromPoseMachine.stream().forEach(pose -> {
//						if (pose.getPaymentType().equals("Revised_Demand_fees")) {
//							refundAmount.setReviseErpNo(consumerApplicationDetail.getRevisedErpNumber());
//						} else {
//							refundAmount.setDemandErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//						}
//					});
//				}
//
//				refundAmount
//						.setSupervisionAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("supervisionAmount"));
//				refundAmount.setDepositAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("depositAmount"));
//				refundAmount.setKvaAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("kvaAmount"));
//				refundAmount.setColonyOrSlumAmount(
//						(BigDecimal) paymentFromSanchayOrManualPayment.get("colonyOrSlumAmount"));
//				refundAmount.setReturnAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("returnAmount"));
//				refundAmount.setMkmyPaidAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("mkmyPaidAmount"));
//				refundAmount.setSecurityDepositAmount(
//						(BigDecimal) paymentFromSanchayOrManualPayment.get("securityDeposit"));
//				refundAmount.setRefundAmount(totalReturnKrneWalaPaisa);
//			}
//
//		}
//
//		refundAmount.setConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo());
//		refundAmount.setConsumerAppId(consumerApplicationDetail.getConsumerApplicationId());
//		refundAmount.setRefundType("Cancellation_Amount");
//		double digit = Math.random();
//		double digit1 = Math.random();
//		String valueOf = String.valueOf(digit);
//		String valueOf1 = String.valueOf(digit1);
//		String substring = valueOf.substring(2, 8);
//		String substring1 = valueOf1.substring(2, 8);
//		refundAmount.setRefundVoucherNo(substring + substring1);
//
//		RefundAmount refundAmntDB = null;
//		RefundAmount byConsumerApplicationNoDB = refundAmountRepository
//				.findByConsumerApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo());
//		if (byConsumerApplicationNoDB == null) {
//			refundAmntDB = refundAmountRepository.save(refundAmount);
//		} else {
//			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
//					"Refund already initiated for this application no. "
//							+ consumerApplicationDetail.getConsumerApplicationNo() + " for refund Type : "
//							+ byConsumerApplicationNoDB.getRefundType()));
//		}
//		if (Objects.isNull(refundAmntDB)) {
//			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
//		} else {
//			consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
//					.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
//			consumerApplictionDetailRepository.save(consumerApplicationDetail);
//			return refundAmntDB;
//		}
//
//	}
	
//	ABOVE CODE COMMENTED KYUKI GIVEN CODE UAT PR DEPLOY KIYA JAA RHA HAI FOR TESTING 06-01-2026
	
	
	
//	the above code consist the payment details not included with sspApplication Amount
// code commented 23-12-2025 kyuki below new code likha hai govt. and NOW 12 ki application k liye
//	@Override
//	public Map<String, BigDecimal> getPaymentDetailForRefund(String consumerApplicationNo, Long value)
//			throws ConsumerApplicationDetailException {
//		BigDecimal refundableAmount = BigDecimal.ZERO;
//		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//
//		Map<String, BigDecimal> result = new HashMap<>();
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(consumerApplicationNo);
//		if (Objects.isNull(consumerApplicationDetail)) {
//			throw new ConsumerApplicationDetailException(
//					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
//		} else {
//			List<BillDeskPaymentResponse> allPaymentDetails = null;
//			List<PoseMachinePostData> demandDataFromPoseMachine = null;
//			ManualPayment demandDataFromManualPayment = null;
//			try {
//				allPaymentDetails = billPaymentResponseeeeeeeRepository.getAllPaymentDetails(consumerApplicationNo);
//				if (allPaymentDetails.isEmpty()) {
//					demandDataFromPoseMachine = poseMachinePostDataRepository
//							.getDemandDataFromPoseMachineData(consumerApplicationNo);
//
//					if (demandDataFromPoseMachine.isEmpty() && allPaymentDetails.isEmpty()) {
//						demandDataFromManualPayment = manualPaymentRepository
//								.getDemandDataFromManualPayment(consumerApplicationNo);
//					}
//				}
//
//				System.err.println(allPaymentDetails);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			if (allPaymentDetails.isEmpty() && demandDataFromPoseMachine.isEmpty()
//					&& Objects.isNull(demandDataFromManualPayment)) {
//				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
//						"Demand Fees not received for application no. : " + consumerApplicationNo));
//			}
////			y poora case chalna chahiye jab allPaymentDetails list empty na ho 
////			value == 1 matlb (application cancle karna hai)
//			if (!allPaymentDetails.isEmpty()) {
//				if (value == 1L) {
//					for (BillDeskPaymentResponse bill : allPaymentDetails) {
//						// cancellation ka total amount dena hai
//
//						if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
//							ErpRev erpRevDB = erpRevRepository.findByConsAppNoAndPayAmt(consumerApplicationNo,
//									new BigDecimal(bill.getAmount()));
//							if (erpRevDB != null) {
//
//								System.err.println("erp aaaaaa : " + erpRevDB.getPayAmt());
//// erpRevDB.getRemCgst() != null y check isliye lagaya h kyuki kabhi kabhi remCgst remSgst me null hota kyuki data remColonyOrSlum me hota hai
//								if (erpRevDB.getRemCgst() != null) {
//									BigDecimal remCgst = erpRevDB.getRemCgst();
//									BigDecimal remSgst = erpRevDB.getRemSgst();
//									BigDecimal totalCgstSgst = remCgst.add(remSgst);
//
//									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//									refundableAmount = billdeskAmount.subtract(totalCgstSgst);
//								} else {
//									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//									refundableAmount = billdeskAmount;
//								}
//								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//							}
//						} else {
//							if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
//								MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
//										.findByConsumerApplicationNumber(consumerApplicationNo);
//
//								BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//								if (billdeskAmount.compareTo(findByConsumerApplicationNumber.getPayableAmount()) == 0) {
//									totalWapaskrneWalaPaisa = new BigDecimal(bill.getAmount())
//											.subtract(new BigDecimal(2500));
//								}
//
//								else {
//									totalWapaskrneWalaPaisa = new BigDecimal(0.0);
//								}
//							} else {
//
//								ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//										.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//								if (erpEstimateDB.getCgst() != null) {
//
//									BigDecimal cgst = erpEstimateDB.getCgst();
//									BigDecimal sgst = erpEstimateDB.getSgst();
//									BigDecimal totalCgstSgst = cgst.add(sgst);
//									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//									refundableAmount = billdeskAmount.subtract(totalCgstSgst);
//								} else {
//									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
//									refundableAmount = billdeskAmount;
//								}
//
//								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//							}
//						}
//					}
//				} else { // return ka amount dena hai
//					for (BillDeskPaymentResponse bill : allPaymentDetails) {
//
//						if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
//							ErpRev erpRev = erpRevRepository.findByConsAppNo(consumerApplicationNo);
//							if (erpRev != null && erpRev.getRemReturnAmt() != null) {
//								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpRev.getRemReturnAmt());
//							}
//						} else {
//							ErpEstimateAmountData erpData = estimateAmountRepository
//									.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//							if (erpData != null && erpData.getJeReturnAmount() != null) {
//								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//							}
//						}
//
//					}
//				}
//			} else {
//
//				Map<String, Object> paymentFromSanchayOrManualPayment = getPaymentFromSanchayOrManualPayment(
//						demandDataFromPoseMachine, demandDataFromManualPayment, value, consumerApplicationDetail);
//				totalWapaskrneWalaPaisa = (BigDecimal) paymentFromSanchayOrManualPayment.get("totalWapaskrneWalaPaisa");
//			}
////			yhan tak code chalega billdesk k liye
//			BeforeRefundAmountCheck byApplicationNo = beforeRefundAmountCheckRepository
//					.findByApplicationNo(consumerApplicationNo);
//			if (byApplicationNo == null) {
//				BeforeRefundAmountCheck check = new BeforeRefundAmountCheck();
//				check.setApplicationNo(consumerApplicationNo);
//				check.setRefundableAmount(totalWapaskrneWalaPaisa);
//				beforeRefundAmountCheckRepository.save(check);
//			} else {
//				byApplicationNo.setRefundableAmount(totalWapaskrneWalaPaisa);
//				beforeRefundAmountCheckRepository.save(byApplicationNo);
//			}
//
//			result.put("totalRefundAmount", totalWapaskrneWalaPaisa);
//			return result;
//		}
//	}
//	ABOVE getPaymentFromSanchayOrManualPayment CODE COMMENTED KYUKI UAT PR TESTING HOGI 06-01-2026
	
	
//	========================================================================================
//	Y poora code uncomment krna hai jab bhi SSP ki payment bhi refund krna hai tab and erp procedure me bhi inhe include krna pdega
//	this code is written on 04-11-2025 isme sspTotal amount and sspRegamount ka deduction add kiya hua hai ise test krna hai

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RefundAmount saveConsumerApplicationCancel(RefundAmount refundAmount)
			throws ConsumerApplicationDetailException {
		BigDecimal totalReturnKrneWalaPaisa = BigDecimal.ZERO;
		BigDecimal refundableAmount = BigDecimal.ZERO;
		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;

		BigDecimal supervisionAmount = BigDecimal.ZERO;
		BigDecimal depositAmount = BigDecimal.ZERO;
		BigDecimal kvaAmount = BigDecimal.ZERO;
		BigDecimal colonyOrSlumAmount = BigDecimal.ZERO;
		BigDecimal returnAmount = BigDecimal.ZERO;
		BigDecimal securityDepositAmount = BigDecimal.ZERO;
		BigDecimal totalHeadAmount = BigDecimal.ZERO;

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {
			List<BillDeskPaymentResponse> allPaymentDetails = null;
			List<PoseMachinePostData> demandDataFromPoseMachine = null;
			ManualPayment demandDataFromManualPayment = null;
			try {
				allPaymentDetails = billPaymentResponseeeeeeeRepository
						.getAllPaymentDetails(refundAmount.getConsumerApplicationNo());
				if (allPaymentDetails.isEmpty()) {
					demandDataFromPoseMachine = poseMachinePostDataRepository
							.getDemandDataFromPoseMachineData(refundAmount.getConsumerApplicationNo());
				}
				if (demandDataFromPoseMachine.isEmpty() && allPaymentDetails.isEmpty()) {
					demandDataFromManualPayment = manualPaymentRepository
							.getDemandDataFromManualPayment(refundAmount.getConsumerApplicationNo());
				}
				System.err.println(allPaymentDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (allPaymentDetails.isEmpty() && demandDataFromPoseMachine.isEmpty()
					&& Objects.isNull(demandDataFromManualPayment)) {
				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Demand Fees not received for application no. : " + refundAmount.getConsumerApplicationNo()));
			}
			if (!allPaymentDetails.isEmpty()) {
				for (BillDeskPaymentResponse bill : allPaymentDetails) {
					ConsumerAppCancellationRefundAmount conAppCancel = new ConsumerAppCancellationRefundAmount();
					conAppCancel.setApplicationNo(refundAmount.getConsumerApplicationNo());
					conAppCancel.setMerchantId(bill.getMercid());
					conAppCancel.setOrderId(bill.getOrderid());
					conAppCancel.setPaymentType(bill.getAdditionalInfo());
					conAppCancel.setTxnAmount(new BigDecimal(bill.getAmount()));
					conAppCancel.setTxnId(bill.getTranId());
					conAppCancel.setTransactionDate(bill.getTransactionDate());
					ConsumerAppCancellationRefundAmount byTxnId = consumerAppCancellationRefundAmountRepository
							.findByTxnIdIsActive(bill.getTranId());
					if (byTxnId == null) {
						consumerAppCancellationRefundAmountRepository.save(conAppCancel);
					}

				}
				BigDecimal billdeskPaidAmount = new BigDecimal(allPaymentDetails.get(0).getAmount());
				if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
					MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
							.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());

					refundAmount.setDemandErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
					refundAmount.setMkmyPaidAmount(findByConsumerApplicationNumber.getCarryAmountByApplicant());
					refundAmount.setSecurityDepositAmount(findByConsumerApplicationNumber.getSecurityDeposit());
					refundAmount.setRefundAmount(billdeskPaidAmount.subtract(new BigDecimal(2500)));
//					added this given check on 21-08-2025 Monika Rajpoot 
					BigDecimal payableMinus2500 = billdeskPaidAmount.subtract(new BigDecimal(2500));

					BigDecimal carryPlusSecurity = findByConsumerApplicationNumber.getCarryAmountByApplicant()
							.add(findByConsumerApplicationNumber.getSecurityDeposit());

					if (payableMinus2500.compareTo(carryPlusSecurity) != 0) {
						throw new ConsumerApplicationDetailException(
								new Response(HttpCode.NOT_ACCEPTABLE, "Amount not matched : Refundalable Amount is : "
										+ payableMinus2500 + " and Total head amount is : " + carryPlusSecurity));
					}
//					check end 21-08-2025 Monika Rajpoot 

				} else {
					List<ErpRev> erpRevDB = erpRevRepository
							.findByConsumerAppNo(refundAmount.getConsumerApplicationNo());
					if (!erpRevDB.isEmpty()) {
						for (ErpRev erpRev : erpRevDB) {
							System.err.println("erp aaaaaa : " + erpRev.getPayAmt());

							ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
									.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
											erpRev.getPayAmt());
							if (erpRev.getRemCgst() != null) {
								BigDecimal remCgst = erpRev.getRemCgst();
								BigDecimal remSgst = erpRev.getRemSgst();
								BigDecimal totalCgstSgst = remCgst.add(remSgst);

								refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
							} else {
								refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
							}

							supervisionAmount = erpRev.getRemSupervisionAmt();
							if (Objects.nonNull(erpRev.getRemmDepositAmt())) {
								depositAmount = erpRev.getRemmDepositAmt();
							}
							if (Objects.nonNull(erpRev.getRemKvaAmt())) {
								kvaAmount = erpRev.getRemKvaAmt();
							}
							if (Objects.nonNull(erpRev.getRemColonyOrSlum())) {
								colonyOrSlumAmount = erpRev.getRemColonyOrSlum();
							}
							if (Objects.nonNull(erpRev.getRemReturnAmt())) {
								returnAmount = erpRev.getRemReturnAmt();
							}

							applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
							consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);

							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
							refundAmount.setReviseErpNo(consumerApplicationDetail.getRevisedErpNumber());
						}
					}
					ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());

					ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = null;
					if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
						applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
								.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
										erpEstimateDB.getTotalBalanceSupervisionAmount());
					} else {
						applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
								.findByApplicationNoAndTxnAmountIsActive(refundAmount.getConsumerApplicationNo(),
										erpEstimateDB.getTotalBalanceDepositAmount());
					}

					BigDecimal sspRegCharge = erpEstimateDB.getSspRegistrationCharge();

					if (erpEstimateDB.getCgst() != null) {

						BigDecimal cgst = erpEstimateDB.getCgst();
						BigDecimal sgst = erpEstimateDB.getSgst();
						BigDecimal totalCgstSgst = cgst.add(sgst);

						if (sspRegCharge == null) {
							refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
						} else {
//							sspRegCharge min. 5 rs to aayega hi 7-11-2025
							refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst)
									.subtract(BigDecimal.valueOf(5));
						}

					} else {
						if (sspRegCharge == null) {
							refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
						} else {
							refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount()
									.subtract(BigDecimal.valueOf(5));
						}
					}
					
//					this case is added for govt. application and NOW 12 Date: 23-12-2025
					if (erpEstimateDB.getRegistrationCharges() != null
							&& erpEstimateDB.getRegistrationCharges().equals(BigDecimal.valueOf(1180))) {
						refundableAmount = refundableAmount.subtract(BigDecimal.valueOf(1180));
					}

					supervisionAmount = supervisionAmount.add(erpEstimateDB.getSupervisionAmount());
					if (Objects.nonNull(erpEstimateDB.getDepositAmount())) {
						depositAmount = depositAmount.add(erpEstimateDB.getDepositAmount());
					}
					if (Objects.nonNull(erpEstimateDB.getKvaLoad())) {
						kvaAmount = kvaAmount.add(erpEstimateDB.getKvaLoad());
					}
					if (Objects.nonNull(erpEstimateDB.getColonyOrSlum())) {
						colonyOrSlumAmount = colonyOrSlumAmount.add(erpEstimateDB.getColonyOrSlum());
					}
					if (Objects.nonNull(erpEstimateDB.getJeReturnAmount())) {
						returnAmount = erpEstimateDB.getJeReturnAmount();
					}
					if (Objects.nonNull(erpEstimateDB.getSecurityDeposit())) {
						securityDepositAmount = securityDepositAmount.add(erpEstimateDB.getSecurityDeposit());
					}

					if (Objects.nonNull(sspRegCharge)) {
						refundAmount.setSspRegCharge(sspRegCharge.subtract(BigDecimal.valueOf(5)));
					}

					BigDecimal sspMeterCost = erpEstimateDB.getSspMeterCost() != null ? erpEstimateDB.getSspMeterCost()
							: BigDecimal.ZERO;

					System.err.println("ssp Reg Charge : " + sspRegCharge);
					System.err.println("ssp Meter Cost : " + sspMeterCost);
					System.err.println("Total refundable amount : " + sspMeterCost);

					refundAmount.setSspMeterCost(sspMeterCost);
					refundAmount.setDemandErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
					refundAmount.setSecurityDepositAmount(securityDepositAmount);
					refundAmount.setSupervisionAmount(supervisionAmount);
					refundAmount.setDepositAmount(depositAmount);
					refundAmount.setKvaAmount(kvaAmount);
					refundAmount.setColonyOrSlumAmount(colonyOrSlumAmount);
					refundAmount.setReturnAmount(returnAmount);
					applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
					totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
					refundAmount.setRefundAmount(totalWapaskrneWalaPaisa);
//					added this given check on 21-08-2025 Monika Rajpoot 
					if (sspRegCharge != null) {
						totalHeadAmount = supervisionAmount.add(depositAmount).add(securityDepositAmount).add(kvaAmount)
								.add(colonyOrSlumAmount).add(returnAmount).add(sspMeterCost)
								.add(sspRegCharge.subtract(BigDecimal.valueOf(5)));
					} else {
						totalHeadAmount = supervisionAmount.add(depositAmount).add(securityDepositAmount).add(kvaAmount)
								.add(colonyOrSlumAmount).add(returnAmount).add(sspMeterCost);
					}
					if (totalWapaskrneWalaPaisa.compareTo(totalHeadAmount) != 0) {
						throw new ConsumerApplicationDetailException(
								new Response(HttpCode.NOT_ACCEPTABLE, "Amount not matched: Refundalable Amount is : "
										+ totalWapaskrneWalaPaisa + " and Total head amount is : " + totalHeadAmount));
					}

//					check end 21-08-2025 Monika Rajpoot 
					consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);

				}
			} else {
				Map<String, Object> paymentFromSanchayOrManualPayment = getPaymentFromSanchayOrManualPayment(
						demandDataFromPoseMachine, demandDataFromManualPayment, 1L, consumerApplicationDetail);
				totalReturnKrneWalaPaisa = (BigDecimal) paymentFromSanchayOrManualPayment
						.get("totalWapaskrneWalaPaisa");
				if (paymentFromSanchayOrManualPayment.containsKey("totalDemandZamaKiyaHuaPaisa")) {
					String amountStr = paymentFromSanchayOrManualPayment.get("totalDemandZamaKiyaHuaPaisa").toString();
					BigDecimal amount = new BigDecimal(amountStr);
					ConsumerAppCancellationRefundAmount demandRefund = new ConsumerAppCancellationRefundAmount();
					demandRefund.setApplicationNo(refundAmount.getConsumerApplicationNo());
					demandRefund.setPaymentType("Demand_fees (Payment not received on billdesk)");
					demandRefund.setTransactionDate(
							(String) paymentFromSanchayOrManualPayment.get("demandPaymentTransactionDate"));
					demandRefund.setTxnAmount(amount);
					demandRefund.setRefundableAmount(
							(BigDecimal) paymentFromSanchayOrManualPayment.get("demandRefundableAmount"));

					ConsumerAppReturnMaterialRefundAmnt check1 = consumerAppReturnMaterialRefundAmntRepository
							.checkApplicationExistOrNot(refundAmount.getConsumerApplicationNo(), amountStr,
									(BigDecimal) paymentFromSanchayOrManualPayment.get("demandRefundableAmount"));

					if (check1 == null) {
						consumerAppCancellationRefundAmountRepository.save(demandRefund);
					}
				}

				// Revise Fees Refund
				if (paymentFromSanchayOrManualPayment.containsKey("totalReviseZamaKiyaHuaPaisa")) {
					String reviseAmountStr = paymentFromSanchayOrManualPayment.get("totalReviseZamaKiyaHuaPaisa")
							.toString();
					BigDecimal amount = new BigDecimal(reviseAmountStr);
					ConsumerAppCancellationRefundAmount reviseRefund = new ConsumerAppCancellationRefundAmount();
					reviseRefund.setApplicationNo(refundAmount.getConsumerApplicationNo());
					reviseRefund.setPaymentType("Revise_fees (Payment not received on billdesk)");
					reviseRefund.setTransactionDate(
							(String) paymentFromSanchayOrManualPayment.get("revisePaymentTransactionDate"));
					reviseRefund.setTxnAmount(amount);
					reviseRefund.setRefundableAmount(
							(BigDecimal) paymentFromSanchayOrManualPayment.get("reviseRefundableAmount"));

					ConsumerAppReturnMaterialRefundAmnt check2 = consumerAppReturnMaterialRefundAmntRepository
							.checkApplicationExistOrNot(refundAmount.getConsumerApplicationNo(), reviseAmountStr,
									(BigDecimal) paymentFromSanchayOrManualPayment.get("reviseRefundableAmount"));

					if (check2 == null) {
						consumerAppCancellationRefundAmountRepository.save(reviseRefund);
					}
				}

				if (!demandDataFromPoseMachine.isEmpty()) {
					demandDataFromPoseMachine.stream().forEach(pose -> {
						if (pose.getPaymentType().equals("Revised_Demand_fees")) {
							refundAmount.setReviseErpNo(consumerApplicationDetail.getRevisedErpNumber());
						} else {
							refundAmount.setDemandErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
						}
					});
				}

				refundAmount
						.setSupervisionAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("supervisionAmount"));
				refundAmount.setDepositAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("depositAmount"));
				refundAmount.setKvaAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("kvaAmount"));
				refundAmount.setColonyOrSlumAmount(
						(BigDecimal) paymentFromSanchayOrManualPayment.get("colonyOrSlumAmount"));
				refundAmount.setReturnAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("returnAmount"));
				refundAmount.setMkmyPaidAmount((BigDecimal) paymentFromSanchayOrManualPayment.get("mkmyPaidAmount"));
				refundAmount.setSecurityDepositAmount(
						(BigDecimal) paymentFromSanchayOrManualPayment.get("securityDeposit"));
				refundAmount.setSspMeterCost((BigDecimal) paymentFromSanchayOrManualPayment.get("sspMeterCost"));
				refundAmount.setSspRegCharge((BigDecimal) paymentFromSanchayOrManualPayment.get("sspRegCharge"));
				refundAmount.setRefundAmount(totalReturnKrneWalaPaisa);
			}

		}

		refundAmount.setConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo());
		refundAmount.setConsumerAppId(consumerApplicationDetail.getConsumerApplicationId());
		refundAmount.setRefundType("Cancellation_Amount");
		double digit = Math.random();
		double digit1 = Math.random();
		String valueOf = String.valueOf(digit);
		String valueOf1 = String.valueOf(digit1);
		String substring = valueOf.substring(2, 8);
		String substring1 = valueOf1.substring(2, 8);
		refundAmount.setRefundVoucherNo(substring + substring1);

		RefundAmount refundAmntDB = null;
		RefundAmount byConsumerApplicationNoDB = refundAmountRepository
				.findByConsumerApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo());
		if (byConsumerApplicationNoDB == null) {
			refundAmntDB = refundAmountRepository.save(refundAmount);
		} else {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
					"Refund already initiated for this application no. "
							+ consumerApplicationDetail.getConsumerApplicationNo() + " for refund Type : "
							+ byConsumerApplicationNoDB.getRefundType()));
		}
		if (Objects.isNull(refundAmntDB)) {
			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
		} else {
			consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
					.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
			consumerApplictionDetailRepository.save(consumerApplicationDetail);
			return refundAmntDB;
		}

	}
//
////	-----------------------------   y Code ssp wale amount k liye likha gaya hai jisme ssp amount k registration charge se 5 rs. wapas nahi krna hai
//
	public Map<String, Object> getPaymentFromSanchayOrManualPayment(
			List<PoseMachinePostData> demandDataFromPoseMachine, ManualPayment demandDataFromManualPayment, Long value,
			ConsumerApplicationDetail consumerApplicationDetail) throws ConsumerApplicationDetailException {

		BigDecimal refundableAmount = BigDecimal.ZERO;
		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
		BigDecimal totalZamaKiyaHuaPaisa = BigDecimal.ZERO;
		String transactionDate = null;
		BigDecimal supervisionAmount = BigDecimal.ZERO;
		BigDecimal depositAmount = BigDecimal.ZERO;
		BigDecimal kvaAmount = BigDecimal.ZERO;
		BigDecimal colonyOrSlumAmount = BigDecimal.ZERO;
		BigDecimal returnAmount = BigDecimal.ZERO;
		BigDecimal mkmyPaidAmount = BigDecimal.ZERO;
		BigDecimal securityDepositAmount = BigDecimal.ZERO;
		BigDecimal sspMeterCost = BigDecimal.ZERO;
		BigDecimal sspRegCharge = BigDecimal.ZERO;
		Map<String, Object> result = new HashMap<>();

		boolean hasPoseData = demandDataFromPoseMachine != null && !demandDataFromPoseMachine.isEmpty();
		boolean hasManualPayment = demandDataFromManualPayment != null;

		if (hasPoseData || hasManualPayment) {
// VALUE =1 MEANS CANCELLATION AMOUNT NIKALNA HAI 
			
			if (value == 1L) {
				if (hasPoseData) {
					for (PoseMachinePostData pose : demandDataFromPoseMachine) {
						String paymentType = pose.getPaymentType();
						totalZamaKiyaHuaPaisa = pose.getTxnAmount();
						transactionDate = pose.getDateOfPayment().toString();

						if ("Revised_Demand_fees".equals(paymentType)) {
							ErpRev erpRev = erpRevRepository
									.findByConsAppNo(consumerApplicationDetail.getConsumerApplicationNo());

							if (erpRev != null) {

								if (totalZamaKiyaHuaPaisa.compareTo(erpRev.getPayAmt()) >= 0) {
									BigDecimal cgst = erpRev.getRemCgst();
									BigDecimal sgst = erpRev.getRemSgst();
									BigDecimal totalReviseTax = (cgst != null && sgst != null) ? cgst.add(sgst)
											: BigDecimal.ZERO;
									refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalReviseTax);
									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);

									supervisionAmount = supervisionAmount.add(erpRev.getRemSupervisionAmt());
									if (Objects.nonNull(erpRev.getRemmDepositAmt())) {
										depositAmount = depositAmount.add(erpRev.getRemmDepositAmt());
									}
									if (Objects.nonNull(erpRev.getRemKvaAmt())) {
										kvaAmount = kvaAmount.add(erpRev.getRemKvaAmt());
									}
									if (Objects.nonNull(erpRev.getRemColonyOrSlum())) {
										colonyOrSlumAmount = colonyOrSlumAmount.add(erpRev.getRemColonyOrSlum());
									}
									if (Objects.nonNull(erpRev.getRemReturnAmt())) {
										returnAmount = returnAmount.add(erpRev.getRemReturnAmt());
									}

								} else {
									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
											"Revise Payment paid less through sanchay Portal"));
								}
							}
						} else {
							if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
								MmkyPayAmount mmkyData = mmkyPayAmountRespository.findByConsumerApplicationNumber(
										consumerApplicationDetail.getConsumerApplicationNo());

								if (mmkyData != null
										&& totalZamaKiyaHuaPaisa.compareTo(mmkyData.getPayableAmount()) == 0) {

									totalWapaskrneWalaPaisa = totalZamaKiyaHuaPaisa.subtract(BigDecimal.valueOf(2500));
									result.put("demandPaymentTransactionDate", transactionDate);
									result.put("totalDemandZamaKiyaHuaPaisa", totalZamaKiyaHuaPaisa);
									result.put("demandRefundableAmount", totalWapaskrneWalaPaisa);
									result.put("mkmyPaidAmount", mmkyData.getCarryAmountByApplicant());
									result.put("securityDeposit", mmkyData.getSecurityDeposit());

								}
							} else {
								ErpEstimateAmountData erpData = estimateAmountRepository
										.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());

								if (erpData != null) {
									Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
									BigDecimal compareAmount = (schemeId.equals(1L))
											? erpData.getTotalBalanceSupervisionAmount()
											: erpData.getTotalBalanceDepositAmount();

									boolean isValidAmount = (schemeId.equals(1L)
											&& totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0)
											|| (schemeId.equals(2L)
													&& totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0);

									if (isValidAmount) {
										if (erpData != null) {
											BigDecimal cgst = erpData.getCgst();
											BigDecimal sgst = erpData.getSgst();
											BigDecimal totalTax = (cgst != null && sgst != null) ? cgst.add(sgst)
													: BigDecimal.ZERO;
											sspRegCharge = erpData.getSspRegistrationCharge();
											
											if(sspRegCharge==null) {
												refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalTax);
											}else {
												refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalTax)
												.subtract(BigDecimal.valueOf(5));
											}
											
//											this case is added for govt. application and NOW 12 Date: 23-12-2025
											if (erpData.getRegistrationCharges() != null
													&& erpData.getRegistrationCharges().equals(BigDecimal.valueOf(1180))) {
												refundableAmount = refundableAmount.subtract(BigDecimal.valueOf(1180));
											}
											totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
											supervisionAmount = supervisionAmount.add(erpData.getSupervisionAmount());
											if (Objects.nonNull(erpData.getDepositAmount())) {
												depositAmount = depositAmount.add(erpData.getDepositAmount());
											}
											if (Objects.nonNull(erpData.getKvaLoad())) {
												kvaAmount = kvaAmount.add(erpData.getKvaLoad());
											}
											if (Objects.nonNull(erpData.getColonyOrSlum())) {
												colonyOrSlumAmount = colonyOrSlumAmount.add(erpData.getColonyOrSlum());
											}
											if (Objects.nonNull(erpData.getJeReturnAmount())) {
												returnAmount = returnAmount.add(erpData.getJeReturnAmount());
											}
											if (Objects.nonNull(erpData.getSecurityDeposit())) {
												securityDepositAmount = securityDepositAmount
														.add(erpData.getSecurityDeposit());
											}
											if (Objects.nonNull(erpData.getSspMeterCost())) {
												sspMeterCost = erpData.getSspMeterCost();
											}
											if (Objects.nonNull(sspRegCharge)) {
												sspRegCharge =sspRegCharge
														.subtract(BigDecimal.valueOf(5));
											}

										}

									} else {
										throw new ConsumerApplicationDetailException(
												new Response(HttpCode.NOT_ACCEPTABLE,
														"Demand Payment paid less through sanchay Portal"));
									}
								}
							}
						}
					}
					result.put("securityDeposit", securityDepositAmount);
					result.put("returnAmount", returnAmount);
					result.put("supervisionAmount", supervisionAmount);
					result.put("depositAmount", depositAmount);
					result.put("kvaAmount", kvaAmount);
					result.put("colonyOrSlumAmount", colonyOrSlumAmount);
					result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
					result.put("sspMeterCost", sspMeterCost);
					result.put("sspRegCharge", sspRegCharge);
				} else if (hasManualPayment) {
					totalZamaKiyaHuaPaisa = new BigDecimal(demandDataFromManualPayment.getAmount());
					System.err.println("totalZamaKiyaHuaPaisa" + totalZamaKiyaHuaPaisa);
					transactionDate = demandDataFromManualPayment.getPaymentDate();
					if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
						MmkyPayAmount mmkyData = mmkyPayAmountRespository
								.findByConsumerApplicationNumber(consumerApplicationDetail.getConsumerApplicationNo());

						if (mmkyData != null && totalZamaKiyaHuaPaisa.compareTo(mmkyData.getPayableAmount()) == 0) {
							totalWapaskrneWalaPaisa = totalZamaKiyaHuaPaisa.subtract(BigDecimal.valueOf(2500));
							result.put("demandPaymentTransactionDate", transactionDate);
							result.put("totalDemandZamaKiyaHuaPaisa", totalZamaKiyaHuaPaisa);
							result.put("demandRefundableAmount", totalWapaskrneWalaPaisa);
							result.put("mkmyPaidAmount", mmkyData.getCarryAmountByApplicant());
							result.put("securityDeposit", mmkyData.getSecurityDeposit());
						}
					} else {
						ErpEstimateAmountData erpData = estimateAmountRepository
								.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());

						if (erpData != null) {
							Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
							BigDecimal compareAmount = (schemeId.equals(1L))
									? erpData.getTotalBalanceSupervisionAmount()
									: erpData.getTotalBalanceDepositAmount();

							boolean isValidAmount = (schemeId.equals(1L)
									&& totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0)
									|| (schemeId.equals(2L) && totalZamaKiyaHuaPaisa.compareTo(compareAmount) >= 0);

							if (isValidAmount) {
								if (erpData != null) {
									BigDecimal cgst = erpData.getCgst();
									BigDecimal sgst = erpData.getSgst();
									BigDecimal totalTax = (cgst != null && sgst != null) ? cgst.add(sgst)
											: BigDecimal.ZERO;
									
									sspRegCharge = erpData.getSspRegistrationCharge();
									
									if(sspRegCharge==null) {
										refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalTax);
									}else {
										refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalTax)
										.subtract(BigDecimal.valueOf(5));
									}
//									this case is added for govt. application and NOW 12 Date: 23-12-2025 Date: 23-12-2025
									if (erpData.getRegistrationCharges() != null
											&& erpData.getRegistrationCharges().equals(BigDecimal.valueOf(1180))) {
										refundableAmount = refundableAmount.subtract(BigDecimal.valueOf(1180));
									}
									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);

									supervisionAmount = supervisionAmount.add(erpData.getSupervisionAmount());
									if (Objects.nonNull(erpData.getDepositAmount())) {
										depositAmount = depositAmount.add(erpData.getDepositAmount());
									}
									if (Objects.nonNull(erpData.getKvaLoad())) {
										kvaAmount = kvaAmount.add(erpData.getKvaLoad());
									}
									if (Objects.nonNull(erpData.getColonyOrSlum())) {
										colonyOrSlumAmount = colonyOrSlumAmount.add(erpData.getColonyOrSlum());
									}
									if (Objects.nonNull(erpData.getJeReturnAmount())) {
										returnAmount = returnAmount.add(erpData.getJeReturnAmount());
									}
									if (Objects.nonNull(erpData.getSecurityDeposit())) {
										securityDepositAmount = securityDepositAmount.add(erpData.getSecurityDeposit());
									}
									if (Objects.nonNull(erpData.getSspMeterCost())) {
										sspMeterCost = erpData.getSspMeterCost();
									}
									if (Objects.nonNull(sspRegCharge)) {
										sspRegCharge =sspRegCharge
												.subtract(BigDecimal.valueOf(5));
									}
								}

								result.put("demandPaymentTransactionDate", transactionDate);
								result.put("totalDemandZamaKiyaHuaPaisa", totalZamaKiyaHuaPaisa);
								result.put("demandRefundableAmount", refundableAmount);

								System.err.println(" demand totalWapaskrneWalaPaisa : " + totalWapaskrneWalaPaisa);
							} else {
								throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
										"Demand Payment paid less through Manual Payment"));
							}
						}

						result.put("securityDeposit", securityDepositAmount);
						result.put("returnAmount", returnAmount);
						result.put("supervisionAmount", supervisionAmount);
						result.put("depositAmount", depositAmount);
						result.put("kvaAmount", kvaAmount);
						result.put("colonyOrSlumAmount", colonyOrSlumAmount);
						result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
						result.put("sspMeterCost", sspMeterCost);
						result.put("sspRegCharge", sspRegCharge);
					}
				}
			} else {
				// For value != 1
				if (hasPoseData) {
					for (PoseMachinePostData pose : demandDataFromPoseMachine) {
						String paymentType = pose.getPaymentType();
						BigDecimal txnAmount = pose.getTxnAmount();
						transactionDate = pose.getDateOfPayment().toString();

						if ("Revised_Demand_fees".equals(paymentType)) {
							ErpRev erpRev = erpRevRepository
									.findByConsAppNo(consumerApplicationDetail.getConsumerApplicationNo());

							if (erpRev != null && erpRev.getRemReturnAmt() != null) {
								if (txnAmount.compareTo(erpRev.getPayAmt()) >= 0) {
									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpRev.getRemReturnAmt());
									result.put("revisePaymentTransactionDate", transactionDate);
									result.put("totalReviseZamaKiyaHuaPaisa", txnAmount);
									result.put("reviseReturnAmount", erpRev.getRemReturnAmt());
								} else {
									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
											"Revise Payment paid less through sanchay Portal"));
								}
							}
						} else {
							ErpEstimateAmountData erpData = estimateAmountRepository
									.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());

							if (erpData != null && erpData.getJeReturnAmount() != null) {
								Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
								BigDecimal compareAmount = (schemeId.equals(1L))
										? erpData.getTotalBalanceSupervisionAmount()
										: erpData.getTotalBalanceDepositAmount();

								boolean isValidAmount = (schemeId.equals(1L) && txnAmount.compareTo(compareAmount) >= 0)
										|| (schemeId.equals(2L) && txnAmount.compareTo(compareAmount) >= 0);

								if (isValidAmount) {
									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
									result.put("demandPaymentTransactionDate", transactionDate);
									result.put("totalDemandZamaKiyaHuaPaisa", txnAmount);
									result.put("demandReturnAmount", erpData.getJeReturnAmount());
								} else {
									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
											"Demand Payment paid less through sanchay Portal"));
								}
							}
						}
					}
					result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
				} else if (hasManualPayment) {
					ErpEstimateAmountData erpData = estimateAmountRepository
							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());

					if (erpData != null && erpData.getJeReturnAmount() != null) {
						Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
						BigDecimal manualAmount = new BigDecimal(demandDataFromManualPayment.getAmount());
						BigDecimal compareAmount = (schemeId.equals(1L)) ? erpData.getTotalBalanceSupervisionAmount()
								: erpData.getTotalBalanceDepositAmount();

						boolean isValidAmount = (schemeId.equals(1L) && manualAmount.compareTo(compareAmount) >= 0)
								|| (schemeId.equals(2L) && manualAmount.compareTo(compareAmount) >= 0);

						if (isValidAmount) {
							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
							transactionDate = demandDataFromManualPayment.getPaymentDate();
							result.put("demandPaymentTransactionDate", transactionDate);
							result.put("totalDemandZamaKiyaHuaPaisa", manualAmount);
							result.put("demandReturnAmount", erpData.getJeReturnAmount());
							result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
						} else {
							throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
									"Demand Payment paid less through sanchay Portal"));
						}
					}
				}
			}
		}

		return result;
	}
//
////	---------------------
//
	@Override
	public Map<String, BigDecimal> getPaymentDetailForRefund(String consumerApplicationNo, Long value)
			throws ConsumerApplicationDetailException {
		BigDecimal refundableAmount = BigDecimal.ZERO;
		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;

		Map<String, BigDecimal> result = new HashMap<>();

		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
				.findByConsumerApplicationNumber(consumerApplicationNo);
		if (Objects.isNull(consumerApplicationDetail)) {
			throw new ConsumerApplicationDetailException(
					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
		} else {
			List<BillDeskPaymentResponse> allPaymentDetails = null;
			List<PoseMachinePostData> demandDataFromPoseMachine = null;
			ManualPayment demandDataFromManualPayment = null;
			try {
				allPaymentDetails = billPaymentResponseeeeeeeRepository.getAllPaymentDetails(consumerApplicationNo);
				if (allPaymentDetails.isEmpty()) {
					demandDataFromPoseMachine = poseMachinePostDataRepository
							.getDemandDataFromPoseMachineData(consumerApplicationNo);

					if (demandDataFromPoseMachine.isEmpty() && allPaymentDetails.isEmpty()) {
						demandDataFromManualPayment = manualPaymentRepository
								.getDemandDataFromManualPayment(consumerApplicationNo);
					}
				}

				System.err.println(allPaymentDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (allPaymentDetails.isEmpty() && demandDataFromPoseMachine.isEmpty()
					&& Objects.isNull(demandDataFromManualPayment)) {
				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
						"Demand Fees not received for application no. : " + consumerApplicationNo));
			}
//			y poora case chalna chahiye jab allPaymentDetails list empty na ho 
//			value == 1 matlb (application cancle karna hai)
			if (!allPaymentDetails.isEmpty()) {
				if (value == 1L) {
					for (BillDeskPaymentResponse bill : allPaymentDetails) {
						// cancellation ka total amount dena hai

						if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
							ErpRev erpRevDB = erpRevRepository.findByConsAppNoAndPayAmt(consumerApplicationNo,
									new BigDecimal(bill.getAmount()));
							if (erpRevDB != null) {

								System.err.println("erp aaaaaa : " + erpRevDB.getPayAmt());
// erpRevDB.getRemCgst() != null y check isliye lagaya h kyuki kabhi kabhi remCgst remSgst me null hota kyuki data remColonyOrSlum me hota hai
								if (erpRevDB.getRemCgst() != null) {
									BigDecimal remCgst = erpRevDB.getRemCgst();
									BigDecimal remSgst = erpRevDB.getRemSgst();
									BigDecimal totalCgstSgst = remCgst.add(remSgst);

									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
									refundableAmount = billdeskAmount.subtract(totalCgstSgst);
								} else {
									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
									refundableAmount = billdeskAmount;
								}
								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);

							}
						} else {
							if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
								MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
										.findByConsumerApplicationNumber(consumerApplicationNo);

								BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
								if (billdeskAmount.compareTo(findByConsumerApplicationNumber.getPayableAmount()) == 0) {
									totalWapaskrneWalaPaisa = new BigDecimal(bill.getAmount())
											.subtract(new BigDecimal(2500));
								}

								else {
									totalWapaskrneWalaPaisa = new BigDecimal(0.0);
								}
							} else {

								ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
										.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
								BigDecimal sspRegCharge = erpEstimateDB.getSspRegistrationCharge();
								if (erpEstimateDB.getCgst() != null) {

									BigDecimal cgst = erpEstimateDB.getCgst();
									BigDecimal sgst = erpEstimateDB.getSgst();
									BigDecimal totalCgstSgst = cgst.add(sgst);
									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());

									if (sspRegCharge == null) {
										refundableAmount = billdeskAmount.subtract(totalCgstSgst);
									} else {
//										sspRegCharge min. 5 rs to aayega hi 7-11-2025
										refundableAmount = billdeskAmount.subtract(totalCgstSgst)
												.subtract(new BigDecimal(5));
									}
								} else {
									BigDecimal billdeskAmount = new BigDecimal(bill.getAmount());
									if (sspRegCharge == null) {
										refundableAmount = billdeskAmount;
									} else {
										refundableAmount = billdeskAmount.subtract(new BigDecimal(5));
									}

								}

//								this case is added for govt. application and NOW 12 Date: 23-12-2025 Date: 23-12-2025
								if (erpEstimateDB.getRegistrationCharges() != null
										&& erpEstimateDB.getRegistrationCharges().equals(BigDecimal.valueOf(1180))) {
									refundableAmount = refundableAmount.subtract(BigDecimal.valueOf(1180));
								}
								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);

							}
						}
					}
				} else { // return ka amount dena hai
					for (BillDeskPaymentResponse bill : allPaymentDetails) {

						if (bill.getAdditionalInfo().equals("Revised_Demand_fees")) {
							ErpRev erpRev = erpRevRepository.findByConsAppNo(consumerApplicationNo);
							if (erpRev != null && erpRev.getRemReturnAmt() != null) {
								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpRev.getRemReturnAmt());
							}
						} else {
							ErpEstimateAmountData erpData = estimateAmountRepository
									.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
							if (erpData != null && erpData.getJeReturnAmount() != null) {
								totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
							}
						}

					}
				}
			} else {

				Map<String, Object> paymentFromSanchayOrManualPayment = getPaymentFromSanchayOrManualPayment(
						demandDataFromPoseMachine, demandDataFromManualPayment, value, consumerApplicationDetail);
				totalWapaskrneWalaPaisa = (BigDecimal) paymentFromSanchayOrManualPayment.get("totalWapaskrneWalaPaisa");
			}
//			yhan tak code chalega billdesk k liye
			BeforeRefundAmountCheck byApplicationNo = beforeRefundAmountCheckRepository
					.findByApplicationNo(consumerApplicationNo);
			if (byApplicationNo == null) {
				BeforeRefundAmountCheck check = new BeforeRefundAmountCheck();
				check.setApplicationNo(consumerApplicationNo);
				check.setRefundableAmount(totalWapaskrneWalaPaisa);
				beforeRefundAmountCheckRepository.save(check);
			} else {
				byApplicationNo.setRefundableAmount(totalWapaskrneWalaPaisa);
				beforeRefundAmountCheckRepository.save(byApplicationNo);
			}

			result.put("totalRefundAmount", totalWapaskrneWalaPaisa);
			return result;
		}
	}

}
