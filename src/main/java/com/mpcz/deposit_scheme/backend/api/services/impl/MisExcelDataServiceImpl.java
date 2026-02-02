package com.mpcz.deposit_scheme.backend.api.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpcz.deposit_scheme.backend.api.domain.Circle;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.Demand;
import com.mpcz.deposit_scheme.backend.api.domain.DistributionCenter;
import com.mpcz.deposit_scheme.backend.api.domain.Division;
import com.mpcz.deposit_scheme.backend.api.domain.PaymentHistory;
import com.mpcz.deposit_scheme.backend.api.domain.SubDivision;
import com.mpcz.deposit_scheme.backend.api.dto.MisExcelData;
import com.mpcz.deposit_scheme.backend.api.dto.MisExcelHeader;
import com.mpcz.deposit_scheme.backend.api.exception.CircleException;
import com.mpcz.deposit_scheme.backend.api.exception.ConsumerApplicationDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DemandDetailException;
import com.mpcz.deposit_scheme.backend.api.exception.DistributionCenterException;
import com.mpcz.deposit_scheme.backend.api.exception.DivisionException;
import com.mpcz.deposit_scheme.backend.api.exception.PaymentHistoryException;
import com.mpcz.deposit_scheme.backend.api.exception.SubDivisionException;
import com.mpcz.deposit_scheme.backend.api.services.CircleService;
import com.mpcz.deposit_scheme.backend.api.services.ConsumerApplicationDetailService;
import com.mpcz.deposit_scheme.backend.api.services.DemandService;
import com.mpcz.deposit_scheme.backend.api.services.DistributionCenterService;
import com.mpcz.deposit_scheme.backend.api.services.DivisionService;
import com.mpcz.deposit_scheme.backend.api.services.MisExcelDataService;
import com.mpcz.deposit_scheme.backend.api.services.PaymentHistoryService;
import com.mpcz.deposit_scheme.backend.api.services.SubDivisionService;




@Service
public class MisExcelDataServiceImpl implements MisExcelDataService{
	
	Logger logger = LoggerFactory.getLogger(MisExcelDataServiceImpl.class);

	
	@Autowired
	PaymentHistoryService paymentHistoryService;
	
	@Autowired
	DistributionCenterService distributionCenterService;
	
	@Autowired
	ConsumerApplicationDetailService consumerApplicationDetailService;
	
	@Autowired
	SubDivisionService subDivisionService;
	
	@Autowired
	DivisionService  divisionService;
	
	@Autowired
	CircleService circleService;
	
	@Autowired
	DemandService demandService;
	
	@Override
	public List<MisExcelData> findAllDetails() throws PaymentHistoryException, ConsumerApplicationDetailException, DistributionCenterException, SubDivisionException, DivisionException, CircleException {
		final String method = "DistributionCenterServiceImpl : findAll()";
		logger.info(method);
		
		List<MisExcelData> misExcelData =new ArrayList<>();
		List<PaymentHistory> paymentHistories = paymentHistoryService.findAllApplication();
		
		System.out.println("*******************************************************"+new Date());
		
		MisExcelData miss=null;
		for(PaymentHistory paymentHistory:paymentHistories) {
			 miss=new MisExcelData();

			if(paymentHistory.getPaymentType().equals("Registration Fees") && paymentHistory.getRequestStatus().equals("success")) {
			
			System.out.println("PAYMANET"+paymentHistory);
			ConsumerApplicationDetail consumerAppDetails = consumerApplicationDetailService.findConsumerApplicationDetailByApplicationNo(paymentHistory.getApplicationNo());
			
			if(consumerAppDetails.getDistributionCenter() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision() != null &&consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivisionCircle()!=null)
					 {
				miss.setCircleName(consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivisionCircle().getCircle());
			}	
				
			
			if(consumerAppDetails.getDistributionCenter() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision() != null
					) {
				miss.setDivisionName(consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivision());
			}	
			
					
			if(consumerAppDetails.getDistributionCenter() != null ) {
				miss.setDistributionCenterName(consumerAppDetails.getDistributionCenter().getDcName());
			}		
			
			miss.setConsumerApplicationNo(consumerAppDetails.getConsumerApplicationNo());
			
			if(paymentHistory.getAmount() != null ) {
				miss.setRegistrationCharges(new BigDecimal(paymentHistory.getAmount()));
			}else {
				miss.setRegistrationCharges(new BigDecimal(0));
			}
//			miss.setRegistrationCharges(new BigDecimal(paymentHistory.getAmount()));
//			miss.setRegistrationCharges(BigDecimal.valueOf(Double.valueOf(paymentHistory.getAmount())));
			misExcelData.add(miss);
			}
		}
		return misExcelData;
		
		
	}
	
