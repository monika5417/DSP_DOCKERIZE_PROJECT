package com.mpcz.deposit_scheme.backend.api.enums;

public enum VendorEnum {

	CONTRACTOR(1L, "CONTRACTOR"), MPMKVVCL(2L, "MPMKVVCL"), OYT(3L, "OYT");

	private Long id;
	private String value;

	private VendorEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static VendorEnum getEnumByName(String name) {
		VendorEnum[] modes = VendorEnum.values();
		if (modes == null) {
			return null;
		}
		for (VendorEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static VendorEnum getEnumByValue(Long value) {
		VendorEnum[] modes = VendorEnum.values();
		if (modes == null) {
			return null;
		}
		for (VendorEnum mode : modes) {
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
