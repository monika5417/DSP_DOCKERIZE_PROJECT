/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mpcz.deposit_scheme.backend.api.domain.Auditable;
import com.mpcz.deposit_scheme.backend.api.domain.Invoice;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Vikas Singh Nalwaya
 * modified by-Saurabh Kumar Gupta
 */



public class InvoiceDetailDTO extends Auditable<Long> {
    
//	CGST(1), SGST(2),SUPER_VISION(3),INITIAL_AMOUNT(4),ESTIMATE_AMOUNT(5),TESTING_CHARGES(6),METER_COST(7),TOTAL_AMOUNT(8)LOAD_ENHANCEMENT_APPLICATION_FEES(9), LOAD_ENHANCEMENT_REGISTRATION_FEES_1LT(10), LOAD_ENHANCEMENT_REGISTRATION_FEES_3LT(11);;
	private Double cGST;
	private Double sGST;
	private Double registrationAmount;
	private Double totalAmount;
	
	
	public Double getcGST() {
		return cGST;
	}
	public void setcGST(Double cGST) {
		this.cGST = cGST;
	}
	public Double getsGST() {
		return sGST;
	}
	public void setsGST(Double sGST) {
		this.sGST = sGST;
	}
	public Double getRegistrationAmount() {
		return registrationAmount;
	}
	public void setRegistrationAmount(Double registrationAmount) {
		this.registrationAmount = registrationAmount;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "InvoiceDetailDTO [cGST=" + cGST + ", sGST=" + sGST + ", registrationAmount=" + registrationAmount
				+ ", totalAmount=" + totalAmount + "]";
	}


	
  
    
    
    
}
