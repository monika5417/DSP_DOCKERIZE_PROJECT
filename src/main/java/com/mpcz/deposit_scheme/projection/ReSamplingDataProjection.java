package com.mpcz.deposit_scheme.projection;

import org.springframework.beans.factory.annotation.Value;

import lombok.val;

public interface ReSamplingDataProjection {
	
	@Value("#{target.CONSUMER_APPLICATION_NUMBER}")
    public String getConsumerApplicaitonNumber();
	
	@Value("#{target.erp_workflow_number}")
	public String getErpWorkFlowNumber();

	
	@Value("#{target.TRANSFORMER_SERIAL_NO}")
	public String getTransformerSerialNo();
	
	@Value("#{target.CONTRACTOR_NAME}")
	public String getContractorName();
	
	@Value("#{target.CONTRACTOR_AUTHANTICATION_ID}")
	public String getcontractorAuthanticationId();
	
	@Value("#{target.NATURE_OF_WORK_NAME}")
	public String getNatureOfWorkName();
	
	@Value("#{target.VENDOR_NAME}")
	public String getVendorName();
	
	@Value("#{target.circle}")
	public String getCircleName();
	
	@Value("#{target.division}")
	public String getdivisionName();
	
	@Value("#{target.dc_name}")
	public String getDcName();
	
	@Value("#{target.CONSUMER_NAME}")
	public String getConsuemrName();
	
	@Value("#{target.ADDRESS}")
	public String getAddress();
	
	
	
	@Value("#{target.PHONE_NUMBER}")
	public String getPhonNumber();
	
	@Value("#{target.CONSUMER_MOBILE_NO}")
	public String getConsumerMobileNo();
	
	@Value("#{target.WORK_ORDER_NO}")
	public String getWorkOrderNo();
	
	@Value("#{target.WORK_ORDER_DATE}")
	public String getWorkOrderDate();
	
	
	@Value("#{target.WORK_COMPLETION_DATE}")
	public String getworkcomplitionDate();


}
