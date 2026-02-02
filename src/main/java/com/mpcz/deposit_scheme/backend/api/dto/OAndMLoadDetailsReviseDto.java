package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

public class OAndMLoadDetailsReviseDto {

	private String consumerAppNo;

	private BigDecimal oAndMLoad;

	private String oAndMLoadUnit;

	private BigDecimal oAndMReturnAmt;

	public String getConsumerAppNo() {
		return consumerAppNo;
	}

	public void setConsumerAppNo(String consumerAppNo) {
		this.consumerAppNo = consumerAppNo;
	}

	

	public String getoAndMLoadUnit() {
		return oAndMLoadUnit;
	}

	public void setoAndMLoadUnit(String oAndMLoadUnit) {
		this.oAndMLoadUnit = oAndMLoadUnit;
	}

	public BigDecimal getoAndMLoad() {
		return oAndMLoad;
	}

	public void setoAndMLoad(BigDecimal oAndMLoad) {
		this.oAndMLoad = oAndMLoad;
	}

	public BigDecimal getoAndMReturnAmt() {
		return oAndMReturnAmt;
	}

	public void setoAndMReturnAmt(BigDecimal oAndMReturnAmt) {
		this.oAndMReturnAmt = oAndMReturnAmt;
	}


	

}
