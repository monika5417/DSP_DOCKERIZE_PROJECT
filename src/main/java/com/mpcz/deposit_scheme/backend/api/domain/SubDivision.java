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

@EqualsAndHashCode(exclude = {"locationDetails", "distributionCenters"})
@ToString(exclude = {"locationDetails", "distributionCenters"})
@Entity(name = "SubDivision")
@Where(clause = "is_deleted=false")
@Table(name = "sub_division" )
public @Data class SubDivision extends Auditable<Long> { 
	/*
		 * 
		 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "sub_division_seq", sequenceName = "sub_division_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_division_seq")
	@Column(name = "subdiv_id")
	private Long subDivisionId;

	@Column(name = "sub_division")
	private String subDivision;
	
	@Column(name = "sub_div_code")
	private String subDivisionCode;

	@ManyToOne
	@JoinColumn(name = "division_id", referencedColumnName = "div_id")
	private Division subdivisionDivision;
	
//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumerSubDivision", cascade = CascadeType.ALL)
//	private Set<ConsumerLocationDetails> locationDetails;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dcSubdivision", cascade = CascadeType.ALL)
	private Set<DistributionCenter> distributionCenters;

	public Long getSubDivisionId() {
		return subDivisionId;
	}

	public void setSubDivisionId(Long subDivisionId) {
		this.subDivisionId = subDivisionId;
	}

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getSubDivisionCode() {
		return subDivisionCode;
	}

	public void setSubDivisionCode(String subDivisionCode) {
		this.subDivisionCode = subDivisionCode;
	}

	public Division getSubdivisionDivision() {
		return subdivisionDivision;
	}

	public void setSubdivisionDivision(Division subdivisionDivision) {
		this.subdivisionDivision = subdivisionDivision;
	}

	public Set<DistributionCenter> getDistributionCenters() {
		return distributionCenters;
	}

	public void setDistributionCenters(Set<DistributionCenter> distributionCenters) {
		this.distributionCenters = distributionCenters;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}















