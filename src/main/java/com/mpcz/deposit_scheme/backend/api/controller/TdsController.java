package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.TdsSection;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.TdsSectionRepository;
import com.mpcz.deposit_scheme.backend.api.response.Response;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tds")
public class TdsController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private TdsSectionRepository tdsSectionRepository;

	@GetMapping("/tdsDemandCalculation/{consumerAppNo}")
	public Response tdsDemandCalculation(@PathVariable String consumerAppNo) {
		Response response = new Response();
		BigDecimal twoPercentTdsCharge = new BigDecimal(0.02);
		BigDecimal tenPercentTdsCharge = new BigDecimal(0.10);
		String underSection194C = null;
		String underSection194 = null;
		String underSection51 = null;
		String tds2 = null;
		String tds10 = null;
		ErpEstimateAmountData save = null;
		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Application No. Not Found In Database");
				return response;
			}
			if (findByConsumerApplicationNumber.getNatureOfWorkType().getNatureOfWorkTypeId().equals(8l)) {
				return new Response(HttpCode.NOT_ACCEPTABLE, "Tds Not Acceptable for MKMY Scheme");
			}
			System.err.println("gstnumber : " + findByConsumerApplicationNumber.getGstNumber());
			if (findByConsumerApplicationNumber.getIsAvedakGovernmentErp().equals("Yes")
					&& findByConsumerApplicationNumber.getGstNumber() != null) {

				Optional<ErpEstimateAmountData> findById = estimateAmountRepository
						.findById(findByConsumerApplicationNumber.getErpWorkFlowNumber());
				if (findById.isPresent()) {
					ErpEstimateAmountData erpEstimateAmountData = findById.get();

					TdsSection tdsSection = tdsSectionRepository.findByConsumerAppNo(consumerAppNo);
					underSection194C = tdsSection.getUnderSection194C();
					underSection194 = tdsSection.getUnderSection194();
					underSection51 = tdsSection.getUnderSection51();
					tds2 = tdsSection.getTds2();
					tds10 = tdsSection.getTds10();

					if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId().equals(1L)) {
						if (underSection194.equals("true")) {
							if (tds2.equals("true")) {
								if (erpEstimateAmountData.getU_sec_194J_tds2() == null) {
									BigDecimal supervisionAmountDb = erpEstimateAmountData.getSupervisionAmount();
									BigDecimal twoPercentTds = round111(
											supervisionAmountDb.multiply(twoPercentTdsCharge));
									erpEstimateAmountData.setU_sec_194J_tds2(twoPercentTds);
									erpEstimateAmountData.setU_sec_194J_tds10(null);
									erpEstimateAmountData.setU_sec_51_tds2(null);
									if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 1L) {
										erpEstimateAmountData
												.setTotalBalanceSupervisionAmount(round111(erpEstimateAmountData
														.getTotalBalanceSupervisionAmount().subtract(twoPercentTds)));
									}
									if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 2L) {
										erpEstimateAmountData
												.setTotalBalanceDepositAmount(round111(erpEstimateAmountData
														.getTotalBalanceDepositAmount().subtract(twoPercentTds)));
									}
//								estimateAmountRepository.save(erpEstimateAmountData);
									save = estimateAmountRepository.save(erpEstimateAmountData);
									response.setCode(HttpCode.OK);
									response.setMessage("Data Updated Successfully");
									response.setList(Arrays.asList(save));
								} else {
									response.setCode(HttpCode.OK);
									response.setMessage("Data Retrived Successfully");
									response.setList(Arrays.asList(erpEstimateAmountData));
								}
							} else if (tds10.equals("true")) {
								if (erpEstimateAmountData.getU_sec_194J_tds10() == null) {
									BigDecimal supervisionAmountDb = erpEstimateAmountData.getSupervisionAmount();
									BigDecimal tenPercentTds = round111(
											supervisionAmountDb.multiply(tenPercentTdsCharge));
									erpEstimateAmountData.setU_sec_194J_tds10(tenPercentTds);
									erpEstimateAmountData.setU_sec_194J_tds2(null);
									erpEstimateAmountData.setU_sec_51_tds2(null);

									if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 1L) {
										erpEstimateAmountData
												.setTotalBalanceSupervisionAmount(round111(erpEstimateAmountData
														.getTotalBalanceSupervisionAmount().subtract(tenPercentTds)));
									}
									if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 2L) {
										erpEstimateAmountData
												.setTotalBalanceDepositAmount(round111(erpEstimateAmountData
														.getTotalBalanceDepositAmount().subtract(tenPercentTds)));
									}
//								estimateAmountRepository.save(erpEstimateAmountData);
									save = estimateAmountRepository.save(erpEstimateAmountData);
									response.setCode(HttpCode.OK);
									response.setMessage("Data Updated Successfully");
									response.setList(Arrays.asList(save));
								} else {
									response.setCode(HttpCode.OK);
									response.setMessage("Data Retrived Successfully");
									response.setList(Arrays.asList(erpEstimateAmountData));
								}
							}
						} else if (underSection51.equals("true")) {
							if (erpEstimateAmountData.getU_sec_51_tds2() == null) {
								BigDecimal supervisionAmountDb = erpEstimateAmountData.getSupervisionAmount();
								if (supervisionAmountDb.compareTo(new BigDecimal(250000)) > 0) {
									BigDecimal twoPercentTds = round111(
											supervisionAmountDb.multiply(twoPercentTdsCharge));
									erpEstimateAmountData.setU_sec_194J_tds2(twoPercentTds);
									erpEstimateAmountData.setU_sec_51_tds2(twoPercentTds);
									erpEstimateAmountData.setU_sec_194J_tds10(null);

									if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 1L) {
										erpEstimateAmountData.setTotalBalanceSupervisionAmount(
												round111(erpEstimateAmountData.getTotalBalanceSupervisionAmount()
														.subtract(twoPercentTds).subtract(twoPercentTds)));
									}
									if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 2L) {
										erpEstimateAmountData.setTotalBalanceDepositAmount(
												round111(erpEstimateAmountData.getTotalBalanceDepositAmount()
														.subtract(twoPercentTds).subtract(twoPercentTds)));
									}

									save = estimateAmountRepository.save(erpEstimateAmountData);
									response.setCode(HttpCode.OK);
									response.setMessage("Data Updated Successfully");
									response.setList(Arrays.asList(save));
									return response;
								}
							} else {
								response.setCode(HttpCode.OK);
								response.setMessage("Data Retrived Successfully");
								response.setList(Arrays.asList(erpEstimateAmountData));
							}

						}
					} else {
						if (underSection194C.equals("true")) {
							if (erpEstimateAmountData.getU_sec_194C_tds2() == null) {
								BigDecimal supervisionAmountDb = erpEstimateAmountData.getSupervisionAmount();
								BigDecimal depositAmountDB = erpEstimateAmountData.getDepositAmount();
								BigDecimal twoPercentTds = round111(supervisionAmountDb.multiply(twoPercentTdsCharge));
								BigDecimal twoPercentTds194C = round111(depositAmountDB.multiply(twoPercentTdsCharge));
								erpEstimateAmountData.setU_sec_194J_tds2(twoPercentTds);
								erpEstimateAmountData.setU_sec_51_tds2(null);
								erpEstimateAmountData.setU_sec_194J_tds10(null);
								erpEstimateAmountData.setU_sec_194C_tds2(twoPercentTds194C);
								erpEstimateAmountData.setU_194C_tds2_fSupDep(twoPercentTds.add(twoPercentTds194C));

								if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 1L) {
									erpEstimateAmountData.setTotalBalanceSupervisionAmount(
											round111(erpEstimateAmountData.getTotalBalanceSupervisionAmount()
													.subtract(twoPercentTds).subtract(twoPercentTds194C)));
								}
								if (findByConsumerApplicationNumber.getSchemeType().getSchemeTypeId() == 2L) {
									erpEstimateAmountData.setTotalBalanceDepositAmount(
											round111(erpEstimateAmountData.getTotalBalanceDepositAmount()
													.subtract(twoPercentTds).subtract(twoPercentTds194C)));
								}

								save = estimateAmountRepository.save(erpEstimateAmountData);
								response.setCode(HttpCode.OK);
								response.setMessage("Data Updated Successfully");
								response.setList(Arrays.asList(save));
								return response;

							} else {
								response.setCode(HttpCode.OK);
								response.setMessage("Data Retrived Successfully");
								response.setList(Arrays.asList(erpEstimateAmountData));
							}

						}
					}
				}
			} else {
				response.setCode(HttpCode.NOT_ACCEPTABLE);
				response.setMessage("Consumer Application is Not Applicable For TDS Calculation");
				return response;
			}

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@PostMapping("/save")
	public Response<TdsSection> saveTdsSection(@RequestParam String consumerAppNo, @RequestParam String section194,
			@RequestParam String section51, @RequestParam String tds2, @RequestParam String tds10,
			@RequestParam String section194C) {
		Response response = new Response();

		try {
			ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
					.findByConsumerApplicationNumber(consumerAppNo);
			if (findByConsumerApplicationNumber == null) {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Application No. Not Found In DataBase");
				return response;
			}

			TdsSection tdsSection = new TdsSection();
			tdsSection.setConsumerAppNo(consumerAppNo);
			tdsSection.setUnderSection194(section194);
			tdsSection.setUnderSection51(section51);
			tdsSection.setTds2(tds2);
			tdsSection.setTds10(tds10);
			tdsSection.setUnderSection194C(section194C);

			TdsSection findByConsumerAppNo = tdsSectionRepository.findByConsumerAppNo(consumerAppNo);
			if (findByConsumerAppNo != null) {
				tdsSectionRepository.delete(findByConsumerAppNo);
			}
			TdsSection save = tdsSectionRepository.save(tdsSection);
			if (save != null) {
				response.setCode(HttpCode.CREATED);
				response.setMessage("Data Saved Successfully");
				response.setList(Arrays.asList(save));
				return response;
			} else {
				response.setCode(HttpCode.NULL_OBJECT);
				response.setMessage("Data Not Saved In Database");
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

//	public static BigDecimal round111(BigDecimal value, int places) {
//		if (places < 0)
//			throw new IllegalArgumentException();
//		BigDecimal bd = value;
//		bd = bd.setScale(places, RoundingMode.CEILING);
//		return bd;
//	}

//	public static BigDecimal round111(BigDecimal amount) {
//		BigDecimal roundedAmount = amount.setScale(0, RoundingMode.FLOOR); // Get the integer part
//
//		BigDecimal remainingPaise = amount.subtract(roundedAmount); // Get the decimal part (paise)
//
//		if (remainingPaise.compareTo(new BigDecimal(0.5)) >= 0) {
//			roundedAmount = roundedAmount.add(BigDecimal.ONE); // Round up if paise is 50 or more
//		}
//		return roundedAmount;
//	

	public static BigDecimal round111(BigDecimal amount) {
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

}
