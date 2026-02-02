package com.mpcz.deposit_scheme.backend.api.dto;
import java.math.BigDecimal;

public class NscStagingDto {
	
    private String connectionDate;
    private String consumerName;
    private String consumerNameH;
    private String relativeName;
    private String relation;
    private Boolean isBPL;
    private String category;
    private Boolean isEmployee;
    private String address1;
    private String address2;
    private String address3;
    private String primaryMobileNo;
    private String aadhaarNo;
    private String tariffCategory;
    private String connectionType;
    private String meteringStatus;
    private String premiseType;
    private Double sanctionedLoad;
    private String sanctionedLoadUnit;
    private Integer contractDemand;
    private String contractDemandUnit;
    private Boolean isSeasonal;
    private Integer purposeOfInstallationId;
    private String purposeOfInstallation;
    private String tariffCode;
    private Integer subCategoryCode;
    private String phase;
    private String tcStartDate;
    private String tcEndDate;
    private Boolean isGovernment;
    private Double plotSize;
    private String plotSizeUnit;
    private Long locationCode;
    private Boolean isXray;
    private Boolean isWeldingTransformerSurcharge;
    private Boolean isCapacitorSurcharge;
    private Boolean isDemandside;
    private Boolean isiMotorType;
    private Boolean isBeneficiary;
    private String dtrName;
    private Long poleNo;
    private String poleLatitude;
    private String poleLongitude;
    private String feederName;
    private Double poleDistance;
    private String areaStatus;
    private String groupNo;
    private String readingDiaryNo;
    private String dateOfReg;
    private BigDecimal registrationFeeAmount;
    private String registrationFeeAmountMRNo;
    private BigDecimal securityDepositAmount;
    private String securityDepositAmountMRNo;
    private Boolean isAffiliatedConsumer;
    private String affiliatedConsumerNo;
    private String portalName;
    private String portalReferenceNo;
    private String propertyName;
    private String propertyValue;
    private String ngbToken;
    private Long ngbStagingId;
    private Long iSamparkLocationCode;
   
    
    
  
	public Long getPoleNo() {
		return poleNo;
	}
	public void setPoleNo(Long poleNo) {
		this.poleNo = poleNo;
	}
	public Double getPoleDistance() {
		return poleDistance;
	}
	public void setPoleDistance(Double poleDistance) {
		this.poleDistance = poleDistance;
	}
	public Long getiSamparkLocationCode() {
		return iSamparkLocationCode;
	}
	public void setiSamparkLocationCode(Long iSamparkLocationCode) {
		this.iSamparkLocationCode = iSamparkLocationCode;
	}
	public Long getNgbStagingId() {
		return ngbStagingId;
	}
	public void setNgbStagingId(Long ngbStagingId) {
		this.ngbStagingId = ngbStagingId;
	}
	public String getNgbToken() {
		return ngbToken;
	}
	public void setNgbToken(String ngbToken) {
		this.ngbToken = ngbToken;
	}
	public Integer getContractDemand() {
		return contractDemand;
	}
	public void setContractDemand(Integer contractDemand) {
		this.contractDemand = contractDemand;
	}
	public String getContractDemandUnit() {
		return contractDemandUnit;
	}
	public void setContractDemandUnit(String contractDemandUnit) {
		this.contractDemandUnit = contractDemandUnit;
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
	public String getAreaStatus() {
		return areaStatus;
	}
	public void setAreaStatus(String areaStatus) {
		this.areaStatus = areaStatus;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public Integer getPurposeOfInstallationId() {
		return purposeOfInstallationId;
	}
	public void setPurposeOfInstallationId(Integer purposeOfInstallationId) {
		this.purposeOfInstallationId = purposeOfInstallationId;
	}
	public String getPurposeOfInstallation() {
		return purposeOfInstallation;
	}
	public void setPurposeOfInstallation(String purposeOfInstallation) {
		this.purposeOfInstallation = purposeOfInstallation;
	}
	public String getTariffCode() {
		return tariffCode;
	}
	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}
	public Integer getSubCategoryCode() {
		return subCategoryCode;
	}
	public void setSubCategoryCode(Integer subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
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
	public Boolean getIsGovernment() {
		return isGovernment;
	}
	public void setIsGovernment(Boolean isGovernment) {
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

	public Long getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(Long locationCode) {
		this.locationCode = locationCode;
	}
	public String getDtrName() {
		return dtrName;
	}
	public void setDtrName(String dtrName) {
		this.dtrName = dtrName;
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
	
	public String getRegistrationFeeAmountMRNo() {
		return registrationFeeAmountMRNo;
	}
	public void setRegistrationFeeAmountMRNo(String registrationFeeAmountMRNo) {
		this.registrationFeeAmountMRNo = registrationFeeAmountMRNo;
	}
	
	public String getSecurityDepositAmountMRNo() {
		return securityDepositAmountMRNo;
	}
	public void setSecurityDepositAmountMRNo(String securityDepositAmountMRNo) {
		this.securityDepositAmountMRNo = securityDepositAmountMRNo;
	}
	public Boolean getIsAffiliatedConsumer() {
		return isAffiliatedConsumer;
	}
	public void setIsAffiliatedConsumer(Boolean isAffiliatedConsumer) {
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
	public String getPortalReferenceNo() {
		return portalReferenceNo;
	}
	public void setPortalReferenceNo(String portalReferenceNo) {
		this.portalReferenceNo = portalReferenceNo;
	}
	public BigDecimal getRegistrationFeeAmount() {
		return registrationFeeAmount;
	}
	public void setRegistrationFeeAmount(BigDecimal registrationFeeAmount) {
		this.registrationFeeAmount = registrationFeeAmount;
	}
	public BigDecimal getSecurityDepositAmount() {
		return securityDepositAmount;
	}
	public void setSecurityDepositAmount(BigDecimal securityDepositAmount) {
		this.securityDepositAmount = securityDepositAmount;
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
	public Boolean getIsBPL() {
		return isBPL;
	}
	public void setIsBPL(Boolean isBPL) {
		this.isBPL = isBPL;
	}
	public Boolean getIsEmployee() {
		return isEmployee;
	}
	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	public Boolean getIsSeasonal() {
		return isSeasonal;
	}
	public void setIsSeasonal(Boolean isSeasonal) {
		this.isSeasonal = isSeasonal;
	}
	public Boolean getIsXray() {
		return isXray;
	}
	public void setIsXray(Boolean isXray) {
		this.isXray = isXray;
	}
	public Boolean getIsWeldingTransformerSurcharge() {
		return isWeldingTransformerSurcharge;
	}
	public void setIsWeldingTransformerSurcharge(Boolean isWeldingTransformerSurcharge) {
		this.isWeldingTransformerSurcharge = isWeldingTransformerSurcharge;
	}
	public Boolean getIsCapacitorSurcharge() {
		return isCapacitorSurcharge;
	}
	public void setIsCapacitorSurcharge(Boolean isCapacitorSurcharge) {
		this.isCapacitorSurcharge = isCapacitorSurcharge;
	}
	public Boolean getIsDemandside() {
		return isDemandside;
	}
	public void setIsDemandside(Boolean isDemandside) {
		this.isDemandside = isDemandside;
	}

	public Boolean getIsiMotorType() {
		return isiMotorType;
	}
	public void setIsiMotorType(Boolean isiMotorType) {
		this.isiMotorType = isiMotorType;
	}
	public Boolean getIsBeneficiary() {
		return isBeneficiary;
	}
	public void setIsBeneficiary(Boolean isBeneficiary) {
		this.isBeneficiary = isBeneficiary;
	}

	
	
    // Getters and setters
    
}