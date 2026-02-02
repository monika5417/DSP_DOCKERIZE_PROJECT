package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.SequenceGenerators;

@Entity(name= "OYT_14")
public class OytOf14 {
	
	

	@Id
	@SequenceGenerator(name = "Oyt_14_s",sequenceName = "Oyt_14_s",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Oyt_14_s")
	@Column(name ="ID")
	private Integer id;
	
	@Column(name ="IVRS_NO")
	private String ivrsNo;
	
	
	@Column(name="LOAD")
	private int load;
	
	@Column(name="LOAD_UNIT")
	private int loadUnit;
	
	@Column(name="CON_APP_NO")
	private String consumerApplciationNo;
	
	
	


	public String getIvrsNo() {
		return ivrsNo;
	}


	public void setIvrsNo(String ivrsNo) {
		this.ivrsNo = ivrsNo;
	}


	public int getLoadUnit() {
		return loadUnit;
	}


	public void setLoadUnit(int loadUnit) {
		this.loadUnit = loadUnit;
	}


	public String getConsumerApplciationNo() {
		return consumerApplciationNo;
	}


	public void setConsumerApplciationNo(String consumerApplciationNo) {
		this.consumerApplciationNo = consumerApplciationNo;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}





	public int getLoad() {
		return load;
	}


	public void setLoad(int load) {
		this.load = load;
	}
	
	
	
	
	
}
