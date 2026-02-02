package com.mpcz.deposit_scheme.backend.api.dto;

import lombok.Data;

@Data
public class ContractorParticipateAndNotPartiDto {
	
		
		private Long srNo;
		
	
		private Boolean isParticipated;
		
	
		private Long userId;
		
	
		private String authorisedPersonE;
		
	
		private String companyNameE;
		
		
		private String userType;
		
	
		private String  authenticationId;
		
		
		private Long companyId;
		
	
		private String companyAdd1;
		
		
		private String companyAdd2;
		
		
		private Long companyPinCode;
		
	
		private String companyDist;
		
	
		private String companyCity;
		
	
		private String companyState;

		private String bigOrderAt;
		
		
		private String contractorCategory;
		
		
		private Double bigAmount;
		

		private String  consumerApplicationNo;
		
		private String  oyt;
		
		private String  consumerId;
		
		private String contactNo;
		
		

		public String getContactNo() {
			return contactNo;
		}

		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}

		public Long getSrNo() {
			return srNo;
		}

		public void setSrNo(Long srNo) {
			this.srNo = srNo;
		}

		public Boolean getIsParticipated() {
			return isParticipated;
		}

		public void setIsParticipated(Boolean isParticipated) {
			this.isParticipated = isParticipated;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getAuthorisedPersonE() {
			return authorisedPersonE;
		}

		public void setAuthorisedPersonE(String authorisedPersonE) {
			this.authorisedPersonE = authorisedPersonE;
		}

		public String getCompanyNameE() {
			return companyNameE;
		}

		public void setCompanyNameE(String companyNameE) {
			this.companyNameE = companyNameE;
		}

		public String getUserType() {
			return userType;
		}

		public void setUserType(String userType) {
			this.userType = userType;
		}

		public String getAuthenticationId() {
			return authenticationId;
		}

		public void setAuthenticationId(String authenticationId) {
			this.authenticationId = authenticationId;
		}

		public Long getCompanyId() {
			return companyId;
		}

		public void setCompanyId(Long companyId) {
			this.companyId = companyId;
		}

		public String getCompanyAdd1() {
			return companyAdd1;
		}

		public void setCompanyAdd1(String companyAdd1) {
			this.companyAdd1 = companyAdd1;
		}

		public String getCompanyAdd2() {
			return companyAdd2;
		}

		public void setCompanyAdd2(String companyAdd2) {
			this.companyAdd2 = companyAdd2;
		}

		public Long getCompanyPinCode() {
			return companyPinCode;
		}

		public void setCompanyPinCode(Long companyPinCode) {
			this.companyPinCode = companyPinCode;
		}

		public String getCompanyDist() {
			return companyDist;
		}

		public void setCompanyDist(String companyDist) {
			this.companyDist = companyDist;
		}

		public String getCompanyCity() {
			return companyCity;
		}

		public void setCompanyCity(String companyCity) {
			this.companyCity = companyCity;
		}

		public String getCompanyState() {
			return companyState;
		}

		public void setCompanyState(String companyState) {
			this.companyState = companyState;
		}

		public String getBigOrderAt() {
			return bigOrderAt;
		}

		public void setBigOrderAt(String bigOrderAt) {
			this.bigOrderAt = bigOrderAt;
		}

		public String getContractorCategory() {
			return contractorCategory;
		}

		public void setContractorCategory(String contractorCategory) {
			this.contractorCategory = contractorCategory;
		}

		public Double getBigAmount() {
			return bigAmount;
		}

		public void setBigAmount(Double bigAmount) {
			this.bigAmount = bigAmount;
		}

		public String getConsumerApplicationNo() {
			return consumerApplicationNo;
		}

		public void setConsumerApplicationNo(String consumerApplicationNo) {
			this.consumerApplicationNo = consumerApplicationNo;
		}

		public String getOyt() {
			return oyt;
		}

		public void setOyt(String oyt) {
			this.oyt = oyt;
		}

		public String getConsumerId() {
			return consumerId;
		}

		public void setConsumerId(String consumerId) {
			this.consumerId = consumerId;
		}

		@Override
		public String toString() {
			return "ContractorParticipateAndNotPartiDto [srNo=" + srNo + ", isParticipated=" + isParticipated
					+ ", userId=" + userId + ", authorisedPersonE=" + authorisedPersonE + ", companyNameE="
					+ companyNameE + ", userType=" + userType + ", authenticationId=" + authenticationId
					+ ", companyId=" + companyId + ", companyAdd1=" + companyAdd1 + ", companyAdd2=" + companyAdd2
					+ ", companyPinCode=" + companyPinCode + ", companyDist=" + companyDist + ", companyCity="
					+ companyCity + ", companyState=" + companyState + ", bigOrderAt=" + bigOrderAt
					+ ", contractorCategory=" + contractorCategory + ", bigAmount=" + bigAmount
					+ ", consumerApplicationNo=" + consumerApplicationNo + ", oyt=" + oyt + ", consumerId=" + consumerId
					+ ", contactNo=" + contactNo + "]";
		}
		
		
		

}
