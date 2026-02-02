package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "TDS_SECTION")
@Entity
public class TdsSection extends Auditable<Long>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "TDS_SECTION_SEQ", sequenceName = "TDS_SECTION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TDS_SECTION_SEQ")
	@Column(name = "TDS_ID")
	private Long tdsId;

	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerAppNo;

	@Column(name = "UNDER_SECTION_194")
	private String underSection194;

	@Column(name = "UNDER_SECTION_51")
	private String underSection51;

	@Column(name = "TDS_2")
	private String tds2;

	@Column(name = "TDS_10")
	private String tds10;

//	19-Aug-2025 Monika Rajpoot
	
	@Column(name = "UNDER_SECTION_194_C") // Only for Deposit case and only 2 % tds will be taken
	private String underSection194C;
	
	
	
	public String getUnderSection194C() {
		return underSection194C;
	}

	public void setUnderSection194C(String underSection194C) {
		this.underSection194C = underSection194C;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getTdsId() {
		return tdsId;
	}

	public void setTdsId(Long tdsId) {
		this.tdsId = tdsId;
	}

	public String getConsumerAppNo() {
		return consumerAppNo;
	}

	public void setConsumerAppNo(String consumerAppNo) {
		this.consumerAppNo = consumerAppNo;
	}

	public String getUnderSection194() {
		return underSection194;
	}

	public void setUnderSection194(String underSection194) {
		this.underSection194 = underSection194;
	}

	public String getUnderSection51() {
		return underSection51;
	}

	public void setUnderSection51(String underSection51) {
		this.underSection51 = underSection51;
	}

	public String getTds2() {
		return tds2;
	}

	public void setTds2(String tds2) {
		this.tds2 = tds2;
	}

	public String getTds10() {
		return tds10;
	}

	public void setTds10(String tds10) {
		this.tds10 = tds10;
	}

}
