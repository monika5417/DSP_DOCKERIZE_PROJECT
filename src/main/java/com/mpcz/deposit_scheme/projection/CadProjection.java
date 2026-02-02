package com.mpcz.deposit_scheme.projection;

import org.springframework.beans.factory.annotation.Value;

public interface CadProjection {

	@Value("#{target.CONSUMER_NAME}")
    String getConsumerName();
    
    @Value("#{target.GUARDIAN_NAME}")
    String getGuardianName();
    
    @Value("#{target.ADDRESS}")
    String getAddress();
    
    @Value("#{target.NATURE_OF_WORK_ID}")
    String getNatureOfWork();
    
    @Value("#{target.AREA}")
    String getArea();
    
    @Value("#{target.AADHAR_NO}")
    String getAadharNo();
    
    @Value("#{target.DC_ID}")
    String getDcId();
    
    @Value("#{target.SHORT_DESCRIPTION_OF_WORK}")
    String getShortDescription();
    
    @Value("#{target.PIN_CODE}")
    String getPinCode();
    
    @Value("#{target.DISTRICT_ID}")
    String getDistrictId();
    
    @Value("#{target.LOAD_REQUESTED_PER_UNIT}")
    String getLoadRequested();
    
    @Value("#{target.LOAD_REQUESTED_ID}")
    String getLoadRequestedId();
    
    @Value("#{target.INDIVIDUAL_OR_GROUPWISE_ID}")
    String getIndividualOrGroup();
    
    @Value("#{target.KHASRA}")
    String getKhasra();
    
    @Value("#{target.samagra_id}")
    String getSamagraId();
    
    @Value("#{target.CAST_CATEGORY}")
    String getCastCategory();
    
    @Value("#{target.Phone_Number}")
    String getPhoneNumber();
    
    
    
    
	
	
}
