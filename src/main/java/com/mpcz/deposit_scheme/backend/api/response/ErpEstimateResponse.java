package com.mpcz.deposit_scheme.backend.api.response;

import java.util.List;
import java.util.Map;

import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;

public class ErpEstimateResponse<T> {

	private String code;
	private String message;
	private List<T> list;
	private Map<Integer, T> map;
	private List<ErrorDetails> error;
	private String token;

	public ErpEstimateResponse() {
		super();
	}

	/**
	 * @param code
	 * @param message
	 */
	public ErpEstimateResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public List<ErrorDetails> getError() {
		return error;
	}

	public void setError(List<ErrorDetails> error) {
		this.error = error;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Map<Integer, T> getMap() {
		return map;
	}

	public void setMap(Map<Integer, T> map) {
		this.map = map;
	}
}
