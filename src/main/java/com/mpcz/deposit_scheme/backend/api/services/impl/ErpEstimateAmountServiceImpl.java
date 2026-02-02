package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.OytProjectDetails;
import com.mpcz.deposit_scheme.backend.api.domain.PoseMachinePostData;
import com.mpcz.deposit_scheme.backend.api.domain.SchemeType;
import com.mpcz.deposit_scheme.backend.api.dto.ErpEstimateCalculatedDto;
import com.mpcz.deposit_scheme.backend.api.dto.SanchayPortalDto;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.OytProjectDetailsRepository;
import com.mpcz.deposit_scheme.backend.api.repository.PoseMachinePostDataRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ErpEstimateAmountService;
import com.mpcz.deposit_scheme.backend.api.services.SchemeTypeService;

//
@Service
public class ErpEstimateAmountServiceImpl implements ErpEstimateAmountService {

	Logger logger = LoggerFactory.getLogger(WorkTypeServiceImpl.class);

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ConsumerApplicationDetailService consumerApplicationDetailService;

	@Autowired
	private SchemeTypeService schemeTypeService;

	@Autowired
	private BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	private PoseMachinePostDataRepository poseMachinePostDataRepository;

	@Override
	public List<ErpEstimateAmountData> saveAllEstimateAmount(List<ErpEstimateAmountData> saveAllEstimateAmountList)
			throws ErpEstimateAmountException {

		// Auto-generated method stub

		final String method = "ErpEstimateServiceImpl : saveWorkType()";
		logger.info(method);

		Response<ErpEstimateAmountData> response = new Response<>();

		if (saveAllEstimateAmountList.isEmpty()) {
			logger.error("ErpEstimate object is null");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.NULL_OBJECT_MESSAGE);
			throw new ErpEstimateAmountException(response);
		} else {

			List<ErpEstimateAmountData> saveAll = estimateAmountRepository.saveAll(saveAllEstimateAmountList);

			if (Objects.isNull(saveAll)) {
				logger.error("repository.save(ErpEstimateAmount) is returning Null");
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage(ResponseMessage.RECORD_INSERTION_FAILED_MESSAGE);
				throw new ErpEstimateAmountException(response);
			} else {
				return saveAll;
			}

		}

	}

	@Override
	public ErpEstimateAmountData findByEstimateNumber(String estimateNumber) throws ErpEstimateAmountException {

		final String method = "ApplicationTypeServiceImpl : findByEstimateNumber(Long applicationTypeId)";

		logger.info(method);

		final Optional<ErpEstimateAmountData> ErpEstimateDomainOptional = estimateAmountRepository
				.findById(estimateNumber);

		final Response<ErpEstimateAmountData> response = new Response<>();

		if (!ErpEstimateDomainOptional.isPresent()) {
//			logger.error("applicationTypeRepository.findById is returning Null when findByApplicationTypeId call");
//			response.setCode(HttpCode.NULL_OBJECT);
//			response.setMessage(ResponseMessage.APPLICATION_TYPE_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
//			throw new ErpEstimateAmountException(response);
			return null;
		}
		ErpEstimateAmountData estimatekResponse = ErpEstimateDomainOptional.get();

		response.setList(Arrays.asList(estimatekResponse));
		logger.info("Response is returning successfully");
		response.setCode(HttpCode.OK);
		response.setMessage(ResponseMessage.RECORD_FETCH_BY_ID_MESSAGE);
		return estimatekResponse;

	}

	@Override
	public ErpEstimateCalculatedDto calculateEstimateAmount(Long consumerAppId) throws Exception {

		ErpEstimateCalculatedDto estimateAmountDto = new ErpEstimateCalculatedDto();
		ConsumerApplicationDetail consumerapplication = null;
		try {
			consumerapplication = consumerApplicationDetailService.findByConsumerApplicationId(consumerAppId);
		} catch (Exception e) {

			e.printStackTrace();
		}
		SchemeType schemeObject = schemeTypeService
				.findBySchemeTypeId(consumerapplication.getSchemeType().getSchemeTypeId());

		Optional<ErpEstimateAmountData> erpEstimateAmountDataOptional = estimateAmountRepository
				.findById(consumerapplication.getErpWorkFlowNumber());

		BigDecimal totalSupervisionAmount = null;
		BigDecimal totalDepositAmount = null;
		double loadRequestedDouble = 0.0;
		BigDecimal loadRequestedDecimal = null;
		BigDecimal kvaLoadAmount = null;
		BigDecimal kwloads = null;
		BigDecimal minusCost = BigDecimal.ZERO;

		ErpEstimateAmountData erpEstimateAmount = erpEstimateAmountDataOptional.get();

		BigDecimal supervisionAmount = roundAmountCgstAndSgst(erpEstimateAmount.getSupervisionAmount());

		BigDecimal superVisionDouble = roundAmountCgstAndSgst(erpEstimateAmount.getSupervisionAmount());

		BigDecimal estimateAmount = erpEstimateAmount.getEstimateAmount();

		BigDecimal estimateAmountDouble = roundAmountCgstAndSgst(erpEstimateAmount.getEstimateAmount());

		if (erpEstimateAmountDataOptional.get().getMinusCost() != null) {
			minusCost = roundAmountCgstAndSgst(
					erpEstimateAmountDataOptional.get().getMinusCost().multiply(new BigDecimal(-1)));
			if (Objects.equals(consumerapplication.getIsAvedakGovernmentErp(), "Yes")) {
				minusCost = BigDecimal.ZERO;
				erpEstimateAmount.setJeReturnAmount(BigDecimal.ZERO);
			} else {
				erpEstimateAmount.setJeReturnAmount(minusCost);
			}

		}

		List<BillDeskPaymentResponse> totalPaymentOfRegistration = billPaymentResponseeeeeeeRepository
				.getTotalPaymentOfRegistration(consumerapplication.getConsumerApplicationNo());

		List<PoseMachinePostData> registrationPaymentDetail = null;

		if (totalPaymentOfRegistration == null || totalPaymentOfRegistration.isEmpty()) {
			registrationPaymentDetail = poseMachinePostDataRepository
					.getRegistrationPaymentDetail(consumerapplication.getConsumerApplicationNo());
		}

		boolean billDeskEmpty = (totalPaymentOfRegistration == null || totalPaymentOfRegistration.isEmpty());

		boolean posEmpty = (registrationPaymentDetail == null || registrationPaymentDetail.isEmpty());
		String isAvedakGovernmentErp = null;
		if (billDeskEmpty && posEmpty) {
			isAvedakGovernmentErp = "Yes";
		} else {
			isAvedakGovernmentErp = "No";
		}
		System.err.println("isAvedakGovernmentErp : " + isAvedakGovernmentErp);

//		consumerapplication.setIsAvedakGovernmentErp(billPaymentResponseeeeeeeRepository
//				.getTotalPaymentOfRegistration(consumerapplication.getConsumerApplicationNo()).isEmpty() ? "Yes"
//						: "No");

		BigDecimal cgst = roundAmountCgstAndSgst(superVisionDouble.multiply(new BigDecimal(.09)));
		BigDecimal sgst = roundAmountCgstAndSgst(superVisionDouble.multiply(new BigDecimal(.09)));

		estimateAmountDto.setSgst(sgst);
		estimateAmountDto.setCgst(cgst);

		erpEstimateAmount.setCgst(cgst);
		erpEstimateAmount.setSgst(sgst);

		BigDecimal supervisionbalanceRemain = superVisionDouble;
		BigDecimal supervisionBalanceRemaining = supervisionbalanceRemain;

		estimateAmountDto.setSuperVisionAmount(superVisionDouble);

		erpEstimateAmount.setSupervisionAmount(supervisionAmount);
		erpEstimateAmount.setEstimateAmount(roundAmountCgstAndSgst(estimateAmount));

		if (consumerapplication.getJeLoad() != null) {

			loadRequestedDouble = Double.valueOf(consumerapplication.getJeLoad());
			loadRequestedDecimal = new BigDecimal(consumerapplication.getJeLoad());

		}

		if (schemeObject.getSchemeTypeName().equals("Supervision")
				&& erpEstimateAmountDataOptional.get().getSchemeCode().contains("SCCW")) {

			if (erpEstimateAmountDataOptional != null) {

				if (erpEstimateAmount.getSchema() != null
						&& erpEstimateAmount.getSchema().toUpperCase().contains("SCCW")) {

//					BigDecimal jeReturnAmount = null;
//
//					if (consumerapplication.getJeReturnAmount() == null) {
//						jeReturnAmount = new BigDecimal(0.0);
//					} else {
//						jeReturnAmount = round111(consumerapplication.getJeReturnAmount(), 0);
//					}

					erpEstimateAmount.setJeReturnAmount(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);

//					Colony Electrification(Illegal)
					if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 4) {

						String loadRequstedUnit = consumerapplication.getJeLoad();
						loadRequestedDecimal = new BigDecimal(loadRequstedUnit);
//						400 matlb 400 tak hai ( yes aya hai front end se)

						double number = Double.parseDouble(loadRequstedUnit);

//						indiviual dalne pr hamesa load unit KW hi ayega 
						if (consumerapplication.getColonyIllegalSelectionType() != null
								&& consumerapplication.getColonyIllegalSelectionType().equals("2")
								&& consumerapplication.getIndividualOrGroup() != null
								&& consumerapplication.getIndividualOrGroup().getIndividualOrGroupId() == 1) {

							if (consumerapplication.getJeLoad() != null && number <= 400) {
								estimateAmountDto.setSgst(new BigDecimal(0.0));
								estimateAmountDto.setCgst(new BigDecimal(0.0));

								erpEstimateAmount.setCgst(new BigDecimal(0.0));
								erpEstimateAmount.setSgst(new BigDecimal(0.0));
								BigDecimal round = null;
//							charitra code start 

//								kw ke case me ye chalega
								round = roundAmountCgstAndSgst(new BigDecimal(number).multiply(new BigDecimal(15567)));

//					charitra end code
//							billdesk walo ko yahi amount beja ja raha hai
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

								erpEstimateAmount.setColonyOrSlum(round);
								estimateAmountDto.setColonyOrSlum(round);

								estimateAmountDto.setSuperVisionAmount(new BigDecimal(0.0));

							} else {

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								BigDecimal round = totalSupervisionAmount;
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

							}

						} else {

							if (consumerapplication.getJeLoadUnitKwYaKva().equals("KW")) {
								System.err.println("number * 1.25 : " + number * 1.25);
								number = upperRound(number * 1.25, 0);
								System.err.println("number : " + number);
							}

							if (consumerapplication.getJeLoad() != null && number <= 1500) {

								BigDecimal round = null;
//							charitra code start 

//								KVA ke case me ye chalega
								round = roundAmountCgstAndSgst(new BigDecimal(number).multiply(new BigDecimal(850)));

//					charitra end code
//							billdesk walo ko yahi amount beja ja raha hai
//								15-04-2025  ye niche ki line comment ki gai hai ,or niche 2 ling bada di gai hai								
//								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
//								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

//								billdesk walo ko yahi amount beja ja raha hai
								erpEstimateAmount.setTotalBalanceSupervisionAmount(supervisionBalanceRemaining.add(cgst)
										.add(sgst).add(round11(round, 2).add(minusCost)));
								estimateAmountDto.setTotalamountOfSupervision(supervisionBalanceRemaining.add(cgst)
										.add(sgst).add(round11(round, 2).add(minusCost)));

								erpEstimateAmount.setKvaLoad(round);
								estimateAmountDto.setKvaLoadAmount(round);
							} else {

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								BigDecimal round = totalSupervisionAmount;
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

							}

						}

//                  NSC 
					} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2) {

						Function<BigDecimal, BigDecimal> safeRound = val -> roundAmountCgstAndSgst(
								Objects.isNull(val) ? BigDecimal.ZERO : val);

						if (Objects.isNull(consumerapplication.getSspTotalAmount())) {
							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
							BigDecimal round = roundAmountCgstAndSgst(totalSupervisionAmount);
							erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
							estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

							erpEstimateAmount.setJeReturnAmount(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							// added this code for taking the amount charges of SSP portal in DSP for NOW 2
							// 11-09-2025 start
							BigDecimal sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());
							BigDecimal sspRegistrationCharge = safeRound
									.apply(consumerapplication.getSspRegistrationCharge());
							BigDecimal sspMeterCost = safeRound.apply(consumerapplication.getSspMeterCost());
							BigDecimal sspSecurityDeposit = safeRound
									.apply(consumerapplication.getSspSecurityDeposit());
							BigDecimal sspSupplyAffordingCharges = safeRound
									.apply(consumerapplication.getSspSupplyAffordingCharges());

							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
							BigDecimal round = roundAmountCgstAndSgst(totalSupervisionAmount);

							erpEstimateAmount.setTotalBalanceSupervisionAmount(
									round11(round, 2).add(minusCost).add(sspTotalAmount));
							estimateAmountDto
									.setTotalamountOfSupervision(round11(round, 2).add(minusCost).add(sspTotalAmount));

							erpEstimateAmount.setSspTotalAmount(sspTotalAmount);
							estimateAmountDto.setSspTotalAmount(sspTotalAmount);

							erpEstimateAmount.setSspRegistrationCharge(sspRegistrationCharge);
							estimateAmountDto.setSspRegistrationCharge(sspRegistrationCharge);

							erpEstimateAmount.setSspMeterCost(sspMeterCost);
							estimateAmountDto.setSspMeterCost(sspMeterCost);

							erpEstimateAmount.setSecurityDeposit(sspSecurityDeposit);
							estimateAmountDto.setSecurityDeposit(sspSecurityDeposit);

							erpEstimateAmount.setKvaLoad(sspSupplyAffordingCharges);
							estimateAmountDto.setKvaLoadAmount(sspSupplyAffordingCharges);

							erpEstimateAmount.setJeReturnAmount(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
//							end
						}

					}

//						Colony Electrification(Legal)
					else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 3) {
						double i1 = Double.parseDouble(consumerapplication.getJeLoad());
						BigDecimal bigDecimal = new BigDecimal(consumerapplication.getJeLoad());

//						kva ke liye code
						if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
							if (i1 < 1500) {
								kvaLoadAmount = bigDecimal.multiply(new BigDecimal(850));

								BigDecimal roundKvaLoad = round111(kvaLoadAmount, 0);

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
										.add(roundKvaLoad);
								BigDecimal round = round11(totalSupervisionAmount, 2);

								erpEstimateAmount.setKvaLoad(roundKvaLoad);
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round.add(minusCost));

								estimateAmountDto.setTotalamountOfSupervision(round.add(minusCost));

								estimateAmountDto.setKvaLoadAmount(roundKvaLoad);

							} else {
								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								estimateAmountDto
										.setTotalamountOfSupervision(round11(totalSupervisionAmount, 2).add(minusCost));
								erpEstimateAmount.setTotalBalanceSupervisionAmount(
										round11(totalSupervisionAmount, 2).add(minusCost));
							}

						}

					} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(9l)) {

						totalSupervisionAmount = roundAmountCgstAndSgst(
								supervisionBalanceRemaining.add(cgst).add(sgst));

						BigDecimal loadAmount = BigDecimal.ZERO;
						if (loadRequestedDouble <= 3) {
							loadAmount = loadRequestedDecimal.multiply(new BigDecimal(50));
						} else if (loadRequestedDouble <= 10) {
							loadAmount = new BigDecimal(150).add(
									(loadRequestedDecimal.subtract(new BigDecimal(3))).multiply(new BigDecimal(150)));
						} else if (loadRequestedDouble <= 25) {
							loadAmount = new BigDecimal(1200).add(
									(loadRequestedDecimal.subtract(new BigDecimal(10))).multiply(new BigDecimal(380)));

						} else if (loadRequestedDouble > 25) {
							loadAmount = new BigDecimal(6900).add(
									(loadRequestedDecimal.subtract(new BigDecimal(25))).multiply(new BigDecimal(630)));
						}

						estimateAmountDto.setKvaLoadAmount(loadAmount);
						erpEstimateAmount.setKvaLoad(loadAmount);

						estimateAmountDto.setTotalamountOfSupervision(
								roundAmountCgstAndSgst(totalSupervisionAmount.add(loadAmount)));
						erpEstimateAmount.setTotalBalanceSupervisionAmount(
								roundAmountCgstAndSgst(totalSupervisionAmount.add(loadAmount)));

					}

					// Nature of work id 1,6 and 7 , 11 k liye  
					// charitra start code 16-12-2025	nature of work 13 , 14 
					else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(1l)
							|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(6l)
							|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(7l)
							|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(11l)
							|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(13l)
							|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(14l)) {
//						BigDecimal jeReturnAmt = round111(consumerapplication.getJeReturnAmount(), 0);
						BigDecimal jeReturnAmt = null;
						if (consumerapplication.getJeReturnAmount() != null) {
							jeReturnAmt = round111(consumerapplication.getJeReturnAmount(), 0);
						}
						if (jeReturnAmt != null) {
							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
									.add(consumerapplication.getJeReturnAmount());

							estimateAmountDto.setJeReturnAmount(jeReturnAmt);
							erpEstimateAmount.setJeReturnAmount(jeReturnAmt);

							estimateAmountDto
									.setTotalamountOfSupervision(roundAmountCgstAndSgst(totalSupervisionAmount));
							erpEstimateAmount
									.setTotalBalanceSupervisionAmount(roundAmountCgstAndSgst(totalSupervisionAmount));
						}

						else {
							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
							estimateAmountDto.setTotalamountOfSupervision(round11(totalSupervisionAmount, 2));
							erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(totalSupervisionAmount, 2));

						}
					} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 10) {
						double i1 = Double.parseDouble(consumerapplication.getJeLoad());
						BigDecimal bigDecimal = new BigDecimal(consumerapplication.getJeLoad());

//						kva ke liye code
						if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
//							yhan 25000 KVA use kiya hai kyuki officially hamare pass iska koi documented data nahi hai 
							if (i1 < 25000) {
								kvaLoadAmount = bigDecimal.multiply(new BigDecimal(1260));

								BigDecimal roundKvaLoad = round111(kvaLoadAmount, 0);

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
										.add(roundKvaLoad);
								BigDecimal round = round11(totalSupervisionAmount, 2);

								erpEstimateAmount.setKvaLoad(roundKvaLoad);
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round.add(minusCost));

								estimateAmountDto.setTotalamountOfSupervision(round.add(minusCost));

								estimateAmountDto.setKvaLoadAmount(roundKvaLoad);
							} else {
								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								estimateAmountDto
										.setTotalamountOfSupervision(round11(totalSupervisionAmount, 2).add(minusCost));
								erpEstimateAmount.setTotalBalanceSupervisionAmount(
										round11(totalSupervisionAmount, 2).add(minusCost));
							}

						}
					}
