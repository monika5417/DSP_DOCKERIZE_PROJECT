package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DGM_STC_REASON")  
public class DgmStcReason extends Auditable<Long>{
	private static final long serialVersionUID = 1L;

	   @Id
	    @SequenceGenerator(name = "DGM_STC_REASON_SEQ", sequenceName = "DGM_STC_REASON_SEQ", allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DGM_STC_REASON_SEQ")
	    @Column(name = "ID")
	    private Long id;
	   
	   @Column(name = "CONSUMER_APP_NO")
	    private String consumerAppNo;
	   
	   @Column(name="REASON")
	   private String reason;
	   
	   

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsumerAppNo() {
		return consumerAppNo;
	}

	public void setConsumerAppNo(String consumerAppNo) {
		this.consumerAppNo = consumerAppNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
  
	
}
