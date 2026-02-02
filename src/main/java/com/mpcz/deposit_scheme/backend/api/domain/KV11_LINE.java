package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KV11_LINE")
public class KV11_LINE {

    @Id
    @Column(name = "ID", length = 20, nullable = false)
    private Long id;

    @Column(name = "ITEM_CODE", length = 900)
    private String itemCode;

    @Column(name = "PARTICULARS", length = 1024)
    private String particulars;

    @Column(name = "UNIT", length = 400)
    private String unit;

    @Column(name = "FOR_RATE")
    private Long forRate;

    @Column(name = "QTY")
    private Long qty;

    @Column(name = "TOTAL_AMOUNT_USING_FOR_RATES")
    private Long totalAmountUsingForRates;

    @Column(name = "ERECTION_RATES")
    private Long erectionRates;

    @Column(name = "TOTAL_AMT_OF_ERECTION")
    private Long totalAmtOfErection;

    @Column(name = "SCHEDULE_NO", length = 20)
    private String scheduleNo;

    @Column(name = "SCHEDULER_NAME", length = 200)
    private String schedulerName;
    
    @Column(name="UPDATED_ITEM_CODE")
    private String updatedItemCode;

    
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUpdatedItemCode() {
		return updatedItemCode;
	}

	public void setUpdatedItemCode(String updatedItemCode) {
		this.updatedItemCode = updatedItemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getForRate() {
		return forRate;
	}

	public void setForRate(Long forRate) {
		this.forRate = forRate;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Long getTotalAmountUsingForRates() {
		return totalAmountUsingForRates;
	}

	public void setTotalAmountUsingForRates(Long totalAmountUsingForRates) {
		this.totalAmountUsingForRates = totalAmountUsingForRates;
	}

	public Long getErectionRates() {
		return erectionRates;
	}

	public void setErectionRates(Long erectionRates) {
		this.erectionRates = erectionRates;
	}

	public Long getTotalAmtOfErection() {
		return totalAmtOfErection;
	}

	public void setTotalAmtOfErection(Long totalAmtOfErection) {
		this.totalAmtOfErection = totalAmtOfErection;
	}

	public String getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

    
    
}