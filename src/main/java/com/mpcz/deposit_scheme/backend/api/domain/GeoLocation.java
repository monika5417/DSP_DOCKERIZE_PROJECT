package com.mpcz.deposit_scheme.backend.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Entity(name = "GEO_LOCATION")
@Table(name = "GeoLocation")
public @Data class GeoLocation extends Auditable<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "GEO_LOCATION_SEQ", sequenceName = "GEO_LOCATION_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEO_LOCATION_SEQ")
	@Column(name = "GEO_LOCATION_ID")
	private Long geoLocationId;

	@Column(name = "CONSUMER_APPLICATION_NO")
	private String consumerApplicationNo;

	@Column(name = "STARTING_LONGITUDE")
	private String startingLongitude;

	@Column(name = "STARTING_LATITUDE")
	private String startingLatitude;

	@Column(name = "ENDING_LONGITUDE")
	private String endingLongitude;

	@Column(name = "ENDING_LATITUDE")
	private String endingLatitude;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	private MultipartFile file1;

	
	@OneToOne
	@JoinColumn(name = "START_DOC_ID", referencedColumnName = "UPLOAD_ID")
	private Upload startDoc;
	
	@OneToOne
	@JoinColumn(name = "END_DOC_ID", referencedColumnName = "UPLOAD_ID")
	private Upload endDoc;

	public Long getGeoLocationId() {
		return geoLocationId;
	}

	public void setGeoLocationId(Long geoLocationId) {
		this.geoLocationId = geoLocationId;
	}

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

	public Upload getStartDoc() {
		return startDoc;
	}

	public void setStartDoc(Upload startDoc) {
		this.startDoc = startDoc;
	}

	public Upload getEndDoc() {
		return endDoc;
	}

	public void setEndDoc(Upload endDoc) {
		this.endDoc = endDoc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
