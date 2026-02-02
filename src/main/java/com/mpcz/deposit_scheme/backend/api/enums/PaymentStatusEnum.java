package com.mpcz.deposit_scheme.backend.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum PaymentStatusEnum {

    
	
//	PENDING(1L, "Registration Fees"),
//	PAID(2L, "Demand Fees"),
	PENDING(1L, "PENDING"),
	PAID(2L, "PAID"),
	FAILURE(3L,"Failure");
	

	private Long id;
	private String value;

	private PaymentStatusEnum(Long id, String value) {
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
