package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.controller.ErpRevController;
import com.mpcz.deposit_scheme.backend.api.domain.ApplicationStatus;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateAmountData;
import com.mpcz.deposit_scheme.backend.api.domain.ErpEstimateDomain;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.enums.ApplicationStatusEnum;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.ErpEstimateAmountException;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ErpRevRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateAmountRepository;
import com.mpcz.deposit_scheme.backend.api.repository.EstimateRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.ErpRevService;

@Service
public class ErpRevServiceIMP implements ErpRevService {

	@Autowired
	private ConsumerApplicationDetailService ConsumerApplicationDetailService;

	@Autowired
	private ErpRevRepository erpRevRepository;

	@Autowired
	private EstimateAmountRepository estimateAmountRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private MmkyPayAmountRespository mmkyPayAmountRespository;

	@Override
	public ErpRev save(String erpNo, String applicationNo, Long value) {

		ResponseEntity<String> exchange = null;
		ErpEstimateAmountData erpEstimateAmountData = null;
		BigDecimal jeReturnAmount = new BigDecimal(0.0);
		BigDecimal colonyOrSlum = new BigDecimal(0.0);
		RestTemplate rest = new RestTemplate();
		ErpRev erpRev = new ErpRev();
		JSONObject json = null;
		BigDecimal oldSupervisionAmount = new BigDecimal(0.0);

		Long l = null;
		Long individualOrGroupId = 10L;
		BigDecimal oldCgst = new BigDecimal(0.0);
		BigDecimal oldSgst = new BigDecimal(0.0);
		BigDecimal newColonyOrSlum = new BigDecimal(0.0);
		BigDecimal newCgst = new BigDecimal(0.0);
		BigDecimal newSgst = new BigDecimal(0.0);
		BigDecimal newKvaLoadAmt = new BigDecimal(0.0);
		BigDecimal newSuperVisionAmt = new BigDecimal(0.0);
		BigDecimal newEstimateAmount = new BigDecimal(0.0);
		BigDecimal remReturnAmt = new BigDecimal(0.0);
		BigDecimal oAndMReturnAmount = new BigDecimal(0.0);
		BigDecimal minusCost = new BigDecimal(0.0);

		try {
			ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo = ConsumerApplicationDetailService
					.findConsumerApplicationDetailByApplicationNo(applicationNo);
//			new Erp api pehle call krni hai Urjas wali api baad me call hogi  09-06-2025

			String url = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNo;
//			String url = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNo;
			HttpEntity<ErpRev> httpEntity = new HttpEntity<>(null);

			exchange = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);
			json = new JSONObject(exchange.getBody());
			if (json.get("statusCode").toString().equals("404")) {
				String url1 = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNo;
//				String url1 = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNo;
				exchange = rest.exchange(url1, HttpMethod.GET, httpEntity, String.class);
				json = new JSONObject(exchange.getBody());
			}
			if (json.get("statusCode").toString().equals("200")) {
				JSONArray jsonArray = json.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
							&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {

						if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
								&& !findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId()
										.equals(1L))

								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
										&& !findConsumerApplicationDetailByApplicationNo.getSchemeType()
												.getSchemeTypeId().equals(2L))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("KMY")
										&& !findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkName().equals("MKMY"))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("OYT")
										&& !findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkTypeId().equals(5L))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("RRTD")
										&& (findConsumerApplicationDetailByApplicationNo.getSchemeType()
												.getSchemeTypeId().equals(1L)
												|| findConsumerApplicationDetailByApplicationNo.getSchemeType()
														.getSchemeTypeId().equals(2L)))

								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
										&& findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkTypeId() == 8l)
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
										&& findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkTypeId() == 5l)) {

							erpRev.setSchemeCode("Scheme code not match");
							return erpRev;
						}

					}
				}
			}

			System.out.println(json);
			if (json.get("statusCode").toString().equals("200")) {
				JSONArray jsonArray = json.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {

					if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null)
						erpRev.setNewErpNo(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
					if (jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME") != null)
						erpRev.setLocation(jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME"));
					if (jsonArray.getJSONObject(i).getString("STATUS") != null)
						erpRev.setEstimateStatus(jsonArray.getJSONObject(i).getString("STATUS"));
					if (jsonArray.getJSONObject(i).getString("SUPERVISION_COST") != null) {

						newSuperVisionAmt = roundAmountCgstAndSgst(
								new BigDecimal(jsonArray.getJSONObject(i).getString("SUPERVISION_COST")));

						newCgst = roundAmountCgstAndSgst(newSuperVisionAmt.multiply(new BigDecimal(.09)));
						newSgst = roundAmountCgstAndSgst(newSuperVisionAmt.multiply(new BigDecimal(.09)));

						erpRev.setNewSupervisionAmt(newSuperVisionAmt);
						erpRev.setNewCgst(newCgst);
						erpRev.setNewSgst(newSgst);

					}
					if (jsonArray.getJSONObject(i).getString("ESTIMATED_COST") != null) {
						newEstimateAmount = roundAmountCgstAndSgst(
								new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST")));

						erpRev.setNewEstimateAmt(newEstimateAmount);
					}

					if (jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME") != null)
						erpRev.setApprovedBy(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME"));

					if (jsonArray.getJSONObject(i).getString("ESTIMATE_NO") != null)
						erpRev.setEstimatsenctionNo(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));

					if (jsonArray.getJSONObject(i).getString("LONG_NAME") != null)
						erpRev.setEstimateName(jsonArray.getJSONObject(i).getString("LONG_NAME"));

					if (jsonArray.getJSONObject(i).getString("ESTIMATE_DATE") != null)
						erpRev.setEstimateDate(jsonArray.getJSONObject(i).getString("ESTIMATE_DATE"));

					if (jsonArray.getJSONObject(i).getString("DESIG") != null)
						erpRev.setDesignation(jsonArray.getJSONObject(i).getString("DESIG"));

					if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
							&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {

						erpRev.setSchemeCode(jsonArray.getJSONObject(i).getString("SCHEMECODE"));

					}
					if (jsonArray.getJSONObject(i).getString("DISMENTALLING_COST") != null) {

						erpRev.setDismentalingCost(new BigDecimal(jsonArray.getJSONObject(i).getString("DISMENTALLING_COST")));

					}
					
					if(findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(1l) ||
							findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId().equals(6l)) {
						if(erpRev.getDismentalingCost().compareTo(BigDecimal.ZERO)<=0) {
							erpRev.setSchemeCode("As per guidelines, dismantling cost cannot be zero or negative hence the estimate is incorrect.");
							return erpRev;
						}
					}
					
					erpRev.setConsAppNo(applicationNo);

					Optional<ErpEstimateAmountData> erpEstimateAmountData1 = estimateAmountRepository
							.findById(findConsumerApplicationDetailByApplicationNo.getErpWorkFlowNumber());

					if (erpEstimateAmountData1.isPresent()) {
						erpEstimateAmountData = erpEstimateAmountData1.get();

						oldSupervisionAmount = erpEstimateAmountData.getSupervisionAmount();

						if (erpEstimateAmountData.getJeReturnAmount() != null) {
							jeReturnAmount = erpEstimateAmountData.getJeReturnAmount();
						}
						if (erpEstimateAmountData.getColonyOrSlum() != null) {
							colonyOrSlum = erpEstimateAmountData.getColonyOrSlum();
						}
						if (erpEstimateAmountData.getKvaLoad() != null) {
							erpRev.setOldkvaloadAmt(erpEstimateAmountData.getKvaLoad());
						}

						erpRev.setOldkWloadAmt(erpEstimateAmountData.getKwLoad());

						erpRev.setOldSupervision(oldSupervisionAmount);

						if (erpEstimateAmountData.getCgst() != null) {
							oldCgst = erpEstimateAmountData.getCgst();
							oldSgst = erpEstimateAmountData.getSgst();

							erpRev.setOldCgst(oldCgst);
							erpRev.setOldSgst(oldSgst);

							BigDecimal checkAmountNagetiveOrPositive = roundAmountCgstAndSgst(
									newCgst.subtract(oldCgst));

							if (checkAmountNagetiveOrPositive.compareTo(BigDecimal.ZERO) < 0) {
								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);
								System.out.println("Amount is negative");
							} else if (checkAmountNagetiveOrPositive.compareTo(BigDecimal.ZERO) >= 0) {

								erpRev.setRemCgst(roundAmountCgstAndSgst(newCgst.subtract(oldCgst)));
								erpRev.setRemSgst(roundAmountCgstAndSgst(newSgst.subtract(oldSgst)));
							}

						}
					}

//					LINES ADDED for save minus cost as o & m return amount 4-march-2025 

					if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
							&& findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId()
									.equals(1L))
							&& jsonArray.getJSONObject(i).getString("MINUS_COST") != null) {
						// agar goverment hai to return amount 0 liya jaayega code start 6-march-2025
						findConsumerApplicationDetailByApplicationNo.setoAndMReturnAmount(
								findConsumerApplicationDetailByApplicationNo.getIsAvedakGovernmentRevise().equals("Yes")
										? BigDecimal.ZERO
										: new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST")).abs());
						ConsumerApplicationDetailService
								.saveConsumerApplication(findConsumerApplicationDetailByApplicationNo);

						// code end 6-march-2025
					} else {
						findConsumerApplicationDetailByApplicationNo.setoAndMReturnAmount(BigDecimal.ZERO);
						ConsumerApplicationDetailService
								.saveConsumerApplication(findConsumerApplicationDetailByApplicationNo);
					}

//					above LINES ADDED for save minus cost as o & m return amount 4-march-2025 

					// These line added for adding remaining return amount
					if (findConsumerApplicationDetailByApplicationNo.getoAndMReturnAmount() != null) {
						oAndMReturnAmount = roundAmountCgstAndSgst(
								findConsumerApplicationDetailByApplicationNo.getoAndMReturnAmount());
						remReturnAmt = roundAmountCgstAndSgst(oAndMReturnAmount.subtract(jeReturnAmount));
						erpRev.setoAndMReturnAmt(oAndMReturnAmount);
						erpRev.setRemReturnAmt(remReturnAmt);
					}
					// These line added for adding remaining return amount

					if (findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId() == 2) {
						BigDecimal oldKvaLoad = BigDecimal.ZERO;
						if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId() == 8l) {
							MmkyPayAmount oldMmkyPayAmountDb = mmkyPayAmountRespository
									.findByConsumerApplicationNumber(applicationNo);

							BigDecimal oldGovMaffAmt = round11(oldMmkyPayAmountDb.getGovMafBill(), 2);
							BigDecimal oldMpmkvvclMaffAmt = round11(oldMmkyPayAmountDb.getMpmkMafBill(), 2);
							BigDecimal oldMkmyPayableAmt = round11(oldMmkyPayAmountDb.getPayableAmount(), 2);
							BigDecimal oldTotalAmountMkmy = round11(oldMmkyPayAmountDb.getTotalAmount(), 2);
							BigDecimal carryAmountByApplicant = round11(oldMmkyPayAmountDb.getCarryAmountByApplicant(),
									2);

							BigDecimal oldAvedanShulk = oldMmkyPayAmountDb.getAvedanShulk();
							BigDecimal oldSecurityDeposit = oldMmkyPayAmountDb.getSecurityDeposit();

							erpRev.setOldGovMafAmt(oldGovMaffAmt);
							erpRev.setOldMpmkMafAmt(oldMpmkvvclMaffAmt);
							erpRev.setOldMkmyPayAmt(oldMkmyPayableAmt);
							erpRev.setOldEstimate(oldTotalAmountMkmy);
							erpRev.setOldAvedanShulk(oldAvedanShulk);
							erpRev.setOldMkmySecurityDeposit(oldSecurityDeposit);
							erpRev.setOldCarryAmt(carryAmountByApplicant);

							BigDecimal newGovMaffAmt = round11(newEstimateAmount.multiply(new BigDecimal(.40)), 2);
							BigDecimal newMpmkvvclMaffAmt = round11(newEstimateAmount.multiply(new BigDecimal(.10)), 2);
							BigDecimal mpmkPayAmt = round11(
									newEstimateAmount.subtract(newGovMaffAmt).subtract(newMpmkvvclMaffAmt), 2);

							erpRev.setGovMafAmt(newGovMaffAmt);
							erpRev.setMpmkMafAmt(newMpmkvvclMaffAmt);
							erpRev.setMkmyPayAmt(mpmkPayAmt);

							erpRev.setRemGovMafAmt(newGovMaffAmt.subtract(oldGovMaffAmt));
							erpRev.setRemMpmkMafAmt(newMpmkvvclMaffAmt.subtract(oldMpmkvvclMaffAmt));
							erpRev.setRemMkmyPayAmt(
									mpmkPayAmt.subtract(oldTotalAmountMkmy.multiply(new BigDecimal(.50))));
							erpRev.setPayAmt(mpmkPayAmt.subtract(oldTotalAmountMkmy.multiply(new BigDecimal(.50))));

							erpRev.setRemEstimateAmt(newEstimateAmount.subtract(oldMmkyPayAmountDb.getTotalAmount()));
						} else {
							BigDecimal oldEstimateAmount = erpEstimateAmountData.getEstimateAmount();
							BigDecimal oldDepositAmountDb = erpEstimateAmountData.getDepositAmount();
							BigDecimal oldSupervisionAmountDb = erpEstimateAmountData.getSupervisionAmount();
							BigDecimal oldTotalBalanceDepositAmount = erpEstimateAmountData
									.getTotalBalanceDepositAmount();

							if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
									.getNatureOfWorkTypeId().equals(2L)
									|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
											.getNatureOfWorkTypeId().equals(5L)) {
								if (erpEstimateAmountData.getSspTotalAmount() != null) {

									erpRev.setOldSspMeterCost(erpEstimateAmountData.getSspMeterCost());
									erpRev.setOldSspRegCharge(erpEstimateAmountData.getSspRegistrationCharge());
									erpRev.setOldSspTotalAmount(erpEstimateAmountData.getSspTotalAmount());

									erpRev.setNewSspMeterCost(erpEstimateAmountData.getSspMeterCost());
									erpRev.setNewSspRegCharge(erpEstimateAmountData.getSspRegistrationCharge());
									erpRev.setNewSspTotalAmount(erpEstimateAmountData.getSspTotalAmount());

									erpRev.setRemSspRegCharge(BigDecimal.ZERO);
									erpRev.setRemSspRegCharge(BigDecimal.ZERO);
									erpRev.setRemSspTotalAmount(BigDecimal.ZERO);

								}
							}

//								erpRev.setOldPayableAmt(oldEstimateAmount);

							erpRev.setOldDeposit(oldDepositAmountDb);
							erpRev.setOldSupervision(oldSupervisionAmountDb);
							erpRev.setOldEstimate(oldEstimateAmount);
							erpRev.setOldJeReturnAmt(jeReturnAmount);
							erpRev.setOldColonyOrSlum(colonyOrSlum);
							erpRev.setOldPayableAmt(oldTotalBalanceDepositAmount);

//							minus cost code added on 27- august -2024 by monika rajpoot
							minusCost = roundAmountCgstAndSgst(
									new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST")));

//							 BigDecimal newDepositAmt = newEstimateAmount.subtract(newSuperVisionAmt).subtract(newCgst)
//										.subtract(newSgst);

							BigDecimal newDepositAmount = newEstimateAmount.subtract(newSuperVisionAmt)
									.subtract(newCgst).subtract(newSgst);

							BigDecimal newDepositAmt = newDepositAmount.subtract(minusCost);

//							y new estimate amount after adding minus cost init
							BigDecimal newEstimateAmnt = newDepositAmt.add(newSuperVisionAmt).add(newCgst).add(newSgst);

							erpRev.setNewMinusCost(minusCost);
//							minus cost code added on 27- august -2024 by monika rajpoot
							erpRev.setNewDepositAmt(newDepositAmt);
							erpRev.setRemmDepositAmt(
									roundAmountCgstAndSgst(newDepositAmt.subtract(oldDepositAmountDb)));
							erpRev.setRemSupervisionAmt(
									roundAmountCgstAndSgst(newSuperVisionAmt.subtract(oldSupervisionAmountDb)));
//							erpRev.setRemEstimateAmt(newEstimateAmount.subtract(oldEstimateAmount));
//							erpRev.setNewPayAmt(newEstimateAmount.add(oAndMReturnAmount));
//							erpRev.setPayAmt(roundAmountCgstAndSgst(newEstimateAmount.add(oAndMReturnAmount).subtract(oldTotalBalanceDepositAmount)));

//							erpRev.setRemEstimateAmt(newEstimateAmnt.subtract(oldEstimateAmount));
							erpRev.setRemEstimateAmt(newEstimateAmount.subtract(oldEstimateAmount));
							erpRev.setNewPayAmt(newEstimateAmnt.add(oAndMReturnAmount));

//							changes needed in given line
							erpRev.setPayAmt(roundAmountCgstAndSgst(
									newEstimateAmnt.add(oAndMReturnAmount).subtract(oldTotalBalanceDepositAmount)));

//							added by Monika Rajpoot 10-12-2025
							if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
									.getNatureOfWorkTypeId().equals(12L)) {
								erpRev.setPayAmt(roundAmountCgstAndSgst(
										newEstimateAmnt.add(oAndMReturnAmount).subtract(oldTotalBalanceDepositAmount
												.subtract(erpEstimateAmountData.getRegistrationCharges()))));
							}
// end 10-12-2025

							// Deposit Nature of work 3 Legal case
							if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
									.getNatureOfWorkTypeId().equals(3L)) {
								BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
								// jab getoAndMLoad 1500 ya usse kam ho
								if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
										.compareTo(new BigDecimal(1500)) <= 0) {

									newKvaLoadAmt = roundAmountCgstAndSgst(oAndMLoad.multiply(new BigDecimal(850)));
									erpRev.setNewKvaAmt(newKvaLoadAmt);

									BigDecimal remKvaLoad = roundAmountCgstAndSgst(
											newKvaLoadAmt.subtract(erpEstimateAmountData.getKvaLoad()));
									erpRev.setRemKvaAmt(remKvaLoad);
									// These line added for adding remaining return amount
//									BigDecimal newTotalEstimate = newEstimateAmount.add(remKvaLoad).add(remReturnAmt);

									BigDecimal newTotalEstimate = newEstimateAmnt.add(remKvaLoad).add(remReturnAmt);
									BigDecimal new_JeReturnAmount = BigDecimal.ZERO;
									
//									erpRev.setPayAmt(roundAmountCgstAndSgst(
//											newTotalEstimate.subtract(oldTotalBalanceDepositAmount)));
									
									erpRev.setNewPayAmt(roundAmountCgstAndSgst(
											erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst()).add(erpRev.getNewSgst()).add(erpRev.getNewDepositAmt())
													.add(new_JeReturnAmount).add(erpRev.getNewKvaAmt())));

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(erpRev.getRemCgst()).add(erpRev.getRemSgst()).add(erpRev.getRemmDepositAmt())
											.add(erpRev.getRemReturnAmt()).add(erpRev.getRemKvaAmt())));
								} else {

//									erpRev.setPayAmt(
//											roundAmountCgstAndSgst(newEstimateAmount.add(remReturnAmt).subtract(oldTotalBalanceDepositAmount)));
									
//									erpRev.setPayAmt(roundAmountCgstAndSgst(
//											newEstimateAmnt.add(remReturnAmt).subtract(oldTotalBalanceDepositAmount)));
//									above line commented due to wrong payamount 08-01-2026
									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(erpRev.getRemCgst()).add(erpRev.getRemSgst()).add(erpRev.getRemmDepositAmt())
											.add(erpRev.getRemReturnAmt()).add(erpRev.getRemKvaAmt())));

								}

								if (newKvaLoadAmt.compareTo(BigDecimal.ZERO) == 0) {
//									erpRev.setNewPayAmt(newEstimateAmount);
									erpRev.setNewPayAmt(newEstimateAmnt);
								} else {
//									erpRev.setNewPayAmt(newEstimateAmount.add(newKvaLoad));
									erpRev.setNewPayAmt(newEstimateAmnt.add(newKvaLoadAmt));
								}
								erpRev.setRemSupervisionAmt(
										roundAmountCgstAndSgst(newSuperVisionAmt.subtract(oldSupervisionAmountDb)));
							}

							// Deposit Nature of work 4 Illegal case
							else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
									.getNatureOfWorkTypeId().equals(4L)) {

								if (findConsumerApplicationDetailByApplicationNo.getColonyIllegalSelectionType()
										.equals("1")
										|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
												.getIndividualOrGroupId() == null
										|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
												.getIndividualOrGroupId() == 2L) {

//									erpRev.setPayAmt(
//											roundAmountCgstAndSgst(newEstimateAmount.add(remReturnAmt).subtract(oldTotalBalanceDepositAmount)));

									BigDecimal old_KvaLoadAmt = BigDecimal.ZERO;
									BigDecimal new_KvaLoadAmt = BigDecimal.ZERO;

									BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();

									if ((erpEstimateAmountData.getKvaLoad() != null)
											&& (erpEstimateAmountData.getKvaLoad().compareTo(new BigDecimal(0)) > 0)) {
										old_KvaLoadAmt = roundAmountCgstAndSgst(erpEstimateAmountData.getKvaLoad());
										erpRev.setOldkvaloadAmt(old_KvaLoadAmt);
									} else {
										erpRev.setOldkvaloadAmt(BigDecimal.ZERO);
//	29-09-2025 this line added because code is haulting for null value
										erpEstimateAmountData.setKvaLoad(BigDecimal.ZERO);
//	end
									}
									if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
											.compareTo(new BigDecimal(1500)) <= 0) {

										new_KvaLoadAmt = roundAmountCgstAndSgst(
												oAndMLoad.multiply(new BigDecimal(850)));

										old_KvaLoadAmt = roundAmountCgstAndSgst(erpEstimateAmountData.getKvaLoad());
										erpRev.setNewPayAmt(newEstimateAmnt.add(new_KvaLoadAmt));
										erpRev.setNewKvaAmt(new_KvaLoadAmt);
										erpRev.setOldkvaloadAmt(old_KvaLoadAmt);
										erpRev.setRemKvaAmt(new_KvaLoadAmt.subtract(old_KvaLoadAmt));
									} else {
										erpRev.setNewKvaAmt(BigDecimal.ZERO);
										erpRev.setRemKvaAmt(new_KvaLoadAmt.subtract(old_KvaLoadAmt));
									}
									BigDecimal new_JeReturnAmount = BigDecimal.ZERO;
//									erpRev.setPayAmt(roundAmountCgstAndSgst(newEstimateAmnt.add(remReturnAmt)
//											.add(erpRev.getRemKvaAmt()).subtract(oldTotalBalanceDepositAmount)));
									erpRev.setNewPayAmt(roundAmountCgstAndSgst(
											erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst()).add(erpRev.getNewSgst()).add(erpRev.getNewDepositAmt())
													.add(new_JeReturnAmount).add(erpRev.getNewKvaAmt())));

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(erpRev.getRemCgst()).add(erpRev.getRemSgst()).add(erpRev.getRemmDepositAmt())
											.add(erpRev.getRemReturnAmt()).add(erpRev.getRemKvaAmt())));
									
								} else if (findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
										.getIndividualOrGroupId() == 1L) {
									BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
									if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
											.compareTo(new BigDecimal(400)) <= 0) {
										newColonyOrSlum = roundAmountCgstAndSgst(
												oAndMLoad.multiply(new BigDecimal(15567)));
										erpRev.setNewColonyOrSlum(newColonyOrSlum);
										BigDecimal remColonyOrSlum = roundAmountCgstAndSgst(
												newColonyOrSlum.subtract(colonyOrSlum));
										erpRev.setRemColonyOrSlum(remColonyOrSlum);

										erpRev.setNewSupervisionAmt(null);
										erpRev.setNewCgst(null);
										erpRev.setNewSgst(null);
										erpRev.setNewDepositAmt(null);
										erpRev.setNewEstimateAmt(null);
										erpRev.setRemSupervisionAmt(null);
										erpRev.setRemCgst(null);
										erpRev.setRemSgst(null);
										erpRev.setRemEstimateAmt(null);
										erpRev.setRemmDepositAmt(null);
										erpRev.setOldSupervision(null);

										if (colonyOrSlum.compareTo(BigDecimal.ZERO) != 0) {
											erpRev.setNewPayAmt(remColonyOrSlum);
											erpRev.setRemColonyOrSlum(remColonyOrSlum);
											erpRev.setNewPayAmt(newColonyOrSlum);
											erpRev.setPayAmt(remColonyOrSlum);
										} else {
											erpRev.setNewPayAmt(remColonyOrSlum);
											erpRev.setPayAmt(roundAmountCgstAndSgst(
													remColonyOrSlum.subtract(oldTotalBalanceDepositAmount)));
										}
									} else {
//										erpRev.setNewPayAmt(newEstimateAmount.add(remReturnAmt));
//										erpRev.setPayAmt(roundAmountCgstAndSgst(newEstimateAmount.add(remReturnAmt)
//												.subtract(oldTotalBalanceDepositAmount)));

										erpRev.setNewPayAmt(newEstimateAmnt.add(remReturnAmt));
										erpRev.setPayAmt(roundAmountCgstAndSgst(newEstimateAmnt.add(remReturnAmt)
												.subtract(oldTotalBalanceDepositAmount)));
									}
								} else {
//									erpRev.setNewPayAmt(
//											newEstimateAmount.add(remReturnAmt));
//									erpRev.setPayAmt(
//											roundAmountCgstAndSgst(newEstimateAmount.add(remReturnAmt).subtract(oldTotalBalanceDepositAmount)));

									erpRev.setNewPayAmt(newEstimateAmnt.add(remReturnAmt));
									erpRev.setPayAmt(roundAmountCgstAndSgst(
											newEstimateAmnt.add(remReturnAmt).subtract(oldTotalBalanceDepositAmount)));
								}
							}

						}

					} else {

//						All key	for old 
						BigDecimal old_Cgst = BigDecimal.ZERO;
						BigDecimal old_Sgst = BigDecimal.ZERO;
						BigDecimal old_JeReturnAmount = BigDecimal.ZERO;
						BigDecimal old_KvaLoad = BigDecimal.ZERO;
						BigDecimal old_ColonyOrSlum = BigDecimal.ZERO;
						BigDecimal old_DepositAmount = BigDecimal.ZERO;
						BigDecimal old_security = BigDecimal.ZERO;

//						new key 
						BigDecimal new_JeReturnAmount = BigDecimal.ZERO;

						if (Objects.equals(findConsumerApplicationDetailByApplicationNo.getIsAvedakGovernmentRevise(),
								"Yes")) {
							new_JeReturnAmount = BigDecimal.ZERO;
						} else {
							new_JeReturnAmount = roundAmountCgstAndSgst(
									findConsumerApplicationDetailByApplicationNo.getoAndMReturnAmount());
						}

//						total supervision amount (liya hua pesa)
						BigDecimal old_totalBalanceSupervisionAmount = roundAmountCgstAndSgst(
								erpEstimateAmountData.getTotalBalanceSupervisionAmount());

//                       supervision amount 
						BigDecimal old_SupervisionAmount = erpEstimateAmountData.getSupervisionAmount();

//						cgst and sgst
						if (erpEstimateAmountData.getCgst() != null) {
							old_Cgst = erpEstimateAmountData.getCgst();
							old_Sgst = erpEstimateAmountData.getSgst();
						}

//						je  ka return amount	
						if (erpEstimateAmountData.getJeReturnAmount() != null) {
							old_JeReturnAmount = roundAmountCgstAndSgst(erpEstimateAmountData.getJeReturnAmount());
						}

//						kva load
						if (erpEstimateAmountData.getKvaLoad() != null) {
							old_KvaLoad = roundAmountCgstAndSgst(erpEstimateAmountData.getKvaLoad());
						}

//						colony or slum
						if (erpEstimateAmountData.getColonyOrSlum() != null) {
							old_ColonyOrSlum = roundAmountCgstAndSgst(erpEstimateAmountData.getColonyOrSlum());
						}

//						security deposit 
						if (erpEstimateAmountData.getDepositAmount() != null) {
							old_DepositAmount = roundAmountCgstAndSgst(erpEstimateAmountData.getDepositAmount());
						}

						if (erpEstimateAmountData.getSecurityDeposit() != null) {
							old_security = erpEstimateAmountData.getSecurityDeposit();
						}

//						old data always same of all applications 
						erpRev.setOldSupervision(old_SupervisionAmount);
						erpRev.setOldCgst(old_Cgst);
						erpRev.setOldSgst(old_Sgst);
						erpRev.setOldJeReturnAmt(old_JeReturnAmount);
						erpRev.setOldSecurityAmt(old_security);
						erpRev.setOldPayableAmt(old_totalBalanceSupervisionAmount);

						if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId() == 1l
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId() == 2l
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId() == 6l
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId() == 7l
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId() == 12l) {

							if (newSuperVisionAmt.compareTo(old_SupervisionAmount) < 0) {

//								

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);
								erpRev.setRemReturnAmt(new_JeReturnAmount.subtract(old_JeReturnAmount));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
										.add(erpRev.getNewCgst().add(erpRev.getNewSgst().add(new_JeReturnAmount)))));

								erpRev.setPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getRemReturnAmt())));

							} else {

//							

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(newCgst.subtract(oldCgst));
								erpRev.setRemSgst(newSgst.subtract(oldSgst));
								erpRev.setRemReturnAmt(
										roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
										.add(erpRev.getNewCgst().add(erpRev.getNewSgst().add(new_JeReturnAmount)))));

								erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(
										erpRev.getRemCgst().add(erpRev.getRemSgst().add(erpRev.getRemReturnAmt())))));

							}

						}

						// Supervision Nature of work 3 Legal case
						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(3L)) {

							BigDecimal old_KvaLoadAmt = BigDecimal.ZERO;
							BigDecimal new_KvaLoadAmt = BigDecimal.ZERO;

							if ((erpEstimateAmountData.getKvaLoad() != null)
									&& (erpEstimateAmountData.getKvaLoad().compareTo(new BigDecimal(0)) > 0)) {
								old_KvaLoadAmt = roundAmountCgstAndSgst(erpEstimateAmountData.getKvaLoad());
								erpRev.setOldkvaloadAmt(old_KvaLoadAmt);
							} else {
								erpRev.setOldkvaloadAmt(BigDecimal.ZERO);
							}

							BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();

							if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
									.compareTo(new BigDecimal(1500)) <= 0) {

								new_KvaLoadAmt = roundAmountCgstAndSgst(oAndMLoad.multiply(new BigDecimal(850)));

								erpRev.setNewKvaAmt(new_KvaLoadAmt);

							} else {
								erpRev.setNewKvaAmt(new BigDecimal(0.0));
							}
							erpRev.setRemKvaAmt(erpRev.getNewKvaAmt().subtract(erpRev.getOldkvaloadAmt()));

							if (newSuperVisionAmt.compareTo(old_SupervisionAmount) < 0) {

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);
								erpRev.setRemReturnAmt(
										roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(
										erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
												.add(new_JeReturnAmount).add(erpRev.getNewKvaAmt())));

								erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt()
										.add(erpRev.getRemReturnAmt()).add(erpRev.getRemKvaAmt())));

							} else {

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(newCgst.subtract(oldCgst));
								erpRev.setRemSgst(newSgst.subtract(oldSgst));
								erpRev.setRemReturnAmt(
										roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(
										erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
												.add(new_JeReturnAmount).add(erpRev.getNewKvaAmt())));

								erpRev.setPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
												.add(erpRev.getRemReturnAmt()).add(erpRev.getRemKvaAmt())));

							}

						}

						// Supervision Nature of work 4 Illegal case
						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(4L)) {

							if (findConsumerApplicationDetailByApplicationNo.getColonyIllegalSelectionType().equals("1")
									|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
											.getIndividualOrGroupId() == null
									|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
											.getIndividualOrGroupId() == 2L) {

								BigDecimal old_KvaLoadAmt = BigDecimal.ZERO;
								BigDecimal new_KvaLoadAmt = BigDecimal.ZERO;

								BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();

								if ((erpEstimateAmountData.getKvaLoad() != null)
										&& (erpEstimateAmountData.getKvaLoad().compareTo(new BigDecimal(0)) > 0)) {
									old_KvaLoadAmt = roundAmountCgstAndSgst(erpEstimateAmountData.getKvaLoad());
									erpRev.setOldkvaloadAmt(old_KvaLoadAmt);
								} else {
									erpRev.setOldkvaloadAmt(BigDecimal.ZERO);
//									29-09-2025 this line added because code is haulting for null value
									erpEstimateAmountData.setKvaLoad(BigDecimal.ZERO);
//									end
								}
								if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
										.compareTo(new BigDecimal(1500)) <= 0) {

									new_KvaLoadAmt = roundAmountCgstAndSgst(oAndMLoad.multiply(new BigDecimal(850)));

									old_KvaLoadAmt = roundAmountCgstAndSgst(erpEstimateAmountData.getKvaLoad());

									erpRev.setNewKvaAmt(new_KvaLoadAmt);
									erpRev.setOldkvaloadAmt(old_KvaLoadAmt);
									erpRev.setRemKvaAmt(new_KvaLoadAmt.subtract(old_KvaLoadAmt));
								} else {
									erpRev.setNewKvaAmt(BigDecimal.ZERO);
									erpRev.setRemKvaAmt(new_KvaLoadAmt.subtract(old_KvaLoadAmt));
								}

								if (newSuperVisionAmt.compareTo(old_SupervisionAmount) < 0) {

									erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
									erpRev.setRemCgst(BigDecimal.ZERO);
									erpRev.setRemSgst(BigDecimal.ZERO);
									erpRev.setRemReturnAmt(
											roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

									erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
											.add(erpRev.getNewCgst()).add(erpRev.getNewSgst()).add(new_JeReturnAmount)
											.add(erpRev.getNewKvaAmt())));

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt()
											.add(erpRev.getRemReturnAmt()).add(erpRev.getRemKvaAmt())));

								} else {

									erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
									erpRev.setRemCgst(newCgst.subtract(oldCgst));
									erpRev.setRemSgst(newSgst.subtract(oldSgst));
									erpRev.setRemReturnAmt(
											roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

									erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
											.add(erpRev.getNewCgst()).add(erpRev.getNewSgst()).add(new_JeReturnAmount)
											.add(erpRev.getNewKvaAmt())));

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt()
											.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
											.add(erpRev.getRemReturnAmt()).add(erpRev.getRemKvaAmt())));

								}

							} else if (findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
									.getIndividualOrGroupId() == 1L) {

								BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
								if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
										.compareTo(new BigDecimal(400)) <= 0) {

									BigDecimal new_ColonyOrSlum = roundAmountCgstAndSgst(
											oAndMLoad.multiply(new BigDecimal(15567)));

									BigDecimal rem_ColonyOrSlum = roundAmountCgstAndSgst(
											new_ColonyOrSlum.subtract(old_ColonyOrSlum));

									erpRev.setNewSupervisionAmt(null);
									erpRev.setNewCgst(null);
									erpRev.setNewSgst(null);
									erpRev.setNewDepositAmt(null);
									erpRev.setNewEstimateAmt(null);
									erpRev.setRemSupervisionAmt(null);
									erpRev.setRemCgst(null);
									erpRev.setRemSgst(null);
									erpRev.setRemEstimateAmt(null);
									erpRev.setRemmDepositAmt(null);
									erpRev.setOldSupervision(null);

									erpRev.setNewSupervisionAmt(null);
									erpRev.setNewCgst(null);
									erpRev.setNewSgst(null);

									erpRev.setNewColonyOrSlum(new_ColonyOrSlum);
									erpRev.setRemColonyOrSlum(rem_ColonyOrSlum);
									erpRev.setOldColonyOrSlum(old_ColonyOrSlum);

									erpRev.setNewPayAmt(new_ColonyOrSlum);
									erpRev.setPayAmt(rem_ColonyOrSlum);

									erpRev.setRemReturnAmt(
											roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

									erpRev.setNewPayAmt(rem_ColonyOrSlum.add(erpRev.getRemReturnAmt()));
									erpRev.setPayAmt(erpRev.getRemColonyOrSlum().add(erpRev.getRemReturnAmt()));

								} else {

								}
							}

						}

						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(5L)) {

							String jeLoadUnitKwYaKva = findConsumerApplicationDetailByApplicationNo
									.getJeLoadUnitKwYaKva();

							BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
							if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
									.compareTo(new BigDecimal(10)) <= 0) {

								BigDecimal new_securityAmt = oAndMLoad.multiply(new BigDecimal(200));

								erpRev.setNewSecurityAmt(new_securityAmt);
								erpRev.setRemSecurityAmt(
										erpRev.getNewSecurityAmt().subtract(erpRev.getOldSecurityAmt()));

							} else {
								BigDecimal new_securityAmt = oAndMLoad.multiply(new BigDecimal(400));

								erpRev.setNewSecurityAmt(new_securityAmt);
								erpRev.setRemSecurityAmt(
										erpRev.getNewSecurityAmt().subtract(erpRev.getOldSecurityAmt()));

							}

							if (newSuperVisionAmt.compareTo(old_SupervisionAmount) < 0) {

//								

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);
								erpRev.setRemReturnAmt(
										roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

								erpRev.setNewPayAmt(
										roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst())
												.add(erpRev.getNewSgst()).add(erpRev.getRemSecurityAmt())));

								erpRev.setPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getRemSecurityAmt())));

							} else {

//							

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(newCgst.subtract(oldCgst));
								erpRev.setRemSgst(newSgst.subtract(oldSgst));
								erpRev.setRemReturnAmt(
										roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
										.add(erpRev.getNewCgst()).add(erpRev.getNewSgst()).add(new_JeReturnAmount)));

								erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(
										erpRev.getRemCgst().add(erpRev.getRemSgst().add(erpRev.getRemSecurityAmt())))));

							}

						}
						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(10L)) {
							BigDecimal kvaAmount = BigDecimal.ZERO;
							BigDecimal multiply = BigDecimal.ZERO;
							double upperRound  =0.0;
							
							BigDecimal getoAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
		
						   
							String getoAndMLoadUnit = findConsumerApplicationDetailByApplicationNo.getoAndMLoadUnit();
							
							
							if(getoAndMLoadUnit.equalsIgnoreCase("kw")) {
//								convertion of KVA
								 multiply = getoAndMLoad.multiply(new BigDecimal(1.25));
								 
								  upperRound = upperRound(multiply.doubleValue(),0);
								 
								 kvaAmount = new BigDecimal(upperRound).multiply(new BigDecimal(1260));
							}else {
								kvaAmount = getoAndMLoad.multiply(new BigDecimal(1260));
								
							}
							     
							     
							if (newSuperVisionAmt.compareTo(old_SupervisionAmount) < 0) {

//								

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);
								erpRev.setRemReturnAmt(
										roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));

								
								erpRev.setNewKvaAmt(kvaAmount);
								erpRev.setRemKvaAmt(kvaAmount.subtract(old_KvaLoad));
								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
										.add(erpRev.getNewCgst()).add(erpRev.getNewSgst()).add(new_JeReturnAmount).add(kvaAmount)));
								erpRev.setPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getRemKvaAmt())));

							} else {

//							

								erpRev.setRemSupervisionAmt(newSuperVisionAmt.subtract(old_SupervisionAmount));
								erpRev.setRemCgst(newCgst.subtract(oldCgst));
								erpRev.setRemSgst(newSgst.subtract(oldSgst));
								erpRev.setRemReturnAmt(
										roundAmountCgstAndSgst(new_JeReturnAmount.subtract(old_JeReturnAmount)));
								erpRev.setNewKvaAmt(kvaAmount);

								erpRev.setRemKvaAmt(kvaAmount.subtract(old_KvaLoad));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
										.add(erpRev.getNewCgst()).add(erpRev.getNewSgst()).add(new_JeReturnAmount).add(kvaAmount)));

								erpRev.setPayAmt(
										roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(
										erpRev.getRemCgst()).add(erpRev.getRemSgst()).add(erpRev.getRemKvaAmt())));

							}

							
						}

