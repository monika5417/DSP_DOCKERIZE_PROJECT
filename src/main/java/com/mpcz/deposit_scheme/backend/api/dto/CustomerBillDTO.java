package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@lombok.Data
@XmlRootElement(name = "MPPKVVCL-Bhopal")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerBillDTO {
	 
	
	@NotNull
	private String consumerApplicationNumber;
	
	
	private String totalAmount;
	
	
	private String tranasactionNumber;

	
}
