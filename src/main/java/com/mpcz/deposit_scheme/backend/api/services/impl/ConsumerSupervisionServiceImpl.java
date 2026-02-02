package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.constant.HttpCode;
import com.mpcz.deposit_scheme.backend.api.constant.ResponseMessage;
import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.dto.ConsumerSupervisionResponseDto;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.SchemeTypeException;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerSupervisionService;
import com.mpcz.deposit_scheme.backend.api.services.DemandService;

@Service
public class ConsumerSupervisionServiceImpl implements ConsumerSupervisionService {

	Logger logger = LoggerFactory.getLogger(DemandServiceImpl.class);

	@Autowired
	DemandService demandService;

	@Override
	public List<ConsumerSupervisionResponseDto> findConsumerApplicationEstimate()
			throws DemandDetailException, SchemeTypeException {
		// TODO Auto-generated method stub
		final String method = "ConsumerSupervisionServiceImpl : findConsumerApplicationEstimate()";
		logger.info(method);
		Response response = new Response<>();

		List<ConsumerSupervisionResponseDto> consumerSupervisionDtoList = new ArrayList<>();

		ConsumerSupervisionResponseDto consumerSupervisionDemand = null;
		List<Demand> demandDetail = demandService.findAll();
		System.out.println(demandDetail);
		for (Demand demand : demandDetail) {
			consumerSupervisionDemand = new ConsumerSupervisionResponseDto();

			System.out.println("*******************************************" + demand);

			if (demand != null) {

				if (demand.getConsumersApplicationDetail() != null) {

					if (demand.getConsumersApplicationDetail().getSchemeType() != null) {
						if (demand.getConsumersApplicationDetail().getSchemeType().getSchemeTypeName() != null) {
							if (demand.getConsumersApplicationDetail().getSchemeType().getSchemeTypeName()
									.equals("Supervision")) {

								if (demand.getConsumersApplicationDetail().getConsumers() != null) {

									if (demand.getConsumersApplicationDetail().getConsumers().getConsumerName() != null)
										consumerSupervisionDemand.setConsumerName(demand.getConsumersApplicationDetail()
												.getConsumers().getConsumerName());

									if (demand.getConsumersApplicationDetail().getConsumers().getConsumerId() != null)
										consumerSupervisionDemand.setConsumerId(
												demand.getConsumersApplicationDetail().getConsumers().getConsumerId());

								}

								if (demand.getConsumersApplicationDetail().getAddress() != null)
									consumerSupervisionDemand
											.setAddress(demand.getConsumersApplicationDetail().getAddress());

								if (demand.getConsumersApplicationDetail().getConsumerApplicationId() != null)
									consumerSupervisionDemand.setConsumerApplicationId(
											demand.getConsumersApplicationDetail().getConsumerApplicationId());

								if (demand.getConsumersApplicationDetail().getArea() != null)
									consumerSupervisionDemand.setArea(demand.getConsumersApplicationDetail().getArea());
								if (demand.getConsumersApplicationDetail().getPincode() != null)
									consumerSupervisionDemand
											.setPincode(demand.getConsumersApplicationDetail().getPincode());

								if (demand.getDemandGenerationDate() != null)
									consumerSupervisionDemand.setDemandGenerationDate(demand.getDemandGenerationDate());

								if (demand.getConsumersApplicationDetail().getSchemeType().getSchemeTypeName() != null)
									consumerSupervisionDemand.setSchemeType(
											demand.getConsumersApplicationDetail().getSchemeType().getSchemeTypeName());

								if (demand.getDemandRs() != null)
									consumerSupervisionDemand.setDemandRs(demand.getDemandRs());

								if (demand.getDemandId() != null)
									consumerSupervisionDemand.setDemandId(demand.getDemandId());

								if (demand.getDemandStatus() != null)
									consumerSupervisionDemand.setDemandStatus(demand.getDemandStatus());

								if (demand.getIsDemandRejected() != null)
									consumerSupervisionDemand.setIsDemandRejected(demand.getIsDemandRejected());

								if (demand.getRejectionRemark() != null)
									consumerSupervisionDemand.setRejectionRemark(demand.getRejectionRemark());

								if (demand.getChargesType() != null)
									consumerSupervisionDemand.setChargesType(demand.getChargesType());

								if (demand.getSupervisionChargesOnCostOfMaterial() != null)
									consumerSupervisionDemand.setSupervisionChargesOnCostOfMaterial(
											demand.getSupervisionChargesOnCostOfMaterial());

								if (demand.getCgst() != null)
									consumerSupervisionDemand.setCgst(demand.getCgst());

								if (demand.getSgst() != null)
									consumerSupervisionDemand.setSgst(demand.getSgst());

								if (demand.getKrishiCess() != null)
									consumerSupervisionDemand.setKrishiCess(demand.getKrishiCess());

								if (demand.getEducationCess() != null)
									consumerSupervisionDemand.setEducationCess(demand.getEducationCess());

								if (demand.getSystemDevelopmentCharges() != null)
									consumerSupervisionDemand
											.setSystemDevelopmentCharges(demand.getSystemDevelopmentCharges());

								if (demand.getCostOfEstimate() != null)
									consumerSupervisionDemand.setCostOfEstimate(demand.getCostOfEstimate());

								if (demand.getOtherInfraStrucRelatedCost() != null)
									consumerSupervisionDemand
											.setOtherInfraStrucRelatedCost(demand.getOtherInfraStrucRelatedCost());

								if (demand.getSupplyAffordingCharges() != null)
									consumerSupervisionDemand
											.setSupplyAffordingCharges(demand.getSupplyAffordingCharges());

								if (demand.getConsumersApplicationDetail().getConsumerApplicationNo() != null)
									consumerSupervisionDemand.setConsumerApplicationNo(
											demand.getConsumersApplicationDetail().getConsumerApplicationNo());

								if (demand.getConsumersApplicationDetail().getShortDescriptionOfWork() != null)
									consumerSupervisionDemand.setShortDescriptionOfWork(
											demand.getConsumersApplicationDetail().getShortDescriptionOfWork());
								// set Location

								if (demand.getConsumersApplicationDetail().getFeeder() != null) {

									if (demand.getConsumersApplicationDetail().getFeeder().getFeederCode() != null)
										consumerSupervisionDemand.setFeederCode(
												demand.getConsumersApplicationDetail().getFeeder().getFeederCode());
									if (demand.getConsumersApplicationDetail().getFeeder().getFeederName() != null)
										consumerSupervisionDemand.setFeederName(
												demand.getConsumersApplicationDetail().getFeeder().getFeederName());

									if (demand.getConsumersApplicationDetail().getFeeder()
											.getFeederSubstation() != null) {

										if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
												.getSubStationCode() != null)
											consumerSupervisionDemand
													.setSubStationCode(demand.getConsumersApplicationDetail()
															.getFeeder().getFeederSubstation().getSubStationCode());
										if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
												.getSubStationName() != null)
											if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
													.getSubStationName() != null)
												consumerSupervisionDemand
														.setSubStationName(demand.getConsumersApplicationDetail()
																.getFeeder().getFeederSubstation().getSubStationName());

										if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
												.getSubstationDistributionCenter() != null) {

											if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
													.getSubstationDistributionCenter().getDistrict() != null) {

												if (demand.getConsumersApplicationDetail().getFeeder()
														.getFeederSubstation().getSubstationDistributionCenter()
														.getDistrict().getDistrictName() != null) {
													consumerSupervisionDemand.setDistrictName(demand
															.getConsumersApplicationDetail().getFeeder()
															.getFeederSubstation().getSubstationDistributionCenter()
															.getDistrict().getDistrictName());
												}
											}

											if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
													.getSubstationDistributionCenter().getDcCode() != null)
												consumerSupervisionDemand
														.setDcCode(demand.getConsumersApplicationDetail().getFeeder()
																.getFeederSubstation().getSubstationDistributionCenter()
																.getDcCode());
											if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
													.getSubstationDistributionCenter().getDcName() != null)

												consumerSupervisionDemand
														.setDcName(demand.getConsumersApplicationDetail().getFeeder()
																.getFeederSubstation().getSubstationDistributionCenter()
																.getDcName());

											if (demand.getConsumersApplicationDetail().getFeeder().getFeederSubstation()
													.getSubstationDistributionCenter().getDcSubdivision() != null) {
												if (demand.getConsumersApplicationDetail().getFeeder()
														.getFeederSubstation().getSubstationDistributionCenter()
														.getDcSubdivision().getSubDivisionCode() != null)
													consumerSupervisionDemand.setSubDivisionCode(demand
															.getConsumersApplicationDetail().getFeeder()
															.getFeederSubstation().getSubstationDistributionCenter()
															.getDcSubdivision().getSubDivisionCode());
												if (demand.getConsumersApplicationDetail().getFeeder()
														.getFeederSubstation().getSubstationDistributionCenter()
														.getDcSubdivision().getSubDivision() != null)
													consumerSupervisionDemand.setSubDivisionName(demand
															.getConsumersApplicationDetail().getFeeder()
															.getFeederSubstation().getSubstationDistributionCenter()
															.getDcSubdivision().getSubDivision());
												if (demand.getConsumersApplicationDetail().getFeeder()
														.getFeederSubstation().getSubstationDistributionCenter()
														.getDcSubdivision().getSubDivisionCode() != null)
													consumerSupervisionDemand.setSubDivisionCode(demand
															.getConsumersApplicationDetail().getFeeder()
															.getFeederSubstation().getSubstationDistributionCenter()
															.getDcSubdivision().getSubDivisionCode());

												if (demand.getConsumersApplicationDetail().getFeeder()
														.getFeederSubstation().getSubstationDistributionCenter()
														.getDcSubdivision().getSubdivisionDivision() != null) {

													if (demand.getConsumersApplicationDetail().getFeeder()
															.getFeederSubstation().getSubstationDistributionCenter()
															.getDcSubdivision().getSubdivisionDivision()
															.getDivisionCode() != null)
														consumerSupervisionDemand.setDivisionCode(demand
																.getConsumersApplicationDetail().getFeeder()
																.getFeederSubstation().getSubstationDistributionCenter()
																.getDcSubdivision().getSubdivisionDivision()
																.getDivisionCode());
													if (demand.getConsumersApplicationDetail().getFeeder()
															.getFeederSubstation().getSubstationDistributionCenter()
															.getDcSubdivision().getSubdivisionDivision()
															.getDivision() != null)
														consumerSupervisionDemand.setDivisionName(demand
																.getConsumersApplicationDetail().getFeeder()
																.getFeederSubstation().getSubstationDistributionCenter()
																.getDcSubdivision().getSubdivisionDivision()
																.getDivision());

													if (demand.getConsumersApplicationDetail().getFeeder()
															.getFeederSubstation().getSubstationDistributionCenter()
															.getDcSubdivision().getSubdivisionDivision()
															.getDivisionCircle() != null) {

														if (demand.getConsumersApplicationDetail().getFeeder()
																.getFeederSubstation().getSubstationDistributionCenter()
																.getDcSubdivision().getSubdivisionDivision()
																.getDivisionCircle().getCircleCode() != null)
															consumerSupervisionDemand.setCircleCode(
																	demand.getConsumersApplicationDetail().getFeeder()
																			.getFeederSubstation()
																			.getSubstationDistributionCenter()
																			.getDcSubdivision().getSubdivisionDivision()
																			.getDivisionCircle().getCircleCode());
														if (demand.getConsumersApplicationDetail().getFeeder()
																.getFeederSubstation().getSubstationDistributionCenter()
																.getDcSubdivision().getSubdivisionDivision()
																.getDivisionCircle().getCircle() != null)
															consumerSupervisionDemand
																	.setCircle(demand.getConsumersApplicationDetail()
																			.getFeeder().getFeederSubstation()
																			.getSubstationDistributionCenter()
																			.getDcSubdivision().getSubdivisionDivision()
																			.getDivisionCircle().getCircle());

														if (demand.getConsumersApplicationDetail().getFeeder()
																.getFeederSubstation().getSubstationDistributionCenter()
																.getDcSubdivision().getSubdivisionDivision()
																.getDivisionCircle().getCircleRegion() != null) {
															if (demand.getConsumersApplicationDetail().getFeeder()
																	.getFeederSubstation()
																	.getSubstationDistributionCenter()
																	.getDcSubdivision().getSubdivisionDivision()
																	.getDivisionCircle().getCircleRegion()
																	.getRegion() != null)
																consumerSupervisionDemand.setRegion(demand
																		.getConsumersApplicationDetail().getFeeder()
																		.getFeederSubstation()
																		.getSubstationDistributionCenter()
																		.getDcSubdivision().getSubdivisionDivision()
																		.getDivisionCircle().getCircleRegion()
																		.getRegion());
															if (demand.getConsumersApplicationDetail().getFeeder()
																	.getFeederSubstation()
																	.getSubstationDistributionCenter()
																	.getDcSubdivision().getSubdivisionDivision()
																	.getDivisionCircle().getCircleRegion()
																	.getRegionCode() != null) {
																consumerSupervisionDemand.setRegionCode(demand
																		.getConsumersApplicationDetail().getFeeder()
																		.getFeederSubstation()
																		.getSubstationDistributionCenter()
																		.getDcSubdivision().getSubdivisionDivision()
																		.getDivisionCircle().getCircleRegion()
																		.getRegionCode());
															}

														}
													}
												}
											}
										}
									}

								}

