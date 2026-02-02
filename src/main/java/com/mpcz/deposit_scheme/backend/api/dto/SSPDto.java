package com.mpcz.deposit_scheme.backend.api.dto;

public class SSPDto {

	private String consumerName;
	private String guardianName;
	private String mobileNumber;
	private String emailId;
	private String address;
	private String nscApplicationNo;
	private Long schemeType; // SUPERVISION=1 DEPOSIT=2
	private Long natureOfWork; // CONNECTION CATEORY
	private String dcCode;
	private String organizationName;
	private String isGoverment;
	private Double area;
	private Long areaUnit;
//	added code 2 -jan 
	private String memberId;

//	15-05-2025
	private String consumerNo;
	private Double appliedLoad;
	private String loadUnit;
	private String connectionType;
	
//	09-09-2025
	private Double registrationCharge;
	private Double meterCost;
	private Double securityDeposit;
	private Double supplyAffordingCharges;
	private Double totalAmount;
	private String purposeOfInstallation;
	private String phase;
	
	private String premiseAreaType;
//	19-11-2025
	private String meteringStatus;
	private String connectionCategory;
	private String caste;
	
//	likha gya code 15-12-2025
//	live code date 
	private Integer isOyt =1;
	
	
	
	
	

	private String isBpl;
	private String isShramik;
	
	
	
	

	public String getIsBpl() {
		return isBpl;
	}

	public void setIsBpl(String isBpl) {
		this.isBpl = isBpl;
	}

	public String getIsShramik() {
		return isShramik;
	}

	public void setIsShramik(String isShramik) {
		this.isShramik = isShramik;
	}

	public Integer getIsOyt() {
		return isOyt;
	}

	public void setIsOyt(Integer isOyt) {
		this.isOyt = isOyt;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getPremiseAreaType() {
		return premiseAreaType;
	}

	public void setPremiseAreaType(String premiseAreaType) {
		this.premiseAreaType = premiseAreaType;
	}

	public String getConnectionCategory() {
		return connectionCategory;
	}

	public void setConnectionCategory(String connectionCategory) {
		this.connectionCategory = connectionCategory;
	}

	public String getMeteringStatus() {
		return meteringStatus;
	}

	public void setMeteringStatus(String meteringStatus) {
		this.meteringStatus = meteringStatus;
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

	public Double getRegistrationCharge() {
		return registrationCharge;
	}

	public void setRegistrationCharge(Double registrationCharge) {
		this.registrationCharge = registrationCharge;
	}

	public Double getMeterCost() {
		return meterCost;
	}

	public void setMeterCost(Double meterCost) {
		this.meterCost = meterCost;
	}

	public Double getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(Double securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public Double getSupplyAffordingCharges() {
		return supplyAffordingCharges;
	}

	public void setSupplyAffordingCharges(Double supplyAffordingCharges) {
		this.supplyAffordingCharges = supplyAffordingCharges;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getConsumerNo() {
		return consumerNo;
	}

	public void setConsumerNo(String consumerNo) {
		this.consumerNo = consumerNo;
	}

	public Double getAppliedLoad() {
		return appliedLoad;
	}

	public void setAppliedLoad(Double appliedLoad) {
		this.appliedLoad = appliedLoad;
	}

	public String getLoadUnit() {
		return loadUnit;
	}

	public void setLoadUnit(String loadUnit) {
		this.loadUnit = loadUnit;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getIsGoverment() {
		return isGoverment;
	}

	public void setIsGoverment(String isGoverment) {
		this.isGoverment = isGoverment;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Long getAreaUnit() {
		return areaUnit;
	}

	public void setAreaUnit(Long areaUnit) {
		this.areaUnit = areaUnit;
	}

	public Long getNatureOfWork() {
		return natureOfWork;
	}

	public void setNatureOfWork(Long natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public String getDcCode() {
		return dcCode;
	}

	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}

	public Long getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(Long schemeType) {
		this.schemeType = schemeType;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNscApplicationNo() {
		return nscApplicationNo;
	}

	public void setNscApplicationNo(String nscApplicationNo) {
		this.nscApplicationNo = nscApplicationNo;
	}
	
	

}
