package com.mpcz.deposit_scheme.backend.api.enums;

public enum PaymentRecieptTypeEnum {

    
	
	RegistrationFees(1L, "Registration Fees"),
	DemandFees(2L, "Demand Fees");
	

	private Long id;
	private String value;

	private PaymentRecieptTypeEnum(Long id, String value) {
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
