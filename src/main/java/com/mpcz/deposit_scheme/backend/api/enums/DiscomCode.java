package com.mpcz.deposit_scheme.backend.api.enums;

public enum DiscomCode {

	INDORE(1, "IDR"), BHOPAL(2, "BPL"), JABALPUR(3, "JBP");

	private int id;
	private String value;

	private DiscomCode(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
}
