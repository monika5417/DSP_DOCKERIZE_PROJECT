package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "DgmHtcPtr")
@Table(name = "Dgm_Htc_Ptr")
public @Data class DgmHtcPtr {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "Dgm_Ptr_SEQ", sequenceName = "Dgm_Ptr_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Dgm_Ptr_SEQ")
	@Column(name = "Dgm_Htc_Ptr_ID", columnDefinition = "serial", updatable = false)
	private Long dgmHtcPtrId;

	@Column(name = "Consumer_APPLICATION_NO")
	private String consumerAppNo;
	
	@Column(name = "Dgm_Name")
	private String dgmName;
}