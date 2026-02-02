package com.mpcz.deposit_scheme.backend.api.enums;

public enum SupplyVoltageEnum {

	VOLTAGE11KV(1L, "11 KV"), VOLTAGE33KV(2L, "33 KV");

	private Long id;
	private String value;

	private SupplyVoltageEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static SupplyVoltageEnum getEnumByName(String name) {
		SupplyVoltageEnum[] modes = SupplyVoltageEnum.values();
		if (modes == null) {
			return null;
		}
		for (SupplyVoltageEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static SupplyVoltageEnum getEnumByValue(Long value) {
		SupplyVoltageEnum[] modes = SupplyVoltageEnum.values();
		if (modes == null) {
			return null;
		}
		for (SupplyVoltageEnum mode : modes) {
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
