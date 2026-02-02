package com.mpcz.deposit_scheme.backend.api.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RETURN_ROW_DATA")
@NoArgsConstructor
@AllArgsConstructor
public class ReturnRowData {
	
	@Id
	@SequenceGenerator(name = "RETURN_ROW_DATA_SEQ", sequenceName = "RETURN_ROW_DATA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RETURN_ROW_DATA_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
    @Column(name= "YEAR_0F_INSTALLATION")
	private String yearOfInstallation;
    
    @Column(name= "ASSET_NO")
    private String assetNo;
    
    @Column(name= "LOCATION")
    private String location;
    
    @Column(name= "QUANTITY")
    private String quantity;
    
    @Column(name= "VALUE_OF_TIME_OF_INSTALLATION")
    private String valueOfTimeOfInstallation;
    
    @Column(name= "CONSUMER_APPLICATION_NUMBER")
    private String consumerApplicationNumber;
    
    @Column(name="ACCOUNT_CODE")
	private String accountCode;
    
    
    

	public String getAccountCode() {
		return accountCode;
	}




	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}




	public String getYearOfInstallation() {
		return yearOfInstallation;
	}




	public void setYearOfInstallation(String yearOfInstallation) {
		this.yearOfInstallation = yearOfInstallation;
	}




	public String getAssetNo() {
		return assetNo;
	}




	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}




	public String getLocation() {
		return location;
	}




	public void setLocation(String location) {
		this.location = location;
	}




	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}




	public String getValueOfTimeOfInstallation() {
		return valueOfTimeOfInstallation;
	}




	public void setValueOfTimeOfInstallation(String valueOfTimeOfInstallation) {
		this.valueOfTimeOfInstallation = valueOfTimeOfInstallation;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}




	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}
    
    
    

}
