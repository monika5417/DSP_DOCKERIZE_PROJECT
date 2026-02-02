package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * @author Hemant Verma
 * @version 1.0
 */

@Entity
@EqualsAndHashCode(exclude = { "userRoles", "userLoginAttempts" })
@ToString(exclude = { "userRoles", "userLoginAttempts" })
@Table(name = "ad_user")
public @Data class User extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_user_seq")
	@SequenceGenerator(name = "ad_user_seq", sequenceName = "ad_user_seq", allocationSize = 1)
	@Column(name = "ad_user_id")
	private Long adUserId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "userid")
	private String userId;

	@JsonIgnore
	@Column(name = "user_credentials")
	private String userCredentials;

	@Column(name = "login_attemp")
	private Long loginAttemp;

	@JsonIgnore
	@Column(name = "login_status")
	private String loginStatus;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@JsonIgnore
	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_logged_in_date")
	private Date lastLoggedInDate;

	@Column(name = "user_email_id")
	private String userEmailId;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "access_level")
	private String accessLevel;

	@Column(name = "aadhar_number")
	private String aadharNo;

	@Column(name = "is_first_login")
	private Boolean isFirstLogin;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {
//            CascadeType.MERGE,
			CascadeType.REFRESH })
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "ad_user_id", referencedColumnName = "ad_user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id", updatable = false))
	private List<Role> userRoles = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", referencedColumnName = "designation_id")
	private Designation userDesignation;

//	sandeep, starts

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISCOM_ID", referencedColumnName = "DISCOM_ID")
	private Discom userDiscom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", referencedColumnName = "region_id")
	private Region userRegion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "circle_id", referencedColumnName = "circle_id")
	private Circle userCircle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "div_id", referencedColumnName = "div_id")
	private Division userDivision;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUB_DIVISION_ID", referencedColumnName = "SUBDIV_ID")
	private SubDivision userSubDivision;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTION_CENTER_ID", referencedColumnName = "DC_ID")
	private DistributionCenter userDc;

//	sandeep, ends

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserLoginAttempts> userLoginAttempts;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FEEDER_ID", referencedColumnName = "FEEDER_ID")
	private Feeder  userFeeder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBSTATION_ID", referencedColumnName = "SUBSTATION_ID")
	private Substation userSubstation;
	

	public Long getAdUserId() {
		return adUserId;
	}

	public void setAdUserId(Long adUserId) {
		this.adUserId = adUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(String userCredentials) {
		this.userCredentials = userCredentials;
	}

	public Long getLoginAttemp() {
		return loginAttemp;
	}

	public void setLoginAttemp(Long loginAttemp) {
		this.loginAttemp = loginAttemp;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
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

	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public List<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Role> userRoles) {
		this.userRoles = userRoles;
	}

	public Designation getUserDesignation() {
		return userDesignation;
	}

	public void setUserDesignation(Designation userDesignation) {
		this.userDesignation = userDesignation;
	}

	public Discom getUserDiscom() {
		return userDiscom;
	}

	public void setUserDiscom(Discom userDiscom) {
		this.userDiscom = userDiscom;
	}

	public Region getUserRegion() {
		return userRegion;
	}

	public void setUserRegion(Region userRegion) {
		this.userRegion = userRegion;
	}

	public Circle getUserCircle() {
		return userCircle;
	}

	public void setUserCircle(Circle userCircle) {
		this.userCircle = userCircle;
	}

	public Division getUserDivision() {
		return userDivision;
	}

	public void setUserDivision(Division userDivision) {
		this.userDivision = userDivision;
	}

	public SubDivision getUserSubDivision() {
		return userSubDivision;
	}

	public void setUserSubDivision(SubDivision userSubDivision) {
		this.userSubDivision = userSubDivision;
	}

	public DistributionCenter getUserDc() {
		return userDc;
	}

	public void setUserDc(DistributionCenter userDc) {
		this.userDc = userDc;
	}

	public Set<UserLoginAttempts> getUserLoginAttempts() {
		return userLoginAttempts;
	}

	public void setUserLoginAttempts(Set<UserLoginAttempts> userLoginAttempts) {
		this.userLoginAttempts = userLoginAttempts;
	}

	public Feeder getUserFeeder() {
		return userFeeder;
	}

	public void setUserFeeder(Feeder userFeeder) {
		this.userFeeder = userFeeder;
	}

	public Substation getUserSubstation() {
		return userSubstation;
	}

	public void setUserSubstation(Substation userSubstation) {
		this.userSubstation = userSubstation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
