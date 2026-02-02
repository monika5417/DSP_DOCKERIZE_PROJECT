package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RETURN_MATERIAL_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnMaterialData {

	@Id
	@SequenceGenerator(name = "RETURN_MATERIAL_DATA_SEQ", sequenceName = "RETURN_MATERIAL_DATA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RETURN_MATERIAL_DATA_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private long id;
	
	@Column(name="CONSUMER_APPLIATION_NUMBER")
	private String consumerApplicationNumber;
	
	@Column(name="ROW_NAME")
	private String rowName;
	
	@Column(name="ACCOUNT_CODE")
	private String accountCode;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID")
	private ReturnRowData rowData;

	
	
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	public ReturnRowData getRowData() {
		return rowData;
	}

	public void setRowData(ReturnRowData rowData) {
		this.rowData = rowData;
	}
	
	
}
