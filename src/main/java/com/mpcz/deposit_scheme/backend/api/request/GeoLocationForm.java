package com.mpcz.deposit_scheme.backend.api.request;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Geo location from")
@Data
public class GeoLocationForm {

	
	@ApiModelProperty("consumer Application number must be String  not be null")
	@NotEmpty
	@NotNull
	private String consumerApplicationNo;
	
	@ApiModelProperty("starting longitude must be String  not be null")
	@NotEmpty
	@NotNull
	private String startingLongitude;
	
	@ApiModelProperty("starting latitude must be String not be null")
	@NotEmpty
	@NotNull
	private String startingLatitude ;
	
	@ApiModelProperty("ending longitude must be Strig not be null")
	@NotEmpty
	@NotNull
	private String endingLongitude;
	
	
	@ApiModelProperty("ending latitude must be string not null")
	@NotEmpty
	@NotNull
	private String endingLatitude;
	
	
	@ApiModelProperty("jpg file must be not null")
	@NotEmpty
	@NotNull
	private MultipartFile file;
	
	
	@ApiModelProperty
	@NotEmpty
	@NotNull
	private MultipartFile file1;


	public String getConsumerApplicationNo() {
		return consumerApplicationNo;
	}


	public void setConsumerApplicationNo(String consumerApplicationNo) {
		this.consumerApplicationNo = consumerApplicationNo;
	}


	public String getStartingLongitude() {
		return startingLongitude;
	}


	public void setStartingLongitude(String startingLongitude) {
		this.startingLongitude = startingLongitude;
	}


	public String getStartingLatitude() {
		return startingLatitude;
	}


	public void setStartingLatitude(String startingLatitude) {
		this.startingLatitude = startingLatitude;
	}


	public String getEndingLongitude() {
		return endingLongitude;
	}


	public void setEndingLongitude(String endingLongitude) {
		this.endingLongitude = endingLongitude;
	}


	public String getEndingLatitude() {
		return endingLatitude;
	}


	public void setEndingLatitude(String endingLatitude) {
		this.endingLatitude = endingLatitude;
	}


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public MultipartFile getFile1() {
		return file1;
	}


	public void setFile1(MultipartFile file1) {
		this.file1 = file1;
	}
	
	
	
}