// code start 02-12-2025 Monika Rajpoot
					else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 12L) {

						BigDecimal jeReturnAmt = consumerapplication.getJeReturnAmount() != null
								? round111(consumerapplication.getJeReturnAmount(), 0)
								: BigDecimal.ZERO;

						totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
								.add(consumerapplication.getJeReturnAmount()).add(new BigDecimal(1180));

						estimateAmountDto.setJeReturnAmount(jeReturnAmt);
						erpEstimateAmount.setJeReturnAmount(jeReturnAmt);

						estimateAmountDto.setTotalamountOfSupervision(roundAmountCgstAndSgst(totalSupervisionAmount));
						erpEstimateAmount
								.setTotalBalanceSupervisionAmount(roundAmountCgstAndSgst(totalSupervisionAmount));

						erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
						estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));
						isAvedakGovernmentErp = "No";
					}
					
				

					

					// code end 02-12-2025
				}
			}
			erpEstimateAmount.setU_sec_194J_tds2(null);
			erpEstimateAmount.setU_sec_194C_tds2(null);
			erpEstimateAmount.setU_194C_tds2_fSupDep(null);
			System.err.println("aaaaaaaaaaaaaaaaaa : " + isAvedakGovernmentErp);
			if ("Yes".equals(isAvedakGovernmentErp)) {
				estimateAmountDto.setTotalamountOfSupervision(
						estimateAmountDto.getTotalamountOfSupervision().add(new BigDecimal(1180)));
				erpEstimateAmount.setTotalBalanceSupervisionAmount(
						erpEstimateAmount.getTotalBalanceSupervisionAmount().add(new BigDecimal(1180)));
				erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
				estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));

			}
			estimateAmountRepository.save(erpEstimateAmount);
			return estimateAmountDto;

		} else if (schemeObject.getSchemeTypeName().equals("Deposit")
				&& erpEstimateAmountDataOptional.get().getSchemeCode().equalsIgnoreCase("DEPOSITE"))

		{

			minusCost = roundAmountCgstAndSgst(
					erpEstimateAmountDataOptional.get().getMinusCost().multiply(new BigDecimal(-1)));
			if (Objects.equals(consumerapplication.getIsAvedakGovernmentErp(), "Yes") && consumerapplication.getGoodMaterialOrnot().equals("0")) {
			
				erpEstimateAmount.setJeReturnAmount(minusCost);
			} else if(Objects.equals(consumerapplication.getIsAvedakGovernmentErp(), "No")) {
				erpEstimateAmount.setJeReturnAmount(minusCost);
			}

		
			
			BigDecimal supervisionAmountDouble = roundAmountCgstAndSgst(erpEstimateAmount.getSupervisionAmount());

			BigDecimal depositAmount = BigDecimal.ZERO;

			depositAmount = estimateAmountDouble.subtract(supervisionAmountDouble).subtract(cgst).subtract(sgst);

			BigDecimal round2 = roundAmountCgstAndSgst(depositAmount);

			BigDecimal totalEstimateAmount = roundAmountCgstAndSgst(
					supervisionAmountDouble.add(round2).add(cgst).add(sgst));

//			estimateAmountDto.setMinusCost(minusCost);
			estimateAmountDto.setDepositAmount(round2);
			estimateAmountDto.setSuperVisionAmount(supervisionAmountDouble);

			erpEstimateAmount.setDepositAmount(round2);

			if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 1
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 6
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 7
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 11
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 13
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 14
					) {

				// added this code for taking the amount charges of SSP portal in DSP for NOW 2
				// 11-09-2025 start
				Function<BigDecimal, BigDecimal> safeRound = val -> roundAmountCgstAndSgst(
						Objects.isNull(val) ? BigDecimal.ZERO : val);
				BigDecimal sspTotalAmount;

				if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2
						&& Objects.nonNull(consumerapplication.getSspTotalAmount())) {

					sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());
					BigDecimal sspRegistrationCharge = safeRound.apply(consumerapplication.getSspRegistrationCharge());
					BigDecimal sspMeterCost = safeRound.apply(consumerapplication.getSspMeterCost());
					BigDecimal sspSecurityDeposit = safeRound.apply(consumerapplication.getSspSecurityDeposit());
					BigDecimal sspSupplyAffordingCharges = safeRound
							.apply(consumerapplication.getSspSupplyAffordingCharges());

					erpEstimateAmount.setSspTotalAmount(sspTotalAmount);
					estimateAmountDto.setSspTotalAmount(sspTotalAmount);

					erpEstimateAmount.setSspRegistrationCharge(sspRegistrationCharge);
					estimateAmountDto.setSspRegistrationCharge(sspRegistrationCharge);

					erpEstimateAmount.setSspMeterCost(sspMeterCost);
					estimateAmountDto.setSspMeterCost(sspMeterCost);

					erpEstimateAmount.setSecurityDeposit(sspSecurityDeposit);
					estimateAmountDto.setSecurityDeposit(sspSecurityDeposit);

					erpEstimateAmount.setKvaLoad(sspSupplyAffordingCharges);
					estimateAmountDto.setKvaLoadAmount(sspSupplyAffordingCharges);
				}
				sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());

