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
@Table(name = "NGB_STAGING_DATA")
public class NgbStagingData extends Auditable<Long> {

	@Id
	@SequenceGenerator(name = "NGB_STAGING_DATA_SEQ", sequenceName = "NGB_STAGING_DATA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NGB_STAGING_DATA_SEQ")
	@Column(name = "NGB_ID", columnDefinition = "serial", updatable = false)
	private Long ngbId;
    
	@Column(name = "LOCATION_CODE")
    private Long locationCode;

    @Column(name = "APPLICATION_NUMBER")
    private String applicationNumber; // DSP APPLICATION NUMBER

    @Column(name = "CONNECTION_DATE")
    private String connectionDate;

    @Column(name = "CONSUMER_NAME")
    private String consumerName;

    @Column(name = "CONSUMER_NAME_H")
    private String consumerNameH;

    @Column(name = "RELATIVE_NAME")
    private String relativeName;

    @Column(name = "RELATION")
    private String relation;

    @Column(name = "IS_BPL")
    private String isBpl;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "IS_EMPLOYEE")
    private String isEmployee;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "ADDRESS3")
    private String address3;

    @Column(name = "PRIMARY_MOBILE_NO")
    private String primaryMobileNo;

    @Column(name = "AADHAAR_NO")
    private String aadhaarNo;

    @Column(name = "TARIFF_CATEGORY")
    private String tariffCategory;

    @Column(name = "CONNECTION_TYPE")
    private String connectionType;

    @Column(name = "METERING_STATUS")
    private String meteringStatus;

    @Column(name = "PREMISE_TYPE")
    private String premiseType;

    @Column(name = "SANCTIONED_LOAD")
    private Double sanctionedLoad;

    @Column(name = "SANCTIONED_LOAD_UNIT")
    private String sanctionedLoadUnit;

    @Column(name = "CONTRACT_DEMAND")
    private Double contractDemand;

    @Column(name = "CONTRACT_DEMAND_UNIT")
    private String contractDemandUnit;

    @Column(name = "IS_SEASONAL")
    private String isSeasonal;

    @Column(name = "PURPOSE_OF_INSTALLATION_ID")
    private Long purposeOfInstallationId;

    @Column(name = "PURPOSE_OF_INSTALLATION")
    private String purposeOfInstallation;

    @Column(name = "TARIFF_CODE")
    private String tarrifCode;

    @Column(name = "SUB_CATEGORY_CODE")
    private Long subCategoryCode;

    @Column(name = "PHASE")
    private String phase;

    @Column(name = "TC_START_DATE")
    private String tcStartDate;

    @Column(name = "TC_END_DATE")
    private String tcEndDate;

    @Column(name = "IS_GOVERNMENT")
    private String isGovernment;

    @Column(name = "PLOT_SIZE")
    private Double plotSize;

    @Column(name = "PLOT_SIZE_UNIT")
    private String plotSizeUnit;

    @Column(name = "IS_XRAY")
    private String isXray;

    @Column(name = "IS_WELDING_TRANSFORMER")
    private String isWeldingTransformerSurcharge;

    @Column(name = "IS_CAPACITOR_SURCHARGE")
    private String isCapacitorSurcharge;

    @Column(name = "IS_DEMANDSIDE")
    private String isDemandside;

    @Column(name = "ISI_MOTOR_TYPE")
    private String isiMotorType;

    @Column(name = "IS_BENEFICIARY")
    private String isBeneficiary;

    @Column(name = "DTR_NAME")
    private String dtrName;

    @Column(name = "POLE_NO")
    private Long poleNo;

    @Column(name = "POLE_LATITUDE")
    private String poleLatitude;

    @Column(name = "POLE_LONGITUDE")
    private String poleLongitude;

    @Column(name = "FEEDER_NAME")
    private String feederName;

    @Column(name = "POLE_DISTANCE")
    private Double poleDistance;

    @Column(name = "AREA_STATUS")
    private String areaStatus;

    @Column(name = "GROUP_NO")
    private String groupNo;

    @Column(name = "READING_DIARY_NO")
    private String readingDiaryNo;

    @Column(name = "DATE_OF_REG")
    private String dateOfReg;

    @Column(name = "REGISTRATION_FEE_AMOUNT")
    private BigDecimal registrationFeeAmount;

    @Column(name = "REGISTRATION_MR_NO")
    private String registrationFeeAmountMrNo;

    @Column(name = "SECURITY_DEPOSIT_AMOUNT")
    private BigDecimal securityDepositAmount;

    @Column(name = "SECURITY_MR_NO")
    private String securityDepositAmountMrNo;

    @Column(name = "IS_AFFILIATED_CONSUMER")
    private String isAffiliatedConsumer;

    @Column(name = "AFFILIATED_CONSUMER_NO")
    private String affiliatedConsumerNo;

    @Column(name = "PORTAL_NAME")
    private String portalName;

    @Column(name = "PROPERTY_NAME")
    private String propertyName;

    @Column(name = "PROPERTY_VALUE")
    private String propertyValue;
    
    @Column(name="NSC_APP_ID")
    private String nscAppId;
    
    @Column(name="NGB_CONSUMER_NO")
    private String ngbConsumerNo;
    
    @Column(name="NGB_PUSH_DATE")
    private String ngbPushDate;
    
   
    
    
	public String getNscAppId() {
		return nscAppId;
	}

	public void setNscAppId(String nscAppId) {
		this.nscAppId = nscAppId;
	}

	public String getNgbPushDate() {
		return ngbPushDate;
	}

	public void setNgbPushDate(String ngbPushDate) {
		this.ngbPushDate = ngbPushDate;
	}

	public Long getNgbId() {
		return ngbId;
	}

	public void setNgbId(Long ngbId) {
		this.ngbId = ngbId;
	}

	public Long getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(Long locationCode) {
		this.locationCode = locationCode;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getConnectionDate() {
		return connectionDate;
	}

	public void setConnectionDate(String connectionDate) {
		this.connectionDate = connectionDate;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerNameH() {
		return consumerNameH;
	}

	public void setConsumerNameH(String consumerNameH) {
		this.consumerNameH = consumerNameH;
	}

	public String getRelativeName() {
		return relativeName;
	}

	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getIsBpl() {
		return isBpl;
	}

	public void setIsBpl(String isBpl) {
		this.isBpl = isBpl;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getPrimaryMobileNo() {
		return primaryMobileNo;
	}

	public void setPrimaryMobileNo(String primaryMobileNo) {
		this.primaryMobileNo = primaryMobileNo;
	}

	public String getAadhaarNo() {
		return aadhaarNo;
	}

	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}

	public String getTariffCategory() {
		return tariffCategory;
	}

	public void setTariffCategory(String tariffCategory) {
		this.tariffCategory = tariffCategory;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getMeteringStatus() {
		return meteringStatus;
	}

	public void setMeteringStatus(String meteringStatus) {
		this.meteringStatus = meteringStatus;
	}

	public String getPremiseType() {
		return premiseType;
	}

	public void setPremiseType(String premiseType) {
		this.premiseType = premiseType;
	}

	public Double getSanctionedLoad() {
		return sanctionedLoad;
	}

	public void setSanctionedLoad(Double sanctionedLoad) {
		this.sanctionedLoad = sanctionedLoad;
	}

	public String getSanctionedLoadUnit() {
		return sanctionedLoadUnit;
	}

	public void setSanctionedLoadUnit(String sanctionedLoadUnit) {
		this.sanctionedLoadUnit = sanctionedLoadUnit;
	}

	public Double getContractDemand() {
		return contractDemand;
	}

	public void setContractDemand(Double contractDemand) {
		this.contractDemand = contractDemand;
	}

	public String getContractDemandUnit() {
		return contractDemandUnit;
	}

	public void setContractDemandUnit(String contractDemandUnit) {
		this.contractDemandUnit = contractDemandUnit;
	}

	public String getIsSeasonal() {
		return isSeasonal;
	}

	public void setIsSeasonal(String isSeasonal) {
		this.isSeasonal = isSeasonal;
	}

	

	public String getPurposeOfInstallation() {
		return purposeOfInstallation;
	}

	public void setPurposeOfInstallation(String purposeOfInstallation) {
		this.purposeOfInstallation = purposeOfInstallation;
	}

	
	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getTcStartDate() {
		return tcStartDate;
	}

	public void setTcStartDate(String tcStartDate) {
		this.tcStartDate = tcStartDate;
	}

	public String getTcEndDate() {
		return tcEndDate;
	}

	public void setTcEndDate(String tcEndDate) {
		this.tcEndDate = tcEndDate;
	}

	public String getIsGovernment() {
		return isGovernment;
	}

	public void setIsGovernment(String isGovernment) {
		this.isGovernment = isGovernment;
	}

	public Double getPlotSize() {
		return plotSize;
	}

	public void setPlotSize(Double plotSize) {
		this.plotSize = plotSize;
	}

	public String getPlotSizeUnit() {
		return plotSizeUnit;
	}

	public void setPlotSizeUnit(String plotSizeUnit) {
		this.plotSizeUnit = plotSizeUnit;
	}

	public String getIsXray() {
		return isXray;
	}

	public void setIsXray(String isXray) {
		this.isXray = isXray;
	}

	public String getIsWeldingTransformerSurcharge() {
		return isWeldingTransformerSurcharge;
	}

	public void setIsWeldingTransformerSurcharge(String isWeldingTransformerSurcharge) {
		this.isWeldingTransformerSurcharge = isWeldingTransformerSurcharge;
	}

	public String getIsCapacitorSurcharge() {
		return isCapacitorSurcharge;
	}

	public void setIsCapacitorSurcharge(String isCapacitorSurcharge) {
		this.isCapacitorSurcharge = isCapacitorSurcharge;
	}

	public String getIsDemandside() {
		return isDemandside;
	}

	public void setIsDemandside(String isDemandside) {
		this.isDemandside = isDemandside;
	}

	public String getIsiMotorType() {
		return isiMotorType;
	}

	public void setIsiMotorType(String isiMotorType) {
		this.isiMotorType = isiMotorType;
	}

	public String getIsBeneficiary() {
		return isBeneficiary;
	}

	public void setIsBeneficiary(String isBeneficiary) {
		this.isBeneficiary = isBeneficiary;
	}

	public String getDtrName() {
		return dtrName;
	}

	public void setDtrName(String dtrName) {
		this.dtrName = dtrName;
	}

	public Long getPoleNo() {
		return poleNo;
	}

	public void setPoleNo(Long poleNo) {
		this.poleNo = poleNo;
	}

	public String getPoleLatitude() {
		return poleLatitude;
	}

	public void setPoleLatitude(String poleLatitude) {
		this.poleLatitude = poleLatitude;
	}

	public String getPoleLongitude() {
		return poleLongitude;
	}

	public void setPoleLongitude(String poleLongitude) {
		this.poleLongitude = poleLongitude;
	}

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public Double getPoleDistance() {
		return poleDistance;
	}

	public void setPoleDistance(Double poleDistance) {
		this.poleDistance = poleDistance;
	}

	public String getAreaStatus() {
		return areaStatus;
	}

	public void setAreaStatus(String areaStatus) {
		this.areaStatus = areaStatus;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getReadingDiaryNo() {
		return readingDiaryNo;
	}

	public void setReadingDiaryNo(String readingDiaryNo) {
		this.readingDiaryNo = readingDiaryNo;
	}

	public String getDateOfReg() {
		return dateOfReg;
	}

	public void setDateOfReg(String dateOfReg) {
		this.dateOfReg = dateOfReg;
	}

	public BigDecimal getRegistrationFeeAmount() {
		return registrationFeeAmount;
	}

	public void setRegistrationFeeAmount(BigDecimal registrationFeeAmount) {
		this.registrationFeeAmount = registrationFeeAmount;
	}

	public String getRegistrationFeeAmountMrNo() {
		return registrationFeeAmountMrNo;
	}

	public void setRegistrationFeeAmountMrNo(String registrationFeeAmountMrNo) {
		this.registrationFeeAmountMrNo = registrationFeeAmountMrNo;
	}

	public BigDecimal getSecurityDepositAmount() {
		return securityDepositAmount;
	}

	public void setSecurityDepositAmount(BigDecimal securityDepositAmount) {
		this.securityDepositAmount = securityDepositAmount;
	}

	public String getSecurityDepositAmountMrNo() {
		return securityDepositAmountMrNo;
	}

	public void setSecurityDepositAmountMrNo(String securityDepositAmountMrNo) {
		this.securityDepositAmountMrNo = securityDepositAmountMrNo;
	}

	public String getIsAffiliatedConsumer() {
		return isAffiliatedConsumer;
	}

	public void setIsAffiliatedConsumer(String isAffiliatedConsumer) {
		this.isAffiliatedConsumer = isAffiliatedConsumer;
	}

	public String getAffiliatedConsumerNo() {
		return affiliatedConsumerNo;
	}

	public void setAffiliatedConsumerNo(String affiliatedConsumerNo) {
		this.affiliatedConsumerNo = affiliatedConsumerNo;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}



	public String getNgbConsumerNo() {
		return ngbConsumerNo;
	}

	public void setNgbConsumerNo(String ngbConsumerNo) {
		this.ngbConsumerNo = ngbConsumerNo;
	}

	public Long getPurposeOfInstallationId() {
		return purposeOfInstallationId;
	}

	public void setPurposeOfInstallationId(Long purposeOfInstallationId) {
		this.purposeOfInstallationId = purposeOfInstallationId;
	}

	public String getTarrifCode() {
		return tarrifCode;
	}

	public void setTarrifCode(String tarrifCode) {
		this.tarrifCode = tarrifCode;
	}

	public Long getSubCategoryCode() {
		return subCategoryCode;
	}

	public void setSubCategoryCode(Long subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}


    

  
}