//					    erpRev.setNewPayAmt(newSuperVisionAmt.add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
//						.add(oAndMReturnAmount));
//						erpRev.setNewPayAmt(newSuperVisionAmt.add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
//									.add(newKvaLoad).add(oAndMReturnAmount));

					}

				}
			}
//			17-Oct-2024 start

			if (erpRev.getPayAmt() != null && erpRev.getRemCgst() != null
					&& (erpRev.getPayAmt().compareTo(BigDecimal.ZERO) <= 0)
					&& (erpRev.getRemCgst().compareTo(BigDecimal.ZERO) <= 0)) {

				erpRev.setConsumerRefundableAmnt(erpRev.getPayAmt());
			}

//			else {
//				erpRev.setConsumerRefundableAmnt(payAmt.add(remCgstSgst));
//			}

//			17-Oct-2024 end	
			if (value == 2L) {

				ErpRev save = erpRevRepository.save(erpRev);

				if (save != null) {
					ApplicationStatus appStatusDb = null;

					if (erpRev.getPayAmt().compareTo(BigDecimal.ZERO) < 0) {
						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
					} else {
						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.REMAING_DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());
					}
					findConsumerApplicationDetailByApplicationNo.setApplicationStatus(appStatusDb);
					findConsumerApplicationDetailByApplicationNo.setErpVersion("V2");
					findConsumerApplicationDetailByApplicationNo.setRevisedErpNumber(erpNo);
					ConsumerApplicationDetail saveConsumerApplication = ConsumerApplicationDetailService
							.saveConsumerApplication(findConsumerApplicationDetailByApplicationNo);
					findConsumerApplicationDetailByApplicationNo.setApplicationStatus(appStatusDb);

					return save;

				}
			} else {
				return erpRev;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static BigDecimal round11(BigDecimal value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = value;
		bd = bd.setScale(2, RoundingMode.CEILING);
		return bd;
	}

	@Override
	public ResponseEntity<?> getConsumerApplication(String consumerApp) {
		Response response = new Response();
		ErpRev findByConsAppNo = erpRevRepository.findByConsAppNo(consumerApp);

		if (findByConsAppNo == null) {
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage("Application no. not found in Revised ERP Table");
			return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
		}
		response.setCode(HttpCode.OK);
		response.setMessage("Data Retrieved Successfully !!!!!!");
		response.setList(Arrays.asList(findByConsAppNo));
		return ResponseEntity.ok().header(ResponseMessage.APPLICATION_TYPE_JSON).body(response);
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
	public ErpRev Dynamic(String erpNo, String applicationNo, Long value) {
		// TODO Auto-generated method stub
		return null;
	}

//	dyanamic payment ke liye
	public ErpRev Dynamic1(String erpNo, String applicationNo, Long value) {

		ResponseEntity<String> exchange = null;

		BigDecimal jeReturnAmount = new BigDecimal(0.0);
		BigDecimal colonyOrSlum = new BigDecimal(0.0);
		RestTemplate rest = new RestTemplate();
		ErpRev erpRev = new ErpRev();
		JSONObject json = null;
		BigDecimal oldSupervisionAmount = new BigDecimal(0.0);

		Long l = null;
		Long individualOrGroupId = 10L;
		BigDecimal oldCgst = new BigDecimal(0.0);
		BigDecimal oldSgst = new BigDecimal(0.0);
		BigDecimal newColonyOrSlum = new BigDecimal(0.0);
		BigDecimal newCgst = new BigDecimal(0.0);
		BigDecimal newSgst = new BigDecimal(0.0);
		BigDecimal newKvaLoadAmt = new BigDecimal(0.0);
		BigDecimal newSuperVisionAmt = new BigDecimal(0.0);
		BigDecimal newEstimateAmount = new BigDecimal(0.0);
		BigDecimal remReturnAmt = new BigDecimal(0.0);
		BigDecimal oAndMReturnAmount = new BigDecimal(0.0);
		BigDecimal minusCost = new BigDecimal(0.0);

		try {
			ConsumerApplicationDetail findConsumerApplicationDetailByApplicationNo = ConsumerApplicationDetailService
					.findConsumerApplicationDetailByApplicationNo(applicationNo);

			String url = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/" + erpNo;

			HttpEntity<ErpRev> httpEntity = new HttpEntity<>(null);

			exchange = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);
			json = new JSONObject(exchange.getBody());
			if (json.get("statusCode").toString().equals("404")) {
				String url1 = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/" + erpNo;
				exchange = rest.exchange(url1, HttpMethod.GET, httpEntity, String.class);
				json = new JSONObject(exchange.getBody());
			}
			if (json.get("statusCode").toString().equals("200")) {
				JSONArray jsonArray = json.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
							&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {

						if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
								&& !findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId()
										.equals(1L))

								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
										&& !findConsumerApplicationDetailByApplicationNo.getSchemeType()
												.getSchemeTypeId().equals(2L))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("KMY")
										&& !findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkName().equals("MKMY"))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("OYT")
										&& !findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkTypeId().equals(5L))
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("RRTD")
										&& (findConsumerApplicationDetailByApplicationNo.getSchemeType()
												.getSchemeTypeId().equals(1L)
												|| findConsumerApplicationDetailByApplicationNo.getSchemeType()
														.getSchemeTypeId().equals(2L)))

								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("DEPOSITE")
										&& findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkTypeId() == 8l)
								|| (jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
										&& findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
												.getNatureOfWorkTypeId() == 5l)) {

							erpRev.setSchemeCode("Scheme code not match");
							return erpRev;
						}

					}
				}
			}

			System.out.println(json);
			if (json.get("statusCode").toString().equals("200")) {
				JSONArray jsonArray = json.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {

					if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null)
						erpRev.setNewErpNo(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
					if (jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME") != null)
						erpRev.setLocation(jsonArray.getJSONObject(i).getString("ORGANIZATION_NAME"));
					if (jsonArray.getJSONObject(i).getString("STATUS") != null)
						erpRev.setEstimateStatus(jsonArray.getJSONObject(i).getString("STATUS"));
					if (jsonArray.getJSONObject(i).getString("SUPERVISION_COST") != null) {

						newSuperVisionAmt = roundAmountCgstAndSgst(
								new BigDecimal(jsonArray.getJSONObject(i).getString("SUPERVISION_COST")));

						newCgst = roundAmountCgstAndSgst(newSuperVisionAmt.multiply(new BigDecimal(.09)));
						newSgst = roundAmountCgstAndSgst(newSuperVisionAmt.multiply(new BigDecimal(.09)));

						erpRev.setNewSupervisionAmt(newSuperVisionAmt);
						erpRev.setNewCgst(newCgst);
						erpRev.setNewSgst(newSgst);

					}
					if (jsonArray.getJSONObject(i).getString("ESTIMATED_COST") != null) {
						newEstimateAmount = roundAmountCgstAndSgst(
								new BigDecimal(jsonArray.getJSONObject(i).getString("ESTIMATED_COST")));

						erpRev.setNewEstimateAmt(newEstimateAmount);

						BigDecimal supCS = erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst())
								.add(erpRev.getNewSgst());

						erpRev.setNewDepositAmt(erpRev.getNewEstimateAmt().subtract(supCS));
					}

					if (jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME") != null)
						erpRev.setApprovedBy(jsonArray.getJSONObject(i).getString("APPROVED_BY_NAME"));

					if (jsonArray.getJSONObject(i).getString("MINUS_COST") != null)
						erpRev.setNewMinusCost(roundAmountCgstAndSgst(
								new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST"))));

					if (jsonArray.getJSONObject(i).getString("ESTIMATE_NO") != null)
						erpRev.setEstimatsenctionNo(jsonArray.getJSONObject(i).getString("ESTIMATE_NO"));

					if (jsonArray.getJSONObject(i).getString("LONG_NAME") != null)
						erpRev.setEstimateName(jsonArray.getJSONObject(i).getString("LONG_NAME"));

					if (jsonArray.getJSONObject(i).getString("ESTIMATE_DATE") != null)
						erpRev.setEstimateDate(jsonArray.getJSONObject(i).getString("ESTIMATE_DATE"));

					if (jsonArray.getJSONObject(i).getString("DESIG") != null)
						erpRev.setDesignation(jsonArray.getJSONObject(i).getString("DESIG"));

					if (jsonArray.getJSONObject(i).getString("SCHEMECODE") != null
							&& jsonArray.getJSONObject(i).getString("PROJECT_TYPE") != null) {

						erpRev.setSchemeCode(jsonArray.getJSONObject(i).getString("SCHEMECODE"));

					}

					erpRev.setConsAppNo(applicationNo);

					String erpVersion = findConsumerApplicationDetailByApplicationNo.getErpVersion();
					Integer charAt = Integer.valueOf(erpVersion.charAt(1)) - 1;

					String olderpVersion = "V" + charAt;

					ErpRev oldErpDetailss = erpRevRepository.findByConsAppNoAndNewErpNoAndErpVersion(applicationNo,
							erpNo, olderpVersion);

					oldSupervisionAmount = oldErpDetailss.getNewSupervisionAmt();

					erpRev.setOldSupervision(oldSupervisionAmount);

					if (oldErpDetailss.getNewEstimateAmt() != null) {
						BigDecimal oldEstimateAmount = oldErpDetailss.getNewEstimateAmt();
						erpRev.setOldEstimate(oldEstimateAmount);
					}
					if (oldErpDetailss.getNewDepositAmt() != null) {
						BigDecimal oldDepositAmountDb = oldErpDetailss.getNewDepositAmt();
						erpRev.setOldDeposit(oldDepositAmountDb);
					}

					if (oldErpDetailss.getNewMinusCost() != null) {
//								jeReturnAmount = erpEstimateAmountData.getJeReturnAmount();
						jeReturnAmount = oldErpDetailss.getNewMinusCost();
						erpRev.setOldJeReturnAmt(jeReturnAmount);
					}
					if (oldErpDetailss.getNewColonyOrSlum() != null) {
						colonyOrSlum = oldErpDetailss.getNewColonyOrSlum();
						erpRev.setOldColonyOrSlum(colonyOrSlum);
					}
					if (oldErpDetailss.getNewKvaAmt() != null) {
						erpRev.setOldkvaloadAmt(oldErpDetailss.getNewKvaAmt());
					}

					if (oldErpDetailss.getNewKwAmt() != null) {
						erpRev.setOldkWloadAmt(oldErpDetailss.getNewKwAmt());
					}
					if (oldErpDetailss.getOldColonyOrSlum() != null) {
						erpRev.setOldColonyOrSlum(oldErpDetailss.getOldColonyOrSlum());
					}

					erpRev.setOldPayableAmt(oldErpDetailss.getOldPayableAmt());

					if (oldErpDetailss.getNewCgst() != null) {
						oldCgst = oldErpDetailss.getNewCgst();
						oldSgst = oldErpDetailss.getNewSgst();

						erpRev.setOldCgst(oldCgst);
						erpRev.setOldSgst(oldSgst);

						BigDecimal checkAmountNagetiveOrPositive = roundAmountCgstAndSgst(newCgst.subtract(oldCgst));

						if (checkAmountNagetiveOrPositive.compareTo(BigDecimal.ZERO) < 0) {
							erpRev.setRemCgst(BigDecimal.ZERO);
							erpRev.setRemSgst(BigDecimal.ZERO);
							System.out.println("Amount is negative");
						} else if (checkAmountNagetiveOrPositive.compareTo(BigDecimal.ZERO) >= 0) {

							erpRev.setRemCgst(roundAmountCgstAndSgst(newCgst.subtract(oldCgst)));
							erpRev.setRemSgst(roundAmountCgstAndSgst(newSgst.subtract(oldSgst)));
						}

					}

					if ((jsonArray.getJSONObject(i).getString("SCHEMECODE").equals("SCCW")
							&& findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId()
									.equals(1L))
							&& jsonArray.getJSONObject(i).getString("MINUS_COST") != null) {

						findConsumerApplicationDetailByApplicationNo.setoAndMReturnAmount(

								findConsumerApplicationDetailByApplicationNo.getIsAvedakGovernmentRevise().equals("Yes")
										? BigDecimal.ZERO
										: new BigDecimal(jsonArray.getJSONObject(i).getString("MINUS_COST")).abs());

						ConsumerApplicationDetailService
								.saveConsumerApplication(findConsumerApplicationDetailByApplicationNo);

					} else {
						findConsumerApplicationDetailByApplicationNo.setoAndMReturnAmount(BigDecimal.ZERO);
						ConsumerApplicationDetailService
								.saveConsumerApplication(findConsumerApplicationDetailByApplicationNo);
					}

					// These line added for adding remaining return amount
					if (findConsumerApplicationDetailByApplicationNo.getoAndMReturnAmount() != null) {
						oAndMReturnAmount = roundAmountCgstAndSgst(
								findConsumerApplicationDetailByApplicationNo.getoAndMReturnAmount());
						remReturnAmt = roundAmountCgstAndSgst(oAndMReturnAmount.subtract(jeReturnAmount));
						erpRev.setoAndMReturnAmt(oAndMReturnAmount);
						erpRev.setRemReturnAmt(remReturnAmt);
					}

//					Deposit scheme ke liye

					if (findConsumerApplicationDetailByApplicationNo.getSchemeType().getSchemeTypeId() == 2) {
						BigDecimal oldKvaLoad = BigDecimal.ZERO;

//						common feild sabke liye same
						erpRev.setRemEstimateAmt(erpRev.getNewEstimateAmt().subtract(erpRev.getOldEstimate()));

						// Deposit Nature of work 3 Legal case
						if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType().getNatureOfWorkTypeId()
								.equals(3L)) {
							BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();

							if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
									.compareTo(new BigDecimal(1500)) <= 0) {

								newKvaLoadAmt = roundAmountCgstAndSgst(oAndMLoad.multiply(new BigDecimal(850))); // ISKO
																													// SUPPLY
																													// AFFORDING
																													// CHARGES
																													// BOLTE
																													// HAI
								erpRev.setNewKvaAmt(newKvaLoadAmt);

								BigDecimal remKvaLoad = roundAmountCgstAndSgst(
										newKvaLoadAmt.subtract(oldErpDetailss.getNewKvaAmt()));
								erpRev.setRemKvaAmt(remKvaLoad);

								BigDecimal newTotalEstimate = erpRev.getNewEstimateAmt().add(remKvaLoad)
										.add(remReturnAmt);

								if (findConsumerApplicationDetailByApplicationNo.getIsAvedakGovernmentRevise()
										.equals("Yes")) {

									if ((erpRev.getNewSupervisionAmt().compareTo(erpRev.getOldSupervision())) <= 0) {

										erpRev.setRemCgst(BigDecimal.ZERO);
										erpRev.setRemSgst(BigDecimal.ZERO);

										erpRev.setPayAmt(erpRev.getRemmDepositAmt().add(erpRev.getRemSupervisionAmt())
												.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
												.add(erpRev.getRemKvaAmt()));

										erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getRemColonyOrSlum()));
									} else {
										erpRev.setPayAmt(erpRev.getRemmDepositAmt().add(erpRev.getRemSupervisionAmt())
												.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
												.add(erpRev.getRemKvaAmt()));

										erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getRemColonyOrSlum()));
									}

								} else {
									if ((erpRev.getNewSupervisionAmt().compareTo(erpRev.getOldSupervision())) <= 0) {
										erpRev.setRemCgst(BigDecimal.ZERO);
										erpRev.setRemSgst(BigDecimal.ZERO);
										erpRev.setPayAmt(erpRev.getRemmDepositAmt().add(erpRev.getRemSupervisionAmt())
												.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
												.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt()));

										erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getRemColonyOrSlum()));
									} else {
										erpRev.setPayAmt(erpRev.getRemmDepositAmt().add(erpRev.getRemSupervisionAmt())
												.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
												.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt()));

										erpRev.setNewPayAmt(erpRev.getNewDepositAmt().add(erpRev.getNewSupervisionAmt())
												.add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
												.add(erpRev.getNewKvaAmt()).add(erpRev.getNewMinusCost()));
									}

								}

							} else {

								erpRev.setPayAmt(erpRev.getRemEstimateAmt().add(erpRev.getRemCgst())
										.add(erpRev.getRemSgst()).add(erpRev.getRemReturnAmt()));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getRemColonyOrSlum()));

							}

						}

						// Deposit Nature of work 4 Illegal case
						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(4L)) {
							// 1= Declared and 2= Undeclared
							if (findConsumerApplicationDetailByApplicationNo.getColonyIllegalSelectionType().equals("1")
									|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
											.getIndividualOrGroupId() == null
									|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
											.getIndividualOrGroupId() == 2L) {

								BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
								BigDecimal newkvaAmt = oAndMLoad.multiply(new BigDecimal(850));

								erpRev.setNewKvaAmt(newkvaAmt);

								BigDecimal remKvaLoad = roundAmountCgstAndSgst(
										newKvaLoadAmt.subtract(oldErpDetailss.getNewKvaAmt()));
								erpRev.setRemKvaAmt(remKvaLoad);

								if (erpRev.getNewSupervisionAmt().compareTo(erpRev.getOldSupervision()) < 0) {

									erpRev.setRemCgst(BigDecimal.ZERO);
									erpRev.setRemSgst(BigDecimal.ZERO);

									erpRev.setPayAmt(roundAmountCgstAndSgst(
											erpRev.getNewDepositAmt().add(erpRev.getNewSupervisionAmt())
													.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
													.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt())));

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemColonyOrSlum()));

								} else {

									erpRev.setPayAmt(roundAmountCgstAndSgst(
											erpRev.getRemmDepositAmt().add(erpRev.getRemSupervisionAmt())
													.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
													.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt())));

									erpRev.setNewPayAmt(roundAmountCgstAndSgst(
											erpRev.getNewDepositAmt().add(erpRev.getNewSupervisionAmt())
													.add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
													.add(erpRev.getNewKvaAmt()).add(erpRev.getNewMinusCost())));

								}

							} else if (findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
									.getIndividualOrGroupId() == 1L) {
								BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
								if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
										.compareTo(new BigDecimal(400)) <= 0) {

									newColonyOrSlum = roundAmountCgstAndSgst(oAndMLoad.multiply(new BigDecimal(15567)));

									erpRev.setNewColonyOrSlum(newColonyOrSlum);
									erpRev.setOldkvaloadAmt(oldErpDetailss.getNewColonyOrSlum());

									BigDecimal remColonyOrSlum = roundAmountCgstAndSgst(
											erpRev.getNewColonyOrSlum().subtract(oldErpDetailss.getNewColonyOrSlum()));

									erpRev.setRemColonyOrSlum(remColonyOrSlum);

									erpRev.setNewSupervisionAmt(null);
									erpRev.setNewCgst(null);
									erpRev.setNewSgst(null);
									erpRev.setNewDepositAmt(null);
									erpRev.setNewEstimateAmt(null);
									erpRev.setRemSupervisionAmt(null);
									erpRev.setRemCgst(null);
									erpRev.setRemSgst(null);
									erpRev.setRemEstimateAmt(null);
									erpRev.setRemmDepositAmt(null);
									erpRev.setOldSupervision(null);

									if (erpRev.getRemColonyOrSlum().compareTo(BigDecimal.ZERO) <= 0) {

									} else {
										erpRev.setNewPayAmt(roundAmountCgstAndSgst(
												erpRev.getRemColonyOrSlum().add(erpRev.getRemReturnAmt())));
										erpRev.setPayAmt(roundAmountCgstAndSgst(
												erpRev.getRemColonyOrSlum().add(erpRev.getRemReturnAmt())));
									}
								}
							} else {
//								excetion throw karwana hai	
							}
						}
