package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * @author Aditya Vyas
 * @version 1.0
 */

@Entity(name = "Circle")
@EqualsAndHashCode(exclude = { "divisions", "users", "locationDetails","billingCycleScheduling" })
@ToString(exclude = { "divisions", "users", "locationDetails","billingCycleScheduling" }) 
@Table(name = "circle")

public @Data class Circle extends Auditable<Long> {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "circle_seq", sequenceName = "circle_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "circle_seq")
	@Column(name = "circle_id", columnDefinition = "serial", updatable = false)
	private Long circleId;

	

	@Column(name = "circle")
	private String circle;

	@Column(name = "circle_code")
	private String circleCode;

	@ManyToOne
	@JoinColumn(name = "region_id", referencedColumnName = "region_id")
	private Region circleRegion;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userCircle", cascade = CascadeType.ALL)
	private Set<User> users;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "divisionCircle", cascade = CascadeType.ALL)
	private Set<Division> divisions;

	public Long getCircleId() {
		return circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

	public Region getCircleRegion() {
		return circleRegion;
	}

	public void setCircleRegion(Region circleRegion) {
		this.circleRegion = circleRegion;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Division> getDivisions() {
		return divisions;
	}

	public void setDivisions(Set<Division> divisions) {
		this.divisions = divisions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumerCircle", cascade = CascadeType.ALL)
//	private Set<ConsumerLocationDetails> locationDetails;
//
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "circle", cascade = CascadeType.ALL)
//	private Set<BillingCycleScheduling> billingCycleScheduling;
	
	
	

}
