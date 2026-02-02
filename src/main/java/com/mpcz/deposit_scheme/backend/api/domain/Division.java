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
 * 
 * @author Aditya Vyas
 * @version 1.0
 */
@Entity(name = "Division")
@ToString(exclude = { "users", "subDivsions", "locationDetails","billingCycleScheduling"  })
@EqualsAndHashCode(exclude = { "users", "subDivsions", "locationDetails","billingCycleScheduling"  })
@Where(clause = "is_deleted=false")
@Table(name = "division")
public @Data class Division extends Auditable<Long> {
	/*
		 * 
		 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "division_seq", sequenceName = "division_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "division_seq")
	@Column(name = "div_id")
	private Long divisionId;

	@Column(name = "division")
	private String division;

	@Column(name = "division_code")
	private String divisionCode;

//	@Column(name = "group_a")
//	private String groupA;
//
//	@Column(name = "group_b")
//	private String groupB;
//
//	@Column(name = "group_c")
//	private String groupC;
//
//	@Column(name = "group_d")
//	private String groupD;
//
//	@Column(name = "group_e")
//	private String groupE;

//	@Column(name = "group_a_status")
//	private boolean groupAStatus;
//	
//	@Column(name = "group_b_status")
//	private boolean groupBStatus;
//	
//	@Column(name = "group_c_status")
//	private boolean groupCStatus;
//	
//	@Column(name = "group_d_status")
//	private boolean groupDStatus;
//
//	@Column(name = "group_e_status")
//	private boolean groupEStatus;

//	@Column(name = "bill_cyc_code")
//	private String billCycCode;

	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userDivision", cascade = CascadeType.ALL)
	private Set<User> users;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subdivisionDivision", cascade = CascadeType.ALL)
	private Set<SubDivision> subDivsions;

	@ManyToOne
	@JoinColumn(name = "circle_id", referencedColumnName = "circle_id")
	private Circle divisionCircle;

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<SubDivision> getSubDivsions() {
		return subDivsions;
	}

	public void setSubDivsions(Set<SubDivision> subDivsions) {
		this.subDivsions = subDivsions;
	}

	public Circle getDivisionCircle() {
		return divisionCircle;
	}

	public void setDivisionCircle(Circle divisionCircle) {
		this.divisionCircle = divisionCircle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumerDivision", cascade = CascadeType.ALL)
//	private Set<ConsumerLocationDetails> locationDetails;
	
	
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "division", cascade = CascadeType.ALL)
//	private Set<BillingCycleScheduling> billingCycleScheduling;
	
	

}