//				end
				if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount.add(minusCost));
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setMinusCost(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);
				} else {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount);
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setMinusCost(null);
					estimateAmountDto.setJeReturnAmount(null);

				}
			}
//			else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2) {
//
//
//				BigDecimal returnEstimate = roundAmountCgstAndSgst(totalEstimateAmount.add(minusCost));
//
//				erpEstimateAmount.setTotalBalanceDepositAmount(returnEstimate);
//				erpEstimateAmount.setSgst(sgst);
//				erpEstimateAmount.setCgst(cgst);
//				estimateAmountDto.setTotaldepositAmount(returnEstimate);
//
//				erpEstimateAmount.setJeReturnAmount(jeReturnAmount);
//				estimateAmountDto.setJeReturnAmount(jeReturnAmount);
//
////				File No. 382469 according this in the given application we don't need to take minus cost added 11-Dec-2024
//				if ((consumerapplication.getConsumerApplicationNo().equals("DS1725527898869"))
//						&& consumerapplication.getErpWorkFlowNumber().equals("137010")) {
//					estimateAmountDto.setDepositAmount(round2.add(minusCost));
//					erpEstimateAmount.setDepositAmount(round2.add(minusCost));
//
//					erpEstimateAmount.setTotalBalanceDepositAmount(estimateAmountDouble);
//					estimateAmountDto.setTotaldepositAmount(estimateAmountDouble);
//				}
//			}

//			Colony Electrification(Legal)

			else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 3) {

				double i1 = Double.parseDouble(consumerapplication.getJeLoad());
				BigDecimal jeLoad = round111(new BigDecimal(consumerapplication.getJeLoad()), 0);

				if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
					if (i1 < 1500) {
						kvaLoadAmount = roundAmountCgstAndSgst(jeLoad.multiply(new BigDecimal(850)));

						totalDepositAmount = totalEstimateAmount.add(kvaLoadAmount);

						erpEstimateAmount.setKvaLoad(kvaLoadAmount);
						estimateAmountDto.setKvaLoadAmount(kvaLoadAmount);

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					} else {
						totalDepositAmount = totalEstimateAmount;
						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					}

				}

			} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 4) {
				double load = Double.valueOf(consumerapplication.getJeLoad());
				loadRequestedDecimal = round111(new BigDecimal(load), 0);

				if (consumerapplication.getColonyIllegalSelectionType() != null
						&& consumerapplication.getColonyIllegalSelectionType().equals("2")
						&& consumerapplication.getIndividualOrGroup() != null
						&& consumerapplication.getIndividualOrGroup().getIndividualOrGroupId() == 1) {

					if (load <= 400) {

						kwloads = roundAmountCgstAndSgst(loadRequestedDecimal.multiply(new BigDecimal(15567)));

						erpEstimateAmount.setColonyOrSlum(kwloads);
						erpEstimateAmount.setSgst(null);
						erpEstimateAmount.setCgst(null);

						estimateAmountDto.setColonyOrSlum(kwloads);
						estimateAmountDto.setCgst(null);
						estimateAmountDto.setSgst(null);
						estimateAmountDto.setSuperVisionAmount(null);
						estimateAmountDto.setDepositAmount(null);

						totalDepositAmount = kwloads;

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							erpEstimateAmount.setTotalBalanceDepositAmount(kwloads.add(minusCost));
							estimateAmountDto.setTotaldepositAmount(kwloads.add(minusCost));
							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							erpEstimateAmount.setTotalBalanceDepositAmount(kwloads);
							estimateAmountDto.setTotaldepositAmount(kwloads);
							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					} else {
//						In the case of illigal colony application, load greater than 400 KW is not Acceptable
//						yaha code kabhi ayega nai, kyuki controler se hi return ho gya hai
//						totalDepositAmount = totalEstimateAmount;
//						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
//							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));
//							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
//							estimateAmountDto.setMinusCost(minusCost);
//						} else {
//							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);
//							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
//							estimateAmountDto.setMinusCost(null);
//							estimateAmountDto.setJeReturnAmount(null);
//						}
					}

				} else {
					if (consumerapplication.getJeLoadUnitKwYaKva().equals("KW")) {
						load = upperRound(load * 1.25, 0);
					}

					if (consumerapplication.getJeLoad() != null && load <= 1500) {

						BigDecimal round = null;

						round = roundAmountCgstAndSgst(round111(new BigDecimal(load), 0).multiply(new BigDecimal(850)));

						erpEstimateAmount.setKvaLoad(round);
						estimateAmountDto.setKvaLoadAmount(round);

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {

							erpEstimateAmount
									.setTotalBalanceDepositAmount(totalEstimateAmount.add(round).add(minusCost));
							estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(round).add(minusCost));
							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(round));
							estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(round));
							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}

					} else {

						totalDepositAmount = estimateAmountDouble;

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							erpEstimateAmount.setTotalBalanceSupervisionAmount(totalDepositAmount.add(minusCost));
							estimateAmountDto.setTotalamountOfSupervision(totalDepositAmount.add(minusCost));
							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							erpEstimateAmount.setTotalBalanceSupervisionAmount(totalDepositAmount);
							estimateAmountDto.setTotalamountOfSupervision(totalDepositAmount);
							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					}

				}

			}

			else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(9l)) {

				BigDecimal loadAmount = BigDecimal.ZERO;
				if (loadRequestedDouble <= 3) {
					loadAmount = loadRequestedDecimal.multiply(new BigDecimal(50));
				} else if (loadRequestedDouble <= 10) {
					loadAmount = new BigDecimal(150)
							.add((loadRequestedDecimal.subtract(new BigDecimal(3))).multiply(new BigDecimal(150)));
				} else if (loadRequestedDouble <= 25) {
					loadAmount = new BigDecimal(1200)
							.add((loadRequestedDecimal.subtract(new BigDecimal(10))).multiply(new BigDecimal(380)));

				} else if (loadRequestedDouble > 25) {
					loadAmount = new BigDecimal(6900)
							.add((loadRequestedDecimal.subtract(new BigDecimal(25))).multiply(new BigDecimal(630)));
				}

				estimateAmountDto.setKvaLoadAmount(loadAmount);
				erpEstimateAmount.setKvaLoad(loadAmount);

				erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(loadAmount));
				estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(loadAmount));

				if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
					erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(loadAmount).add(minusCost));
					estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(loadAmount).add(minusCost));
					estimateAmountDto.setMinusCost(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);
				} else {
					erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(loadAmount));
					estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(loadAmount));
					estimateAmountDto.setMinusCost(null);
					estimateAmountDto.setJeReturnAmount(null);
				}
			} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 10) {

				double i1 = Double.parseDouble(consumerapplication.getJeLoad());
				BigDecimal jeLoad = round111(new BigDecimal(consumerapplication.getJeLoad()), 0);

				if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
//					yhan 25000 KVA use kiya hai kyuki officially hamare pass iska koi documented data nahi hai 
					if (i1 < 25000) {
						kvaLoadAmount = roundAmountCgstAndSgst(jeLoad.multiply(new BigDecimal(1260)));

						totalDepositAmount = totalEstimateAmount.add(kvaLoadAmount);

						erpEstimateAmount.setKvaLoad(kvaLoadAmount);
						estimateAmountDto.setKvaLoadAmount(kvaLoadAmount);

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					} else {
						totalDepositAmount = totalEstimateAmount;
						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					}

				}

			}
			// code start 02-12-2025 Monika Rajpoot
			if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 12L) {
				if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount.add(minusCost));
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(new BigDecimal(1180)));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount.add(new BigDecimal(1180)));
					estimateAmountDto.setMinusCost(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);
					erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
					estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));
				} else {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount);
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(new BigDecimal(1180)));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount.add(new BigDecimal(1180)));
					estimateAmountDto.setMinusCost(null);
					estimateAmountDto.setJeReturnAmount(null);
					erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
					estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));

				}
				isAvedakGovernmentErp = "No";
			}
			
			// charitra start code 16-12-2025					
		
			
