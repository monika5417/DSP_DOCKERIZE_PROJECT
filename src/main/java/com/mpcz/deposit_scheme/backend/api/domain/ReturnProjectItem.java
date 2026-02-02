package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name= "RETURN_PROJECT_ITEM")
public class ReturnProjectItem extends Auditable<Long>{
	
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name="REJECT_PROJECT_ITEM_SEQ",sequenceName =  "REJECT_PROJECT_ITEM_SEQ",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "REJECT_PROJECT_ITEM_SEQ")
	private Integer id;
	
	@Column(name = "PROJECT_NUMBER")
    private String projectNumber;
	
	@Column(name="ITEM_CODE")
    private String itemCode;
	
	@Column(name = "DESCRIPTION")	
    private String description; 
	
	@Column(name = "INV_UOM_CODE")
    private String invUomCode;
	
	@Column(name = "PROJECT_UOM")
    private String projectUom;
	
	@Column(name = "PROJECT_QTY")
    private double projectQty;
	
	@Column(name = "RETURN_QTY")
    private double returnQty;
	
	@Column(name = "BAL_QTY")
    private double balQty;

    // Getters and Setters
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

    public double getProjectQty() {
        return projectQty;
    }

    public void setProjectQty(double projectQty) {
        this.projectQty = projectQty;
    }

    public double getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(double returnQty) {
        this.returnQty = returnQty;
    }

    public double getBalQty() {
        return balQty;
    }

    public void setBalQty(double balQty) {
        this.balQty = balQty;
    }
}
