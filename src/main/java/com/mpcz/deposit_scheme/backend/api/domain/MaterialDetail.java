package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_DETAILS")
public class MaterialDetail {

	@Id
	@SequenceGenerator(name = "MATERIAL_DETAISL_SEQ", sequenceName = "MATERIAL_DETAISL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MATERIAL_DETAISL_SEQ")
	@Column(name = "MATERIAL_DETAILS_ID")
	private Long MaterialDetailsId;

	@Column(name = "SERIAL_NO")
	private int serialNo;

	@Column(name = "MATERIAL_SERIAL_NO")
	private String materialSerialNo;

	@Column(name = "BATCH_NO")
	private String batchNo;

	@Column(name = "FINAL_REMARK")
	private String finalRemark;

	@Column(name = "CONSUMEMR_APPLICATION_NUMBER")
	private String consumerApplicationNumber;

	public Long getMaterialDetailsId() {
		return MaterialDetailsId;
	}

	public void setMaterialDetailsId(Long materialDetailsId) {
		MaterialDetailsId = materialDetailsId;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getMaterialSerialNo() {
		return materialSerialNo;
	}

	public void setMaterialSerialNo(String materialSerialNo) {
		this.materialSerialNo = materialSerialNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getFinalRemark() {
		return finalRemark;
	}

	public void setFinalRemark(String finalRemark) {
		this.finalRemark = finalRemark;
	}

	public String getConsumerApplicationNumber() {
		return consumerApplicationNumber;
	}

	public void setConsumerApplicationNumber(String consumerApplicationNumber) {
		this.consumerApplicationNumber = consumerApplicationNumber;
	}

	// Getters and Setters (or use Lombok @Data)

}
