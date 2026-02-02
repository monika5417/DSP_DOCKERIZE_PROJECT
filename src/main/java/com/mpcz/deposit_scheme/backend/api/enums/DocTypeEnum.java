package com.mpcz.deposit_scheme.backend.api.enums;

public enum DocTypeEnum {
	
	AADHAR_CARD(1L, "AADHAR_CARD"),
	PAN_CARD(2L, "PAN_CARD"),
	ENERGY_BILLS(3L, "ENERGY_BILLS"),
	REGISTRY_OR_LEASE_DOCUMENT(4L, "REGISTRY_OR_LEASE_DOCUMENT"),
	RESIDENTIAL_PROOF(5L, "RESIDENTIAL_PROOF"),
	BUILDING_PERMISSION_OR_TNCP_DOCUMENT(6L, "BUILDING_PERMISSION_OR_TNCP_DOCUMENT");

	private Long id;
	private String value;

	private DocTypeEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static DocTypeEnum getEnumByName(String name) {
		DocTypeEnum[] modes = DocTypeEnum.values();
		if (modes == null) {
			return null;
		}
		for (DocTypeEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static DocTypeEnum getEnumByValue(Long value) {
		DocTypeEnum[] modes = DocTypeEnum.values();
		if (modes == null) {
			return null;
		}
		for (DocTypeEnum mode : modes) {
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
