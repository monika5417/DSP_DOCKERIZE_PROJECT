package com.mpcz.deposit_scheme.backend.api.enums;

public enum StatusEnum {
	
	APPROVED(1L, "APPROVED"),
	PENDING(2L, "PENDING"),
	REJECTED(3L, "REJECTED"),
	COMPLETED(4L, "COMPLETED"); 

	private Long id;
	private String value;

	private StatusEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static StatusEnum getEnumByName(String name) {
		StatusEnum[] modes = StatusEnum.values();
		if (modes == null) {
			return null;
		}
		for (StatusEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static StatusEnum getEnumByValue(Long value) {
		StatusEnum[] modes = StatusEnum.values();
		if (modes == null) {
			return null;
		}
		for (StatusEnum mode : modes) {
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
