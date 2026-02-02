package com.mpcz.deposit_scheme.backend.api.response;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.request.ErrorDetails;

public class Response<T> {
	private String code;
	private String message;
	private List<T> list;
	private Map<Integer, T> map;
	private Map<String, List<?>> map1;
	private List<ErrorDetails> error;
	private String token;
	private String dspApplicationNo;
	private Map<String, Object> map2;



	
	public Map<String, Object> getMap2() {
		return map2;
	}


	public void setMap2(Map<String, Object> map2) {
		this.map2 = map2;
	}


	public String getDspApplicationNo() {
		return dspApplicationNo;
	}


	public void setDspApplicationNo(String dspApplicationNo) {
		this.dspApplicationNo = dspApplicationNo;
	}


	public Response() {
		super();
	}

	
	public Response(String code, String message, List<T> list) {
		super();
		this.code = code;
		this.message = message;
		this.list = list;
	}

	/**
	 * @param code
	 * @param message
	 */
	public Response(String code, String message) {
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


	public Map<String, List<?>> getMap1() {
		return map1;
	}


	public void setMap1(Map<String, List<?>> map1) {
		this.map1 = map1;
	}


	



}
