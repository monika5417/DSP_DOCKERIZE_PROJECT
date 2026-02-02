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

@EqualsAndHashCode(exclude = {"locationDetails", "substations"})
@ToString(exclude =  {"locationDetails", "substations"})
@Entity(name = "DistributionCenter")
@Where(clause = "is_deleted=false")
@Table(name = "distribution_center")
public @Data class DistributionCenter extends Auditable<Long> { 
	/*
		 * 
		 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "distribution_center_seq", sequenceName = "distribution_center_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distribution_center_seq")
	@Column(name = "dc_id")
	private Long dcId;

	@Column(name = "dc_name")
	private String dcName;
	
	@Column(name = "dc_code")
	private String dcCode;

		
	@ManyToOne
	@JoinColumn(name = "subdiv_id", referencedColumnName = "subdiv_id")
	private SubDivision dcSubdivision;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "substationDistributionCenter", cascade = CascadeType.ALL)
	private Set<Substation> substations;
	
	
	@ManyToOne
	@JoinColumn(name = "DISTRICT_ID", referencedColumnName = "DISTRICT_ID")
	private District district;

	@Column(name="NGB_DC_CD")
	private String ngbDcCd;

	public String getNgbDcCd() {
		return ngbDcCd;
	}


	public void setNgbDcCd(String ngbDcCd) {
		this.ngbDcCd = ngbDcCd;
	}


	public Long getDcId() {
		return dcId;
	}


	public void setDcId(Long dcId) {
		this.dcId = dcId;
	}


	public String getDcName() {
		return dcName;
	}


	public void setDcName(String dcName) {
		this.dcName = dcName;
	}


	public String getDcCode() {
		return dcCode;
	}


	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}


	public SubDivision getDcSubdivision() {
		return dcSubdivision;
	}


	public void setDcSubdivision(SubDivision dcSubdivision) {
		this.dcSubdivision = dcSubdivision;
	}


	public Set<Substation> getSubstations() {
		return substations;
	}


	public void setSubstations(Set<Substation> substations) {
		this.substations = substations;
	}


	public District getDistrict() {
		return district;
	}


	public void setDistrict(District district) {
		this.district = district;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumerDistributionCenter", cascade = CascadeType.ALL)
//	private Set<ConsumerLocationDetails> locationDetails;

	
	
	
}
