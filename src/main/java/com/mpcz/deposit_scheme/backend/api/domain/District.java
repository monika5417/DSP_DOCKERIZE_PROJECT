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
 
@EqualsAndHashCode(exclude = {"distributionCenters"})
@ToString(exclude = {"distributionCenters"})
@Entity(name = "District")
@Table(name = "DISTRICT" )
public @Data class District extends Auditable<Long> { 
		/*
			 * 
			 */
		private static final long serialVersionUID = 1L;

		@Id
		@SequenceGenerator(name = "district_seq", sequenceName = "district_seq", allocationSize = 1)
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_seq")
		@Column(name = "DISTRICT_ID")
		private Long districtId;

		@Column(name = "DISTRICT_NAME")
		private String districtName;
		
		@JsonIgnore
		@OneToMany(fetch = FetchType.LAZY, mappedBy = "district", cascade = CascadeType.ALL)
		private Set<DistributionCenter> distributionCenters;

		public Long getDistrictId() {
			return districtId;
		}

		public void setDistrictId(Long districtId) {
			this.districtId = districtId;
		}

		public String getDistrictName() {
			return districtName;
		}

		public void setDistrictName(String districtName) {
			this.districtName = districtName;
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
