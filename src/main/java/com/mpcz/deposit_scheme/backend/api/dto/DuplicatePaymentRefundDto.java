package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.List;

public class DuplicatePaymentRefundDto {

	
	private String totalPaymentOfRegistration;
	private String totalPaymentOfDemand;
//	private String totalPaymentOfReviseDemand;
	
	private String registationRefundCompleted;
	private String demandRefundCompleted;
//	private String reviseDemandRefundCompleted;
	
	private String remainingRegistrationRefund;
	private String remainingDemandRefund;
//	private String remainingReviseDemandRefund;
	
	private List<RegistrationRefundDto> registrationDto;
	
	private List<DemandRefundDto> demandDto;

	public String getTotalPaymentOfRegistration() {
		return totalPaymentOfRegistration;
	}

	public void setTotalPaymentOfRegistration(String totalPaymentOfRegistration) {
		this.totalPaymentOfRegistration = totalPaymentOfRegistration;
	}

	public String getTotalPaymentOfDemand() {
		return totalPaymentOfDemand;
	}

	public void setTotalPaymentOfDemand(String totalPaymentOfDemand) {
		this.totalPaymentOfDemand = totalPaymentOfDemand;
	}

	public String getRegistationRefundCompleted() {
		return registationRefundCompleted;
	}

	public void setRegistationRefundCompleted(String registationRefundCompleted) {
		this.registationRefundCompleted = registationRefundCompleted;
	}

	public String getDemandRefundCompleted() {
		return demandRefundCompleted;
	}

	public void setDemandRefundCompleted(String demandRefundCompleted) {
		this.demandRefundCompleted = demandRefundCompleted;
	}

	public String getRemainingRegistrationRefund() {
		return remainingRegistrationRefund;
	}

	public void setRemainingRegistrationRefund(String remainingRegistrationRefund) {
		this.remainingRegistrationRefund = remainingRegistrationRefund;
	}

	public String getRemainingDemandRefund() {
		return remainingDemandRefund;
	}

	public void setRemainingDemandRefund(String remainingDemandRefund) {
		this.remainingDemandRefund = remainingDemandRefund;
	}

	public List<RegistrationRefundDto> getRegistrationDto() {
		return registrationDto;
	}

	public void setRegistrationDto(List<RegistrationRefundDto> registrationDto) {
		this.registrationDto = registrationDto;
	}

	public List<DemandRefundDto> getDemandDto() {
		return demandDto;
	}

	public void setDemandDto(List<DemandRefundDto> demandDto) {
		this.demandDto = demandDto;
	}
	
//	private List<ReviseDemandRefundDto> reviseDemandDto;
	
	
	
	

	
	
	
}
