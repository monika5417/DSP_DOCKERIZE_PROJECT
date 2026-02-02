package com.mpcz.deposit_scheme.backend.api.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * @author Aditya Vyas
 * @version 1.0
 */

@Entity(name = "Region")
@EqualsAndHashCode(exclude = { "circles", "users" ,"locationDetails","billingCycleScheduling"})
@ToString(exclude = { "circles", "users","locationDetails","billingCycleScheduling"}) 
@Table(name = "region")
public @Data class Region extends Auditable<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "region_seq", sequenceName = "region_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_seq")
	@Column(name = "region_id", columnDefinition = "serial", updatable = false)
	private Long regionId;

	
	@Column(name = "region")
	private String region;

	@Column(name = "region_code")
	private String regionCode;


	@ManyToOne
	@JoinColumn(name = "discom_id", referencedColumnName = "discom_id")
	private Discom regionDiscom;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "circleRegion", cascade = CascadeType.ALL)
	private Set<Circle> circles;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userRegion", cascade = CascadeType.ALL)
	private Set<User> users;

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Discom getRegionDiscom() {
		return regionDiscom;
	}

	public void setRegionDiscom(Discom regionDiscom) {
		this.regionDiscom = regionDiscom;
	}

	public Set<Circle> getCircles() {
		return circles;
	}

	public void setCircles(Set<Circle> circles) {
		this.circles = circles;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumerRegion", cascade = CascadeType.ALL)
//	private Set<ConsumerLocationDetails> locationDetails;
//		
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "region", cascade = CascadeType.ALL)
//	private Set<BillingCycleScheduling> billingCycleScheduling;
	
	
	
	
	
}
