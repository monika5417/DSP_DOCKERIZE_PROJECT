package com.mpcz.deposit_scheme.backend.api.dto;

import java.util.Date;
import java.util.Set;

import com.mpcz.deposit_scheme.backend.api.domain.Role;

import lombok.Data;

public @Data class UserDTO {

	private Long adUserId;
	private String userId;
	private String userName;
	private Date lastLoggedInDate;
	private String userEmailId;
	private Long loginAttemp;
	private String mobileNo;
	private String accessLevel;
	private String aadharNo;
	private String designationName;
	private Set<Role> userRoles;
	private String discomName;
	private String regionName;
	private String circleName;
	private String divisionName;
	private String subDivisionName;
	private String dcName;
	private String feederName;
	private String substationName;
	public Long getAdUserId() {
		return adUserId;
	}
	public void setAdUserId(Long adUserId) {
		this.adUserId = adUserId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLastLoggedInDate() {
		return lastLoggedInDate;
	}
	public void setLastLoggedInDate(Date lastLoggedInDate) {
		this.lastLoggedInDate = lastLoggedInDate;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public Long getLoginAttemp() {
		return loginAttemp;
	}
	public void setLoginAttemp(Long loginAttemp) {
		this.loginAttemp = loginAttemp;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	public String getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public Set<Role> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}
	public String getDiscomName() {
		return discomName;
	}
	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getSubDivisionName() {
		return subDivisionName;
	}
	public void setSubDivisionName(String subDivisionName) {
		this.subDivisionName = subDivisionName;
	}
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	public String getFeederName() {
		return feederName;
	}
	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}
	public String getSubstationName() {
		return substationName;
	}
	public void setSubstationName(String substationName) {
		this.substationName = substationName;
	}

	
}