	@Override
	public List<MisExcelHeader> findAllHeaderDetails() throws PaymentHistoryException, ConsumerApplicationDetailException, DistributionCenterException, SubDivisionException, DivisionException, CircleException, DemandDetailException {
		final String method = "DistributionCenterServiceImpl : findAll()";
		logger.info(method);
		
		List<MisExcelHeader> misHeader =new ArrayList<>();
		
		MisExcelHeader misExcelHeader=null;
		
		List<Demand> demands  = demandService.findAll();
		
		for(Demand demandDetail:demands) {
			
			misExcelHeader=new MisExcelHeader();
		
			ConsumerApplicationDetail consumerAppDetails = consumerApplicationDetailService.findConsumerApplicationDetailByApplicationNo(demandDetail.getConsumersApplicationDetail().getConsumerApplicationNo());
			
			if(consumerAppDetails.getDistributionCenter() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision() != null &&consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivisionCircle()!=null)
					 {
			misExcelHeader.setCircleName(consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivisionCircle().getCircle());
					 }
			
			if(consumerAppDetails.getDistributionCenter() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision() != null
					&& consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision() != null
					) {
			
			misExcelHeader.setDivisionName(consumerAppDetails.getDistributionCenter().getDcSubdivision().getSubdivisionDivision().getDivision());
			}
			
			if(consumerAppDetails.getDistributionCenter() != null ) {
			misExcelHeader.setDistributionCenterName(consumerAppDetails.getDistributionCenter().getDcName());
			}
			
			misExcelHeader.setConsumerApplicationNo(consumerAppDetails.getConsumerApplicationNo());
			
			misExcelHeader.setSupervisionChargesOnCostOfMaterial(demandDetail.getSupervisionChargesOnCostOfMaterial());
			misExcelHeader.setCgst(demandDetail.getCgst());
			misExcelHeader.setSgst(demandDetail.getSgst());
			misExcelHeader.setKrishiCess(demandDetail.getKrishiCess());
			misExcelHeader.setEducationCess(demandDetail.getEducationCess());
			misExcelHeader.setEecondaryHigherEduCess(demandDetail.getEecondaryHigherEduCess());
			misExcelHeader.setSystemDevelopmentCharges(demandDetail.getSystemDevelopmentCharges());
			misExcelHeader.setCostOfEstimate(demandDetail.getCostOfEstimate());
			misExcelHeader.setOtherInfraStrucRelatedCost(demandDetail.getOtherInfraStrucRelatedCost());
			misExcelHeader.setSupplyAffordingCharges(demandDetail.getSupplyAffordingCharges());
			
			misHeader.add(misExcelHeader);
			}
		return misHeader;
	}

	@Override
	public Double totalOfRegistrationCharges(List<MisExcelData> misExcelHeaderDetails) {
		// TODO Auto-generated method stub
		
		double sum = 0.0;
		
		for(MisExcelData misExcelHeaderObject:misExcelHeaderDetails) {
			sum=sum+misExcelHeaderObject.getRegistrationCharges().doubleValue();	
		}
		
		return sum;
	}

	@Override
	public List<Double> totalOfDemandChargesDetails(List<MisExcelHeader> misExcelHeaderDetail) {
		// TODO Auto-generated method stub
           double sumOfSupervisionChargesOnCostOfMaterial = 0.0;
           double sumOfCgst=0.0;
           double sumOfSgst=0.0;
           double sumOfEducationCess=0.0;
           double sumOfEecondaryHigherEduCess=0.0;
           double sumOfKrishiCess=0.0;
           double sumOfSupplyAffordingCharges=0.0;
           double sumOfOtherInfraStrucRelatedCost=0.0;
           double sumOfSystemDevelopmentCharges=0.0;
           double sumOfCostOfEstimate=0.0;
           
           List<Double> listOfDemandDetailSum=new ArrayList<Double>();
		
		for(MisExcelHeader misExcelHeaderObjects:misExcelHeaderDetail) {
			sumOfSupervisionChargesOnCostOfMaterial=sumOfSupervisionChargesOnCostOfMaterial+misExcelHeaderObjects.getSupervisionChargesOnCostOfMaterial().doubleValue();
			sumOfCgst=sumOfCgst+misExcelHeaderObjects.getCgst().doubleValue();
			sumOfSgst=sumOfSgst+misExcelHeaderObjects.getSgst().doubleValue();
			sumOfEducationCess=sumOfEducationCess+misExcelHeaderObjects.getEducationCess().doubleValue();
			sumOfEecondaryHigherEduCess=sumOfEecondaryHigherEduCess+misExcelHeaderObjects.getEecondaryHigherEduCess().doubleValue();
			sumOfKrishiCess=sumOfKrishiCess+misExcelHeaderObjects.getKrishiCess().doubleValue();
			sumOfOtherInfraStrucRelatedCost=sumOfOtherInfraStrucRelatedCost+misExcelHeaderObjects.getOtherInfraStrucRelatedCost().doubleValue();
			sumOfSupplyAffordingCharges=sumOfSupplyAffordingCharges+misExcelHeaderObjects.getSupplyAffordingCharges().doubleValue();
			sumOfSystemDevelopmentCharges=sumOfSystemDevelopmentCharges+misExcelHeaderObjects.getSystemDevelopmentCharges().doubleValue();
			sumOfCostOfEstimate=sumOfCostOfEstimate+misExcelHeaderObjects.getCostOfEstimate().doubleValue();	
			
		}
		listOfDemandDetailSum.add(sumOfSupervisionChargesOnCostOfMaterial);
		listOfDemandDetailSum.add(sumOfCgst);
		listOfDemandDetailSum.add(sumOfSgst);
		listOfDemandDetailSum.add(sumOfEducationCess);
		listOfDemandDetailSum.add(sumOfEecondaryHigherEduCess);
		listOfDemandDetailSum.add(sumOfKrishiCess);
		listOfDemandDetailSum.add(sumOfOtherInfraStrucRelatedCost);
		listOfDemandDetailSum.add(sumOfSupplyAffordingCharges);
		listOfDemandDetailSum.add(sumOfSystemDevelopmentCharges);
		listOfDemandDetailSum.add(sumOfCostOfEstimate);
		
		
		return listOfDemandDetailSum;
	}

			

}
