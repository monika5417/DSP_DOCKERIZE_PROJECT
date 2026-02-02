package com.mpcz.deposit_scheme.backend.api.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAPPING_DP")
public class TappingDp {

    @Id
    @Column(name = "ID", length = 20)
    private Long id;

    @Column(name = "ITEM_CODE", length = 900)
    private String itemCode;

    @Column(name = "PARTICULARS_UNIT", length = 1024)
    private String particularsUnit;

    @Column(name = "UNIT", length = 400)
    private String unit;

    @Column(name = "FOR_RATE")
    private BigDecimal forRate;

    @Column(name = "HSN_OR_SAC_CODE", length = 50)
    private String hsnOrSacCode;

    @Column(name = "QTY")
    private BigDecimal qty;

    @Column(name = "TOTAL_AMOUNT_USING_FOR_RATES")
    private BigDecimal totalAmountUsingForRates;

    @Column(name = "ERECTION_RATES")
    private BigDecimal erectionRates;

    @Column(name = "TOTAL_AMT_OF_ERECTION")
    private BigDecimal totalAmtOfErection;

    @Column(name = "SCHEDULE_NO", length = 20)
    private String scheduleNo;

    @Column(name = "SCHEDULER_NAME", length = 200)
    private String schedulerName;

    // ======================
    // GETTERS & SETTERS
    // ======================

    

    public String getItemCode() {
        return itemCode;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	public void setScheduleNo(String scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

	public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getParticularsUnit() {
        return particularsUnit;
    }

    public void setParticularsUnit(String particularsUnit) {
        this.particularsUnit = particularsUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getForRate() {
        return forRate;
    }

    public void setForRate(BigDecimal forRate) {
        this.forRate = forRate;
    }

    public String getHsnOrSacCode() {
        return hsnOrSacCode;
    }

    public void setHsnOrSacCode(String hsnOrSacCode) {
        this.hsnOrSacCode = hsnOrSacCode;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getTotalAmountUsingForRates() {
        return totalAmountUsingForRates;
    }

    public void setTotalAmountUsingForRates(BigDecimal totalAmountUsingForRates) {
        this.totalAmountUsingForRates = totalAmountUsingForRates;
    }

    public BigDecimal getErectionRates() {
        return erectionRates;
    }

    public void setErectionRates(BigDecimal erectionRates) {
        this.erectionRates = erectionRates;
    }

    public BigDecimal getTotalAmtOfErection() {
        return totalAmtOfErection;
    }

    public void setTotalAmtOfErection(BigDecimal totalAmtOfErection) {
        this.totalAmtOfErection = totalAmtOfErection;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }
}