package com.mpcz.deposit_scheme.backend.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity(name = "TariffCategory")
@Table(name = "TARIFF_CATEGORY")
public @Data class TariffCategory extends Auditable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TARIFF_CATEGORY_SEQ", sequenceName = "TARIFF_CATEGORY_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TARIFF_CATEGORY_SEQ")
	@Column(name = "TARIFF_CATEGORY_ID", columnDefinition = "serial", updatable = false)
	private Long tariffCategoryId;

	@Column(name = "TARIFF_CATEGORY_NAME")
	private String tariffCategoryName;

	@Column(name = "TARIFF_CATEGORY_CODE")
	private String tariffCategoryCode;

	@Column(name = "TARIFF_CATEGORY_DESCRIPTION")
	private String tariffCategoryDescription;

	public Long getTariffCategoryId() {
		return tariffCategoryId;
	}

	public void setTariffCategoryId(Long tariffCategoryId) {
		this.tariffCategoryId = tariffCategoryId;
	}

	public String getTariffCategoryName() {
		return tariffCategoryName;
	}

	public void setTariffCategoryName(String tariffCategoryName) {
		this.tariffCategoryName = tariffCategoryName;
	}

	public String getTariffCategoryCode() {
		return tariffCategoryCode;
	}

	public void setTariffCategoryCode(String tariffCategoryCode) {
		this.tariffCategoryCode = tariffCategoryCode;
	}

	public String getTariffCategoryDescription() {
		return tariffCategoryDescription;
	}

	public void setTariffCategoryDescription(String tariffCategoryDescription) {
		this.tariffCategoryDescription = tariffCategoryDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
