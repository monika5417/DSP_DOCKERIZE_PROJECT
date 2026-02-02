package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CONNECTION_PRADAY")
public class ConnectionPraday extends Auditable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CONNECTION_PRADAY_SEQ", sequenceName = "CONNECTION_PRADAY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONNECTION_PRADAY_SEQ")
	@Column(name = "ID", columnDefinition = "serial", updatable = false)
	private Long id;

	@Column(name = "LOCATION_CODE")
	private String locationCode;

	@Column(name = "CONNECTION_DATE")
	private String connectionDate;

	@Column(name = "PRIMARY_MOBILE_NO")
	private String primaryMobileNo;

	@Column(name = "TARIFF_CATEGORY")
	private String tariffCategory;

	@Column(name = "CREATED_ON")
	private String createdOn;

	@Column(name = "PREMISE_TYPE")
	private String premiseType;

	@Column(name = "CONTRACT_DEMAND")
	private Double contractDemand;

	@Column(name = "SANCTIONED_LOAD_UNIT")
	private String sanctionedLoadUnit;

	@Column(name = "CONTRACT_DEMAND_UNIT")
	private String contractDemandUnit;

	@Column(name = "CONSUMER_NAME")
	private String consumerName;

	@Column(name = "READING_DIARY_NO")
	private String readingDiaryNo;

	@Column(name = "SANCTIONED_LOAD")
	private Double sanctionedLoad;

	@Column(name = "TARIFF_CODE")
	private String tariffCode;

	@Column(name = "FEEDER_NAME")
	private String feederName;

	@Column(name = "GROUP_NO")
	private String groupNo;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PURPOSE_OF_INSTALLATION")
	private String purposeOfInstallation;

	@Column(name = "METERING_STATUS")
	private String meteringStatus;

	@Column(name = "CONNECTION_TYPE")
	private String connectionType;

	@Column(name = "DTR_NAME")
	private String dtrName;

	@Column(name = "IVRS")
	private String ivrs;

	@Column(name = "PHASE")
	private String phase;

	@Column(name="CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;
	
	
	public String getConnectionDate() {
		return connectionDate;
	}

	public void setConnectionDate(String connectionDate) {
		this.connectionDate = connectionDate;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}

	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}



	public String getPrimaryMobileNo() {
		return primaryMobileNo;
	}

	public void setPrimaryMobileNo(String primaryMobileNo) {
		this.primaryMobileNo = primaryMobileNo;
	}

	public String getTariffCategory() {
		return tariffCategory;
	}

	public void setTariffCategory(String tariffCategory) {
		this.tariffCategory = tariffCategory;
	}

	
	public String getPremiseType() {
		return premiseType;
	}

	public void setPremiseType(String premiseType) {
		this.premiseType = premiseType;
	}

	public Double getContractDemand() {
		return contractDemand;
	}

	public void setContractDemand(Double contractDemand) {
		this.contractDemand = contractDemand;
	}

	public String getSanctionedLoadUnit() {
		return sanctionedLoadUnit;
	}

	public void setSanctionedLoadUnit(String sanctionedLoadUnit) {
		this.sanctionedLoadUnit = sanctionedLoadUnit;
	}

	public String getContractDemandUnit() {
		return contractDemandUnit;
	}

	public void setContractDemandUnit(String contractDemandUnit) {
		this.contractDemandUnit = contractDemandUnit;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getReadingDiaryNo() {
		return readingDiaryNo;
	}

	public void setReadingDiaryNo(String readingDiaryNo) {
		this.readingDiaryNo = readingDiaryNo;
	}

	public Double getSanctionedLoad() {
		return sanctionedLoad;
	}

	public void setSanctionedLoad(Double sanctionedLoad) {
		this.sanctionedLoad = sanctionedLoad;
	}

	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPurposeOfInstallation() {
		return purposeOfInstallation;
	}

	public void setPurposeOfInstallation(String purposeOfInstallation) {
		this.purposeOfInstallation = purposeOfInstallation;
	}

	public String getMeteringStatus() {
		return meteringStatus;
	}

	public void setMeteringStatus(String meteringStatus) {
		this.meteringStatus = meteringStatus;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getDtrName() {
		return dtrName;
	}

	public void setDtrName(String dtrName) {
		this.dtrName = dtrName;
	}

	public String getIvrs() {
		return ivrs;
	}

	public void setIvrs(String ivrs) {
		this.ivrs = ivrs;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	
	
	// Getters and setters
}
