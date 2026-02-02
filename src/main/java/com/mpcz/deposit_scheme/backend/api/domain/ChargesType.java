package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;



@Entity(name = "ChargesType")
@Table(name = "CHARGES_TYPE")
public @Data class ChargesType extends Auditable<Long> {

	    @Id
	    @SequenceGenerator(name = "CHARGES_TYPE_SEQ",sequenceName = "CHARGES_TYPE_SEQ",allocationSize = 1)
	    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CHARGES_TYPE_SEQ")
		@Column(name = "CHARGE_TYPE_ID")
		private Long chargeTypeId; 
			
		@Column(name = "NAME")
		private String name;

		public Long getChargeTypeId() {
			return chargeTypeId;
		}

		public void setChargeTypeId(Long chargeTypeId) {
			this.chargeTypeId = chargeTypeId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	
}