//			end
			erpEstimateAmount.setU_sec_194J_tds2(null);
			erpEstimateAmount.setU_sec_194C_tds2(null);
			erpEstimateAmount.setU_194C_tds2_fSupDep(null);
			if ("Yes".equals(isAvedakGovernmentErp)) {
				erpEstimateAmount.setTotalBalanceDepositAmount(
						erpEstimateAmount.getTotalBalanceDepositAmount().add(new BigDecimal(1180)));
				estimateAmountDto
						.setTotaldepositAmount(estimateAmountDto.getTotaldepositAmount().add(new BigDecimal(1180)));
				erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
				estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));

			}
			estimateAmountRepository.save(erpEstimateAmount);
			return estimateAmountDto;

		} else if (schemeObject.getSchemeTypeName().equals("Departmental (MPMKVVCL)")) {

			BigDecimal oytAmount = estimateAmountDouble.add(cgst).add(sgst);
			BigDecimal parikshan = oytAmount.multiply(new BigDecimal(.03));
			BigDecimal parikshanCgst = parikshan.multiply(new BigDecimal(.09));
			BigDecimal parikshanMpgst = parikshan.multiply(new BigDecimal(.09));
			BigDecimal finalOyt = parikshan.add(parikshanMpgst).add(parikshanMpgst);
			BigDecimal finalOytDouble = finalOyt;
			BigDecimal finalparikshanCgst = parikshanCgst;
			BigDecimal parikshanAmount = parikshan;

			erpEstimateAmount.setTotalOytAmount(finalOyt);
			erpEstimateAmount.setCgst(parikshanCgst);
			erpEstimateAmount.setSgst(parikshanMpgst);
			erpEstimateAmount.setPrayakshanShulk(parikshan);
			estimateAmountDto.setParikshanCgst(finalparikshanCgst);
			estimateAmountDto.setPrayakshanShulk(parikshanAmount);
			estimateAmountDto.setFinalOyt(finalOytDouble);
			erpEstimateAmount.setU_sec_194J_tds2(null);
			erpEstimateAmount.setU_sec_194C_tds2(null);
			erpEstimateAmount.setU_194C_tds2_fSupDep(null);
			estimateAmountRepository.save(erpEstimateAmount);
			return estimateAmountDto;
		} else if (schemeObject.getSchemeTypeName().equals("Supervision")
				&& erpEstimateAmountDataOptional.get().getSchemeCode().contains("OYT")) {

			BigDecimal jeLoadAmount = BigDecimal.ZERO;
			// oyt ke case me je load 1.1 hone pr bhi 2 hona chaiye
			// ye 22-01-2025 ko bola gya hai
			// isme 3 field badai hai = 2495 rs , + 5 Rs , + jeload*600 RS

			if (erpEstimateAmountDataOptional != null) {

				estimateAmountDto = new ErpEstimateCalculatedDto();

				if (erpEstimateAmount.getSchema() != null
						&& erpEstimateAmount.getSchema().toUpperCase().contains("OYT")) {

					if (Objects.isNull(consumerapplication.getSspTotalAmount())
							|| (consumerapplication.getSspTotalAmount()).compareTo(BigDecimal.ZERO) <= 0) {
//			code start		7 - April -2025 if je load is less than or equal to 10 than 200 per hp and above 10 so 400 pr hp 
						if (consumerapplication.getJeLoad() == null) {
							consumerapplication.setJeLoad(String.valueOf(BigDecimal.ZERO));
						}
						double jeLoad = Double.parseDouble(consumerapplication.getJeLoad());
						if (jeLoad <= 10) {
							jeLoadAmount = new BigDecimal(
									upperRound(Double.parseDouble(consumerapplication.getJeLoad()), 0))
									.multiply(new BigDecimal(200));
						} else {
							jeLoadAmount = new BigDecimal(
									upperRound(Double.parseDouble(consumerapplication.getJeLoad()), 0))
									.multiply(new BigDecimal(400));
						}

						System.err.println("aaaaaaaaaa : " + jeLoadAmount);
//					code end 07-04-2025
						estimateAmountDto.setSgst(sgst);
						estimateAmountDto.setCgst(cgst);

						erpEstimateAmount.setCgst(cgst);
						erpEstimateAmount.setSgst(sgst);
						BigDecimal round = superVisionDouble.add(cgst).add(sgst);

						estimateAmountDto.setSuperVisionAmount(supervisionAmount);

//					estimateAmountDto.setAvedanShulk(new BigDecimal(2495));
						estimateAmountDto.setAvedanShulkFiveRupee(new BigDecimal(5));
						estimateAmountDto.setSecurityDeposit(roundAmountCgstAndSgst(jeLoadAmount));

						erpEstimateAmount.setU_sec_194J_tds2(null);
						erpEstimateAmount.setU_sec_194C_tds2(null);
						erpEstimateAmount.setU_194C_tds2_fSupDep(null);
//					erpEstimateAmount.setAvedanShulk(new BigDecimal(2495));
						erpEstimateAmount.setAvedanShulkFiveRupee(new BigDecimal(5));
						erpEstimateAmount.setSecurityDeposit(roundAmountCgstAndSgst(jeLoadAmount));

						BigDecimal total_amount = new BigDecimal(0.0);
						Map<String, BigDecimal> getoytMaterialforGetAmount = getoytMaterialforGetAmount(
								consumerapplication.getConsumerApplicationNo());

						if ((getoytMaterialforGetAmount.get("total_amount").compareTo(new BigDecimal(0))) <= 0) {
							total_amount = new BigDecimal(0.0);
						} else {
							total_amount = getoytMaterialforGetAmount.get("total_amount");
						}

						erpEstimateAmount.setOytMaterialTotalCostWithCsgstAndSgst(total_amount);
						estimateAmountDto.setOytMaterialtotalcostwithCgstAndSgst(total_amount);

						erpEstimateAmount.setTotalBalanceSupervisionAmount(
								round11(round, 2).add(new BigDecimal(5)).add(jeLoadAmount).add(total_amount));
						estimateAmountDto.setTotalamountOfSupervision(
								round11(round, 2).add(new BigDecimal(5)).add(jeLoadAmount).add(total_amount));

						estimateAmountRepository.save(erpEstimateAmount);
						return estimateAmountDto;
					} else {
// Condition for those new OYT application whose total amount is coming from SSP Portal as sspTotalAmount so now we will take supervision+cgst+sgst+sspTotalAmount
// 25-09-2025 code written by Monika Rajpoot  
						estimateAmountDto.setSgst(sgst);
						estimateAmountDto.setCgst(cgst);

						erpEstimateAmount.setCgst(cgst);
						erpEstimateAmount.setSgst(sgst);
						BigDecimal round = superVisionDouble.add(cgst).add(sgst);

						estimateAmountDto.setSuperVisionAmount(supervisionAmount);

						estimateAmountDto
								.setSspMeterCost(roundAmountCgstAndSgst(consumerapplication.getSspMeterCost()));
						estimateAmountDto.setKvaLoadAmount(
								roundAmountCgstAndSgst(consumerapplication.getSspSupplyAffordingCharges()));
						estimateAmountDto.setSspRegistrationCharge(
								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
//						estimateAmountDto.setAvedanShulkFiveRupee(
//								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
						estimateAmountDto.setSecurityDeposit(
								roundAmountCgstAndSgst(consumerapplication.getSspSecurityDeposit()));
						estimateAmountDto
								.setSspTotalAmount(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()));

						erpEstimateAmount.setU_sec_194J_tds2(null);
						erpEstimateAmount.setU_sec_194C_tds2(null);
						erpEstimateAmount.setU_194C_tds2_fSupDep(null);

						erpEstimateAmount
								.setSspMeterCost(roundAmountCgstAndSgst(consumerapplication.getSspMeterCost()));
						erpEstimateAmount
								.setKvaLoad(roundAmountCgstAndSgst(consumerapplication.getSspSupplyAffordingCharges()));
						erpEstimateAmount.setSspRegistrationCharge(
								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
//						erpEstimateAmount.setAvedanShulkFiveRupee(
//								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
						erpEstimateAmount.setSecurityDeposit(
								roundAmountCgstAndSgst(consumerapplication.getSspSecurityDeposit()));
						erpEstimateAmount
								.setSspTotalAmount(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()));

						BigDecimal total_amount = new BigDecimal(0.0);
						Map<String, BigDecimal> getoytMaterialforGetAmount = getoytMaterialforGetAmount(
								consumerapplication.getConsumerApplicationNo());

						if ((getoytMaterialforGetAmount.get("total_amount").compareTo(new BigDecimal(0))) <= 0) {
							total_amount = new BigDecimal(0.0);
						} else {
							total_amount = getoytMaterialforGetAmount.get("total_amount");
						}

						erpEstimateAmount.setOytMaterialTotalCostWithCsgstAndSgst(total_amount);
						estimateAmountDto.setOytMaterialtotalcostwithCgstAndSgst(total_amount);

						erpEstimateAmount.setTotalBalanceSupervisionAmount(
								round11(round, 2).add(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()))
										.add(total_amount));
						estimateAmountDto.setTotalamountOfSupervision(
								round11(round, 2).add(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()))
										.add(total_amount));

						estimateAmountRepository.save(erpEstimateAmount);
						return estimateAmountDto;
					}
// 25-09-2025 code written by Monika Rajpoot  end
				} else {
//		throw new Exception("Estimate Scheme not Matched");

				}

			}
		}

		return null;

	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(2, RoundingMode.CEILING);
		return bd.doubleValue();
	}

//	oyt ke case me je load 1.1 hone pr bhi 2 hona chaiye  code added on 14-Feb-2025
	public static double upperRound(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		double scale = Math.pow(10, places);
		return Math.ceil(value);
	}

//
//	public static double round1(double value, int places) {
//		if (places < 0)
//			throw new IllegalArgumentException();																																																																																																																																																																																																																																																																																																																																																										
//		BigDecimal bd = BigDecimal.valueOf(value);
//		bd = bd.setScale(0, RoundingMode.CEILING);
//		return bd.doubleValue();
//	}
//
	public static BigDecimal round0(BigDecimal value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = value;
		bd = bd.setScale(2, RoundingMode.CEILING);
		return bd;
	}