								if (demand.getConsumersApplicationDetail().getNatureOfWorkType() != null) {
									if (demand.getConsumersApplicationDetail().getNatureOfWorkType()
											.getNatureOfWorkName() != null)
										consumerSupervisionDemand.setNatureOfWork(demand.getConsumersApplicationDetail()
												.getNatureOfWorkType().getNatureOfWorkName());
								}

								try {
									 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
									    Date date = new Date();  
									    System.out.println(formatter.format(date));  
									consumerSupervisionDemand.setRequestTime(formatter.format(date));
								} catch (Exception e) {
									System.out.println(e);
									Log.error(e);
								}

								consumerSupervisionDtoList.add(consumerSupervisionDemand);
							}
						}
					}

				}
			}

		}

		if (Objects.isNull(consumerSupervisionDtoList) || consumerSupervisionDtoList.isEmpty()) {
			logger.error("repository.findAll is returning Null when findAll call");
			response.setCode(HttpCode.NULL_OBJECT);
			response.setMessage(ResponseMessage.CONSUMERS_RECORD_FETCH_BY_ID_FAILED_MESSAGE);
			throw new DemandDetailException(response);
		}
		response.setList(consumerSupervisionDtoList);
		logger.info("Response is returning successfully");
		response.setCode(HttpCode.OK);
		response.setMessage(ResponseMessage.RECORD_FETCH_ALL_MESSAGE);
		return consumerSupervisionDtoList;
	}

}