//						4th case end 
//						nature of work 1 or 6 ke liye
						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(1L)
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId().equals(2L)) {

							erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemmDepositAmt()
									.add(erpRev.getRemSupervisionAmt()).add(erpRev.getRemCgst())
									.add(erpRev.getRemSgst()).add(erpRev.getRemReturnAmt())));

							erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewDepositAmt()
									.add(erpRev.getNewSupervisionAmt()).add(erpRev.getNewCgst())
									.add(erpRev.getNewSgst()).add(erpRev.getNewMinusCost())));
						}

					} else {

//						scheme type 1 ke liye

//nature of work 1,6,7,12 ke liye

						if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId() == 1l
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId() == 6l
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId() == 7l
								|| findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
										.getNatureOfWorkTypeId() == 12l) {

							if (erpRev.getNewSupervisionAmt().compareTo(erpRev.getOldSupervision()) < 0) {

//								

								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);

								erpRev.setRemReturnAmt(erpRev.getNewMinusCost().subtract(erpRev.getOldJeReturnAmt()));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt().add(
										erpRev.getNewCgst().add(erpRev.getNewSgst().add(erpRev.getNewMinusCost())))));

								erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(
										erpRev.getRemCgst().add(erpRev.getRemSgst().add(erpRev.getRemReturnAmt())))));

							} else {

//							

								erpRev.setRemReturnAmt(erpRev.getNewMinusCost().subtract(erpRev.getOldJeReturnAmt()));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt().add(
										erpRev.getNewCgst().add(erpRev.getNewSgst().add(erpRev.getNewMinusCost())))));

								erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(
										erpRev.getRemCgst().add(erpRev.getRemSgst().add(erpRev.getRemReturnAmt())))));

							}

						}

						// Supervision Nature of work 3 Legal case
						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(3L)) {

							BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();

							if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
									.compareTo(new BigDecimal(1500)) <= 0) {

								erpRev.setNewKvaAmt(roundAmountCgstAndSgst(oAndMLoad.multiply(new BigDecimal(850))));

							} else {
								erpRev.setNewKvaAmt(new BigDecimal(0.0));
							}
							erpRev.setRemKvaAmt(erpRev.getNewKvaAmt().subtract(erpRev.getOldkvaloadAmt()));

							if (erpRev.getNewSupervisionAmt().compareTo(erpRev.getOldSupervision()) < 0) {

								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);

								erpRev.setRemReturnAmt(roundAmountCgstAndSgst(erpRev.getRemReturnAmt()));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
												.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt())));

								erpRev.setPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
												.add(erpRev.getNewKvaAmt()).add(erpRev.getNewMinusCost())));

							} else {

								erpRev.setRemReturnAmt(roundAmountCgstAndSgst(erpRev.getRemReturnAmt()));

								erpRev.setNewPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
												.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt())));

								erpRev.setPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
												.add(erpRev.getNewKvaAmt()).add(erpRev.getNewMinusCost())));

							}

						}

						// Supervision Nature of work 4 colony elictification Illegal case
						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(4L)) {

							if (findConsumerApplicationDetailByApplicationNo.getColonyIllegalSelectionType().equals("1")
									|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
											.getIndividualOrGroupId() == null
									|| findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
											.getIndividualOrGroupId() == 2L) {
								// 1= Declared and 2= Undeclared

								BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
								BigDecimal newkvaAmt = oAndMLoad.multiply(new BigDecimal(850));

								erpRev.setNewKvaAmt(newkvaAmt);

								BigDecimal remKvaLoad = roundAmountCgstAndSgst(
										newKvaLoadAmt.subtract(oldErpDetailss.getNewKvaAmt()));
								erpRev.setRemKvaAmt(remKvaLoad);

								if (erpRev.getNewSupervisionAmt().compareTo(erpRev.getOldSupervision()) < 0) {

									erpRev.setRemCgst(BigDecimal.ZERO);
									erpRev.setRemSgst(BigDecimal.ZERO);

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
											.add(erpRev.getNewCgst()).add(erpRev.getNewCgst())
											.add(erpRev.getNewKvaAmt()).add(erpRev.getNewMinusCost())));

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
											.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
											.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt())));

								} else {

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
											.add(erpRev.getNewCgst()).add(erpRev.getNewCgst())
											.add(erpRev.getNewKvaAmt()).add(erpRev.getNewMinusCost())));

									erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt()
											.add(erpRev.getRemCgst()).add(erpRev.getRemSgst())
											.add(erpRev.getRemKvaAmt()).add(erpRev.getRemReturnAmt())));

								}

							} else if (findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
									.getIndividualOrGroupId() == 1L) {
								BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
								if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
										.compareTo(new BigDecimal(400)) <= 0) {

									newColonyOrSlum = roundAmountCgstAndSgst(oAndMLoad.multiply(new BigDecimal(15567)));

									erpRev.setNewColonyOrSlum(newColonyOrSlum);
									erpRev.setOldkvaloadAmt(oldErpDetailss.getNewColonyOrSlum());

									BigDecimal remColonyOrSlum = roundAmountCgstAndSgst(
											erpRev.getNewColonyOrSlum().subtract(oldErpDetailss.getNewColonyOrSlum()));

									erpRev.setRemColonyOrSlum(remColonyOrSlum);

									erpRev.setNewSupervisionAmt(null);
									erpRev.setNewCgst(null);
									erpRev.setNewSgst(null);
									erpRev.setNewDepositAmt(null);
									erpRev.setNewEstimateAmt(null);
									erpRev.setRemSupervisionAmt(null);
									erpRev.setRemCgst(null);
									erpRev.setRemSgst(null);
									erpRev.setRemEstimateAmt(null);
									erpRev.setRemmDepositAmt(null);
									erpRev.setOldSupervision(null);

									if (erpRev.getRemColonyOrSlum().compareTo(BigDecimal.ZERO) <= 0) {

									} else {
										erpRev.setNewPayAmt(roundAmountCgstAndSgst(
												erpRev.getNewColonyOrSlum().add(erpRev.getNewMinusCost())));
										erpRev.setPayAmt(roundAmountCgstAndSgst(
												erpRev.getRemColonyOrSlum().add(erpRev.getRemReturnAmt())));
									}
								}
							} else {
//									excetion throw karwana hai	
							}
						} else if (findConsumerApplicationDetailByApplicationNo.getIndividualOrGroup()
								.getIndividualOrGroupId() == 1L) {

							BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
							if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
									.compareTo(new BigDecimal(400)) <= 0) {

								BigDecimal new_ColonyOrSlum = roundAmountCgstAndSgst(
										oAndMLoad.multiply(new BigDecimal(15567)));

								BigDecimal rem_ColonyOrSlum = roundAmountCgstAndSgst(
										new_ColonyOrSlum.subtract(erpRev.getOldColonyOrSlum()));

								erpRev.setNewSupervisionAmt(null);
								erpRev.setNewCgst(null);
								erpRev.setNewSgst(null);
								erpRev.setNewDepositAmt(null);
								erpRev.setNewEstimateAmt(null);
								erpRev.setRemSupervisionAmt(null);
								erpRev.setRemCgst(null);
								erpRev.setRemSgst(null);
								erpRev.setRemEstimateAmt(null);
								erpRev.setRemmDepositAmt(null);
								erpRev.setOldSupervision(null);

								erpRev.setNewSupervisionAmt(null);
								erpRev.setNewCgst(null);
								erpRev.setNewSgst(null);

								erpRev.setNewColonyOrSlum(new_ColonyOrSlum);
								erpRev.setRemColonyOrSlum(rem_ColonyOrSlum);
								erpRev.setOldColonyOrSlum(erpRev.getOldColonyOrSlum());

								erpRev.setNewPayAmt(new_ColonyOrSlum.add(erpRev.getNewMinusCost()));
								erpRev.setPayAmt(erpRev.getRemColonyOrSlum().add(erpRev.getRemReturnAmt()));

							} else {

							}
						}

						else if (findConsumerApplicationDetailByApplicationNo.getNatureOfWorkType()
								.getNatureOfWorkTypeId().equals(5L)) {

							String jeLoadUnitKwYaKva = findConsumerApplicationDetailByApplicationNo
									.getJeLoadUnitKwYaKva();

							BigDecimal oAndMLoad = findConsumerApplicationDetailByApplicationNo.getoAndMLoad();
							if (findConsumerApplicationDetailByApplicationNo.getoAndMLoad()
									.compareTo(new BigDecimal(10)) <= 0) {

								BigDecimal new_securityAmt = oAndMLoad.multiply(new BigDecimal(200));

								erpRev.setNewSecurityAmt(new_securityAmt);
								erpRev.setRemSecurityAmt(
										erpRev.getNewSecurityAmt().subtract(erpRev.getOldSecurityAmt()));

							} else {
								BigDecimal new_securityAmt = oAndMLoad.multiply(new BigDecimal(400));

								erpRev.setNewSecurityAmt(new_securityAmt);
								erpRev.setRemSecurityAmt(
										erpRev.getNewSecurityAmt().subtract(erpRev.getOldSecurityAmt()));

							}

							if (erpRev.getNewSupervisionAmt().compareTo(erpRev.getOldSupervision()) < 0) {

//								

								erpRev.setRemCgst(BigDecimal.ZERO);
								erpRev.setRemSgst(BigDecimal.ZERO);

								erpRev.setNewPayAmt(
										roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst())
												.add(erpRev.getNewSgst()).add(erpRev.getRemSecurityAmt())));

								erpRev.setPayAmt(roundAmountCgstAndSgst(
										erpRev.getRemSupervisionAmt().add(erpRev.getRemSecurityAmt())));

							} else {

//							

								erpRev.setRemCgst(newCgst.subtract(oldCgst));
								erpRev.setRemSgst(newSgst.subtract(oldSgst));

								erpRev.setNewPayAmt(
										roundAmountCgstAndSgst(erpRev.getNewSupervisionAmt().add(erpRev.getNewCgst())
												.add(erpRev.getNewSgst()).add(erpRev.getRemReturnAmt())));

								erpRev.setPayAmt(roundAmountCgstAndSgst(erpRev.getRemSupervisionAmt().add(
										erpRev.getRemCgst().add(erpRev.getRemSgst().add(erpRev.getRemSecurityAmt())))));

							}

						}