//
	public static BigDecimal round11(BigDecimal value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = value;
		bd = bd.setScale(2, RoundingMode.CEILING);
		return bd;
	}

	public static BigDecimal round111(BigDecimal value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = value;
		bd = bd.setScale(places, RoundingMode.CEILING);
		return bd;
	}

//	public static BigDecimal roundAmountCgstAndSgst(BigDecimal amount) {
//		BigDecimal roundedAmount = amount.setScale(0, RoundingMode.FLOOR); // Get the integer part
//
//		BigDecimal remainingPaise = amount.subtract(roundedAmount); // Get the decimal part (paise)
//
//		if (remainingPaise.compareTo(new BigDecimal(0.5)) >= 0) {
//			roundedAmount = roundedAmount.add(BigDecimal.ONE); // Round up if paise is 50 or more
//		}
//		return roundedAmount;
//	}

	public static BigDecimal roundAmountCgstAndSgst(BigDecimal amount) {
		// Round the amount to 2 decimal places to avoid precision issues
		BigDecimal preciseAmount = amount.setScale(2, RoundingMode.HALF_UP);

		// Get the integer part
		BigDecimal roundedAmount = preciseAmount.setScale(0, RoundingMode.FLOOR);

		// Get the fractional part (paise)
		BigDecimal remainingPaise = preciseAmount.subtract(roundedAmount);

		// Round up if paise is 0.5 or more
		if (remainingPaise.compareTo(new BigDecimal("0.5")) >= 0) {
			roundedAmount = roundedAmount.add(BigDecimal.ONE);
		}

		return roundedAmount;
	}

	@Override
	public ErpEstimateCalculatedDto calculateEstimateAmount1(Long consumerAppId) throws Exception {

		ErpEstimateCalculatedDto estimateAmountDto = new ErpEstimateCalculatedDto();
		ConsumerApplicationDetail consumerapplication = null;
		try {
			consumerapplication = consumerApplicationDetailService.findByConsumerApplicationId(consumerAppId);
		} catch (Exception e) {

			e.printStackTrace();
		}
		SchemeType schemeObject = schemeTypeService
				.findBySchemeTypeId(consumerapplication.getSchemeType().getSchemeTypeId());

		Optional<ErpEstimateAmountData> erpEstimateAmountDataOptional = estimateAmountRepository
				.findById(consumerapplication.getErpWorkFlowNumber());

		BigDecimal totalSupervisionAmount = null;
		BigDecimal totalDepositAmount = null;
		double loadRequestedDouble = 0.0;
		BigDecimal loadRequestedDecimal = BigDecimal.ZERO;
		BigDecimal kvaLoadAmount = null;
		BigDecimal kwloads = null;
		BigDecimal minusCost = BigDecimal.ZERO;

		ErpEstimateAmountData erpEstimateAmount = erpEstimateAmountDataOptional.get();

		BigDecimal supervisionAmount = roundAmountCgstAndSgst(erpEstimateAmount.getSupervisionAmount());

		BigDecimal superVisionDouble = roundAmountCgstAndSgst(erpEstimateAmount.getSupervisionAmount());

		BigDecimal estimateAmount = erpEstimateAmount.getEstimateAmount();

		BigDecimal estimateAmountDouble = roundAmountCgstAndSgst(erpEstimateAmount.getEstimateAmount());

		if (erpEstimateAmountDataOptional.get().getMinusCost() != null) {
			minusCost = roundAmountCgstAndSgst(
					erpEstimateAmountDataOptional.get().getMinusCost().multiply(new BigDecimal(-1)));
			if (Objects.equals(consumerapplication.getIsAvedakGovernmentErp(), "Yes")) {
				minusCost = BigDecimal.ZERO;
				erpEstimateAmount.setJeReturnAmount(BigDecimal.ZERO);
			} else {
				erpEstimateAmount.setJeReturnAmount(minusCost);
			}

		}
		BigDecimal cgst = roundAmountCgstAndSgst(superVisionDouble.multiply(new BigDecimal(.09)));
		BigDecimal sgst = roundAmountCgstAndSgst(superVisionDouble.multiply(new BigDecimal(.09)));

		estimateAmountDto.setSgst(sgst);
		estimateAmountDto.setCgst(cgst);

		erpEstimateAmount.setCgst(cgst);
		erpEstimateAmount.setSgst(sgst);

		BigDecimal supervisionbalanceRemain = superVisionDouble;
		BigDecimal supervisionBalanceRemaining = supervisionbalanceRemain;

		estimateAmountDto.setSuperVisionAmount(superVisionDouble);

		erpEstimateAmount.setSupervisionAmount(supervisionAmount);
		erpEstimateAmount.setEstimateAmount(roundAmountCgstAndSgst(estimateAmount));

		if (consumerapplication.getJeLoad() != null) {

			loadRequestedDouble = Double.valueOf(consumerapplication.getJeLoad());
			loadRequestedDecimal = new BigDecimal(consumerapplication.getJeLoad());

		}

		if (schemeObject.getSchemeTypeName().equals("Supervision")
				&& erpEstimateAmountDataOptional.get().getSchemeCode().contains("SCCW")) {

			if (erpEstimateAmountDataOptional != null) {

				if (erpEstimateAmount.getSchema() != null
						&& erpEstimateAmount.getSchema().toUpperCase().contains("SCCW")) {

//					BigDecimal jeReturnAmount = null;
//
//					if (consumerapplication.getJeReturnAmount() == null) {
//						jeReturnAmount = new BigDecimal(0.0);
//					} else {
//						jeReturnAmount = round111(consumerapplication.getJeReturnAmount(), 0);
//					}

					erpEstimateAmount.setJeReturnAmount(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);
//					Colony Electrification(Illegal)
					if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 4) {

						String loadRequstedUnit = consumerapplication.getJeLoad();
						loadRequestedDecimal = new BigDecimal(loadRequstedUnit);
//						400 matlb 400 tak hai ( yes aya hai front end se)

						double number = Double.parseDouble(loadRequstedUnit);

//						indiviual dalne pr hamesa load unit KW hi ayega 
						if (consumerapplication.getColonyIllegalSelectionType() != null
								&& consumerapplication.getColonyIllegalSelectionType().equals("2")
								&& consumerapplication.getIndividualOrGroup() != null
								&& consumerapplication.getIndividualOrGroup().getIndividualOrGroupId() == 1) {

							if (consumerapplication.getJeLoad() != null && number <= 400) {
								estimateAmountDto.setSgst(null);
								estimateAmountDto.setCgst(null);

								erpEstimateAmount.setCgst(null);
								erpEstimateAmount.setSgst(null);
								BigDecimal round = null;
//							charitra code start 

//								kw ke case me ye chalega
								round = roundAmountCgstAndSgst(new BigDecimal(number).multiply(new BigDecimal(15567)));

//					charitra end code
//							billdesk walo ko yahi amount beja ja raha hai
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

								erpEstimateAmount.setColonyOrSlum(round);
								estimateAmountDto.setColonyOrSlum(round);

								estimateAmountDto.setSuperVisionAmount(null);

							} else {

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								BigDecimal round = totalSupervisionAmount;
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

							}

						} else {

							if (
//									consumerapplication.getIndividualOrGroup().getIndividualOrGroupId().equals(2l) && 
							consumerapplication.getJeLoadUnitKwYaKva().equals("KW")) {
								number = upperRound(number * 1.25, 0);
							}

							if (consumerapplication.getJeLoad() != null && number <= 1500) {

								BigDecimal round = null;
//							charitra code start 

//								KVA ke case me ye chalega
								round = roundAmountCgstAndSgst(new BigDecimal(number).multiply(new BigDecimal(850)));

//					charitra end code
//							billdesk walo ko yahi amount beja ja raha hai
//								15-04-2025  ye niche ki line comment ki gai hai ,or niche 2 ling bada di gai hai								
//								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
//								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

//								billdesk walo ko yahi amount beja ja raha hai
								erpEstimateAmount.setTotalBalanceSupervisionAmount(supervisionBalanceRemaining.add(cgst)
										.add(sgst).add(round11(round, 2).add(minusCost)));
								estimateAmountDto.setTotalamountOfSupervision(supervisionBalanceRemaining.add(cgst)
										.add(sgst).add(round11(round, 2).add(minusCost)));

								erpEstimateAmount.setKvaLoad(round);
								estimateAmountDto.setKvaLoadAmount(round);
							} else {

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								BigDecimal round = totalSupervisionAmount;
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
								estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

							}

						}
					}
//                  NSC 
//					commented given code because there is change in NOW 2 calculation 15-09-2025
//					} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2) {
//
//						totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
//						BigDecimal round = roundAmountCgstAndSgst(totalSupervisionAmount);
//						erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
//						estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));
//
//						erpEstimateAmount.setJeReturnAmount(minusCost);
//						estimateAmountDto.setJeReturnAmount(minusCost);
//
//					}

					else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2) {
						Function<BigDecimal, BigDecimal> safeRound = val -> roundAmountCgstAndSgst(
								Objects.isNull(val) ? BigDecimal.ZERO : val);

						if (Objects.isNull(consumerapplication.getSspTotalAmount())) {
							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
							BigDecimal round = roundAmountCgstAndSgst(totalSupervisionAmount);

							erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(round, 2).add(minusCost));
							estimateAmountDto.setTotalamountOfSupervision(round11(round, 2).add(minusCost));

							erpEstimateAmount.setJeReturnAmount(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							// added this code for taking the amount charges of SSP portal in DSP for NOW 2
							// 11-09-2025 start
							BigDecimal sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());
							BigDecimal sspRegistrationCharge = safeRound
									.apply(consumerapplication.getSspRegistrationCharge());
							BigDecimal sspMeterCost = safeRound.apply(consumerapplication.getSspMeterCost());
							BigDecimal sspSecurityDeposit = safeRound
									.apply(consumerapplication.getSspSecurityDeposit());
							BigDecimal sspSupplyAffordingCharges = safeRound
									.apply(consumerapplication.getSspSupplyAffordingCharges());

							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
							BigDecimal round = roundAmountCgstAndSgst(totalSupervisionAmount);

							erpEstimateAmount.setTotalBalanceSupervisionAmount(
									round11(round, 2).add(minusCost).add(sspTotalAmount));
							estimateAmountDto
									.setTotalamountOfSupervision(round11(round, 2).add(minusCost).add(sspTotalAmount));

							erpEstimateAmount.setSspTotalAmount(sspTotalAmount);
							estimateAmountDto.setSspTotalAmount(sspTotalAmount);

							erpEstimateAmount.setSspRegistrationCharge(sspRegistrationCharge);
							estimateAmountDto.setSspRegistrationCharge(sspRegistrationCharge);

							erpEstimateAmount.setSspMeterCost(sspMeterCost);
							estimateAmountDto.setSspMeterCost(sspMeterCost);

							erpEstimateAmount.setSecurityDeposit(sspSecurityDeposit);
							estimateAmountDto.setSecurityDeposit(sspSecurityDeposit);

							erpEstimateAmount.setKvaLoad(sspSupplyAffordingCharges);
							estimateAmountDto.setKvaLoadAmount(sspSupplyAffordingCharges);

							erpEstimateAmount.setJeReturnAmount(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
//								end
						}

					}

//						Colony Electrification(Legal)
					else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 3) {
						double i1 = Double.parseDouble(consumerapplication.getJeLoad());
						BigDecimal bigDecimal = new BigDecimal(consumerapplication.getJeLoad());

//						kva ke liye code
						if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
							if (i1 < 1500) {
								kvaLoadAmount = bigDecimal.multiply(new BigDecimal(850));

								BigDecimal roundKvaLoad = round111(kvaLoadAmount, 0);

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
										.add(roundKvaLoad);
								BigDecimal round = round11(totalSupervisionAmount, 2);

								erpEstimateAmount.setKvaLoad(roundKvaLoad);
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round.add(minusCost));

								estimateAmountDto.setTotalamountOfSupervision(round.add(minusCost));

								estimateAmountDto.setKvaLoadAmount(roundKvaLoad);
							} else {
								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								estimateAmountDto
										.setTotalamountOfSupervision(round11(totalSupervisionAmount, 2).add(minusCost));
								erpEstimateAmount.setTotalBalanceSupervisionAmount(
										round11(totalSupervisionAmount, 2).add(minusCost));
							}

						}

					} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 10) {
						double i1 = Double.parseDouble(consumerapplication.getJeLoad());
						BigDecimal bigDecimal = new BigDecimal(consumerapplication.getJeLoad());

//						kva ke liye code
						if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
//							yhan 25000 KVA use kiya hai kyuki officially hamare pass iska koi documented data nahi hai 
							if (i1 < 25000) {
								kvaLoadAmount = bigDecimal.multiply(new BigDecimal(1260));

								BigDecimal roundKvaLoad = round111(kvaLoadAmount, 0);

								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
										.add(roundKvaLoad);
								BigDecimal round = round11(totalSupervisionAmount, 2);

								erpEstimateAmount.setKvaLoad(roundKvaLoad);
								erpEstimateAmount.setTotalBalanceSupervisionAmount(round.add(minusCost));

								estimateAmountDto.setTotalamountOfSupervision(round.add(minusCost));

								estimateAmountDto.setKvaLoadAmount(roundKvaLoad);
							} else {
								totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
								estimateAmountDto
										.setTotalamountOfSupervision(round11(totalSupervisionAmount, 2).add(minusCost));
								erpEstimateAmount.setTotalBalanceSupervisionAmount(
										round11(totalSupervisionAmount, 2).add(minusCost));
							}

						}
					} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(9l)) {

						totalSupervisionAmount = roundAmountCgstAndSgst(
								supervisionBalanceRemaining.add(cgst).add(sgst));

						BigDecimal loadAmount = BigDecimal.ZERO;
						if (loadRequestedDouble <= 3) {
							loadAmount = loadRequestedDecimal.multiply(new BigDecimal(50));
						} else if (loadRequestedDouble <= 10) {
							loadAmount = new BigDecimal(150).add(
									(loadRequestedDecimal.subtract(new BigDecimal(3))).multiply(new BigDecimal(150)));
						} else if (loadRequestedDouble <= 25) {
							loadAmount = new BigDecimal(1200).add(
									(loadRequestedDecimal.subtract(new BigDecimal(10))).multiply(new BigDecimal(380)));

						} else if (loadRequestedDouble > 25) {
							loadAmount = new BigDecimal(6900).add(
									(loadRequestedDecimal.subtract(new BigDecimal(25))).multiply(new BigDecimal(630)));
						}

						estimateAmountDto.setKvaLoadAmount(loadAmount);
						erpEstimateAmount.setKvaLoad(loadAmount);

						estimateAmountDto.setTotalamountOfSupervision(
								roundAmountCgstAndSgst(totalSupervisionAmount.add(loadAmount)));
						erpEstimateAmount.setTotalBalanceSupervisionAmount(
								roundAmountCgstAndSgst(totalSupervisionAmount.add(loadAmount)));

					}

					// Nature of work id 1,6 and 7 k liye or 11 ke liye
					else {
//						BigDecimal jeReturnAmt = round111(consumerapplication.getJeReturnAmount(), 0);
						BigDecimal jeReturnAmt = null;
						if (consumerapplication.getJeReturnAmount() != null) {
							jeReturnAmt = round111(consumerapplication.getJeReturnAmount(), 0);
						}
						if (jeReturnAmt != null) {
							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
									.add(consumerapplication.getJeReturnAmount());

							estimateAmountDto.setJeReturnAmount(jeReturnAmt);
							erpEstimateAmount.setJeReturnAmount(jeReturnAmt);

							estimateAmountDto.setTotalamountOfSupervision(round11(totalSupervisionAmount, 2));
							erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(totalSupervisionAmount, 2));
						}

						else {
							totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst);
							estimateAmountDto.setTotalamountOfSupervision(round11(totalSupervisionAmount, 2));
							erpEstimateAmount.setTotalBalanceSupervisionAmount(round11(totalSupervisionAmount, 2));

						}
					}
					// code start 02-12-2025 Monika Rajpoot
					if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 12L) {

						BigDecimal jeReturnAmt = consumerapplication.getJeReturnAmount() != null
								? round111(consumerapplication.getJeReturnAmount(), 0)
								: BigDecimal.ZERO;

						totalSupervisionAmount = supervisionBalanceRemaining.add(cgst).add(sgst)
								.add(consumerapplication.getJeReturnAmount()).add(new BigDecimal(1180));

						System.err.println("totalSupervisionAmount  : " + totalSupervisionAmount);
						estimateAmountDto.setJeReturnAmount(jeReturnAmt);
						erpEstimateAmount.setJeReturnAmount(jeReturnAmt);

						estimateAmountDto.setTotalamountOfSupervision(roundAmountCgstAndSgst(totalSupervisionAmount));
						erpEstimateAmount
								.setTotalBalanceSupervisionAmount(roundAmountCgstAndSgst(totalSupervisionAmount));

						erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
						estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));
					}

					// charitra start code 16-12-2025					
					if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 13|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 14) {

						

						estimateAmountDto.setTotalamountOfSupervision(roundAmountCgstAndSgst(estimateAmountDto.getSuperVisionAmount().add(cgst).add(sgst)));
						erpEstimateAmount
								.setTotalBalanceSupervisionAmount(roundAmountCgstAndSgst(estimateAmountDto.getSuperVisionAmount().add(cgst).add(sgst)));

			
					}
					// code end 02-12-2025

					erpEstimateAmount.setU_sec_194J_tds2(null);
					erpEstimateAmount.setU_sec_194C_tds2(null);
					erpEstimateAmount.setU_194C_tds2_fSupDep(null);

					return estimateAmountDto;
				} else {
					throw new Exception("Estimate Scheme not Matched");

				}
			}

		} else if (schemeObject.getSchemeTypeName().equals("Deposit")
				&& erpEstimateAmountDataOptional.get().getSchemeCode().equalsIgnoreCase("DEPOSITE")) {

			BigDecimal supervisionAmountDouble = roundAmountCgstAndSgst(erpEstimateAmount.getSupervisionAmount());

			BigDecimal depositAmount = BigDecimal.ZERO;

			depositAmount = estimateAmountDouble.subtract(supervisionAmountDouble).subtract(cgst).subtract(sgst);

			BigDecimal round2 = roundAmountCgstAndSgst(depositAmount);

			BigDecimal totalEstimateAmount = roundAmountCgstAndSgst(
					supervisionAmountDouble.add(round2).add(cgst).add(sgst));

//			estimateAmountDto.setMinusCost(minusCost);
			estimateAmountDto.setDepositAmount(round2);
			estimateAmountDto.setSuperVisionAmount(supervisionAmountDouble);

			erpEstimateAmount.setDepositAmount(round2);

			if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 1
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 6
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 7
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2
					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 11) {

				// added this code for taking the amount charges of SSP portal in DSP for NOW 2
				// 11-09-2025 start
				Function<BigDecimal, BigDecimal> safeRound = val -> roundAmountCgstAndSgst(
						Objects.isNull(val) ? BigDecimal.ZERO : val);
				BigDecimal sspTotalAmount;

				if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2
						&& Objects.nonNull(consumerapplication.getSspTotalAmount())) {

					sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());
					BigDecimal sspRegistrationCharge = safeRound.apply(consumerapplication.getSspRegistrationCharge());
					BigDecimal sspMeterCost = safeRound.apply(consumerapplication.getSspMeterCost());
					BigDecimal sspSecurityDeposit = safeRound.apply(consumerapplication.getSspSecurityDeposit());
					BigDecimal sspSupplyAffordingCharges = safeRound
							.apply(consumerapplication.getSspSupplyAffordingCharges());

					erpEstimateAmount.setSspTotalAmount(sspTotalAmount);
					estimateAmountDto.setSspTotalAmount(sspTotalAmount);

					erpEstimateAmount.setSspRegistrationCharge(sspRegistrationCharge);
					estimateAmountDto.setSspRegistrationCharge(sspRegistrationCharge);

					erpEstimateAmount.setSspMeterCost(sspMeterCost);
					estimateAmountDto.setSspMeterCost(sspMeterCost);

					erpEstimateAmount.setSecurityDeposit(sspSecurityDeposit);
					estimateAmountDto.setSecurityDeposit(sspSecurityDeposit);

					erpEstimateAmount.setKvaLoad(sspSupplyAffordingCharges);
					estimateAmountDto.setKvaLoadAmount(sspSupplyAffordingCharges);
				}
				sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());

