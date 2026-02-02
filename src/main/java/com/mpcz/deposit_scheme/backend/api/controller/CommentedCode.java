/**
 * 
 */
package com.mpcz.deposit_scheme.backend.api.controller;

/**
 * @author Monika Rajpoot
 * @since 07-Nov-2025
 * @description CommentedCode.java class description
 */

public class CommentedCode {

	
	
	
	
	
	
//	Refund amount serviceImpl line no. 585 to 783
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public RefundAmount saveReturnAmntApplication(RefundAmount refundAmount) throws ConsumerApplicationDetailException {
//		BigDecimal totalReturnKrneWalaPaisa = BigDecimal.ZERO;
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
//		if (Objects.isNull(consumerApplicationDetail)) {
//			throw new ConsumerApplicationDetailException(
//					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
//		} else {
//			if (consumerApplicationDetail.getErpVersion() != null
//					&& consumerApplicationDetail.getErpVersion().equals("V2")) {
//
//				ErpRev erpRevDB = erpRevRepository.findByConsAppNo(refundAmount.getConsumerApplicationNo());
//				if (erpRevDB.getRemReturnAmt() != null) {
//					BigDecimal reviseReturnAmtDB = erpRevDB.getRemReturnAmt();
//
//					if (reviseReturnAmtDB.compareTo(BigDecimal.ZERO) > 0) {
//						String reviseReturn = reviseReturnAmtDB.toString();
//						String newReviseReturn = reviseReturn.concat(".%");
//
//						System.err.println("   newReviseReturn    : " + newReviseReturn);
//						BillDeskPaymentResponse billdeskSeReviseDemandKaAmount = billPaymentResponseeeeeeeRepository
//								.getReviseAmountData(refundAmount.getConsumerApplicationNo(), newReviseReturn);
//						System.err.println("aaaaaaaaa : " + new Gson().toJson(billdeskSeReviseDemandKaAmount));
//						ConsumerAppReturnMaterialRefundAmnt conKoReturnKrneWalaAmount = new ConsumerAppReturnMaterialRefundAmnt();
//						conKoReturnKrneWalaAmount.setApplicationNo(refundAmount.getConsumerApplicationNo());
//						conKoReturnKrneWalaAmount.setMerchantId(billdeskSeReviseDemandKaAmount.getMercid());
//						conKoReturnKrneWalaAmount.setOrderId(billdeskSeReviseDemandKaAmount.getOrderid());
//						conKoReturnKrneWalaAmount.setPaymentType(billdeskSeReviseDemandKaAmount.getAdditionalInfo());
//						conKoReturnKrneWalaAmount
//								.setTransactionDate(billdeskSeReviseDemandKaAmount.getTransactionDate());
//						conKoReturnKrneWalaAmount.setTxnAmount(billdeskSeReviseDemandKaAmount.getAmount());
//						conKoReturnKrneWalaAmount.setTxnId(billdeskSeReviseDemandKaAmount.getTranId());
//						conKoReturnKrneWalaAmount.setRefundableAmount(reviseReturnAmtDB);
//						ConsumerAppReturnMaterialRefundAmnt byTxnIdDB = consumerAppReturnMaterialRefundAmntRepository
//								.findByTxnIdAndIsActive(billdeskSeReviseDemandKaAmount.getTranId());
//						if (byTxnIdDB == null) {
//							consumerAppReturnMaterialRefundAmntRepository.save(conKoReturnKrneWalaAmount);
//						}
//						totalReturnKrneWalaPaisa = totalReturnKrneWalaPaisa.add(reviseReturnAmtDB);
//
//					}
//				}
//				ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//						.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//				if (erpEstimateDB.getJeReturnAmount() != null) {
//					String totalAmount = null;
//					BigDecimal jeReturnAmountDB = erpEstimateDB.getJeReturnAmount();
//					if (jeReturnAmountDB.compareTo(BigDecimal.ZERO) > 0) {
//						if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
//							totalAmount = erpEstimateDB.getTotalBalanceSupervisionAmount().toString().concat(".%");
//						} else {
//							totalAmount = erpEstimateDB.getTotalBalanceDepositAmount().toString().concat(".%");
//						}
//						if (totalAmount != null) {
//							BillDeskPaymentResponse demandAmountDataForReturnMaterial = billPaymentResponseeeeeeeRepository
//									.getDemandAmountDataForReturnMaterial(refundAmount.getConsumerApplicationNo(),
//											totalAmount);
//							ConsumerAppReturnMaterialRefundAmnt conKoReturnKrneWalaAmount = new ConsumerAppReturnMaterialRefundAmnt();
//							conKoReturnKrneWalaAmount.setApplicationNo(refundAmount.getConsumerApplicationNo());
//							conKoReturnKrneWalaAmount.setMerchantId(demandAmountDataForReturnMaterial.getMercid());
//							conKoReturnKrneWalaAmount.setOrderId(demandAmountDataForReturnMaterial.getOrderid());
//							conKoReturnKrneWalaAmount
//									.setPaymentType(demandAmountDataForReturnMaterial.getAdditionalInfo());
//							conKoReturnKrneWalaAmount
//									.setTransactionDate(demandAmountDataForReturnMaterial.getTransactionDate());
//							conKoReturnKrneWalaAmount.setTxnAmount(demandAmountDataForReturnMaterial.getAmount());
//							conKoReturnKrneWalaAmount.setTxnId(demandAmountDataForReturnMaterial.getTranId());
//							conKoReturnKrneWalaAmount.setRefundableAmount(jeReturnAmountDB);
//
//							ConsumerAppReturnMaterialRefundAmnt byTxnIdDB = consumerAppReturnMaterialRefundAmntRepository
//									.findByTxnIdAndIsActive(demandAmountDataForReturnMaterial.getTranId());
//							if (byTxnIdDB == null) {
//								consumerAppReturnMaterialRefundAmntRepository.save(conKoReturnKrneWalaAmount);
//							}
//
//						}
//						totalReturnKrneWalaPaisa = totalReturnKrneWalaPaisa.add(jeReturnAmountDB);
//					}
//				}
//			} else {
//				ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//						.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//				if (erpEstimateDB.getJeReturnAmount() != null) {
//					String totalAmount = null;
//					BigDecimal jeReturnAmountDB = erpEstimateDB.getJeReturnAmount();
//					if (jeReturnAmountDB.compareTo(BigDecimal.ZERO) > 0) {
//						if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
//							totalAmount = erpEstimateDB.getTotalBalanceSupervisionAmount().toString().concat(".%");
//						} else {
//							totalAmount = erpEstimateDB.getTotalBalanceDepositAmount().toString().concat(".%");
//						}
//						if (totalAmount != null) {
//							int dotIndex = totalAmount.indexOf('.'); // Find the first dot
//
//							if (dotIndex != -1) {
//								totalAmount = totalAmount.substring(0, dotIndex).concat(".%"); // Extract substring
//																								// before the first dot
//							}
//							BillDeskPaymentResponse demandAmountDataForReturnMaterial = billPaymentResponseeeeeeeRepository
//									.getDemandAmountDataForReturnMaterial(refundAmount.getConsumerApplicationNo(),
//											totalAmount);
//							ConsumerAppReturnMaterialRefundAmnt conKoReturnKrneWalaAmount = new ConsumerAppReturnMaterialRefundAmnt();
//							conKoReturnKrneWalaAmount.setApplicationNo(refundAmount.getConsumerApplicationNo());
//							conKoReturnKrneWalaAmount.setMerchantId(demandAmountDataForReturnMaterial.getMercid());
//							conKoReturnKrneWalaAmount.setOrderId(demandAmountDataForReturnMaterial.getOrderid());
//							conKoReturnKrneWalaAmount
//									.setPaymentType(demandAmountDataForReturnMaterial.getAdditionalInfo());
//							conKoReturnKrneWalaAmount
//									.setTransactionDate(demandAmountDataForReturnMaterial.getTransactionDate());
//							conKoReturnKrneWalaAmount.setTxnAmount(demandAmountDataForReturnMaterial.getAmount());
//							conKoReturnKrneWalaAmount.setTxnId(demandAmountDataForReturnMaterial.getTranId());
//							conKoReturnKrneWalaAmount.setRefundableAmount(jeReturnAmountDB);
//							ConsumerAppReturnMaterialRefundAmnt byTxnIdDB = consumerAppReturnMaterialRefundAmntRepository
//									.findByTxnIdAndIsActive(demandAmountDataForReturnMaterial.getTranId());
//							if (byTxnIdDB == null) {
//								consumerAppReturnMaterialRefundAmntRepository.save(conKoReturnKrneWalaAmount);
//							}
//							totalReturnKrneWalaPaisa = totalReturnKrneWalaPaisa.add(jeReturnAmountDB);
//						}
//					}
//				}
//			}
//			refundAmount.setRefundAmount(totalReturnKrneWalaPaisa);
//			refundAmount.setRefundType("Return_Amount");
//			double digit = Math.random();
//			double digit1 = Math.random();
//			String valueOf = String.valueOf(digit);
//			String valueOf1 = String.valueOf(digit1);
//			String substring = valueOf.substring(2, 8);
//			String substring1 = valueOf1.substring(2, 8);
//			refundAmount.setRefundVoucherNo(substring + substring1);
//			refundAmount.setConsumerAppId(consumerApplicationDetail.getConsumerApplicationId());
//			RefundAmount save = null;
//			RefundAmount byConsumerApplicationNoDB = refundAmountRepository
//					.findByConsumerApplicationNoIsActive(consumerApplicationDetail.getConsumerApplicationNo());
//			if (byConsumerApplicationNoDB == null) {
//				save = refundAmountRepository.save(refundAmount);
//			} else {
//				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT,
//						"Refund already initiated for this application no. "
//								+ consumerApplicationDetail.getConsumerApplicationNo() + " for refund Type : "
//								+ byConsumerApplicationNoDB.getRefundType()));
//			}
//			if (Objects.isNull(save)) {
//				throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
//			} else {
//				consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
//						.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_STC_FOR_REFUND.getId()).get());
//				consumerApplictionDetailRepository.save(consumerApplicationDetail);
//				return save;
//			}
//		}
//	}



//	@Override
//	public RefundRejectedRemark financeAoRefundReject(String consumerApplicationNo, boolean financeAoRefundReject,
//			String financeAoId, String remark) throws ConsumerApplicationDetailException {
//		RefundRejectedRemark save = null;
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(consumerApplicationNo);
//		if (Objects.isNull(consumerApplicationDetail)) {
//			throw new ConsumerApplicationDetailException(
//					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
//		} else {
//			if (financeAoRefundReject) {
//				RefundRejectedRemark rejectedRemark = new RefundRejectedRemark();
//				rejectedRemark.setApplicationNo(consumerApplicationNo);
//				rejectedRemark.setRemark(remark);
//				Optional<User> userId = userRepository.findByUserId(financeAoId);
//				if (userId.isPresent()) {
//					User user = userId.get();
//					rejectedRemark.setUserId(financeAoId);
//					rejectedRemark.setUserName(user.getUserName());
//					List<Role> userRoles = user.getUserRoles();
//					for (Role role : userRoles) {
//						rejectedRemark.setUserRole(role.getRole());
//
//					}
//				}
//				save = refundRejectedRemarkRepository.save(rejectedRemark);
//				RefundAmount refundAmountDB = refundAmountRepository.findByConsumerApplicationNo(consumerApplicationNo);
//				if (refundAmountDB.getRefundType().equals("Cancellation_Amount")) {
//					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
//							.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_GM_FOR_REFUND.getId()).get());
//				} else { // y Return Material and Negative revise k liye chalega
//					consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
//							.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
//				}
//				consumerApplictionDetailRepository.save(consumerApplicationDetail);
//			}
//
//		}
//		return save;
//	}
	
	
//	line 836 to 1110
//	************************************************************************************************************
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

//	y code comment kiya hai kyuki iska better and understandable code below method me likha hua hai 26-06-2025

//	public Map<String, Object> getPaymentFromSanchayOrManualPayment(List<PoseMachinePostData> demandDataFromPoseMachine,
//			ManualPayment demandDataFromManualPayment, Long value, ConsumerApplicationDetail consumerApplicationDetail)
//			throws ConsumerApplicationDetailException {
//		BigDecimal refundableAmount = BigDecimal.ZERO;
//		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//		BigDecimal totalZamaKiyaHuaPaisa = BigDecimal.ZERO;
//		String transactionDate = null;
//		Map<String, Object> result = new HashMap<>();
//
//		if (!demandDataFromPoseMachine.isEmpty() || Objects.nonNull(demandDataFromManualPayment)) {
////			if (Objects.nonNull(demandDataFromPoseMachine)) {
////				totalZamaKiyaHuaPaisa = demandDataFromPoseMachine.getTxnAmount();
////				transactionDate = demandDataFromPoseMachine.getDateOfPayment().toString();
////			} else {
////				totalZamaKiyaHuaPaisa = new BigDecimal(demandDataFromManualPayment.getAmount());
////				transactionDate = demandDataFromManualPayment.getPaymentDate();
////			}
////			result.put("totalZamaKiyaHuaPaisa", totalZamaKiyaHuaPaisa);
////			result.put("transactionDate", transactionDate);
//			if (value == 1l) {
//
//				if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
//					MmkyPayAmount findByConsumerApplicationNumber = mmkyPayAmountRespository
//							.findByConsumerApplicationNumber(consumerApplicationDetail.getConsumerApplicationNo());
//
//					if (totalZamaKiyaHuaPaisa.compareTo(findByConsumerApplicationNumber.getPayableAmount()) == 0) {
//						totalWapaskrneWalaPaisa = totalZamaKiyaHuaPaisa.subtract(new BigDecimal(2500));
//					} else {
//						totalWapaskrneWalaPaisa = new BigDecimal(0.0);
//					}
//				} else {
//
//					ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//					if (erpEstimateDB.getCgst() != null) {
//
//						BigDecimal cgst = erpEstimateDB.getCgst();
//						BigDecimal sgst = erpEstimateDB.getSgst();
//						BigDecimal totalCgstSgst = cgst.add(sgst);
//						refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalCgstSgst);
//					} else {
//
//						refundableAmount = totalZamaKiyaHuaPaisa;
//					}
//
//					totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//				}
//
//				result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//			} else {
//				if (!demandDataFromPoseMachine.isEmpty()) {
//
//					for (PoseMachinePostData pose : demandDataFromPoseMachine) {
//
//						if (pose.getPaymentType().equals("Revised_Demand_fees")) {
//							ErpRev erpRev = erpRevRepository
//									.findByConsAppNo(consumerApplicationDetail.getConsumerApplicationNo());
//
//							if (erpRev != null && erpRev.getRemReturnAmt() != null) {
//								if (pose.getTxnAmount().compareTo(erpRev.getPayAmt()) >= 0) {
//									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpRev.getRemReturnAmt());
//									transactionDate = pose.getDateOfPayment().toString();
//									result.put("revisePaymentTransactionDate", transactionDate);
//									result.put("totalReviseZamaKiyaHuaPaisa", pose.getTxnAmount());
//									result.put("reviseReturnAmount", erpRev.getRemReturnAmt());
//								} else {
//									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//											"Revise Payment paid less through sanchay Portal"));
//								}
//								;
//							}
//						} else {
//							ErpEstimateAmountData erpData = estimateAmountRepository
//									.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//							if (erpData != null && erpData.getJeReturnAmount() != null) {
//								if ((consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1l) && pose
//										.getTxnAmount().compareTo(erpData.getTotalBalanceSupervisionAmount()) >= 0)
//										|| (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(2l)
//												&& pose.getTxnAmount()
//														.compareTo(erpData.getTotalBalanceDepositAmount()) > 0)) {
//									totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//									transactionDate = pose.getDateOfPayment().toString();
//									result.put("demandPaymentTransactionDate", transactionDate);
//									result.put("totalDemandZamaKiyaHuaPaisa", pose.getTxnAmount());
//									result.put("demandReturnAmount", erpData.getJeReturnAmount());
//								} else {
//									throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//											"Demand Payment paid less through sanchay Portal"));
//								}
//								;
//							}
//						}
//
//					}
//					result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//				} else {
////				Yhan revise demand fees ka bhi data add krwana pdega
//					ErpEstimateAmountData erpData = estimateAmountRepository
//							.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//					if (erpData != null && erpData.getJeReturnAmount() != null) {
//						if ((consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1l)
//								&& new BigDecimal(demandDataFromManualPayment.getAmount())
//										.compareTo(erpData.getTotalBalanceSupervisionAmount()) >= 0)
//								|| (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(2l)
//										&& new BigDecimal(demandDataFromManualPayment.getAmount())
//												.compareTo(erpData.getTotalBalanceDepositAmount()) > 0)) {
//							totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//							transactionDate = demandDataFromManualPayment.getPaymentDate();
//							result.put("demandPaymentTransactionDate", transactionDate);
//							result.put("totalDemandZamaKiyaHuaPaisa", demandDataFromManualPayment.getAmount());
//							result.put("demandReturnAmount", erpData.getJeReturnAmount());
//							result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//						} else {
//							throw new ConsumerApplicationDetailException(new Response(HttpCode.NOT_ACCEPTABLE,
//									"Demand Payment paid less through sanchay Portal"));
//						}
//						;
//					}
//
//				}
//			}
//
//		}
//		return result;
//	}
	
	
//	************************************************************************************************************
//	public Map<String, Object> getPaymentFromSanchayOrManualPayment(
//	        List<PoseMachinePostData> demandDataFromPoseMachine,
//	        ManualPayment demandDataFromManualPayment,
//	        Long value,
//	        ConsumerApplicationDetail consumerApplicationDetail) throws ConsumerApplicationDetailException {
//
//	    BigDecimal refundableAmount = BigDecimal.ZERO;
//	    BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//	    BigDecimal totalZamaKiyaHuaPaisa = BigDecimal.ZERO;
//	    String transactionDate = null;
//	    Map<String, Object> result = new HashMap<>();
//
//	    boolean hasPoseData = demandDataFromPoseMachine != null && !demandDataFromPoseMachine.isEmpty();
//	    boolean hasManualPayment = demandDataFromManualPayment != null;
//
//	    if (hasPoseData || hasManualPayment) {
//
//	        if (value == 1L) {
//	            // For value 1
//	            if (consumerApplicationDetail.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8L)) {
//	                MmkyPayAmount mmkyData = mmkyPayAmountRespository
//	                        .findByConsumerApplicationNumber(consumerApplicationDetail.getConsumerApplicationNo());
//
//	                if (mmkyData != null && totalZamaKiyaHuaPaisa.compareTo(mmkyData.getPayableAmount()) == 0) {
//	                    totalWapaskrneWalaPaisa = totalZamaKiyaHuaPaisa.subtract(BigDecimal.valueOf(2500));
//	                }
//
//	            } else {
//	                ErpEstimateAmountData estimateData = estimateAmountRepository
//	                        .findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//	                if (estimateData != null) {
//	                    BigDecimal cgst = estimateData.getCgst();
//	                    BigDecimal sgst = estimateData.getSgst();
//	                    BigDecimal totalTax = (cgst != null && sgst != null) ? cgst.add(sgst) : BigDecimal.ZERO;
//	                    refundableAmount = totalZamaKiyaHuaPaisa.subtract(totalTax);
//	                    totalWapaskrneWalaPaisa = refundableAmount;
//	                }
//	            }
//	            result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//
//	        } else {
//	            // For value != 1
//	            if (hasPoseData) {
//	                for (PoseMachinePostData pose : demandDataFromPoseMachine) {
//	                    String paymentType = pose.getPaymentType();
//	                    BigDecimal txnAmount = pose.getTxnAmount();
//	                    transactionDate = pose.getDateOfPayment().toString();
//
//	                    if ("Revised_Demand_fees".equals(paymentType)) {
//	                        ErpRev erpRev = erpRevRepository
//	                                .findByConsAppNo(consumerApplicationDetail.getConsumerApplicationNo());
//
//	                        if (erpRev != null && erpRev.getRemReturnAmt() != null) {
//	                            if (txnAmount.compareTo(erpRev.getPayAmt()) >= 0) {
//	                                totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpRev.getRemReturnAmt());
//	                                result.put("revisePaymentTransactionDate", transactionDate);
//	                                result.put("totalReviseZamaKiyaHuaPaisa", txnAmount);
//	                                result.put("reviseReturnAmount", erpRev.getRemReturnAmt());
//	                            } else {
//	                                throw new ConsumerApplicationDetailException(
//	                                        new Response(HttpCode.NOT_ACCEPTABLE,
//	                                                "Revise Payment paid less through sanchay Portal"));
//	                            }
//	                        }
//	                    } else {
//	                        ErpEstimateAmountData erpData = estimateAmountRepository
//	                                .findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//	                        if (erpData != null && erpData.getJeReturnAmount() != null) {
//	                            Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
//	                            BigDecimal compareAmount = (schemeId.equals(1L)) ? erpData.getTotalBalanceSupervisionAmount()
//	                                    : erpData.getTotalBalanceDepositAmount();
//
//	                            boolean isValidAmount = (schemeId.equals(1L) && txnAmount.compareTo(compareAmount) >= 0)
//	                                    || (schemeId.equals(2L) && txnAmount.compareTo(compareAmount) > 0);
//
//	                            if (isValidAmount) {
//	                                totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//	                                result.put("demandPaymentTransactionDate", transactionDate);
//	                                result.put("totalDemandZamaKiyaHuaPaisa", txnAmount);
//	                                result.put("demandReturnAmount", erpData.getJeReturnAmount());
//	                            } else {
//	                                throw new ConsumerApplicationDetailException(
//	                                        new Response(HttpCode.NOT_ACCEPTABLE,
//	                                                "Demand Payment paid less through sanchay Portal"));
//	                            }
//	                        }
//	                    }
//	                }
//	                result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//	            } else if (hasManualPayment) {
//	                ErpEstimateAmountData erpData = estimateAmountRepository
//	                        .findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//	                if (erpData != null && erpData.getJeReturnAmount() != null) {
//	                    Long schemeId = consumerApplicationDetail.getSchemeType().getSchemeTypeId();
//	                    BigDecimal manualAmount = new BigDecimal(demandDataFromManualPayment.getAmount());
//	                    BigDecimal compareAmount = (schemeId.equals(1L)) ? erpData.getTotalBalanceSupervisionAmount()
//	                            : erpData.getTotalBalanceDepositAmount();
//
//	                    boolean isValidAmount = (schemeId.equals(1L) && manualAmount.compareTo(compareAmount) >= 0)
//	                            || (schemeId.equals(2L) && manualAmount.compareTo(compareAmount) > 0);
//
//	                    if (isValidAmount) {
//	                        totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(erpData.getJeReturnAmount());
//	                        transactionDate = demandDataFromManualPayment.getPaymentDate();
//	                        result.put("demandPaymentTransactionDate", transactionDate);
//	                        result.put("totalDemandZamaKiyaHuaPaisa", manualAmount);
//	                        result.put("demandReturnAmount", erpData.getJeReturnAmount());
//	                        result.put("totalWapaskrneWalaPaisa", totalWapaskrneWalaPaisa);
//	                    } else {
//	                        throw new ConsumerApplicationDetailException(
//	                                new Response(HttpCode.NOT_ACCEPTABLE,
//	                                        "Demand Payment paid less through sanchay Portal"));
//	                    }
//	                }
//	            }
//	        }
//	    }
//
//	    return result;
//	}
	
	
//	line 1412 to 1709
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
	
}
