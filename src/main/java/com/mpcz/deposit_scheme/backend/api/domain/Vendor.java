package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "Vendor")
@Table(name = "VENDOR")
public @Data class Vendor extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "VENDOR_SEQ", sequenceName = "VENDOR_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VENDOR_SEQ")
	@Column(name = "VENDOR_ID", columnDefinition = "serial", updatable = false)
	private Long vendorId;

	@Column(name = "VENDOR_NAME")
	private  String vendorName;

	@Column(name = "VENDOR_CODE")
	private String vendorCode;
	
	@Column(name="MOBILE_NO")
	private String mobilNo;
	
	@Column(name="QC_PORTEL_VENDER_ID")
	private Long qcProtelVenderId;

}