//				end

				// added this code for taking the amount charges of SSP portal in DSP for NOW 2
				// 11-09-2025 start

				if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2
						&& Objects.nonNull(consumerapplication.getSspTotalAmount())) {

					sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());
					BigDecimal sspRegistrationCharge = safeRound.apply(consumerapplication.getSspRegistrationCharge());
					BigDecimal sspMeterCost = safeRound.apply(consumerapplication.getSspMeterCost());
					BigDecimal sspSecurityDeposit = safeRound.apply(consumerapplication.getSspSecurityDeposit());
					BigDecimal sspSupplyAffordingCharges = safeRound
							.apply(consumerapplication.getSspSupplyAffordingCharges());

					erpEstimateAmount.setSspTotalAmount(sspTotalAmount);
					estimateAmountDto.setSspTotalAmount(sspTotalAmount);

					erpEstimateAmount.setSspRegistrationCharge(sspRegistrationCharge);
					estimateAmountDto.setSspRegistrationCharge(sspRegistrationCharge);

					erpEstimateAmount.setSspMeterCost(sspMeterCost);
					estimateAmountDto.setSspMeterCost(sspMeterCost);

					erpEstimateAmount.setSecurityDeposit(sspSecurityDeposit);
					estimateAmountDto.setSecurityDeposit(sspSecurityDeposit);

					erpEstimateAmount.setKvaLoad(sspSupplyAffordingCharges);
					estimateAmountDto.setKvaLoadAmount(sspSupplyAffordingCharges);
				}
				sspTotalAmount = safeRound.apply(consumerapplication.getSspTotalAmount());

