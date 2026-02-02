package com.mpcz.deposit_scheme.backend.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Tariff Category Model Form.")
public @Data class TariffCategoryForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Tariff Category Name must not be null", required = true)
	@NotEmpty
	private String tariffCategoryName;

	@ApiModelProperty(notes = "Tariff Category Code must not be null", required = true)
	@NotEmpty
	private String tariffCategoryCode;

	@ApiModelProperty(notes = "Tariff Category Description must not be null", required = true)
	@NotEmpty
	private String tariffCategoryDescription;

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
