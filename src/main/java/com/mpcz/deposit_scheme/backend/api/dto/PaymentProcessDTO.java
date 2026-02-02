/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcz.deposit_scheme.backend.api.dto;

/**
 *
 * @author Vikas Singh Nalwaya
 */
public class PaymentProcessDTO {

	private String successUrl;
	private String failureUrl;
	private String cancelUrl;
	private String hashKey;
	private String baseUrl;
//	private HexDTO hexDTO;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

//	public HexDTO getHexDTO() {
//		return hexDTO;
//	}
//
//	public void setHexDTO(HexDTO hexDTO) {
//		this.hexDTO = hexDTO;
//	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

}
