package com.mpcz.deposit_scheme.backend.api.dto;
import java.math.BigDecimal;


//          Dto for sending data to shamshad
public class NgbDto {
	
	 private Long locationCode;
	    private String applicationNumber; // DSP APPLICATION NUMBER
	    private String connectionDate;
	    private String consumerName;
	    private String consumerNameH;
	    private String relativeName;
	    private String relation;
	    private String isBpl;
	    private String category;
	    private String isEmployee;
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
	    private Double contractDemand;
	    private String contractDemandUnit;
	    private String isSeasonal;
	    private Long purposeOfInstallationId;
	    private String purposeOfInstallation;
	    private String tarrifCode;
	    private Long subCategoryCode;
	    private String phase;
	    private String tcStartDate;
	    private String tcEndDate;
	    private String isGovernment;
	    private Double plotSize;
	    private String plotSizeUnit;
	    private String isXray;
	    private String isWeldingTransformerSurcharge;
	    private String isCapacitorSurcharge;
	    private String isDemandside;
	    private String isiMotorType;
	    private String isBeneficiary;
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
	    private String registrationFeeAmountMrNo;
	    private BigDecimal securityDepositAmount;
	    private String securityDepositAmountMrNo;
	    private String isAffiliatedConsumer;
	    private String affiliatedConsumerNo;
	    private String portalName;
	    private String propertyName;
	    private String propertyValue;
	    private Long iSamparkLocationCode;
	    private String oytTempApplicationNo;
	    
	    
	    
	    
		public String getOytTempApplicationNo() {
			return oytTempApplicationNo;
		}
		public void setOytTempApplicationNo(String oytTempApplicationNo) {
			this.oytTempApplicationNo = oytTempApplicationNo;
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
		public Long getiSamparkLocationCode() {
			return iSamparkLocationCode;
		}
		public void setiSamparkLocationCode(Long iSamparkLocationCode) {
			this.iSamparkLocationCode = iSamparkLocationCode;
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