package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;


@Entity(name = "NscData")
@Table(name = "NSCDATA")
@Data
public class NscData extends Auditable<Long> {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "NSC_SEQ", sequenceName = "NSC_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NSC_SEQ")
	@Column(name = "NSC_ID", columnDefinition = "serial", updatable = false)
	private Long id;
	
	
	@Column(name = "APPLICATION_NUMBER")
	private String applicationNumber;

	@Column(name = "APPLICATION_LOAD")
	private String appliedLoad;

	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "COLONY_TYPE")
	private String colonyType;
	
	@Column(name = "CONSUMER_ADDRESS")
	private String consumerAddress;
	
	@Column(name = "CONSUMER_NAME")
	private String consumerName;
	
	@Column(name = "DATE_OF_APPLY")
	private Date dateOfApply;
	
	@Column(name = "DATE_OF_POST_DEPOSIT")
	private Date dateOfPostDeposit;
	
	@Column(name = "DC_CODE")
	private String dcCode;
	
	@Column(name = "DISTANCE_POLE")
	private String distanceFromLtPole;
	
	@Column(name = "DTRCODE")
	private String dtrCode;
	
	@Column(name = "DTRNAME")
	private String dtrName;
	
	@Column(name = "FEEDER_CODE")
	private String feederCode;
	
	@Column(name = "FEEDER_NAME")
	private String feederName;
	
	@Column(name = "FLOOR_COUNT")
	private String floorCount;
	
	@Column(name = "FLOOR_NUMBER")
	private String floorNo;
	
	@Column(name = "NSC_ID_DATA")
	private String nscId;
	
	@Column(name = "ISAMPARK_CODE")
	private String iSamparkDcCode;
	
	@Column(name = "ISMULTIFLOOR_COMPLEX")
	private String isMultiFloorComplex;
	
	@Column(name = "LOAD_UNIT")
	private String loadUnit;
	
	@Column(name = "METER_COST")
	private String meterCost;
	
	@Column(name = "PURPOSE")
	private String purpose;
	
	@Column(name = "REASON")
	private String reason;
	
	@Column(name = "REGISTRATION_FEES")
	private String registrationFees;
	
	@Column(name = "REMARK_OF_DC_USER")
	private String remarkOfDcUser;
	
	
	@Column(name = "REMARK_OF_SURVAYOR")
	private String remarkOfSurvayor;
	
	@Column(name = "SECURITY_DEPOSIT_CHARGES")
	private String securityDepositCharge;
	
	@Column(name = "SUPPLY_AFFORDING_CHARGE")
	private String supplyAffordingCharge;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getAppliedLoad() {
		return appliedLoad;
	}

	public void setAppliedLoad(String appliedLoad) {
		this.appliedLoad = appliedLoad;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getColonyType() {
		return colonyType;
	}

	public void setColonyType(String colonyType) {
		this.colonyType = colonyType;
	}

	public String getConsumerAddress() {
		return consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public Date getDateOfApply() {
		return dateOfApply;
	}

	public void setDateOfApply(Date dateOfApply) {
		this.dateOfApply = dateOfApply;
	}

	public Date getDateOfPostDeposit() {
		return dateOfPostDeposit;
	}

	public void setDateOfPostDeposit(Date dateOfPostDeposit) {
		this.dateOfPostDeposit = dateOfPostDeposit;
	}

	public String getDcCode() {
		return dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}

	public String getDistanceFromLtPole() {
		return distanceFromLtPole;
	}

	public void setDistanceFromLtPole(String distanceFromLtPole) {
		this.distanceFromLtPole = distanceFromLtPole;
	}

	public String getDtrCode() {
		return dtrCode;
	}

	public void setDtrCode(String dtrCode) {
		this.dtrCode = dtrCode;
	}

	public String getDtrName() {
		return dtrName;
	}

	public void setDtrName(String dtrName) {
		this.dtrName = dtrName;
	}

	public String getFeederCode() {
		return feederCode;
	}

	public void setFeederCode(String feederCode) {
		this.feederCode = feederCode;
	}

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public String getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(String floorCount) {
		this.floorCount = floorCount;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getNscId() {
		return nscId;
	}

	public void setNscId(String nscId) {
		this.nscId = nscId;
	}

	public String getiSamparkDcCode() {
		return iSamparkDcCode;
	}

	public void setiSamparkDcCode(String iSamparkDcCode) {
		this.iSamparkDcCode = iSamparkDcCode;
	}

	public String getIsMultiFloorComplex() {
		return isMultiFloorComplex;
	}

	public void setIsMultiFloorComplex(String isMultiFloorComplex) {
		this.isMultiFloorComplex = isMultiFloorComplex;
	}

	public String getLoadUnit() {
		return loadUnit;
	}

	public void setLoadUnit(String loadUnit) {
		this.loadUnit = loadUnit;
	}

	public String getMeterCost() {
		return meterCost;
	}

	public void setMeterCost(String meterCost) {
		this.meterCost = meterCost;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRegistrationFees() {
		return registrationFees;
	}

	public void setRegistrationFees(String registrationFees) {
		this.registrationFees = registrationFees;
	}

	public String getRemarkOfDcUser() {
		return remarkOfDcUser;
	}

	public void setRemarkOfDcUser(String remarkOfDcUser) {
		this.remarkOfDcUser = remarkOfDcUser;
	}

	public String getRemarkOfSurvayor() {
		return remarkOfSurvayor;
	}

	public void setRemarkOfSurvayor(String remarkOfSurvayor) {
		this.remarkOfSurvayor = remarkOfSurvayor;
	}

	public String getSecurityDepositCharge() {
		return securityDepositCharge;
	}

	public void setSecurityDepositCharge(String securityDepositCharge) {
		this.securityDepositCharge = securityDepositCharge;
	}

	public String getSupplyAffordingCharge() {
		return supplyAffordingCharge;
	}

	public void setSupplyAffordingCharge(String supplyAffordingCharge) {
		this.supplyAffordingCharge = supplyAffordingCharge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
	
	
	
}
