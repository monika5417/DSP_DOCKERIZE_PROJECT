package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.checkerframework.checker.fenum.qual.AwtColorSpace;

@Entity
@Table(name = "KVA_63_DTR")
public class Kva63Dtr {
	
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "ITEM_CODE")
	private String itemcode;
	
	
	@Column(name =  "ITEM")
	private String itemName;
	
	@Column(name="QTY")
	private int qty;
	
	@Column(name = "RATE")
	private BigDecimal RATE;
	
	
	@Column(name ="UNIT")
	private String unit;
	
	
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@Column(name="ERECTION_RATE")
	private BigDecimal erectionRate;
	
	@Column(name="COST")
	private BigDecimal cost;
	
	@Transient
	private BigDecimal totalCost;
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getRATE() {
		return RATE;
	}

	public void setRATE(BigDecimal rATE) {
		RATE = rATE;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getErectionRate() {
		return erectionRate;
	}

	public void setErectionRate(BigDecimal erectionRate) {
		this.erectionRate = erectionRate;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
	
	

}
