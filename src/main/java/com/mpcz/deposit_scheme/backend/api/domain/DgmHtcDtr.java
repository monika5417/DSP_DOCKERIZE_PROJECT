package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "DgmHtcDtr")
@Table(name = "Dgm_Htc_Dtr")
public @Data class DgmHtcDtr {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "Dgm_Dtr_SEQ", sequenceName = "Dgm_Dtr_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Dgm_Dtr_SEQ")
	@Column(name = "DGM_HTC_DTR_ID", columnDefinition = "serial", updatable = false)
	private Long dgmHtcDtrId;

	@Column(name = "Consumer_APPLICATION_NO")
	private String consumerAppNo;
	
	@Column(name = "Dgm_Name")
	private String dgmName;

}