//				end

				if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount.add(minusCost));
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setMinusCost(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);
				} else {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount);
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount.add(sspTotalAmount));
					estimateAmountDto.setMinusCost(null);
					estimateAmountDto.setJeReturnAmount(null);

				}

			}
//			commented given code because there is change in NOW 2 calculation 15-09-2025
//			if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 1
//					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 6
//					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 7
//					|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2) {
//
//				if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
//					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount.add(minusCost));
//					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount);
//					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount);
//					estimateAmountDto.setMinusCost(minusCost);
//					estimateAmountDto.setJeReturnAmount(minusCost);
//				} else {
//					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount);
//					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount);
//					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount);
//					estimateAmountDto.setMinusCost(null);
//					estimateAmountDto.setJeReturnAmount(null);
//
//				}
//			}
//			else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 2) {
//
//
//				BigDecimal returnEstimate = roundAmountCgstAndSgst(totalEstimateAmount.add(minusCost));
//
//				erpEstimateAmount.setTotalBalanceDepositAmount(returnEstimate);
//				erpEstimateAmount.setSgst(sgst);
//				erpEstimateAmount.setCgst(cgst);
//				estimateAmountDto.setTotaldepositAmount(returnEstimate);
//
//				erpEstimateAmount.setJeReturnAmount(jeReturnAmount);
//				estimateAmountDto.setJeReturnAmount(jeReturnAmount);
//
////				File No. 382469 according this in the given application we don't need to take minus cost added 11-Dec-2024
//				if ((consumerapplication.getConsumerApplicationNo().equals("DS1725527898869"))
//						&& consumerapplication.getErpWorkFlowNumber().equals("137010")) {
//					estimateAmountDto.setDepositAmount(round2.add(minusCost));
//					erpEstimateAmount.setDepositAmount(round2.add(minusCost));
//
//					erpEstimateAmount.setTotalBalanceDepositAmount(estimateAmountDouble);
//					estimateAmountDto.setTotaldepositAmount(estimateAmountDouble);
//				}
//			}

//			Colony Electrification(Legal)

			else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 3) {

				double i1 = Double.parseDouble(consumerapplication.getJeLoad());
				BigDecimal jeLoad = round111(new BigDecimal(consumerapplication.getJeLoad()), 0);

				if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
					if (i1 < 1500) {
						kvaLoadAmount = roundAmountCgstAndSgst(jeLoad.multiply(new BigDecimal(850)));

						totalDepositAmount = totalEstimateAmount.add(kvaLoadAmount);

						erpEstimateAmount.setKvaLoad(kvaLoadAmount);
						estimateAmountDto.setKvaLoadAmount(kvaLoadAmount);

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					} else {
						totalDepositAmount = totalEstimateAmount;
						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					}

				}

			} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 4) {
				double load = Double.valueOf(consumerapplication.getJeLoad());
				loadRequestedDecimal = round111(new BigDecimal(load), 0);

				if (consumerapplication.getColonyIllegalSelectionType() != null
						&& consumerapplication.getColonyIllegalSelectionType().equals("2")
						&& consumerapplication.getIndividualOrGroup() != null
						&& consumerapplication.getIndividualOrGroup().getIndividualOrGroupId() == 1) {

					if (load <= 400) {

						kwloads = roundAmountCgstAndSgst(loadRequestedDecimal.multiply(new BigDecimal(15567)));

						erpEstimateAmount.setColonyOrSlum(kwloads);
						erpEstimateAmount.setSgst(null);
						erpEstimateAmount.setCgst(null);
						erpEstimateAmount.setDepositAmount(null);

						estimateAmountDto.setColonyOrSlum(kwloads);
						estimateAmountDto.setCgst(null);
						estimateAmountDto.setSgst(null);
						estimateAmountDto.setSuperVisionAmount(null);
						estimateAmountDto.setDepositAmount(null);

						totalDepositAmount = kwloads;

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							erpEstimateAmount.setTotalBalanceDepositAmount(kwloads.add(minusCost));
							estimateAmountDto.setTotaldepositAmount(kwloads.add(minusCost));
							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							erpEstimateAmount.setTotalBalanceDepositAmount(kwloads);
							estimateAmountDto.setTotaldepositAmount(kwloads);
							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					} else {
//						In the case of illigal colony application, load greater than 400 KW is not Acceptable
//						yaha code kabhi ayega nai, kyuki controler se hi return ho gya hai
//						totalDepositAmount = totalEstimateAmount;
//						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
//							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));
//							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
//							estimateAmountDto.setMinusCost(minusCost);
//						} else {
//							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);
//							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
//							estimateAmountDto.setMinusCost(null);
//							estimateAmountDto.setJeReturnAmount(null);
//						}
					}

				} else {
					if (consumerapplication.getJeLoadUnitKwYaKva().equals("KW")) {
						load = upperRound(load * 1.25, 0);
					}

					if (consumerapplication.getJeLoad() != null && load <= 1500) {

						BigDecimal round = null;

						round = roundAmountCgstAndSgst(round111(new BigDecimal(load), 0).multiply(new BigDecimal(850)));

						erpEstimateAmount.setKvaLoad(round);
						estimateAmountDto.setKvaLoadAmount(round);

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {

							erpEstimateAmount
									.setTotalBalanceDepositAmount(totalEstimateAmount.add(round).add(minusCost));
							estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(round).add(minusCost));
							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(round));
							estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(round));
							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}

					} else {

						totalDepositAmount = estimateAmountDouble;

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							erpEstimateAmount.setTotalBalanceSupervisionAmount(totalDepositAmount.add(minusCost));
							estimateAmountDto.setTotalamountOfSupervision(totalDepositAmount.add(minusCost));
							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);
						} else {
							erpEstimateAmount.setTotalBalanceSupervisionAmount(totalDepositAmount);
							estimateAmountDto.setTotalamountOfSupervision(totalDepositAmount);
							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					}

				}

			}

			else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId().equals(9l)) {

				BigDecimal loadAmount = BigDecimal.ZERO;
				if (loadRequestedDouble <= 3) {
					loadAmount = loadRequestedDecimal.multiply(new BigDecimal(50));
				} else if (loadRequestedDouble <= 10) {
					loadAmount = new BigDecimal(150)
							.add((loadRequestedDecimal.subtract(new BigDecimal(3))).multiply(new BigDecimal(150)));
				} else if (loadRequestedDouble <= 25) {
					loadAmount = new BigDecimal(1200)
							.add((loadRequestedDecimal.subtract(new BigDecimal(10))).multiply(new BigDecimal(380)));

				} else if (loadRequestedDouble > 25) {
					loadAmount = new BigDecimal(6900)
							.add((loadRequestedDecimal.subtract(new BigDecimal(25))).multiply(new BigDecimal(630)));
				}

				estimateAmountDto.setKvaLoadAmount(loadAmount);
				erpEstimateAmount.setKvaLoad(loadAmount);

				erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(loadAmount));
				estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(loadAmount));

				if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
					erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(loadAmount).add(minusCost));
					estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(loadAmount).add(minusCost));
					estimateAmountDto.setMinusCost(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);
				} else {
					erpEstimateAmount.setTotalBalanceDepositAmount(totalEstimateAmount.add(loadAmount));
					estimateAmountDto.setTotaldepositAmount(totalEstimateAmount.add(loadAmount));
					estimateAmountDto.setMinusCost(null);
					estimateAmountDto.setJeReturnAmount(null);
				}
			} else if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 10) {

				double i1 = Double.parseDouble(consumerapplication.getJeLoad());
				BigDecimal jeLoad = round111(new BigDecimal(consumerapplication.getJeLoad()), 0);

				if (consumerapplication.getJeLoadUnitKwYaKva().equals("KVA")) {
//					yhan 25000 KVA use kiya hai kyuki officially hamare pass iska koi documented data nahi hai 
					if (i1 < 25000) {
						kvaLoadAmount = roundAmountCgstAndSgst(jeLoad.multiply(new BigDecimal(1260)));

						totalDepositAmount = totalEstimateAmount.add(kvaLoadAmount);

						erpEstimateAmount.setKvaLoad(kvaLoadAmount);
						estimateAmountDto.setKvaLoadAmount(kvaLoadAmount);

						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					} else {
						totalDepositAmount = totalEstimateAmount;
						if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount.add(minusCost));
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount.add(minusCost));

							estimateAmountDto.setMinusCost(minusCost);
							estimateAmountDto.setJeReturnAmount(minusCost);

						} else {
							estimateAmountDto.setTotaldepositAmount(totalDepositAmount);
							erpEstimateAmount.setTotalBalanceDepositAmount(totalDepositAmount);

							estimateAmountDto.setMinusCost(null);
							estimateAmountDto.setJeReturnAmount(null);
						}
					}

				}

			} // code start 02-12-2025 Monika Rajpoot
			if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 12L) {
				if (consumerapplication.getGoodMaterialOrnot().equals(0)) {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount.add(minusCost));
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(new BigDecimal(1180)));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount);
					estimateAmountDto.setMinusCost(minusCost);
					estimateAmountDto.setJeReturnAmount(minusCost);
					erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
					estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));
				} else {
					BigDecimal totalPayabelDepositAmount = roundAmountCgstAndSgst(totalEstimateAmount);
					erpEstimateAmount.setTotalBalanceDepositAmount(totalPayabelDepositAmount.add(new BigDecimal(1180)));
					estimateAmountDto.setTotaldepositAmount(totalPayabelDepositAmount);
					estimateAmountDto.setMinusCost(null);
					estimateAmountDto.setJeReturnAmount(null);
					erpEstimateAmount.setRegistrationCharges(new BigDecimal(1180));
					estimateAmountDto.setRegistrationCharges(new BigDecimal(1180));

				}
			}
			
			// charitra start code 16-12-2025					
			if (consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 13|| consumerapplication.getNatureOfWorkType().getNatureOfWorkTypeId() == 14) {

				

				estimateAmountDto.setTotalamountOfSupervision(roundAmountCgstAndSgst(estimateAmountDto.getSuperVisionAmount().add(cgst).add(sgst)));
				erpEstimateAmount
						.setTotalBalanceSupervisionAmount(roundAmountCgstAndSgst(estimateAmountDto.getSuperVisionAmount().add(cgst).add(sgst)));

	
			}
