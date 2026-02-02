 
package com.mpcz.deposit_scheme.backend.api.enums;

public enum ChargesTypeEnum {
	
	AMOUNT(1L, "AMOUNT"),
	CGST(2L, "CGST"),
	SGST(3L, "SGST"),
	TOTAL_AMOUNT(4L, "TOTAL AMOUNT");
	 
	
	

	private Long id;
	private String value;

	private ChargesTypeEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static ChargesTypeEnum getEnumByName(String name) {
		ChargesTypeEnum[] modes = ChargesTypeEnum.values();
		if (modes == null) {
			return null;
		}
		for (ChargesTypeEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static ChargesTypeEnum getEnumByValue(Long value) {
		ChargesTypeEnum[] modes = ChargesTypeEnum.values();
		if (modes == null) {
			return null;
		}
		for (ChargesTypeEnum mode : modes) {
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
