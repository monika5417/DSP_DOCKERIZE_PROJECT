package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "ContractorQc")
@Table(name = "CONTRACTOR")
public @Data class ContractorQc  {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONTRACTOR_DETAIL_SEQ", sequenceName = "CONTRACTOR_DETAIL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTOR_DETAIL_SEQ")
	@Column(name = "CONTRACTOR_ID", columnDefinition = "serial", updatable = false)
	private Long contractorId;

	

	@Column(name = "BID_ORDER_AT")
	public String bidOrderAt;

	@Column(name = "BID_AMOUNT")
	public Double bidAmount;

	@Column(name = "CONTRACTOR_NAME")
	public String contractorName;

	@Column(name = "IS_REJECTED")
	public Boolean  isRejected ;

	

}

