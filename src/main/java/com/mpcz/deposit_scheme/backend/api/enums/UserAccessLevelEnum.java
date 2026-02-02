package com.mpcz.deposit_scheme.backend.api.enums;

public enum UserAccessLevelEnum {
	
	DISCOM(1L, "DISCOM"),
	REGION(2L, "REGION"),
	CIRCLE(3L, "CIRCLE"),
	DIVISION(4L, "DIVISION"),
	SUBDIVISION(5L, "SUBDIVISION"),
	DC(6L, "DC");

	private Long id;
	private String value;

	private UserAccessLevelEnum(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static UserAccessLevelEnum getEnumByName(String name) {
		UserAccessLevelEnum[] modes = UserAccessLevelEnum.values();
		if (modes == null) {
			return null;
		}
		for (UserAccessLevelEnum mode : modes) {
			if (mode.getValue().equals(name)) {
				return mode;
			}
		}
		return null;
	}

	public static UserAccessLevelEnum getEnumByValue(Long value) {
		UserAccessLevelEnum[] modes = UserAccessLevelEnum.values();
		if (modes == null) {
			return null;
		}
		for (UserAccessLevelEnum mode : modes) {
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


 
