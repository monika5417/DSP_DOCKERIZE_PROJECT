package com.mpcz.deposit_scheme.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ConsummerAppDetails {

	@Value("#{target.CONSUMER_APPLICATION_NUMBER}")
	 String getconsumeApplicationNumber();
	 
	
	@Value("#{target.REV_ERP}")
	 String getnewErpNo();
	
	
	@Value("#{target.APPLICATION_STATUS}")
	String getApplicationStatus();
	
	
	
	

}