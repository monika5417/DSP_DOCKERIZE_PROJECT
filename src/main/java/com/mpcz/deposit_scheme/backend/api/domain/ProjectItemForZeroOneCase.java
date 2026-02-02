package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_ITEM_FOR_Z_O_C")
public class ProjectItemForZeroOneCase {

	@Id
	@SequenceGenerator(name = "PROJECT_ITEM_FOR_Z_O_C_SEQ", sequenceName = "PROJECT_ITEM_FOR_Z_O_C_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_ITEM_FOR_Z_O_C_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = true)
	private Long id;

	@JsonProperty("PROJECT_NUMBER")
	private String projectNumber;

	@JsonProperty("ITEM_CODE") 
	private String itemCode;

	@JsonProperty("DESCRIPTION")
	private String description;

	@JsonProperty("INV_UOM_CODE")
	private String invUomCode;

	@JsonProperty("PROJECT_UOM")
	private String projectUom;

	@JsonProperty("PROJECT_QTY")
	private Double projectQty;

	@JsonProperty("RETURN_QTY")
	private Double returnQty;

	@JsonProperty("BAL_QTY")
	private Double balQty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}



	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInvUomCode() {
		return invUomCode;
	}

	public void setInvUomCode(String invUomCode) {
		this.invUomCode = invUomCode;
	}

	public String getProjectUom() {
		return projectUom;
	}

	public void setProjectUom(String projectUom) {
		this.projectUom = projectUom;
	}

	public Double getProjectQty() {
		return projectQty;
	}

	public void setProjectQty(Double projectQty) {
		this.projectQty = projectQty;
	}

	public Double getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Double returnQty) {
		this.returnQty = returnQty;
	}

	public Double getBalQty() {
		return balQty;
	}

	public void setBalQty(Double balQty) {
		this.balQty = balQty;
	}



}