//					    erpRev.setNewPayAmt(newSuperVisionAmt.add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
//						.add(oAndMReturnAmount));
//						erpRev.setNewPayAmt(newSuperVisionAmt.add(erpRev.getNewCgst()).add(erpRev.getNewSgst())
//									.add(newKvaLoad).add(oAndMReturnAmount));

					}

				}
			}
//			17-Oct-2024 start

			if (erpRev.getPayAmt() != null && erpRev.getRemCgst() != null
					&& (erpRev.getPayAmt().compareTo(BigDecimal.ZERO) <= 0)
					&& (erpRev.getRemCgst().compareTo(BigDecimal.ZERO) <= 0)) {

				erpRev.setConsumerRefundableAmnt(erpRev.getPayAmt());
			}

//			else {
//				erpRev.setConsumerRefundableAmnt(payAmt.add(remCgstSgst));
//			}

//			17-Oct-2024 end	
			if (value == 2L) {

				ErpRev save = erpRevRepository.save(erpRev);

				if (save != null) {
					ApplicationStatus appStatusDb = null;

					if (erpRev.getPayAmt().compareTo(BigDecimal.ZERO) < 0) {
						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.PENDING_FOR_WORK_ORDER.getId());
					} else {
						appStatusDb = applicationStatusService
								.findById(ApplicationStatusEnum.REMAING_DEMAND_PAYMENT_PENDING_BY_CONSUMER.getId());
					}
					findConsumerApplicationDetailByApplicationNo.setApplicationStatus(appStatusDb);
					findConsumerApplicationDetailByApplicationNo.setErpVersion("V2");
					findConsumerApplicationDetailByApplicationNo.setRevisedErpNumber(erpNo);
					ConsumerApplicationDetail saveConsumerApplication = ConsumerApplicationDetailService
							.saveConsumerApplication(findConsumerApplicationDetailByApplicationNo);
					findConsumerApplicationDetailByApplicationNo.setApplicationStatus(appStatusDb);

					return save;

				}
			} else {
				return erpRev;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	public static double upperRound(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		double scale = Math.pow(10, places);
		return Math.ceil(value);
	}
}
