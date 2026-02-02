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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(exclude = { "locationDetails", "feeders" })
@ToString(exclude = { "locationDetails", "feeders" })
//@Where(clause = "is_deleted=false")
@Entity(name = "Substation")
@Table(name = "substation")
public @Data class Substation extends Auditable<Long> {
	/*
		 * 
		 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "substation_substation_seq", sequenceName = "substation_substation_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "substation_substation_seq")
	@Column(name = "substation_id")
	private Long subStationId;

	@Column(name = "substation_name")
	private String subStationName;

	@Column(name = "substation_code")
	private String subStationCode;

	@ManyToOne
	@JoinColumn(name = "dc_id", referencedColumnName = "dc_id")
	private DistributionCenter substationDistributionCenter;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "feederSubstation", cascade = CascadeType.ALL)
	private Set<Feeder> feeders;

//	@JsonIgnore
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumerSubStation", cascade = CascadeType.ALL)
//	private Set<ConsumerLocationDetails> locationDetails;

	/********** new columns added ***************/

	@Column(name = "division_id")
	private String divisionId;

	public Long getSubStationId() {
		return subStationId;
	}

	public void setSubStationId(Long subStationId) {
		this.subStationId = subStationId;
	}

	public String getSubStationName() {
		return subStationName;
	}

	public void setSubStationName(String subStationName) {
		this.subStationName = subStationName;
	}

	public String getSubStationCode() {
		return subStationCode;
	}

	public void setSubStationCode(String subStationCode) {
		this.subStationCode = subStationCode;
	}

	public DistributionCenter getSubstationDistributionCenter() {
		return substationDistributionCenter;
	}

	public void setSubstationDistributionCenter(DistributionCenter substationDistributionCenter) {
		this.substationDistributionCenter = substationDistributionCenter;
	}

	public Set<Feeder> getFeeders() {
		return feeders;
	}

	public void setFeeders(Set<Feeder> feeders) {
		this.feeders = feeders;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
