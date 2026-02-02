/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcz.deposit_scheme.backend.api.enums;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  
 */
public enum PaymentTypeEnum {
	REGISTRATION_FEES(1L, "Registration Fees"),
	DEMAND_FEES(2L, "Demand Fees");
	
	

	private Long id;
	private String value;

	private PaymentTypeEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static PaymentTypeEnum getEnumByName(String name) {
		PaymentTypeEnum[] modes = PaymentTypeEnum.values();
		if (modes == null) {
			return null;
		}
		for (PaymentTypeEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static PaymentTypeEnum getEnumByValue(Long value) {
		PaymentTypeEnum[] modes = PaymentTypeEnum.values();
		if (modes == null) {
			return null;
		}
		for (PaymentTypeEnum mode : modes) {
			if (mode.getId() == value) {
				return mode;
			}
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
    
}
