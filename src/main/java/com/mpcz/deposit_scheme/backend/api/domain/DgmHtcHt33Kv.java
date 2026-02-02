package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "DgmHtcHt33Kv")
@Table(name = "Dgm_Htc_33Kv")
public @Data class DgmHtcHt33Kv  {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "Dgm_Htc_33Kv_SEQ", sequenceName = "Dgm_Htc_33Kv_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Dgm_Htc_33Kv_SEQ")
	@Column(name = "Dgm_Htc_33Kv_ID", columnDefinition = "serial", updatable = false)
	private Long dgmHtc33KvId;

	@Column(name = "Consumer_APPLICATION_NO")
	private String consumerAppNo;
	
	@Column(name = "Dgm_Name")
	private String dgmName;

}