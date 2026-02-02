package com.mpcz.deposit_scheme.backend.api.constant;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Aditya Access Level that can be used
 * @since 1.0
 * 
 * 
 */
public enum AccessLevelEnum implements Serializable{
	/**
	 * Discom Level Officer Access
	 */
	DISCOM(1L, "DISCOM"),
	REGION(2L, "REGION"),
	CIRCLE(3L, "CIRCLE"),
	DIVISION(4L, "DIVISION"),
	SUBDIVISION(5L, "SUBDIVISION"),
	DC(6L, "DC");

	private Long id;
	private String level;

	private AccessLevelEnum(Long id, String level) {
		this.setId(id);
		this.setLevel(level);
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	private static final Map<Long, String> accessLevels = new LinkedHashMap<Long, String>();


	static {

		for (AccessLevelEnum levelEnum : AccessLevelEnum.values()) {
			accessLevels.put(levelEnum.getId(),levelEnum.getLevel());
		}
	}

	/**
	 * Returns a map consisting of string name of access levels.
	 *
	 * @return Map of Access Levels
	 */
	public static Map<Long, String> getAccessLevels() {
		return accessLevels;
	}
	
}