//			end
			erpEstimateAmount.setU_sec_194J_tds2(null);
			erpEstimateAmount.setU_sec_194C_tds2(null);
			erpEstimateAmount.setU_194C_tds2_fSupDep(null);
//			estimateAmountRepository.save(erpEstimateAmount);
			return estimateAmountDto;

		} else if (schemeObject.getSchemeTypeName().equals("Departmental (MPMKVVCL)"))

		{

			BigDecimal oytAmount = estimateAmountDouble.add(cgst).add(sgst);
			BigDecimal parikshan = oytAmount.multiply(new BigDecimal(.03));
			BigDecimal parikshanCgst = parikshan.multiply(new BigDecimal(.09));
			BigDecimal parikshanMpgst = parikshan.multiply(new BigDecimal(.09));
			BigDecimal finalOyt = parikshan.add(parikshanMpgst).add(parikshanMpgst);
			BigDecimal finalOytDouble = finalOyt;
			BigDecimal finalparikshanCgst = parikshanCgst;
			BigDecimal parikshanAmount = parikshan;

			erpEstimateAmount.setTotalOytAmount(finalOyt);
			erpEstimateAmount.setCgst(parikshanCgst);
			erpEstimateAmount.setSgst(parikshanMpgst);
			erpEstimateAmount.setPrayakshanShulk(parikshan);
			estimateAmountDto.setParikshanCgst(finalparikshanCgst);
			estimateAmountDto.setPrayakshanShulk(parikshanAmount);
			estimateAmountDto.setFinalOyt(finalOytDouble);
			erpEstimateAmount.setU_sec_194J_tds2(null);
			erpEstimateAmount.setU_sec_194C_tds2(null);
			erpEstimateAmount.setU_194C_tds2_fSupDep(null);

			return estimateAmountDto;
		} else if (schemeObject.getSchemeTypeName().equals("Supervision")
				&& erpEstimateAmountDataOptional.get().getSchemeCode().contains("OYT")) {

			BigDecimal jeLoadAmount = BigDecimal.ZERO;
			// oyt ke case me je load 1.1 hone pr bhi 2 hona chaiye
			// ye 22-01-2025 ko bola gya hai
			// isme 3 field badai hai = 2495 rs , + 5 Rs , + jeload*600 RS

			if (erpEstimateAmountDataOptional != null) {

				estimateAmountDto = new ErpEstimateCalculatedDto();

				if (erpEstimateAmount.getSchema() != null
						&& erpEstimateAmount.getSchema().toUpperCase().contains("OYT")) {

					if (Objects.isNull(consumerapplication.getSspTotalAmount())
							|| (consumerapplication.getSspTotalAmount()).compareTo(BigDecimal.ZERO) <= 0) {
//			code start		7 - April -2025 if je load is less than or equal to 10 than 200 per hp and above 10 so 400 pr hp 
						if (consumerapplication.getJeLoad() == null) {
							consumerapplication.setJeLoad(String.valueOf(BigDecimal.ZERO));
						}
						double jeLoad = Double.parseDouble(consumerapplication.getJeLoad());
						if (jeLoad <= 10) {
							jeLoadAmount = new BigDecimal(
									upperRound(Double.parseDouble(consumerapplication.getJeLoad()), 0))
									.multiply(new BigDecimal(200));
						} else {
							jeLoadAmount = new BigDecimal(
									upperRound(Double.parseDouble(consumerapplication.getJeLoad()), 0))
									.multiply(new BigDecimal(400));
						}

						System.err.println("aaaaaaaaaa : " + jeLoadAmount);
//					code end 07-04-2025
						estimateAmountDto.setSgst(sgst);
						estimateAmountDto.setCgst(cgst);

						erpEstimateAmount.setCgst(cgst);
						erpEstimateAmount.setSgst(sgst);
						BigDecimal round = superVisionDouble.add(cgst).add(sgst);

						estimateAmountDto.setSuperVisionAmount(supervisionAmount);

//					estimateAmountDto.setAvedanShulk(new BigDecimal(2495));
						estimateAmountDto.setAvedanShulkFiveRupee(new BigDecimal(5));
						estimateAmountDto.setSecurityDeposit(roundAmountCgstAndSgst(jeLoadAmount));

						erpEstimateAmount.setU_sec_194J_tds2(null);
						erpEstimateAmount.setU_sec_194C_tds2(null);
						erpEstimateAmount.setU_194C_tds2_fSupDep(null);
//					erpEstimateAmount.setAvedanShulk(new BigDecimal(2495));
						erpEstimateAmount.setAvedanShulkFiveRupee(new BigDecimal(5));
						erpEstimateAmount.setSecurityDeposit(roundAmountCgstAndSgst(jeLoadAmount));

						BigDecimal total_amount = new BigDecimal(0.0);
						Map<String, BigDecimal> getoytMaterialforGetAmount = getoytMaterialforGetAmount(
								consumerapplication.getConsumerApplicationNo());

						if ((getoytMaterialforGetAmount.get("total_amount").compareTo(new BigDecimal(0))) <= 0) {
							total_amount = new BigDecimal(0.0);
						} else {
							total_amount = getoytMaterialforGetAmount.get("total_amount");
						}

						erpEstimateAmount.setOytMaterialTotalCostWithCsgstAndSgst(total_amount);
						estimateAmountDto.setOytMaterialtotalcostwithCgstAndSgst(total_amount);

						erpEstimateAmount.setTotalBalanceSupervisionAmount(
								round11(round, 2).add(new BigDecimal(5)).add(jeLoadAmount).add(total_amount));
						estimateAmountDto.setTotalamountOfSupervision(
								round11(round, 2).add(new BigDecimal(5)).add(jeLoadAmount).add(total_amount));

						return estimateAmountDto;
					} else {
// Condition for those new OYT application whose total amount is coming from SSP Portal as sspTotalAmount so now we will take supervision+cgst+sgst+sspTotalAmount
// 25-09-2025 code written by Monika Rajpoot  
						estimateAmountDto.setSgst(sgst);
						estimateAmountDto.setCgst(cgst);

						erpEstimateAmount.setCgst(cgst);
						erpEstimateAmount.setSgst(sgst);
						BigDecimal round = superVisionDouble.add(cgst).add(sgst);

						estimateAmountDto.setSuperVisionAmount(supervisionAmount);

						estimateAmountDto
								.setSspMeterCost(roundAmountCgstAndSgst(consumerapplication.getSspMeterCost()));
						estimateAmountDto.setKvaLoadAmount(
								roundAmountCgstAndSgst(consumerapplication.getSspSupplyAffordingCharges()));
						estimateAmountDto.setSspRegistrationCharge(
								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
						estimateAmountDto.setAvedanShulkFiveRupee(
								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
						estimateAmountDto.setSecurityDeposit(
								roundAmountCgstAndSgst(consumerapplication.getSspSecurityDeposit()));
						estimateAmountDto
								.setSspTotalAmount(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()));

						erpEstimateAmount.setU_sec_194J_tds2(null);
						erpEstimateAmount.setU_sec_194C_tds2(null);
						erpEstimateAmount.setU_194C_tds2_fSupDep(null);

						erpEstimateAmount
								.setSspMeterCost(roundAmountCgstAndSgst(consumerapplication.getSspMeterCost()));
						erpEstimateAmount
								.setKvaLoad(roundAmountCgstAndSgst(consumerapplication.getSspSupplyAffordingCharges()));
						erpEstimateAmount.setSspRegistrationCharge(
								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
						erpEstimateAmount.setAvedanShulkFiveRupee(
								roundAmountCgstAndSgst(consumerapplication.getSspRegistrationCharge()));
						erpEstimateAmount.setSecurityDeposit(
								roundAmountCgstAndSgst(consumerapplication.getSspSecurityDeposit()));
						erpEstimateAmount
								.setSspTotalAmount(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()));

						BigDecimal total_amount = new BigDecimal(0.0);
						Map<String, BigDecimal> getoytMaterialforGetAmount = getoytMaterialforGetAmount(
								consumerapplication.getConsumerApplicationNo());

						if ((getoytMaterialforGetAmount.get("total_amount").compareTo(new BigDecimal(0))) <= 0) {
							total_amount = new BigDecimal(0.0);
						} else {
							total_amount = getoytMaterialforGetAmount.get("total_amount");
						}

						erpEstimateAmount.setOytMaterialTotalCostWithCsgstAndSgst(total_amount);
						estimateAmountDto.setOytMaterialtotalcostwithCgstAndSgst(total_amount);

						erpEstimateAmount.setTotalBalanceSupervisionAmount(
								round11(round, 2).add(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()))
										.add(total_amount));
						estimateAmountDto.setTotalamountOfSupervision(
								round11(round, 2).add(roundAmountCgstAndSgst(consumerapplication.getSspTotalAmount()))
										.add(total_amount));

						return estimateAmountDto;
					}
// 25-09-2025 code written by Monika Rajpoot  end
				} else {
//		throw new Exception("Estimate Scheme not Matched");

				}

			}
		}
		return null;

	}

	@Autowired
	OytProjectDetailsRepository oytProjectDetailsRepository;

	public Map<String, BigDecimal> getoytMaterialforGetAmount(@PathVariable String consumerApplicationNo)
			throws ConsumerApplicationDetailException {
		List<OytProjectDetails> oytProjectDetails = oytProjectDetailsRepository
				.findDistinctByConsumerApplicationNoAndItemCodeFlag(consumerApplicationNo, 1);

		System.out.println("oytProjectDetails" + oytProjectDetails);
		OytProjectDetails oyt = new OytProjectDetails();

		BigDecimal d = BigDecimal.ZERO;
		Response<?> response = new Response<SanchayPortalDto>();
		for (OytProjectDetails s : oytProjectDetails) {

			d = d.add(s.getOytMaterialTotalCostWithCgstAndSgst());

		}

		Map<String, BigDecimal> m = new HashMap<String, BigDecimal>();
		m.put("total_amount", d);
		return m;
	}

}