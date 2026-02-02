package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AMOUNT_MATCH")
public class AmountMatch {

	@Id
	@SequenceGenerator(name = "AMOUNT_MATCH_SEQ", sequenceName = "AMOUNT_MATCH_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AMOUNT_MATCH_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private long id;
	
	
	@Column(name="CONSUMER_APP_NO")
	private String ConsuemrAppNo;
	
	@Column(name="ERP_NO")
	private String erpNo;
	
	@Column(name="SUP_AMT")
	private BigDecimal supAmt;
	
	@Column(name="CGST")
	private BigDecimal cgst;
	
	@Column(name="SGST")
	private BigDecimal sgst;
	
	@Column(name="TOTAL_SUP_AMT")
	private BigDecimal totalSupAmt;
	
	
	@Column(name="R_SUP_AMT")
	private BigDecimal roundSupAmt;
	
	@Column(name="R_CGST")
	private BigDecimal rCgst;
	
	@Column(name="R_SGST")
	private BigDecimal rSgst;
	
	@Column(name="TOTAL_R_SUP_AMT")
	private BigDecimal totalRoundSupAmt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConsuemrAppNo() {
		return ConsuemrAppNo;
	}

	public void setConsuemrAppNo(String consuemrAppNo) {
		ConsuemrAppNo = consuemrAppNo;
	}


	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

	public BigDecimal getSupAmt() {
		return supAmt;
	}

	public void setSupAmt(BigDecimal supAmt) {
		this.supAmt = supAmt;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getTotalSupAmt() {
		return totalSupAmt;
	}

	public void setTotalSupAmt(BigDecimal totalSupAmt) {
		this.totalSupAmt = totalSupAmt;
	}

	public BigDecimal getRoundSupAmt() {
		return roundSupAmt;
	}

	public void setRoundSupAmt(BigDecimal roundSupAmt) {
		this.roundSupAmt = roundSupAmt;
	}

	public BigDecimal getrCgst() {
		return rCgst;
	}

	public void setrCgst(BigDecimal rCgst) {
		this.rCgst = rCgst;
	}

	public BigDecimal getrSgst() {
		return rSgst;
	}

	public void setrSgst(BigDecimal rSgst) {
		this.rSgst = rSgst;
	}

	public BigDecimal getTotalRoundSupAmt() {
		return totalRoundSupAmt;
	}

	public void setTotalRoundSupAmt(BigDecimal totalRoundSupAmt) {
		this.totalRoundSupAmt = totalRoundSupAmt;
	}
	
	
}
