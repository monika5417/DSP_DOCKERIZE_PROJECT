
package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ERP_ESTIMATE_CALCULATION")
public class ErpEstimateCalculation {

	@Id
	@SequenceGenerator(name = "ERP_ESTIMATE_CALCULATION_SEQ",sequenceName = "ERP_ESTIMATE_CALCULATION_SEQ",allocationSize =1 )
	@GeneratedValue( strategy = GenerationType.IDENTITY,generator = "ERP_ESTIMATE_CALCULATION_SEQ" )
	@Column(name = "ERP_ESTIMATE_CALCULATION_ID")
	private int id;
	
	
	
	@Column(name = "KV_LINE_33" )
	public String Kvline33;  // A 1.1
	
	@Column(name = "KV_LINE_11" )
	public String lvline11;
	
	@Column(name = "KV_LINE_33_ABOVE" )
	public String kvline33Above;
	
	@Column(name = "D_POLE_REQUERMENT" )
	public String DpoleRequerment; //B 1.1    minimun 1 pole is mandatory
	
	@Column(name = "DTR_23_63" )
	public String dtr25or63;
	
	@Column(name = "CONSUMER_APPLICATION_NUMBER" )
	public String consumerApplicationNumber;

	public String getKvline33() {
		return Kvline33;
	}

	public void setKvline33(String kvline33) {
		Kvline33 = kvline33;
	}

	public String getLvline11() {
		return lvline11;
	}

	public void setLvline11(String lvline11) {
		this.lvline11 = lvline11;
	}

	public String getKvline33Above() {
		return kvline33Above;
	}

	public void setKvline33Above(String kvline33Above) {
		this.kvline33Above = kvline33Above;
	}

	public String getDpoleRequerment() {
		return DpoleRequerment;
	}

	public void setDpoleRequerment(String dpoleRequerment) {
		DpoleRequerment = dpoleRequerment;
	}

	public String getDtr25or63() {
		return dtr25or63;
	}

	public void setDtr25or63(String dtr25or63) {
		this.dtr25or63 = dtr25or63;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}
	
	
	
	
	
	
	
	
	
	
}
