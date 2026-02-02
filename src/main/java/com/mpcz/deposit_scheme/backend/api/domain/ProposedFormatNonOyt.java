package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PROPOSED_FORMATE_OYT")
public class ProposedFormatNonOyt {

	@Id
	@SequenceGenerator(name = "PROPOSED_FORMATE_OYT_SEQ",sequenceName = "PROPOSED_FORMATE_OYT_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PROPOSED_FORMATE_OYT_SEQ")
	@Column(name= "ID")
	private int id;
	
	@Column(name= "YEAR_OF_MANUFACTURRING")
	private String yearOfManufacturing;
	
	@Column(name = "MONTH_OF_MANUFACTURRING")
	private String monthOfManufacturing;
	
	@Column(name  = "APPLICATION_SCHEME_CODE")
	private String ApplicationSchemeCode;
	
	@Column(name = "CENTRAL_ZONE")
	private String CentralZone;
	
	@Column(name= "MANUFACTURING_SERIAL_NO")
	private String manufacturingSerialNo;
	
	
	@Column(name="APPLICATION_NUMBER")
	private String applicationNumbe;
	
	@Column(name="DTR_CODE")
	private String dtrcode;
	
	

	public String getDtrcode() {
		return dtrcode;
	}

	public void setDtrcode(String dtrcode) {
		this.dtrcode = dtrcode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYearOfManufacturing() {
		return yearOfManufacturing;
	}

	public void setYearOfManufacturing(String yearOfManufacturing) {
		this.yearOfManufacturing = yearOfManufacturing;
	}
	

	public String getMonthOfManufacturing() {
		return monthOfManufacturing;
	}

	public void setMonthOfManufacturing(String monthOfManufacturing) {
		this.monthOfManufacturing = monthOfManufacturing;
	}

	public String getApplicationSchemeCode() {
		return ApplicationSchemeCode;
	}

	public void setApplicationSchemeCode(String applicationSchemeCode) {
		ApplicationSchemeCode = applicationSchemeCode;
	}

	public String getCentralZone() {
		return CentralZone;
	}

	public void setCentralZone(String centralZone) {
		CentralZone = centralZone;
	}

	public String getManufacturingSerialNo() {
		return manufacturingSerialNo;
	}

	public void setManufacturingSerialNo(String manufacturingSerialNo) {
		this.manufacturingSerialNo = manufacturingSerialNo;
	}

	public String getApplicationNumbe() {
		return applicationNumbe;
	}

	public void setApplicationNumbe(String applicationNumbe) {
		this.applicationNumbe = applicationNumbe;
	}
	
	
	
	
	

	
}
